package com.example.aestheticaevent.HomeScreen.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aestheticaevent.R;
import com.google.android.material.slider.RangeSlider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FragmentFilter extends DialogFragment {
    ImageView img_close;
    TextView tvFilterCateName;
    TextView txprice;
    int price=0;
    EditText etFilterName,etFilterPrice, etFilterDate, etFilterLocation;
    CardView txtFilter;
    RangeSlider priceRangeFilter;
    DataClick dataClick;
    public interface DataClick{
        void dataClick(int price,String date);
    }
    public void setupInterface(DataClick dataClick){
        this.dataClick = dataClick;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

       // tvFilterCateName = view.findViewById(R.id.tvFilterCateName);
        img_close = view.findViewById(R.id.img_close);
      //  etFilterName = view.findViewById(R.id.etFilterName);
        etFilterDate = view.findViewById(R.id.etFilterDate);
        etFilterDate.setText("Select the Date");
       // etFilterLocation = view.findViewById(R.id.etFilterLocation);
        txtFilter = view.findViewById(R.id.txtFilter);
        txprice = view.findViewById(R.id.txprice);
        priceRangeFilter = view.findViewById(R.id.priceRangeFilter);

        etFilterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataClick.dataClick(price,etFilterDate.getText().toString().trim());
             /*   String name = etFilterName.getText().toString();
                String date = etFilterDate.getText().toString();
                String location = etFilterLocation.getText().toString();
                String price = etFilterPrice.getText().toString();

                dataClick.dataClick(name, date, location,price);*/
                dismiss();
            }
        });
        priceRangeFilter.addOnChangeListener(new RangeSlider.OnChangeListener() {
         @Override
         public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
         txprice.setText("0 - " + floatToInt(value));
         price = floatToInt(value);

         }
        });
        return view;
    }
    public static int floatToInt(Float value){
        String str = String.valueOf(value);
        String newStr = "";
        for (int i=0;i<str.length();i++){
            if (str.charAt(i) == '.'){
                break;
            }else {
                newStr = newStr+str.charAt(i);
            }
        }
        return Integer.parseInt(newStr);
    }

              /*

List<ModelDataClass> list;  // api se milta hai
user selete -- name;

List<ModelDataClass> newList;

    for(int i=0;i<list.size();i++){
        if(list.get(i).getDate().euqle(etvdate.gettext().toString)){
            newList.add(list.get(i))
        }
    }

newList set adapter-------
 */

    private void showDatePickerDialog() {
        etFilterDate.setFocusable(false);
        etFilterDate.setClickable(true);

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDay);
                if (selectedDate.getTimeInMillis() >= currentDate.getTimeInMillis()) {
                    // Format the date as "EEE MMM-dd-yyyy"
                    String formattedDate = new SimpleDateFormat("EEE MMM-dd-yyyy", Locale.getDefault()).format(selectedDate.getTime());
                    etFilterDate.setText(formattedDate);
                }
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }


}



