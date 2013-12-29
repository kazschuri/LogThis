import javax.swing.*;
import java.awt.BorderLayout;

/**
 * @author daniel
 *
 */
public class LogGUI {
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void guiing()
    {
        /* Erzeugung eines neuen Frames mit dem 
        Titel "Mein JFrame Beispiel" */             
     JFrame meinFrame = new JFrame("Mein JFrame Beispiel");
     /* Wir setzen die Breite und die Höhe 
        unseres Fensters auf 200 Pixel */          
     meinFrame.setSize(410,200);
     meinFrame.getContentPane().setLayout(new BorderLayout(10, 10));
     
     JTextArea textArea = new JTextArea("test");
     meinFrame.getContentPane().add(textArea, BorderLayout.CENTER);
     
     // Wir lassen unseren Frame anzeigen
     meinFrame.setVisible(true);
    }
}
