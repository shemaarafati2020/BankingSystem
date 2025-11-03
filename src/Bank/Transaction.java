package Bank;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single transaction that happened on a bank account. Each
 * transaction keeps track of the timestamp, the amount that was moved, the
 * resulting balance and a short description so that the history can be easily
 * displayed to the user.
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private final String description;

    public Transaction(TransactionType type, double amount, double balanceAfter, String description) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description == null ? "" : description.trim();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

