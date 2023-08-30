package userway.test.urlka.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import userway.test.urlka.model.Url;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByOriginalLink(String originalLink);

    Optional<Url> findByShortLink(String shortUrl);
}
