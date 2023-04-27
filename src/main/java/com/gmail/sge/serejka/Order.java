package com.gmail.sge.serejka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Order {
    private int id;
    private int clientId;
    private Map<Integer,Integer> orderMap;
    private Gson gson;
    private ClientDAOImpl clientDAO;
    private ProductDAOImpl productDAO;

    public Order(){
        super();
        orderMap = new HashMap<>();
        gson = new GsonBuilder().create();
    }

    public Order(ClientDAOImpl clientDAO, ProductDAOImpl productDAO){
        this.clientDAO = clientDAO;
        this.productDAO = productDAO;
        orderMap = new HashMap<>();
        gson = new GsonBuilder().create();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    public void addToOrder(Product product){
        if (orderMap.containsKey(product.getId())){
            int temp = orderMap.get(product.getId());
            orderMap.put(product.getId(),temp + 1);
        } else {
            orderMap.put(product.getId(),1);
        }
    }

    public String mapToJson(){
        return gson.toJson(orderMap, HashMap.class);
    }

    public void setMapFromJson(String json){
        this.orderMap = gson.fromJson(json,HashMap.class);
    }

    public String productsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        Set<Integer> keySet = orderMap.keySet();
        for (Integer key : keySet){
            stringBuilder.append(productDAO.getById(key)).append(" amount ").
                    append(orderMap.get(key)).append(", ");
        }
        return stringBuilder.toString();
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientName=" + clientDAO.getById(clientId).getName() +
                ", order=" + productsToString() +
                '}';
    }
}