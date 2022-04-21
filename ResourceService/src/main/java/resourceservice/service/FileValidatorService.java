package resourceservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
@Service
public class FileValidatorService implements FileValidator {
    @Override
    public Boolean validateFile(MultipartFile multipartFile) {
        return  (multipartFile.isEmpty() || Objects.requireNonNull(multipartFile.getContentType()).isEmpty() || !multipartFile.getContentType().equals("audio/mpeg"));
    }
}
