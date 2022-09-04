package com.ujjman.money.expensetracker;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class PaymentEntry {
    public String senderName;
    public String senderPhone;
    public String receiverName;
    public String receiverPhone;
    public String amount;
    public String description;
    public String date;
    public String time;
    public String createdBy;
    public String paymentId;
    public PaymentEntry()
    {}
    public PaymentEntry(String senderName,String senderPhone,String receiverName,String receiverPhone,String amount,String description,String date,String time,String createdBy,String paymentId)
    {
        this.senderName=senderName.trim();
        this.senderPhone=senderPhone.trim();
        this.receiverName=receiverName.trim();
        this.receiverPhone=receiverPhone.trim();
        this.amount=amount.trim();
        this.description=description.trim();
        this.date=date.trim();
        this.time=time.trim();
        this.createdBy=createdBy.trim();
        this.paymentId=paymentId.trim();
    }
    public String getSenderName()
    {
        return senderName;
    }
    public String getReceiverName()
    {
        return receiverName;
    }
    public String getSenderPhone()
    {
        return senderPhone;
    }
    public String getReceiverPhone()
    {
        return receiverPhone;
    }
    public String getAmount()
    {
        return amount;
    }
    public String getDescription()
    {
        return description;
    }
    public String getDate()
    {
        return date;
    }
    public String getTime()
    {
        return time;
    }
    public String getCreatorName()
    {
        return createdBy;
    }
    public String getPaymentId()
    {
        return paymentId;
    }

}
