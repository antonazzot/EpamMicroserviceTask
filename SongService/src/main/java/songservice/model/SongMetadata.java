package songservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "metadata")
public class SongMetadata {
    @Id
    Integer id;
    @Column (name = "matadata")
    String metadata;
}
