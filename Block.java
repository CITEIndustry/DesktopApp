import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;


public class Block implements Serializable {
    private String name;
    private Map<Integer, Switch> switchList;
    private Map<Integer, Slider> sliderList;
    private Map<Integer, Dropdown> dropdownList;
    private Map<Integer, Sensor> sensorList;
    private JPanel contentPane = new JPanel();
    private JPanel togglebutton_panel = new JPanel();
	private JPanel slider_panel = new JPanel();
	private JPanel dropdown_panel = new JPanel();
	private JPanel sensor_panel = new JPanel();
    public static Map<Integer, JToggleButton> switches = new HashMap<>();
	public static Map<Integer, JSlider> jsliders = new HashMap<>();
	public static Map<Integer, JComboBox> comboBoxes = new HashMap<>();
	public static Map<Integer, JTextArea> texts = new HashMap<>();
    private XmlReader xml;

    public Block(String name, XmlReader xml){
        this.name=name;
        this.switchList = new HashMap<Integer, Switch>();
        this.sliderList = new HashMap<Integer,Slider>();
        this.dropdownList = new HashMap<Integer,Dropdown>();
        this.sensorList = new HashMap<Integer,Sensor>();
        this.xml = xml;
        loadAllComponents();
        contentPane.setBackground(new Color(204, 204, 204));
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

				contentPane.setLayout(new GridLayout(2, 0, 5, 5));

				JScrollPane scrollPane = new JScrollPane();
				contentPane.add(scrollPane);
				togglebutton_panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

				scrollPane.setViewportView(togglebutton_panel);
				togglebutton_panel.setLayout(new BoxLayout(togglebutton_panel, BoxLayout.Y_AXIS));

				JPanel togglebutton_header = new JPanel();
				togglebutton_header.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				togglebutton_header.setBackground(new Color(194, 139, 217));
				scrollPane.setColumnHeaderView(togglebutton_header);

				JLabel togglebutton_label = new JLabel("Lights");
				togglebutton_header.add(togglebutton_label);
				togglebutton_label.setVerticalAlignment(SwingConstants.CENTER);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				contentPane.add(scrollPane_1);

				JPanel slider_header = new JPanel();
				slider_header.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				slider_header.setBackground(new Color(194, 139, 217));
				scrollPane_1.setColumnHeaderView(slider_header);

				JLabel slider_label = new JLabel("Boilers' power");
				slider_header.add(slider_label);
				slider_panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

				scrollPane_1.setViewportView(slider_panel);
				slider_panel.setLayout(new BoxLayout(slider_panel, BoxLayout.Y_AXIS));

				JScrollPane scrollPane_2 = new JScrollPane();
				contentPane.add(scrollPane_2);
				dropdown_panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

				scrollPane_2.setViewportView(dropdown_panel);
				dropdown_panel.setLayout(new BoxLayout(dropdown_panel, BoxLayout.Y_AXIS));

				JPanel dropdown_header = new JPanel();
				dropdown_header.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				dropdown_header.setBackground(new Color(194, 139, 217));
				scrollPane_2.setColumnHeaderView(dropdown_header);

				JLabel dropdown_label = new JLabel("Mode");
				dropdown_header.add(dropdown_label);
				dropdown_label.setVerticalAlignment(SwingConstants.CENTER);

				JScrollPane scrollPane_3 = new JScrollPane();
				contentPane.add(scrollPane_3);
				sensor_panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

				scrollPane_3.setViewportView(sensor_panel);
				sensor_panel.setLayout(new BoxLayout(sensor_panel, BoxLayout.Y_AXIS));

				JPanel sensor_header = new JPanel();
				sensor_header.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				sensor_header.setBackground(new Color(194, 139, 217));
				scrollPane_3.setColumnHeaderView(sensor_header);

				JLabel sensor_label = new JLabel("Boilers' temperature");
				sensor_header.add(sensor_label);
				sensor_label.setVerticalAlignment(SwingConstants.CENTER);

                switches.get(2).setEnabled(true);
                
                //contentPane.add(togglebutton_panel);
                //contentPane.add(slider_panel);
                //contentPane.add(dropdown_panel);
                //contentPane.add(sensor_panel);
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

    public void loadAllComponents(){
        togglebutton_panel = xml.loadJToggleButtons(name,switches);
        slider_panel = xml.loadJSliders(name,jsliders);
        dropdown_panel = xml.loadJDropdown(name,comboBoxes);
        sensor_panel = xml.loadSensor(name,texts);
        contentPane.validate();
        contentPane.repaint();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public JPanel getTogglebutton_panel() {
        return togglebutton_panel;
    }

    public void setTogglebutton_panel(JPanel togglebutton_panel) {
        this.togglebutton_panel = togglebutton_panel;
    }

    public JPanel getSlider_panel() {
        return slider_panel;
    }

    public void setSlider_panel(JPanel slider_panel) {
        this.slider_panel = slider_panel;
    }

    public JPanel getDropdown_panel() {
        return dropdown_panel;
    }

    public void setDropdown_panel(JPanel dropdown_panel) {
        this.dropdown_panel = dropdown_panel;
    }

    public JPanel getSensor_panel() {
        return sensor_panel;
    }

    public void setSensor_panel(JPanel sensor_panel) {
        this.sensor_panel = sensor_panel;
    }

    public XmlReader getXml() {
        return xml;
    }

    public void setXml(XmlReader xml) {
        this.xml = xml;
    }

    public static Map<Integer, JToggleButton> getSwitches() {
        return switches;
    }

    public static void setSwitches(Map<Integer, JToggleButton> switches) {
        Block.switches = switches;
    }

    public static Map<Integer, JSlider> getJsliders() {
        return jsliders;
    }

    public static void setJsliders(Map<Integer, JSlider> jsliders) {
        Block.jsliders = jsliders;
    }

    public static Map<Integer, JComboBox> getComboBoxes() {
        return comboBoxes;
    }

    public static void setComboBoxes(Map<Integer, JComboBox> comboBoxes) {
        Block.comboBoxes = comboBoxes;
    }

    public static Map<Integer, JTextArea> getTexts() {
        return texts;
    }

    public static void setTexts(Map<Integer, JTextArea> texts) {
        Block.texts = texts;
    }
    
    
}
