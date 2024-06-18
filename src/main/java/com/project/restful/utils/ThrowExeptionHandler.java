package com.project.restful.utils;

import com.project.restful.exeptions.BadRequestExeption;

public class ThrowExeptionHandler {

    public static void throwBadRequestFuntional(String message){
        throw new BadRequestExeption(message);
    }
}
