public class Sensor {

    private int id;
    private String units;
    private int thresholdlow;
    private int thresholdhight;

    public Sensor(int id, String units, int thresholdlow, int thresholdhight) {
        this.id = id;
        this.units = units;
        this.thresholdlow = thresholdlow;
        this.thresholdhight = thresholdhight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getThresholdlow() {
        return thresholdlow;
    }

    public void setThresholdlow(int thresholdlow) {
        this.thresholdlow = thresholdlow;
    }

    public int getThresholdhight() {
        return thresholdhight;
    }

    public void setThresholdhight(int thresholdhight) {
        this.thresholdhight = thresholdhight;
    }
}
