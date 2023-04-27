package com.gmail.sge.serejka;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Products");
                statement.execute("CREATE TABLE Products (id int AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(20) NOT NULL, price int NOT NULL, description VARCHAR(50));");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO Products (name, price, description) VALUES (" +
                        "'" + product.getName() + "', " + product.getPrice() + ", '" +
                        product.getDescription() + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Products")) {
                    while (rs.next()) {
                        Product product = new Product();
                        productFromRs(product, rs);
                        list.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product getById(int id) {
        Product product = new Product();
        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Products WHERE id = " + id)){
                   while (rs.next()){
                       productFromRs(product,rs);
                   }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void changePrice(Product product, int newPrice) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE Products Set price = " + newPrice +
                        " WHERE id = " + product.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM Products WHERE id = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void productFromRs(Product product, ResultSet rs) throws SQLException {
        product.setId(rs.getInt(1));
        product.setName(rs.getString(2));
        product.setPrice(rs.getInt(3));
        product.setDescription(rs.getString(4));
    }
}