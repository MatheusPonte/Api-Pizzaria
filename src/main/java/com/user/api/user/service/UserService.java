package com.user.api.user.service;

import com.user.api.auth.Auth;
import com.user.api.exception.EmailAlreadyUse;
import com.user.api.user.DTO.UpdateContactRequest;
import com.user.api.user.DTO.UserDTO;
import com.user.api.user.DTO.UserResponseDTO;
import com.user.api.user.entity.User;
import com.user.api.user.enums.UserRole;
import com.user.api.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser (UserDTO userDTO){
        String normalizedEmail = userDTO.email().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)){
            throw new EmailAlreadyUse();

        }
        User user = new User();
        user.setFullname((userDTO.fullName()));
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setAddress(userDTO.address());
        user.setActive(true);
        user.setPhoneNumber(userDTO.phoneNumber());
        user.setAuth(Auth.LOCAL);
        user.setUserRole(UserRole.USER);

        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getUserId(),
                saved.getFullname(),
                saved.getEmail(),
                saved.getAddress(),
                saved.getPhoneNumber()
        );
    }

    public UserResponseDTO login(UserRole userRole, UserDTO userDTO){

        return
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public UserResponseDTO updateUser(UUID id, UpdateContactRequest updateContactRequest){
        User userToUpdate = getUserById(id);

        if(updateContactRequest.address() != null && !updateContactRequest.address().isBlank()){
            String addr = updateContactRequest.address().trim();
            if(!addr.equals(userToUpdate.getAddress())){
                userToUpdate.setAddress((addr));
            }
        }

        if (updateContactRequest.phoneNumber() != null && !updateContactRequest.phoneNumber().isBlank()) {
            String normalized = updateContactRequest.phoneNumber().replaceAll("\\s+", "");
            if (!normalized.equals(userToUpdate.getPhoneNumber())) {
                userToUpdate.setPhoneNumber(normalized);

            }
        }

        User saved = userRepository.save(userToUpdate);
        return new UserResponseDTO(
                saved.getUserId(),
                saved.getFullname(),
                saved.getEmail(),
                saved.getAddress(),
                saved.getPhoneNumber()
        );

    }

//    @Transactional
//    public  UserResponseDTO updateUser(UUID id, UserDTO userDTO){
//         User userToUpdate = getUserById(id);
//
//        if(userDTO.address() != null){
//            userToUpdate.setAddress((userDTO.address()));
//        }
//
//        if(userDTO.phoneNumber() != null){
//            userToUpdate.setPhoneNumber(userDTO.phoneNumber());
//        }
//
//        User saved = userRepository.save(userToUpdate);
//        return new UserResponseDTO(
//                saved.getUserId(),
//                saved.getFullname(),
//                saved.getEmail(),
//                saved.getAddress(),
//                saved.getPhoneNumber()
//        );
    }
