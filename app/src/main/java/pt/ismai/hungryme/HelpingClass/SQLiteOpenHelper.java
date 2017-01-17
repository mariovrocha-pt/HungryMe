package pt.ismai.hungryme.HelpingClass;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.ismai.hungryme.Recipe.FavoriteRecipe;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    public static final String TAG = SQLiteOpenHelper.class.getSimpleName();

    public static final String DB_NAME = "favoritesRecipes.db";
    public static final int DB_VERSION = 1;
    public static final String RECIPES_TABLE = "recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_IMAGE = "image";

    public static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + RECIPES_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_AUTHOR + " TEXT,"
            + COLUMN_CALORIES + " TEXT,"
            + COLUMN_INGREDIENTS + " TEXT,"
            + COLUMN_URL + " TEXT,"
            + COLUMN_IMAGE + " TEXT);";

    public SQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbR) {
        dbR.execSQL(CREATE_TABLE_RECIPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbR, int oldVersion, int newVersion) {
        dbR.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE);
        onCreate(dbR);
    }


    public void addRecipe(String name, String author, String calories, String ingredients, String url, String image) {
        SQLiteDatabase dbR = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_CALORIES, calories);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_URL, url);
        values.put(COLUMN_IMAGE, image);
        long id = dbR.insert(RECIPES_TABLE, null, values);
        dbR.close();
        Log.d(TAG, "Recipe Added" + id);
    }


    public List<FavoriteRecipe> getAllRecipes() {
        ArrayList<FavoriteRecipe> recipesList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + RECIPES_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FavoriteRecipe recipe = new FavoriteRecipe();
                recipe.set_id(Integer.parseInt(cursor.getString(0)));
                recipe.set_name(cursor.getString(1));
                recipe.set_author(cursor.getString(2));
                recipe.setCalories(cursor.getString(3));
                recipe.set_ingredients(cursor.getString(4));
                recipe.set_url(cursor.getString(5));
                recipe.set_photoURL(cursor.getString(6));
                recipesList.add(recipe);
            } while (cursor.moveToNext());
        }
        return recipesList;
    }
}