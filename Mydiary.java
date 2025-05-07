/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mydiary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Mydiary {
    private static final String FILE_NAME = "C:/diary/diary.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    viewAllEntries();
                    break;
                case "3":
                    viewEntryById();
                    break;
                case "4":
                    System.out.println("Exiting Diary Application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Diary Application ---");
        System.out.println("1. Add New Diary Entry");
        System.out.println("2. View All Entries");
        System.out.println("3. View Entry by ID");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addEntry() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Enter entry ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();

            System.out.println("Enter diary content (type 'END' on a new line to finish):");
            StringBuilder content = new StringBuilder();
            while (true) {
                String line = scanner.nextLine();
                if (line.trim().equalsIgnoreCase("END")) {
                    break;
                }
                content.append(line).append("\n");
            }

            writer.write(id + "\n");
            writer.write(date + "\n");
            writer.write(content.toString());
            writer.write("===\n");
            System.out.println("Entry added successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred while saving the diary entry.");
        }
    }

    private static void viewAllEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            StringBuilder entry = new StringBuilder();
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("===")) {
                    System.out.println("\n--- Diary Entry ---");
                    System.out.print(entry.toString());
                    entry.setLength(0);
                    found = true;
                } else {
                    entry.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("No diary entries found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No diary file found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the diary file.");
        }
    }

    private static void viewEntryById() {
        System.out.print("Enter ID to search: ");
        String searchId = scanner.nextLine().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            StringBuilder entry = new StringBuilder();
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("===")) {
                    String[] lines = entry.toString().split("\n", 3);
                    if (lines.length > 0 && lines[0].equals(searchId)) {
                        System.out.println("\n--- Diary Entry Found ---");
                        System.out.print(entry.toString());
                        found = true;
                        break;
                    }
                    entry.setLength(0); // reset
                } else {
                    entry.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("Entry with ID " + searchId + " not found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No diary file found.");
        } catch (IOException e) {
            System.out.println("An error occurred while searching the diary file.");
        }
    }

    }

