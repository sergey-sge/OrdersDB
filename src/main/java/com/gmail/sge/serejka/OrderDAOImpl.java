package com.gmail.sge.serejka;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Orders");
                statement.execute("CREATE TABLE Orders (id int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "clientId int REFERENCES Clients (id), orderJSON json)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOrder(Order order) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO Orders (clientId, orderJson) VALUES (" +
                        order.getClientId() + ", '" + order.mapToJson() + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int id) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM Orders WHERE id = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Orders")) {
                    while (rs.next()) {
                        Order order = new Order();
                        orderFromRs(order, rs);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getById(int id) {
        Order order = new Order();
        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Orders WHERE id = " + id)) {
                    while (rs.next()) {
                        orderFromRs(order, rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    private void orderFromRs(Order order, ResultSet rs) throws SQLException {
        order.setId(rs.getInt(1));
        order.setClientId(rs.getInt(2));
        order.setMapFromJson(rs.getString(3));
    }
}