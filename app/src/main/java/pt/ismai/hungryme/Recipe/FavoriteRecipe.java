package pt.ismai.hungryme.Recipe;

public class FavoriteRecipe {
    int _id;
    String _name;
    String _author;
    String _photoURL;
    String _url;
    String calories;
    String _ingredients;
    String _label;

    public String get_label() {
        return _label;
    }

    public void set_label(String _label) {
        this._label = _label;
    }

    public String get_ingredients() {
        return _ingredients;
    }

    public void set_ingredients(String _ingredients) {
        this._ingredients = _ingredients;
    }

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public String get_photoURL() {
        return _photoURL;
    }

    public void set_photoURL(String _photoURL) {
        this._photoURL = _photoURL;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
