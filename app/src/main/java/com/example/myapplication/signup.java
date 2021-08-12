package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity
{
    EditText mail,mophone,pswd,usrusr;
    TextView lin,sup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sup = (TextView) findViewById(R.id.sup);
       // lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
       // pswd = (EditText) findViewById(R.id.pswrdd);
        mail = (EditText) findViewById(R.id.mail);
        mophone = (EditText) findViewById(R.id.mobphone);
        Typeface custom_font;
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        mophone.setTypeface(custom_font1);
        sup.setTypeface(custom_font1);
      //  pswd.setTypeface(custom_font);
        //lin.setTypeface(custom_font1);
        usrusr.setTypeface(custom_font1);
        mail.setTypeface(custom_font1);
        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( usrusr.getText().toString().isEmpty()&& usrusr.getText().toString().isEmpty()) {
                    Toast.makeText(signup.this, "Please enter resercher username", Toast.LENGTH_SHORT).show();
                    // ed1.setTextColor(Color.parseColor("#bf170b"));
                    return;
                }
                Intent it = new Intent(signup.this,MainActivity.class);
                it.putExtra("metadataassistantusername",usrusr.getText().toString());
                startActivity(it);
            }
        });
    }
}
