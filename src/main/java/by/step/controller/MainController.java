package by.step.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;


public class MainController {

    @Getter
    @Setter
    private AcademicController academicController;
    private Scanner scanner;


    public MainController(AcademicController academicController) {
        this.academicController = academicController;
        this.scanner = new Scanner(System.in);
    }

    public void init() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Академическая система");
            System.out.println("0. Выход");
            System.out.print("Выберите опцию: ");

            String choice = scanner.next().trim();

            switch (choice) {
                case "1" -> academicController.start();
                case "2","0" -> {
                    running = false;
                    System.out.println("Выход из программы...");
                }
                default -> System.out.println("Такой опции нет");
            }
        }

        scanner.close();
    }
}
