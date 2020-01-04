package com.example.spider;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LaunchActivityTest {

    @Rule
    public ActivityTestRule<LaunchActivity> mActivityTestRule = new ActivityTestRule<>(LaunchActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void launchActivityTest() throws Exception {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button6),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                8),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input),
                        childAtPosition(
                                allOf(withId(R.id.bar_search),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());
        appCompatEditText.perform(replaceText("鲁迅"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.iv_search), withText("搜索"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bar_search),
                                        0),
                                1),
                        isDisplayed()));
        appCompatTextView.perform(click());
        testLoading();
        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.text), withText("中国鲁迅:《呐喊》"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.show),
                                        10),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());
        testLoading();
        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.text), withText("鲁迅小说:《孔乙己》"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chapter),
                                        9),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.bt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.parent),
                                        1),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.background_3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet1),
                                        6),
                                2),
                        isDisplayed()));
        appCompatImageView.perform(click());
        testLoading();
        ViewInteraction kaitiView = onView(
                allOf(withId(R.id.text_style2), withText("楷书字体"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet1),
                                        8),
                                1),
                        isDisplayed()));
        kaitiView.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.bt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.parent),
                                        1),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        pressBack();

        pressBack();

        pressBack();
        testLoading();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
    public void testLoading() throws Exception {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        myThread.start();
        myThread.join();

    }
}
