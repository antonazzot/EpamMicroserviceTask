package contracts

import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return even when number input is even"
    request{
        method GET()
        url("http://localhost:8081//metadata/songs") {
            queryParameters {
                parameter("id", "183")
            }
        }
    }
    response {
        body("{\"xmpDM:genre\":\"232\",\"xmpDM:album\":\"Albombombom\",\"xmpDM:trackNumber\":\"21/1\",\"xmpDM:releaseDate\":\"2022\",\"xmpDM:artist\":\"Anton Barbardjan\",\"dc:creator\":\"Anton Barbardjan\",\"xmpDM:audioCompressor\":\"MP3\",\"xmpDM:audioChannelType\":\"Stereo\",\"version\":\"MPEG 3 Layer III Version 1\",\"xmpDM:logComment\":\"eng - \\nEdited by Maztr Audio Tag Editor. https://maztr.com/audiotageditor\",\"xmpDM:audioSampleRate\":\"44100\",\"channels\":\"2\",\"dc:title\":\"IT My -track title\",\"xmpDM:duration\":\"147.46780395507812\",\"Content-Type\":\"audio/mpeg\",\"samplerate\":\"44100\"}")
        status(200)
    }
}
