import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class ContactApp{
 //Create button names for main menu & arraylist to store contacts.
 private static final String ADD_CONTACT = "Add Contact";
 private static final String SEARCH_CONTACTS = "Search Contacts";
 private static final String DISPLAY_CONTACTS = "Display All Contacts";
 private static final String IMPORT_EXPORT = "Import/Export Contact Information";
 static ArrayList<Contact> contactRecord = new ArrayList<Contact>();

 //method to create the main menu
 private static void createGUI() {
     JFrame MainMenu = new JFrame("Contact Book");
     MainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     //Set up the content pane.
     addComponentsToPane(MainMenu.getContentPane());

     //Display the window.
     MainMenu.pack();
     MainMenu.setVisible(true);
 }

 //method to add the buttons to main menu
 public static void addComponentsToPane(Container pane) {
     pane.setLayout(new GridLayout(0,1));
     addAButton(ADD_CONTACT, pane);
     addAButton(SEARCH_CONTACTS, pane);
     addAButton(DISPLAY_CONTACTS, pane);
     addAButton(IMPORT_EXPORT, pane);
 }
 //method to create each button for main menu
 public static void addAButton(String text, Container container) {
     JButton button = new JButton(text);
     button.setAlignmentX(Component.LEFT_ALIGNMENT);
     button.addActionListener(new ActionListener(){
       //action listener for the main menu buttons that takes the correct action based on the text of the button
       public void actionPerformed(ActionEvent e){
         Component source = null;
         String srcAction = "";
         source = (JButton)(e.getSource());
         srcAction = ((JButton)source).getText();
         if (srcAction == ADD_CONTACT){
           addContact();
         }
         else if(srcAction == SEARCH_CONTACTS){
           contactSearch();
         }
         else if(srcAction == DISPLAY_CONTACTS){
           displayContacts();
         }
         else if(srcAction == IMPORT_EXPORT){
           importExportContacts();
         }
       }
     });
     container.add(button);
 }
 //Method to create window to enter new contact
 public static void addContact(){
   //Create addContactForm JPanel
   addContactForm addContact = new addContactForm();

   JFrame f = new JFrame("Add Contact");
   f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   f.getContentPane().add(addContact, BorderLayout.NORTH);


   JButton submit = new JButton("Add Contact");
   JPanel p = new JPanel();
   p.add(submit);
   submit.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       //create new contact using the text entered in add contact window
       contactRecord.add(new Contact(addContact.getText(0),addContact.getText(1),addContact.getText(2)));
       JOptionPane.showMessageDialog(f, "Contact Added");
       f.dispose();
   }
 });
  f.getContentPane().add(p, BorderLayout.SOUTH);
  f.pack();
  f.setVisible(true);
 }
 //Contact search method
 public static void contactSearch(){
  int resultCount = 0;
  int strLen;

  JFrame s = new JFrame("Search Results");
  s.setLayout(new GridLayout(0,1));
  s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  //Open window to enter text to search
  String searchText = JOptionPane.showInputDialog("Please enter the first letters of a contact's name to search for all contacts that start with those letters.");
  //catch if cancel is pressed in input box and exit method
  if (searchText == null){
    return;
  }
  //catch if nothing was entered
  else{
    strLen = searchText.length();
    if(strLen == 0){
      JOptionPane.showMessageDialog(s, "No letters entered.");
    }
   //catch if contact book is empty
   else if(contactRecord.size() == 0){
     JOptionPane.showMessageDialog(s, "Contact Book is Empty.");
   }
   else{
     //iterate over array and if the text matches the name of the contact being checked, create a button for it and add to search results JFrame
     for(int i = 0; i < contactRecord.size(); i++){
       final int arrayIndex = i;
       if(contactRecord.get(i).getName().startsWith(searchText) == true){
       JButton contactButton = new JButton(contactRecord.get(i).getName());
       contactButton.setAlignmentX(Component.LEFT_ALIGNMENT);

       contactButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        //action listener on button to call show contact details method on the correct contact
        showContactDetails(contactRecord.get(arrayIndex));
        s.dispose();
       }
       });
       s.getContentPane().add(contactButton);
       //increment result count to show match found
       resultCount++;
       }
     }
     //if no matches found, show pop up and close
     if (resultCount == 0){
       JOptionPane.showMessageDialog(s,"No results found.");
       s.dispose();
     }
     //if matches found
     else{
       //add return to main menu button to the bottom of the results JFrame
       JButton resultsClose = new JButton("Return to Menu");
       resultsClose.setAlignmentX(Component.LEFT_ALIGNMENT);
       resultsClose.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          s.dispose();
        }
       });
       //display results JFrame
       s.getContentPane().add(resultsClose);
       s.pack();
       s.setVisible(true);
    }
   }
  }

 }

 //method to display individual contact details and amend/delete if needed
 public static void showContactDetails(Contact a){
   //get contact information from contact object
   String name = a.getName();
   String number = a.getNumber();
   String email = a.getEmail();

   //create an  add contact form
   addContactForm showContact = new addContactForm();

   //prepopulate text fields of add contact form with the contact details currently in object
   showContact.fields[0].setText(name);
   showContact.fields[1].setText(number);
   showContact.fields[2].setText(email);


   JFrame c = new JFrame("Contact Details");
   c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   c.getContentPane().add(showContact, BorderLayout.NORTH);

   JPanel p = new JPanel();
   //create amend contact button
   JButton amend = new JButton("Update Contact");
   amend.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e) {
     //if amend contact button is pressed, set the contact's information to what is currently in textboxes and display notice of update
     a.setName(showContact.getText(0));
     a.setNumber(showContact.getText(1));
     a.setEmail(showContact.getText(2));
     JOptionPane.showMessageDialog(c, "Contact Amended");
     c.dispose();
   }
   });

   //create delete contact button
   JButton delete = new JButton("Delete Contact");
    delete.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e) {
       //if button is pressed, get index of the contact in contact array and remove from array and notify user
       int arrIndex = contactRecord.indexOf(a);
       System.out.println(arrIndex);
       contactRecord.remove(arrIndex);
       JOptionPane.showMessageDialog(c, "Contact Deleted");
       c.dispose();
   }
   });

   //create return to menu button to dispose window
   JButton close = new JButton("Return To Menu");
   close.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e) {
     c.dispose();
   }
   });

   //add all buttons and display jframe
   p.add(amend);
   p.add(delete);
   p.add(close);

   c.getContentPane().add(p, BorderLayout.SOUTH);
   c.pack();
   c.setVisible(true);

 }

 //method to display all contacts in contact array
 public static void displayContacts(){

   JFrame d = new JFrame("All Contacts");
   d.setLayout(new GridLayout(0,1));
   d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

   //if contact array is empty, notify user
   if (contactRecord.size() == 0){
    JOptionPane.showMessageDialog(d,"Empty Contact Book");
   }
   else{
     //else for each contact, create a button that calls the display/amend/delete contact method on that contact
     for(int i = 0; i < contactRecord.size(); i++){
       final int arrayIndex = i;
       JButton contactButton = new JButton(contactRecord.get(i).getName());
       contactButton.setAlignmentX(Component.LEFT_ALIGNMENT);
       contactButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         showContactDetails(contactRecord.get(arrayIndex));
         d.dispose();
       }
       });
       d.getContentPane().add(contactButton);
     }
     //add return to main menu button
     JButton resultsClose = new JButton("Return to Menu");
     resultsClose.setAlignmentX(Component.LEFT_ALIGNMENT);
     resultsClose.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        d.dispose();
      }
     });
     //display all contacts JFrame
   d.getContentPane().add(resultsClose);
   d.pack();
   d.setVisible(true);
  }
 }

 //Method to save contacts in contact array to file for retrieval later
 public static void importExportContacts(){
   //create array of button labels
   Object[] options = { "Import Contacts", "Export Contacts" };
   int input = JOptionPane.showOptionDialog(null, "Please select an option.", "Import/Export",
   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
   null, options, options[0]);
   //if import contacts pressed
   if(input == 0){
     try{
       //create input stream objects
       FileInputStream fis = new FileInputStream("contacts.tmp");
       ObjectInputStream ois = new ObjectInputStream(fis);
       //read object from stream
       Object obj = ois.readObject();
       //create generic array using casting
       ArrayList<?> arr = (ArrayList<?>)obj;
       //for each item in generic array, add item to contact array, casting each as a Contact object,
       //allowing contacts to be imported when there are contacts in the contact array already
       for(int i = 0; i < arr.size() ; i++){
         contactRecord.add((Contact) arr.get(i));
       }

     } catch(Exception e){
       JOptionPane.showMessageDialog(null,"Read Error");
      }
     //else if export is selected
   } else{
     try{
      //create output streams and serialize the current contact array to file
      FileOutputStream fos = new FileOutputStream("contacts.tmp");
      ObjectOutputStream oos= new ObjectOutputStream(fos);
      oos.writeObject(contactRecord);
     } catch(Exception e){
       JOptionPane.showMessageDialog(null,"Write Error");
      }
   }
 }

 //Main method to invoke the create main menu gui method
 public static void main(String[] args) {
  SwingUtilities.invokeLater(new Runnable(){
    public void run(){
    createGUI();
   }
 });
 }


}
