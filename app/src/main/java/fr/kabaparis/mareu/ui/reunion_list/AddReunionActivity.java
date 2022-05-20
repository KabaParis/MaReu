package fr.kabaparis.mareu.ui.reunion_list;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
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
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.theme.MaterialComponentsViewInflater;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.jar.Attributes;

import javax.security.auth.Subject;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.databinding.ActivityReunionBinding;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.service.DummyColourGenerator;
import fr.kabaparis.mareu.service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TimePicker mReunionTime;
    private DatePicker mReunionDate;
    private TextView mReunionSubject;
    private TextInputEditText mAttendeesNames;
    private TextInputLayout mAttendees;
    private ImageButton mAddButton;
    private MaterialButton createReunion;
    private TextInputLayout subject;
    private ChipGroup mAttendeesMails;

    private ReunionApiService mApiService;
    private Spinner spinner;
    private EditText editText;
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private Button chipEntry;
    private ChipGroup chipGroup;

    private Button buttonClear;


    private static final String LOG_TAG = "AndroidChipDemo";
    private String text;
    private String msg;

    /**
     * Used to navigate to this activity
     *
     * @params activity
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

    public void init() {

        // get the views identified by their attributes

        mReunionTime = findViewById(R.id.reunionTimePicker);
        mReunionDate = findViewById(R.id.reunionDatePicker);
        mReunionSubject = findViewById(R.id.reunionSubject);
        mAttendeesNames = findViewById(R.id.attendeesNames);
        mAttendees = findViewById(R.id.attendees);
        mAddButton = findViewById(R.id.add_reunion);
        createReunion = findViewById(R.id.createReunion);
        subject = findViewById(R.id.subject);
        mAttendeesMails = findViewById(R.id.attendeesMails);


        // get reunion id by the api service
        mApiService = DI.getReunionApiService();

        Calendar currentDate = Calendar.getInstance();
        mReunionDate.init(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH),
                null

        );

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

                    chip.setCheckable(false);
                    chip.setText(mail);
                    chip.setCloseIconVisible(true);
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chipGroup.removeView(chipEntry);
                        }
                    });

                    chipGroup.addView(chip);
                    mAttendeesNames.setText("");
                    mAttendees.setError(null);
                } else {
                    mAttendees.setError("merci de saisir une adresse mail");
                }
            }
        });


        // add information to meeting creation
        this.createReunion.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                boolean result = addReunion();
                if (result == true) {


                    Log.d("click", "JE PASSE SUR LE CLICK");

                    final String room = (String) spinner.getSelectedItem();
                    Log.d("Room name", room);

                    final String subject = mReunionSubject.getText().toString();
                    Log.d("Reunion subject", subject);

                    final Integer currentHour = mReunionTime.getCurrentHour();
                    final Integer currentMinute = mReunionTime.getCurrentMinute();
                    Log.d("Reunion time", currentHour + "h" + currentMinute);

                    final int year = mReunionDate.getYear();
                    final int month = mReunionDate.getMonth();
                    final int dayOfMonth = mReunionDate.getDayOfMonth();
                    Log.d("Reunion date", year + "/" + month + "/" + dayOfMonth);

                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth, currentHour, currentMinute);
                    Log.d("Timestamp", calendar.getTimeInMillis() + "");


                    // Get the selected chips from the chipGroup
                    String mails = "";
                    int chipsCount = chipGroup.getChildCount();
                    int i = 0;
                    while (i < chipsCount) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);

                        mails += chip.getText().toString() + " ";

                        i++;

                        Log.d("value", i + "");
                    }
                    ;
                    Log.d("CHIP GROUP", mails);
                    Log.d("test", "test");


                    List<Integer> intArray = DummyColourGenerator.DUMMY_COLOURS;
                    Random random = new Random();
                    int i1 = random.nextInt(intArray.size());
                    Log.d("colour", "dummy colour");


                    Reunion reunion = new Reunion(
                            UUID.randomUUID().hashCode(),
                            room,
                            calendar.getTimeInMillis(),
                            subject,
                            mails,
                            intArray.get(i1)

                    );

                    mApiService.createReunion(reunion);
                    finish();
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        //    Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView){

    }

    private boolean addReunion() {

        if (mReunionSubject.getText().toString().equals("") == true) {
            subject.setError("merci de saisir un sujet de réunion");
        //    Toast.makeText(AddReunionActivity.this, "merci de saisir un sujet de réunion", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (chipGroup.getCheckedChipIds().isEmpty()) {

            Toast.makeText(AddReunionActivity.this, "merci d'ajouter des participants", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    // Make sure to avoid overlapping same meeting in the same room at the same time and date
    public void avoidOverlappingReunions() {


    }


}
