package resourceprocessor.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;

import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class ParserController {

    private final RestTemplate restTemplate;

    @PostMapping("/parse/{file}")
    @ResponseBody
   @SneakyThrows
    public String some (@RequestBody File file) {


        Tika tika = new Tika();
        Reader parse = tika.parse(file);
        String detect = tika.detect(file);
        String s = tika.parseToString(file);


        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata1 = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();
        Mp3Parser  mp3Parser = new  Mp3Parser();
        mp3Parser.parse(inputstream, handler, metadata1, pcontext);
        LyricsHandler lyrics = new LyricsHandler(inputstream,handler);

        System.out.println("!!!!!  " + metadata1.toString()) ;

        System.out.println(
        );

        System.out.println();
        while(lyrics.hasLyrics()) {
            System.out.println(lyrics.toString());
        }

        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata1.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata1.get(name));
        }

        restTemplate.postForObject("http://METADATA/metadata/{metaid}", metadata1,
                String.class, 1, metadata1);

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler1 = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream1 = new FileInputStream(file);
        ParseContext context = new ParseContext();
        parser.parse(inputstream1, handler1, metadata, context);
        String[] metadataNames1 = metadata.names();
        System.out.println("Meta Data:");
        for (String name : metadataNames1) {
            System.out.println(name + ": " + metadata.get(name));
        }
        return "f";
    }


}
