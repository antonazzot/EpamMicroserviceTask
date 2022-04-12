package songservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import songservice.service.SongMetadataService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class SongMetadataController {

    private final SongMetadataService songMetadataService;

    @PostMapping("/{metaid}")
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> saveMetadata (@RequestParam Integer metaid, @RequestBody Metadata metadata) {
        songMetadataService.saveSong(metaid, metadata.toString());

//        PutObjectResult putObjectResult = amazonS3.putObject("mybucketname", "secondname", file.getInputStream(), extractObjectMetadata(file));
//       File file1 = convertMultiPartToFile(file);
//        restTemplate.postForObject("http://PARSER/parser/parse/{file}", file1,
//        String.class, file1);
        return ResponseEntity.ok(2);
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<?> ok () {

        return ResponseEntity.ok("get");
    }

}
