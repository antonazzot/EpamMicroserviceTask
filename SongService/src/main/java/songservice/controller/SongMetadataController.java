package songservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import songservice.model.SongDTO;
import songservice.service.SongMetadataService;

import java.util.Arrays;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class SongMetadataController {

    private final SongMetadataService songMetadataService;

    @PostMapping("/save/{metadatadto}")
    @ResponseBody
    public void saveMetadata (@RequestBody SongDTO songDTO) {
      songMetadataService.saveSongMetadataFromKafka(songDTO);
    }

    @DeleteMapping("/delete/{deleteid}")
    @ResponseBody
    public void deleteMetadata (@PathVariable Integer [] deleteid) {
        songMetadataService.deleteById(Arrays.asList(deleteid));
    }

    @GetMapping("/songs/{id}")
    @ResponseBody
    public Optional<String> getSongsMetadata (@PathVariable(name = "id") Integer id) {
        return songMetadataService.getSongsById(id);
    }

}
