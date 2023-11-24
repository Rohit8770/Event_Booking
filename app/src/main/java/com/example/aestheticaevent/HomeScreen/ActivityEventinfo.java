package com.example.aestheticaevent.HomeScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.HomeResponse.DataModelNew;
import com.example.aestheticaevent.MoreSettings.TicketRespomse.ButTicketListResponse;
import com.example.aestheticaevent.MoreSettings.ActivityTicket;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityEventinfo extends AppCompatActivity {

    ImageView textImg;
    TextView txName, txCalander, txStartTime, txEndTime, txtLocation, txAbout, txtPrice, txtVenue;
    Restcall restcall;
    ImageView ivProfileBack;
    String subCatId, categoryId, userId, eventId, ticketId;
    LinearLayout btnBuy;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfo);
        ivProfileBack = findViewById(R.id.ivProfileBack);
        btnBuy = findViewById(R.id.btnBuy);
        sharedPreference = new SharedPreference(ActivityEventinfo.this);

        /*Intent intent = getIntent();
        if (intent != null) {
        }
*/

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
                    Intent resultIntent = new Intent(ActivityEventinfo.this, Activity_HomeScreen.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(ActivityEventinfo.this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    Notification notification = new NotificationCompat.Builder(ActivityEventinfo.this, "alarm_channel")
                            .setContentTitle("Congratulations")
                            .setContentText("Meeting Room is Booked SuccessFully")
                            .setSmallIcon(R.drawable.location)
                            .setContentIntent(resultPendingIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) ActivityEventinfo.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
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
                                if (dataModelNew.getStatus().equals(VariableBag.SUCCESS_CODE)) {

                                    txName.setText(dataModelNew.getEventList().get(0).getSub_category_name());

                                    Glide
                                            .with(ActivityEventinfo.this)
                                            .load(dataModelNew.getEventList().get(0).getPicture())
                                            .into(textImg);

                                    txStartTime.setText(dataModelNew.getEventList().get(0).getStart_time());
                                    txEndTime.setText(dataModelNew.getEventList().get(0).getEnd_time());
                                    txCalander.setText(dataModelNew.getEventList().get(0).getDate());
                                    txtLocation.setText(dataModelNew.getEventList().get(0).getLocation());
                                    txAbout.setText(dataModelNew.getEventList().get(0).getDesciprtion());
                                    txtPrice.setText(dataModelNew.getEventList().get(0).getPrice());
                                    txtVenue.setText(dataModelNew.getEventList().get(0).getVenue());
                                    eventId = dataModelNew.getEventList().get(0).getEvent_id();
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
                                    ticketId=butTicketListResponse.getId().toString().trim();
                                }
                                Toast.makeText(ActivityEventinfo.this, "" + butTicketListResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent ticketIntent = new Intent(ActivityEventinfo.this, ActivityTicket.class);
        ticketIntent.putExtra("subCatId", subCatId);
        ticketIntent.putExtra("ticketId", ticketId);
        ticketIntent.putExtra("eventId", eventId);
        startActivity(ticketIntent);
    }
}