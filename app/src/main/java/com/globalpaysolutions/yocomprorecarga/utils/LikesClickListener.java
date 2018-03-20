package com.globalpaysolutions.yocomprorecarga.utils;

import com.facebook.share.widget.ShareButton;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface LikesClickListener
{
    void onClickListener(int position, Constants.FacebookActions action, ShareButton shareButton);
}
