package com.stardev.soigolexy.firebaseauth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts/{id}/comments")
    Call<List<Comments>> getComments(@Path("id")int postId);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //url encoded way of sending data to the server
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id,@Body Post post);

    @PATCH("posts/{id}")
    Call<Post> putPatch(@Path("id") int id,@Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
