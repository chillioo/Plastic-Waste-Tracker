public class wasterecord {
    private String country;
    private String date;       // YYYY-MM-DD
    private double weight;     // kg

    public wasterecord(String country, String date, double weight) {
        this.country = country;
        this.date = date;
        this.weight = weight;
    }

    public String getCountry() { return country; }
    public String getDate() { return date; }
    public double getWeight() { return weight; }

    @Override
    public String toString() {
        return country + " | " + date + " | " + weight + "kg";
    }
}