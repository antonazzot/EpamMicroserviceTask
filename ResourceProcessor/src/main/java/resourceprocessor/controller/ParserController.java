package resourceprocessor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import resourceprocessor.processorservice.ProcessorService;
import resourceprocessor.processorservice.SongDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class ParserController {
    private final ProcessorService processorService;
    
    @PostMapping("/parse/{file}")
    @ResponseBody
    public SongDTO postFile (@RequestBody SongDTO songDTO) {
     return    processorService.extractMetadataAndSave(songDTO);
    }
}
