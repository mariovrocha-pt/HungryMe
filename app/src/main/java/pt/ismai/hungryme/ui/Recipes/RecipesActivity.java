package pt.ismai.hungryme.ui.Recipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import pt.ismai.hungryme.HelpingClass.JSONParser;
import pt.ismai.hungryme.HelpingClass.MyListAdapter;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.Recipe.RecipeContent;
import pt.ismai.hungryme.ui.UI.BaseActivity;
import pt.ismai.hungryme.ui.Utils;

public class RecipesActivity extends BaseActivity implements RecipeListFragment.Callback {
    private boolean twoPaneMode;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_list);
        setupToolbar();


        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });

        if (isTwoPaneLayoutUsed()) {
            twoPaneMode = true;
            enableActiveItemState();
        }
        if (savedInstanceState == null && twoPaneMode) {
            setupDetailFragment();
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (twoPaneMode) {
            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, fragment).commit();
        } else {
            Intent detailIntent = new Intent(this, RecipeDetailActivity.class);
            detailIntent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);

    }

    private void setupDetailFragment() {
        RecipeDetailFragment fragment =  RecipeDetailFragment.newInstance(RecipeContent.ITEMS.get(0).id);
        getFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, fragment).commit();
    }

    private void enableActiveItemState() {
        RecipeListFragment fragmentById = (RecipeListFragment) getFragmentManager().findFragmentById(R.id.recipe_list);
        fragmentById.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private boolean isTwoPaneLayoutUsed() {
        return findViewById(R.id.recipe_detail_container) != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return 0;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
