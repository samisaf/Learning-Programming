package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Observable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a book of contacts, it stores any number of contacts in a vector, 
 * and has methodes to retrieve the contacts as well as to write them on a file.
 * It also contains a currentContact fileds used to go through the contacts in the book.
 * @author Sami Safadi 2007
 */
public class ContactBook extends Observable{
    private Vector<Contact> contactsVector;
    private File dataFile;
    private int currentIndex;
    private Contact currentContact;
    private boolean isOpen;
    
    /**
     * Creates a new contact book object from the file passed to it.
     * If the file doesn't exist, a new file is created and an empty
     * contact book is created.
     * @param dataFile The contact book file.
     * @throws java.io.IOException 
     */
    public ContactBook(File dataFile) throws IOException {
        openBook(dataFile);
    }
    
    /**
     * Creates a new contact book object from the file (default.book).
     * If this file doesn't exist, a new file is created and an empty
     * contact book is created.
     * @throws java.io.IOException 
     */
    public ContactBook() throws IOException{
        openBook(new File("default.book"));
    }
    
    /**
     * Reads the content of the file passed to it, and assign these contents to
     * the current contact book object. If the file doesn't exist, a new file is 
     * created and the current contact book becomes empty.
     * @param file Contact book file.
     * @throws java.io.IOException 
     */
    public void openBook(File file) throws IOException{
        isOpen = true;
        dataFile = file;
        readFile();
        setCurrentContact(0);
    }
    
    /**
     * Close the current contact book.
     */
    public void closeBook(){
        isOpen = false;
        contactsVector = null;
        dataFile = null;
        currentContact = null;
        currentIndex = -1;
        setChangedNotify();
    }

    /**
     * Checks whether the current contact book object is open or not.
     * @return true if book is open.
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    private void readFile() throws IOException{
        if (isOpen == false) return;
        ObjectInputStream stream = null;
        contactsVector = new Vector<Contact>();
        
        if (!dataFile.exists()) {
            dataFile.createNewFile();
            writeFile();
        }
        else{
            try{
                stream = new ObjectInputStream(new FileInputStream(dataFile));
                contactsVector =  (Vector<Contact>) stream.readObject();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        if (contactsVector.size()==0) contactsVector.add(new Contact());
    }
    
    private void writeFile() {
        if (isOpen == false) return;
        ObjectOutputStream stream = null;
        try{
            stream = new ObjectOutputStream(new FileOutputStream(dataFile));
            stream.writeObject(contactsVector);
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Saves the current contact book to it specified file.
     */
    public void saveBook(){
        writeFile();
    }
      
    /**
     * Retrives a contact from the book based on its index.
     * @param index the contact index.
     * @return a contact object.
     */
    public Contact getContact(int index){
        if (index < contactsVector.size()) return (Contact) contactsVector.get(index);
        else return null;
    }
    
    /**
     * Returns the size of the book.
     * @return The size of the book.
     */
    public int getSize(){
        return contactsVector.size();
    }
    
    /**
     * Sets the current contact to the contact of the specified index.
     * @param index 
     */
    public void setCurrentContact(int index){
        if (index < 0 || index >= contactsVector.size()) return;
        currentIndex = index;
        currentContact = contactsVector.get(index);
        setChangedNotify();
    }
    
    /**
     * 
     * @return the index of the current contact in the book.
     */
    public int getCurrentIndex(){
        return currentIndex;
    }
    
    /**
     * 
     * @return the current contact in the book.
     */
    public Contact getCurrentContact(){
        return currentContact;
    }
    
    /**
     * Replaces the current contact in the book with the passed contact object.
     * @param contact The new contact
     */
    public void replaceCurrentContact(Contact contact){
        currentContact = contact;
        contactsVector.set(currentIndex, currentContact);
        setChangedNotify();
    }
    
    /**
     * Deletes the current contact from the book.
     */
    public void deleteCurrentContact(){
        switch (currentIndex){
        case -1:
            break;
        case 0:
            contactsVector.remove(currentIndex);
            if (getSize() == 0 ) currentIndex--;
            break;
        default:
            contactsVector.remove(currentIndex);
            currentIndex--;
            currentContact = contactsVector.get(currentIndex);
        }
        if (this.getSize() == 0) this.addNewContact(new Contact());
        setChangedNotify();
    }
    
    /**
     * Adds a new contact to the book.
     * @param contact The new contact.
     */
    public void addNewContact(Contact contact){
        contactsVector.add(contact);
        currentContact = contact;
        currentIndex = contactsVector.size() - 1;
        setChangedNotify();
    }
    
    /**
     * Flags the contact book, and notify all the observer objects.
     */
    public void setChangedNotify(){
        setChanged();
        notifyObservers(currentContact);
    }
    
    /**
     * 
     * @return The file name assigned to the contact book object.
     */
    public String getFileName(){
        if (isOpen)
            return dataFile.getName();
        else return "";
    }
    
    /**
     * Sorts the contacts based on their last name, and first name.
     */
    public void sort(){
        Collections.sort(contactsVector);
        setCurrentContact(0);
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for (Contact c : contactsVector) sb.append(c).append('\n');
        return sb.toString();
    }
    
    /**
     * Test drive method.
     * @param args command line arguments.
     */
    public static void main(String[] args){
        try {
            model.ContactBook myBook = new model.ContactBook(new java.io.File("c:/test.book"));
            long startTime = java.lang.System.currentTimeMillis();
            for (int i = 10000; i > 0; i--) {
                myBook.addNewContact(new Contact(java.lang.String.valueOf(i)));
            }
            myBook.saveBook();
            long endTime = java.lang.System.currentTimeMillis();
            long elaspedTime = endTime - startTime;
            System.out.println("Time to create the book in ms " + elaspedTime);
            
            startTime = java.lang.System.currentTimeMillis();
            myBook.sort();
            myBook.saveBook();
            endTime = java.lang.System.currentTimeMillis();
            elaspedTime = endTime - startTime;
            System.out.println("Time to sort the book in ms " + elaspedTime);
            
            System.out.println(myBook);
        }
        catch (IOException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
    }
}
