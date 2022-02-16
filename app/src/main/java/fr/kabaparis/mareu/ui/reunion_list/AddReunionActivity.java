package fr.kabaparis.mareu.ui.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity {


    private TextView mRoomName;
    private TextView mReunionTime;
    private TextView mReunionSubject;
    private TextView mAttendeesNames;
    private ImageButton mAddButton;

    private ReunionApiService mApiService;
    private Reunion mReunion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);


        // get the views identified by their attributes

        mRoomName = findViewById(R.id.roomName);
        mReunionTime = findViewById(R.id.reunionTime);
        mReunionSubject = findViewById(R.id.reunionSubject);
        mAttendeesNames = findViewById(R.id.attendeesNames);
      //  mAddButton = findViewById(R.id.add_reunion);

        // get neighbour id by the api service

        long id = getIntent().getLongExtra("id", 0);
        mApiService = DI.getReunionApiService();
        mReunion = mApiService.getReunionById(id);

        //get attributes of widgets

        this.mRoomName.setText(mRoomName.getText());
        this.mReunionTime.setText(mReunionTime.getText());
        this.mReunionSubject.setText(mReunionSubject.getText());
        this.mAttendeesNames.setText(mAttendeesNames.getText());


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

    /**
     * Used to navigate to this activity
     *
     * @param activity
     */
    public static void navigate(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}