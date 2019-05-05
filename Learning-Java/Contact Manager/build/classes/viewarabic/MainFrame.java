package viewarabic;

import controller.Application;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import model.Contact;
import model.ContactBook;

/**
 * A frame that represents the main view of the application. It contains observer
 * components to view the contact book, and control components to change the book.
 * @author Sami Safadi 2007
 */
public class MainFrame extends JFrame implements Observer {
    private boolean isContactBookChanged = false;
    
    /** 
     * Default constructor.
     * Creates new form NewFrame 
     */
    public MainFrame() {
        initComponents();
        initializeGUI();
    }
        private void initializeGUI() {
            this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    promptSaveBook();
                }
             });
            this.pack();
            this.setVisible(true);
    }

    private void saveContact(ActionEvent e) {
        Contact tempContact = new Contact();
        for (int i=0; i<Contact.numberOfFileds; i++){
            tempContact.setField(i,myContactDataPanel.textFields[i].getText());
        }
        Application.myContactBook.replaceCurrentContact(tempContact);
        isContactBookChanged = true;
    }

    private void createNewContact(ActionEvent e) {
        Application.myContactBook.addNewContact(new Contact());
        isContactBookChanged = true;
    }
    
    /**
     * 
     * @return The observer object in the frame.
     */
    public Observer[] getObserverObjects(){
        return new Observer[] {myContactDataPanel,myContactListPane,this};
    }

    private void goPreviousContact(ActionEvent e) {
        if (Application.myContactBook.getCurrentIndex()>0)
            Application.myContactBook.setCurrentContact(Application.myContactBook.getCurrentIndex()-1);
    }

    private void goNextContact(ActionEvent e) {
        if (Application.myContactBook.getCurrentIndex() < Application.myContactBook.getSize()-1)
            Application.myContactBook.setCurrentContact(Application.myContactBook.getCurrentIndex()+1);
    }
    
    private void goFirstContact(){
        Application.myContactBook.setCurrentContact(0);
    }
    
    private void goLastContact(){
        Application.myContactBook.setCurrentContact(Application.myContactBook.getSize() - 1);
    }

    private void saveBook(ActionEvent e) {
        Application.myContactBook.saveBook();
        isContactBookChanged = false;
    }

    private void promptSaveBook(){
        if (isContactBookChanged == true){
            int answer = JOptionPane.showConfirmDialog(this,"Do you want to save you data book?", "Select an option", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                saveBook(null);
            }
        }
    }

    private void deleteContact(ActionEvent e) {
        Application.myContactBook.deleteCurrentContact();
        isContactBookChanged = true;
    }

    private void openBook(ActionEvent evt){
        promptSaveBook();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open contact book");
        fileChooser.setDialogType(fileChooser.OPEN_DIALOG);
        fileChooser.setFileFilter(new ContactBookFileFilter());
        int status = fileChooser.showOpenDialog(this);
        if (status == fileChooser.ERROR_OPTION || status == fileChooser.CANCEL_OPTION) return;
        if (!fileChooser.getSelectedFile().exists()) return;
        File dataFile = fileChooser.getSelectedFile();
        try{
            Application.myContactBook.openBook(dataFile);
        } catch(IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot open the file", "File input/output error", JOptionPane.ERROR_MESSAGE);
            this.closeBook(null);
        }
    }

    private void createNewBook(ActionEvent evt){
        promptSaveBook();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Create a new contact book");
        fileChooser.setDialogType(fileChooser.SAVE_DIALOG);
        fileChooser.setFileFilter(new ContactBookFileFilter());
        int status = fileChooser.showSaveDialog(this);
        if (status == fileChooser.ERROR_OPTION || status == fileChooser.CANCEL_OPTION) return;
        if (fileChooser.getSelectedFile().exists()) {
            int answer = JOptionPane.showConfirmDialog(this,"The file you selected already exists.\nDo you want to overwrite it?", 
                    "Select an option", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.NO_OPTION) return;
        }
        File dataFile = new File("");
        
        dataFile = fileChooser.getSelectedFile();
        if (dataFile.exists()) dataFile.delete();
        if (!dataFile.toString().endsWith(".book")) 
            dataFile = new File(fileChooser.getSelectedFile().toString() + ".book");
        try{
            Application.myContactBook.openBook(dataFile);
        } catch(IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot createp;-[-= the file", "File input/output error", JOptionPane.ERROR_MESSAGE);
            this.closeBook(null);
        }
    }

    private void closeBook(ActionEvent evt){
        this.promptSaveBook();
        Application.myContactBook.closeBook();
        isContactBookChanged = false;
    }

    /**
     * Updates the title of the frame, to reflect the file name of the contact book.
     * @param o The observable contact book object.
     * @param arg Message from observable.
     */
    public void update(Observable o, Object arg){
        ContactBook cb = (ContactBook) o;
        this.setTitle("Contact Manager - " + cb.getFileName());
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelContactData = new javax.swing.JPanel();
        jPanelControl1 = new javax.swing.JPanel();
        jButtonLastContact = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jButtonFrstContact = new javax.swing.JButton();
        myContactDataPanel = new viewarabic.ContactDataPanel();
        jPanelContactList = new javax.swing.JPanel();
        jPanelControl2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonNew = new javax.swing.JButton();
        myContactListPane = new viewarabic.ContactListPane();
        jPanel2 = new javax.swing.JPanel();
        jButtonAboutUs = new javax.swing.JButton();
        jButtonCloseBook = new javax.swing.JButton();
        jButtonSaveBook = new javax.swing.JButton();
        jButtonOpenBook = new javax.swing.JButton();
        jButtonNewBook = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNewBook = new javax.swing.JMenuItem();
        jMenuItemOpenBook = new javax.swing.JMenuItem();
        jMenuItemSaveBook = new javax.swing.JMenuItem();
        jMenuItemDeleteBook = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuContact = new javax.swing.JMenu();
        jMenuItemFirstContact = new javax.swing.JMenuItem();
        jMenuItemLastContact = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemPreviousContact = new javax.swing.JMenuItem();
        jMenuItemNextContact = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemNewContact = new javax.swing.JMenuItem();
        jMenuItemSaveContact = new javax.swing.JMenuItem();
        jMenuItemDeleteContact = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAboutUs = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Contact Manager");
        setMinimumSize(new java.awt.Dimension(500, 350));
        setResizable(false);

        jPanelContactData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "\u0627\u0644\u0645\u0639\u0644\u0648\u0645\u0627\u062a", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanelContactData.setLayout(new java.awt.BorderLayout());

        jButtonLastContact.setText("|<");
        jButtonLastContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLastContactActionPerformed(evt);
            }
        });
        jPanelControl1.add(jButtonLastContact);

        jButtonNext.setText("<");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });
        jPanelControl1.add(jButtonNext);

        jButtonPrevious.setText(">");
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });
        jPanelControl1.add(jButtonPrevious);

        jButtonFrstContact.setText(">|");
        jButtonFrstContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFrstContactActionPerformed(evt);
            }
        });
        jPanelControl1.add(jButtonFrstContact);

        jPanelContactData.add(jPanelControl1, java.awt.BorderLayout.SOUTH);
        jPanelContactData.add(myContactDataPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelContactData, java.awt.BorderLayout.CENTER);

        jPanelContactList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "\u0633\u0631\u062f \u0627\u0644\u0639\u0646\u0627\u0648\u064a\u0646", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanelContactList.setLayout(new java.awt.BorderLayout());

        jButtonDelete.setText("\u062d\u0630\u0641");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanelControl2.add(jButtonDelete);

        jButtonSave.setText("\u062d\u0641\u0638");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelControl2.add(jButtonSave);

        jButtonNew.setText("\u062c\u062f\u064a\u062f");
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });
        jPanelControl2.add(jButtonNew);

        jPanelContactList.add(jPanelControl2, java.awt.BorderLayout.SOUTH);
        jPanelContactList.add(myContactListPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelContactList, java.awt.BorderLayout.WEST);

        jButtonAboutUs.setText("\u0644\u0645\u062d\u0629 \u0639\u0646\u0627");
        jButtonAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAboutUsActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonAboutUs);

        jButtonCloseBook.setText("\u0625\u063a\u0644\u0627\u0642 \u0627\u0644\u062f\u0641\u062a\u0631");
        jButtonCloseBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseBookActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCloseBook);

        jButtonSaveBook.setText("\u062d\u0641\u0638 \u0627\u0644\u062f\u0641\u062a\u0631");
        jButtonSaveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveBookActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSaveBook);

        jButtonOpenBook.setText("\u0641\u062a\u062d \u0627\u0644\u062f\u0641\u062a\u0631");
        jButtonOpenBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenBookActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonOpenBook);

        jButtonNewBook.setText("\u062f\u0641\u062a\u0631 \u062c\u062f\u064a\u062f");
        jButtonNewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewBookActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonNewBook);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jMenuFile.setText("\u0645\u0644\u0641");

        jMenuItemNewBook.setText("\u062f\u0641\u062a\u0631 \u062c\u062f\u064a\u062f");
        jMenuItemNewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewBookActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNewBook);

        jMenuItemOpenBook.setText("\u0641\u062a\u062d \u0627\u0644\u062f\u0641\u062a\u0631");
        jMenuItemOpenBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenBookActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpenBook);

        jMenuItemSaveBook.setText("\u062d\u0641\u0638 \u0627\u0644\u062f\u0641\u062a\u0631");
        jMenuItemSaveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveBookActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSaveBook);

        jMenuItemDeleteBook.setText("\u062d\u0630\u0641 \u0627\u0644\u062f\u0641\u062a\u0631");
        jMenuItemDeleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteBookActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemDeleteBook);
        jMenuFile.add(jSeparator1);

        jMenuItemExit.setText("\u062e\u0631\u0648\u062c");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuContact.setText("\u0639\u0646\u0648\u0627\u0646");

        jMenuItemFirstContact.setText("\u0627\u0644\u0639\u0646\u0648\u0627\u0646 \u0627\u0644\u0623\u0648\u0644");
        jMenuItemFirstContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFirstContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemFirstContact);

        jMenuItemLastContact.setText("\u0627\u0644\u0639\u0646\u0648\u0627\u0646 \u0627\u0644\u0623\u062e\u064a\u0631");
        jMenuItemLastContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLastContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemLastContact);
        jMenuContact.add(jSeparator2);

        jMenuItemPreviousContact.setText("\u0627\u0644\u0639\u0646\u0648\u0627\u0646 \u0627\u0644\u0633\u0627\u0628\u0642");
        jMenuItemPreviousContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreviousContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemPreviousContact);

        jMenuItemNextContact.setText("\u0627\u0644\u0639\u0646\u0648\u0627\u0646 \u0627\u0644\u062a\u0627\u0644\u064a");
        jMenuItemNextContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNextContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemNextContact);
        jMenuContact.add(jSeparator3);

        jMenuItemNewContact.setText("\u0639\u0646\u0648\u0627\u0646 \u062c\u062f\u064a\u062f");
        jMenuItemNewContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemNewContact);

        jMenuItemSaveContact.setText("\u062d\u0641\u0638 \u0627\u0644\u0639\u0646\u0648\u0627\u0646");
        jMenuItemSaveContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemSaveContact);

        jMenuItemDeleteContact.setText("\u062d\u0630\u0641 \u0627\u0644\u0639\u0646\u0648\u0627\u0646");
        jMenuItemDeleteContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteContactActionPerformed(evt);
            }
        });
        jMenuContact.add(jMenuItemDeleteContact);

        jMenuBar1.add(jMenuContact);

        jMenuHelp.setText("\u0645\u0633\u0627\u0639\u062f\u0629");

        jMenuItemAboutUs.setText("\u0644\u0645\u062d\u0629 \u0639\u0646\u0627");
        jMenuItemAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutUsActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAboutUs);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jMenuItemAboutUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutUsActionPerformed
    jButtonAboutUs.doClick();
}//GEN-LAST:event_jMenuItemAboutUsActionPerformed

private void jMenuItemDeleteContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteContactActionPerformed
    this.deleteContact(evt);
}//GEN-LAST:event_jMenuItemDeleteContactActionPerformed

private void jMenuItemSaveContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveContactActionPerformed
    this.saveContact(evt);
}//GEN-LAST:event_jMenuItemSaveContactActionPerformed

private void jMenuItemNewContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewContactActionPerformed
    this.createNewContact(evt);
}//GEN-LAST:event_jMenuItemNewContactActionPerformed

private void jMenuItemNextContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNextContactActionPerformed
    this.goNextContact(evt);
}//GEN-LAST:event_jMenuItemNextContactActionPerformed

private void jMenuItemPreviousContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreviousContactActionPerformed
    this.goPreviousContact(evt);
}//GEN-LAST:event_jMenuItemPreviousContactActionPerformed

private void jMenuItemLastContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLastContactActionPerformed
    this.goLastContact();
}//GEN-LAST:event_jMenuItemLastContactActionPerformed

private void jMenuItemFirstContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFirstContactActionPerformed
    this.goFirstContact();
}//GEN-LAST:event_jMenuItemFirstContactActionPerformed

private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
    this.promptSaveBook();
    System.exit(0);
}//GEN-LAST:event_jMenuItemExitActionPerformed

private void jMenuItemDeleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteBookActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemDeleteBookActionPerformed

private void jMenuItemSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveBookActionPerformed
    this.saveBook(evt);
}//GEN-LAST:event_jMenuItemSaveBookActionPerformed

private void jMenuItemOpenBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenBookActionPerformed
    this.openBook(evt);
}//GEN-LAST:event_jMenuItemOpenBookActionPerformed

private void jMenuItemNewBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewBookActionPerformed
    this.createNewBook(evt);
}//GEN-LAST:event_jMenuItemNewBookActionPerformed

private void jButtonAboutUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAboutUsActionPerformed
    JOptionPane.showMessageDialog(this, "Programmed by Sami Safadi 2007");
}//GEN-LAST:event_jButtonAboutUsActionPerformed

private void jButtonCloseBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseBookActionPerformed
    closeBook(evt);
}//GEN-LAST:event_jButtonCloseBookActionPerformed

private void jButtonNewBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewBookActionPerformed
    createNewBook(evt);
}//GEN-LAST:event_jButtonNewBookActionPerformed

private void jButtonOpenBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenBookActionPerformed
    openBook(evt);
}//GEN-LAST:event_jButtonOpenBookActionPerformed

private void jButtonSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveBookActionPerformed
    saveBook(evt);
}//GEN-LAST:event_jButtonSaveBookActionPerformed

private void jButtonLastContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLastContactActionPerformed
    goLastContact();
}//GEN-LAST:event_jButtonLastContactActionPerformed

private void jButtonFrstContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFrstContactActionPerformed
    goFirstContact();
}//GEN-LAST:event_jButtonFrstContactActionPerformed

private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
    deleteContact(evt);
}//GEN-LAST:event_jButtonDeleteActionPerformed

private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
    saveContact(evt);
}//GEN-LAST:event_jButtonSaveActionPerformed

private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
    createNewContact(evt);
}//GEN-LAST:event_jButtonNewActionPerformed

private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
    goNextContact(evt);
}//GEN-LAST:event_jButtonNextActionPerformed

private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
    goPreviousContact(evt);
}//GEN-LAST:event_jButtonPreviousActionPerformed
    
    /**
     * Test drive method.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAboutUs;
    private javax.swing.JButton jButtonCloseBook;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonFrstContact;
    private javax.swing.JButton jButtonLastContact;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonNewBook;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonOpenBook;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSaveBook;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuContact;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAboutUs;
    private javax.swing.JMenuItem jMenuItemDeleteBook;
    private javax.swing.JMenuItem jMenuItemDeleteContact;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemFirstContact;
    private javax.swing.JMenuItem jMenuItemLastContact;
    private javax.swing.JMenuItem jMenuItemNewBook;
    private javax.swing.JMenuItem jMenuItemNewContact;
    private javax.swing.JMenuItem jMenuItemNextContact;
    private javax.swing.JMenuItem jMenuItemOpenBook;
    private javax.swing.JMenuItem jMenuItemPreviousContact;
    private javax.swing.JMenuItem jMenuItemSaveBook;
    private javax.swing.JMenuItem jMenuItemSaveContact;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelContactData;
    private javax.swing.JPanel jPanelContactList;
    private javax.swing.JPanel jPanelControl1;
    private javax.swing.JPanel jPanelControl2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private viewarabic.ContactDataPanel myContactDataPanel;
    private viewarabic.ContactListPane myContactListPane;
    // End of variables declaration//GEN-END:variables
}

class ContactBookFileFilter extends FileFilter{

    public boolean accept(File f) {
        if (f.getName().endsWith(".book") || f.isDirectory()) return true;
        else return false;
    }

    public String getDescription() {
        return "Contact book files";
    }
    
}