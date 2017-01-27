package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Amount;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRequestTopupPresenter;
import com.globalpaysolutions.yocomprorecarga.presenters.RequestTopupPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.OperatorsAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogCreator;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestTopup extends AppCompatActivity implements RequestTopupView
{
    //Adapters y Layouts
    Button btnEnvar;
    EditText etCodeNumber;
    EditText etExplPhone;
    Button btnMyNumber;
    TextView lblSelectedAmount;
    OperatorsAdapter mOperatorsAdapter;
    GridView mOperatorsGridView;
    LinearLayout lnrSelectAmount;

    //MVP
    IRequestTopupPresenter presenter;

    //Global variables
    Amount selectedAmount;
    Validation mValidator;
    CustomDialogCreator mDialogCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_topup);

        btnEnvar = (Button) findViewById(R.id.btnEnvar);
        etCodeNumber = (EditText) findViewById(R.id.etCodeNumber);
        etExplPhone = (EditText) findViewById(R.id.etExplPhone);
        lnrSelectAmount = (LinearLayout) findViewById(R.id.lnrSelectAmount);
        lblSelectedAmount = (TextView) findViewById(R.id.lblSelectedAmount);
        btnMyNumber = (Button) findViewById(R.id.btnMyNumber);
        mOperatorsGridView = (GridView) findViewById(R.id.gvOperadores);

        mValidator = new Validation(this);
        mDialogCreator = new CustomDialogCreator(this, this);
        presenter = new RequestTopupPresenterImpl(this, this, this);
        presenter.setInitialViewState();
        presenter.fetchOperators();

    }

    @Override
    public void renderOperators(final List<CountryOperator> countryOperators)
    {
        mOperatorsAdapter = new OperatorsAdapter(this, R.layout.custom_operator_gridview_item);
        mOperatorsGridView.setNumColumns(countryOperators.size());
        mOperatorsGridView.setAdapter(mOperatorsAdapter);
        mOperatorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final CountryOperator operator = ((CountryOperator) parent.getItemAtPosition(position));
                presenter.onOperatorSelected(position);
                selectedAmount = null;
                lnrSelectAmount.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        presenter.selectAmount(operator);
                    }
                });
            }
        });

        //Llenado de items en el GridView
        try
        {
            for (CountryOperator item : countryOperators)
            {
                mOperatorsAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void initialViewsState()
    {
        mValidator.setPhoneInutFormatter(etExplPhone);
        lblSelectedAmount.setEnabled(false);
    }

    @Override
    public void showAmounts(final List<String> pAmountsNames, final HashMap<String, Amount> pAmountsMap )
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.label_title_select_amount_dialog));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, pAmountsNames);
        builder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String amountName = pAmountsNames.get(i);
                selectedAmount = pAmountsMap.get(amountName);
            }
        });

        builder.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(selectedAmount != null)
                {
                    setSelectedAmount(selectedAmount);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void setSelectedOperator(int position)
    {
        for (int i = 0; i < mOperatorsGridView.getAdapter().getCount(); i++)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            {
                mOperatorsGridView.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.custom_rounded_corner_operator));
            }
            else
            {
                mOperatorsGridView.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rounded_corner_operator));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            mOperatorsGridView.getChildAt(position).setBackground(getResources().getDrawable(R.drawable.custom_rounded_corner_selected));
        }
        else
        {
            mOperatorsGridView.getChildAt(position).setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_rounded_corner_selected));
        }
    }

    @Override
    public void resetAmount()
    {
        lblSelectedAmount.setEnabled(true);
        lblSelectedAmount.setText(getString(R.string.spinner_select));
        lblSelectedAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        lblSelectedAmount.setTypeface(null, Typeface.NORMAL);
    }

    @Override
    public void showSuccessMessage()
    {

    }

    public void setSelectedAmount(Amount pSelected)
    {
        lblSelectedAmount.setText(pSelected.getDescription());
        lblSelectedAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        lblSelectedAmount.setTypeface(null, Typeface.BOLD);
    }


}
