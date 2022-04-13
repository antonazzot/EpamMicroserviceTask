package resourceservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
@Service
public class FileValidatorService implements FileValidator {
    @Override
    public Boolean validateFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || Objects.requireNonNull(multipartFile.getContentType()).isEmpty() || !multipartFile.getContentType().equals("audio/mpeg"))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
