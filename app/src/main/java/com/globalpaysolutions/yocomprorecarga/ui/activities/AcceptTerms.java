package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.globalpaysolutions.yocomprorecarga.R;

public class AcceptTerms extends AppCompatActivity
{
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);

        btnAccept = (Button) findViewById(R.id.btnAcceptTerms);
    }

    public void acceptTerms(View view)
    {
        Intent accept = new Intent(AcceptTerms.this, ValidatePhone.class);
        startActivity(accept);
    }
}
