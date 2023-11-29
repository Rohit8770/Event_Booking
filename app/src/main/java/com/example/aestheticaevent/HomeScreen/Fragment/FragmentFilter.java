package com.example.aestheticaevent.HomeScreen.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aestheticaevent.Models.Model_EventList;
import com.example.aestheticaevent.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentFilter extends DialogFragment {
    ImageView img_close;
    TextView tvFilterCateName;
    EditText etFilterName, etFilterDate, etFilterLocation;
    CardView txtFilter;
    List<Model_EventList> dataModelNewList;
    List<Model_EventList> newdataModelNewList;
    DataClick dataClick;
    public interface DataClick{
        void dataClick(String name,String date ,String location);
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

        tvFilterCateName = view.findViewById(R.id.tvFilterCateName);
        img_close = view.findViewById(R.id.img_close);
        etFilterName = view.findViewById(R.id.etFilterName);
        etFilterDate = view.findViewById(R.id.etFilterDate);
        etFilterLocation = view.findViewById(R.id.etFilterLocation);
        txtFilter = view.findViewById(R.id.txtFilter);
        dataModelNewList=new ArrayList<Model_EventList>();
        newdataModelNewList=new ArrayList<>();

        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   dataClick.dataClick(etFilterName.getText().toString());
                String name = etFilterName.getText().toString();
                String date = etFilterDate.getText().toString();
                String location = etFilterLocation.getText().toString();

                dataClick.dataClick(name, date, location);
                dismiss();
            }
        });


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
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        etFilterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etFilterDate);
            }
        });

        return view;
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                }
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                }
            }
        });
        datePickerDialog.show();
    }
}



