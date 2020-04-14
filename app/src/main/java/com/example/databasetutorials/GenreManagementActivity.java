package com.example.databasetutorials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.databasetutorials.provider.MovieStoreProvider;

import java.util.ArrayList;

public class GenreManagementActivity extends AppCompatActivity {
    //content://com.example.databasetutorials.provider.MovieStoreProvider/genres
    Uri uri = MovieStoreProvider.CONTENT_URI;
    ContentResolver resolver;

    ListView lv_genres;
    ArrayList<String> genrenames = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_management);
        lv_genres=findViewById(R.id.lv_genres);
        //goi contentprovider de lay danh sach ten cua bang genres
        resolver= getContentResolver();
        Cursor cursor = resolver.query(uri,new String[]{"id", "name", "imageurl"},null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() ==false){
                String genre_name = cursor.getString(cursor.getColumnIndex("name"));
                String genre_image =  cursor.getString(cursor.getColumnIndex("imageurl"));
                genrenames.add(genre_name + "-" + genre_image);
                cursor.moveToNext();

            }
        }
        cursor.close();
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genrenames);
        lv_genres.setAdapter(adapter);


        //@Quang: Cursor cursor = resolver.query(uri,new String[]{"id", "name", "imageurl"},"name = ? and description = ?",
        // new String[] {"<your name>","<your desc>"},null);
        //Cursor cursor = resolver.query(uri,new String[]{"id", "name", "imageurl"},"name in ('123', '456')",null,null);

    }
}
