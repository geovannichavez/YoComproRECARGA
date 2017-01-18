package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRequestTopupPresenter;
import com.globalpaysolutions.yocomprorecarga.presenters.RequestTopupPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.OperatorsAdapter;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;

import java.util.List;

public class RequestTopup extends AppCompatActivity implements RequestTopupView
{
    //Adapters y Layouts
    Button btnEnvar;
    EditText etCodeNumber;
    EditText etExplPhone;
    Spinner spMontoRecarga;
    Button btnMyNumber;
    OperatorsAdapter mOperatorsAdapter;
    GridView mOperatorsGridView;

    //MVP
    IRequestTopupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_topup);

        presenter = new RequestTopupPresenterImpl(this, this, this);
        presenter.setInitialViewState();
        presenter.fetchOperators();

        btnEnvar = (Button) findViewById(R.id.btnEnvar);
        etCodeNumber = (EditText) findViewById(R.id.etCodeNumber);
        etExplPhone = (EditText) findViewById(R.id.etExplPhone);
        spMontoRecarga = (Spinner) findViewById(R.id.spMontoRecarga);
        btnMyNumber = (Button) findViewById(R.id.btnMyNumber);
        mOperatorsGridView = (GridView) findViewById(R.id.gvOperadores);



    }


    @Override
    public void renderOperators(List<CountryOperator> countryOperators)
    {
        mOperatorsAdapter = new OperatorsAdapter(this, R.layout.custom_operator_gridview_item);
        mOperatorsGridView.setNumColumns(countryOperators.size());
        mOperatorsGridView.setAdapter(mOperatorsAdapter);
        mOperatorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });

        for (CountryOperator item : countryOperators)
        {
            mOperatorsAdapter.add(item);
        }
    }

    @Override
    public void initialViewsState()
    {

    }
}
