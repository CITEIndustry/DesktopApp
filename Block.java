import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Block implements Serializable {
    private String name;
    private Map<Integer, Switch> switchList;
    private Map<Integer, Slider> sliderList;
    private Map<Integer, Dropdown> dropdownList;
    private Map<Integer, Sensor> sensorList;

    public Block(String name){
        this.name=name;
        this.switchList = new HashMap<Integer, Switch>();
        this.sliderList = new HashMap<Integer,Slider>();
        this.dropdownList = new HashMap<Integer,Dropdown>();
        this.sensorList = new HashMap<Integer,Sensor>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Switch> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(Map<Integer, Switch> switchList) {
        this.switchList = switchList;
    }

    public Map<Integer, Slider> getSliderList() {
        return sliderList;
    }

    public void setSliderList(Map<Integer, Slider> sliderList) {
        this.sliderList = sliderList;
    }

    public Map<Integer, Dropdown> getDropdownList() {
        return dropdownList;
    }

    public void setDropdownList(Map<Integer, Dropdown> dropdownList) {
        this.dropdownList = dropdownList;
    }

    public Map<Integer, Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(Map<Integer, Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    
}
