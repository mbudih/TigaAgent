package com.tiga.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiga.agent.R;
import com.tiga.firebase.model.InvoiceTracking;
import com.tiga.firebase.model.penjualan.TransactionItem;

public class TrackingHolder extends RecyclerView.ViewHolder{

    private ImageView ivIcon;
    private TextView tvCheckPoint, tvDesc, tvStatus;

    public TrackingHolder(View itemView) {
        super(itemView);
        ivIcon = (ImageView) itemView.findViewById(R.id.t_img);
        tvCheckPoint = (TextView) itemView.findViewById(R.id.t_point);
        tvDesc = (TextView) itemView.findViewById(R.id.t_desc);
        tvStatus = (TextView) itemView.findViewById(R.id.t_status);
    }


    public void setValue(InvoiceTracking invoiceTracking){
        tvCheckPoint.setText(invoiceTracking.getTitle());
        tvDesc.setText(invoiceTracking.getDescription());
        tvStatus.setText(invoiceTracking.getStatus());
        if (invoiceTracking.getStatus().equals("DONE")){
            tvStatus.setTextColor(tvStatus.getResources().getColor(R.color.colorPrimary));
        }else{
            tvStatus.setTextColor(tvStatus.getResources().getColor(R.color.c_orange));
        }
    }
}
