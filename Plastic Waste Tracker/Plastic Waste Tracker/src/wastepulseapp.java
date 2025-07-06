import javax.swing.*;
import java.io.IOException;
import java.util.Map;

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
                    "Welcome! Our app helps you calculate the amount of materials you've saved based on how much plastic you've recycled",
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
                    "Calculate amount of materials saved",
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

                }   else if (choice2.equals("Calculate amount of materials saved")) {
                String[] calcOptions = { "Show total (all countries)", "Show one country", "Cancel" };
                String selection = (String) JOptionPane.showInputDialog(
                        null,
                        "Choose what to display:",
                        "Material Savings Options",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        calcOptions,
                        calcOptions[0]);

                if (selection == null || selection.equals("Cancel")) {
                    continue;
                }

                if (selection.equals("Show total (all countries)")) {
                    Map<String, Double> countryTotals = processor.getTotalPlasticByCountry();

                    if (countryTotals.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No records available to calculate savings.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        double grandTotal = 0.0;

                        for (Map.Entry<String, Double> entry : countryTotals.entrySet()) {
                            String country = entry.getKey();
                            double weight = entry.getValue();
                            grandTotal += weight;

                            MaterialSavingsCalculator calc = new MaterialSavingsCalculator(weight);
                            sb.append(calc.getSummaryLine(country)).append("\n");
                            sb.append(calc.toString()).append("\n\n");
                        }

                        // Show total savings
                        MaterialSavingsCalculator totalCalc =
                                new MaterialSavingsCalculator(grandTotal);
                        sb.append("=== TOTAL (All Countries) ===\n");
                        sb.append(totalCalc.getSummaryLine("All countries")).append("\n");
                        sb.append(totalCalc.toString());

                        JTextArea textArea = new JTextArea(sb.toString());
                        textArea.setEditable(false);
                        textArea.setCaretPosition(0); // scroll to top
                        textArea.setLineWrap(true);
                        textArea.setWrapStyleWord(true);

                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));

                        JOptionPane.showMessageDialog(null, scrollPane, "Material Savings Summary", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else if (selection.equals("Show one country")) {
                    String countryName = JOptionPane.showInputDialog("Enter country name to display:");
                    if (countryName == null || countryName.trim().isEmpty()) continue;

                    double totalForCountry = 0.0;
                    for (wasterecord rec : processor.getRecords()) {
                        if (rec.getCountry().equalsIgnoreCase(countryName.trim())) {
                            totalForCountry += rec.getWeight();
                        }
                    }

                    if (totalForCountry == 0.0) {
                        JOptionPane.showMessageDialog(null, "No data found for country: " + countryName);
                    } else {
                        MaterialSavingsCalculator calc = new MaterialSavingsCalculator(totalForCountry);
                        String result = calc.getSummaryLine(countryName) + "\n\n" + calc.toString();
                        JOptionPane.showMessageDialog(null, result);
                    }
                }
            }




            else if (choice2.equals("Save & Exit")) {
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
