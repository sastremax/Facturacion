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
                (rs, rowNum) -> {
                    Client client = new Client(
                            rs.getString("name"),
                            rs.getString("lastname"),
                            rs.getString("docnumber")
                    );
                    client.setId(rs.getInt("id"));
                    return client;
                }
        );
    }

    public void updateClient(int id, Client client) {
        this.jdbc.update(
                "UPDATE client SET name = ?, lastname = ?, docnumber = ? WHERE id = ?",
                client.getName(), client.getLastname(), client.getDocnumber(), id
        );
    }

    public void deleteClient(int id) {
        this.jdbc.update("DELETE FROM client WHERE id = ?", id);
    }

}
