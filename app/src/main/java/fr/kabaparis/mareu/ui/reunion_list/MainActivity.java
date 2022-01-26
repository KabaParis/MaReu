package fr.kabaparis.mareu.ui.reunion_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.kabaparis.mareu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.Toolbar));
        getSupportActionBar().setTitle(R.string.app_name);
    }
}