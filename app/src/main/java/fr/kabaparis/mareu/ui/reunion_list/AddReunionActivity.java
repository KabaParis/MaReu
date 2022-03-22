package fr.kabaparis.mareu.ui.reunion_list;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.util.Patterns;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.Subscribe;

import java.time.Year;
import java.util.Calendar;
import java.util.Objects;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.databinding.ActivityReunionBinding;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView mRoomName;
    private TimePicker mReunionTime;
    private DatePicker mReunionDate;
    private TextView mReunionSubject;
    private ImageView mRoomColour;
    private TextInputEditText mAttendeesNames;
    private TextInputLayout mAttendees;
    private ImageButton mAddButton;
    private MaterialButton createReunion;

    private ReunionApiService mApiService;
    private Reunion reunion;
    private Spinner spinner;
    private EditText editText;
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private Chip chipEntry;
    private ChipGroup chipGroup;

    private Button buttonClear;



    private static final String LOG_TAG = "AndroidChipDemo";
    private String text;

    /**
     * Used to navigate to this activity
     *
     * @param activity
     */
    public static void navigate(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        setTitle(getString(R.string.room_name));
        init();
    }



        public void init() {

            // get the views identified by their attributes
            mRoomName = findViewById(R.id.roomName);
            mReunionTime = findViewById(R.id.reunionTimePicker);
            mReunionDate = findViewById(R.id.reunionDatePicker);
            mReunionSubject = findViewById(R.id.reunionSubject);
            mAttendeesNames = findViewById(R.id.attendeesNames);
            mAttendees = findViewById(R.id.attendees);
            mAddButton = findViewById(R.id.add_reunion);
            mRoomColour = findViewById(R.id.room_colour);
            createReunion = findViewById(R.id.createReunion);


            // get reunion id by the api service
            mApiService = DI.getReunionApiService();


            // create spinner
            spinner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rooms, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);


            // add attendees with default chip
            this.chipEntry = (Chip) this.findViewById(R.id.chipEntry);
            this.chipGroup = this.findViewById(R.id.attendeesMails);
            this.chipEntry.setOnClickListener(new View.OnClickListener() {


                // add attendees with chip group
                @Override
                public void onClick(View v) {
                    String mail = mAttendeesNames.getEditableText().toString();
                    Log.d("CHIPS", mail);

                    if (Patterns.EMAIL_ADDRESS.matcher(mail).matches() == true) {
                        Chip chip = new Chip(AddReunionActivity.this);

                        chipEntry.setCheckable(false);
                        chipEntry.setText(mail);
                        chipEntry.setCloseIconVisible(true);
                        chipEntry.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                chipGroup.removeView(chipEntry);
                            }
                        });

                        chipGroup.addView(chipEntry);
                        mAttendeesNames.setText("");
                        mAttendees.setError(null);
                    } else {
                        mAttendees.setError("merci de saisir une adresse mail");
                    }
                }
            });


        //     findViewById(R.id.attendeesNames).checkInputConnectionProxy(chipEntry);
        //                chipGroup.addView(chipEntry);


            // add information to meeting creation
            this.createReunion.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.d("click", "JE PASSE SUR LE CLICK");
                    mRoomName :  Log.d("Room name", mRoomName.getText().toString());
                    mReunionSubject : Log.d("Reunion subject", mReunionSubject.getText().toString());
             //       textInputLayout = findViewById((R.id.reunionSubject));

                    // if (editText.getText().toString().equals(""))
                 //   if (textInputEditText.getText().toString().isEmpty()) {
                        Reunion reunion = new Reunion(
                                System.currentTimeMillis(),
                                mRoomName.getText().toString(),
                                mRoomColour,
                                mReunionTime,
                                mReunionDate,
                                mReunionSubject.getText().toString(),
                                mAttendeesNames.getEditableText().toString());
                    }
        //        }

            });

            this.mApiService.createReunion(reunion);

        }
        public void setmReunionSubject (View view) {
        Log.d("reunion subject","successful");
        }

         public void setmRoomName (View view) {
        Log.d("room name","room selected");
         }
    /*       {

               mReunionSubject.setError("merci de remplir ce champ");
               textInputEditText.setError("merci de remplir ce champ");
            }
    */



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class TimePickerDemoActivity extends Activity implements
            TimePicker.OnTimeChangedListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_reunion);

            TimePicker picker = (TimePicker) findViewById(R.id.reunionTimePicker);

            picker.setOnTimeChangedListener(this);
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            Calendar then = Calendar.getInstance();

            then.set(Calendar.HOUR_OF_DAY, hourOfDay);
            then.set(Calendar.MINUTE, minute);
            then.set(Calendar.SECOND, 0);

            Toast.makeText(this, then.getTime().toString(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public class DatePickerDemoActivity extends Activity implements
            DatePicker.OnDateChangedListener {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_reunion);

            DatePicker picker = (DatePicker) findViewById(R.id.reunionDatePicker);

            picker.setOnDateChangedListener(this);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            Calendar then = Calendar.getInstance();

            then.set(Calendar.YEAR, year);
            then.set(Calendar.MONTH, month);
            then.set(Calendar.DAY_OF_WEEK, day);

            Toast.makeText(this, then.getTime().toString(), Toast.LENGTH_SHORT)
                    .show();
        }
    }



 /*    private void appendLog(String text)
        this.editTextLog.append(text);
        ;
        this.editTextLog.append("\n");
        Log.i(LOG_TAG, text);

        class  ClickListenerImpl implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                appendLog("Clicked");
            }

            private void appendLog(String clicked) {
            }
        }


        class CloseIconClickListenerImpl implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                appendLog("Close Icon Clicked");
            }

            private void appendLog(String close_icon_clicked) {
            }
        }

        class CheckedChangeListenerImpl implements  CompoundButton.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appendLog("Checked Changed! isChecked? " + isChecked);
            }

            private void appendLog(String s) {
            }
        }

*/

}