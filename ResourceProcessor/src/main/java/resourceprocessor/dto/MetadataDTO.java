package resourceprocessor.dto;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetadataDTO implements Serializable {
    private Integer songId;
    private ObjectMetadata objectMetadata;
}
