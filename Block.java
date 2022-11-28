import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;


public class Block implements Serializable {
    private String name;
    private Map<Integer, Switch> switchList = new HashMap<Integer, Switch>();
    private Map<Integer, Slider> sliderList = new HashMap<Integer, Slider>();;
    private Map<Integer, Dropdown> dropdownList = new HashMap<Integer, Dropdown>();
    private Map<Integer, Sensor> sensorList = new HashMap<Integer, Sensor>();
    private JPanel contentPane = new JPanel();
    private JPanel togglebutton_panel = new JPanel();
	private JPanel slider_panel = new JPanel();
	private JPanel dropdown_panel = new JPanel();
	private JPanel sensor_panel = new JPanel();
    public Map<Integer, JToggleButton> switches = new HashMap<>();
	public Map<Integer, JSlider> jsliders = new HashMap<>();
	public Map<Integer, JComboBox> comboBoxes = new HashMap<>();
	public Map<Integer, JTextArea> texts = new HashMap<>();
    private XmlReader xml;

    public Block(String name, XmlReader xml){
        this.name=name;
        this.switchList = new HashMap<Integer, Switch>();
        this.sliderList = new HashMap<Integer,Slider>();
        this.dropdownList = new HashMap<Integer,Dropdown>();
        this.sensorList = new HashMap<Integer,Sensor>();
        this.xml = xml;
        if(!xml.isEmpty()){
            loadAllComponents();
        }
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

                //switches.get(2).setEnabled(true);
                
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
        togglebutton_panel = xml.loadJToggleButtons(name,switches, switchList);
        slider_panel = xml.loadJSliders(name,jsliders, sliderList);
        dropdown_panel = xml.loadJDropdown(name,comboBoxes, dropdownList);
        sensor_panel = xml.loadSensor(name,texts, sensorList);
        contentPane.validate();
        contentPane.repaint();
    }

    public void loadSnapshotComponent(String[] value){
        //Switch
        if (value[0].equals("switch")){
            switchList.put(Integer.parseInt(value[2]), new Switch(Integer.parseInt(value[2]), value[4], value[3]));
            JToggleButton jtb = new JToggleButton(value[4]);
            if(value[3].equals("on")){
                jtb.setSelected(true);
                System.out.println("añado");
            }else{
                jtb.setSelected(false);
            }
            JLabel label = new JLabel(value[4]);
			label.setAlignmentX(Frame.CENTER_ALIGNMENT);
            jtb.setAlignmentX(Frame.CENTER_ALIGNMENT);
            switches.put(Integer.parseInt(value[2]), jtb);
            jtb.addChangeListener(new ChangeListener() {
				
                @Override
                public void stateChanged(ChangeEvent e) {
                    // TODO Auto-generated method stub
                    String switchChange="";
                    if(jtb.isSelected()){
                        switchList.get(Integer.parseInt(value[2])).setDefaultVal("on");
                    }
                    else{
                        switchList.get(Integer.parseInt(value[2])).setDefaultVal("off");
                    
                    }
                    for(int i : switchList.keySet()){
                        switchChange="change;;switch::"+value[1]+"::"+switchList.get(i).getId()+"::"+switchList.get(i).getDefaultVal();
                        Main.server.enviaCanvi(switchChange);
                    }
                }
            });
            togglebutton_panel.add(label);
            togglebutton_panel.add(jtb);
        }
        //Slider
        if (value[0].equals("slider")){
            sliderList.put(Integer.parseInt(value[2]), new Slider(Integer.parseInt(value[2]), Integer.parseInt(value[3]), Integer.parseInt(value[5]), Integer.parseInt(value[4]), Integer.parseInt(value[6]), value[7]));
            JSlider slider = new JSlider();
            slider.setMaximumSize(new Dimension((int) slider.getPreferredSize().getWidth(), 40));
            slider.setSnapToTicks(true);
            slider.setPaintTicks(true);
            slider.setMinimum(Integer.parseInt(value[5]));
            slider.setMaximum(Integer.parseInt(value[4]));
            slider.setMinorTickSpacing(Integer.parseInt(value[6]));
            slider.setMajorTickSpacing(Integer.parseInt(value[6]));
            slider.setValue(Integer.parseInt(value[3]));
            slider.setPaintLabels(true);
            JLabel label = new JLabel(value[7]);
			label.setAlignmentX(Frame.CENTER_ALIGNMENT);
            jsliders.put(Integer.parseInt(value[2]), slider);
            slider.addChangeListener(new ChangeListener() {
				
                @Override
                public void stateChanged(ChangeEvent e) {
                    // TODO Auto-generated method stub
                    sliderList.get(Integer.parseInt(value[2])).setDefaultVal(slider.getValue());
                    System.out.println(slider.getValue());
                    String sliderChange="";
                        for(int j : sliderList.keySet()){
                            sliderChange="change;;slider::"+value[1]+"::"+sliderList.get(j).getId()+"::"+sliderList.get(j).getDefaultVal();
                            Main.server.enviaCanvi(sliderChange);
                        }
                    }
            });
            slider_panel.add(label);
            slider_panel.add(slider);

        }
        if (value[0].equals("dropdown")){
            String[] options = value[5].split("/");
            Dropdown drw = new Dropdown(Integer.parseInt(value[2]), Integer.parseInt(value[3]), options.length, value[4]);
            
            JComboBox combo = new JComboBox();
            combo.setMaximumSize(new Dimension(100,25));
            combo.setBounds(100, 200, 100, 200);
            for(int i = 0;i<options.length;i++){
                String[] opc = options[i].split(":");
                combo.addItem(opc[1]);
                System.out.println(opc[0]+" "+opc[1]);
                drw.setOption(i, 0, opc[0]);
                drw.setOption(i, 1, opc[1]);
                
            }
            combo.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    dropdownList.get(Integer.parseInt(value[2])).setDefaultVal(combo.getSelectedIndex());
                    String dropdownChange="";
                    for(int i : dropdownList.keySet()){
                        dropdownChange="change;;dropdown::"+value[1]+"::"+dropdownList.get(i).getId()+"::"+dropdownList.get(i).getDefaultVal()+"::";
                        Main.server.enviaCanvi(dropdownChange);
                    }
                }
                
            });
            dropdownList.put(Integer.parseInt(value[2]), drw);
            
            JLabel label = new JLabel(value[4]);
			label.setAlignmentX(Frame.CENTER_ALIGNMENT);
            combo.setSelectedIndex(Integer.parseInt(value[3]));
            comboBoxes.put(Integer.parseInt(value[2]), combo);
            
            dropdown_panel.add(label);
            dropdown_panel.add(combo);

        }
        if (value[0].equals("sensor")){
            sensorList.put(Integer.parseInt(value[2]), new Sensor(Integer.parseInt(value[2]), value[3], Integer.parseInt(value[4]), Integer.parseInt(value[5]), Integer.parseInt(value[6]), value[7]));

            int randomValue = Integer.parseInt(value[6]);
            JTextArea sensor = new JTextArea();
            int id = Integer.parseInt(value[2]);
            String units = value[3];
            int low = Integer.parseInt(value[4]);
            int high = Integer.parseInt(value[5]);
            sensor.setMaximumSize(new Dimension(100,25));
            sensor.setText(randomValue+units);
            sensor.setEditable(false);
            if(randomValue>=low&&randomValue<=high){
                sensor.setBackground(Color.GREEN);
            }
            else if(randomValue<low){
                sensor.setBackground(Color.cyan);
            }
            else if(randomValue>high){
                sensor.setBackground(Color.RED);
            }
            
            
            
            JLabel label = new JLabel(value[7]);
			label.setAlignmentX(Frame.CENTER_ALIGNMENT);
            texts.put(Integer.parseInt(value[2]), sensor);
            
            sensor_panel.add(label);
            sensor_panel.add(sensor);

        }
    }

    public void updateAllPanels(){
        //togglebutton_panel.revalidate();
        //togglebutton_panel.repaint();
        //contentPane.revalidate();
        //contentPane.repaint();
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

    public Map<Integer, JToggleButton> getSwitches() {
        return switches;
    }

    public void setSwitches(Map<Integer, JToggleButton> switches) {
        this.switches = switches;
    }

    public Map<Integer, JSlider> getJsliders() {
        return jsliders;
    }

    public void setJsliders(Map<Integer, JSlider> jsliders) {
        this.jsliders = jsliders;
    }

    public Map<Integer, JComboBox> getComboBoxes() {
        return comboBoxes;
    }

    public void setComboBoxes(Map<Integer, JComboBox> comboBoxes) {
        this.comboBoxes = comboBoxes;
    }

    public Map<Integer, JTextArea> getTexts() {
        return texts;
    }

    public void setTexts(Map<Integer, JTextArea> texts) {
        this.texts = texts;
    }
    
    
}
