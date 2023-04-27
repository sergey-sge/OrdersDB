package com.gmail.sge.serejka;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Connection connection = ConnectionFactory.getConnection();
            ClientDAOImpl clientDAO = new ClientDAOImpl(connection);
            ProductDAOImpl productDAO = new ProductDAOImpl(connection);
            OrderDAOImpl orderDAO = new OrderDAOImpl(connection);
            clientDAO.createTable();
            productDAO.createTable();
            orderDAO.createTable();
            try {
                while (true) {
                    System.out.println("Choose one of the following options: ");
                    System.out.println("1 -- work with clients");
                    System.out.println("2 -- work with products");
                    System.out.println("3 -- work with orders");
                    System.out.println("0 -- exit");
                    String s = scanner.nextLine();
                    switch (s) {
                        case "0":
                            System.exit(0);
                        case "1":
                            workWithClients(clientDAO);
                            break;
                        case "2":
                            workWithProducts(productDAO);
                            break;
                        case "3":
                            workWithOrders(productDAO, orderDAO, clientDAO);
                            break;
                        default:
                            System.out.println("Input the correct number");
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void workWithClients(ClientDAOImpl clientDAO) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 -- add new client");
            System.out.println("2 -- get all clients");
            System.out.println("3 -- get client by id");
            System.out.println("4 -- delete client");
            System.out.println("0 -- exit to main menu");
            String s = scanner.nextLine();
            try {
                int id;
                switch (s) {
                    case "1":
                        clientDAO.addClient(newClient());
                        System.out.println("The client was added to database");
                        break;
                    case "2":
                        System.out.println(clientDAO.getAll());
                        break;
                    case "3":
                        System.out.println("Input the client's ID");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println(clientDAO.getById(id));
                        break;
                    case "4":
                        System.out.println("Input the client's ID");
                        id = Integer.parseInt(scanner.nextLine());
                        clientDAO.deleteClient(id);
                        System.out.println("Client was deleted");
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Input the correct number");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static void workWithProducts(ProductDAOImpl productDAO) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 -- add new product");
            System.out.println("2 -- get all products");
            System.out.println("3 -- get product by id");
            System.out.println("4 -- delete product");
            System.out.println("5 -- change price for a product");
            System.out.println("0 -- exit to main menu");
            String s = scanner.nextLine();
            try {
                int id;
                switch (s) {
                    case "1":
                        productDAO.addProduct(newProduct());
                        System.out.println("The product was added to database");
                        break;
                    case "2":
                        System.out.println(productDAO.getAll());
                        break;
                    case "3":
                        System.out.println("Input the product's ID");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println(productDAO.getById(id));
                        break;
                    case "4":
                        System.out.println("Input the product's ID");
                        id = Integer.parseInt(scanner.nextLine());
                        productDAO.deleteProduct(id);
                        System.out.println("Product was deleted");
                        break;
                    case "5":
                        System.out.println("Input the product's ID");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Input the new price");
                        int price = Integer.parseInt(scanner.nextLine());
                        productDAO.changePrice(productDAO.getById(id), price);
                        System.out.println("The price have been updated");
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Input the correct number");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static void workWithOrders(ProductDAOImpl productDAO, OrderDAOImpl orderDAO, ClientDAOImpl clientDAO) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 -- make a new order");
            System.out.println("2 -- delete order");
            System.out.println("3 -- get all orders");
            System.out.println("4 -- get order by id");
            System.out.println("0 -- exit to main menu");
            String s = scanner.nextLine();
            try {
                switch (s) {
                    case "0":
                        return;
                    case "1":
                        newOrder(productDAO, orderDAO,clientDAO);
                        break;
                    case "2":
                        System.out.println("Input the order's ID");
                        orderDAO.deleteOrder(Integer.parseInt(scanner.nextLine()));
                        break;
                    case "3":
                        System.out.println(orderDAO.getAll());
                        break;
                    case "4":
                        System.out.println("Input the order's ID");
                        System.out.println(orderDAO.getById(Integer.parseInt(scanner.nextLine())));
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static Client newClient() {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        System.out.println("Input the name");
        client.setName(scanner.nextLine());
        System.out.println("Input the email");
        client.setEmail(scanner.nextLine());
        System.out.println("Input the address");
        client.setAddress(scanner.nextLine());
        return client;
    }

    private static Product newProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        System.out.println("Input the name");
        product.setName(scanner.nextLine());
        System.out.println("Input the description");
        product.setDescription(scanner.nextLine());
        System.out.println("Input the price");
        try {
            product.setPrice(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return product;
    }

    private static void newOrder(ProductDAOImpl productDAO, OrderDAOImpl orderDAO, ClientDAOImpl clientDAO) {
        Order order = new Order(clientDAO, productDAO);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the client's ID");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine());
            order.setClientId(id);
            System.out.println("Here are the products");
            System.out.println(productDAO.getAll());
            while (true) {
                System.out.println("Input the ID of product to add or 0 to exit");
                int productId = Integer.parseInt(scanner.nextLine());
                if (productId != 0) {
                    order.addToOrder(productDAO.getById(productId));
                    System.out.println("Product was added to the order");
                }
                if (productId == 0) {
                    System.out.println("Order complete");
                    System.out.println(order);
                    orderDAO.addOrder(order);
                    break;
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}