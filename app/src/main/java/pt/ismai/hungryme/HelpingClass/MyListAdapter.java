package pt.ismai.hungryme.HelpingClass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.Recipe.RecipeContent;

public class MyListAdapter extends BaseAdapter {
    private Activity activity;

    public MyListAdapter(Activity baseActivity) {
        RecipeContent.ITEMS.clear();
        activity = baseActivity;
    }

    @Override
    public int getCount() {
        return RecipeContent.ITEMS.size();
    }

    @Override
    public Object getItem(int position) {
        return RecipeContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return RecipeContent.ITEMS.get(position).id.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_recipe, container, false);
        }

        final RecipeContent.RecipeItem item = (RecipeContent.RecipeItem) getItem(position);
        ((TextView) convertView.findViewById(R.id.recipe_title)).setText(item.title);
        ((TextView) convertView.findViewById(R.id.recipe_calories)).setText(item.calories + " kcal");
        ((TextView) convertView.findViewById(R.id.label)).setText(item.label + " ");
        final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);
        Glide.with(activity)
                .load(item.photoURL)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        img.setImageDrawable(circularBitmapDrawable);
                    }
                });


        return convertView;
    }
}
