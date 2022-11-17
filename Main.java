
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextArea;


public class Main extends JFrame {

	public static ArrayList<Switch> toggleButtons;
	public static ArrayList<Slider> sliders;
	public static ArrayList<Dropdown> dropdowns;
	public static ArrayList<JTextArea> sensor;
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
					
					Server server=new Server(8888,toggleButtons,sliders,dropdowns);
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