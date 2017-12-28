package com.hl.htk_customer.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/30.
 */

public class TimeUtils {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;
    private Context context;
    private int selectYear;
    private int selectMonth;
    private int selectDay;
    private int selectHour;
    private int selectMinute;

    private ResultListener resultListener;

    public TimeUtils(Context context) {
        this.context = context;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public static TimeUtils getInstance(Context context){
        return new TimeUtils(context);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            selectYear = year;
            selectMonth = month;
            selectDay = dayOfMonth;
            showTimePicker();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            selectHour = hourOfDay;
            selectMinute = minute;
            resultListener.getResult(getResult());
        }
    };

    public void showDataPicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context , 0 , dateSetListener , year , month , day);
        datePickerDialog.show();
    }

    private void showTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    private String getResult(){
        String month;
        String day;
        String hour;
        String minute;

        if (selectMonth >= 1 && selectMonth <= 9){
            month = "0" + selectMonth;
        }else {
            month = "" + selectMonth;
        }

        if (selectDay >= 1 && selectDay <= 9){
            day = "0" + selectDay;
        }else {
            day = "" + selectDay;
        }

        if (selectHour >= 0 && selectHour <= 9){
            hour = "0" + selectHour;
        }else {
            hour = "" + selectHour;
        }

        if (selectMinute >= 0 && selectMinute <= 9){
            minute = "0" + selectMinute;
        }else {
            minute = "" + selectMinute;
        }
        return selectYear + "-" + (Integer.parseInt(month)+1) + "-" + day + " " + hour + ":" + minute;
    }

    public interface ResultListener{
        void getResult(String result);
    }

    public void setResultListener(ResultListener listener){
        this.resultListener = listener;
    }
}
