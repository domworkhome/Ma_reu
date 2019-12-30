package com.lamzone.mareu;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.lamzone.mareu.controllers.activities.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.lamzone.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    private MainActivity mActivity;
    private static final int ITEMS_COUNT = 4;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void meetingList_shouldNotBeEmpty() {
        onView(ViewMatchers.withId(R.id.list_meetings))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void clickOnFab_thenGoToCreateDialog(){
        onView(withId(R.id.meeting_fab_add)).perform(click());
        onView(withId(R.id.meeting_topic)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnValidateAddMeetingDialogButton_thenCreateNewMeeting(){
        onView(withId(R.id.meeting_fab_add)).perform(click());
        onView(withId(R.id.meeting_topic)).perform(click())
                .perform(replaceText("Ajout de réunion"), closeSoftKeyboard());
        onView(withId(R.id.tv_datepicker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.members_spinner))
                .perform(click());
        onData(anything()).atPosition(9).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withText("AJOUTER")).perform(click());
        onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT + 1));
    }
}
