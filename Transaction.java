import java.io.Serializable;
import java.util.Date;

/**
 * Transaction Class (Model)
 * Stores details of a single financial operation.
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 2L;
    
    private String type; // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER_OUT", "TRANSFER_IN"
    private double amount;
    private Date date;
    private String referenceAccount; // Account number for transfers, or null

    public Transaction(String type, double amount, String referenceAccount) {
        this.type = type;
        this.amount = amount;
        this.referenceAccount = referenceAccount;
        this.date = new Date();
    }

    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public String getReferenceAccount() { return referenceAccount; }

    @Override
    public String toString() {
        String ref = (referenceAccount == null || referenceAccount.isEmpty()) ? "" : " (Ref: " + referenceAccount + ")";
        // Using SimpleDateFormat for date formatting
        return String.format("[%s] %-15s $%.2f%s", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), type, amount, ref);
    }
}