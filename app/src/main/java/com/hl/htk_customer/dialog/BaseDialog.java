package com.hl.htk_customer.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;

import com.hl.htk_customer.R;


/**


 */
public abstract class BaseDialog implements IDialog, DialogInterface.OnKeyListener,View.OnClickListener  {

    protected Adapter adapter;
    protected OnDialogClickListener listener;
    protected AlertDialog dialog;
    protected Context context;

    protected String title;
    protected Button btnOk;
    protected Button btnCancel;

    protected int layoutRid;

    protected String okLable;
    protected String cancelLable;

    protected boolean isTitle;
    protected boolean isOk;
    protected boolean isCancle;
    protected boolean isTouchOutside;

    protected  int id;

    protected Object tag;


    public BaseDialog(Context context)
    {
        this.context=context;

        okLable="确定";
        cancelLable="取消";
        isTitle=true;
        isOk=true;
        isCancle=true;
        isTouchOutside=true;
        tag=null;
        id=0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void setAdapter(Adapter adapter) {
       this.adapter=adapter;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public void setOnDialogClickListener(OnDialogClickListener listener) {
      this.listener=listener;
    }

    @Override
    public void show() {
        dialog = new AlertDialog.Builder(this.context , R.style.loading_dialog).create();
        dialog.setCanceledOnTouchOutside(isTouchOutside);
        dialog.show();
        dialog.setOnKeyListener(this);
        Window window = dialog.getWindow();
        window.setContentView(layoutRid);
       // FontUtils.logingViewFont(window.getDecorView());
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            dialogCancel();
            dialog.dismiss();
            if(listener!=null)
            {
                listener.onCancleClick(this);
            }
        }
        return false;
    }

    protected  void dialogOk()
    {

    }

    protected void dialogCancel()
    {

    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setIsTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isCancle() {
        return isCancle;
    }

    public void setIsCancle(boolean isCancle) {
        this.isCancle = isCancle;
    }

    public boolean isTouchOutside() {
        return isTouchOutside;
    }

    public void setIsTouchOutside(boolean isTouchOutside) {
        this.isTouchOutside = isTouchOutside;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOkLable() {
        return okLable;
    }

    public void setOkLable(String okLable) {
        this.okLable = okLable;
    }

    public String getCancelLable() {
        return cancelLable;
    }

    public void setCancelLable(String cancelLable) {
        this.cancelLable = cancelLable;
    }

    @Override
    public String toString() {
        return title;
    }
}
