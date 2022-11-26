package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException() {
    }

    public EntryNotFoundException(String message) {
        super(message);
    }
}
