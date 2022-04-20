package cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import resourceservice.controller.ResourceController;
import resourceservice.service.SongService;

import java.io.File;
import java.io.IOException;

public class MyStepdefs {

    @Autowired

    private SongService songService;

    @When("audio file upload in server")
    public void audioFileUploadInServer() throws IOException {
        File file = new File("src/test/resources/mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");
        MockMultipartFile multipartFile =
                new MockMultipartFile("file",
                        file.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file));

        songService.saveSong(multipartFile);
    }

    @Then("It start extract metadata and upload audio in aws server")
    public void itStartExtractMetadataAndUploadAudioInAwsServer() {
    }
}
