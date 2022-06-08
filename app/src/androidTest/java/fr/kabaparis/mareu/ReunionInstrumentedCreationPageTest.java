package fr.kabaparis.mareu;

import android.app.Activity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentActivity;
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

import fr.kabaparis.mareu.ui.reunion_list.AddReunionActivity;
import fr.kabaparis.mareu.ui.reunion_list.ReunionActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
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
import static utils.TextInputLayoutMatcher.hasTextInputLayoutErrorText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedCreationPageTest {

        // This is fixed
        private int ITEMS_COUNT = 10;

        private View decorView;
        private ActivityScenario<AddReunionActivity> mActivity;

        @Rule
        public ActivityScenarioRule<AddReunionActivity> mActivityRule =
                new ActivityScenarioRule<AddReunionActivity>(AddReunionActivity.class);

        @Before
        public void setUp() {
            mActivity = mActivityRule.getScenario();
            assertThat(mActivity, notNullValue());
        }

    /**
     * When we try to create a meeting without adding a subject, an error message is displayed and
     * the creation is blocked
     */
    @Test
    public void meetingCreation_withoutSubject_shouldShowErrorMessage() {

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




}
