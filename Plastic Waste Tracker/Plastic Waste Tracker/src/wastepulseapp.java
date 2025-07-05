import javax.swing.*;
import java.io.IOException;

public class wastepulseapp {

    public static void main(String[] args) {
        dataprocessor processor = new dataprocessor();

        while (true) {
            String[] home = {
                    "Start",
                    "Close App"
            };

            String choice1 = (String) JOptionPane.showInputDialog(
                    null,
                    "Welcome! Our app helps you calculate the percentage of Carbon Footprint you've saved based on how much plastic you've recycled",
                    "WastePulse Recycling Calculator",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    home,
                    home[0]);

            if (choice1 == null || choice1.equals("Close App")) {
                return;
            }

            String[] menu = {
                    "Add new waste record manually",
                    "View all waste records",
                    "Search records by country",
                    "Load CSV data from file (user-provided)",
                    "Calculate carbon footprint saved",
                    "Save & Exit"
            };

            while (true) {
                String choice2 = (String) JOptionPane.showInputDialog(
                        null,
                        "Select an option:",
                        "WastePulse Recycling Tracker",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        menu,
                        menu[0]);

                if (choice2 == null) break;

                if (choice2.equals("Add new waste record manually")) {
                    try {
                        String country = JOptionPane.showInputDialog("Enter country name:");
                        if (country == null) continue;

                        String date;
                        while (true) {
                            date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
                            if (date == null) break;
                            if (dataprocessor.isValidDate(date.trim())) {
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid date. Please enter a valid date between 1900-01-01 and 2025-12-31.");
                            }
                        }

                        String weightInput = JOptionPane.showInputDialog("Enter weight in kilograms (e.g., 3.5):");
                        if (weightInput == null) continue;
                        double weight = Double.parseDouble(weightInput.trim());

                        wasterecord record = new wasterecord(country.trim(), date.trim(), weight);
                        processor.addRecord(record);
                        JOptionPane.showMessageDialog(null, "Record added successfully.");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid weight input. Please enter a number.");
                    }

                } else if (choice2.equals("View all waste records")) {
                    processor.displayAll();

                } else if (choice2.equals("Search records by country")) {
                    String searchCountry = JOptionPane.showInputDialog("Enter country name to search:");
                    if (searchCountry != null) {
                        processor.searchByCountry(searchCountry.trim());
                    }

                } else if (choice2.equals("Load CSV data from file (user-provided)")) {
                    String filePath = JOptionPane.showInputDialog("Enter full path to your CSV file:");
                    if (filePath != null) {
                        try {
                            processor.loadCSV(filePath);
                            JOptionPane.showMessageDialog(null, "CSV data loaded successfully!");
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
                        }
                    }

                } else if (choice2.equals("Calculate carbon footprint saved")) {
                    JOptionPane.showMessageDialog(null, "Function is not available yet!");

                } else if (choice2.equals("Save & Exit")) {
                    try {
                        processor.saveToFile();
                        JOptionPane.showMessageDialog(null, "Data saved to waste_data.txt. Goodbye!");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
                    }
                    return;

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
                }
            }
        }
    }
}
