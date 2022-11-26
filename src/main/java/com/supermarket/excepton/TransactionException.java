package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
