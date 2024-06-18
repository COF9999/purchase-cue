package com.project.restful.exeptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String detail){
        super(detail);
    }


}
