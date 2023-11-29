package com.example.aestheticaevent.HomeScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.BuyTicketSplash;
import com.example.aestheticaevent.HomeScreen.HomeResponse.DataModelNew;
import com.example.aestheticaevent.HomeScreen.HomeResponse.ButTicketListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.LocationLisResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityEventinfo extends AppCompatActivity {

    Context context;
    ImageView textImg, ivFollowed;
    TextView txName;
    TextView txCalander;
    TextView txStartTime;
    TextView txEndTime;
    TextView txtLocation;
    TextView txAbout;
    TextView txtPrice;
    TextView txtVenue,txtVenue1;
    TextView tvFollow;
    TextView txtorg;
    Restcall restcall;
    ImageView ivProfileBack;
    String subCatId, categoryId, userId, eventId;
    LinearLayout btnBuy;
    SharedPreference sharedPreference;
    Tools tools;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfo);

        tools=new Tools();
        ivProfileBack = findViewById(R.id.ivProfileBack);
        btnBuy = findViewById(R.id.btnBuy);
        txtVenue1 = findViewById(R.id.txtVenue1);

        ivFollowed = findViewById(R.id.ivFollowed);
        tvFollow = findViewById(R.id.tvFollow);

        sharedPreference = new SharedPreference(ActivityEventinfo.this);

        ivFollowed.setVisibility(View.GONE);


        txtVenue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetsubcategoryCall();

            /*    double latitude = LocationLisResponse.Subcategory.
                double longitude = LocationLisResponse.Subcategory.get(0).getLongitude();

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
            }
        });
        ivFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFollowed.setVisibility(View.GONE);
                tvFollow.setVisibility(View.VISIBLE);

            }
        });
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFollow.setVisibility(View.GONE);
                ivFollowed.setVisibility(View.VISIBLE);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEventinfo.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to buy this event?");
                builder.setPositiveButton("Buy", (dialog, which) -> {
                    handleBuyEvent();
                    AddTicketDetailsCall();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel(ActivityEventinfo.this);
                    }
                    showNotification();
                   /* Intent resultIntent = new Intent(ActivityEventinfo.this, ActivityNotification.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(ActivityEventinfo.this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    Notification notification = new NotificationCompat.Builder(ActivityEventinfo.this, "alarm_channel")
                            .setContentTitle("Congratulations")
                            .setContentText("Your Event is Booked SuccessFully")
                            .setSmallIcon(R.drawable.notification_alarm)
                            .setContentIntent(resultPendingIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) ActivityEventinfo.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);*/
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                });
                alertDialog.show();
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
            subCatId = i.getStringExtra("subCatId");
            categoryId = i.getStringExtra("categoryId");
            userId = sharedPreference.getStringvalue(VariableBag.USER_ID);


            textImg = findViewById(R.id.textImg);
            txName = findViewById(R.id.txName);
            txCalander = findViewById(R.id.txCalander);
            txStartTime = findViewById(R.id.txStartTime);
            txEndTime = findViewById(R.id.txEndTime);
            txtLocation = findViewById(R.id.txtLocation);
            txAbout = findViewById(R.id.txAbout);
            txtPrice = findViewById(R.id.txtPrice);
            txtVenue = findViewById(R.id.txtVenue);
            restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
            Geteventinfo(subCatId);
        }
    }

    private void Geteventinfo(String subCatId) {
        tools.stopLoading();
        restcall.Geteventinfo("geteventinfo", subCatId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<DataModelNew>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(ActivityEventinfo.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(DataModelNew dataModelNew) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (dataModelNew.getStatus().equals(VariableBag.SUCCESS_CODE)) {

                                    txName.setText(dataModelNew.getEventList().get(0).getSubCategoryName());


                                    Glide
                                            .with(ActivityEventinfo.this)
                                            .load(dataModelNew.getEventList().get(0).getPicture())
                                            .into(textImg);

                                    txStartTime.setText(dataModelNew.getEventList().get(0).getTiming());
                                    txEndTime.setText(dataModelNew.getEventList().get(0).getEndTime());
                                    txCalander.setText(dataModelNew.getEventList().get(0).getDate());
                                    txtLocation.setText(dataModelNew.getEventList().get(0).getLocation());
                                    txAbout.setText(dataModelNew.getEventList().get(0).getDesciprtion());
                                    txtPrice.setText(dataModelNew.getEventList().get(0).getPrice());
                                    txtVenue.setText(dataModelNew.getEventList().get(0).getVenue());
                                    txtVenue1.setText(dataModelNew.getEventList().get(0).getVenue());
                                    eventId = dataModelNew.getEventList().get(0).getEventId();
                                }
                                Toast.makeText(ActivityEventinfo.this, "" + dataModelNew.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void AddTicketDetailsCall() {
        restcall.AddTicketDetails("Addticketdetails", eventId, userId, subCatId, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ButTicketListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(ActivityEventinfo.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ButTicketListResponse butTicketListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (butTicketListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                }
                                Toast.makeText(ActivityEventinfo.this, "" + butTicketListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void GetsubcategoryCall() {
        restcall.GetsubcategoryCall("getsubcategory",categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LocationLisResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(ActivityEventinfo.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(LocationLisResponse locationLisResponses) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (locationLisResponses.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    List<LocationLisResponse.Subcategory> subcategoryList = locationLisResponses.getSubcategoryList();

                                    if (subcategoryList != null && !subcategoryList.isEmpty()) {
                                        double latitude = Double.parseDouble(subcategoryList.get(0).getLatitude());
                                        double longitude = Double.parseDouble(subcategoryList.get(0).getLongitude());

                                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        startActivity(intent);
                                    } else {
                                        // Handle the case where the location data is not available
                                        Toast.makeText(ActivityEventinfo.this, "Location data not available", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                Toast.makeText(ActivityEventinfo.this, "" + locationLisResponses.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for alarm notifications");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    private void handleBuyEvent() {
        Toast.makeText(ActivityEventinfo.this, "Your Ticket is Booked!", Toast.LENGTH_SHORT).show();
        Intent ticketIntent = new Intent(ActivityEventinfo.this, BuyTicketSplash.class);
        startActivity(ticketIntent);
    }

    private void showNotification() {
        Intent resultIntent = new Intent(ActivityEventinfo.this, ActivityNotification.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ActivityEventinfo.this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(ActivityEventinfo.this, "alarm_channel")
                .setContentTitle("Congratulations")
                .setContentText("Your Event is Booked Successfully")
                .setSmallIcon(R.drawable.notification_alarm)
                .setContentIntent(resultPendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

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