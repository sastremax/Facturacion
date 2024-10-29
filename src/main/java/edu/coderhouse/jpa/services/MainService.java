package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.dto.TimeApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    public LocalDate getCurrentUtcDate() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://timeapi.io/api/Time/current/zone?timeZone=UTC";

        try {
            TimeApiResponse response = restTemplate.getForObject(url, TimeApiResponse.class);
            if (response != null && response.getDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                logger.info("Fecha obtenida desde la API: {}", response.getDate());
                return LocalDate.parse(response.getDate(), formatter);
            } else {
                logger.warn("ALa API retornó una respuesta nula. Usando la hora local.");
                return LocalDate.now();
            }
        } catch (Exception e) {
            logger.error("Error al obtener la hora de la API. Usando la hora local.", e);
            return LocalDate.now();
        }

    }

}
