import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JTabbedPane;
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
	private JTabbedPane panelDePestanas;
	private JMenuItem saveSnapshot;
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
        //setContentPane(contentPane);
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

		menu = new JMenu("Snapshots");
		menubar.add(menu);
		saveSnapshot = new JMenuItem("Save snapshot");
		saveSnapshot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String basePath = System.getProperty("user.dir") + File.separator;
				String filePath = basePath + "databaseIndustrialUser.db";
            	Connection connDBUser=UtilsSQLite.connect(filePath);
				UtilsSQLite.connect(filePath);
				String user = JOptionPane.showInputDialog("Introdueix el nom d'usuari");
				ResultSet userId = UtilsSQLite.querySelect(connDBUser, "SELECT id FROM user where name ='"+user+"';");
				int usId = 0;
				try {
					usId = userId.getInt("id");
					UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO snapshot (day,idUser) VALUES (datetime('now','localtime'),"+usId+");");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(usId!=0){
					ResultSet rsId = UtilsSQLite.querySelect(connDBUser, "SELECT id FROM snapshot ORDER BY id desc;");
					try {
						String componentText="";
						if(Main.toggleButtons!=null||Main.sliders!=null||Main.dropdowns!=null){
							for(int i : Main.toggleButtons.keySet()){
								componentText="switch::"+Main.toggleButtons.get(i).getId()+"::"+Main.toggleButtons.get(i).getDefaultVal()+"::"+Main.toggleButtons.get(i).getLabel();
								UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
															+componentText+"\","+rsId.getInt("id")+");");
							}
							for(int i : Main.sliders.keySet()){
								componentText="slider::"+Main.sliders.get(i).getId()+"::"+Main.sliders.get(i).getDefaultVal()+"::"+Main.sliders.get(i).getMax()
								+"::"+Main.sliders.get(i).getMin()+"::"+Main.sliders.get(i).getStep()+"::"+Main.sliders.get(i).getLabel();
								UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
															+componentText+"\","+rsId.getInt("id")+");");
							}
							System.out.println(Main.dropdowns.size());
							for(int i : Main.dropdowns.keySet()){
								componentText="dropdown::"+Main.dropdowns.get(i).getId()+"::"+Main.dropdowns.get(i).getDefaultVal()+"::"+Main.dropdowns.get(i).getLabel()+"::";
								for(int j=0;j<Main.dropdowns.get(i).getOption().length;j++){
									componentText=componentText+Main.dropdowns.get(i).getOption()[j][0]+":"+Main.dropdowns.get(i).getOption()[j][1]+"/";
								}
								UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
								+componentText+"\","+rsId.getInt("id")+");");
							}
							for(int i : Main.sensors.keySet()){
								componentText="sensor::"+Main.sensors.get(i).getId()+"::"+Main.sensors.get(i).getUnits()+"::"+Main.sensors.get(i).getThresholdlow()
								+"::"+Main.sensors.get(i).getThresholdhight()+"::"+Main.sensors.get(i).getValue()+"::"+Main.sensors.get(i).getLabel();
								UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
								+componentText+"\","+rsId.getInt("id")+");");
							}
						}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			UtilsSQLite.disconnect(connDBUser);
			}
			
		});
		saveSnapshot.setEnabled(false);
		menu.add(saveSnapshot);
    }
	public void enable(){
		saveSnapshot.setEnabled(true);
	}
    public void makeContentPane(){
        /*contentPane.setLayout(new GridLayout(2, 0, 5, 5));

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
		sensor_label.setVerticalAlignment(SwingConstants.CENTER);*/
		panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
		panelDePestanas.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelDePestanas);
		
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
            xml = new XmlReader(filePath,this);
			xml.loadBlocks();
			for(String block:Main.blocks.keySet()){
				System.out.println("bloque");
				panelDePestanas.addTab(Main.blocks.get(block).getName(), Main.blocks.get(block).getContentPane());
				
			}
			this.revalidate();
			this.repaint();
			System.out.println(filePath);
				if(Main.comboBoxes!=null){
					Main.comboBoxes.clear();
				}
				if(Main.sliders!=null){
					Main.switches.clear();
				}
				if(Main.jsliders!=null){
					Main.jsliders.clear();
				}
				if(Main.sliders!=null){
					Main.sliders.clear();
				}
				if(Main.dropdowns!=null){
					Main.dropdowns.clear();
				}
				if(Main.comboBoxes!=null){
					Main.comboBoxes.clear();
				}
				if(Main.texts!=null){
					Main.texts.clear();
				}
				if(Main.sensors!=null){
					Main.sensors.clear();
				}	
			/*xml.loadJToggleButtons(togglebutton_panel);
			if(xml.getCont()){
				xml.loadJSliders(slider_panel);
			}
			if(xml.getCont()){
				xml.loadJDropdown(dropdown_panel);
			}
			if(xml.getCont()){
				xml.loadSensor(sensor_panel);
			}
			*/
			if(!xml.getCont()){
				togglebutton_panel.removeAll();
				togglebutton_panel.repaint();
				slider_panel.removeAll();
				slider_panel.repaint();
				dropdown_panel.removeAll();
				dropdown_panel.repaint();
				sensor_panel.removeAll();
				sensor_panel.repaint();
			}
		}
        else {
        	xml.showError("The file is not .xml");
        }
		enable();
		setVisible(true);
	}

    
    
}
