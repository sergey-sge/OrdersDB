package com.gmail.sge.serejka;

import java.util.List;

public interface OrderDAO {
    void createTable();
    void addOrder(Order order);
    void deleteOrder(int id);
    List<Order> getAll();
    Order getById(int id);
}
