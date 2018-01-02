package name.juhasz.judit.udacity.delicious;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import name.juhasz.judit.udacity.delicious.activity.RecipeDetailActivity;
import name.juhasz.judit.udacity.delicious.activity.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    @Rule
    public IntentsTestRule<RecipeListActivity> mRecipeListActivityTestRule =
            new IntentsTestRule<RecipeListActivity>(RecipeListActivity.class);
    @Test
    public void clickOnRecipeListItem_opensRecipeDetailsActivity() throws Exception {
        // Given: A RecipeListActivity with at least one element (downloaded from a server)
        // When: Click on any element of the recipes list
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        // Then: The proper intent is started with the required extras
        intended(
                allOf(hasComponent(RecipeDetailActivity.class.getCanonicalName()),
                        hasExtraWithKey(RecipeDetailActivity.RECIPE_DATA)
                )
        );
    }
}
