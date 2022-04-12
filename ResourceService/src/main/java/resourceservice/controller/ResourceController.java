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
    @SneakyThrows
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        if (songService.validateFile(file))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IllegalArgumentException("foo"));

        return ResponseEntity.of(songService.saveSong(file).describeConstable());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> ok () {
        return ResponseEntity.ok("get");
    }
}
