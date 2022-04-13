package songservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class MetaDataConvertorJson implements MetaDataConvertor {
    @Override
    public String getMetadata(ObjectMetadata objectMetadata) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objectMetadata.getUserMetadata());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objectMapper.toString();
    }
}
