package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.dto.TimeApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class MainService {

    public LocalDateTime getCurrentUtcTime() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://worldclockapi.com/api/json/utc/now";

        try {
            TimeApiResponse response = restTemplate.getForObject(url, TimeApiResponse.class);
            if (response != null) {
                return LocalDateTime.parse(response.getCurrentDateTime());
            } else {
                return LocalDateTime.now();
            }
        } catch (Exception e) {
            return LocalDateTime.now();
        }

    }

}
