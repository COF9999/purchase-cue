package com.project.restful.suppliers;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class FormaterImage {

    public static byte[] QualitySizeWebp(MultipartFile multipartFile, int heigth, int width, float quality) throws IOException {
        // Leer la imagen original en formato WebP
        BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());

        // Redimensionar la imagen
        Image resizedImage = originalImage.getScaledInstance(width, heigth, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Comprimir y guardar como WebP
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("JPEG");
            if (!writers.hasNext()) {
                throw new IllegalArgumentException("No se encontró un escritor para WebP");
            }

            ImageWriter writer = writers.next();
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // Calidad entre 0 (baja) y 1 (alta)
            }

            writer.write(null, new javax.imageio.IIOImage(outputImage, null, null), param);
            writer.dispose();

            return baos.toByteArray();
        }
    }
}
