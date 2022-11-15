public class Dropdown {
    private int id;
    private int defaultVal;
    private String[][] option;

    public Dropdown(int id, int defaultVal, int options) {
        this.id = id;
        this.defaultVal = defaultVal;
        this.option = new String[options][2];
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

    public String[][] getOption() {
        return option;
    }

    public void setOption(int opt,int pos,String value) {
        this.option[opt][pos]=value;
    }
}
