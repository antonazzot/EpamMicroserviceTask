package resourceprocessor.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import resourceprocessor.processorservice.ProcessorService;
import resourceservice.model.SongDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class ParserController {

    private final ProcessorService processorService;

    @PostMapping("/parse/{songdto}")
    @ResponseBody
    public ObjectMetadata some (@RequestBody SongDTO songDTO) {
//    return processorService.extractMetadataAndSave(songDTO);
    return new ObjectMetadata();
    }
}
