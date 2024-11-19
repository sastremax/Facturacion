package edu.coderhouse.jpa.exceptions;

public class DuplicateDocNumberException extends RuntimeException {
    public DuplicateDocNumberException(String message) {
        super(message);
    }
}
