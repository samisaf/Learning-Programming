package view;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Contact;
import model.ContactBook;

/**
 * This class represents a panel that includes labels and text boxes, that constitute
 * the view of the current contact of the observable contact book object.
 * @author Sami Safadi 2007
 */
public class ContactDataPanel extends JPanel implements Observer {
    JLabel[] labelFields = null;
    JTextField[] textFields = null;
    GridLayout myGridLayout = null;
    int numberOfFields;
    
    /**
     * A default contructor.
     */
    public ContactDataPanel() {
        super();
        addComponents();
    }
    
    private void addComponents(){
        numberOfFields = Contact.numberOfFileds;
        myGridLayout = new GridLayout(numberOfFields, 2, 5, 5);
        this.setLayout(myGridLayout);
        labelFields = new JLabel[numberOfFields];
        textFields = new JTextField[numberOfFields];
        for (int i = 0; i < numberOfFields; i++) {
            labelFields[i] = new JLabel(Contact.contactFieldNames[i]);
            this.add(labelFields[i]);
            textFields[i] = new JTextField(10);
            this.add(textFields[i]);
        }
    }

    /**
     * The update method, to change the contents of the textboxes based on the
     * current contact in the observable contact book object.
     * @param o Observable contact book object
     * @param arg Message from observable
     */
    public void update(Observable o, Object arg) {
        ContactBook cb = (ContactBook) o;
        if (cb.isOpen()) {
            Contact contact = (Contact) arg;
            for (int i=0; i < numberOfFields; i++){
                textFields[i].setEnabled(true);
                textFields[i].setText(contact.getField(i));
            }
        }
        else 
            for (JTextField t  : textFields) {
                t.setEnabled(false);
            }
    }
}