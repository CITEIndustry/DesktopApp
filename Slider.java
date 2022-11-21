public class Slider {
    private int id;
    private int defaultVal;
    private int min;
    private int max;
    private int step;
    private String label;

    public Slider(int id, int defaultVal, int min, int max, int step,String label) {
        this.id = id;
        this.defaultVal = defaultVal;
        this.min = min;
        this.max = max;
        this.step = step;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(int defaultVal) {
        this.defaultVal = defaultVal;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
