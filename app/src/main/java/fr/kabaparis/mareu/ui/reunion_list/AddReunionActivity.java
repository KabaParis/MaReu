package fr.kabaparis.mareu.ui.reunion_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import fr.kabaparis.mareu.R;
import service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity {


    @BindView(R.id.room)
    TextInputLayout roomName;
    @BindView(R.id.time)
    TextInputLayout time;
    @BindView(R.id.subject)
    TextInputLayout subjectText;
    @BindView(R.id.attendees)
    TextInputLayout attendeesNames;
    @BindView(R.id.create)
    MaterialButton addButton;

    private ReunionApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getReunionApiService();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

}