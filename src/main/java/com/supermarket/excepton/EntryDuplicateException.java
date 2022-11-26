package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class EntryDuplicateException extends RuntimeException {
    public EntryDuplicateException() {
    }

    public EntryDuplicateException(String message) {
        super(message);
    }
}
