public class Switch {
    public int id;
    public String label;
    public String defaultVal;

    public Switch(int id, String label,String defaultVal) {
        this.id = id;
        this.label=label;
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

    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label=label;
    }
}
