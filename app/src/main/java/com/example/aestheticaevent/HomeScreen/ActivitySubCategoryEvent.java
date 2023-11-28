package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.Fragment.FragmentFilter;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivitySubCategoryEvent extends AppCompatActivity {
    private String filterName = "";
    private String filterDate = "";
    private String filterLocation = "";

    RecyclerView rvEventList;
    Restcall restcall;
    String categoryId;
    SubCateAdapter subCateAdapter;
    ImageView ivProfileBack;
    EditText etSubCateSearch;
    Button btnBack2;
    LinearLayout LyFilterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_event);
        ivProfileBack = findViewById(R.id.ivProfileBack);
        etSubCateSearch = findViewById(R.id.etSubCateSearch);
        btnBack2 = findViewById(R.id.btnBack2);
        LyFilterBtn = findViewById(R.id.LyFilterBtn);

     /*   LyFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentFilter fragmentFilter = new FragmentFilter();
                fragmentFilter.show(fragmentTransaction, "#tag");
                fragmentFilter.setCancelable(false);
            }
        });*/

      /*  btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySubCategoryEvent.this, ActivityEventinfo.class);
                startActivity(intent);
            }
        });*/

       /* etSubCateSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (subCateAdapter!=null){
                    subCateAdapter.Search(charSequence, rvEventList);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        ivProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        if (i != null) {
            categoryId = i.getStringExtra("categoryId");
        }



        rvEventList = findViewById(R.id.rvEventList);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
    }

    protected void onResume() {
        super.onResume();
        GetSubCategoryCall();
    }

    public void GetSubCategoryCall() {
        restcall.Getsubcategory("getsubcategory", categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivitySubCategoryEvent.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && subCategoryListResponse.getSubcategoryList() != null
                                        && subCategoryListResponse.getSubcategoryList().size() > 0) {

                                //    applyFilters(subCategoryListResponse.getSubcategoryList());


                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivitySubCategoryEvent.this, RecyclerView.VERTICAL, false);
                                    rvEventList.setLayoutManager(layoutManager);
                                    subCateAdapter = new SubCateAdapter(ActivitySubCategoryEvent.this, subCategoryListResponse.getSubcategoryList());
                                    subCateAdapter.setSubCategoryInterface(new SubCateAdapter.SubCategoryInterface() {
                                        @Override
                                        public void onSubCategoryClicked(String subCategoryId) {
                                            Intent i = new Intent(ActivitySubCategoryEvent.this, ActivityEventinfo.class);
                                            i.putExtra("subCatId", subCategoryId);
                                            i.putExtra("categoryId", categoryId);
                                            startActivity(i);
                                        }
                                    });
                                    rvEventList.setAdapter(subCateAdapter);
                                }
                            }
                        });
                    }
                });
    }*/
 /*   private void applyFilters(List<Subcategory> subcategoryList) {
        List<Subcategory> filteredList = new ArrayList<>();

        // Apply name filter
        for (Subcategory subcategory : subcategoryList) {
            if (subcategory.getSubCategoryName().toLowerCase().contains(filterName.toLowerCase())
                    && subcategory.getDate().toLowerCase().contains(filterDate.toLowerCase())
                    && subcategory.getLocation().toLowerCase().contains(filterLocation.toLowerCase())) {
                filteredList.add(subcategory);
            }
        }

        // Update the adapter with the filtered list
        if (subCateAdapter != null) {
            subCateAdapter.updateData(filteredList);
        }
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter_item, null);
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        EditText editTextLocation = dialogView.findViewById(R.id.editTextLocation);
        Button btnFilter = dialogView.findViewById(R.id.btnFilter);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextDate);
            }
        });

        builder.setView(dialogView)
                .setTitle("Filter")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve values from the EditText fields and apply your filter logic
                        filterName = editTextName.getText().toString();
                        filterDate = editTextDate.getText().toString();
                        filterLocation = editTextLocation.getText().toString();

                        // Refresh the RecyclerView with new filters
                        GetSubCategoryCall();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Set the color for the positive button (Apply)
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                if (positiveButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.black)); // Replace 'your_color' with your desired color
                }
            }
        });

        alertDialog.show();
    }




    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the chosen date in the EditText
                        editText.setText(String.format("%02d/%02d/%04d", monthOfYear + 1, dayOfMonth, year));
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();

        // Customize the color of the positive button (OK)
        Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(getResources().getColor(R.color.black)); // Replace 'your_color' with your desired color
        }
    }*/

    }
}