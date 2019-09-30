package com.tbg.www.thebutterflycorner;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CouponActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<String> coupons;
    ArrayList<String> couponNos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        getSupportActionBar().setTitle("Coupons");
        db = FirebaseFirestore.getInstance();
        coupons=new ArrayList<>();
        couponNos=new ArrayList<>();

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, couponNos);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        listView.setDividerHeight(1);

        db.collection("coupons")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> coupon=document.getData();
                                if(coupon.get("userId").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())&& coupon.get("used").equals("0")){
                                    coupons.add(String.valueOf(coupon.get("couponId")));
                                    couponNos.add("Coupon "+(couponNos.size()+1));
                                }

                                Log.d("bhenchod", document.getId() + " => " + document.getData());
                                listView.setAdapter(adapter);

                            }
                            Log.d("Bhendi",coupons.toString()+"xxxx"+coupons.size());

                        } else {
                            Log.w("bhenchod", "Error getting documents.", task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code=coupons.get(position);
                final Dialog dialogQr = new Dialog(CouponActivity.this);
                dialogQr.setContentView(R.layout.layout_qr_code);
                ImageView imgBarcode = dialogQr.findViewById(R.id.imgQrCode);

                JSONObject embeddedObj = new JSONObject();
                try {


                    embeddedObj.put("couponId",code );
                    embeddedObj.put("userId",FirebaseAuth.getInstance().getCurrentUser().getUid());

                    // Whatever you need to encode in the QR code
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    BitMatrix bitMatrix = multiFormatWriter.encode(embeddedObj.toString(), BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imgBarcode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialogQr.show();
            }
        });

    }
}
