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
						if(Main.blocks!=null){
							for(String s : Main.blocks.keySet()){
								String componentText="block::"+Main.blocks.get(s).getName();
								UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
																+componentText+"\","+rsId.getInt("id")+");");
								for(int i : Main.blocks.get(s).getSwitchList().keySet()){
									componentText="switch::"+Main.blocks.get(s).getName()+"::"+Main.blocks.get(s).getSwitchList().get(i).getId()+"::"+Main.blocks.get(s).getSwitchList().get(i).getDefaultVal()+"::"+Main.blocks.get(s).getSwitchList().get(i).getLabel();
									UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
																+componentText+"\","+rsId.getInt("id")+");");
								}
								for(int i : Main.blocks.get(s).getSliderList().keySet()){
									componentText="slider::"+Main.blocks.get(s).getName()+"::"+Main.blocks.get(s).getSliderList().get(i).getId()+"::"+Main.blocks.get(s).getSliderList().get(i).getDefaultVal()+"::"+Main.blocks.get(s).getSliderList().get(i).getMax()
									+"::"+Main.blocks.get(s).getSliderList().get(i).getMin()+"::"+Main.blocks.get(s).getSliderList().get(i).getStep()+"::"+Main.blocks.get(s).getSliderList().get(i).getLabel();
									UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
																+componentText+"\","+rsId.getInt("id")+");");
								}
								for(int i : Main.blocks.get(s).getDropdownList().keySet()){
									componentText="dropdown::"+Main.blocks.get(s).getName()+"::"+Main.blocks.get(s).getDropdownList().get(i).getId()+"::"+Main.blocks.get(s).getDropdownList().get(i).getDefaultVal()+"::"+Main.blocks.get(s).getDropdownList().get(i).getLabel()+"::";
									for(int j=0;j<Main.blocks.get(s).getDropdownList().get(i).getOption().length;j++){
										componentText=componentText+Main.blocks.get(s).getDropdownList().get(i).getOption()[j][0]+":"+Main.blocks.get(s).getDropdownList().get(i).getOption()[j][1]+"/";
									}
									UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
									+componentText+"\","+rsId.getInt("id")+");");
								}
								for(int i : Main.blocks.get(s).getSensorList().keySet()){
									componentText="sensor::"+Main.blocks.get(s).getName()+"::"+Main.blocks.get(s).getSensorList().get(i).getId()+"::"+Main.blocks.get(s).getSensorList().get(i).getUnits()+"::"+Main.blocks.get(s).getSensorList().get(i).getThresholdlow()
									+"::"+Main.blocks.get(s).getSensorList().get(i).getThresholdhight()+"::"+Main.blocks.get(s).getSensorList().get(i).getValue()+"::"+Main.blocks.get(s).getSensorList().get(i).getLabel();
									UtilsSQLite.queryUpdate(connDBUser, "INSERT INTO components(component,idSnapshot) VALUES (\""
									+componentText+"\","+rsId.getInt("id")+");");
								}
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
			if(Main.blocks!=null){
				panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
				panelDePestanas.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(panelDePestanas);
				for(String s: Main.blocks.keySet()){
					Main.blocks.get(s).getTogglebutton_panel().removeAll();
					Main.blocks.get(s).getTogglebutton_panel().repaint();
					Main.blocks.get(s).getSlider_panel().removeAll();
					Main.blocks.get(s).getSlider_panel().repaint();
					Main.blocks.get(s).getDropdown_panel().removeAll();
					Main.blocks.get(s).getDropdown_panel().repaint();
					Main.blocks.get(s).getSensor_panel().removeAll();
					Main.blocks.get(s).getSensor_panel().repaint();
				}
			}
            xml = new XmlReader(filePath,this);
			xml.loadBlocks();
			for(String block:Main.blocks.keySet()){
				System.out.println("bloque");
				panelDePestanas.addTab(Main.blocks.get(block).getName(), Main.blocks.get(block).getContentPane());
				
			}
			this.revalidate();
			this.repaint();
			if(!xml.getCont()){
				for(String s: Main.blocks.keySet()){
					panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
					panelDePestanas.setBorder(new EmptyBorder(5, 5, 5, 5));
					setContentPane(panelDePestanas);
					Main.blocks.get(s).getTogglebutton_panel().removeAll();
					Main.blocks.get(s).getTogglebutton_panel().repaint();
					Main.blocks.get(s).getSlider_panel().removeAll();
					Main.blocks.get(s).getSlider_panel().repaint();
					Main.blocks.get(s).getDropdown_panel().removeAll();
					Main.blocks.get(s).getDropdown_panel().repaint();
					Main.blocks.get(s).getSensor_panel().removeAll();
					Main.blocks.get(s).getSensor_panel().repaint();
				}
			}
		}
        else {
        	xml.showError("The file is not .xml");
        }
		enable();
		setVisible(true);
	}

    
    
}
