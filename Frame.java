import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Document;

public class Frame extends JFrame {

    private JPanel contentPane;
    private JMenuBar menubar = new JMenuBar();
    private JPanel togglebutton_panel = new JPanel();
	private JPanel slider_panel = new JPanel();
	private JPanel dropdown_panel = new JPanel();
	private JPanel sensor_panel = new JPanel();
    private static String filePath;
    private static JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
    private static Document doc;
    private XmlReader xml;

    public Frame(){
        this.setTitle("Desktop APP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        makeMenuBar();
        setJMenuBar(menubar);
        makeContentPane();
        setContentPane(contentPane);
        setVisible(true);
    }

    public void makeMenuBar(){
        JMenu menu;
		JMenuItem item;
        // create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		item = new JMenuItem("Load settings");
		item.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}

		});
		menu.add(item);
		
		menu = new JMenu("Views");
		menubar.add(menu);
    }

    public void makeContentPane(){
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
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		filechooser.setFileFilter(filter);
		int returnVal = filechooser.showOpenDialog(contentPane);
        
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
		File selectedFile = filechooser.getSelectedFile();
		if(selectedFile.toString().contains(".xml")) {
			filePath = filechooser.getSelectedFile().getAbsolutePath();
            xml = new XmlReader(filePath, this);
			System.out.println(filePath);
			xml.loadJToggleButtons(togglebutton_panel);
			xml.loadJSliders(slider_panel);
			xml.loadJDropdown(dropdown_panel);
			xml.loadSensor(sensor_panel);
		}
        else {
        	System.out.println("Error: The file needs to be a .xml");
        }
		setVisible(true);
	}

    
    
}
