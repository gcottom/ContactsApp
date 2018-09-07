package edu.gec140230utdallas.contactsapp;
//THIS IS WHERE ALL THE MAGIC HAPPENS
//AKA THE MAIN SCREEN
//ONCREATE SAVES THE CURRENT MEMORY STATE OF CONTACTS TO THE FILE
//AND THEN RELOADS THE FILE
//ENSURING UPDATED INFORMATION AT ALL TIMES
//Gage Cottom

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.gec140230utdallas.contactsapp.R;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static List<String> namesInList = new ArrayList<String>();
    //this is what actually stores all the contacts in memory (below)
    public static List<Contact> contacts = new ArrayList<Contact>();
    public int numContacts = 0;

    public static boolean firstTime = true;
    public static boolean back = false;

    FileOperations fo;
    ListView list;
    ArrayAdapter<String> adapter;

    SensorClass sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        /////this will create the array adapter which links namesInList to the ListView\\\\\
        list = (ListView) findViewById(R.id.contactsList);

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
        {
            Log.d("PROBLEM", "Couldn't access storage.");
        }

        sensor = new SensorClass(this, this);
        fo = new FileOperations(this, this);


        //SET UP LISTENERS FOR BOTH THE DELETE AND EDIT BUTTONS, THAT PASS THROUGH THE CURRENT CONTACT\\
        Button addButton = (Button)findViewById(R.id.add);
        Button clearButton = (Button)findViewById(R.id.clear);
        Button importButton = (Button)findViewById(R.id.importButton);
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        };
        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        };
        View.OnClickListener importListener = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                importContacts();
            }
        };
        addButton.setOnClickListener(addListener);
        clearButton.setOnClickListener(clearListener);
        importButton.setOnClickListener(importListener);


        //NOW WE LOAD THE CONTACT INFO FROM THE FILE TO MEMORY
        loadContactsFromFile();
        if(firstTime){
            sort();
            firstTime = false;
        }
        //NOW WE DISPLAY THE LIST OF NAMES\\
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesInList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        }
    public static void delete(int pos){
        contacts.remove(pos);

    }
        //Listens for clicks on the list
    public void onItemClick(AdapterView<?> l, View v, int position, long id){
        Intent intent = new Intent();
        intent.setClass(this, ContactView.class);
        Contact c = contacts.get(position);
        Bundle b = new Bundle();
        //sends selected contact info to next activity
        intent.putExtra("Contact", c);
        intent.putExtra("Position", position);
        intent.putExtras(b);
        startActivity(intent);
    }

    ///THIS ALLOWS THE USER TO EASILY CLEAR ALL THE CONTACTS HE/SHE HAS MADE.
    ///EVENTUALLY WE SHOULD CREATE AN "ARE YOU SURE" DIALOG BOX FOR THIS
    ///BUT MOSTLY I IMPLEMENTED IT FOR TESTING REASONS FOR NOW
    public void clear()
    {
        namesInList.clear();
        contacts.clear();
        numContacts = 0;
        adapter.notifyDataSetInvalidated();

    }
    ///THIS LOADS THE CONTACTS INTO MEMORY FROM THE TEXT FILE\\\\
    public void loadContactsFromFile()
    {
        //first we need to check if there's any new info we need to save
        if(!contacts.isEmpty())
        {
            fo.saveChanges();
        }

        //now we clear everything out and reload the file
        contacts.clear();
        namesInList.clear();
        fo.loadChanges();

         //   sort();
    }
    public void importContacts(){
        fo.loadTfile();
        fo.saveChanges();
    }
    //Gage Cottom
    public void add(){
        Intent intent = new Intent();
        intent.setClass(this, EditContact.class);
        intent.putExtra("newContact", true);
        startActivity(intent);
    }

    public void onShake()
    {
        reverseSort();
        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        startActivity(intent);
    }


    //Gage Cottom
    //alphabetizes lists using bubble sort
    public static void sort() {
        for (int n = 0; n < contacts.size(); n++) {
            for (int m = 0; m < (contacts.size()-1) - n; m++) {
                if ((namesInList.get(m).compareTo(namesInList.get(m + 1))) > 0) {
                    String swap = namesInList.get(m);
                    namesInList.set(m, namesInList.get(m+1));
                    namesInList.set(m+1, swap);
                    Contact swapC = contacts.get(m);
                    contacts.set(m, contacts.get(m+1));
                    contacts.set(m+1, swapC);
                }
            }
        }
    }
    //Gage Cottom
    public static void reverseSort(){
    //sort arrays in reverse of what they currently are sorted
        List<Contact> tempc = new ArrayList<>();
        List<String> temps = new ArrayList<>();
        int count;
        for(count = 0; count<contacts.size(); count++){
            tempc.add(contacts.get((contacts.size()-count)-1));
        }
        contacts.clear();
        for(count = 0; count<tempc.size(); count++){
            contacts.add(tempc.get(count));
        }
        for(count = 0; count<namesInList.size(); count++){
            temps.add(namesInList.get((namesInList.size()-count)-1));
        }
        namesInList.clear();
        for(count = 0; count<temps.size(); count++){
            namesInList.add(temps.get(count));
        }
    }
    //Gage Cottom
    protected void onPause() {
        super.onPause();
        sensor.unregister();
    }
    //Gage Cottom
    //handles sensors on program resume
    protected void onResume() {
        super.onResume();
        sensor.register();
    }

}
