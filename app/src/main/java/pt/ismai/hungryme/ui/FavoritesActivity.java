package pt.ismai.hungryme.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ismai.hungryme.Recipe.FavoriteRecipe;
import pt.ismai.hungryme.HelpingClass.FavoriteListAdapter;
import pt.ismai.hungryme.HelpingClass.SQLiteOpenHelper;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.UI.BaseActivity;

public class FavoritesActivity extends BaseActivity {

    ListView lv;
    ArrayList<FavoriteRecipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_favorites);
        setupToolbar();

        SQLiteOpenHelper db = new SQLiteOpenHelper(this);

        lv = (ListView) findViewById(R.id.lv);

        recipes = (ArrayList<FavoriteRecipe>) db.getAllRecipes();

        FavoriteListAdapter favoriteListAdapter = new FavoriteListAdapter(this,R.layout.list_item_recipe, recipes);
        lv.setAdapter(favoriteListAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent webBrowser = new Intent(getBaseContext(), WebViewActivity.class);
                webBrowser.putExtra("url", "");
                startActivity(webBrowser);
            }
        });

    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favorites");
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
        return R.id.nav_fast;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

}
