package com.user.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser (@Valid @RequestBody UserDTO userDTO){
        UserResponseDTO created = userService.registerUser(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO>updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO){
        UserResponseDTO update = userService.updateUser(id,userDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(update);
    }
}
