package com.hl.htk_customer.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.hl.htk_customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：百迅
 * 创建者： 龙威陶
 **/
public class OneLineDialog extends BaseDialog {

    private OneLineDialog.getDataListener getDataListener;
    private String oneData;
    private PickerView pvDay;
    private Button btnFunction;
    private int dayIndex;
    private Button btnCancel;
    private int viewId;
    private List<String> data;


    public interface getDataListener {
        void getData(String time, int viewId);
    }

    public void setOngetDataListener(getDataListener getDataListener, int viewID) {
        this.getDataListener = getDataListener;
        this.viewId = viewID;
    }

    public OneLineDialog(Context context) {
        super(context);

        layoutRid = R.layout.dialog_one_line;
        data = new ArrayList<>();
    }

    @Override
    public void show() {
        super.show();

        if (dialog == null)
            return;
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = context.getResources().getDisplayMetrics().widthPixels;
        params.y = wm.getDefaultDisplay().getHeight() / 2;
        window.setAttributes(params);

        pvDay = (PickerView) window.findViewById(R.id.pvDay);
        pvDay.setData(data);


        btnFunction = (Button) window.findViewById(R.id.tvFunction2);
        btnCancel = (Button) window.findViewById(R.id.tvFunction1);
        btnFunction.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        PickerView.onSelectListener listener = new PickerView.onSelectListener() {

            @Override
            public void onSelect(View view, String text) {
                if (view == pvDay) {
                    oneData = text;
                }
            }
        };

        pvDay.setOnSelectListener(listener);


        List<String> dataList = pvDay.getmDataList();
        for (int i = 0; i < dataList.size(); i++) {
            if (oneData.equals(dataList.get(i))) {
                dayIndex = i;
            }
        }

        pvDay.setSelected(dayIndex);
        // lvSex.getChildAt(2).setBackgroundColor();
    }


    private int cltime(String time) {
        boolean isn = false;
        String val = "";
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != '0') {
                isn = true;
            }
            if (isn) {
                val += time.charAt(i);
            }
        }
        if (!"".equals(val))
            return Integer.parseInt(val);
        else
            return 0;
    }


    public String getOneData() {
        return oneData;
    }

    public void setShowOne(String time) {
        this.oneData = time;
    }

    public void setData(List<String> data) {

        this.data = data;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvFunction2) {
            if (this.listener != null) {
                this.listener.onClick(this);
            }
            getDataListener.getData(getOneData(), this.viewId);
        }

        dialog.dismiss();
        super.onClick(v);
    }

}
