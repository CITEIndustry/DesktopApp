import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
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
	static Document doc;
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	JFrame Frame;


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
		}
	}

	public void loadJToggleButtons(JPanel togglebutton_panel) {
		togglebutton_panel.removeAll();
		Main.toggleButtons = new HashMap<Integer, Switch>();
		NodeList list = doc.getElementsByTagName("switch");
		if (list.getLength() != 0){
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elm = (Element) node;
					JToggleButton button = new JToggleButton();
					button.setText(elm.getTextContent());
					if (elm.getAttribute("default").equals("on")) {
						button.setSelected(true);
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
								switchChange="change::"+"switch::"+Main.toggleButtons.get(i).getId()+"::"+Main.toggleButtons.get(i).getDefaultVal();
								Main.server.enviaCanvi(switchChange);
							}
						}
					});
					togglebutton_panel.add(Box.createRigidArea(new Dimension(0, 10)));
					togglebutton_panel.add(button);
				} 
			}
		}else{
			showError("There is a problem in the switch at the .xml");
		}
	}

	public void loadJSliders(JPanel slider_panel) {
		slider_panel.removeAll();
		Main.sliders = new HashMap<Integer, Slider>();
		NodeList list =	doc.getElementsByTagName("slider");
		if (list.getLength() != 0){
			//  Puedes continuar.
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
					Main.sliders.put(Integer.parseInt(elm.getAttribute("id")),new Slider(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),Integer.parseInt(elm.getAttribute("min")),Integer.parseInt(elm.getAttribute("max")),Integer.parseInt(elm.getAttribute("step"))));
					slider.setAlignmentX(Frame.CENTER_ALIGNMENT);
					slider_panel.add(Box.createRigidArea(new Dimension(0, 10)));
					slider.addChangeListener(new ChangeListener() {
	
						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							Main.sliders.get(id).setDefaultVal(slider.getValue());
							String sliderChange="";
								for(int i : Main.sliders.keySet()){
									sliderChange="change::slider::"+Main.sliders.get(i).getId()+"::"+Main.sliders.get(i).getDefaultVal();
									Main.server.enviaCanvi("sliderChange");
								}
							}
					});
					JLabel label = new JLabel(elm.getTextContent());
					label.setAlignmentX(Frame.CENTER_ALIGNMENT);
					slider_panel.add(label);
					slider_panel.add(slider);
				}
			}
		}else{
			//  Uno de los objetos esta nullo.
			// CREAR UN POPUP CON EL ERROR
			showError("There is a problem in the slider at the .xml");
		}
	}

	public void loadJDropdown(JPanel dropdown_panel) {
		dropdown_panel.removeAll();
		Main.dropdowns = new HashMap<Integer, Dropdown>();
		NodeList list =	doc.getElementsByTagName("dropdown");
		JLabel label = new JLabel();
		if (list.getLength() != 0){
			//  Puedes continuar.
			for (int i = 0; i < list.getLength(); i++) {
				JComboBox combo = new JComboBox();
				combo.setMaximumSize(new Dimension(100,25));
				Node node = list.item(i);
				combo.setBounds(100, 200, 100, 200);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elm = (Element) node;
					NodeList options = elm.getElementsByTagName("option");
					Dropdown drw = new Dropdown(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),options.getLength());
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
								dropdownChange="change::dropdown::"+Main.dropdowns.get(i).getId()+"::"+Main.dropdowns.get(i).getDefaultVal()+"::";
								Main.server.enviaCanvi(dropdownChange);
							}
						}
						
					}
						
					);
				}
				/*JLabel label = new JLabel(node.getAttribute("label"));
				label.setAlignmentX(Frame.CENTER_ALIGNMENT);
				dropdown_panel.add(label);*/
				label.setAlignmentX(Frame.CENTER_ALIGNMENT);
				dropdown_panel.add(label);
				dropdown_panel.add(combo);
			}
		}else{
			//  Uno de los objetos esta nullo.
			// CREAR UN POPUP CON EL ERROR
			showError("There is a problem in the dropdown at the .xml");
		}
	}

	public void loadSensor(JPanel sensor_panel){
		sensor_panel.removeAll();
		Main.sensors = new HashMap<Integer, Sensor>();
		NodeList list =	doc.getElementsByTagName("sensor");
		if (list.getLength() != 0){
			//  Puedes continuar.
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
					Main.sensors.put(id,new Sensor(id,units,low,high));
					JLabel label = new JLabel(elm.getTextContent());
					label.setAlignmentX(Frame.CENTER_ALIGNMENT);
					sensor_panel.add(label);
					sensor.setAlignmentX(Frame.CENTER_ALIGNMENT);
					//sensor_panel.add(Box.createRigidArea(new Dimension(0, 10)));
					sensor_panel.add(sensor);
				}
				sensor_panel.repaint();
			}
		}else{
			//  Uno de los objetos esta nullo.
			// CREAR UN POPUP CON EL ERROR
			showError("There is a problem in the sensor at the .xml");
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
