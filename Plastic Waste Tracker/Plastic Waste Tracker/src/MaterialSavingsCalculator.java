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
                "\n========== MATERIAL SAVINGS REPORT ==========\n" +
                        "Plastic Waste Input:        %,10.2f kg (%.3f tonnes)\n" +
                        "--------------------------------------------\n" +
                        "CO₂ Emissions Prevented:    %,10.2f kg (%.3f tonnes)\n" +
                        "Energy Saved:               %,10.2f kWh\n" +
                        "Landfill Space Saved:       %,10.2f m³\n" +
                        "Gasoline Saved (Estimate):  %,10.2f – %,10.2f liters\n" +
                        "============================================\n",
                plasticKg, getPlasticTonnes(),
                getCO2PreventedKg(), getCO2PreventedTonnes(),
                getEnergySavedKWh(),
                getLandfillSpaceSavedM3(),
                getGasolineSavedLitersMin(), getGasolineSavedLitersMax()
        );
    }

    public String getSummaryLine(String label) {
        return String.format(
                "%s could prevent %.2f kg CO\u2082, save %.2f kWh energy, reclaim %.2f m³ of landfill space, " +
                        "and offset %.2f–%.2f liters of gasoline by recycling %.2f kg of plastic.",
                label,
                getCO2PreventedKg(),
                getEnergySavedKWh(),
                getLandfillSpaceSavedM3(),
                getGasolineSavedLitersMin(),
                getGasolineSavedLitersMax(),
                plasticKg
        );
    }
}

