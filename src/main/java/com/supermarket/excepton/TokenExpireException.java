package com.supermarket.excepton;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public class TokenExpireException extends RuntimeException {
    public TokenExpireException(String message) {
        super(message);
    }
}
