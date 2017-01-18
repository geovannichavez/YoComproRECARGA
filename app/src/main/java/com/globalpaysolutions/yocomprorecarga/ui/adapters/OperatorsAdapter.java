package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.squareup.picasso.Picasso;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class OperatorsAdapter extends ArrayAdapter<CountryOperator>
{
    private Context AdapterContext;
    private int AdapResource;
    int SelectedItem;

    public OperatorsAdapter(Context pContext, int pResource)
    {
        super(pContext, pResource);

        AdapterContext = pContext;
        AdapResource = pResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        final CountryOperator currentItem = getItem(position);

        if (view == null)
        {
            LayoutInflater inflater = ((Activity) AdapterContext).getLayoutInflater();
            view = inflater.inflate(AdapResource, parent, false);
        }

        view.setTag(currentItem);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivOperador);

        if (imageView == null)
        {
            imageView = new ImageView(AdapterContext);
        }
        String url = currentItem.getLogoUrl();

        Picasso.with(AdapterContext).load(url).into(imageView);


        return view;
    }
}
