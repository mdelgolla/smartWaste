package com.wastebanking.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wastebanking.R;
import com.wastebanking.Utility.WBSquareShape;

import java.util.ArrayList;

/**
 * Created by user1 on 4/14/2018.
 */

public class WBContactCollectinCenter extends AppCompatActivity implements View.OnClickListener {
    private String centerId, name, address, contact;
    private TextView tv_address, tv_contact, tv_organic_price, tv_glass_price, tv_plastic_price, tv_e_waste_price;
    private Button btn_trach_calculator;
    private ImageView img_organic_accpt, img_organic_reject, img_glass_accept, img_glass_reject, img_plastic_accept, img_plastic_reject, img_e_waste_accept, img_e_waste_reject;
    private WBSquareShape layout_organic_waste;
    private ArrayList<String> acceptedWaste;
    private ArrayList<String>priceList;
    private Toolbar my_toolbar;
    private LinearLayout layout_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_contact_collecting_center);
        initViews();
    }

    public void initViews() {
        Intent intent = getIntent();
        centerId = intent.getStringExtra("centerId");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        contact = intent.getStringExtra("contact");
        acceptedWaste = new ArrayList<String>();
        acceptedWaste = intent.getStringArrayListExtra("acceptedWaste");
        priceList = new ArrayList<String>();
        priceList=intent.getStringArrayListExtra("priceList");
        Log.d("centerInfo", "" + centerId + " " + address + " " + acceptedWaste.toString()+" "+priceList.toString());

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitle("Collecting Center");
        setSupportActionBar(my_toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address.setText(address);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_contact.setText(contact);
        btn_trach_calculator = (Button) findViewById(R.id.btn_trach_calculator);
        btn_trach_calculator.setOnClickListener(this);
        img_organic_accpt = (ImageView) findViewById(R.id.img_organic_accpt);
        img_organic_reject = (ImageView) findViewById(R.id.img_organic_reject);
        img_glass_accept = (ImageView) findViewById(R.id.img_glass_accept);
        img_glass_reject = (ImageView) findViewById(R.id.img_glass_reject);
        img_e_waste_accept = (ImageView) findViewById(R.id.img_e_waste_accept);
        img_e_waste_reject = (ImageView) findViewById(R.id.img_e_waste_reject);
        img_plastic_accept = (ImageView) findViewById(R.id.img_plastic_accept);
        img_plastic_reject = (ImageView) findViewById(R.id.img_plastic_reject);
        tv_organic_price = (TextView) findViewById(R.id.tv_organic_price);
        tv_glass_price = (TextView) findViewById(R.id.tv_glass_price);
        tv_e_waste_price = (TextView) findViewById(R.id.tv_e_waste_price);
        tv_plastic_price = (TextView) findViewById(R.id.tv_plastic_price);
        layout_contact=(LinearLayout)findViewById(R.id.layout_contact);
        layout_contact.setOnClickListener(this);

        distinguishAcceptedWaste(acceptedWaste,priceList);
    }

    public void distinguishAcceptedWaste(ArrayList<String> acceptedWasteCategories,ArrayList<String>CategoricalPriceList) {
        if (acceptedWasteCategories.contains("plastic") || acceptedWasteCategories.contains("polythene")) {
            int position=acceptedWasteCategories.indexOf("plastic");
            String price=CategoricalPriceList.get(position);
            img_plastic_accept.setVisibility(View.VISIBLE);
            tv_plastic_price.setVisibility(View.VISIBLE);
            tv_plastic_price.setText("Rs"+price+"/Kg");
        } else {
            img_plastic_accept.setVisibility(View.GONE);
            img_plastic_reject.setVisibility(View.VISIBLE);
            tv_plastic_price.setVisibility(View.GONE);
        }
        if (acceptedWasteCategories.contains("organic")) {
            int position=acceptedWasteCategories.indexOf("organic");
            String price=CategoricalPriceList.get(position);
            img_organic_accpt.setVisibility(View.VISIBLE);
            tv_organic_price.setVisibility(View.VISIBLE);
            tv_organic_price.setText("Rs"+price+"/Kg");
        } else {
            img_organic_accpt.setVisibility(View.GONE);
            img_organic_reject.setVisibility(View.VISIBLE);
            tv_organic_price.setVisibility(View.GONE);
        }
        if (acceptedWasteCategories.contains("e-waste")) {
            int position=acceptedWasteCategories.indexOf("e-waste");
            String price=CategoricalPriceList.get(position);
            img_e_waste_accept.setVisibility(View.VISIBLE);
            tv_e_waste_price.setVisibility(View.VISIBLE);
            tv_e_waste_price.setText("Rs"+price+"/Kg");
        } else {
            img_e_waste_accept.setVisibility(View.GONE);
            img_e_waste_reject.setVisibility(View.VISIBLE);
            tv_e_waste_price.setVisibility(View.GONE);
        }
        if (acceptedWasteCategories.contains("glass")) {
            int position=acceptedWasteCategories.indexOf("glass");
            String price=CategoricalPriceList.get(position);
            img_glass_accept.setVisibility(View.VISIBLE);
            tv_glass_price.setVisibility(View.VISIBLE);
            tv_glass_price.setText("Rs"+price+"/Kg");

        }else {
            img_glass_accept.setVisibility(View.GONE);
            img_glass_reject.setVisibility(View.VISIBLE);
            tv_glass_price.setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_trach_calculator:
                Intent intent1 = new Intent(this, WBTrashCalculator.class);
                intent1.putExtra("acceptedWaste", acceptedWaste);
                intent1.putExtra("priceList",priceList);
                startActivity(intent1);
                break;

            case R.id.layout_contact:
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +tv_contact.getText().toString()));
                startActivity(intent);
                break;

                default:
                    break;
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
