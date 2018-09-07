package edu.gec140230utdallas.contactsapp;

//THIS IS THE CONTACT CLASS
//IT CONTAINS EVERY PIECE OF INFORMATION FOR EACH CONTACT
//AND EVEN HAS A METHOD TO EASILY GET THE FILE-FRIENDLY LINE



import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Gage Cottom 2018
 */

/////THIS IS THE CLASS FOR ALL CONTACTS\\\\\\
public class Contact implements Serializable
{
    private String fName;
    private String lName;
    private String mName = "NULL";
    private String pNumber;
    private String bDate = "NULL";
    private String metDate;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String state;
    private String zipCode;

    private boolean hasBDate = false;
    private boolean hasMiddle = false;
    private boolean hasAddressOne = false;
    private boolean hasAddressTwo = false;
    private boolean hasCity = false;
    private boolean hasState = false;
    private boolean hasZip = false;

    //Constructor
    Contact(String first, String last, String phone, String met)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = new Date();

        this.fName = first;
        this.lName = last;
        this.metDate = met;
        this. pNumber = phone;
    }

    //special function to write this specific contact as a line in the text file
    public String getFileLine()
    {
        return this.fName + "\t" + this.lName + "\t" + this.pNumber + "\t" + this.metDate + "\t" +  this.mName + "\t" + this.bDate + "\n";
    }

    //GETTERS\\
    public String getFName()
    {
        return this.fName;
    }

    public String getLName()
    {
        return this.lName;
    }

    public String getPNumber()
    {
        return this.pNumber;
    }

    public String getMetDate()
    {
        return this.metDate;
    }

    public String getMiddle(String defaultReturn)
    {
        if(this.hasMiddle)
            return this.mName;
        else return defaultReturn;
    }

    public String getBDate(String defaultReturn)
    {
        if(this.hasBDate)
            return this.bDate;
        else return defaultReturn;
    }
    public String getAddressOne(String defaultReturn) {
        if(this.hasAddressOne)
            return this.addressOne;
        else return defaultReturn;
    }
    public String getAddressTwo(String defaultReturn) {
        if(this.hasAddressTwo)
            return this.addressTwo;
        else return defaultReturn;
    }
    public String getCity(String defaultReturn) {
        if(this.hasCity)
            return this.city;
        else return defaultReturn;
    }
    public String getState(String defaultReturn) {
        if(this.hasState)
            return this.state;
        else return defaultReturn;
    }
    public String getZipCode(String defaultReturn) {
        if(this.hasZip)
            return this.zipCode;
        else return defaultReturn;
    }

    public void setFName(String name) { this.fName = name; }
    public void setLName(String name) { this.lName = name; }
    public void setmName(String name) { this.hasMiddle = true; this.mName = name; }
    public void setpNumber(String pnumb) {this.pNumber = pnumb; }
    public void setMet(String met) {this.metDate = met; }
    public void setbDate(String birth) {this.hasBDate = true;  this.bDate = birth; }
    public void setAddressOne(String address) {this.hasAddressOne = true; this.addressOne = address; }
    public void setAddressTwo(String address) {this.hasAddressTwo = true; this.addressTwo = address; }
    public void setCity(String city) {this.hasCity = true; this.city = city; }
    public void setState(String state) {this.hasState = true; this.state = state; }
    public void setZipCode(String zip) {this.hasZip = true; this.zipCode = zip; }
}