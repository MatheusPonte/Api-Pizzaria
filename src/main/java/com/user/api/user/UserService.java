package com.user.api.user;

import com.user.api.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser (UserDTO userDTO){
        String normalizedEmail = userDTO.email().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)){

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


}
