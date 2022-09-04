package com.ujjman.money.expensetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder>{

    Context context;
    ArrayList<PaymentEntry> entryList;
    ArrayList<PaymentEntry> displayedList;
    private int lastPosition=-1;

    public PaymentListAdapter(Context c, ArrayList<PaymentEntry> p) {
        context = c;
        entryList = p;
    }

    public void updateList(ArrayList<PaymentEntry> list){
        entryList = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.people_Name.setText(entryList.get(i).getSenderName()+" to "+entryList.get(i).getReceiverName());
        myViewHolder.dateCreated.setText("Created on : "+entryList.get(i).getDate()+" "+entryList.get(i).getTime());
        myViewHolder.amount.setText("Rs. "+entryList.get(i).getAmount());
        myViewHolder.creatorName.setText("Created By : "+entryList.get(i).getCreatorName());
        setAnimation(myViewHolder.parent,i);
        final String getSenderName = entryList.get(i).getSenderName();
        final String getReceiverName = entryList.get(i).getReceiverName();
        final String getDate = entryList.get(i).getDate();
        final String getTime = entryList.get(i).getTime();
        final String getAmount = entryList.get(i).getAmount();
        final String getSenderPhone = entryList.get(i).getSenderPhone();
        final String getReceiverPhone = entryList.get(i).getReceiverPhone();
        final String getDescription = entryList.get(i).getDescription();
        final String getPaymentId = entryList.get(i).getPaymentId();
        final String getCreatorName=entryList.get(i).getCreatorName();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim=AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.button_anim);
                v.startAnimation(anim);
                Handler mHandler = new Handler();
                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Animation a=AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.button_anim2);
                        v.startAnimation(a);
                    }
                };

                // its trigger runnable after 4000 millisecond.
                mHandler.postDelayed(mRunnable,130);
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent aa = new Intent(context, EditPaymentEntry.class);
                        if(context.getClass().getSimpleName().equals("TransactionHistory"))
                        {
                            aa=new Intent(context,EditTransactionHistory.class);
                        }
                        aa.putExtra("senderName", getSenderName);
                        aa.putExtra("receiverName", getReceiverName);
                        aa.putExtra("senderPhone", getSenderPhone);
                        aa.putExtra("receiverPhone", getReceiverPhone);
                        aa.putExtra("date", getDate);
                        aa.putExtra("time", getTime);
                        aa.putExtra("amount", getAmount);
                        aa.putExtra("description", getDescription);
                        aa.putExtra("paymentId", getPaymentId);
                        aa.putExtra("className",context.getClass().getSimpleName());
                        aa.putExtra("creatorName",getCreatorName);
                        context.startActivity(aa);
                        ((Activity) context).finish();
                    }
                };

                // its trigger runnable after 4000 millisecond.
                mHandler=new Handler();
                mHandler.postDelayed(mRunnable,250);

            }
        });
    }
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation;
            if (position%2==0)
            animation=AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.slide_in_left);
            else
                animation=AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.right_to_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView people_Name, dateCreated, amount,creatorName;
        LinearLayout parent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=(LinearLayout) itemView.findViewById(R.id.linearlayout_all_payments);
            people_Name = (TextView) itemView.findViewById(R.id.people_name);
            dateCreated = (TextView) itemView.findViewById(R.id.date);
            amount = (TextView) itemView.findViewById(R.id.amount);
            creatorName = (TextView) itemView.findViewById(R.id.creator_name);
        }
    }

}