package resourceservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@FunctionalInterface
public interface FileValidator {
     Boolean validateFile (MultipartFile multipartFile);
}
