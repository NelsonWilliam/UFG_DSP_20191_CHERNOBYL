package br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageService {

    private static String IMG_PREFIX = "data:image/gif;base64,";

    public static String convertImageToString(MultipartFile imageFile) throws IOException {
        String base64 = encodeFileToBase64(imageFile);
        return IMG_PREFIX + base64;
    }

    private static String encodeFileToBase64(MultipartFile multipartFile) throws IOException {
        File file = saveMultipartFileToFile(multipartFile);
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
        } finally {
            file.delete();
        }
    }

    private static File saveMultipartFileToFile(MultipartFile multiFile) throws IOException {
        String filename = multiFile.getOriginalFilename();
        byte[] bytes = multiFile.getBytes();
        Path path = Paths.get(filename);
        Files.write(path, bytes);
        return new File(filename);
    }
}
