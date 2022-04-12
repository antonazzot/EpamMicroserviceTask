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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final AmazonS3 amazonS3;
    private final RestTemplate restTemplate;

    @PostMapping
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        log.info("fileinfo:={}", file.getContentType()+"  " + file.getName() + "   "+  file.getSize()) ;
        amazonS3.createBucket("mybucketname");
        PutObjectResult putObjectResult = amazonS3.putObject("mybucketname", "secondname", file.getInputStream(), extractObjectMetadata(file));
       File file1 = convertMultiPartToFile(file);
        restTemplate.postForObject("http://PARSER/parser/parse/{file}", file1,
        String.class, file1);
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
    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
