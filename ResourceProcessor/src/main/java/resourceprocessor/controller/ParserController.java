package resourceprocessor.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import resourceprocessor.processorservice.ProcessorService;
import resourceservice.model.SongDTO;

import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class ParserController {

    private final ProcessorService processorService;

    @PostMapping("/parse/{songdto}")
    @ResponseBody
    public ObjectMetadata some (@RequestBody SongDTO songDTO) {
    return processorService.extractMetadataAndSave(songDTO);
    }
}
