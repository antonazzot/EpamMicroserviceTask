package resourceprocessor.processorservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TikaExtractorService {

    public ObjectMetadata objectMetadataExtractor(File file) throws TikaException, IOException, SAXException {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        Tika tika = new Tika();
        Reader parse = tika.parse(file);
        String detect = tika.detect(file);
        String s = tika.parseToString(file);
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();
        Mp3Parser mp3Parser = new Mp3Parser();
        mp3Parser.parse(inputstream, handler, metadata, pcontext);
        LyricsHandler lyrics = new LyricsHandler(inputstream, handler);

        System.out.println("!!!!!  " + metadata.toString());
        String[] metadataNames = metadata.names();
        Map<String, String> userMetadata = new HashMap<>();

        for (String name : metadataNames) {
            userMetadata.put(name, metadata.get(name));
        }
        objectMetadata.setUserMetadata(userMetadata);

        return objectMetadata;

    }


}
