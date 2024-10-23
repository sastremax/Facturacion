package edu.coderhouse.jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    @NotBlank
    @Schema(description = "Código de estado HTTP", example = "404")
    String statusCode;

    @NotBlank
    @Schema(description = "Estado de la respuesta", example = "Not Found")
    String status;

    @NotBlank
    @Schema(description = "Mensaje de error detallado", example = "Cliente no encontrado")
    String msg;

    @Schema(description = "Campo relacionado al error", example = "id")
    String field;

}
