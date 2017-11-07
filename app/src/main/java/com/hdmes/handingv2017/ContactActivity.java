package com.hdmes.handingv2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button callbutton = (Button) findViewById(R.id.button7);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:15137961846"));
                startActivity(intent);
            }
        });
    }
}
