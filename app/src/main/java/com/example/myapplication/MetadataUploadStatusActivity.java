package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class MetadataUploadStatusActivity extends AppCompatActivity {

    //metalanguagecode= uploadstatusprofile.getStringExtra("metalanguagecode");

    String metaassistantname, metaintervieweename,metalocation,metainterveweegender, metalanguage,
            meta_agegroup,metalanguagecode,metacategory,metatopic,metasource,metainterviewdate,metadatacode;

    TextView textViewmetaprofilecategory,textviewmetaprofilresercheassistant,textviewspeakerlanguage,textviewmetaprofileintervieweeagegroup,textviewmetaprofileinterviewmetacode,textviewmetaprofilemetainterveweeagegroup
            ,textViewmetaprofilesource,textViewmetaprofiletopic,textviewmetaprofilelocation,textviewmetaprofileintervieweename,textviewmetaprofileinterviewlangagecode,
            textViewmetaprofileinterviewdate,textviewmetaprofilemetainterveweegender,textviewmetauploadfilelink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent uploadstatusprofile=getIntent();
        Intent intent = getIntent();
        setContentView(R.layout.activity_metadata_upload_status);
        metadatacode=intent.getStringExtra("metadatacode");
        metaassistantname=intent.getStringExtra("metaassistantname");
        metaintervieweename=intent.getStringExtra("metaintervieweename");
        metalocation=intent.getStringExtra("metalocation");
        metainterveweegender=intent.getStringExtra("metainterveweegender");
        meta_agegroup=intent.getStringExtra("meta_agegroup");
        metalanguagecode=intent.getStringExtra("metalanguagecode");
        metacategory=intent.getStringExtra("metacategory");
        metatopic=intent.getStringExtra("metatopic");
        metalanguage=intent.getStringExtra("metalanguage");
        metasource=intent.getStringExtra("metasource");
        //metadatacode=intent.getStringExtra("metacode");
        metainterviewdate=intent.getStringExtra("metainterviewdate");
        /////////////////////////////////////////

        textViewmetaprofilecategory=(TextView)findViewById(R.id.tvuploadprofilemetadataCategory);
        textViewmetaprofilecategory.setText(metacategory);

        textviewmetaprofileintervieweename=(TextView)findViewById(R.id.tvuploadprofilemetadataintervieweename);
        textviewmetaprofileintervieweename.setText(metaintervieweename);

        textviewmetaprofilresercheassistant=(TextView)findViewById(R.id.tvuploadprofilemetadatresearchassistant);
        textviewmetaprofilresercheassistant.setText(metaassistantname);

        textviewmetaprofileintervieweeagegroup=(TextView)findViewById(R.id.tvuploadprofilemetadata_interviewee_agegroup);
        textviewmetaprofileintervieweeagegroup.setText(meta_agegroup);

        textviewmetaprofilelocation=(TextView)findViewById(R.id.tvuploadprofilemetadatalocation);
        textviewmetaprofilelocation.setText(metalocation);

        textviewmetaprofilemetainterveweegender=(TextView)findViewById(R.id.tvuploadgender);
        textviewmetaprofilemetainterveweegender.setText("Gender : "+metainterveweegender);

        textviewspeakerlanguage=(TextView)findViewById(R.id.tvuploadprofilemetadatainterviewlanguage);
        textviewspeakerlanguage.setText("Language: "+metalanguagecode);
        textViewmetaprofileinterviewdate=(TextView)findViewById(R.id.tvprofilemetadatadate);
        textViewmetaprofileinterviewdate.setText(metainterviewdate);



        textViewmetaprofiletopic=(TextView)findViewById(R.id.tvuploadprofilemetadatatopic);
        textViewmetaprofiletopic.setText(metatopic);

        textViewmetaprofilesource=(TextView)findViewById(R.id.tvuploadprofilemetadata_datasource);
        textViewmetaprofilesource.setText("Data Source: "+metasource);


       /* textviewmetaprofileinterviewmetacode.setText("Record Code:  "+metadatacode);
         textviewmetaprofileinterviewmetacode=(TextView)findViewById(R.id.tvprofilemetadatainterviewcode);
   textviewmetauploadfilelink=(TextView)findViewById(R.id.tvuplodprofilemetadata_status);
        */

        //////////////////////////////////////////////////////////////////////////////////////////////////
    }

}
