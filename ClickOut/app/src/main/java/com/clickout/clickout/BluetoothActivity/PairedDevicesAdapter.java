package com.clickout.clickout.BluetoothActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clickout.clickout.R;

public class PairedDevicesAdapter extends RecyclerView.Adapter<PairedDevicesAdapter.ViewHolder> {
    String[] devices;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;

    public PairedDevicesAdapter(Context context1, String[] devices) {

        this.devices = devices;
        context = context1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.tv_device);
        }
    }

    @Override
    public PairedDevicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.paired_devices, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(devices[position]);
    }

    @Override
    public int getItemCount() {
        return devices.length;
    }

    public String getItem(int position) {
        if (position >= this.devices.length) {
            return null;
        }

        return this.devices[position];
    }
}


