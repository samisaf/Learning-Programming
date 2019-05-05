package model;

import java.io.Serializable;

/**
 * This class represents the contact infromation for one person. The information 
 * is stored in a string array.
 * @author Sami Safadi 2007
 */
public class Contact implements Serializable, Comparable<Contact> {
    /**
     * This enum class represents the names of the fields, it may sometimes be more 
     * convienient to use it to get the value of the field of interest instead of using
     * their indexed values. For example:
     * <CODE>myContact.getField(contactFields.firstName)</CODE> can be more expressive 
     * than <CODE>myContact.getField(0)</CODE>
     */
    public static enum contactFields {firstName, lastName, workPhone, homePhone, faxNumber, mobileNumber, email, address}
    /**
     * This is a String array representing the names of the fields of the contact data.
     */
    public final static  String[] contactFieldNames = {"First name", "Last Name", "Work Phone", 
        "Home Phone", "Fax Number", "Mobile Number", "Email", "Address"};
    
    /**
     * This is a String array representing the Arabic names of the fields of the contact data.
     */
    public final static  String[] contactFieldNamesArabic = {"الاسم الأول", "اسم العائلة", "رقم العمل", 
        "رقم المنزل", "رقم الفاكس", "رقم الخليوي", "البريد الالكتروني", "العنوان"};
        
    /**
     * This is the number of the fields in the contact data array.
     */
    public final static int numberOfFileds = contactFields.values().length;
    
    private String [] contactData;

    /**
     * A default constructor, all the contact data fields are assigned to ""
     */
    public Contact(){
        initialize();
    }

    /**
     * A constructor that creates a Contact object based on a string array.
     * @param data The string array fields are used to fill the contact data string array.
     * If the length array provided is shorter than the lentgth of the contact data array, 
     * the remaining fields are filled with empty strings "". If the array provided is 
     * longer than the contact field array, the additional information is discarded.
     */
    public Contact(String... data){
        initialize(data);
    }
    
    private void initialize(String... data){
        assert contactFields.values().length == contactFieldNames.length;
        contactData = new String[numberOfFileds];
        for(int i = 0; i < contactData.length; i++){
            if (i < data.length) contactData[i] =data[i]; 
            else contactData[i] = "";
        }
    }
    
    /**
     * Assigns a new value to a contact data field.
     * @param fieldName The name of the field you want to change.
     * @param newValue The new string value of the field.
     */
    public void setField(contactFields fieldName, String newValue){
        contactData[fieldName.ordinal()] = newValue;
    }
    
    /**
     * Assigns a new value to a contact data field.
     * @param index The index of the field you want to change.
     * @param newValue The new string value of the field.
     */
    public void setField(int index, String newValue){
        contactData[index] = newValue;
    }
    
    /**
     * Retreives the value of the specified contact data field.
     * @param fieldName The name of the field you want to get the value of.
     * @return The string value of the field.
     */
    public String getField(contactFields fieldName){
        return contactData[fieldName.ordinal()];
    }
    
    /**
     * Retreives the value of the specified contact data field.
     * @param index The name of the field you want to get the value of.
     * @return The string value of the field.
     */
    public String getField(int index){
        return contactData[index];
    }
    

    /**
     * 
     * @return A String representation of the contact
     */
    public String toString(){
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < contactFieldNames.length; i++)
            b.append(contactFieldNames[i]).append(": ").append(contactData[i]).append('\n');
        return b.toString();
    }

    /**
     * This method compares two conatct based on their last names, and first names.
     * @param contact 
     * @return an integer value representing the relationship to the contact passed.
     */
    public int compareTo(Contact contact) {
        int firstNameComparison = this.getField(0).compareTo(contact.getField(0));
        int lastNameComparison = this.getField(1).compareTo(contact.getField(1));
        if (lastNameComparison == 0) return firstNameComparison;
        else return lastNameComparison;
    }
    
    /**
     * Test drive method.
     * @param args Command line arguments
     */
    public static void main(String[] args){
        Contact person = new Contact("Sami", "Safadi", "5120197");

        System.out.println(person);
        person.setField(contactFields.email, "samisaf@gmail.com");
        System.out.println(person);
        System.out.println("The email of " + person.getField(contactFields.firstName) + " is "+ person.getField(contactFields.email));
        for (contactFields c : contactFields.values()) System.out.println(c.name());
    }
}