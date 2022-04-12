package resourceservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.service.SongService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final SongService songService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        if (songService.validateFile(file))
            return ResponseEntity.badRequest().body(new IllegalArgumentException("File mustn't be empty or file format not supported"));
        return ResponseEntity.of(songService.saveSong(file).describeConstable());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> get (@RequestParam (name = "id") Integer id) {
        return songService.getSongById(id);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> delete (@RequestParam ("deleteid") Integer [] id) {
        return ResponseEntity.ok(songService.deleteSongById(id));
    }

}
