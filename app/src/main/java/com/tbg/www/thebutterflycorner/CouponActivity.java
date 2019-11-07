package com.tbg.www.thebutterflycorner;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    class Coupon{

        String couponId;
        int couponName;

        public Coupon(String couponId, int couponName) {
            this.couponId = couponId;
            this.couponName = couponName;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public int getCouponName() {
            return couponName;
        }

        public void setCouponName(int couponName) {
            this.couponName = couponName;
        }
    }

    FirebaseFirestore db;
    ArrayList<Coupon> usedCoupons;
    ArrayList<Coupon> unusedCoupons;
    FloatingActionButton Play;
    Spinner spinner;


    public ArrayList<String> coup(ArrayList<Coupon> coupons){
        ArrayList<String> couponNos=new ArrayList<>();

        for(Coupon coupon:coupons){
            couponNos.add("Coupon" + coupon.getCouponName());
        }
        return couponNos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        getSupportActionBar().setTitle("Coupons");
        db = FirebaseFirestore.getInstance();
        usedCoupons=new ArrayList<>();
        unusedCoupons=new ArrayList<>();
        spinner=findViewById(R.id.spiinerCoupon);
        final ArrayAdapter[] adapter = {new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coup(unusedCoupons))};

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        listView.setDividerHeight(1);
        listView.setEmptyView(findViewById(R.id.placeHolder));

        final String[] spinnerItems={"Unused Coupons","Used Coupons"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinnerItems);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    adapter[0] = new ArrayAdapter<String>(CouponActivity.this,android.R.layout.simple_list_item_1, coup(unusedCoupons));
                }
                else{
                    adapter[0] = new ArrayAdapter<String>(CouponActivity.this,android.R.layout.simple_list_item_1, coup(usedCoupons));

                }
                listView.setAdapter(adapter[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Play=findViewById(R.id.playbtn);

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CouponActivity.this,GameActivity.class);
                startActivity(i);
                finish();

            }
        });




        db.collection("coupons")
                .whereEqualTo("userId",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> coupon=document.getData();
                                if(coupon.get("used").equals("0")){
                                    unusedCoupons.add(new Coupon(String.valueOf(coupon.get("couponId")),unusedCoupons.size()+1));

                                }
                                else
                                    usedCoupons.add(new Coupon(String.valueOf(coupon.get("couponId")),usedCoupons.size()+1));


                                Log.d("bhenchod", document.getId() + " => " + document.getData());
                                adapter[0] = new ArrayAdapter<String>(CouponActivity.this,android.R.layout.simple_list_item_1, coup(unusedCoupons));

                                listView.setAdapter(adapter[0]);

                            }
//                            Log.d("Bhendi",coupons.toString()+"xxxx"+coupons.size());

                        } else {
                            Log.w("bhenchod", "Error getting documents.", task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String code="";
                if(spinner.getSelectedItemPosition()==0) {
                    code = unusedCoupons.get(position).getCouponId();
                    final Dialog dialogQr = new Dialog(CouponActivity.this);
                    dialogQr.setContentView(R.layout.layout_qr_code);
                    ImageView imgBarcode = dialogQr.findViewById(R.id.imgQrCode);

                    JSONObject embeddedObj = new JSONObject();
                    try {


                        embeddedObj.put("couponId", code);
                        embeddedObj.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        embeddedObj.put("userEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
                else{
                    Toast.makeText(getApplicationContext(),"Coupon Already Used!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
