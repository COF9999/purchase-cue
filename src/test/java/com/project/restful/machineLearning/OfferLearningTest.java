package com.project.restful.machineLearning;

import com.project.restful.dtos.machineLearning.MachineLearningRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class OfferLearningTest {

    private final MachineLearningPublication machineLearningPublication;


    @Autowired
    public OfferLearningTest(MachineLearningPublication machineLearningPublication){
        this.machineLearningPublication = machineLearningPublication;
    }

    @Test
    void test() throws InterruptedException {
        System.out.println("INICIO-PROBANDO");
        Thread.sleep(1000);
       // machineLearningPublication.feedMachineLearningModel(new MachineLearningRequestDto(18L,"ELECTRONICO",20000f,9));
        Thread.sleep(1000);
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
        System.out.println("FINAL-PROBANDO");
    }

    @Test
    void insert(){
        machineLearningPublication.feedMachineLearningModel(new MachineLearningRequestDto("20","ELECTRONICO",290000.0,(byte) 9));
    }

    @Test
    void metric(){
        String metric = machineLearningPublication.getMetric(new MachineLearningRequestDto("18","ELECTRONICO",130000.0,(byte) 8));
        System.out.println(metric);
    }





    @Test
    void integrateEmailInsert(){
        System.out.println("ARRIBA");
        insert();
        System.out.println("ABAJO");
    }
}
