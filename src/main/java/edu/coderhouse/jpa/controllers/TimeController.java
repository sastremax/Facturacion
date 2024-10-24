package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.services.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/time")
public class TimeController {

    @Autowired
    private MainService mainService;

    @Operation(summary = "Obtener la fecha y hora actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha y hora obtenidas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al obtener la fecha y hora")
    })
    @GetMapping("/now")
    public LocalDateTime getCurrentTime() {
        return mainService.getCurrentUtcTime();
    }

}