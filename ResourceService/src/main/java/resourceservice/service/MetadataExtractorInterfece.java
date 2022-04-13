package resourceservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import resourceservice.model.SongDTO;

public interface MetadataExtractorInterfece {

    public ObjectMetadata extractMetadata (SongDTO songDTO);

    public String getMetadataFromBD (Integer id);

}
