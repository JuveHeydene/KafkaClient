package com.example.producerService.secure;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table("api_keys")
@AllArgsConstructor
@Getter
@Setter
public class ApiKey {

    @PrimaryKey("key_id")
    private UUID id;

    // Correspond à user_id de la table users
    @Column("client_id")
    private UUID clientId;

    @Column("key")
    private String key;

    @Column("enabled")
    private boolean enabled;

}
