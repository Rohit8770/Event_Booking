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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.ActivitySubEvent;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.Tools;
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
    ImageView ivProfileBack, tvNoData;
    TextView tvNoDataFound;
    EditText etSubCateSearch;
    LinearLayout LyFilterBtn;
    Tools tools;
    SwipeRefreshLayout swipeRefreshUpcomingLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_up_coming, container, false);

        apiList = new ArrayList<>();
        tools=new Tools(getContext());
        ivProfileBack = v.findViewById(R.id.ivProfileBack);
        tvNoData = v.findViewById(R.id.tvNoData);
        tvNoDataFound = v.findViewById(R.id.tvNoDataFound);
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
                tools.vibrate();
                tools.playBeepSound();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentFilter fragmentFilter = new FragmentFilter();
                fragmentFilter.show(fragmentTransaction, "#tag");
                fragmentFilter.setCancelable(false);
                fragmentFilter.setupInterface(new FragmentFilter.DataClick() {
                    @Override
                    public void dataClick(int price, String date) {
                        List<Subcategory> newList = new ArrayList<>();
                        if (apiList != null) {
                            if (price != 0 && date.equals("Select the Date")) {
                                for (int i = 0; i < apiList.size(); i++) {
                                    if (floatToInt(Float.valueOf(apiList.get(i).getPrice())) < price) {
                                        newList.add(apiList.get(i));
                                    }
                                }
                            } else if (price == 0 && !date.equals("Select the Date")) {
                                for (int i = 0; i < apiList.size(); i++) {
                                    if (apiList.get(i).getDate().equals(date)) {
                                        newList.add(apiList.get(i));
                                    }
                                }
                            } else if (price != 0 && !date.equals("Select the Date")) {
                                List<Subcategory> filterList = new ArrayList<>();
                                for (int i = 0; i < apiList.size(); i++) {
                                    if (apiList.get(i).getDate().equals(date)) {
                                        filterList.add(apiList.get(i));
                                    }
                                }
                                for (int i = 0; i < filterList.size(); i++) {
                                    if (floatToInt(Float.valueOf(filterList.get(i).getPrice())) < price) {
                                        newList.add(filterList.get(i));
                                    }
                                }
                            }
                        }
                        subCateAdapter.updateData(newList);

                        // Show or hide the "No Data Found" message based on filtered results
                        boolean isFilteredResultsEmpty = subCateAdapter.isEmpty();
                        if (isFilteredResultsEmpty) {
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoDataFound.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.GONE);
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

                    // Show or hide the "No Data Found" message based on search results

                    boolean isSearchResultsEmpty = subCateAdapter.isEmpty();
                    if (isSearchResultsEmpty) {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.VISIBLE);
                    } else {
                        tvNoDataFound.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.GONE);
                    }
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
        tools.showLoading();
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
                                tools.stopLoading();
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && subCategoryListResponse.getSubcategoryList() != null
                                        && subCategoryListResponse.getSubcategoryList().size() > 0) {

                                    apiList = subCategoryListResponse.getSubcategoryList();

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
                                         if (subCateAdapter.isEmpty()) {
                                            tvNoDataFound.setVisibility(View.VISIBLE);
                                            tvNoData.setVisibility(View.VISIBLE);
                                        } else {
                                            tvNoDataFound.setVisibility(View.GONE);
                                            tvNoData.setVisibility(View.GONE);
                                        }
                                    } else {
                                        // Handle the case where there is no data
                                        tvNoDataFound.setVisibility(View.VISIBLE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
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

}