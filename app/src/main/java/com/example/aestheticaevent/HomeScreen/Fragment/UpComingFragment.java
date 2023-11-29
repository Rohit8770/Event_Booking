package com.example.aestheticaevent.HomeScreen.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.ActivitySubEvent;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class UpComingFragment extends Fragment {

    private String filterName = "";
    private String filterDate = "";
    private String filterLocation = "";

    RecyclerView rvEventList;
    Restcall restcall;
    String categoryId;
    List<Subcategory> apiList;

    SubCateAdapter subCateAdapter;
    ImageView ivProfileBack;
    EditText etSubCateSearch;
    LinearLayout LyFilterBtn;
    SwipeRefreshLayout swipeRefreshUpcomingLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_up_coming, container, false);


        apiList = new ArrayList<>();
        ivProfileBack = v.findViewById(R.id.ivProfileBack);
        etSubCateSearch = v.findViewById(R.id.etSubCateSearch);
        LyFilterBtn = v.findViewById(R.id.LyFilterBtn);
        swipeRefreshUpcomingLayout = v.findViewById(R.id.swipeRefreshUpcomingLayout);

        swipeRefreshUpcomingLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    swipeRefreshUpcomingLayout.setRefreshing(false);
                }, 1500);
            }
        });

        LyFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentFilter fragmentFilter = new FragmentFilter();
                fragmentFilter.show(fragmentTransaction, "#tag");
                fragmentFilter.setCancelable(false);
                fragmentFilter.setupInterface(new FragmentFilter.DataClick() {
                    @Override
                    public void dataClick(String name, String date, String location) {


                        if (name!=null){
                            List<Subcategory> newList = new ArrayList<>();
                            for (int i=0;i<apiList.size();i++){
                                if (apiList.get(i).getSubCategoryName().equals(name)){
                                    newList.add(apiList.get(i));
                                }
                            }
                            for (int i=0;i<apiList.size();i++){
                                if (apiList.get(i).getDate().equals(date)){
                                    newList.add(apiList.get(i));
                                }
                            }
                            for (int i=0;i<apiList.size();i++){
                                if (apiList.get(i).getLocation().equals(location)){
                                    newList.add(apiList.get(i));
                                }
                            }
                            subCateAdapter.updateData(newList);
                        }

                    }
                });
            }
        });

        etSubCateSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (subCateAdapter != null) {
                    subCateAdapter.Search(charSequence, rvEventList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        Intent intent = getActivity().getIntent();
        if (intent != null) {
            categoryId = intent.getStringExtra("categoryId");
        }


        rvEventList = v.findViewById(R.id.rvEventList);
            restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);



        Getupcomingevents();
        return v;
    }



    public void Getupcomingevents() {
        restcall.Getupcomingevents("getupcomingevents", categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && subCategoryListResponse.getSubcategoryList() != null
                                        && subCategoryListResponse.getSubcategoryList().size() > 0) {

                                    apiList = subCategoryListResponse.getSubcategoryList();
                                  /*  List<Subcategory> activeSubCategories = new ArrayList<>();
                                    List<Subcategory> inActiveSubCategories = new ArrayList<>();

                                    for (Subcategory subCategory : subCategoryListResponse.getSubcategoryList()) {
                                        if (subCategory.getSub_category_status().equals("0")){
                                            activeSubCategories.add(subCategory);
                                        }else{
                                            inActiveSubCategories.add(subCategory);
                                        }
                                    }*/

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rvEventList.setLayoutManager(layoutManager);
                                    subCateAdapter = new SubCateAdapter(getContext(), subCategoryListResponse.getSubcategoryList());
                                    rvEventList.setAdapter(subCateAdapter);
                                    subCateAdapter.setSubCategoryInterface(new SubCateAdapter.SubCategoryInterface() {
                                        @Override
                                        public void onSubCategoryClicked(String subCategoryId) {
                                            Intent i = new Intent(getContext(), ActivityEventinfo.class);
                                            i.putExtra("subCatId", subCategoryId);
                                            i.putExtra("categoryId", categoryId);
                                            requireActivity().startActivity(i);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
    }

    /*private void applyFilters(List<Subcategory> subcategoryList) {
        List<Subcategory> upcomingEvents = new ArrayList<>();
        List<Subcategory> completedEvents = new ArrayList<>();

        // Separate upcoming and completed events based on status
        for (Subcategory subcategory : subcategoryList) {
            if (subcategory.getStatus() == 0) {
                upcomingEvents.add(subcategory);
            } else {
                completedEvents.add(subcategory);
            }
        }*/

}
