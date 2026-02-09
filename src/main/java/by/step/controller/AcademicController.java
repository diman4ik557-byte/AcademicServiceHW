package by.step.controller;

import by.step.model.Grade;
import by.step.model.Student;
import by.step.model.Subject;
import by.step.service.AcademicService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AcademicController {

    private final AcademicService academicService;
    private Scanner scanner;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
        this.scanner = new Scanner(System.in);
    }

    public void init() {
        this.scanner = new Scanner(System.in);

    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> getAverageScore();
                case 2 -> getTopStudents();
                case 3 -> getSubjectAverage();
                case 4 -> addStudent();
                case 5 -> removeStudent();
                case 6 -> addGrade();
                case 7 -> removeGrade();
                case 0 -> {
                    running = false;
                    System.out.println("Выход из программы");
                }
                default -> System.out.println("Такой опции нет");
            }
        }
        if ( scanner != null ) {
            scanner.close();
        }
    }

    private void printMenu() {
        System.out.println("\n=== Academic Controller ===");
        System.out.println("1. Средний балл студента");
        System.out.println("2. Топ студентов");
        System.out.println("3. Средний балл по предмету");
        System.out.println("4. Добавить студента");
        System.out.println("5. Удалить студента");
        System.out.println("6. Добавить оценку");
        System.out.println("7. Удалить оценку");
        System.out.println("0. Выход");
    }

    private void getAverageScore() {
        System.out.println("--- Средний балл студента ---");

        System.out.println("Выберите способ поиска: " +
                "\n1. По имени \n2.По id");
        int choice = scanner.nextInt();

        switch (choice){
            case 1 -> {
                System.out.println("Введите имя студента: ");
                String studentName = scanner.next();

                try {
                    double average = academicService.getAverageScore(studentName);
                    System.out.println("Средний балл студента "
                            + studentName + " | " + average);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }

            case 2 ->{
                System.out.println("Введите id студента: ");
                int studentId = scanner.nextInt();

                try {
                    double average = academicService.getAverageScoreByStId(studentId);
                    System.out.println("Средний балл студента "
                            +academicService.findStudentById(studentId).getName()
                            + " | " + average);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }

            default -> System.out.println("Такой опции нет");
        }

    }

    private void getTopStudents() {
        System.out.println("--- Топ студентов ---");
        System.out.println("Введите лимит топа: ");
        int limit = scanner.nextInt();

        List<Student> topStudents = academicService.getTopStudents(limit);
        System.out.println("\nТоп " + limit + " студентов: ");
        for (int i = 0; i < topStudents.size(); i++) {
            Student student = topStudents.get(i);
            double average = academicService.getAverageScore(student.getName());
            System.out.printf(i + 1 + " | " + student.getName()
                    + " [" + student.getId() + "] - средний балл: " + average +"\n");
        }
    }

    private void getSubjectAverage() {
        System.out.println("--- Средний балл по предмету ---");
        System.out.println("Предметы: ");
        for (Subject subject : Subject.values()) {
            System.out.println("- " + subject.name());
        }

        System.out.println("Введите название предмета: ");
        String subjectName = scanner.next();
        try {
            Subject subject = Subject.valueOf(subjectName.toUpperCase());
            double average = academicService.getAverageBySubject(subject);
            System.out.println("Средний балл по предмету " + subjectName
                    + ": " + average);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверное название предмета");
        }
    }

    private void addStudent() {
        System.out.println("--- Добавление студента ---");

        System.out.println("Введите имя студента: ");
        String name = scanner.nextLine();
        String sc = scanner.nextLine();
        System.out.println("Введите email студента: ");
        String email = scanner.next();

        Student student = Student.builder()
                .name(name)
                .email(email)
                .build();
        try {
            academicService.addStudent(student);
            System.out.println("Студент добавлен");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeStudent() {
        System.out.println("--- Удаление студента ---");
        System.out.println("Введите ID студента для удаления: ");
        int id = scanner.nextInt();

        try {
            academicService.removeStudent(id);
            System.out.println("Студент удален");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addGrade() {
        System.out.println("--- Добавление оценки ---");

        List<Student> students = academicService.getAllStudents();
        if ( students.isEmpty() ) {
            System.out.println("Студенты не найдены");
            return;
        }

        System.out.println("Список студентов: ");
        for (Student student : students) {
            System.out.println("[" + student.getId() + "] " + student.getName());
        }

        System.out.println("Введите id студента для добавления оценки: ");
        int studentId = scanner.nextInt();

        Student student = academicService.findStudentById(studentId);
        if ( student == null ) {
            System.out.println("Студент не найден");
            return;
        }

        System.out.println("Студент : [" + student.getId() + "] "
                + student.getName() + " | " + student.getEmail());
        System.out.println("Доступные предметы: ");
        for (Subject subject : Subject.values()) {
            System.out.println("- " + subject.name());
        }

        System.out.println("\n Выберите предмет: ");
        String subjectName = scanner.next();

        try {
            Subject subject = Subject.valueOf(subjectName.toUpperCase());
            System.out.println("Введите оценку(0-10): ");
            int score = scanner.nextInt();
            Grade grade = Grade.builder()
                    .studentId(studentId)
                    .score(score)
                    .subject(subject)
                    .build();

            academicService.addGrade(grade);
            System.out.println("Оценка добавлена");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверное название предмета");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeGrade() {
        System.out.println("\n--- Удаление оценки ---");


        List<Student> students = academicService.getAllStudents();
        if ( students.isEmpty() ) {
            System.out.println("Студенты не найдены");
            return;
        }

        System.out.println("Список студентов: ");
        for (Student student : students) {
            System.out.println("[" + student.getId() + "] " + student.getName());
        }

        System.out.println("Введите id студента для удаления оценки: ");
        int studentId = scanner.nextInt();

        try {
            Student student = academicService.findStudentById(studentId);
            if ( student == null ) {
                System.out.println("Студент не найден");
                return;
            }

            List<Grade> grades = academicService.getGradesByStudentId(studentId);
            if ( grades.isEmpty() ) {
                System.out.println("У студента нет оценок");
                return;
            }

            System.out.println("Студент : [" + student.getId() + "] "
                    + student.getName() + " | " + student.getEmail());
            System.out.println("Оценки студента: ");

            for (int i = 0; i < grades.size(); i++) {
                Grade grade = grades.get(i);
                System.out.println(i + "[" + grade.getSubject().name() + "] - "
                        + grade.getScore());
            }

            System.out.println("\n Введите номер оценки для удаления(0 - "
                    + (grades.size() - 1) + "):");
            int gradeInput = scanner.nextInt();

            if (gradeInput >= 0 && gradeInput < grades.size()) {
                academicService.removeGrade(gradeInput);
                System.out.println("Оценка успешно удалена!");
            } else {
                System.out.println("Неверный номер оценки");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
