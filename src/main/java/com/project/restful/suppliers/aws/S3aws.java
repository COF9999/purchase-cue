package com.project.restful.suppliers.aws;

import com.project.restful.interfaces.FileUpload;
import com.project.restful.models.Users;
import com.project.restful.suppliers.FormaterImage;
import com.project.restful.suppliers.objects.FileUploadResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;



@Component(value = "S3")
public class S3aws implements FileUpload {

    private final S3Config s3Config;

    private final S3Client s3Client;

    private final String extension = "JPEG";

    public S3aws(S3Config s3Config,S3Client s3Client){
        this.s3Config = s3Config;
        this.s3Client = s3Client;
    }


    @Override
    public FileUploadResponseDto uploadFile(MultipartFile file, String newFileName, Users users) {
        try {
            byte[] bytes =   FormaterImage.QualitySizeWebp(file,200,200,0.80f);
            String key = String.format("%s.%s", UUID.randomUUID(),extension);

            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(s3Config.getNameBucket())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .key(key)
                    .build();


            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(
                  bytes
            ));

            return new FileUploadResponseDto(key,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
