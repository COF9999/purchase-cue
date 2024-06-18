package com.project.restful.exeptions;

public class ErrorMessage {
    private final String exeption;
    private final String message;
    private final String path;

    public ErrorMessage(Exception exception,String path){
        this.exeption = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
    }

    public String getExeption() {
        return exeption;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "exeption='" + exeption + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
