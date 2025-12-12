package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        try (Scanner myScanner = new Scanner(System.in)) {
            boolean isDone = false;

            while (!isDone) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("  1) Display all products");
                System.out.println("  2) Display all customers");
                System.out.println("  3) Display all categories");
                System.out.println("  0) Exit");
                System.out.println("Select an option:");
                String userInput = myScanner.nextLine().trim();

                int userChoice = Integer.parseInt(userInput);

                switch (userChoice) {
                    case 1:
                        displayAllProducts(username, password);
                        break;
                    case 2:
                        displayAllCustomers(username, password);
                        break;
                    case 3:
                        displayAllCategories(username, password);
                        System.out.println("Choose a categoryID to display products");
                        String userInputCategoryID = myScanner.nextLine().trim();

                        displayCategoryProducts(username, password, userInputCategoryID);
                    case 0:
                        isDone = true;
                        break;
                    default:
                        System.out.println(" Choice not valid");
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error onv inputs!");
            System.out.println(ex.getMessage());
        }
    }

    public static void displayCategoryProducts(String username, String password, String categoryID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = """
                    SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                    FROM products
                    WHERE CategoryID = ?
                    """;

            try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/northwind",
                        username,
                        password
            ); PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setString(1, categoryID);

                try (ResultSet results = preparedStatement.executeQuery()) {
                    while (results.next()) {
                        int productID = results.getInt(1);
                        String productName = results.getString(2);
                        double productPrice = results.getDouble(3);
                        int productStock = results.getInt(4);

                        System.out.println("Product ID: " + productID);
                        System.out.println("Name: " + productName);
                        System.out.println("Price: " + productPrice);
                        System.out.println("Stock: " + productStock);
                        System.out.println("--------------------------");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
            System.out.println(ex.getMessage());
        }
    }
    public static void displayAllCategories(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = """
                        SELECT CategoryID, CategoryName
                        FROM categories
                        """;

            try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/northwind",
                        username,
                        password
            );

                 PreparedStatement preparedStatement = connection.prepareStatement(query);

                 ResultSet results = preparedStatement.executeQuery()
            ) {
                while (results.next()) {
                        int categoryID = results.getInt(1);
                        String categoryName = results.getString(2);

                        System.out.println("Category ID" + categoryID);
                        System.out.println("Category Name" + categoryName);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
            System.out.println(ex.getMessage());
        }
    }
    public static void displayAllProducts(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = """
                    SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                    FROM products
                    """;
            try (Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/northwind",
                                username,
                                password
            );
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet results = preparedStatement.executeQuery()
            ) {
                while (results.next()) {
                    int productID = results.getInt(1);
                    String productName = results.getString(2);
                    double productPrice = results.getDouble(3);
                    int productStock = results.getInt(4);

                    System.out.println("Product ID: " + productID);
                    System.out.println("Name: " + productName);
                    System.out.println("Price: " + productPrice);
                    System.out.println("Stock: " + productStock);
                    System.out.println("--------------------------");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
            System.out.println(ex.getMessage());
        }
    }
    public static void displayAllCustomers(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = """
                    SELECT ContactName, CompanyName, City, Country, Phone
                    FROM customers
                    ORDER BY Country
                    """;

            try (Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/northwind",
                                username,
                                password
            );

                 PreparedStatement preparedStatement = connection.prepareStatement(query);

                 ResultSet results = preparedStatement.executeQuery()
            ) {
                while (results.next()) {
                    String contactName = results.getString(1);
                    String companyName = results.getString(2);
                    String city = results.getString(3);
                    String country = results.getString(4);
                    String phone = results.getString(5);

                    System.out.println("Contact Name: " + contactName);
                    System.out.println("Company Name: " + companyName);
                    System.out.println("City: " + city);
                    System.out.println("Country: " + country);
                    System.out.println("Phone: " + phone);
                    System.out.println("--------------------------");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
            System.out.println(ex.getMessage());
        }

    }
}
