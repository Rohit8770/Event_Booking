package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.Adapters.Adapter_EventList;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivitySubCategoryEvent extends AppCompatActivity {

    RecyclerView rvEventList;
    Restcall restcall;
    String categoryId;
    SubCateAdapter subCateAdapter;
    ImageView ivProfileBack;
    EditText etSubCateSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_event);
        ivProfileBack=findViewById(R.id.ivProfileBack);
        etSubCateSearch=findViewById(R.id.etSubCateSearch);

        etSubCateSearch.addTextChangedListener(new TextWatcher() {
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


    }
}