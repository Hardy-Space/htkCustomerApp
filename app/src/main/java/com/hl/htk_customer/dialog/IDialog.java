package com.hl.htk_customer.dialog;

import android.widget.Adapter;


/**
 * 作者 龙威陶
 */
public interface IDialog {

    String getTitle();
    void setTitle(String title);
    void setAdapter(Adapter adapter);
    void setOnDialogClickListener(OnDialogClickListener listener);

    void show();

  //  void onActivityResult(int requestCode, int resultCode, Intent data);
}
