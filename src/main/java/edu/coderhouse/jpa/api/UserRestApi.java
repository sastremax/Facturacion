package edu.coderhouse.jpa.api;

import edu.coderhouse.jpa.entities.UserEntity;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class UserRestApi {

    final String url = "https://jsonplaceholder.typicode.com/users";

    public ResponseEntity<UserEntity[]> getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserEntity[]> users = restTemplate.getForEntity(this.url, UserEntity[].class);
        return users;
    }

}
