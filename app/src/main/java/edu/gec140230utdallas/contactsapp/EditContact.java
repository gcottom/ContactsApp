package edu.gec140230utdallas.contactsapp;
///THIS ACTIVITY IS FOR EITHER EDITING AN EXISTING CONTACT OR CREATING A NEW ONE
///IT POPS UP CALENDAR DIALOGS FOR DATE FIELDS, AND A PHONE NUMBER ENTRY FOR PHONE FIELD
//Gage Cottom

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import edu.gec140230utdallas.contactsapp.R;

public class EditContact extends AppCompatActivity {
    EditText fname;
    EditText lname;
    EditText mname;
    EditText phone;
    EditText bday;
    EditText mdate;
    EditText addressOne;
    EditText addressTwo;
    EditText city;
    EditText state;
    EditText zipCode;

    ///set up text watcher so that user can't save blank contacts (disables save button unless certain things occur)
    TextWatcher tw = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkButton();
        }
    };

    //this is called anytime the text changes in fname, lname or phone
    void checkButton()
    {
        Button save = (Button)findViewById(R.id.save);
        if((fname.getText().length() > 0) && (lname.getText().length() > 0) && (phone.getText().length() == 10))
        {
            save.setEnabled(true);
        }
        else save.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Intent intent = getIntent();
        final boolean isNewContact = intent.getExtras().getBoolean("newContact");

        fname = (EditText) findViewById(R.id.fName);
        lname = (EditText) findViewById(R.id.lName);
        mname = (EditText) findViewById(R.id.mName);
        phone = (EditText) findViewById(R.id.phoneNumber);
        bday = (EditText) findViewById(R.id.birthDate);
        mdate = (EditText) findViewById(R.id.metDate);
        addressOne = (EditText) findViewById(R.id.address);
        addressTwo = (EditText) findViewById(R.id.address2);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zipCode= (EditText) findViewById(R.id.zipcode);
        Button save = (Button)findViewById(R.id.save);

        fname.addTextChangedListener(tw);
        lname.addTextChangedListener(tw);
        phone.addTextChangedListener(tw);

        bday.setFocusable(false);
        bday.setClickable(true);
        mdate.setFocusable(false);
        mdate.setClickable(true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        if(isNewContact) {
            mdate.setText((month+1) + "/" + day + "/" + year);
            save.setEnabled(false);
        }

        //SET UP THE CALENDAR DIALOG BOXES\\
        final DatePickerDialog.OnDateSetListener bdayDialogListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                bday.setText((i1+1) + "/" + i2 + "/" + i);
            }
        };

        //SET UP LISTENERS FOR BOTH THE SAVE BUTTON, THAT PASS THROUGH THE CURRENT CONTACT\\
        View.OnClickListener bdayListen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog picker1 = new DatePickerDialog(EditContact.this, android.R.style.Theme_Holo, bdayDialogListener, year, month, day);
                picker1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                picker1.show();
            }
        };
        bday.setOnClickListener(bdayListen);

        final DatePickerDialog.OnDateSetListener mDayDialogListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mdate.setText((i1+1) + "/" + i2 + "/" + i);
            }
        };

        //SET UP LISTENERS FOR BOTH THE SAVE BUTTON, THAT PASS THROUGH THE CURRENT CONTACT\\
        View.OnClickListener mdayListen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog picker2 = new DatePickerDialog(EditContact.this, android.R.style.Theme_Holo , mDayDialogListener, year, month, day);
                picker2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                picker2.show();
            }
        };
        mdate.setOnClickListener(mdayListen);

        //IF THIS ISN'T A NEW CONTACT, POPULATE ALL THE FIELDS REGARDING IT
        if(!isNewContact) {

            final int position = intent.getIntExtra("Position",0);
            final Contact thisContact = MainScreen.contacts.get(position);
            boolean exists = MainScreen.contacts.contains(thisContact);


            if (fname.getText().toString() != null) {
                fname.setText(thisContact.getFName());
            }

            if (lname.getText().toString() != null) {
                lname.setText(thisContact.getLName());
            }

            if (mname.getText().toString() != null) {
                mname.setText(thisContact.getMiddle(""));
            }

            if (phone.getText().toString() != null) {
                phone.setText(thisContact.getPNumber());
            }

            if (bday.getText().toString() != null) {
                bday.setText(thisContact.getBDate(""));
            }

            if (mdate.getText().toString() != null) {
                mdate.setText(thisContact.getMetDate());
            }
            if (addressOne.getText().toString() != null) {
                addressOne.setText(thisContact.getAddressOne(""));
            }
            if (addressTwo.getText().toString() != null) {
                addressTwo.setText(thisContact.getAddressTwo(""));
            }
            if (city.getText().toString() != null){
                city.setText(thisContact.getCity(""));
            }
            if (state.getText().toString() != null) {
                state.setText(thisContact.getState(""));
            }
            if (zipCode.getText().toString() != null) {
                zipCode.setText(thisContact.getZipCode(""));
            }

            //SET UP LISTENERS FOR BOTH THE SAVE BUTTON, THAT PASS THROUGH THE CURRENT CONTACT\\
            View.OnClickListener saveListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit(position);
                }
            };
            save.setOnClickListener(saveListener);
        }
        else
        {
            View.OnClickListener saveListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveNew();
                }
            };
            save.setOnClickListener(saveListener);
        }

        //SET UP LISTENERS FOR BOTH THE SAVE & CANCEL BUTTONS, THAT PASS THROUGH THE CURRENT CONTACT\\
        Button cancel = (Button)findViewById(R.id.cancel);
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        };
        cancel.setOnClickListener(backListener);
    }

    //this method saves a new contact
    public void saveNew()
    {
        String newFName = fname.getText().toString();
        String newLName = lname.getText().toString();
        String newPhone = phone.getText().toString();
        String newMiddle = mname.getText().toString();
        String newBirth = bday.getText().toString();
        String newMDate = mdate.getText().toString();
        String newAddress = addressOne.getText().toString();
        String newAddressTwo = addressTwo.getText().toString();
        String newCity = city.getText().toString();
        String newState = state.getText().toString();
        String newZip = zipCode.getText().toString();


        Contact newContact = new Contact(newFName, newLName, newPhone, newMDate);

        if(!newMiddle.equals("")) {
            newContact.setmName(newMiddle);
        }
        if(!newBirth.equals("")) {
            newContact.setbDate(newBirth);
        }
        if(!newAddress.equals(null)) {
            newContact.setAddressOne(newAddress);
        }
        if(!newAddressTwo.equals(null)) {
            newContact.setAddressTwo(newAddressTwo);
        }
        if(!newCity.equals(null)) {
            newContact.setCity(newCity);
        }
        if(!newState.equals(null)) {
            newContact.setState(newState);
        }
        if(!newZip.equals(null)) {
            newContact.setZipCode(newZip);
        }

        MainScreen.contacts.add(newContact);
        MainScreen.firstTime = true;
        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        startActivity(intent);
    }

    //this method simply edits an existing contact
    public void edit(int pos) {

        String newFName = fname.getText().toString();
        String newLName = lname.getText().toString();
        String newPhone = phone.getText().toString();
        String newMiddle = mname.getText().toString();
        String newBirth = bday.getText().toString();
        String newMDate = mdate.getText().toString();

        MainScreen.contacts.get(pos).setFName(newFName);
        MainScreen.contacts.get(pos).setLName(newLName);
        MainScreen.contacts.get(pos).setpNumber(newPhone);
        MainScreen.contacts.get(pos).setMet(newMDate);

        if(!newMiddle.equals("")) {
            MainScreen.contacts.get(pos).setmName(newMiddle);
        }

        if(!newBirth.equals("")) {
            MainScreen.contacts.get(pos).setbDate(newBirth);
        }

        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        MainScreen.firstTime = true;
        startActivity(intent);
    }

    //go back without saving changes
    public void back()
    {
        Intent intent = new Intent();
        intent.setClass(this, MainScreen.class);
        MainScreen.back = true;
        startActivity(intent);
    }
}
