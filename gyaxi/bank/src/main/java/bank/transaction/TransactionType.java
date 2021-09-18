package bankremake.transaction;

public enum TransactionType {
    DEPOSIT, WITHDRAW, SEND, RECEIVE;

    public boolean isTargetNeeded() {
        return this == SEND || this == RECEIVE;
    }
}