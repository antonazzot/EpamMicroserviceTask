package resourceservice.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import resourceservice.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileValidatorServiceTest {
    private static final File file = new File("src/test/resources/mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");
    private static final File file2 = new File("src/test/resources/mp3/Снимок экрана 2022-04-08 в 11.29.46 AM.png");

    @Test
    void validateFile() throws IOException {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file",
                        file.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file));

        MockMultipartFile multipartFile2 =
                new MockMultipartFile("file",
                        file2.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file2));

        Assertions.assertTrue(multipartFile.isEmpty() || Objects.requireNonNull(multipartFile.getContentType()).isEmpty() || !multipartFile.getContentType().equals("audio/mpeg"));
        Assertions.assertFalse(multipartFile2.isEmpty() || Objects.requireNonNull(multipartFile2.getContentType()).isEmpty() || multipartFile2.getContentType().equals("audio/mpeg"));

    }
}