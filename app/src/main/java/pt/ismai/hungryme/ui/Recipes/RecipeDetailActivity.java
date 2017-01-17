package pt.ismai.hungryme.ui.Recipes;

import android.os.Bundle;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.Recipe.RecipeContent;
import pt.ismai.hungryme.ui.UI.BaseActivity;
import pt.ismai.hungryme.ui.Utils;

public class RecipeDetailActivity extends BaseActivity {

    private String TAG = RecipeContent.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
        getFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, fragment).commit();
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

}
