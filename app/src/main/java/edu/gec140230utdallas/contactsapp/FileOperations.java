package edu.gec140230utdallas.contactsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * THIS CLASS TAKES CARE OF THE CONTACTS DATABASE THAT IS STORED IN INTERNAL STORAGE
 * IT UPDATES THE DATABASE EACH TIME SAVECHANGES IS CALLED, AND IT LOADS THE CHANGES INTO MEMORY EACH
 * TIME LOAD CHANGE IS CALLED
 */

public class FileOperations
{

    MainScreen ms;
    private String filePath = "contacts.txt";
    Context thisContext;
    SQLiteDatabase db;

    String createTable =
            "CREATE TABLE IF NOT EXISTS Contacts (" +
                    "ContactID int AUTO_INCREMENT PRIMARY_KEY, " +
                    "LastName varchar(100) NOT NULL, FirstName varchar(100) NOT NULL, " +
                    "MetDate Date NOT NULL, BirthDate date," +
                    "phoneNumber varchar(10) NOT NULL, middleInitial char(1), addressOne varchar(100), addressTwo varchar(100), city varchar(20), state varchar(20), zipCode varchar(5));";

    FileOperations(MainScreen callingScreen, Context context)
    {
        ms = callingScreen;
        thisContext = context;
    }
    public void loadTfile(){
        //if the file existed already
        FileInputStream contactsInfo = null;
        try {
            contactsInfo = thisContext.openFileInput(filePath);
            //eventually, it needs to check whether the file has any data in it
            if (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(contactsInfo));
                //StringBuffer buff = new StringBuffer();

                String thisLine;
                Contact thisContact;
                String[] tokens;

                while ((thisLine = br.readLine()) != null) {
                    tokens = thisLine.split("\\t");
                    thisContact = new Contact(tokens[0], tokens[1], tokens[2], tokens[3]);

                    if (!tokens[4].equals("NULL")) {
                        thisContact.setmName(tokens[4]);
                    }

                    if (!tokens[5].equals("NULL")) {
                        thisContact.setbDate(tokens[5]);
                    }


                    MainScreen.contacts.add(thisContact);
                    MainScreen.namesInList.add(thisContact.getLName() + ", " + thisContact.getFName() + " " + thisContact.getMiddle(""));
                }

            }
                } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveChanges() {
        db = thisContext.openOrCreateDatabase(filePath, Context.MODE_PRIVATE, null);
        String drop = "DROP TABLE IF EXISTS CONTACTS";
        db.execSQL(drop);
        db.execSQL(createTable);


        for (int i = 0; i < MainScreen.contacts.size(); i++) {
            String thisCmd = "INSERT into Contacts " +
                    "(LastName, FirstName, MetDate, BirthDate, phoneNumber, middleInitial, addressOne, addressTwo, city, state, zipCode) VALUES "+
                    "('" + MainScreen.contacts.get(i).getLName() + "','" + MainScreen.contacts.get(i).getFName()
                    +"','" + MainScreen.contacts.get(i).getMetDate() +"','" + MainScreen.contacts.get(i).getBDate("NULL")
                    +"','" + MainScreen.contacts.get(i).getPNumber() + "','" + MainScreen.contacts.get(i).getMiddle("NULL")
                    +"','" + MainScreen.contacts.get(i).getAddressOne("NULL") + "','" + MainScreen.contacts.get(i).getAddressTwo("NULL")
                    +"','" + MainScreen.contacts.get(i).getCity("NULL") + "','" + MainScreen.contacts.get(i).getState("NULL")
                    +"','" + MainScreen.contacts.get(i).getZipCode("NULL") +"');";
                db.execSQL(thisCmd);
        }

        db.close();
    }

    public void loadChanges() {

        db = thisContext.openOrCreateDatabase(filePath, Context.MODE_PRIVATE, null);
        db.execSQL(createTable);

        Cursor cs = db.rawQuery("SELECT * FROM CONTACTS ", null);

        if (cs.getCount() > 0) {

            cs.moveToFirst();


            do {
                String lname = cs.getString(cs.getColumnIndex("LastName"));
                String fname = cs.getString(cs.getColumnIndex("FirstName"));
                String mDate = cs.getString(cs.getColumnIndex("MetDate"));
                String bdate = cs.getString(cs.getColumnIndex("BirthDate"));
                String pNumb = cs.getString(cs.getColumnIndex("phoneNumber"));
                String middle = cs.getString(cs.getColumnIndex("middleInitial"));
                String addOne = cs.getString(cs.getColumnIndex("addressOne"));
                String addTwo = cs.getString(cs.getColumnIndex("addressTwo"));
                String city = cs.getString(cs.getColumnIndex("city"));
                String state = cs.getString(cs.getColumnIndex("state"));
                String zip = cs.getString(cs.getColumnIndex("zipCode"));

                Contact thisContact = new Contact(fname, lname, pNumb, mDate);
                if (!bdate.equals("NULL")) thisContact.setbDate(bdate);
                if (!middle.equals("NULL")) thisContact.setmName(middle);
                if (!addOne.equals("NULL")) thisContact.setAddressOne(addOne);
                if (!addTwo.equals("NULL")) thisContact.setAddressTwo(addTwo);
                if (!city.equals("NULL")) thisContact.setCity(city);
                if(!state.equals("NULL")) thisContact.setState(state);
                if(!zip.equals("NULL")) thisContact.setZipCode(zip);


                ms.numContacts++;
                MainScreen.namesInList.add(lname + ", " + fname + " " + thisContact.getMiddle(""));
                MainScreen.contacts.add(thisContact);
            }
            while (cs.moveToNext());

            cs.close();
            db.close();

        }
    }
}
