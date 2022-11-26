package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class StatusException extends RuntimeException {
    public StatusException() {
    }

    public StatusException(String message) {
        super(message);
    }
}
