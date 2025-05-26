package com.project.restful.suppliers.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${config.aws.acceskeyid}")
    private String accessKeyId;

    @Value("${config.aws.secretpassword}")
    private  String secretPassword;

    @Value("${config.aws.nameBucket}")
    private String nameBucket;

    @Bean
    public S3Client s3Client(){
        Region region = Region.US_EAST_2;

        AwsBasicCredentials awsBasicCredentials =  AwsBasicCredentials.create(accessKeyId,secretPassword);

       return S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
    }


    public String getNameBucket() {
        return nameBucket;
    }
}
