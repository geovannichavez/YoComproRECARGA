package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.interactors.CombosInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.CombosListener;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICombosPresenter;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosPresenterImpl implements ICombosPresenter, CombosListener
{
    private static final String TAG = CombosPresenterImpl.class.getSimpleName();

    private Context mContext;
    private CombosView mView;
    private CombosInteractor mInteractor;

    public CombosPresenterImpl(Context context, AppCompatActivity activity, CombosView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new CombosInteractor(mContext);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void retrieveCombos()
    {
        mInteractor.retrieveCombos(this);
    }

    @Override
    public void onRetrieveSuccess(CombosResponse combos)
    {
        try
        {
            mView.renderCombos(combos.getResponse());
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onRetrieveError(int code, Throwable throwable, String internalCode)
    {

    }
}
