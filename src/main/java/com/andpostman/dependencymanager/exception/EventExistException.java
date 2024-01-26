package com.andpostman.dependencymanager.exception;

public class EventExistException extends RuntimeException{
    public EventExistException() {
        super("Данный проект существует в таблице Events");
    }
}
