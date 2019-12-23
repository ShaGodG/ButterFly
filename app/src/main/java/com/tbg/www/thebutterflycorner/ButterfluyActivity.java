package com.tbg.www.thebutterflycorner;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

public class ButterfluyActivity extends AppCompatActivity {
    Toolbar mToolbar;
    GridView GridView;
    Dialog myDialog;
    ArrayList<String> gridViewString;
    ArrayList<Integer>gridViewImageId;
    ArrayList<String>gridViewStringDesc;
    ArrayList<String> dialogScientificName;
    ArrayList<String>dialogFamily;
    ArrayList<String>dialogRank;
    ArrayList<String>dialogOrder;
    static final String IMAGE_URL="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfluy);
        gridViewString = new ArrayList<>(Arrays.asList("Acraea Violae","Cethosia Cyane","Danaus Chrysippus","Graphium Agamemnon","Hypolimnas Bolina","Pachiliopta Aristolochiae","Papilio Demoleus","Papilio Memnon","Parthenos Slyvia"
        ));
        getSupportActionBar().setTitle("Butterflies");

        gridViewImageId = new ArrayList<>(Arrays.asList( R.drawable.av1,R.drawable.cc1, R.drawable.dc1,
                R.drawable.ga1, R.drawable.hb1, R.drawable.pa1,
                R.drawable.pd1, R.drawable.pm1, R.drawable.ps1));

        

       gridViewStringDesc =new ArrayList<>(Arrays.asList(
               "Acraea Violae, the tawny coster, is a small, 53–64 millimetres, leathery-winged butterfly common in grassland and scrub habitats. It belongs to the Nymphalidae or brush-footed butterfly family. It has a weak fluttery flight. It is avoided by most insect predators.",
               "Cethosia cyane, the leopard lacewing, is a species of heliconiine butterfly found from India to southern China, and Indochina. Its range has expanded in the last few decades, and its arrival in the southern part of the Malay Peninsula, including Singapore, is relatively recent.",
               "Danaus chrysippus, also known as the plain tiger or African queen, is a medium-sized butterfly widespread in Asia, Australia and Africa. It belongs to the Danainae subfamily of the brush-footed butterfly family Nymphalidae. Danainae primarily consume plants in the genus Asclepias, more commonly called milkweed.",
               "Graphium agamemnon, the tailed jay, is a predominantly green and black tropical butterfly that belongs to the swallowtail family. The butterfly is also called the green-spotted triangle, tailed green jay, or green triangle.",
               "Hypolimnas bolina, the great eggfly, common eggfly or in New Zealand the blue moon butterfly is a species of nymphalid butterfly found from Madagascar to Asia and Australia.",
               "Pachliopta aristolochiae, the common rose, is a swallowtail butterfly belonging to the genus Pachliopta, the roses, or red-bodied swallowtails. It is a common butterfly which is extensively distributed across south and southeast Asia.",
               "Papilio demoleus is a common and widespread swallowtail butterfly. The butterfly is also known as the lime butterfly, lemon butterfly, lime swallowtail, and chequered swallowtail. These common names refer to their host plants, which are usually citrus species such as the cultivated lime.",
               "Papilio memnon, the great Mormon, is a large butterfly native to southern Asia that belongs to the swallowtail family. It is widely distributed and has thirteen subspecies. The female is polymorphic and with mimetic forms.",
               "Parthenos sylvia, the clipper, is a species of nymphalid butterfly found in south and southeast Asia, mostly in forested areas. The clipper is a fast-flying butterfly and has a habit of flying with its wings flapping stiffly between the horizontal position and a few degrees below the horizontal."

       ));

        dialogScientificName = new ArrayList<>(Arrays.asList("Acraea terpsicore","Cethosia cyane","Danaus chrysippus","Graphium agamemnon","Hypolimnas bolina","Pachliopta aristolochiae","Papilio demoleus","Papilio memnon","Parthenos sylvia"));

        dialogRank = new ArrayList<>(Arrays.asList("Species","Species","Species","Species","Species","Species","Species","Species","Species"));

        dialogFamily = new ArrayList<>(Arrays.asList("Nymphalidae","Nymphalidae","Nymphalidae","Papilionidae","Nymphalidae","Papilionidae","Papilionidae","Papilionidae","Nymphalidae"));

        dialogOrder = new ArrayList<>(Arrays.asList("Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera","Lepidoptera"));
        CustomGridAdapter adapterViewAndroid = new CustomGridAdapter(ButterfluyActivity.this, gridViewString, gridViewImageId,gridViewStringDesc);
        GridView=(GridView)findViewById(R.id.grid_view_image_text);
        GridView.setAdapter(adapterViewAndroid);

        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                int pos= gridViewImageId.get(i);
                ShowDialogBox(i);

            }
        });



    }
    public void ShowDialogBox(final int i){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.desc_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView dialogName= dialog.findViewById(R.id.dialog_name);
        TextView dialogDesc =dialog.findViewById(R.id.textviewDesc);
        final ImageView imgDesc= dialog.findViewById(R.id.dialog_icon);
        TextView tvSpecificName = dialog.findViewById(R.id.tvSpecificName);
        TextView rankInput = dialog.findViewById(R.id.rankInput);
        TextView familyInput = dialog.findViewById(R.id.familyInput);
        TextView orderInput = dialog.findViewById(R.id.orderInput);
        ImageView dialogIcon = dialog.findViewById(R.id.dialog_icon);
        ImageView dialogShare =dialog.findViewById(R.id.shareButton);

        dialogName.setText(gridViewString.get(i));
        dialogDesc.setText(gridViewStringDesc.get(i));
        imgDesc.setImageResource(gridViewImageId.get(i));
        tvSpecificName.setText(dialogScientificName.get(i));
        rankInput.setText(dialogRank.get(i));
        familyInput.setText(dialogFamily.get(i));
        orderInput.setText(dialogOrder.get(i));
        
        dialogShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable;
                Bitmap bitmap1;
//write this code in your share button or function

                bitmapDrawable = (BitmapDrawable) imgDesc.getDrawable();// get the from imageview or use your drawable from drawable folder
                bitmap1 = bitmapDrawable.getBitmap();
                String imgBitmapPath= MediaStore.Images.Media.insertImage(getContentResolver(),bitmap1,"The Butterfly Corner",null);
                Uri imgBitmapUri=Uri.parse(imgBitmapPath);
                String shareText="*"+gridViewString.get(i)+"*"+"\n"+gridViewStringDesc.get(i);
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent,"Share using"));
            }
        });





        dialogIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageDialogBox(i);
            }
        });

        dialog.show();


    }

    private void showImageDialogBox(int i) {

        Dialog dialogInside = new Dialog(this);

        dialogInside.setContentView(R.layout.image_butterflies);
        dialogInside.setCanceledOnTouchOutside(true);
        dialogInside.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imgView1 = dialogInside.findViewById(R.id.imgView1);


        switch (i){

            case 0:
                imgView1.setImageResource(R.drawable.av1);
                break;
            case 1:

                imgView1.setImageResource(R.drawable.cc1);
                break;
            case 2:

                imgView1.setImageResource(R.drawable.dc1);
                break;
            case 3:

                imgView1.setImageResource(R.drawable.ga1);
                break;

            case 4:

                imgView1.setImageResource(R.drawable.hb1);
                break;
            case 5:

                imgView1.setImageResource(R.drawable.pa1);
                break;
            case 6:

                imgView1.setImageResource(R.drawable.pd1);
                break;
            case 7:

                imgView1.setImageResource(R.drawable.pm1);
                break;
            case 8:

                imgView1.setImageResource(R.drawable.ps1);
                break;

        }
        dialogInside.show();
    }
    }
