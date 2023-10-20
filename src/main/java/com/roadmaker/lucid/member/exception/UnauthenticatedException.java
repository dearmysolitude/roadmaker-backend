package com.roadmaker.lucid.member.exception;

public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException() { super(); }
    public UnauthenticatedException(String message) { super(message); }
    public UnauthenticatedException(String message, Throwable error) { super(message, error); }
}
