package resourceservice.service.changerservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import resourceservice.model.SongDTO;

public interface MetadataExtractorInterfece {

    public SongDTO extractMetadata (SongDTO songDTO);

    public String getMetadataFromBD (Integer  id);

}
