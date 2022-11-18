import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.lang.foreign.GroupLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
		Main.toggleButtons = new ArrayList<Switch>();
		NodeList list = doc.getElementsByTagName("switch");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				JToggleButton button = new JToggleButton();
				button.setText(elm.getTextContent());
				if (elm.getAttribute("default").equals("on")) {
					button.setSelected(true);
				}
				Main.toggleButtons.add(new Switch(Integer.parseInt(elm.getAttribute("id")),elm.getAttribute("default")));
				button.setAlignmentX(Frame.CENTER_ALIGNMENT);
				togglebutton_panel.add(Box.createRigidArea(new Dimension(0, 10)));
				togglebutton_panel.add(button);
			} 
		}
	}

	public void loadJSliders(JPanel slider_panel) {
		slider_panel.removeAll();
		Main.sliders = new ArrayList<Slider>();
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
					Main.sliders.add(new Slider(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),Integer.parseInt(elm.getAttribute("min")),Integer.parseInt(elm.getAttribute("max")),Integer.parseInt(elm.getAttribute("step"))));
					slider.setAlignmentX(Frame.CENTER_ALIGNMENT);
					slider_panel.add(Box.createRigidArea(new Dimension(0, 10)));
					slider.addChangeListener(new ChangeListener() {
	
						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							for(Slider s:Main.sliders){
								if(s.getId()==Integer.parseInt(elm.getAttribute("id"))){
									s.setDefaultVal(Integer.parseInt(elm.getAttribute("default")));;
								}
							}
						}
					}

				});
				slider_panel.add(slider);
			}
		}else{
			//  Uno de los objetos esta nullo.
			// CREAR UN POPUP CON EL ERROR
			showError("There is a problem in the slider at the .xml");
		}
	}

	public void loadJDropdown(JPanel dropdown_panel) {
		dropdown_panel.removeAll();
		Main.dropdowns = new ArrayList<Dropdown>();
		NodeList list =	doc.getElementsByTagName("dropdown");
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
					for (int j = 0; j < options.getLength(); j++) {
						Node nodeoption = options.item(j);
						if (nodeoption.getNodeType() == Node.ELEMENT_NODE) {
							Element opc = (Element) nodeoption;
							combo.addItem(opc.getTextContent());
							drw.setOption(j, 0, opc.getAttribute("value"));
							drw.setOption(j, 1, opc.getTextContent());
						}
						
					}
					
					Main.dropdowns.add(drw);
				}
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
		Main.sensors = new ArrayList<Sensor>();
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
					Main.sensors.add(new Sensor(id,units,low,high));
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
