package com.project.restful.interfaces;

import com.project.restful.models.Users;
import com.project.restful.suppliers.objects.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
    FileUploadResponseDto uploadFile(MultipartFile file,
                                     String newFileName,
                                     Users users);
}
