
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import java.util.HashMap;
import java.util.Map;


public class Main extends JFrame {

	public static Map<Integer, Switch> toggleButtons;
	public static Map<Integer, Slider> sliders;
	public static Map<Integer, Dropdown> dropdowns;
	public static Map<Integer, Sensor> sensors;
	public static Map<Integer, JToggleButton> switches = new HashMap<>();
	public static Map<Integer, JSlider> jsliders = new HashMap<>();
	public static Map<Integer, JComboBox> comboBoxes = new HashMap<>();
	public static Map<Integer, JTextArea> texts = new HashMap<>();
	public static Map<String, Block> blocks = new HashMap<>();
	public static Server server;
	//public static ArrayList<Switch> toggleButtons;
	//public static ArrayList<Slider> sliders;
	//public static ArrayList<Dropdown> dropdowns;
	//public static ArrayList<Sensor> sensors;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		Frame frame = new Frame();
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					//Main frame = new Main();
					//frame.setVisible(true);
					
					server=new Server(8888);
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
}