package co.com.library.management.repository;

import co.com.library.management.model.Resources;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends MongoRepository<Resources, String> {
    Iterable<Resources> findByType(String type);

    Iterable<Resources> findByTheme(String theme);

    Iterable<Resources> findByName(String name);
}
