package com.project.restful.utils;

public class CalcPoints {

    public static Float calculatePoints(Float priceOffer, Double productPrice){

        float acum = 0f;
        int dividendum = 1000;
        if(priceOffer!=null){
            acum += priceOffer;
        }

        if(productPrice!=null){
            acum += (float) (double) productPrice;
            dividendum = dividendum - 250;
        }

        return acum/dividendum;
    }
}
