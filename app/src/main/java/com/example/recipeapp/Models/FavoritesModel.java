package com.example.recipeapp.Models;

public class FavoritesModel {

    private String favorite_name, favorite_calory, display_time, favorite_type, image_dir;
    int favorite_rate, serving_num, is_liked;

    public FavoritesModel(String favorite_name, String favorite_calory, String favorite_type, int favorite_rate, String dispay_time, int serving_num, int is_liked, String image_dir) {
        this.favorite_name = favorite_name;
        this.favorite_calory = favorite_calory;
        this.favorite_type = favorite_type;
        this.favorite_rate = favorite_rate;
        this.display_time = dispay_time;
        this.serving_num = serving_num;
        this.is_liked = is_liked;
        this.image_dir = image_dir;


    }

    public int getFavorite_rate() {
        return favorite_rate;
    }

    public String getImage_dir() {
        return image_dir;
    }

    public String getDisplay_time() {
        return display_time;
    }

    public int getServing_num() {
        return serving_num;
    }

    public int getIs_liked() {
        return is_liked;
    }

    public String getFavorite_type() {
        return favorite_type;
    }

    public String getFavorite_calory() {
        return favorite_calory;
    }

    public String getFavorite_name() {
        return favorite_name;
    }

    public void setFavorite_calory(String favorite_calory) {
        this.favorite_calory = favorite_calory;
    }

    public void setFavorite_type(String favorite_type) {
        this.favorite_type = favorite_type;
    }

    public void setImage_dir(String image_dir) {
        this.image_dir = image_dir;
    }

    public void setFavorite_name(String favorite_name) {
        this.favorite_name = favorite_name;
    }

    public void setFavorite_rate(int favorite_rate) {
        this.favorite_rate = favorite_rate;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public void setServing_num(int serving_num) {
        this.serving_num = serving_num;
    }

    public void setIs_liked(int is_liked) {
        this.is_liked = is_liked;
    }
}
