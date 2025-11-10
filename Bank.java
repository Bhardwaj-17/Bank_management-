import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Bank {
    private List<Account> accounts;
    private static final String DATA_FILE = "bank_accounts.dat";
    private static final String ADMIN_USER = "admin";
    private static final int ADMIN_PIN = 0000;

    public Bank() {
        this.accounts = new ArrayList<>();
        loadAccounts(); 

        if (this.accounts.stream().noneMatch(a -> a.getAccountHolderName().equals("Test User"))) {
             Account testAccount = new Account("Test User", 1234, 1500.00);
             this.accounts.add(testAccount);
             System.out.println("--- SYSTEM INITIALIZED ---");
             System.out.println("!!! USER ACCOUNT CREATED: Number: " + testAccount.getAccountNumber() + ", PIN: 1234");
             System.out.println("!!! ADMIN LOGIN: User: " + ADMIN_USER + ", PIN: " + ADMIN_PIN);
             System.out.println("--------------------------");
             saveAccounts();
        } else {
             System.out.println("Successfully loaded " + this.accounts.size() + " accounts from persistence file (" + DATA_FILE + ").");
        }
    }

    public boolean isAdmin(String username, int pin) {
        return username.equals(ADMIN_USER) && pin == ADMIN_PIN;
    }

    public Account findAccount(String accNum) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNum)) {
                return acc;
            }
        }
        return null;
    }

    public Account authenticateUser(String accNum, int pin) {
        Account acc = findAccount(accNum);
        if (acc != null && acc.getPin() == pin) {
            return acc;
        }
        return null;
    }
    
    public Account createAccount(String name, int pin, double initialDeposit) {
        Account newAccount = new Account(name, pin, initialDeposit);
        accounts.add(newAccount);
        Transaction initialTx = new Transaction("DEPOSIT", initialDeposit, "");
        newAccount.addTransaction(initialTx);
        saveAccounts();
        return newAccount;
    }

    public boolean deposit(Account account, double amount) {
        if (amount <= 0) return false;
        
        account.setBalance(account.getBalance() + amount);
        account.addTransaction(new Transaction("DEPOSIT", amount, ""));
        saveAccounts();
        return true;
    }

    public boolean withdraw(Account account, double amount) {
        if (amount <= 0 || account.getBalance() < amount) return false;
        
        account.setBalance(account.getBalance() - amount);
        account.addTransaction(new Transaction("WITHDRAWAL", amount, ""));
        saveAccounts();
        return true;
    }
    
    public boolean transferFunds(Account sender, String recipientAccNum, double amount) {
        if (amount <= 0 || sender.getBalance() < amount) return false;

        Account recipient = findAccount(recipientAccNum);
        if (recipient == null) return false;

        sender.setBalance(sender.getBalance() - amount);
        sender.addTransaction(new Transaction("TRANSFER_OUT", amount, recipientAccNum));
        
        recipient.setBalance(recipient.getBalance() + amount);
        recipient.addTransaction(new Transaction("TRANSFER_IN", amount, sender.getAccountNumber()));
        
        saveAccounts();
        return true;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts); 
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> all = new ArrayList<>();
        for (Account acc : accounts) {
            all.addAll(acc.getHistory());
        }
        all.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        return all;
    }
    
    public boolean deleteAccount(String accNum) {
        Account accountToDelete = findAccount(accNum);
        if (accountToDelete != null && !accountToDelete.getAccountHolderName().equals("Test User")) {
            accounts.remove(accountToDelete);
            saveAccounts();
            return true;
        }
        return false;
    }

    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println(">> Account data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    private void loadAccounts() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                @SuppressWarnings("unchecked")
                List<Account> loadedAccounts = (List<Account>) ois.readObject();
                this.accounts = loadedAccounts;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading accounts. File might be corrupted: " + e.getMessage());
                this.accounts = new ArrayList<>();
            }
        }
    }
}