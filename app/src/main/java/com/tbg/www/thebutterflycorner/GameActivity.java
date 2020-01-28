package com.tbg.www.thebutterflycorner;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class GameActivity extends AppCompatActivity {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    //private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    // key to store image path in savedInstance state
    public static  final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    //public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    GridView gridView;
    ArrayList<String>gridViewStringDesc;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "TheButterflyCorner";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";

    private static String imageStoragePath;

    ArrayList<ModelCompare>my_list;

    String statusImage="";
    int imageResult;

   ArrayList<ModelResult> allImagePaths;

    private TextView txtDescription;
    private TextView txtDescription1;
    private ImageView imgPreview;
    RequestQueue requestQueue;
    String result;

    private TextView scoreTextView;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private Button btnCapturePicture, btnCompare,btnReset,btnResult;
    private static final String SERVER_URL ="http://gandharva19.pythonanywhere.com";
    public int counter=0;
    public int score=0;

    FirebaseFirestore db;
    Map<String, Object> coupon;

    FloatingActionButton help;

    @Override
    protected void onResume() {
        super.onResume();
       }

    public void update(){

        String text=counter+"/ 5";
        scoreTextView.setText( text);
        btnCapturePicture.setAlpha(1f);
        btnCapturePicture.setEnabled(true);
        btnCompare.setAlpha(.5f);
        btnCompare.setEnabled(false);
        if(counter==5){
            btnCompare.setAlpha(.5f);
            btnCompare.setEnabled(false);
            btnCapturePicture.setAlpha(.5f);
            btnCapturePicture.setEnabled(false);
            btnResult.setAlpha(1f);
            btnResult.setEnabled(true);
        }
    }
    ButterflyFragment butterflyFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        requestQueue = Volley.newRequestQueue(this);
        my_list=new ArrayList<>();
        counter=0;
        score=0;
        //dialog fragments
        fm = getSupportFragmentManager();
        db = FirebaseFirestore.getInstance();

//        help = findViewById(R.id.helpbtn);

        butterflyFragment= new ButterflyFragment();
        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
        allImagePaths=new ArrayList<>();
        txtDescription = findViewById(R.id.txt_descs);
        txtDescription1 = findViewById(R.id.txt_descs1);
        imgPreview = findViewById(R.id.imgPreviews);
        btnReset=findViewById(R.id.btnReset);
        btnResult=findViewById(R.id.btnResult);
        scoreTextView=findViewById(R.id.score);
        btnCapturePicture = findViewById(R.id.btnCapturePicture);
        btnCompare = findViewById(R.id.btnCompare);
        btnCompare.setAlpha(.5f);
        btnCompare.setEnabled(false);
        btnResult.setAlpha(.5f);
        btnResult.setEnabled(false);
        /**
         * Capture image on button click
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage();
                } else {
                    requestCameraPermission(MEDIA_TYPE_IMAGE);
                }
            }
        });

//        help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(GameActivity.this,HelpActivity.class);
//                startActivity(i);
//
//            }
//        });
        btnReset.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                builder.setTitle("Progress will be lost");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure, you want to reset the progress?");
                //This will not allow to close dialogbox until user selects an option
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        imgPreview.setImageDrawable(null);
                        counter = 0;
                        score=0;
                        String text=counter+"/ 5";
                        scoreTextView.setText( text);
                        btnCompare.setAlpha(.5f);
                        btnCompare.setEnabled(false);
                        btnResult.setAlpha(.5f);
                        btnResult.setEnabled(false);
                        btnCapturePicture.setAlpha(1f);
                        btnCapturePicture.setEnabled(true);
                        txtDescription.setVisibility(View.VISIBLE);
                        txtDescription1.setVisibility(View.VISIBLE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button

                        dialog.cancel();
                    }
                });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                //alert.setTitle("AlertDialogExample");
                alert.show();


            }
        });
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTaskRunner myTask = new AsyncTaskRunner();
                myTask.execute();
              //  Toast.makeText(getApplicationContext(),"Score: 100 ",Toast.LENGTH_SHORT);

//                try
//                {
//
//
//                    HttpClient client = new DefaultHttpClient();
//                    HttpPost post = new HttpPost("http://gandharva19.pythonanywhere.com");
//
//                    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
//                    entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//                    entityBuilder.addTextBody("username", "shasha");
//                    File file = new File(imageStoragePath);
//                    File compressedImgFile = new Compressor(getApplicationContext()).compressToFile(file);
//
//                    System.out.println("==============================="+file);
//                    System.out.println("==============================="+imageStoragePath);
//
//                    entityBuilder.addBinaryBody("file", compressedImgFile);
//                    System.out.println("==============================="+entityBuilder);
//
//                    HttpEntity entity = entityBuilder.build();
//                    post.setEntity(entity);
//                    HttpResponse response = client.execute(post);
//                    HttpEntity httpEntity = response.getEntity();
//                    result = EntityUtils.toString(httpEntity);
//                    Log.v("result", result);
//                    Toast.makeText(GameActivity.this, result, Toast.LENGTH_SHORT).show();
//                    setResult(result);
//
//                    Bundle args = new Bundle();
//                    args.putString("name",result);
//                    FragmentTransaction ft=fm.beginTransaction();
//
//                    butterflyFragment.setArguments(args);
//                    ft.addToBackStack("butterflies");
//                    ft.add(butterflyFragment, "butterflies");
//                    ft.commit();
//
//
//                }
//                catch(Exception e)
//                {
//                    e.printStackTrace();
//                }
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                btnResult.setAlpha(0.5f);
                btnResult.setEnabled(false);

                Intent i = new Intent(GameActivity.this,ResultActivity.class);
                i.putExtra("list",allImagePaths);
                startActivity(i);

                db.collection("coupons")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int c=0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> coupon=document.getData();
                                        if(coupon.get("userId").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                          c++;
                                        }

                                    }

                                    if(c<6){
                                        if (score >= 3){
                                            final Dialog dialogQr = new Dialog(GameActivity.this);
                                            dialogQr.setContentView(R.layout.layout_qr_code);
                                            ImageView imgBarcode = dialogQr.findViewById(R.id.imgQrCode);

                                            JSONObject embeddedObj = new JSONObject();
                                            try {

                                                // embeddedObj.put("code", UUID.randomUUID().toString());
                                                String couponId=UUID.randomUUID().toString();
                                                embeddedObj.put("couponId",couponId );
                                                embeddedObj.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                embeddedObj.put("used","0");
                                                embeddedObj.put("userEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                                // Whatever you need to encode in the QR code
                                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                                                BitMatrix bitMatrix = multiFormatWriter.encode(embeddedObj.toString(), BarcodeFormat.QR_CODE, 200, 200);
                                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                imgBarcode.setImageBitmap(bitmap);
                                                coupon = new HashMap<>();
                                                coupon.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                coupon.put("couponId",couponId);
                                                coupon.put("used","0");

                                            } catch (WriterException e) {
                                                e.printStackTrace();
                                            }catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            dialogQr.show();
                                            storetoDB(coupon);

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Better Luck Next time",Toast.LENGTH_LONG).show();
                                            finish();

                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Maximum coupon limit reached", Toast.LENGTH_LONG).show();
                                    }



                                } else {
                                    Log.w("bhenchod", "Error getting documents.", task.getException());
                                }
                            }
                        });



            }
        });

        restoreFromBundle(savedInstanceState);
    }

    void storetoDB(Map<String, Object> coupon){


// Add a new document with a generated ID
        db.collection("coupons")
                .add(coupon)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("bc", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("bc", "Error adding document", e);
                    }
                });
    }



    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    previewCapturedImage();
                }
            }
        }
    }

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }



    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }
        Log.v("ImagePath",imageStoragePath);

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    public void updateResult(String inputText,int imageResultfrom){

        allImagePaths.add(new ModelResult(imageStoragePath,inputText,imageResultfrom));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // Refreshing the gallery


                    CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                    // successfully captured the image
                    // display it in image view
                    btnCompare.setAlpha(1f);
                    btnCompare.setEnabled(true);
                    btnCapturePicture.setAlpha(.5f);
                    btnCapturePicture.setEnabled(false);
                    previewCapturedImage();
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Progress will be lost");
        builder.setMessage("Are you sure you want to go back?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameActivity.super.onBackPressed();

            }
        });
        builder.setNegativeButton("No",null);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }



    /**
     * Saving stored image path to saved instance state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }




    /**
     * Display image from gallery
     */
    private void previewCapturedImage() {
        try {
            // hide video preview
            txtDescription.setVisibility(View.GONE);
            txtDescription1.setVisibility(View.GONE);
            imgPreview.setVisibility(View.VISIBLE);


            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            imgPreview.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            CameraUtils.openSettings(GameActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }



    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            try {


                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://gandharva19.pythonanywhere.com");

                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                entityBuilder.addTextBody("username", "shasha");
                File file = new File(imageStoragePath);
                File compressedImgFile = new Compressor(getApplicationContext()).compressToFile(file);

                System.out.println("===============================" + file);
                System.out.println("===============================" + imageStoragePath);

                entityBuilder.addBinaryBody("file", compressedImgFile);
                System.out.println("===============================" + entityBuilder);

                HttpEntity entity = entityBuilder.build();
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                Log.v("result", result);
//                Toast.makeText(GameActivity.this, result, Toast.LENGTH_SHORT).show();
                setResult(result);

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "1";
        }




        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            Bundle args = new Bundle();
            args.putString("name",result);
            FragmentTransaction ft=fm.beginTransaction();

            butterflyFragment.setArguments(args);
            ft.addToBackStack("butterflies");
            ft.add(butterflyFragment, "butterflies");
            ft.commit();

        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(GameActivity.this,"Please wait","Fetching Results..");

        }



    }


}