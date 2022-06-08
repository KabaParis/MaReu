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
            onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT-1));

        }

    /**
     * When we click on the add button, the meeting creation page is displayed
     */
    @Test
    public void myReunionList_addButton_adds_addReunionActivity() {
            // Given : We are on the list page
            // We perform a click on add button
            onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.add_reunion))).perform(click());
            // Then check if activity details is displayed
            onView(Matchers.allOf(ViewMatchers.isDisplayed(),
                    withId(R.id.add_reunion_activity))).check(matches(ViewMatchers.isDisplayed()));

    }

    /**
     * When we filter meetings by room names, only filtered room names should be displayed
     */
    @Test
    public void myReunionList_filterByRoom_button_shouldFilterByRoom() {
        // Given : We are on the list page
        // We perform a click on add button
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.add_reunion))).perform(click());


    }

    /**
     * When we filter meetings by dates, only filtered dates should be displayed
     */
    @Test
    public void myReunionList_filterByDate_button_shouldFilterByDate() {
        // Given : We are on the list page
        // We perform a click on add button
        onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.add_reunion))).perform(click());

    }

    /**
     * When we don't filter meetings, the full meeting list should be displayed
     */
    @Test
    public void myReunionList_noFilter_button_should_alwaysDisplay_the_full_meetingList() {


    }

}