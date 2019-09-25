package com.tbg.www.thebutterflycorner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgView;
    private String mCurrentPhotoPath;
    private static final String SERVER_ADDRESS="http://akzarma1.pythonanywhere.com/";
    Button imgBtn;
    Button compareImg;
    EditText playerName;
    Bitmap bitmap;

    private final int requestCode = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("Play Game");
        imgView  = findViewById(R.id.gridViewImages);
        playerName = findViewById(R.id.userName);
        imgBtn =  findViewById(R.id.imgButton);
        compareImg = findViewById(R.id.nxtButton);

        EnableRuntimePermission();
        imgBtn.setOnClickListener(this);
        compareImg.setOnClickListener(this);








    }

    private void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(GameActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(GameActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(GameActivity.this,new String[]{
                    Manifest.permission.CAMERA}, requestCode);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            bitmap = (Bitmap)data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case requestCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(GameActivity.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(GameActivity.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgButton:
                Intent i  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,requestCode);
                break;
            case R.id.nxtButton:
                new UploadImage(bitmap,playerName.getText().toString()).execute();
                break;

        }
    }

    private class UploadImage extends AsyncTask<Void,Void,Void>{

        Bitmap image;
        String name;

        public UploadImage(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
            ArrayList<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("image", encodeImage));
            params.add(new BasicNameValuePair("name",name));

            HttpParams httpParams=getHttpRequestParams();
            HttpClient client=new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS);
            try {

                post.setEntity(new UrlEncodedFormEntity(params));
                client.execute(post);

            }catch (Exception e){
                Log.d("Tag","error in uploading");
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(),"Data uploaded",Toast.LENGTH_SHORT);
        }
    }

    private HttpParams getHttpRequestParams(){

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,1000*30);
        HttpConnectionParams.setSoTimeout(httpParams,1000*30);
        return  httpParams;
    }
}
