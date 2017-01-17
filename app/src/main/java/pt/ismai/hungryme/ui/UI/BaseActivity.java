package pt.ismai.hungryme.ui.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pt.ismai.hungryme.LoginAndRegister.LoginActivity;
import pt.ismai.hungryme.LoginAndRegister.Session;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.AccountActivity;
import pt.ismai.hungryme.ui.FavoritesActivity;
import pt.ismai.hungryme.ui.SettingsActivity;
import pt.ismai.hungryme.ui.Recipes.RecipesActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private Session session;
    protected static final int NAV_DRAWER_ITEM_INVALID = -1;
    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String title = "";
    SharedPreferences prefs;
    String verify;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
        session = new Session(this);
    }

    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
            View hView = navigationView.getHeaderView(0);
            TextView nav_user = (TextView) hView.findViewById(R.id.emailHeader);
            prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            verify = prefs.getString("namestring", "");

            if(!verify.matches("")) {
                nav_user.setText(prefs.getString("namestring", ""));
            } else {
                nav_user.setText(prefs.getString("EMAIL", ""));
            }
        }

    }

    private void logout(){
        session.setLoggedin(false);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void setSelectedItem(NavigationView navigationView) {
        int selectedItem = getSelfNavDrawerItem();
        navigationView.setCheckedItem(selectedItem);
    }


    public void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
    }

    public void onNavigationItemClicked(final int itemId) {
        if(itemId == getSelfNavDrawerItem()) {
            closeDrawer();
            return;
        }
        goToNavDrawerItem(itemId);
    }

    public void goToNavDrawerItem(int item) {
        Intent intent = new Intent(this, RecipesActivity.class);
        switch (item) {
            case R.id.nav_healthy:
                intent.putExtra("search", "healthy");
                title ="Healthy";
                intent.putExtra("title", title);
                break;

            case R.id.nav_casual:
                intent.putExtra("search", "pasta");
                title ="Casual";
                intent.putExtra("title", title);
                break;
            case R.id.nav_vegan:
                intent.putExtra("search", "vegan");
                title ="Vegan";
                intent.putExtra("title", title);
                break;
            case R.id.nav_breakfast:
                intent.putExtra("search", "breakfast");
                title ="Breakfast";
                intent.putExtra("title", title);
                break;
            case R.id.nav_desserts:
                intent.putExtra("search", "dessert");
                title ="Desserts";
                intent.putExtra("title", title);
                break;
            case R.id.nav_fast:
                intent = null;
                startActivity(new Intent(this, FavoritesActivity.class));
                break;
            case R.id.nav_account:
                intent = null;
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.nav_settings:
                intent = null;
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_logout:
                intent = null;
                logout();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
            if(intent != null) startActivity(intent);
    }

    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }

    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public abstract boolean providesActivityToolbar();

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
