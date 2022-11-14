public class Slider {
    private int id;
    private float defaultVal;
    private float min;
    private float max;
    private float step;

    public Slider(int id, float defaultVal, float min, float max, float step) {
        this.id = id;
        this.defaultVal = defaultVal;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(float defaultVal) {
        this.defaultVal = defaultVal;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }
}
