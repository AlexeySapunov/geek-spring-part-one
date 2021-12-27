package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ProductService productService = context.getBean("productService", ProductService.class);
        CartService cartService = context.getBean("cartService", CartService.class);

        productService.init();

        System.out.println("Product count: " + productService.count());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String choice;

            label:
            do {
                System.out.println("Welcome to our shop!\n" +
                        "Enter ADD, if you want to add new product to cart\n" +
                        "Enter DEL, if you want to delete product from cart\n" +
                        "Enter I AM DONE, if you want to exit");

                choice = reader.readLine();
                long id;

                switch (choice) {
                    case "ADD":
                        System.out.println("Enter id number (from 1 to 5 inclusive): ");
                        id = Long.parseLong(reader.readLine());
                        cartService.addProduct(id);
                        System.out.println("You add " + productService.nameProduct(id));
                        break;
                    case "DEL":
                        System.out.println("Enter id number (from 1 to 5 inclusive): ");
                        id = Long.parseLong(reader.readLine());
                        cartService.delProduct(id);
                        System.out.println("You delete " + productService.nameProduct(id));
                        break;
                    case "I AM DONE":
                        break label;
                    default:
                        System.out.println("Incorrect choice!");
                        reader.close();
                        break;
                }

                System.out.println("In your cart " + cartService.count() + " products");

            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
