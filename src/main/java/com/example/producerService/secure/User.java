package com.example.producerService.secure;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table("users")
public class User {

    @PrimaryKey("user_id")
    private UUID userId;

    @Column("username")
    private String username;

    @Column("email")
    private String email;
    
    @Column("password")
    private String password;

    @Column("role")
    private Role role;

    @Column("enabled")
    private boolean enabled;
}