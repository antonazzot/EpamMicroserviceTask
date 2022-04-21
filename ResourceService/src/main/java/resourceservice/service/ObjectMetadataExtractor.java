package resourceservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ObjectMetadataExtractor {
    public ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.getUserMetadata().put("fileExtension", file.getOriginalFilename());
        return objectMetadata;
    }
    public ObjectMetadata extractObjectMetadata(MultipartFile file, Map<String, String > userMeta) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setUserMetadata(userMeta);
//        objectMetadata.getUserMetadata().put("fileExtension", file.getOriginalFilename());
        return objectMetadata;
    }
}
