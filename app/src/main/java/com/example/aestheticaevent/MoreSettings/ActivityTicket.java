package com.example.aestheticaevent.MoreSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.MoreSettings.TicketAdapterss.TicketAdapter;
import com.example.aestheticaevent.MoreSettings.TicketRespomse.PassListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityTicket extends AppCompatActivity {

    RecyclerView rcvTicket;
    Restcall restcall;
    ImageView ivTicketBack;
    TicketAdapter ticketAdapter;
    EditText etTicketSearch;
    SharedPreference sharedPreference;
    SwipeRefreshLayout swipeRefreshTicketLayout;
    String subCatId,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        sharedPreference=new SharedPreference(ActivityTicket.this);
        etTicketSearch=findViewById(R.id.etTicketSearch);
        swipeRefreshTicketLayout=findViewById(R.id.swipeRefreshTicketLayout);
        ivTicketBack=findViewById(R.id.ivTicketBack);
        ivTicketBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(ActivityTicket.this, Activity_HomeScreen.class);
                startActivity(i);*/
                finish();
            }
        });
        swipeRefreshTicketLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetEventTicketCall();
                new Handler().postDelayed(() -> {
                    swipeRefreshTicketLayout.setRefreshing(false);
                }, 1500);
            }
        });
        etTicketSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ticketAdapter!=null){
                    ticketAdapter.Search(charSequence, rcvTicket);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        Intent i = getIntent();
        if (i != null) {
            user_id = sharedPreference.getStringvalue(VariableBag.USER_ID);
        }

        rcvTicket=findViewById(R.id.rcvTicket);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);



    }
    protected void onResume() {
        super.onResume();
        GetEventTicketCall();
    }

    public  void GetEventTicketCall(){

        restcall.GetTicketDetails("getticketdetails",user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<PassListResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivityTicket.this, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(PassListResponse passListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (passListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && passListResponse.getTicketdetailsList() != null
                                        && passListResponse.getTicketdetailsList().size() > 0) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityTicket.this,RecyclerView.VERTICAL,false);
                                    rcvTicket.setLayoutManager(layoutManager);
                                    ticketAdapter = new TicketAdapter(ActivityTicket.this, passListResponse.getTicketdetailsList());
                                    rcvTicket.setAdapter(ticketAdapter);
                                }
                            }
                        });
                    }
                });
    }
}