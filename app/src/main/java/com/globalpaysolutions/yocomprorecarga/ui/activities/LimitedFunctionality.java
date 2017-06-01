package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.LimitedFunctionalityPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.LimitedFunctionalityView;

public class LimitedFunctionality extends AppCompatActivity implements LimitedFunctionalityView
{
    TextView tvComponents;
    Button btnWhatever;

    //MVP
    LimitedFunctionalityPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_functionality);

        tvComponents = (TextView) findViewById(R.id.tvComponents);
        btnWhatever = (Button) findViewById(R.id.btnWhatever);

        mPresenter = new LimitedFunctionalityPresenterImpl(this, this, this);

        mPresenter.enlistMissingComponents();

        btnWhatever.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.navigateNext();
            }
        });

    }

    @Override
    public void displayMissingComponents(String pComponents)
    {
       try
       {
           String componentsText = String.format(getString(R.string.label_limited_functionality_content), pComponents);
           tvComponents.setText(componentsText);
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
    }

    @Override
    public void navigateNextActivity()
    {
        try
        {
            Intent navigate = new Intent(this, Home.class);
            startActivity(navigate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
