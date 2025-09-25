package com.user.api.user;

import com.user.api.auth.Auth;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String password;
    @Embedded
    private String address;

    @Column(length = 20)
    private String phoneNumber;
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private Auth auth = Auth.LOCAL;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(@Valid UserDTO userDTO){
        this.fullname = userDTO.fullName();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.address = userDTO.address();
        this.phoneNumber = userDTO.phoneNumber();
    }
}
