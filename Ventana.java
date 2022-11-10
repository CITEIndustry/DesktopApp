import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import javafx.event.*;
import javafx.event.ActionEvent;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Ventana {
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    
    private JFrame frame;
    private ImagePanel imagePanel;
    private JLabel filenameLabel;
    private OFImage currentImage;
    private JLabel statusLabel;
    private File selectedFile;
    private JButton deshacer;
    private JButton recargar;
    JMenu menu;

    public Ventana() {
        currentImage = null;
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame("CITEIndustry");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        makeMenuBar(frame);
        
        // Especificamos el layout manager con un buen espaciado
        contentPane.setLayout(new BorderLayout(6, 6));

        // Crear el panel de imágenes en el centro
        imagePanel = new ImagePanel();
        imagePanel.setBorder(new EtchedBorder());
        contentPane.add(imagePanel, BorderLayout.CENTER);
        
        // Creamos una Label arriba para indicar el nombre del documento seleccionado
        filenameLabel = new JLabel();
        contentPane.add(filenameLabel, BorderLayout.NORTH);
        
        // Organizar los componentes
        showFilename(null);
        frame.pack();
        
        // Coloque el marco en el centro de la pantalla y muestre
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
    private void makeMenuBar(JFrame frame) {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("Arxiu");
        menubar.add(menu);
        
        item = new JMenuItem("Carregar configuració");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                    openFile();
                               }

                            @Override
                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                // TODO Auto-generated method stub
                                
                            }
                           });
        menu.add(item);

        // create the File menu
        menu = new JMenu("Visualitzacions");
        menubar.add(menu);
    }

    private void showFilename(String filename) {
        if(filename == null) {
            filenameLabel.setText("No file displayed.");
        }
        else {
            filenameLabel.setText("File: " + filename);
        }
    }
    private void showStatus(String text) {
        statusLabel.setText(text);
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(frame); 

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
        selectedFile = fileChooser.getSelectedFile();
        currentImage = ImageFileManager.loadImage(selectedFile);
        
        if(currentImage == null) {   // image file was not a valid image
            JOptionPane.showMessageDialog(frame,
                    "The file was not in a recognized image file format.",
                    "Image Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        imagePanel.setImage(currentImage);
        showFilename(selectedFile.getPath());
        showStatus("File loaded.");
        frame.pack();
    }
}