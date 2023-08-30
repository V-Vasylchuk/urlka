package userway.test.urlka.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "urls")
@NoArgsConstructor
public class Url implements Serializable {
    @Id
    private String id;
    private String originalLink;
    @Indexed(unique = true)
    private String shortLink;

    public Url(String originalLink) {
        this.originalLink = originalLink;
    }
}
