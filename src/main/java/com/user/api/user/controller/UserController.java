package com.user.api.user.controller;

import com.user.api.user.DTO.UpdateContactRequest;
import com.user.api.user.DTO.UserDTO;
import com.user.api.user.DTO.UserResponseDTO;
import com.user.api.user.service.UserService;
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

    @PutMapping("/{id}/Contact")
    public ResponseEntity<UserResponseDTO>updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateContactRequest updateContactRequest){
        UserResponseDTO update = userService.updateUser(id,updateContactRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(update);
    }
}
