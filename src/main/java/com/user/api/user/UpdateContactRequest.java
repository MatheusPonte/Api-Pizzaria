package com.user.api.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateContactRequest(
        @Size(max = 255, message = "Endereço muito longo")
        String address,

        @Size(min = 8, max = 20, message = "Telefone deve ter entre 8 e 20 caracteres")
        @Pattern(regexp = "^[+0-9()\\s.-]*$", message = "Telefone inválido")
        String phoneNumber
) {}

