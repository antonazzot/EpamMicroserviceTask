package songservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserMetadataConvectorJsonImpl implements UserMetadataConvectorJson {
    @Override
    public String getJsonFromUserMetadata(Map<String, String> userMeta) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(userMeta);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userMeta.toString();
    }
}
