package com.dylan.assignment1forrealsies;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public ArrayList<String> imageList;
    public int imageIndex;
    public int numImages;
    public ArrayList<ArrayList<String>> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);
        imageList = getImagesPath(MainActivity.this);
        numImages = imageList.size();
        imageIndex = numImages - 1;

        showImage();
    }

    public void showImage() {
        Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(imageIndex));
        ImageView imageView = (ImageView) MainActivity.this.findViewById(R.id.mainImage);
        imageView.setImageBitmap(bitmap);
    }

    public void nextImage(View view) {
        imageIndex--;
        if(imageIndex < 0) {
            imageIndex = numImages - 1;
        }
        setText();
        showImage();
    }
    public void lastImage(View view) {
        imageIndex++;
        if(imageIndex > numImages - 1) {
            imageIndex = 0;
        }
        setText();
        showImage();
    }

    public void getTag(View view) {
        TextInputEditText userInp = findViewById(R.id.commentInner);
//        Log.d("INPUT", userInp.getText().toString());

        boolean found = false;
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(userInp.getText().toString());
        temp.add(Integer.toString(imageIndex));

        for(int i = 0; i <tags.size(); i++) {
            if (tags.get(i).contains(Integer.toString(imageIndex))) {
                tags.set(i, temp);
                found = !found;
                break;
            }
        }

        if (!found) {
            tags.add(temp);
        }
//        Log.d("OUT", tags.toString());
    }

    public void updateImages(View view) {
        imageList = getImagesPath(MainActivity.this);
        numImages = imageList.size();
        imageIndex = numImages - 1;

        showImage();
    }

    public void findImage(View view) {
        TextInputEditText userInp = findViewById(R.id.searchInner);
        boolean found = false;

        for(int i = 0; i < tags.size(); i++) {
            Log.d("get", tags.get(i).get(0).toString());
            Log.d("inp", userInp.getText().toString());
            Log.d("tags", tags.toString());
            if(tags.get(i).get(0).toString().equals(userInp.getText().toString())) {
                imageIndex = Integer.parseInt(tags.get(i).get(1));
                showImage();
                setText();
                found = true;
                break;
            }
        }
        if (!found) {
            userInp.setText("Image not Found");
        }
    }

    public void setText() {
        TextInputEditText userInp = findViewById(R.id.commentInner);
        userInp.setText("");

        for (int i = 0; i < tags.size(); i++) {
            if(tags.get(i).contains(Integer.toString(imageIndex))) {
//                Log.d("Found", "FOUND");
                userInp.setText(tags.get(i).get(0));
            }
        }
    }
    public void openCamera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }
    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }

        return listOfAllImages;
    }
}
