package com.user.api.user.DTO;

public record UserResponseDTO<UUID>(
        UUID userId,
        String fullName,
        String email,
        String address,
        String phoneNumber

) {
}
