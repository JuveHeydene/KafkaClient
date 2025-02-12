package com.example.producerService.secure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
    @AllowFiltering
    Optional<User> findByUsername(String username);   
    
    @AllowFiltering
    boolean existsByUsername(String username);
}