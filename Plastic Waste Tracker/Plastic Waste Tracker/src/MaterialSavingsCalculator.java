public class MaterialSavingsCalculator {

    private double plasticKg;

    public MaterialSavingsCalculator(double plasticKg) {
        this.plasticKg = plasticKg;
    }

    public double getPlasticTonnes() {
        return plasticKg / 1000.0;
    }

    public double getCO2PreventedKg() {
        return plasticKg * 1.02;
    }

    public double getCO2PreventedTonnes() {
        return getCO2PreventedKg() / 1000.0;
    }

    public double getEnergySavedKWh() {
        return getPlasticTonnes() * 5774;
    }

    public double getLandfillSpaceSavedM3() {
        double cubicYards = getPlasticTonnes() * 30.4;
        return cubicYards * 0.764555;
    }

    public double getGasolineSavedLitersMin() {
        return getPlasticTonnes() * 1000 * 3.78541;
    }

    public double getGasolineSavedLitersMax() {
        return getPlasticTonnes() * 2000 * 3.78541;
    }

    @Override
    public String toString() {
        return String.format(
                "Total Plastic: %.2f kg (%.3f tonnes)%n" +
                        "CO2 Prevented: %.2f kg (%.3f tonnes)%n" +
                        "Energy Saved: %.2f kWh%n" +
                        "Landfill Space Saved: %.2f m³%n" +
                        "Gasoline Saved: %.2f - %.2f liters",
                plasticKg, getPlasticTonnes(),
                getCO2PreventedKg(), getCO2PreventedTonnes(),
                getEnergySavedKWh(),
                getLandfillSpaceSavedM3(),
                getGasolineSavedLitersMin(), getGasolineSavedLitersMax()
        );
    }
    public String getSummaryLine(String label) {
        return String.format(
                "if %s would recycle their %.2f kg plastic, they would prevent %.2f kg CO\u2082 emissions,  saved %.2f kWh,  " +
                        " freed %.2f m\u00B3 landfill space and offset %.2f–%.2f litres of gasoline.",
                label,
                plasticKg,
                getCO2PreventedKg(),
                getEnergySavedKWh(),
                getLandfillSpaceSavedM3(),
                getGasolineSavedLitersMin(),
                getGasolineSavedLitersMax()
        );
    }
}

