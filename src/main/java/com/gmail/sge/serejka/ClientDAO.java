package com.gmail.sge.serejka;

import java.util.List;

public interface ClientDAO {
    void createTable();
    void addClient(Client client);
    List<Client> getAll();
    Client getById(int id);
    void deleteClient(int id);
}
