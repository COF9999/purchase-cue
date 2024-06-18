package com.project.restful.exeptions;

public class BadRequestExeption extends RuntimeException{

    public BadRequestExeption(String detail){
        super(detail);
    }
}

