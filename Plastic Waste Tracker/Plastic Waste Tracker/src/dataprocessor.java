import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dataprocessor {
    private List<wasterecord> records = new ArrayList<>();

    // Load data from CSV file (country,year,plastic_waste)
    public void loadCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                String country = parts[0].trim();
                String year = parts[1].trim();
                String weightStr = parts[2].trim();

                if (year.isEmpty() || weightStr.isEmpty()) continue;

                try {
                    double weight = Double.parseDouble(weightStr);
                    String date = year + "-01-01";
                    records.add(new wasterecord(country, date, weight));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Skipping invalid line: " + line);
                }
            }
        }
    }

    // Save all records to a local text file
    public void saveToFile() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter("waste_data.txt"))) {
            for (wasterecord r : records) {
                pw.println(r.getCountry() + "," + r.getDate() + "," + r.getWeight());
            }
        }
    }

    public void addRecord(wasterecord record) {
        records.add(record);
    }

    public void displayAll() {
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No records to display.");
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append(String.format("%-22s | %-10s | %10s%n", "Country", "Date", "Weight"));
        message.append("--------------------------------------------------------\n");

        for (wasterecord r : records) {
            message.append(String.format("%-22s | %-10s | %10.2f%n", r.getCountry(), r.getDate(), r.getWeight()));

        }
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Search Result",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Search records by country (case-insensitive)
    public void searchByCountry(String keyword) {
        boolean found = false;

        StringBuilder message = new StringBuilder();
        message.append(String.format("%-22s | %-15s | %15s%n", "Country", "Date", "Weight"));
        message.append("--------------------------------------------------------\n");


        for (wasterecord r : records) {
            if (r.getCountry().toLowerCase().contains(keyword.toLowerCase())) {

                message.append(String.format("%-22s | %-10s | %10.2f%n", r.getCountry(), r.getDate(), r.getWeight()));
                found = true;
            }
        }

        if (!found) {
            message.append(String.format("Invalid input. Please enter a valid country name. "));
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    //Static utility method for validating date format and range
    public static boolean isValidDate(String dateStr) {
        try {
            String[] parts = dateStr.split("-");
            if (parts.length != 3) return false;

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            if (year < 1900 || year > 2025) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<wasterecord> getRecords() {
        return records;
    }
    public Map<String, Double> getTotalPlasticByCountry() {
        Map<String, Double> countryTotals = new HashMap<>();
        for (wasterecord rec : records) {
            countryTotals.put(rec.getCountry(),
                    countryTotals.getOrDefault(rec.getCountry(), 0.0) + rec.getWeight());
        }
        return countryTotals;
    }
}


