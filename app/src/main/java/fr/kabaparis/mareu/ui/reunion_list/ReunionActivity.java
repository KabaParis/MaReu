package fr.kabaparis.mareu.ui.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import fr.kabaparis.mareu.R;


public class ReunionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.app_name);

    findViewById(R.id.add_reunion).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddReunionActivity.navigate(ReunionActivity.this);

        }
    });



    }
}