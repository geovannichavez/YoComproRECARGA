package com.globalpaysolutions.yocomprorecarga.utils;

import android.view.View;

/**
 * Created by Josué Chávez on 17/01/2018.
 */

public interface RecyclerClickListener
{
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
