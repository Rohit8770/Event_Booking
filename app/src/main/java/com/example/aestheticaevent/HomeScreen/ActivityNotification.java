package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.Adapters.NotificationAdapter;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.PassListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityNotification extends AppCompatActivity {

    ImageView ivNotificationBack,tvNoData;
    TextView tvNoDataFound;
    RecyclerView rcvNotification;
    Restcall restcall;
    String user_id;
    Tools tools;
    SharedPreference sharedPreference;
    NotificationAdapter notificationAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Activity_HomeScreen.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        tools=new Tools(this);
        ivNotificationBack=findViewById(R.id.ivNotificationBack);
        rcvNotification=findViewById(R.id.rcvNotification);
        sharedPreference=new SharedPreference(ActivityNotification.this);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools.ScreenshotBlock(getWindow());
        tvNoData = findViewById(R.id.tvNoData);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);

        ivNotificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNotification.this, Activity_HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        Intent i = getIntent();
        if (i != null) {
            user_id = sharedPreference.getStringvalue(VariableBag.USER_ID);
        }

    }
    protected void onResume() {
        super.onResume();
        GetEventTicketCall();
    }
    public  void GetEventTicketCall(){
        tools.showLoading();
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
                                tools.stopLoading();
                                Toast.makeText(ActivityNotification.this, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(PassListResponse passListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (passListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && passListResponse.getTicketdetailsList() != null
                                        && passListResponse.getTicketdetailsList().size() > 0) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityNotification.this,RecyclerView.VERTICAL,false);
                                    rcvNotification.setLayoutManager(layoutManager);
                                    notificationAdapter = new NotificationAdapter(ActivityNotification.this, passListResponse.getTicketdetailsList());
                                    rcvNotification.setAdapter(notificationAdapter);
                                    updateNoDataVisibility();
                                } else {
                                    showNoDataViews();
                                }
                            }
                        });
                    }
                });
    }

    private void showNoDataViews() {
        tvNoDataFound.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.VISIBLE);
        rcvNotification.setVisibility(View.GONE);
    }


    private void updateNoDataVisibility() {
        if (notificationAdapter != null && notificationAdapter.getItemCount() > 0) {
            tvNoDataFound.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
            rcvNotification.setVisibility(View.VISIBLE);
        } else {
            showNoDataViews();
        }
    }
}