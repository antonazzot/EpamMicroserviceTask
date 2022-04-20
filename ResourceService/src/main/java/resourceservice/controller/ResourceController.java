package resourceservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.exception.MyCustomAppException;
import resourceservice.service.SongService;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final SongService songService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        Integer saveSongId;
        try {
            saveSongId = songService.saveSong(file);
        }
        catch (MyCustomAppException ex) {
            log.error("Song service exception: ={}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(saveSongId);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> get (@RequestParam(name = "id") Integer id) {
        try {
            return  ResponseEntity.ok(songService.getSongById(id));
        }
        catch (MyCustomAppException ex) {
            log.error("Song service exception: ={}", ex.getMessage());
           return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Id problem", "Id not found").build();
        }
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> delete (@RequestParam ("deleteid") Integer [] id) {
        return ResponseEntity.ok(songService.deleteSongById(id));
    }

    @GetMapping("/getmeta")
    @ResponseBody
    public ResponseEntity<?> getMetadata (@RequestParam (name = "id") Integer id) {
            return ResponseEntity.of(Optional.of(songService.getMetaById(id)));
    }

}
