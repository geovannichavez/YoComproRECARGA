package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Amount;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.RequestTopupPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRequestTopupPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.OperatorsAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogCreator;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogScenarios;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;

import java.util.HashMap;
import java.util.List;

public class RequestTopup extends AppCompatActivity implements RequestTopupView
{
    //Adapters y Layouts
    Button btnEnvar;
    EditText etCodeNumber;
    EditText etExplPhone;
    Toolbar mToolbar;
    ToggleButton btnMyNumber;
    TextView lblSelectedAmount;
    OperatorsAdapter mOperatorsAdapter;
    GridView mOperatorsGridView;
    LinearLayout lnrSelectAmount;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog progressDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //MVP
    IRequestTopupPresenter presenter;

    //Global variables
    Amount selectedAmount;
    Validation mValidator;
    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_topup);
        mToolbar = (Toolbar) findViewById(R.id.requestTopupToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEnvar = (Button) findViewById(R.id.btnEnvar);
        etCodeNumber = (EditText) findViewById(R.id.etCodeNumber);
        etExplPhone = (EditText) findViewById(R.id.etExplPhone);
        lnrSelectAmount = (LinearLayout) findViewById(R.id.lnrSelectAmount);
        lblSelectedAmount = (TextView) findViewById(R.id.lblSelectedAmount);
        btnMyNumber = (ToggleButton) findViewById(R.id.btnMyNumber);
        mOperatorsGridView = (GridView) findViewById(R.id.gvOperadores);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mUserData = new UserData(this);
        mValidator = new Validation(this, coordinatorLayout);
        presenter = new RequestTopupPresenterImpl(this, this, this);
        presenter.setInitialViewState();
        presenter.fetchOperators();

    }

    @Override
    public void renderOperators(final List<CountryOperator> countryOperators)
    {
        mOperatorsAdapter = new OperatorsAdapter(this, R.layout.custom_operator_gridview_item);
        mOperatorsAdapter.notifyDataSetChanged();
        mOperatorsGridView.setNumColumns(countryOperators.size());
        mOperatorsGridView.setAdapter(mOperatorsAdapter);
        mOperatorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final CountryOperator operator = ((CountryOperator) parent.getItemAtPosition(position));
                presenter.onOperatorSelected(position);
                presenter.createRequestTopupObject().setOperatorId(String.valueOf(operator.getOperatorID()));

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
    public void setInitialViewsState()
    {
        try
        {
            //Setea 'formater' en el EditText y limpia campos
            mValidator.setPhoneInutFormatter(etExplPhone);
            lblSelectedAmount.setEnabled(false);
            etExplPhone.setEnabled(true);
            etExplPhone.setText("");
            etExplPhone.clearFocus();
            etCodeNumber.setText("");
            etCodeNumber.clearFocus();
            btnMyNumber.setChecked(false);

            //Resetea el MONTO
            lblSelectedAmount.setText(getString(R.string.spinner_select));
            lblSelectedAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            lblSelectedAmount.setTypeface(null, Typeface.NORMAL);
            lnrSelectAmount.setOnClickListener(null);

            //Setea el SwipeRefreshLayout
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3, R.color.SubtitleTextColor);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    presenter.refreshOperators();
                }
            });


            //Deselecciona el operador del GridView
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
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
                    presenter.createRequestTopupObject().setAmount(selectedAmount.getAmount());
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
    public void showGenericMessage(DialogViewModel pMessageModel)
    {
        CreateDialog(pMessageModel.getTitle(), pMessageModel.getLine1(), pMessageModel.getAcceptButton());
    }

    @Override
    public void showLoadingDialog(String pLabel)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(pLabel);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void toggleShowRefreshing(boolean pSetRefreshing)
    {
        try
        {
            //Cambia la altura del spinner
            //mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mSwipeRefreshLayout.setRefreshing(pSetRefreshing);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showSuccessMessage(DialogViewModel pDialogModel)
    {
        CustomDialogCreator.Builder dialogCreator = new CustomDialogCreator.Builder(this, this);
        dialogCreator.setTitle(pDialogModel.getTitle())
                .setMessageLine1(pDialogModel.getLine1())
                .setMessageLine2(pDialogModel.getLine2())
                .setButton(pDialogModel.getAcceptButton())
                .setInteraction(CustomDialogScenarios.SUCCESS)
                .build();

    }

    @Override
    public void showErrorMessage(DialogViewModel pMessageModel)
    {
        CustomDialogCreator.Builder dialogCreator = new CustomDialogCreator.Builder(this, this);
        dialogCreator.setTitle(pMessageModel.getTitle())
                .setMessageLine1(pMessageModel.getLine1())
                .setMessageLine2(pMessageModel.getLine2())
                .setButton(pMessageModel.getAcceptButton())
                .setInteraction(CustomDialogScenarios.ERROR)
                .build();
    }

    public void setSelectedAmount(Amount pSelected)
    {
        lblSelectedAmount.setText(pSelected.getDescription());
        lblSelectedAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        lblSelectedAmount.setTypeface(null, Typeface.BOLD);
    }

    public void sendTopupRequest(View view)
    {
        if(CheckValidation())
        {
            presenter.createRequestTopupObject().setMSISDN(etExplPhone.getText().toString());
            presenter.createRequestTopupObject().setVendorCode(etCodeNumber.getText().toString());
            presenter.sendTopupRequest();
        }
    }

    public void toggleNumber(View view)
    {
        boolean isChecked = ((ToggleButton) view).isChecked();
        try
        {
            if (isChecked)
            {
                String userPhone = mUserData.GetUserFormattedPhone();
                etExplPhone.setEnabled(false);
                etExplPhone.setText(userPhone);
                etExplPhone.clearFocus();
            }
            else
            {
                etExplPhone.setText("");
                etExplPhone.setEnabled(true);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /*
    *
    *
    *   OTROS METODOS
    *
    */
    public void CreateDialog(String pTitle, String pMessage, String pButton)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RequestTopup.this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void CreateSnackbar(String pLine)
    {
        Snackbar mSnackbar = Snackbar.make(coordinatorLayout, pLine, Snackbar.LENGTH_LONG);
        View snackbarView = mSnackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this,  R.color.materia_error_700));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        mSnackbar.show();
    }

    private boolean CheckValidation()
    {
        boolean valid = true;

        if(presenter.createRequestTopupObject() == null)
        {
            return false;
        }

        if (!mValidator.isPhoneNumber(etExplPhone, true))
        {
            return false;
        }

        if(TextUtils.isEmpty(presenter.createRequestTopupObject().getOperatorId()))
        {
            CreateSnackbar(getString(R.string.validation_required_operator));
            return false;
        }

        if(selectedAmount == null)
        {
            CreateSnackbar(getString(R.string.validation_required_amount));
            return false;
        }

        if(!mValidator.isVendorCode(etCodeNumber, true))
        {
            return false;
        }

        return valid;
    }

}
