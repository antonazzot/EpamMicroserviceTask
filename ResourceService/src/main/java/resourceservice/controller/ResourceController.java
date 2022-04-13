package resourceservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.service.SongService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final SongService songService;
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveFile (@RequestBody MultipartFile file) {
        return songService.saveSong(file);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> get (@RequestParam (name = "id") Integer id) {
        return songService.getSongById(id);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> delete (@RequestParam ("deleteid") Integer [] id) {
        return songService.deleteSongById(id);
    }

    @GetMapping("/getmeta")
    @ResponseBody
    public ResponseEntity<?> getMetadata (@RequestParam (name = "id") Integer id) {
        return songService.getMetaById(id);
    }
}
