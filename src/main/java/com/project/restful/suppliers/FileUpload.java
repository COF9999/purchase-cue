package com.project.restful.suppliers;

import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.models.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class FileUpload {

    private static final String BASE_UPLOAD_DIR = "/home/christian09/Documents/NUCLEAR-CUE-5/restful/src/main/resources/static/images";
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png","image/jpg");

    public String uploadFile(MultipartFile file,
                             String newFileName,
                            Users users) {


        if (file.isEmpty()) {
            throw new BadRequestExeption("Please select a file to upload");
        }

        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            throw new BadRequestExeption("Only JPEG, PNG and JPG files are allowed");
        }

        try {
            String sanitizedFileName = "";
            if(newFileName.equalsIgnoreCase("")){
                sanitizedFileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", "_");
            }else {
                String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                sanitizedFileName = newFileName.replaceAll("\\s+", "_") + extension;
            }


            String pathFolder1 = users.getIdentification().replaceAll("\\s+", "");
            String pathFolder2 = users.getUsername().replaceAll("\\s+", "");
            String path = Paths.get(pathFolder1,pathFolder2).toString();
            String dirReturn = path+"/"+sanitizedFileName;
            String fullDirPath = Paths.get(BASE_UPLOAD_DIR, pathFolder1, pathFolder2).toString();
            File dir = new File(fullDirPath);
            // Create the directory if it does not exist
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Ensure the new file name does not have spaces and append the correct extension


            // Save the file to the specified directory
            String filePath = Paths.get(fullDirPath, sanitizedFileName).toString();
            System.out.println(dirReturn);
            File newFile = new File(filePath);
            if (newFile.exists()) {
                throw new BadRequestExeption("File already exists with the same name");
            }

            file.transferTo(new File(filePath));

            return dirReturn;

        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestExeption("ERROR DEL SERVIDOR AL CARGAR LA IMAGEN");
        }
    }
}
