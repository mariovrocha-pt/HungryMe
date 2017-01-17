package pt.ismai.hungryme.ui.Recipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.Bind;
import pt.ismai.hungryme.HelpingClass.SQLiteOpenHelper;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.Recipe.RecipeContent;
import pt.ismai.hungryme.ui.UI.BaseActivity;
import pt.ismai.hungryme.ui.UI.BaseFragment;
import pt.ismai.hungryme.ui.WebViewActivity;

public class RecipeDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";
    private RecipeContent.RecipeItem recipeItem;
    private FloatingActionButton fab;
    SharedPreferences prefs;
    SQLiteOpenHelper dbRecipes;


    @Bind(R.id.dButton)
    Button directions;

    @Bind(R.id.label)
    TextView label;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.backdrop)
    ImageView backdropImg;

    @Bind(R.id.card_Title)
    TextView cardTitle;

    @Bind(R.id.calories)
    TextView calories;

    @Bind(R.id.ingredients)
    TextView ingredients;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            recipeItem = RecipeContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_quick_detail);

        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (recipeItem != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(recipeItem.title);
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.toolbar_text);
            cardTitle.setText(recipeItem.title);
            author.setText(recipeItem.author);
            label.setText(recipeItem.healthlabel);

            calories.setText("Calories: " + recipeItem.calories);
            ingredients.setText(recipeItem.ingredients);

            Button dButton = (Button) rootView.findViewById(R.id.dButton);
            dButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webBrowser = new Intent(getActivity(), WebViewActivity.class);
                    webBrowser.putExtra("url", recipeItem.url);
                    startActivity(webBrowser);
                }
            });

            dbRecipes = new SQLiteOpenHelper(getActivity());

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String nameR = recipeItem.title;
                            String authorR = recipeItem.author;
                            String caloriesR = recipeItem.calories;
                            String ingredientsR = recipeItem.ingredients;
                            String urlR = recipeItem.url;
                            String imageR = recipeItem.photoURL;

                            dbRecipes.addRecipe(nameR,authorR, caloriesR, ingredientsR, urlR, imageR);
                            Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                        }
            });
        }
        return rootView;
    }

    private void loadBackdrop() {
        Glide.with(getActivity())
                .load(recipeItem.photoURL)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(backdropImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static RecipeDetailFragment newInstance(String itemID) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(RecipeDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeDetailFragment() {}
}
