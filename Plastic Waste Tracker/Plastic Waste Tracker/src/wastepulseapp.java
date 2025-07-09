import java.io.IOException;
import java.util.*;

public class wastepulseapp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        dataprocessor processor = new dataprocessor();

        System.out.println("=====================================");
        System.out.println(" Welcome to WastePulse Calculator 🌿");
        System.out.println(" Our app helps calculate your saved:");
        System.out.println(" ▲ CO₂ emissions\n ▲ Energy\n ▲ Landfill Space\n ▲ Gasoline");
        System.out.println("=====================================\n");

        while (true) {
            System.out.println("Type '1' to begin or '0' to quit:");
            String input = scanner.nextLine();
            if (input.equals("0")) break;

            while (true) {
                System.out.println("\nMAIN MENU:");
                System.out.println("1. Add new waste records");
                System.out.println("2. View waste records");
                System.out.println("3. Calculate amount of materials saved");
                System.out.println("4. Save & Exit");
                System.out.print("Enter your choice (1-4): ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                        while (true) {
                            System.out.println("\nWASTE INPUT MENU:");
                            System.out.println("1. Add record manually");
                            System.out.println("2. Load from CSV file");
                            System.out.println("3. Return to Main Menu");
                            System.out.print("Enter your choice (1-3): ");
                            String subChoice = scanner.nextLine();

                            if (subChoice.equals("1")) {
                                try {
                                    System.out.print("Enter country name: ");
                                    String country = scanner.nextLine();

                                    String date;
                                    while (true) {
                                        System.out.print("Enter date (YYYY-MM-DD): ");
                                        date = scanner.nextLine();
                                        if (dataprocessor.isValidDate(date.trim())) break;
                                        System.out.println("Invalid date. Try again.");
                                    }

                                    System.out.print("Enter weight in kilograms: ");
                                    double weight = Double.parseDouble(scanner.nextLine());

                                    wasterecord record = new wasterecord(country.trim(), date.trim(), weight);
                                    processor.addRecord(record);
                                    System.out.println("✅ Record added successfully.");
                                }
                                catch (NumberFormatException e) {
                                    System.out.println("❌ Invalid weight. Please enter a number.");
                                }

                            }
                            else if (subChoice.equals("2")) {
                                System.out.print("Enter full path to your CSV file: ");
                                String filePath = scanner.nextLine();
                                try {
                                    processor.loadCSV(filePath);
                                    System.out.println("✅ CSV data loaded successfully!");
                                }
                                catch (IOException e) {
                                    System.out.println("❌ Error: " + e.getMessage());
                                }
                            }
                            else if (subChoice.equals("3")) {
                                break;
                            }
                        }

                    }
                    else if (choice.equals("2")) {
                        while (true) {
                            System.out.println("\nVIEW RECORDS MENU:");
                            System.out.println("1. View all waste records");
                            System.out.println("2. Search by country");
                            System.out.println("3. Return to Main Menu");
                            System.out.print("Enter your choice (1-3): ");
                            String viewChoice = scanner.nextLine();

                            if (viewChoice.equals("1")) {
                                processor.displayAll();
                            }
                            else if (viewChoice.equals("2")) {
                                System.out.print("Enter country name to search: ");
                                String country = scanner.nextLine();
                                processor.searchByCountry(country.trim());
                            }
                            else if (viewChoice.equals("3")) {
                                break;
                            }
                        }

                    } else if (choice.equals("3")) {
                        while (true) {
                            System.out.println("\nCALCULATION MENU:");
                            System.out.println("1. Show total (all countries)");
                            System.out.println("2. Show one country");
                            System.out.println("3. Return to Main Menu");
                            System.out.print("Enter your choice (1-3): ");
                            String calcChoice = scanner.nextLine();

                            if (calcChoice.equals("1")) {
                                Map<String, Double> countryTotals = processor.getTotalPlasticByCountry();
                                if (countryTotals.isEmpty()) {
                                    System.out.println("⚠️ No records available.");
                                }
                                else {
                                    double grandTotal = 0.0;
                                    for (Map.Entry<String, Double> entry : countryTotals.entrySet()) {
                                        MaterialSavingsCalculator calc = new MaterialSavingsCalculator(entry.getValue());
                                        System.out.println(calc.getSummaryLine(entry.getKey()));
                                        System.out.println(calc.toString());
                                        grandTotal += entry.getValue();
                                    }
                                    MaterialSavingsCalculator totalCalc = new MaterialSavingsCalculator(grandTotal);
                                    System.out.println("=== TOTAL (All Countries) ===");
                                    System.out.println(totalCalc.getSummaryLine("All countries"));
                                    System.out.println(totalCalc.toString());
                                }

                            }
                            else if (calcChoice.equals("2")) {
                                System.out.print("Enter country name: ");
                                String country = scanner.nextLine();
                                double total = 0.0;
                                for (wasterecord rec : processor.getRecords()) {
                                    if (rec.getCountry().equalsIgnoreCase(country.trim())) {
                                        total += rec.getWeight();
                                    }
                                }
                                if (total == 0.0) {
                                    System.out.println("⚠️ No data found for " + country);
                                } else {
                                    MaterialSavingsCalculator calc = new MaterialSavingsCalculator(total);
                                    System.out.println(calc.getSummaryLine(country));
                                    System.out.println(calc.toString());
                                }

                            }
                            else if (calcChoice.equals("3")) {
                                break;
                            }
                        }

                    }
                    else if (choice.equals("4")) {
                        try {
                            processor.saveToFile();
                            System.out.println("✅ Data saved. Goodbye!");
                            return;
                        }
                        catch (IOException e) {
                            System.out.println("❌ Failed to save: " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println("❌ Invalid choice.");
                    }
            }
        }
    }
}
