package org.example.skillboxnews.advice;

public record ErrorDescription(String message) {
    public ErrorDescription(RuntimeException e) {
        this(e.getMessage());
    }
}
