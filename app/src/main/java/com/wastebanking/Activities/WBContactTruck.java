package com.wastebanking.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wastebanking.R;

import java.util.ArrayList;

/**
 * Created by user1 on 4/14/2018.
 */

public class WBContactTruck extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_truckId,tv_contact_num,tv_contact;
    private String centerId,name,address,contact;
    private ArrayList<String>acceptedWaste;
    private Toolbar my_toolbar;
    private ImageView img_organic_accpt, img_organic_reject, img_glass_accept, img_glass_reject, img_plastic_accept, img_plastic_reject, img_e_waste_accept, img_e_waste_reject;
    private LinearLayout layout_contact_one,layout_contact_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_contact_truck);
        initViews();
    }
    public void initViews(){
        Intent intent = getIntent();
        centerId=intent.getStringExtra("centerId");
        name=intent.getStringExtra("name");
        address=intent.getStringExtra("address");
        contact=intent.getStringExtra("contact");
        acceptedWaste = new ArrayList<String>();
        acceptedWaste = intent.getStringArrayListExtra("acceptedWaste");
        Log.d("truckInfo",""+centerId+" "+name+" "+acceptedWaste.toString());

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitle("Collecting Truck");
        setSupportActionBar(my_toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tv_truckId=(TextView)findViewById(R.id.tv_truckId);
        tv_truckId.setText(centerId);
        tv_contact_num=(TextView)findViewById(R.id.tv_contact_num);
        tv_contact_num.setText(contact);
        tv_contact=(TextView)findViewById(R.id.tv_contact);
        tv_contact.setText(contact);
        img_organic_accpt = (ImageView) findViewById(R.id.img_organic_accpt);
        img_organic_reject = (ImageView) findViewById(R.id.img_organic_reject);
        img_glass_accept = (ImageView) findViewById(R.id.img_glass_accept);
        img_glass_reject = (ImageView) findViewById(R.id.img_glass_reject);
        img_e_waste_accept = (ImageView) findViewById(R.id.img_e_waste_accept);
        img_e_waste_reject = (ImageView) findViewById(R.id.img_e_waste_reject);
        img_plastic_accept = (ImageView) findViewById(R.id.img_plastic_accept);
        img_plastic_reject = (ImageView) findViewById(R.id.img_plastic_reject);
        layout_contact_one=(LinearLayout)findViewById(R.id.layout_contact_one) ;
        layout_contact_one.setOnClickListener(this);
        layout_contact_two=(LinearLayout)findViewById(R.id.layout_contact_two);
        layout_contact_two.setOnClickListener(this);

        distinguishAcceptedWaste(acceptedWaste);
    }

    public void distinguishAcceptedWaste(ArrayList<String> acceptedWasteCategories) {
        if (acceptedWasteCategories.contains("plastic") || acceptedWasteCategories.contains("polythene")) {
            img_plastic_accept.setVisibility(View.VISIBLE);

        } else {
            img_plastic_accept.setVisibility(View.GONE);
            img_plastic_reject.setVisibility(View.VISIBLE);

        }
        if (acceptedWasteCategories.contains("organic")) {
            img_organic_accpt.setVisibility(View.VISIBLE);

        } else {
            img_organic_accpt.setVisibility(View.GONE);
            img_organic_reject.setVisibility(View.VISIBLE);

        }
        if (acceptedWasteCategories.contains("e-waste")) {
            img_e_waste_accept.setVisibility(View.VISIBLE);

        } else {
            img_e_waste_accept.setVisibility(View.GONE);
            img_e_waste_reject.setVisibility(View.VISIBLE);
        }
        if (acceptedWasteCategories.contains("glass")) {
            img_glass_accept.setVisibility(View.VISIBLE);
        }else {
            img_glass_accept.setVisibility(View.GONE);
            img_glass_reject.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.layout_contact_one:
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +tv_contact_num.getText().toString()));
                startActivity(intent);

            case R.id.layout_contact_two:
                Intent intent1=new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" +tv_contact.getText().toString()));
                startActivity(intent1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
