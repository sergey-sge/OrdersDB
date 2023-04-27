package com.gmail.sge.serejka;

import java.util.List;

public interface ProductDAO {
    void createTable();
    void addProduct(Product product);
    List<Product> getAll();
    Product getById(int id);
    void changePrice(Product product, int newPrice);
    void deleteProduct(int id);

}
