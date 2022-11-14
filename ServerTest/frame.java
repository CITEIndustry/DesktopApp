import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;

public class frame extends JFrame {

	private static final Component button = null;
	private static JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
	private static String filePath;
	private static Document doc;
	private static ArrayList<Switch> toggleButtons;
	private static ArrayList<Slider> sliders;
	private static ArrayList<Dropdown> dropdown;
	private static ArrayList<JTextArea> sensor;
	private JPanel contentPane;
	private JPanel togglebutton_panel = new JPanel();
	private JPanel slider_panel = new JPanel();
	private JPanel dropdown_panel = new JPanel();
	private JPanel sensor_panel = new JPanel();
	private ArrayList<String> objList;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arxius XML", "xml");
		filechooser.setFileFilter(filter);

		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
					
					Server server=new Server(8888,toggleButtons,sliders,dropdown);
					server.run();
					if(frame.isActive()==false){
						server.stop();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		myRunnable.run();
	}

	/**
	 * Create the frame.
	 */
	public frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		// create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		item = new JMenuItem("Load settings");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		menu.add(item);
		
		menu = new JMenu("Views");
		menubar.add(menu);

		setContentPane(contentPane);
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

		JLabel togglebutton_label = new JLabel("ToggleButtons");
		togglebutton_header.add(togglebutton_label);
		togglebutton_label.setVerticalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);

		JPanel slider_header = new JPanel();
		slider_header.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		slider_header.setBackground(new Color(194, 139, 217));
		scrollPane_1.setColumnHeaderView(slider_header);

		JLabel slider_label = new JLabel("Sliders");
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

		JLabel dropdown_label = new JLabel("Dropdown");
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

		JLabel sensor_label = new JLabel("Sensor");
		sensor_header.add(sensor_label);
		sensor_label.setVerticalAlignment(SwingConstants.CENTER);
	}

	public void openFile() {
		int returnVal = filechooser.showOpenDialog(contentPane);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
		File selectedFile = filechooser.getSelectedFile();
		if(selectedFile.toString().contains(".xml")) {
			filePath = filechooser.getSelectedFile().getAbsolutePath();
			System.out.println(filePath);
			doc = XmlReader.llegirXML(filePath);
			loadJToggleButtons();
			loadJSliders();
			loadJDropdown();
		}
        else {
        	System.out.println("No funciona, no es .xml");
        }
		setVisible(true);
	}
	
	public void loadJToggleButtons() {
		toggleButtons = new ArrayList<Switch>();
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
				toggleButtons.add(new Switch(Integer.parseInt(elm.getAttribute("id")),elm.getAttribute("default")));
				button.setAlignmentX(CENTER_ALIGNMENT);
				togglebutton_panel.add(Box.createRigidArea(new Dimension(0, 10)));
				togglebutton_panel.add(button);
			} 
		}
		togglebutton_panel.repaint();
		
	}
	
	public void loadJSliders() {
		sliders = new ArrayList<Slider>();
		NodeList list =	doc.getElementsByTagName("slider");
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
				slider.setMaximumSize(new Dimension((int) slider.getPreferredSize().getWidth(), 30));
				slider.setSnapToTicks(true);
				slider.setPaintTicks(true);
				slider.setMinimum(min);
				slider.setMaximum(max);
				slider.setMinorTickSpacing(step);
				slider.setMajorTickSpacing(step);
				slider.setValue(initialValue);
				sliders.add(new Slider(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),Integer.parseInt(elm.getAttribute("min")),Integer.parseInt(elm.getAttribute("max")),Integer.parseInt(elm.getAttribute("step"))));
				slider.setAlignmentX(CENTER_ALIGNMENT);
				slider_panel.add(Box.createRigidArea(new Dimension(0, 10)));
				slider_panel.add(slider);
			}
		}
	}

	public void loadJDropdown() {
		dropdown = new ArrayList<Dropdown>();
		NodeList list =	doc.getElementsByTagName("dropdown");
		for (int i = 0; i < list.getLength(); i++) {
			JComboBox combo = new JComboBox();
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				NodeList options = elm.getElementsByTagName("option");
				for (int j = 0; j < options.getLength(); j++) {
					Node nodeoption = options.item(j);
					if (nodeoption.getNodeType() == Node.ELEMENT_NODE) {
						Element opc = (Element) nodeoption;
						int value = Integer.valueOf(opc.getAttribute("value"));
						combo.addItem(value);
						dropdown.add(new Dropdown(Integer.parseInt(elm.getAttribute("id")),Integer.parseInt(elm.getAttribute("default")),options.getLength()));
					}
				}
			}
			dropdown_panel.add(combo);
		}
	}

	/*public void loadJTextArea() {
		sensor = new ArrayList<JTextArea>();
		NodeList list =	doc.getElementsByTagName("sensor");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				JTextArea txta = new JTextArea();
				int id = Integer.valueOf(elm.getAttribute("id"));
				Integer units = Integer.valueOf(elm.getAttribute("units"));
				int initialValue = Integer.valueOf(elm.getAttribute("default"));
				int thresholdlow = Integer.valueOf(elm.getAttribute("thresholdlow"));
				int thresholdhigh = Integer.valueOf(elm.getAttribute("thresholdhigh"));
				txta.add("The temperature is: ")
				sensor_panel.add(txta);
			}
		} 
	}
	
	public void saveJToggleButtons() {
		NodeList list = doc.getElementsByTagName("switch");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			JToggleButton button = toggleButtons.get(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				String status = String.valueOf(button.isSelected());
				elm.setAttribute("pressed", status);
			}
		}
	}
	
	public void saveJSliders() {
		NodeList list = doc.getElementsByTagName("slider");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			JSlider slider = sliders.get(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
				System.out.println(slider.getValue());
				String value = String.valueOf(slider.getValue());
				elm.setAttribute("initialValue", value);
			}
		}
	}*/
}
