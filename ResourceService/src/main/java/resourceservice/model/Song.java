package resourceservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column (name = "songname")
    String songName;
    @Column (name = "songsize")
    Long songSize;
    @Column (name = "bucket")
    String songAWSBucketName;
}
