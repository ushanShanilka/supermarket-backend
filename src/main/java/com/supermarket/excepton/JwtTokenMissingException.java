package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException() {
    }

    public JwtTokenMissingException(String message) {
        super(message);
    }
}
