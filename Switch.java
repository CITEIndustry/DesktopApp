public class Switch {
    public int id;
    public String defaultVal;

    public Switch(int id, String defaultVal) {
        this.id = id;
        this.defaultVal = defaultVal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }
}
