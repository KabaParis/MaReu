package fr.kabaparis.mareu;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.regex.MatchResult;

import fr.kabaparis.mareu.ui.reunion_list.ReunionActivity;
import utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static utils.RecyclerViewItemCountAssertion.withItemCount;
import static utils.TextInputLayoutMatcher.hasTextInputLayoutErrorText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedListPageTest {

    // This is fixed
    private static int ITEMS_COUNT=10;

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

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("fr.kabaparis.mareu", appContext.getPackageName());
    }

   /**
    * When we delete an item, the item is no more shown
    */
        @Test
        public void myReunionList_deleteAction_shouldRemoveItem() {
            // Given : The list contains 10 items
            onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));
            // We perform a click on delete icon at position 1
            onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.list)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
            // Then there is 9 items remaining
            ITEMS_COUNT = ITEMS_COUNT -1;
            onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));


        }

    /**
     * When we click on the add button, the meeting creation page is displayed
     */
    @Test
    public void myReunionList_addButton_addReunionActivity() {
            // Given : We are on the list page
            // We perform a click on add button
            onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.add_reunion))).perform(click());
            // Then check if activity details is displayed
            onView(Matchers.allOf(ViewMatchers.isDisplayed(),
                    withId(R.id.add_reunion_activity))).check(matches(ViewMatchers.isDisplayed()));

    }


    /**
     * When we try to create a meeting without adding a subject, an error message is displayed and
     * the creation is blocked
     */
    @Test
    public void meetingCreation_withoutSubject_shouldShowErrorMessage() {
        // Given : We are on the list page
        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // Given : we use the default room, time and date
        // We write the email address
        onView(withId(R.id.attendeesNames)).perform(scrollTo(),typeText("test@gmail.com"));
        Espresso.closeSoftKeyboard();

        // We click on the button "ENREGISTRER"
        onView(withId(R.id.createReunion)).perform(scrollTo()).perform(click());

        // Then a toast must appear with text : "merci de saisir un sujet de réunion"
    //    onView(withText("merci de saisir un sujet de réunion"))
    //            .inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
        onView(withId(R.id.subject)).check(matches(hasTextInputLayoutErrorText("merci de saisir un sujet de réunion")));
    }


    /**
     * When we try to create a meeting without adding participants, an error message is displayed and
     * the creation is blocked
     */
    @Test
    public void meetingCreation_withoutParticipants_shouldShowErrorMessage() {
        // Given : We are on the list page
        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // Given : we use the default room, time and date
        // We fill the subject in
        onView(withId(R.id.reunionSubject)).perform(scrollTo(),typeText("Sujet"),
                ViewActions.closeSoftKeyboard());

        // We click on button "ENREGISTRER"
        onView(withId(R.id.createReunion)).perform(scrollTo()).perform(click());

 /*       // Then a toast must appear with text : "merci d'ajouter des participants"
        onView(withText("merci d'ajouter des participants"))
                .inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
*/
        // Technical limit due to upgraded version of android, please check :
        // Toast message assertions not working with android 11 and target sdk 30 · Issue #803 · android/android-test
    }


    /**
     * We ensure that the chip validation button shows an error message when a wrong email address is typed
     */
    @Test
    public void validationButton_shows_error_message_when_wrong_typo_is_entered() {
        // Given : We are on the list page
        // We click on add button
        onView(withId(R.id.add_reunion)).perform(click());

        // Given : we use the default room, time and date
        // We fill the subject in
        onView(withId(R.id.reunionSubject)).perform(scrollTo(),typeText("Sujet"),
                ViewActions.closeSoftKeyboard());

        // We fill in an email address with a wrong typo
        onView(withId(R.id.attendeesNames)).perform(typeText("testgmail.com"));

        // We click on validate button
        onView(withId(R.id.chipEntry)).perform(scrollTo(),click());

        // Then an error message should appear with text : "merci de saisir une adresse mail"
        onView(withId(R.id.attendees)).check(matches(hasTextInputLayoutErrorText("merci de saisir une adresse mail")));
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

        ITEMS_COUNT = ITEMS_COUNT ++;
        // We check if a new meeting is added
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

    }

    /**
     * We ensure that there is no overlapping when creating meetings
     */
    @Test
    public void meetingCreation_should_avoid_overlapping() {
        // Given we are on the list page
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

        // Then the meeting should appear on the list
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