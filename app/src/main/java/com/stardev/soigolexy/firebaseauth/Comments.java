package com.stardev.soigolexy.firebaseauth;

import com.google.gson.annotations.SerializedName;

public class Comments {
    private int postId;
    private int Id;
    private String name;
    private String email;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }

    @SerializedName("body")
    private String text;

}
