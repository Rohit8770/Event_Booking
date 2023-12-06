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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.Adapters.CompleteAdapter;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CompleteResponse;
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

public class CompleteFragment extends Fragment {

    private String filterName = "";
    private String filterDate = "";
    private String filterLocation = "";
    RecyclerView rvEventList;
    Restcall restcall;
    String categoryId;
    CompleteAdapter completeAdapter;
    Tools tools;
    ImageView ivProfileBack;
    EditText etSubCategorySearch;
    SwipeRefreshLayout swipeRefreshUpcomingLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_complete, container, false);

        tools=new Tools(getContext());
        etSubCategorySearch = v.findViewById(R.id.etSubCategorySearch);
            swipeRefreshUpcomingLayout = v.findViewById(R.id.swipeRefreshUpcomingLayout);

            swipeRefreshUpcomingLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(() -> {
                        swipeRefreshUpcomingLayout.setRefreshing(false);
                    }, 1500);
                }
            });



        etSubCategorySearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (completeAdapter != null) {
                        completeAdapter.Search(charSequence, rvEventList);
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

            return v;
        }

        public void onResume() {
            super.onResume();
            Getclosedevents();
        }

        public void Getclosedevents() {
        tools.showLoading();
            restcall.Getclosedevents("getclosedevents", categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<CompleteResponse>() {
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
                        public void onNext(CompleteResponse completeResponse) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tools.stopLoading();
                                    if (completeResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                            && completeResponse.getCloseeventList() != null
                                            && completeResponse.getCloseeventList().size() > 0) {

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                        rvEventList.setLayoutManager(layoutManager);
                                        completeAdapter = new CompleteAdapter(getContext(), completeResponse.getCloseeventList());
                                        rvEventList.setAdapter(completeAdapter);
                                    }
                                }
                            });
                        }
                    });
        }
}