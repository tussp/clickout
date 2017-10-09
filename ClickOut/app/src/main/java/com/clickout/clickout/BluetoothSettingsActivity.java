package com.clickout.clickout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.clickout.clickout.BluetoothActivity.BluetoothTestActivity;
import com.clickout.clickout.BluetoothActivity.PairedDevicesAdapter;
import com.clickout.clickout.BluetoothActivity.PairedDevicesTouchListener;

public class BluetoothSettingsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PairedDevicesAdapter pairedDevicesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_bluethooth_settings);

        this.recyclerView = (RecyclerView) this.findViewById(R.id.paired_devices);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.pairedDevicesAdapter = new PairedDevicesAdapter(this, this.getDevices());
        this.recyclerView.setAdapter(this.pairedDevicesAdapter);

        this.recyclerView.addOnItemTouchListener(new PairedDevicesTouchListener(this, this.recyclerView, new PairedDevicesTouchListener.PairedDevicesClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String device = pairedDevicesAdapter.getItem(position);
//                BusyIndicator busyIndicator = new BusyIndicator(BluetoothSettingsActivity.this);
//                busyIndicator.show();
                Intent intent = new Intent(BluetoothSettingsActivity.this, BluetoothTestActivity.class);
                startActivity(intent);
            }
        }));


    }

    private String[] getDevices() {
        String[] devices =
                {
                    "ANDROID",
                    "PHP",
                    "BLOGGER",
                    "WORDPRESS",
                    "JOOMLA",
                    "ASP.NET",
                    "JAVA",
                    "C++",
                    "MATHS",
                    "HINDI",
                    "ENGLISH"
                };

        return devices;
    }
}
