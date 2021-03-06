package utils;

import android.view.View;

import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import fr.kabaparis.mareu.R;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(androidx.test.espresso.UiController uiController, View view) {
        View button = view.findViewById(R.id.room_list_delete_button);
        // Maybe check for null
        button.performClick();
    }


}
