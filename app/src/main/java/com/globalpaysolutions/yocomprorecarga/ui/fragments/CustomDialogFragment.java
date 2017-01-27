package com.globalpaysolutions.yocomprorecarga.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogScenarios;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment
{
    private static final String TAG = CustomDialogFragment.class.getSimpleName();
    TextView tvTitle;
    TextView tvLine1;
    TextView tvLine2;
    TextView tvLine3;
    Button btnDismiss;
    ImageView ivResult;

    public CustomDialogFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_custom_dialog, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tvDialogTitle);
        tvLine1 = (TextView) view.findViewById(R.id.tvFirstLine);
        tvLine2 = (TextView) view.findViewById(R.id.tvSecondLine);
        tvLine3 = (TextView) view.findViewById(R.id.tvThirdLine);
        ivResult = (ImageView) view.findViewById(R.id.ivResult);
        btnDismiss = (Button) view.findViewById(R.id.btnDismiss);

        String Title = this.getArguments().getString("Title");
        String Line1 = this.getArguments().getString("Line1");
        String Line2 = this.getArguments().getString("Line2");
        String Line3 = this.getArguments().getString("Line3");
        String Button = this.getArguments().getString("Button");
        String Interaction = this.getArguments().getString("Interaction");

        tvTitle.setText(Title);
        tvLine1.setText(Line1);
        tvLine2.setText(Line2);
        tvLine3.setText(Line3);

        try
        {
            switch (Interaction)
            {
                case CustomDialogScenarios.SUCCESS:
                    ivResult.setImageResource(R.drawable.ic_check_success_white);
                    break;
                case CustomDialogScenarios.ERROR:
                    ivResult.setImageResource(R.drawable.ic_cross_error_white);
                    break;
                case CustomDialogScenarios.WARNING:
                    Log.e(TAG, "Must provide warning icon");
                    break;
                case CustomDialogScenarios.TOPUP_SUCCESS:
                    ivResult.setImageResource(R.drawable.ic_phone_success);
                    break;
                case CustomDialogScenarios.TOPUP_ERROR:
                    ivResult.setImageResource(R.drawable.ic_phone_error);
                    break;
                case CustomDialogScenarios.TOPUP_WARNING:
                    Log.e(TAG, "Must provide phone-warning icon");
                    break;
                default:
                    Log.i(TAG, "There is no default action");
                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        btnDismiss.setText(Button);
        return view;
    }

}
