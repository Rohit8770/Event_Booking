package com.example.aestheticaevent.HomeScreen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.Ticketdetails;
import com.example.aestheticaevent.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiViewAdapter> {

    Context context;
    List<Ticketdetails> ticketdetailsList;

    public NotificationAdapter(Context context, List<Ticketdetails> ticketdetailsList) {
        this.context = context;
        this.ticketdetailsList = ticketdetailsList;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotiViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item_file, parent, false);
        return new NotiViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotiViewAdapter holder, int position) {
        Ticketdetails userdetails = ticketdetailsList.get(position);
        holder.txNotificationName.setText(userdetails.getSubCategoryName());
        holder.txNotificationTime.setText(userdetails.getTicketTime());
    }

    @Override
    public int getItemCount() {
        return ticketdetailsList.size();
    }

    public class NotiViewAdapter extends RecyclerView.ViewHolder {
        TextView txNotificationTime,txNotificationName;
        public NotiViewAdapter(@NonNull View itemView) {
            super(itemView);
            txNotificationTime=itemView.findViewById(R.id.txNotificationTime);
            txNotificationName=itemView.findViewById(R.id.txNotificationName);
        }
    }
}
