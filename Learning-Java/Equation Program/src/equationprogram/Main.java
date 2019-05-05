/*
 * Main.java
 *
 * Created on April 20, 2007, 5:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package equationprogram;
import javax.swing.*;

/**
 * The start point of the application.
 * @author Sami Safadi
 */

public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static void main(String[] args) {
        // Set the look and feel of the application
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        // call the main form
        MainWindow.main(args);
        //MainForm.main(args);
    }
    
}
