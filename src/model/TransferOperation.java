package model;

public class TransferOperation {
    private final String fileName;
    private final String fromAccount;
    private final String toAccount;
    private final double amount;
    private final String status;
    private final String timestamp;
    private final Double fromBalanceAfter;
    private final Double toBalanceAfter;

    public TransferOperation(String fileName, String fromAccount, String toAccount, Double amount,
                             String status, String timestamp, Double fromBalanceAfter, Double toBalanceAfter) {
        this.fileName = fileName;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
        this.fromBalanceAfter = fromBalanceAfter;
        this.toBalanceAfter = toBalanceAfter;
    }

    @Override
    public String toString() {
        return timestamp + " | " + fileName +
                " | перевод с " + fromAccount + " (" + String.format("%.2f", fromBalanceAfter) + ") " +
                " на " + toAccount + " (" + String.format("%.2f", toBalanceAfter) + ") " +
                amount + " | " + status;
    }
}
