package com.user.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "O nome Completo é obrigatorio")
        @Size(min=3, max=50, message = "O nome deve ter entre 3 e 50 caracteres")
        String fullName,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min=8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
        @Pattern(regexp = "^^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve conter ao menos 1 letra maiúscula e 1 letra minúscula"
        )
        String password,

        @NotBlank( message = "O endereço é obrigatorio")
        String address,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\+?[0-9]{2}\\s?\\(?[0-9]{2}\\)?\\s?[0-9]{4,5}-?[0-9]{4}$",
                        message = "Telefone inválido. Use o formato +55 (85) 99999-9999")
        String phoneNumber
) {
}
