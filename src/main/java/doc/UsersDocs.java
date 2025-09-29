package doc;

import com.user.api.user.UserDTO;
import com.user.api.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


import com.user.api.user.UpdateContactRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.util.UUID;

@Tag(name = "Usuários", description = "Operações de registro e gerenciamento de usuários")
public interface UsersDocs {

    // =========================
    // POST /users  (público)
    // =========================
    @Operation(
            summary = "Cria um novo usuário",
            description = "Endpoint público para cadastro de usuário. Valida os campos e retorna os dados do usuário criado (sem senha)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo 201",
                                    value = """
                    {
                      "userId": "7c1d0b2e-3f77-4e6a-9fd4-6b2cfe7bb0a1",
                      "fullName": "Ana Silva",
                      "email": "ana@example.com",
                      "address": "Rua X, 123",
                      "phoneNumber": "+5585987654321"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail já cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo 409",
                                    value = """
                    {
                      "status": 409,
                      "error": "Conflict",
                      "message": "E-mail já está em uso.",
                      "path": "/api/v1/users"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Falha de validação do payload",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Object.class)),
                            examples = @ExampleObject(
                                    name = "Exemplo 400",
                                    value = """
                    {
                      "status": 400,
                      "error": "Bad Request",
                      "message": "Validation failed",
                      "fieldErrors": [
                        {"field": "email", "message": "must be a well-formed email address"},
                        {"field": "password", "message": "size must be between 8 and 64"}
                      ],
                      "path": "/api/v1/users"
                    }
                    """
                            )
                    )
            )
    })
    @RequestBody(
            required = true,
            description = "Dados necessários para criar um usuário",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class),
                    examples = @ExampleObject(
                            name = "Exemplo de requisição",
                            value = """
                {
                  "fullName": "Ana Silva",
                  "email": "ana@example.com",
                  "password": "S3nh@123",
                  "address": "Rua X, 123",
                  "phoneNumber": "+5585987654321"
                }
                """
                    )
            )
    )
    ResponseEntity<UserResponseDTO> createUser(UserDTO userDTO);


    // ============================================
    // PATCH /users/{id}/contact  (protegido Basic)
    // ============================================
    @Operation(
            summary = "Atualiza contato (endereço e/ou telefone)",
            description = "Atualização parcial do contato do usuário. Aceita apenas `address` e/ou `phoneNumber`.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato atualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo 200",
                                    value = """
                    {
                      "userId": "7c1d0b2e-3f77-4e6a-9fd4-6b2cfe7bb0a1",
                      "fullName": "Ana Silva",
                      "email": "ana@example.com",
                      "address": "Rua Y, 456",
                      "phoneNumber": "+5585988000000"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nada para atualizar / falha de validação",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo 400",
                                    value = """
                    {
                      "status": 400,
                      "error": "Bad Request",
                      "message": "Nada para atualizar.",
                      "path": "/api/v1/users/{id}/contact"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo 404",
                                    value = """
                    {
                      "status": 404,
                      "error": "Not Found",
                      "message": "Usuário não encontrado.",
                      "path": "/api/v1/users/{id}/contact"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão (ex.: tentando alterar outro usuário)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @RequestBody(
            required = true,
            description = "Campos opcionais para atualização parcial",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateContactRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Apenas endereço",
                                    value = """
                    { "address": "Rua Y, 456" }
                    """
                            ),
                            @ExampleObject(
                                    name = "Apenas telefone",
                                    value = """
                    { "phoneNumber": "+5585988000000" }
                    """
                            ),
                            @ExampleObject(
                                    name = "Ambos",
                                    value = """
                    { "address": "Rua Y, 456", "phoneNumber": "+5585988000000" }
                    """
                            )
                    }
            )
    )
    ResponseEntity<UserResponseDTO> updateContact(UUID id, UpdateContactRequest request);
}
