import java.io.IOException;
import java.util.Scanner;

public class wastepulseapp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        dataprocessor processor = new dataprocessor();

        while (true) {
            System.out.println("\n=== WastePulse Recycling Tracker ===");
            System.out.println("1.\tAdd new waste record manually");
            System.out.println("2.\tView all records");
            System.out.println("3.\tSearch records by country");
            System.out.println("4.\tLoad CSV data from file (user-provided)");
            System.out.println("5.\tSave & Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter country name: ");
                        String country = scanner.nextLine().trim();

                        String date;
                        while (true) {
                            System.out.print("Enter date (YYYY-MM-DD): ");
                            date = scanner.nextLine().trim();
                            if (dataprocessor.isValidDate(date)) {
                                break;
                            } else {
                                System.out.println("Invalid date. Please enter a valid date between 1900-01-01 and 2025-12-31.");
                            }
                        }

                        System.out.print("Enter weight in kilograms (e.g., 3.5): ");
                        double weight = Double.parseDouble(scanner.nextLine().trim());

                        wasterecord record = new wasterecord(country, date, weight);
                        processor.addRecord(record);
                        System.out.println("Record added successfully.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid weight input. Please enter a number.");
                    }
                    break;

                case "2":
                    processor.displayAll();
                    break;

                case "3":
                    System.out.print("Enter country name to search: ");
                    String searchCountry = scanner.nextLine().trim();
                    processor.searchByCountry(searchCountry);
                    break;

                case "4":
                    System.out.print("Enter full path to your CSV file: ");
                    String filePath = scanner.nextLine();
                    try {
                        processor.loadCSV(filePath);
                        System.out.println("CSV data loaded successfully!");
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;

                case "5":
                    try {
                        processor.saveToFile();
                        System.out.println("Data saved to waste_data.txt. Goodbye!");
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }
}
