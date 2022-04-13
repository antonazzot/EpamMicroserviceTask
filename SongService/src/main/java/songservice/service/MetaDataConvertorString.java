package songservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetaDataConvertorString implements MetaDataConvertor {
    @Override
    public String getMetadata(ObjectMetadata objectMetadata) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> userMetadata = objectMetadata.getUserMetadata();
        userMetadata.values().forEach(s-> stringBuilder.append(s+" : "+userMetadata.get(s)+", "));
        return stringBuilder.toString();
}
}
