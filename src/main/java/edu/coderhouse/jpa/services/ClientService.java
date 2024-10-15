package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private JdbcTemplate jdbc;

    public ClientService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void createClient(Client client) {
        this.jdbc.update("INSERT INTO client (name, lastname, docnumber) VALUES (?, ?, ?)", client.getName(), client.getLastname(), client.getDocnumber());
    }

    public List<Client> getClients() {
        return this.jdbc.query(
                "SELECT id, name, lastname, docnumber FROM client",
                (rs, rowNum) -> new Client(
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("docnumber")
                )
        );
    }

}
