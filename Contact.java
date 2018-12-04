import java.io.IOException;

public class Contact implements java.io.Serializable{

 //ID for serializing class in import/export method in main class
 private static final long serialVersionUID = 7526472295622776147L;

 //Variables to store contact information
 public String ContactName;
 public String ContactNo;
 public String ContactEmail;

 //Constructor for class
 Contact(String name, String number, String email){

  ContactName = name;
  ContactNo = number;
  ContactEmail = email;

 }

 // Get & Set methods for each variable
 public String getName(){

   return ContactName;

 }

 public String getNumber(){

   return ContactNo;

 }

 public String getEmail(){

   return ContactEmail;

 }

 public void setName(String nameIn){
   ContactName = nameIn;
 }

 public void setNumber(String numberIn){
   ContactNo = numberIn;
 }

 public void setEmail(String emailIn){
   ContactEmail = emailIn;
 }




}
