package com.example.chatx.fragments.post;

public class PostFormat {
    String texts;
    int likes, dislikes; //reaction sections


    public PostFormat(String texts) {
        this.texts = texts;
        likes = 0;
        dislikes = 0;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
