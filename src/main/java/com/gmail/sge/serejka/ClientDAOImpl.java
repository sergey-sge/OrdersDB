package com.gmail.sge.serejka;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private final Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Clients");
                statement.execute("CREATE TABLE Clients (id int AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(20) NOT NULL, email VARCHAR(40) NOT NULL, address VARCHAR(50));");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClient(Client client) {
        try{
            try (Statement statement = connection.createStatement()){
                statement.execute("INSERT INTO Clients (name, email, address) VALUES (" +
                        "'" + client.getName() + "', '" + client.getEmail() + "', '" +
                        client.getAddress() + "')");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement()){
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Clients")){
                    while (rs.next()){
                        Client client = new Client();
                        clientFromRs(client,rs);
                        list.add(client);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Client getById(int id) {
        Client client = new Client();

        try {
            try (Statement statement = connection.createStatement()){
                try(ResultSet rs = statement.executeQuery("SELECT * FROM Clients WHERE ID = " + id)) {
                    while (rs.next()) {
                        clientFromRs(client, rs);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void deleteClient(int id) {
        try {
            try(Statement statement = connection.createStatement()){
                statement.execute("DELETE FROM CLients WHERE id = " + id);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void clientFromRs(Client client, ResultSet rs) throws SQLException {
        client.setId(Integer.parseInt(rs.getString(1)));
        client.setName(rs.getString(2));
        client.setEmail(rs.getString(3));
        client.setAddress(rs.getString(4));
    }
}