package controller;

import java.io.File;
import java.io.IOException;
import java.util.Observer;
import javax.swing.UIManager;
import model.ContactBook;
import view.MainFrame;

/**
 * This class represents the controller in the program, it initializes the model
 * and view objects, and linkes them.
 * @author Sami Safadi 2007
 */
public class Application {
    /**
     * Model objects:
     */
    public static ContactBook myContactBook;
    /**
     * View Object:
     */
    public static MainFrame myMainFrame;
    
    /**
     * The class constructor.
     */
    public Application() {
        try{
            myContactBook = new ContactBook(new File("default.book"));
            myMainFrame = new MainFrame();
            addObservers();
            myContactBook.setChangedNotify();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method links the model & view objects by using the observer/observable
     * pattern
     */
    public void addObservers(){
        for (Observer o : myMainFrame.getObserverObjects())
            myContactBook.addObserver(o);
    }
    
    /**
     * The main method, the program starts here.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application application = new Application();
    }
}
