import java.awt.*;
import javax.swing.*;
public class BankManagementApp extends JFrame {
    private Bank bank;
    private Account currentAccount;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField loginAccountField;
    private JPasswordField loginPinField;
    private JTextField createNameField, createInitialDepositField;
    private JPasswordField createPinField, createPinConfirmField;
    private JLabel userWelcomeLabel, userBalanceLabel;
    private JTextField actionAmountField, transferRecipientField, transferAmountField;
    private JTextArea historyArea;
    private JTextArea allTransactionsArea, allAccountsArea;
    private JTextField deleteAccountField;

    public BankManagementApp() {
        this.bank = new Bank();
        this.currentAccount = null;

        setTitle("OOP Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); 
        setResizable(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createCreateAccountPanel(), "CreateAccount");
        mainPanel.add(createUserPanel(), "UserActions");
        mainPanel.add(createAdminPanel(), "AdminActions");

        add(mainPanel);
        centerWindow();
        setVisible(true);
        cardLayout.show(mainPanel, "Login");
    }

    private void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - this.getSize().width/2, dim.height/2 - this.getSize().height/2);
    }
    
    private void clearUserFields() {
        loginAccountField.setText("");
        loginPinField.setText("");
        createNameField.setText("");
        createPinField.setText("");
        createPinConfirmField.setText("");
        createInitialDepositField.setText("");
        actionAmountField.setText("");
        transferRecipientField.setText("");
        transferAmountField.setText("");
    }
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Bank Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Account/Username:"), gbc);
        loginAccountField = new JTextField(20);
        gbc.gridx = 1; panel.add(loginAccountField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("PIN:"), gbc);
        loginPinField = new JPasswordField(20);
        gbc.gridx = 1; panel.add(loginPinField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JButton createAccButton = new JButton("Create New Account");
        createAccButton.addActionListener(e -> {
            clearUserFields();
            cardLayout.show(mainPanel, "CreateAccount");
        });
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(createAccButton, gbc);

        return panel;
    }
    
    private JPanel createCreateAccountPanel() {
         JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Register New User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Full Name:"), gbc);
        createNameField = new JTextField(15);
        gbc.gridx = 1; panel.add(createNameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("4-Digit PIN:"), gbc);
        createPinField = new JPasswordField(15);
        gbc.gridx = 1; panel.add(createPinField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Confirm PIN:"), gbc);
        createPinConfirmField = new JPasswordField(15);
        gbc.gridx = 1; panel.add(createPinConfirmField, gbc);
        gbc.gridy = 4; gbc.gridx = 0; panel.add(new JLabel("Initial Deposit (min 100):"), gbc);
        createInitialDepositField = new JTextField(15);
        gbc.gridx = 1; panel.add(createInitialDepositField, gbc);

        JButton createButton = new JButton("Register Account");
        createButton.setBackground(new Color(52, 168, 83));
        createButton.setForeground(Color.WHITE);
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);
        createButton.addActionListener(e -> handleCreateAccount());
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        userWelcomeLabel = new JLabel("", SwingConstants.CENTER);
        userWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userBalanceLabel = new JLabel("Balance: $0.00", SwingConstants.CENTER);
        userBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        infoPanel.add(userWelcomeLabel);
        infoPanel.add(userBalanceLabel);
        panel.add(infoPanel, BorderLayout.NORTH);

        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerSplit.setResizeWeight(0.5); 
        JPanel actionContainer = new JPanel();
        actionContainer.setLayout(new BoxLayout(actionContainer, BoxLayout.Y_AXIS));
        JPanel dwPanel = new JPanel(new GridBagLayout());
        dwPanel.setBorder(BorderFactory.createTitledBorder("Deposit / Withdraw"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        actionAmountField = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 0; dwPanel.add(new JLabel("Enter Amount:"), gbc);
        gbc.gridy = 1; dwPanel.add(actionAmountField, gbc);

        gbc.gridwidth = 1;
        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(52, 168, 83));
        depositButton.setForeground(Color.WHITE);
        depositButton.setOpaque(true); depositButton.setBorderPainted(false);
        depositButton.addActionListener(e -> handleDeposit());
        gbc.gridy = 2; gbc.gridx = 0; dwPanel.add(depositButton, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(234, 67, 53));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setOpaque(true); withdrawButton.setBorderPainted(false);
        withdrawButton.addActionListener(e -> handleWithdraw());
        gbc.gridx = 1; dwPanel.add(withdrawButton, gbc);
        
        actionContainer.add(dwPanel);
        actionContainer.add(Box.createVerticalStrut(15)); 
        actionContainer.add(Box.createVerticalGlue()); 

        JPanel transferPanel = new JPanel(new GridBagLayout());
        transferPanel.setBorder(BorderFactory.createTitledBorder("Transfer Funds"));
        GridBagConstraints gbcTransfer = new GridBagConstraints();
        gbcTransfer.insets = new Insets(5, 5, 5, 5);
        gbcTransfer.fill = GridBagConstraints.HORIZONTAL;
        
        gbcTransfer.gridwidth = 2;
        gbcTransfer.gridx = 0; gbcTransfer.gridy = 0; transferPanel.add(new JLabel("Recipient Account Number:"), gbcTransfer);
        transferRecipientField = new JTextField(10);
        gbcTransfer.gridy = 1; transferPanel.add(transferRecipientField, gbcTransfer);

        gbcTransfer.gridy = 2; transferPanel.add(new JLabel("Amount to Transfer:"), gbcTransfer);
        transferAmountField = new JTextField(10);
        gbcTransfer.gridy = 3; transferPanel.add(transferAmountField, gbcTransfer);
        
        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(251, 188, 5));
        transferButton.setForeground(Color.WHITE);
        transferButton.setOpaque(true); transferButton.setBorderPainted(false);
        transferButton.addActionListener(e -> handleTransfer());
        gbcTransfer.gridy = 4; transferPanel.add(transferButton, gbcTransfer);
        
        actionContainer.add(transferPanel);
        actionContainer.add(Box.createVerticalGlue()); 
        centerSplit.setLeftComponent(actionContainer);
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        centerSplit.setRightComponent(historyScroll);

        panel.add(centerSplit, BorderLayout.CENTER);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        panel.add(logoutButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel adminWelcome = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        adminWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(adminWelcome, BorderLayout.NORTH);
        
        JPanel deletePanel = new JPanel(new FlowLayout());
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Account"));
        deletePanel.add(new JLabel("Account to Delete:"));
        deleteAccountField = new JTextField(10);
        deletePanel.add(deleteAccountField);
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setBackground(new Color(234, 67, 53));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true); deleteButton.setBorderPainted(false);
        deleteButton.addActionListener(e -> handleDeleteAccount());
        deletePanel.add(deleteButton);
        topPanel.add(deletePanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        allAccountsArea = new JTextArea();
        allAccountsArea.setEditable(false);
        JScrollPane accountsScroll = new JScrollPane(allAccountsArea);
        tabbedPane.addTab("Customer List", accountsScroll);
        allTransactionsArea = new JTextArea();
        allTransactionsArea.setEditable(false);
        JScrollPane transactionsScroll = new JScrollPane(allTransactionsArea);
        tabbedPane.addTab("All Transactions", transactionsScroll);
        
        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        panel.add(logoutButton, BorderLayout.SOUTH);

        return panel;
    }

    private void handleLogin() {
        String inputId = loginAccountField.getText().trim();
        int pin = -1;
        try {
            pin = Integer.parseInt(new String(loginPinField.getPassword()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PIN must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (bank.isAdmin(inputId, pin)) {
            updateAdminView();
            cardLayout.show(mainPanel, "AdminActions");
        } else {
            currentAccount = bank.authenticateUser(inputId, pin);
            if (currentAccount != null) {
                updateUserView();
                cardLayout.show(mainPanel, "UserActions");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account/Username or PIN.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
        clearUserFields();
    }
    
    private void handleCreateAccount() {
        String name = createNameField.getText().trim();
        String pinStr = new String(createPinField.getPassword());
        String pinConfirmStr = new String(createPinConfirmField.getPassword());
        String initialDepositStr = createInitialDepositField.getText();

        if (name.isEmpty() || pinStr.isEmpty() || initialDepositStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!pinStr.equals(pinConfirmStr)) {
            JOptionPane.showMessageDialog(this, "PINs do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pinStr.length() != 4) {
            JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int pin;
        double initialDeposit;
        try {
            pin = Integer.parseInt(pinStr);
            initialDeposit = Double.parseDouble(initialDepositStr);
            if (initialDeposit < 100.00) throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PIN and Deposit must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Initial deposit must be at least $100.00.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Account newAccount = bank.createAccount(name, pin, initialDeposit);
        String message = String.format("Account Created Successfully!\nName: %s\nAccount Number: %s",
                                       newAccount.getAccountHolderName(), newAccount.getAccountNumber());
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);

        clearUserFields();
        cardLayout.show(mainPanel, "Login");
    }

    private void handleDeposit() {
        double amount = getActionAmount(actionAmountField.getText());
        if (amount <= 0) return;

        if (bank.deposit(currentAccount, amount)) {
            JOptionPane.showMessageDialog(this, String.format("Successfully deposited $%.2f.", amount), "Deposit Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Deposit failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateUserView();
    }

    private void handleWithdraw() {
        double amount = getActionAmount(actionAmountField.getText());
        if (amount <= 0) return;

        if (bank.withdraw(currentAccount, amount)) {
            JOptionPane.showMessageDialog(this, String.format("Successfully withdrew $%.2f.", amount), "Withdrawal Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Withdrawal failed. Check balance or amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateUserView();
    }
    
    private void handleTransfer() {
        String recipientAccNum = transferRecipientField.getText().trim();
        double amount = getActionAmount(transferAmountField.getText());
        if (amount <= 0) return;

        Account recipient = bank.findAccount(recipientAccNum);

        if (recipient == null) {
            JOptionPane.showMessageDialog(this, "Recipient account not found.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (recipientAccNum.equals(currentAccount.getAccountNumber())) {
            JOptionPane.showMessageDialog(this, "Cannot transfer to your own account.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (bank.transferFunds(currentAccount, recipientAccNum, amount)) {
            double senderFinalBalance = currentAccount.getBalance();
            double recipientFinalBalance = recipient.getBalance();

            String message = String.format(
                "Transfer Successful!\n\n" +
                "Recipient (%s) Name: %s\n" +
                "Amount Transferred: $%.2f\n\n" +
                "Your New Balance: $%.2f\n" +
                "Recipient's New Balance: $%.2f",
                recipientAccNum, 
                recipient.getAccountHolderName(),
                amount,
                senderFinalBalance,
                recipientFinalBalance
            );
            JOptionPane.showMessageDialog(this, message, "Transfer Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Transfer failed. Insufficient funds.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
        }
        updateUserView();
    }
    
    private void handleDeleteAccount() {
        String accNum = deleteAccountField.getText().trim();
        
        if (accNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete account " + accNum + "? This cannot be undone.", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
             if (bank.deleteAccount(accNum)) {
                 JOptionPane.showMessageDialog(this, "Account " + accNum + " deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
             } else {
                 JOptionPane.showMessageDialog(this, "Account " + accNum + " not found or cannot be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
             }
        }
        deleteAccountField.setText("");
        updateAdminView(); 
    }

    private void handleLogout() {
        currentAccount = null;
        clearUserFields();
        cardLayout.show(mainPanel, "Login");
    }

    private void updateUserView() {
        if (currentAccount != null) {
            userWelcomeLabel.setText("Welcome, " + currentAccount.getAccountHolderName() + " (Acc: " + currentAccount.getAccountNumber() + ")");
            userBalanceLabel.setText(String.format("Current Balance: $%.2f", currentAccount.getBalance()));
            actionAmountField.setText("");
            transferAmountField.setText("");
            transferRecipientField.setText("");
            StringBuilder sb = new StringBuilder();
            for (Transaction tx : currentAccount.getHistory()) {
                sb.append(tx.toString()).append("\n");
            }
            historyArea.setText(sb.toString());
            historyArea.setCaretPosition(historyArea.getDocument().getLength()); 
        }
    }
    
    private void updateAdminView() {
        StringBuilder accSb = new StringBuilder("Account Number | Name | Balance\n");
        accSb.append("----------------------------------------------------\n");
        for (Account acc : bank.getAllAccounts()) {
            accSb.append(String.format("%-15s| %-20s| $%.2f\n", 
                acc.getAccountNumber(), acc.getAccountHolderName(), acc.getBalance()));
        }
        allAccountsArea.setText(accSb.toString());
        StringBuilder txSb = new StringBuilder("Date | Type | Amount | Reference Account\n");
        txSb.append("----------------------------------------------------------------------------------------\n");
        for (Transaction tx : bank.getAllTransactions()) {
             String ref = tx.getReferenceAccount() == null ? "" : tx.getReferenceAccount();
             txSb.append(String.format("%s | %-12s | $%-10.2f | %s\n", 
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(tx.getDate()),
                tx.getType(), tx.getAmount(), ref));
        }
        allTransactionsArea.setText(txSb.toString());
    }
    
    private double getActionAmount(String amountStr) {
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                 JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                 return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankManagementApp();
        });
    }
}