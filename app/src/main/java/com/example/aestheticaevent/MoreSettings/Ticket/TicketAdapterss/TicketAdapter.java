package com.example.aestheticaevent.MoreSettings.Ticket.TicketAdapterss;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.ActivityTicket;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.Ticketdetails;
import com.example.aestheticaevent.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder>{
    Context context;
    List<Ticketdetails> ticketdetailsList;
    List<Ticketdetails> searchList;



    public TicketAdapter(Context context, List<Ticketdetails> ticketdetailsList) {
        this.context = context;
        this.ticketdetailsList = ticketdetailsList;
        this.searchList =  new ArrayList<>(ticketdetailsList);
    }
    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @NonNull
    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item_file, parent, false);
        return new TicketViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        Ticketdetails userdetails = searchList.get(position);
        holder.txtUserName.setText(userdetails.getUsername());
        holder.txtEventSubCateName.setText(userdetails.getSubCategoryName());
        holder.txtEventTime.setText(userdetails.getTiming());
        holder.txtEventDate.setText(userdetails.getDate());
        holder.txtEventAddress.setText(userdetails.getLocation());
        holder.txtPassPrice.setText(userdetails.getTotal_price());
        holder.txtBookingTime.setText(userdetails.getTicketTime());
        holder.txTicketNumber.setText(userdetails.getTicketId());
        holder.txtBookingPerson.setText(userdetails.getQty_member());

        try {
            Glide.with(context)
                    .load(searchList.get(position).getQrcode())
                    .placeholder(R.drawable.person_image)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.qrCodeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.qrCodeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageInDialog(searchList.get(position).getQrcode());
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName,txtEventSubCateName,txtEventDate,txtEventTime,txtEventAddress,txtPassPrice,txtBookingTime,txTicketNumber,txtBookingPerson;
        ImageView qrCodeId;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName=itemView.findViewById(R.id.txtUserName);
            txtEventDate=itemView.findViewById(R.id.txtEventDate);
            txtEventTime=itemView.findViewById(R.id.txtEventTime);
            txtEventAddress=itemView.findViewById(R.id.txtEventAddress);
            txtPassPrice=itemView.findViewById(R.id.txtPassPrice);
            txtBookingTime=itemView.findViewById(R.id.txtBookingTime);
            txtEventSubCateName=itemView.findViewById(R.id.txtEventSubCateName);
            txTicketNumber=itemView.findViewById(R.id.txTicketNumber);
            txtBookingPerson=itemView.findViewById(R.id.txtBookingPerson);
            qrCodeId=itemView.findViewById(R.id.qrCodeId);


        }
    }

    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = new ArrayList<>(ticketdetailsList);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<Ticketdetails> filterList = new ArrayList<>();
                for (Ticketdetails Row : ticketdetailsList) {
                    if (Row.getSubCategoryName().toLowerCase().contains(charString.toLowerCase())) {
                        filterList.add(Row);
                    }
                }
                searchList = new ArrayList<>(filterList);

                if (searchList.isEmpty()) {
                    categoryListRecyclerView.setVisibility(View.GONE);
                } else {
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showImageInDialog(String imageUrl) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.qr_dialog_full_screen);

        ImageView imageView = dialog.findViewById(R.id.imgFullScreen);
        try {
            Glide.with(dialog.getContext()).load(imageUrl).placeholder(R.drawable.background).error(R.drawable.ic_launcher_foreground).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close the dialog
        imageView.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
