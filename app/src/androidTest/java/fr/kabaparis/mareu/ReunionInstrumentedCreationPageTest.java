package fr.kabaparis.mareu;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import fr.kabaparis.mareu.ui.reunion_list.ReunionActivity;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ReunionInstrumentedCreationPageTest {

    /**
     * Instrumented test, which will execute on an Android device.
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     */
    @RunWith(AndroidJUnit4.class)
    public class ReunionInstrumentedListPageTest {

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
    }
