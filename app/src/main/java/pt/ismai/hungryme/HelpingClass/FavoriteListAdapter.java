package pt.ismai.hungryme.HelpingClass;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.Recipe.FavoriteRecipe;

public class FavoriteListAdapter extends ArrayAdapter<FavoriteRecipe> {

    String url;

    ArrayList<FavoriteRecipe> recipesList = new ArrayList<>();

    public FavoriteListAdapter(Context context, int textViewResourceId, ArrayList<FavoriteRecipe> objects) {
        super(context, textViewResourceId, objects);
        recipesList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_recipe, null);
        TextView textView = (TextView) v.findViewById(R.id.recipe_title);
        TextView textView2 = (TextView) v.findViewById(R.id.recipe_calories);
        TextView textView3 = (TextView) v.findViewById(R.id.label);
        final ImageView imageView = (ImageView) v.findViewById(R.id.thumbnail);
        textView.setText(recipesList.get(position).get_name());
        textView2.setText("Calories: " +recipesList.get(position).getCalories());
        textView3.setText(recipesList.get(position).get_author() + " ");

        url = recipesList.get(position).get_url();

        Glide.with(getContext())
                .load(recipesList.get(position).get_photoURL())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

        return v;

    }
}