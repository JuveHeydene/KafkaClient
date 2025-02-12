package com.example.producerService.secure;



import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends CassandraRepository<ApiKey, UUID> {

    // Méthode pour trouver une clé API par son champ 'key'
    Optional<ApiKey> findByKey(String key);

    // Méthode pour vérifier si une clé API existe
    boolean existsByKey(String key);
}
