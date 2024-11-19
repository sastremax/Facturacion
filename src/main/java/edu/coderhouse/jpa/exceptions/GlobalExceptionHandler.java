package edu.coderhouse.jpa.exceptions;

import edu.coderhouse.jpa.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateDocNumberException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateDocNumberException(DuplicateDocNumberException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "400",
                "Solicitud invalida",
                e.getMessage(),
                "docNumber"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "500",
                "Error Interno de Servidor",
                "Ocurrió un error inesperado",
                "internal_error"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
