package com.stardev.soigolexy.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {

    private TextView text_view_result;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();

        text_view_result = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

         jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            getPosts();
            //getComments();
            //createPost();
            //updatePost();
            //deletePost();


    }
     private void getPosts() {
         Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{2,3,4,6},null, null);

         call.enqueue(new Callback<List<Post>>() {
             @Override
             public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                 if (!response.isSuccessful()) {
                     text_view_result.setText("Code" + response.code());
                 }
                 List<Post> posts = response.body();
                 for (Post post : posts){
                     String content = "";
                     content += "ID :" + post.getId() + "\n";
                     content += "User ID :" + post.getUserId() + "\n";
                     content += "Title :" + post.getTitle() + "\n";
                     content += "Text :" + post.getText() + "\n\n";

                     text_view_result.append(content);
                 }

             }

             @Override
             public void onFailure(Call<List<Post>> call, Throwable t) {
                 text_view_result.setText(t.getMessage());
             }
         });
     }

     private void getComments() {
        Call<List<Comments>> call = jsonPlaceHolderApi.getComments(3);

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                if (!response.isSuccessful()) {
                    text_view_result.setText("Code" + response.code());
                    return;
                }

                List<Comments> comments = response.body();

                for (Comments comments1 : comments) {
                    String content = "";
                    content += "ID" + comments1.getId() + "\n";
                    content += "Post ID" + comments1.getPostId() + "\n";
                    content += "Name" + comments1.getName() + "\n";
                    content += "Email" + comments1.getEmail() + "\n\n";
                    content += "Text" + comments1.getText() + "\n\n";

                    text_view_result.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });
     }

     private  void createPost() {
        Post post = new Post(25,"NewTitle","NewText");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    text_view_result.setText("Code" + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code " + response.code() + "\n";
                content += "Post ID" + postResponse.getId() + "\n";
                content += "User ID" + postResponse.getUserId() + "\n";
                content += "Title" + postResponse.getTitle() + "\n\n";
                content += "Text" + postResponse.getText() + "\n\n";

                text_view_result.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text_view_result.setText(t.getMessage());

            }
        });
     }

     private void updatePost() {
        Post post = new Post(21, null, "MyText");
        Call<Post> call = jsonPlaceHolderApi.putPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    text_view_result.setText("Code" + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Post ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";

                text_view_result.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });
     }

     private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                text_view_result.setText("Code : " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                text_view_result.setText(t.getMessage());
            }
        });
     }
}
