package fr.kabaparis.mareu;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.kabaparis.mareu.ui.reunion_list.ReunionActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static utils.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedEndToEndTest {

    // This is fixed
    private int ITEMS_COUNT = 10;

    private ActivityScenario<ReunionActivity> mActivity;
    private View decorView;

    @Rule
    public ActivityScenarioRule<ReunionActivity> mActivityRule =
            new ActivityScenarioRule<ReunionActivity>(ReunionActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getScenario();
        assertThat(mActivity, notNullValue());
        mActivity.onActivity(new ActivityScenario.ActivityAction<ReunionActivity>() {
            @Override
            public void perform(ReunionActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    /**
     * We ensure that the meeting creation is functioning properly
     */
    @Test
    public void createButton_isFunctioning_properly() {
        // Given : The meeting list contains 10 items
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // We select the Réunion B in spinner
        onView(withId(R.id.spinner)).perform(click());
        onData(Matchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Réunion B")));

        // We select the time in timePicker
        onView(withId(R.id.reunionTimePicker)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(11, 30));

        // We select the date in datePicker
        onView(withId(R.id.reunionDatePicker)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate
                (2022, 05, 15));

        // We scroll down and then fill the subject in
        onView(withId(R.id.reunionSubject)).perform(scrollTo(),typeText("Subject"),
                ViewActions.closeSoftKeyboard());

        // We write the email address
        onView(withId(R.id.attendeesNames)).perform(typeText("test@gmail.com"));
        Espresso.closeSoftKeyboard();

        // We validate the chip
        onView(withId(R.id.chipEntry)).perform(click());

        // We click on button "ENREGISTRER"
        onView(withId(R.id.createReunion)).perform(scrollTo()).perform(click());

        // We check if a new meeting is added
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT+1));

    }


    /**
     * We ensure that there is no overlapping when creating meetings
     */
    @Test
    public void meetingCreation_should_avoid_overlapping() {
        // Given we are on the list page and the meeting list contains 10 items
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // We select the Réunion B in spinner
        onView(withId(R.id.spinner)).perform(click());
        onData(Matchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Réunion B")));

        // We select the time in timePicker
        onView(withId(R.id.reunionTimePicker)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(11, 30));

        // We select the date in datePicker
        onView(withId(R.id.reunionDatePicker)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate
                (2022, 05, 15));

        // We scroll down and then fill the subject in
        onView(withId(R.id.reunionSubject)).perform(scrollTo(),typeText("Subject"),
                ViewActions.closeSoftKeyboard());

        // We write the email address
        onView(withId(R.id.attendeesNames)).perform(typeText("test@gmail.com"));
        Espresso.closeSoftKeyboard();

        // We validate the chip
        onView(withId(R.id.chipEntry)).perform(click());

        // We click on button "ENREGISTRER"
        onView(withId(R.id.createReunion)).perform(scrollTo()).perform(click());

        // Then the meeting should appear on the list (we check if it's added)
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT+1));

        // Then we create another meeting
        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // We select the Réunion B in spinner
        onView(withId(R.id.spinner)).perform(click());
        onData(Matchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Réunion B")));

        // We select the time in timePicker
        onView(withId(R.id.reunionTimePicker)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(12, 00));

        // We select the date in datePicker
        onView(withId(R.id.reunionDatePicker)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate
                (2022, 05, 15));

        // We scroll down and then fill the subject in
        onView(withId(R.id.reunionSubject)).perform(scrollTo(),typeText("Subject2"),
                ViewActions.closeSoftKeyboard());

        // We write the email address
        onView(withId(R.id.attendeesNames)).perform(typeText("test2@gmail.com"));
        Espresso.closeSoftKeyboard();

        // We validate the chip
        onView(withId(R.id.chipEntry)).perform(click());

        // We click on button "ENREGISTRER"
        onView(withId(R.id.createReunion)).perform(scrollTo()).perform(click());

        // The creation should not be allowed

    }

}
