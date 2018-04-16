package com.wastebanking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wastebanking.R;

/**
 * Created by user1 on 4/14/2018.
 */

public class WBContactTruck extends AppCompatActivity {
    private TextView tv_truckId,tv_contact_num,tv_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_contact_truck);
        initViews();
    }
    public void initViews(){
        tv_truckId=(TextView)findViewById(R.id.tv_truckId);
        tv_contact_num=(TextView)findViewById(R.id.tv_contact_num);
        tv_contact=(TextView)findViewById(R.id.tv_contact);
    }
}
