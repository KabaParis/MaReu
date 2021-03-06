package fr.kabaparis.mareu;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.kabaparis.mareu.ui.reunion_list.ReunionActivity;
import utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static utils.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedListPageTest {

    // This is fixed
    private static int ITEMS_COUNT = 10;
    @Rule
    public ActivityScenarioRule<ReunionActivity> mActivityRule =
            new ActivityScenarioRule<ReunionActivity>(ReunionActivity.class);
    private ActivityScenario<ReunionActivity> mActivity;
    private View decorView;

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
        ITEMS_COUNT = ITEMS_COUNT - 1;
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

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
        // We perform a click on the triple dots button on the toolbar
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We perform a click on "filter room" button
        onView(withText(R.string.filter_room)).perform(click());

        // Then select "Reunion C"
        onView(withText("R??union C")).perform(click());

        // We click on "filtrer par salle" button
        onView(withText("FILTRER PAR SALLE")).perform(click());

        // Then we check that there is 1 remaining item on the list
        ITEMS_COUNT = ITEMS_COUNT - 9;
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

        // We click on the triple dots button on the toolbar
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We click on "no filter" button
        onView(withText(R.string.no_filter)).perform(click());

        // Then we check that the full list is displayed ; 10 items
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT = 10));


    }

    /**
     * When we filter meetings by dates, only filtered dates should be displayed
     */
    @Test
    public void myReunionList_filterByDate_button_shouldFilterByDate() {
        // Given : We are on the list page
        // We perform a click on the triple dots button
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We perform a click on "filter date" button
        onView(withText("Filtrer par date")).perform(click());

        // We select a date on the date picker that is matching with one meeting
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2022, 03, 30));

        // Then we click the ok button on the date picker
        onView(withText("OK")).perform(click());

        // We check that there is 1 remaining item on the list
        ITEMS_COUNT = ITEMS_COUNT - 9;
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

        // We click on the triple dots button on the toolbar
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We click on "no filter" button
        onView(withText("Ne pas filtrer")).perform(click());


        // Then we check that the full list is displayed ; 10 items
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT = 10));

    }

    /**
     * When we don't filter meetings, the full meeting list should be displayed
     */
    @Test
    public void myReunionList_noFilter_button_should_alwaysDisplay_the_full_meetingList() {
        // Given we are on the list page
        // We perform a click on the triple dots button
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We perform a click on "filter room" button
        onView(withText(R.string.filter_room)).perform(click());

        // Then select "Reunion G"
        onView(withText("R??union G")).perform(click());

        // We click on "filtrer par salle" button
        onView(withText("FILTRER PAR SALLE")).perform(click());

        // Then we check that there is 1 remaining item on the list
        ITEMS_COUNT = ITEMS_COUNT - 9;
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));

        // We perform a click on the triple dots button
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We perform a click on "filter date" button
        onView(withText(R.string.filter_date)).perform(click());

        // We select a date on the date picker that is not matching with one meeting
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 01, 22));

        // Then we click the ok button on the date picker
        onView(withText("OK")).perform(click());

        // We check that there is 0 item remaining on the list
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT = 0));

        // We perform a click on the triple dots button
        openActionBarOverflowOrOptionsMenu(decorView.getContext());

        // We perform a click on "no filter" button
        onView(withText(R.string.no_filter)).perform(click());

        // Then we check that the full list is displayed ; 10 items
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT = 10));

    }

}