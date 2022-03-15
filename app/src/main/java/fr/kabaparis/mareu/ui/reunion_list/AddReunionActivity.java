package fr.kabaparis.mareu.ui.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView mRoomName;
    private TextView mReunionTime;
    private TextView mReunionSubject;
    private ImageView mRoomColour;
    private TextInputEditText mAttendeesNames;
    private ImageButton mAddButton;

    private ReunionApiService mApiService;
    private Reunion mReunion;
    private Spinner spinner;
    private EditText editText;
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private Chip chipEntry;
    private ChipGroup chipGroup;

    private Button buttonClear;
    private MaterialButton createReunion;


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
        public void init()
        {

        // get the views identified by their attributes
        mRoomName = findViewById(R.id.roomName);
     // mReunionTime = findViewById(R.id.reunionTime);
        mReunionSubject = findViewById(R.id.reunionSubject);
        mAttendeesNames = findViewById(R.id.attendeesNames);
        mAddButton = findViewById(R.id.add_reunion);
        mRoomColour = findViewById(R.id.room_colour);
        createReunion = findViewById(R.id.createReunion);


            // add information to meeting creation
            this.createReunion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Reunion reunion = new Reunion(
                            System.currentTimeMillis(),
                            mRoomName.getText().toString(),
                            mRoomColour,
                            mReunionTime.getEditableText().toString(),
                            mReunionSubject.getEditableText().toString(),
                            mAttendeesNames.getEditableText().toString();

                }


            });




        // get reunion id by the api service
        mApiService = DI.getReunionApiService();


        // create spinner
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener( this);





        // add attendees with default chip
        this.chipEntry = (Chip) this.findViewById(R.id.chipEntry);
        this.chipGroup = this.findViewById(R.id.attendeesMails);
        this.chipEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


        // add attendees with chip group

            Chip chip = new Chip(AddReunionActivity.this);
            chip.setCheckable(false);
            chip.setText(mAttendeesNames.getEditableText());
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chipGroup.removeView(chip);
                }

            });
               // findViewById(R.id.attendeesNames).checkInputConnectionProxy(chip);
                chipGroup.addView(chip);

            // clickListener
                switch (v.getId()) {
                    case R.id.chipEntry:
                        Toast.makeText(AddReunionActivity.this, "Validé", Toast.LENGTH_SHORT).show();
                        break;
                }
       //    findViewById().chipGroup.addView(chip);
        }

        });


        // create error message

        editText = findViewById((R.id.reunionSubject));
        if (editText.getText().toString().isEmpty())
       // if (editText.getText().toString().equals(""))
        {
           editText.setError("merci de remplir ce champ");
       //   textInputEditText.setError("merci de remplir ce champ");
        }

    }




   //  View parent = (View)findViewById(R.id.createReunion).getParent();



  /*     private void appendLog(String text)
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



}