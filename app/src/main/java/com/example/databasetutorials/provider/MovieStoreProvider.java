package com.example.databasetutorials.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieStoreProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "com.example.databasetutorials.provider.MovieStoreProvider";
    public static final String DATA_TYPE="genres";
    public static final String URL="content://"+PROVIDER_NAME+"/"+DATA_TYPE;
    public static final  Uri CONTENT_URI = Uri.parse(URL);

    public static final int GENRES=1;
    public static final int GENRE_ID = 2;

    public static final UriMatcher uriMatcher;
    static {
        uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,DATA_TYPE,GENRES);

        uriMatcher.addURI(PROVIDER_NAME,DATA_TYPE+"/#",GENRE_ID);
        //sample
        //tap du lieu co so luong hang > 1 : content://com.example.databasetutorials.provider.MovieStoreProvider/genres
        //tap du lieu co so luong hang  =1 :content://com.example.databasetutorials.provider.MovieStoreProvider/genres/5
    }

    MyDBHelper helper;
    SQLiteDatabase db;
    @Override
    public boolean onCreate() {

        Context ctx =getContext();
        helper = new MyDBHelper(ctx);
        db= helper.getWritableDatabase();
        return db!=null ? true:false;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor c= null;
        switch (uriMatcher.match(uri)){

            case GENRES:
                c= db.query("tbl_genres",projection,selection,selectionArgs,null,null,"name ASC");
                break;
            case GENRE_ID:
                String genreId = uri.getPathSegments().get(1);
                c= db.query("tbl_genres",projection,"id=?",new String[] {genreId},null,null,"name ASC");
                break;
            default:
                throw new IllegalArgumentException("unsupport "+uri);


        }
        getContext().getContentResolver().notifyChange(uri,null);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case GENRES:
                return "vnd.android.cursor.dir/vnd.movie.genres";
            case GENRE_ID:
                return "vnd.android.cursor.item/vnd.movie.genres";
            default:
                throw new IllegalArgumentException("unsupport "+uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int id = -1;
        switch (uriMatcher.match(uri)){

            case GENRES:
                id = (int)db.insert("tbl_genres", null,values);
                break;
            default:
                throw new IllegalArgumentException("unsupport"+uri);

        }
        Uri uri_new = ContentUris.withAppendedId(uri,id);
        getContext().getContentResolver().notifyChange(uri_new,null);
        return uri_new;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int result = 0;
        switch (uriMatcher.match(uri)){

            case GENRES:
                result = db.delete("tbl_genres",null,null);
                break;
            case GENRE_ID:
                String id= uri.getPathSegments().get(1);
                result = db.delete("tbl_gneres","id = ?",new String[] {id});
                break;
            default:
                throw new IllegalArgumentException("unsupport"+uri);
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int result= 0;
        switch (uriMatcher.match(uri)){

            case GENRE_ID:
                String id = uri.getPathSegments().get(1);
                result = db.update("tbl_genres",values,"id=?", new String[] {id});
                break;
            default:
                throw new IllegalArgumentException("unsupport"+uri);
        }
        return result;
    }
}
