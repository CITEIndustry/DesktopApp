public class Sensor {

    private int id;
    private String units;
    private int thresholdlow;
    private int thresholdhight;
    private int value;
    private String label;

    public Sensor(int id, String units, int thresholdlow, int thresholdhight,int value,String label) {
        this.id = id;
        this.units = units;
        this.thresholdlow = thresholdlow;
        this.thresholdhight = thresholdhight;
        this.label=label;
        this.value = value;
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
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
