import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent.EventType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.password4j.SecureString;

public class XmlReader {
	private JPanel contentPane;
    private JPanel togglebutton_panel = new JPanel();
	private JPanel slider_panel = new JPanel();
	private JPanel dropdown_panel = new JPanel();
	private JPanel sensor_panel = new JPanel();
	private boolean cont=true;
	static Document doc;

	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	JFrame Frame;
	
	
	public void setCont(boolean cont){
		this.cont=cont;
	}

	public boolean getCont(){
		return this.cont;
	}
	public XmlReader(String path, JFrame Frame){
		this.Frame = Frame;
		try {
			File file = new File(path);
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError("XML badly written");
		}
	}

	public JTabbedPane loadBlocks(){
		NodeList list = doc.getElementsByTagName("controls");
		JTabbedPane panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
		panelDePestanas.setBorder(new EmptyBorder(5, 5, 5, 5));
		for(int i=0;i<list.getLength();i++){
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				System.out.println("Bloques "+elm.getAttribute("name"));

				contentPane = new JPanel();
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
				panelDePestanas.addTab(elm.getAttribute("name"), contentPane);
			}
		}
		//Frame.add(panelDePestanas);
		return panelDePestanas;
	}
	public void loadJToggleButtons(JPanel togglebutton_panel) {
		togglebutton_panel.removeAll();
		Main.toggleButtons = new HashMap<Integer, Switch>();
		NodeList list = doc.getElementsByTagName("switch");
			try{
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element elm = (Element) node;
						JToggleButton button = new JToggleButton();
						button.setText(elm.getTextContent());
						if (elm.getAttribute("default").equals("on")) {
							button.setSelected(true);
						}
						else if (elm.getAttribute("default").equals("off")) {
							button.setSelected(false);
						}
						else{
							showError("There is a problem in the default value of the switch at the .xml");
							cont=false;
						}
						JLabel label = new JLabel(elm.getTextContent());
						label.setAlignmentX(Frame.CENTER_ALIGNMENT);
						togglebutton_panel.add(label);
						Main.toggleButtons.put(Integer.parseInt(elm.getAttribute("id")),new Switch(Integer.parseInt(elm.getAttribute("id")),elm.getTextContent(),elm.getAttribute("default")));
						button.setAlignmentX(Frame.CENTER_ALIGNMENT);
						button.addChangeListener(new ChangeListener() {
		
							@Override
							public void stateChanged(ChangeEvent e) {
								// TODO Auto-generated method stub
								String switchChange="";
								if(button.isSelected()){
									Main.toggleButtons.get(Integer.parseInt(elm.getAttribute("id"))).setDefaultVal("on");
								}
								else{
									Main.toggleButtons.get(Integer.parseInt(elm.getAttribute("id"))).setDefaultVal("off");
								
								}
								for(int i : Main.toggleButtons.keySet()){
									switchChange="change;;switch::"+Main.toggleButtons.get(i).getId()+"::"+Main.toggleButtons.get(i).getDefaultVal();
									Main.server.enviaCanvi(switchChange);
								}
							}
						});
						if(Main.switches.containsKey(Integer.parseInt(elm.getAttribute("id")))){
							showError("There is a problem in the switch at the .xml, repeated id");
							cont=false;
						}
						Main.switches.put(Integer.parseInt(elm.getAttribute("id")),button);
						togglebutton_panel.add(Box.createRigidArea(new Dimension(0, 10)));
						togglebutton_panel.add(button);
					} 
				}
		}
		catch(Exception e){
			showError("There is a problem in the switch at the .xml");
			cont=false;
		}
	}

	public void loadJSliders(JPanel slider_panel) {
		slider_panel.removeAll();
		Main.sliders = new HashMap<Integer, Slider>();
		NodeList list =	doc.getElementsByTagName("slider");
			try{
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element elm = (Element) node;
						JSlider slider = new JSlider();
						int id = Integer.valueOf(elm.getAttribute("id"));
						int initialValue = Integer.valueOf(elm.getAttribute("default"));
						int min = Integer.valueOf(elm.getAttribute("min"));
						int max = Integer.valueOf(elm.getAttribute("max"));
						int step = Integer.valueOf(elm.getAttribute("step"));
						slider.setMaximumSize(new Dimension((int) slider.getPreferredSize().getWidth(), 40));
						slider.setSnapToTicks(true);
						slider.setPaintTicks(true);
						slider.setMinimum(min);
						slider.setMaximum(max);
						slider.setMinorTickSpacing(step);
						slider.setMajorTickSpacing(step);
						slider.setValue(initialValue);
						slider.setPaintLabels(true);
						Main.sliders.put(Integer.parseInt(elm.getAttribute("id")),new Slider(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),Integer.parseInt(elm.getAttribute("min")),Integer.parseInt(elm.getAttribute("max")),Integer.parseInt(elm.getAttribute("step")),elm.getTextContent()));
						slider.setAlignmentX(Frame.CENTER_ALIGNMENT);
						slider_panel.add(Box.createRigidArea(new Dimension(0, 10)));
						slider.addChangeListener(new ChangeListener() {
		
							@Override
							public void stateChanged(ChangeEvent e) {
								// TODO Auto-generated method stub
								Main.sliders.get(id).setDefaultVal(slider.getValue());
								System.out.println(slider.getValue());
								String sliderChange="";
									for(int i : Main.sliders.keySet()){
										sliderChange="change;;slider::"+Main.sliders.get(i).getId()+"::"+Main.sliders.get(i).getDefaultVal();
										Main.server.enviaCanvi(sliderChange);
									}
								}
						});
						if(Main.jsliders.containsKey(Integer.parseInt(elm.getAttribute("id")))){
							showError("There is a problem in the slider at the .xml, repeated id");
							cont=false;
						}
						Main.jsliders.put(Integer.parseInt(elm.getAttribute("id")),slider);
						JLabel label = new JLabel(elm.getTextContent());
						label.setAlignmentX(Frame.CENTER_ALIGNMENT);
						slider_panel.add(label);
						slider_panel.add(slider);
					}
				}
		}
		catch(Exception e){
			showError("There is a problem in the slider at the .xml");
			cont=false;
		}
	}

	public void loadJDropdown(JPanel dropdown_panel) {
		dropdown_panel.removeAll();
		Main.dropdowns = new HashMap<Integer, Dropdown>();
		NodeList list =	doc.getElementsByTagName("dropdown");
		JLabel label = new JLabel();
		int defVal=0;
			try{
				for (int i = 0; i < list.getLength(); i++) {
					JComboBox combo = new JComboBox();
					combo.setMaximumSize(new Dimension(100,25));
					Node node = list.item(i);
					combo.setBounds(100, 200, 100, 200);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element elm = (Element) node;
						NodeList options = elm.getElementsByTagName("option");
						defVal = Integer.parseInt(elm.getAttribute("default"));
						Dropdown drw = new Dropdown(Integer.parseInt(elm.getAttribute("id")),defVal,options.getLength(),elm.getAttribute("label"));
						label = new JLabel(elm.getAttribute("label"));
						
						for (int j = 0; j < options.getLength(); j++) {
							Node nodeoption = options.item(j);
							if (nodeoption.getNodeType() == Node.ELEMENT_NODE) {
								Element opc = (Element) nodeoption;
								combo.addItem(opc.getTextContent());
								drw.setOption(j, 0, opc.getAttribute("value"));
								drw.setOption(j, 1, opc.getTextContent());
							}
							
						}
						Main.dropdowns.put(Integer.parseInt(elm.getAttribute("id")),drw);
						combo.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Main.dropdowns.get(Integer.parseInt(elm.getAttribute("id"))).setDefaultVal(combo.getSelectedIndex());
								String dropdownChange="";
								for(int i : Main.dropdowns.keySet()){
									dropdownChange="change;;dropdown::"+Main.dropdowns.get(i).getId()+"::"+Main.dropdowns.get(i).getDefaultVal()+"::";
									Main.server.enviaCanvi(dropdownChange);
								}
							}
							
						}
							
						);
						if(Main.comboBoxes.containsKey(Integer.parseInt(elm.getAttribute("id")))){
							showError("There is a problem in the dropdown at the .xml, repeated id");
							cont=false;
						}
						Main.comboBoxes.put(Integer.parseInt(elm.getAttribute("id")),combo);
					}
					/*JLabel label = new JLabel(node.getAttribute("label"));
					label.setAlignmentX(Frame.CENTER_ALIGNMENT);
					dropdown_panel.add(label);*/
					label.setAlignmentX(Frame.CENTER_ALIGNMENT);
					dropdown_panel.add(label);
					combo.setSelectedIndex(defVal);
					dropdown_panel.add(combo);
				}
		}
		catch(Exception e){
			showError("There is a problem in the dropdown at the .xml");
			cont=false;
		}
	}

	public void loadSensor(JPanel sensor_panel){
		sensor_panel.removeAll();
		Main.sensors = new HashMap<Integer, Sensor>();
		NodeList list =	doc.getElementsByTagName("sensor");
			try{
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						int randomValue = (int) (Math.random()*20-1);
						Element elm = (Element) node;
						JTextArea sensor = new JTextArea();
						int id = Integer.valueOf(elm.getAttribute("id"));
						String units = String.valueOf(elm.getAttribute("units"));
						int low = Integer.valueOf(elm.getAttribute("thresholdlow"));
						int high = Integer.valueOf(elm.getAttribute("thresholdhigh"));
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
						Main.sensors.put(id,new Sensor(id,units,low,high,randomValue,elm.getTextContent()));
						JLabel label = new JLabel(elm.getTextContent());
						label.setAlignmentX(Frame.CENTER_ALIGNMENT);
						sensor_panel.add(label);
						sensor.setAlignmentX(Frame.CENTER_ALIGNMENT);
						//sensor_panel.add(Box.createRigidArea(new Dimension(0, 10)));
						if(Main.texts.containsKey(Integer.parseInt(elm.getAttribute("id")))){
							showError("There is a problem in the sensor at the .xml, repeated id");
							cont=false;
						}
						Main.texts.put(id,sensor);
						sensor_panel.add(sensor);
					}
					sensor_panel.repaint();
				}
		}catch(Exception e){
			cont=false;
			showError("There is a problem with something in the sensor at the .xml");
		}
	}
	public void guardarXML(String path) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			trimWhitespace(doc);
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	static public NodeList getNodeList(Document doc, String expression) {
		NodeList llista = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			llista = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return llista;
	}

	public static void trimWhitespace(Node node) {
		// Per algun motiu es guarden salts de línia innecessaris,
		// aquesta funció els neteja deixant l'XML bonic
		if (node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); ++i) {
				Node child = children.item(i);
				if (child.getNodeType() == Node.TEXT_NODE) {
					child.setTextContent(child.getTextContent().trim());
				}
				trimWhitespace(child);
			}
		}
	}

	public static NodeList getElementsByTagName(String string) {
		return null;
	}

	public void showError(String message){  
		JOptionPane.showMessageDialog(Frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
