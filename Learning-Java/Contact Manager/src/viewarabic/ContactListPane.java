package viewarabic;

import controller.Application;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Contact;
import model.ContactBook;

/**
 * This class represents a scroll list that containes a list of all the 
 * contacts in the observable contact book object.
 * @author Sami Safadi 2007
 */
public class ContactListPane extends JScrollPane implements Observer{
    JList myList;
    DefaultListModel myListModel;
    
    /**
     * A default constructor.
     */
    public ContactListPane() {
        myList = new JList();
    }

    /**
     * Updates the contents of the list based on the observable contact book object.
     * @param o The observable contact book object.
     * @param arg Message from observable.
     */
    public void update(Observable o, Object arg) {
        ContactBook cb = (ContactBook) o;
        if (cb.isOpen()){
            String temp;
            myListModel = new DefaultListModel();
            String[] listData = new String[cb.getSize()];
            for (int i=0; i < cb.getSize(); i++) {
                temp = cb.getContact(i).getField(Contact.contactFields.firstName) + " " + 
                    cb.getContact(i).getField(Contact.contactFields.lastName);
                myListModel.addElement(temp);
                listData[i] = temp;
            }
            myList = new JList(myListModel);
            myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            myList.setSelectedIndex(cb.getCurrentIndex());
            this.setViewportView(myList);
            myList.ensureIndexIsVisible(myList.getSelectedIndex());
            myList.addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e) {
                int selection = myList.getSelectedIndex();
                if (selection >=0)
                    changeCurrentContact(selection);
            }
            });
        }
        else myList.setEnabled(false);
    }
    
    private void changeCurrentContact(int newIndex){
        Application.myContactBook.setCurrentContact(newIndex);
    }
}