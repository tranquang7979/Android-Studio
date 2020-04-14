package com.example.databasetutorials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databasetutorials.provider.MovieStoreProvider;

import java.util.ArrayList;

public class CRUDGenreActivity extends AppCompatActivity {


    Uri uri_select = MovieStoreProvider.CONTENT_URI;
    Uri uri_crud = MovieStoreProvider.CONTENT_URI;

    Button btn_add,btn_update, btn_delete,btn_viewall;
    EditText ed_genre_name, ed_genre_desc,ed_genre_image;
    Spinner sp_genre_id;
    ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d_genre);

        sp_genre_id = findViewById(R.id.sp_genre_id);
        btn_add =findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_viewall = findViewById(R.id.btn_viewall);

        ed_genre_name = findViewById(R.id.ed_genre_name);
        ed_genre_desc = findViewById(R.id.ed_genre_desc);
        ed_genre_image = findViewById(R.id.ed_genre_image);

        resolver = getContentResolver();
        getAllGenreId();

        sp_genre_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_genre_id =(String) parent.getSelectedItem();
                getGenreDetailsById(selected_genre_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGenre();
                getAllGenreId();
            }
        });
        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(CRUDGenreActivity.this, GenreManagementActivity.class);
                startActivity(it);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGenre();
                getAllGenreId();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGenre();
                getAllGenreId();
            }
        });

    }


    private void updateGenre(){
        String id= sp_genre_id.getSelectedItem().toString();
        String url = MovieStoreProvider.CONTENT_URI+"/"+id;
        uri_crud = Uri.parse(url);
        ContentValues values = new ContentValues();
        values.put("name", ed_genre_name.getText().toString());
        values.put("description", ed_genre_desc.getText().toString());
        values.put("imageurl", ed_genre_image.getText().toString());
        resolver.update(uri_crud,values,"id=?" , new String[] {id});
        Toast.makeText(this, "Genre is updated!!", Toast.LENGTH_LONG).show();

    }

    private void deleteGenre(){
        String id= sp_genre_id.getSelectedItem().toString();
        String url = MovieStoreProvider.CONTENT_URI+"/"+id;
        uri_crud = Uri.parse(url);
        resolver.delete(uri_crud,"id=?", new String[]{id});
        Toast.makeText(this, "Genre is deleted!!", Toast.LENGTH_LONG).show();
    }
    private void createGenre(){
        String url = MovieStoreProvider.CONTENT_URI+"";
        uri_crud = Uri.parse(url);
        ContentValues values = new ContentValues();
        values.put("name", ed_genre_name.getText().toString());
        values.put("description", ed_genre_desc.getText().toString());
        values.put("imageurl", ed_genre_image.getText().toString());

        Uri uri_new= resolver.insert(uri_crud, values);
        Toast.makeText(this, "Genre is added!!", Toast.LENGTH_LONG).show();
    }
  private void getGenreDetailsById(String id){
        String url = MovieStoreProvider.CONTENT_URI + "/"+id;
        uri_select = Uri.parse(url);

      Cursor c = resolver.query(uri_select, new String[] {"id","name","description","imageurl"},
              "id=?",new String[]{id},null);
      if(c.moveToFirst()){
            String name= c.getString(c.getColumnIndex("name"));
            String desc= c.getString(c.getColumnIndex("description"));
            String imageurl= c.getString(c.getColumnIndex("imageurl"));
            ed_genre_name.setText(name);
            ed_genre_desc.setText(desc);
            ed_genre_image.setText(imageurl);
      }
      c.close();
  }
    private void getAllGenreId(){
        uri_select = MovieStoreProvider.CONTENT_URI;
        ArrayList<String> genreIdList =new    ArrayList<String>();
        Cursor c = resolver.query(uri_select, new String[] {"id"},null,null,null);
        if(c.moveToFirst()){
            while(!c.isAfterLast()){
                String id= c.getString(c.getColumnIndex("id"));
                genreIdList.add(id);
                c.moveToNext();
            }
        }
        c.close();
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreIdList);
        sp_genre_id.setAdapter(adapter);

    }
}
