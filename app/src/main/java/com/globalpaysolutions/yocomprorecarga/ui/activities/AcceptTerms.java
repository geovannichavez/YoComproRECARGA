package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.AcceptTermsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;

public class AcceptTerms extends AppCompatActivity implements AcceptTermsView
{
    Button btnAccept;
    AcceptTermsPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);

        presenter = new AcceptTermsPresenterImpl(this, this, this);

        btnAccept = (Button) findViewById(R.id.btnAcceptTerms);

    }

    public void acceptTerms(View view)
    {
        presenter.acceptTerms();
        Intent accept = new Intent(AcceptTerms.this, ValidatePhone.class);
        startActivity(accept);
    }

}
