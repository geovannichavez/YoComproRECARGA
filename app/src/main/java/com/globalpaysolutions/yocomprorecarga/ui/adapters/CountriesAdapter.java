package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Country;

import java.util.List;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class CountriesAdapter extends ArrayAdapter<Country>
{
    Context adapContext;
    int adapResource;
    int adaptTextViewResourceId;

    public CountriesAdapter(Context pContext, int pResource, int pTextViewResource, List<Country> pCountriesList)
    {
        super(pContext, pResource, pTextViewResource, pCountriesList);
        adapContext = pContext;
        adapResource = pResource;
        adaptTextViewResourceId = pTextViewResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        final Country currentItem = getItem(position);

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) adapContext).getLayoutInflater();
            row = inflater.inflate(adapResource, parent, false);
        }

        String Name = currentItem.getName();
        String Code = currentItem.getCode();
        String CountryCode =  currentItem.getCountrycode();
        String PhoneCode =  currentItem.getPhoneCode();

        row.setTag(currentItem);
        row.setTag(position);
        final CheckedTextView label = (CheckedTextView) row.findViewById(R.id.tvCountryName);
        label.setText(Name);

        return row;
    }
}
