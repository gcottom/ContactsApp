package edu.gec140230utdallas.contactsapp;
//THIS JAVA FILE SIMPLY DISPLAYS THE CURRENT INFORMATION FOR THE SELECTED CONTACT
//AND HAS BUTTONS THAT ALLOW YOU TO EDIT OR DELETE, AS WELL AS GO BACK
//Gage Cottom

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import edu.gec140230utdallas.contactsapp.R;

public class ContactView extends AppCompatActivity {
    static int pos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Intent intent = getIntent();

        final int position = intent.getIntExtra("Position", 0);
        final Contact thisContact = MainScreen.contacts.get(position);

        TextView fname = (TextView) findViewById(R.id.textView);
        fname.setText(thisContact.getFName());
        TextView lname = (TextView) findViewById(R.id.textView2);
        lname.setText(thisContact.getLName());
        TextView mname = (TextView) findViewById(R.id.textView3);
        mname.setText(thisContact.getMiddle("Unknown"));
        TextView phone = (TextView) findViewById(R.id.textView4);
        phone.setText(thisContact.getPNumber());
        TextView bday = (TextView) findViewById(R.id.textView5);
        bday.setText(thisContact.getBDate("Unknown"));
        TextView mday = (TextView) findViewById(R.id.textView19);
        mday.setText(thisContact.getMetDate());
        TextView addressOne = (TextView) findViewById(R.id.textView6);
        addressOne.setText(thisContact.getAddressOne("Unknown"));
        TextView addressTwo = (TextView) findViewById(R.id.textView27);
        addressTwo.setText(thisContact.getAddressTwo("Unknown"));
        TextView city = (TextView) findViewById(R.id.textView29);
        city.setText(thisContact.getCity("Unknown"));
        TextView state = (TextView) findViewById(R.id.textView31);
        state.setText(thisContact.getState("?"));
        TextView zipCode = (TextView) findViewById(R.id.textView33);
        zipCode.setText(thisContact.getZipCode("Unknown"));
        pos = intent.getIntExtra("pos", 0);
        Log.d("Info", Integer.toString(pos));



        //SET UP LISTENERS FOR BOTH THE DELETE AND EDIT BUTTONS, THAT PASS THROUGH THE CURRENT CONTACT\\
        Button deleteButton = (Button) findViewById(R.id.delete);
        View.OnClickListener delListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(position);
            }
        };
        deleteButton.setOnClickListener(delListener);

        Button editButton = (Button) findViewById(R.id.edit);
        View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(position);
            }
        };
        editButton.setOnClickListener(editListener);

        //SET UP LISTENERS FOR BOTH THE SAVE BUTTON, THAT PASS THROUGH THE CURRENT CONTACT\\
        Button cancel = (Button) findViewById(R.id.cancel);
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        };
        cancel.setOnClickListener(backListener);


    }

    //Gage Cottom
    //Delete a contact from the list
    public void delete(int pos) {
        MainScreen.contacts.remove(pos);

        //check if that was the only contact, and if so, delete the file
        //otherwise, MainScreen will think that the app is just now starting up
        //and load back in the deleted contact

        if (MainScreen.contacts.size() == 0) {
            File file = new File(getFilesDir(), "contacts.txt");
            boolean success = file.delete();
        }

        //delete from the list
        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        startActivity(intent);

    }

    public void edit(int pos) {
        Intent intent = new Intent();

        intent.setClass(this, EditContact.class);
        intent.putExtra("Position", pos);
        //tell the new Activity that this isn't a brand new contact, we're editing
        intent.putExtra("newContact", false);

        MainScreen.namesInList.remove(pos);
        startActivity(intent);
    }

    public void back() {
        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        startActivity(intent);
    }
}
