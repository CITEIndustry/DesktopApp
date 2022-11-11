import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class Ventana extends JFrame {

	private JPanel contentPane;
	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private JToggleButton tglbtnNewToggleButton;
	private JSlider slider;
	private JSpinner spinner;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("Arxiu");
        menubar.add(menu);
        
        item = new JMenuItem("Carregar configuració");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                                openFile();
                            }
                           });
        menu.add(item);

        // create the File menu
        menu = new JMenu("Visualitzacions");
        menubar.add(menu);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tglbtnNewToggleButton = new JToggleButton("Toggle Button");
		tglbtnNewToggleButton.setBounds(33, 11, 121, 23);
		contentPane.add(tglbtnNewToggleButton);
		
		slider = new JSlider();
		slider.setBounds(186, 11, 200, 26);
		contentPane.add(slider);
		spinner = new JSpinner();
		spinner.setBounds(53, 143, 44, 23);
		contentPane.add(spinner);
		btnNewButton = new JButton("Guardar");
		btnNewButton.setBounds(297, 143, 89, 23);
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
					if(tglbtnNewToggleButton.isSelected()) {
						writer.write(1+"\n");
					}
					else {
						writer.write(0+"\n");
					}
					writer.write(slider.getValue()+"\n");
					writer.write(spinner.getValue()+"\n");
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		contentPane.add(btnNewButton);
	}
	private void openFile()
    {
        int returnVal = fileChooser.showOpenDialog(contentPane);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
        File selectedFile = fileChooser.getSelectedFile();
        if(selectedFile.toString().contains(".xml")) {
	        try {
				Scanner sc = new Scanner(selectedFile);
				String line = sc.nextLine();
				System.out.println(line+"\n");
				if(Integer.parseInt(line)==1) {
					this.tglbtnNewToggleButton.setSelected(true);
				}
				else {
					this.tglbtnNewToggleButton.setSelected(false);
				}
				line=sc.nextLine();
				if(Integer.parseInt(line)>100) {
					this.slider.setMaximum(Integer.parseInt(line));
				}
				else if(Integer.parseInt(line)<0) {
					this.slider.setMinimum(Integer.parseInt(line));
				}
				this.slider.setValue(Integer.parseInt(line));
				line=sc.nextLine();
				this.spinner.setValue(Integer.parseInt(line));
				btnNewButton.setEnabled(true);
			}
			catch (FileNotFoundException e){
				e.printStackTrace();
			}
        }
        else {
        	System.out.println("No funciona, no es .xml");
        }
        
    }
}