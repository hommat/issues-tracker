package com.mateuszziomek.cqrs.query.dispatcher;

public class TooManyQueryHandlersException extends IllegalStateException {
    public TooManyQueryHandlersException() {
        super("Query can only have one handler.");
    }
}
