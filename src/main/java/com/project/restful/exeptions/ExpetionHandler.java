package com.project.restful.exeptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExpetionHandler {

    /**
     * Return exeption generated in Runtime
     * @param request
     * @param exeption
     * @return ErrorMessage with the corresponding error
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestExeption.class
    })
    @ResponseBody
    public ErrorMessage badResquest(HttpServletRequest request,Exception exeption){
        return new ErrorMessage(exeption,request.getRequestURI());
    }


    /**
     * Return exeption generated in Runtime
     * @param request
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFound(HttpServletRequest request,Exception exception){
        return new ErrorMessage(exception,request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            IllegalArgumentException.class
    })
    @ResponseBody
    public ErrorMessage unauthorized(HttpServletRequest request,Exception exception){
        return new ErrorMessage(exception,request.getRequestURI());
    }
}
