package edu.coderhouse.jpa.api;

import edu.coderhouse.jpa.entities.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRestApi {

    final String url = "https://jsonplaceholder.typicode.com/users";

    public ResponseEntity<UserEntity[]> getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                this.url,
                UserEntity[].class
        );
    }

    public UserEntity getUserById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        try {
            UserEntity user = restTemplate.getForObject(
                    this.url + "/{id}",
                    UserEntity.class,
                    params
            );
            return user;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("existe un error al obtener el usuario", e);
        }
    }

    public UserEntity saveUser(UserEntity user) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                this.url,
                user,
                UserEntity.class
        );
    }

    public UserEntity updateUser(int id, UserEntity user) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        restTemplate.put(
                this.url + "/{id}",
                user,
                params
        );
        return user;
    }

    public UserEntity deleteUser(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        UserEntity user = restTemplate.getForObject(
                this.url + "/{id}",
                UserEntity.class,
                params);
        restTemplate.delete(
                this.url + "/{id}",
                params
        );
        return user;
    }
}
