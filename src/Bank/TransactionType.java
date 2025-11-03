package Bank;

import java.io.Serializable;

/**
 * Defines the different types of transactions that can occur on a bank
 * account. Having a dedicated enum keeps transaction recording consistent
 * across the application and makes it easier to build analytics on top of the
 * recorded history.
 */
public enum TransactionType implements Serializable {
    ACCOUNT_CREATED,
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER_IN,
    TRANSFER_OUT,
    INTEREST,
    FEE
}

