package resourceservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final AmazonS3 amazonS3;

    @PostMapping
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        log.info("fileinfo:={}", file.getContentType()+"  " + file.getName() + "   "+  file.getSize()) ;
        Bucket mybucketname = amazonS3.createBucket("mybucketname");
        PutObjectResult putObjectResult = amazonS3.putObject("onexlab", "secondname", file.getInputStream(), extractObjectMetadata(file));
        return ResponseEntity.ok(2);
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        objectMetadata.getUserMetadata().put("fileExtension", file.getOriginalFilename());

        return objectMetadata;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> ok () {

        return ResponseEntity.ok("get");
    }
}
