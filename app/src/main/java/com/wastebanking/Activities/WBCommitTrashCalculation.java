package com.wastebanking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wastebanking.R;
import com.wastebanking.Utility.WBAlertUtils;

import java.util.ArrayList;

/**
 * Created by user1 on 4/22/2018.
 */

public class WBCommitTrashCalculation extends AppCompatActivity implements View.OnClickListener{
    private Toolbar my_toolbar;
    private ArrayList<String> acceptedWaste;
    private ArrayList<String>priceList;
    private RelativeLayout rl_organic,rl_glass,rl_plastic,rl_eWaste;
    private Button btn_calculate;
    private double organic_price=10.00,glass_price=10.00,plastic_price=10.00,e_waste_price=10.00;
    private EditText et_orgnic_amount,et_glass_amount,et_plastic_amount,et_ewaste_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_commit_trash_calculation);
        initViews();
    }
    public void initViews(){
        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitle("Trash Calculator");
        setSupportActionBar(my_toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        rl_organic=(RelativeLayout)findViewById(R.id.rl_organic);
        rl_plastic=(RelativeLayout)findViewById(R.id.rl_plastic);
        rl_glass=(RelativeLayout)findViewById(R.id.rl_glass);
        rl_eWaste=(RelativeLayout)findViewById(R.id.rl_eWaste);
        et_orgnic_amount=(EditText)findViewById(R.id.et_orgnic_amount);
        et_glass_amount=(EditText)findViewById(R.id.et_glass_amount) ;
        et_plastic_amount=(EditText)findViewById(R.id.et_plastic_amount) ;
        et_ewaste_amount=(EditText)findViewById(R.id.et_ewaste_amount) ;
        btn_calculate=(Button)findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(this);
        Intent intent = getIntent();
        if (getIntent().getExtras()!=null){
            acceptedWaste = new ArrayList<String>();
            acceptedWaste = intent.getStringArrayListExtra("acceptedWaste");
            priceList = new ArrayList<String>();
            priceList=intent.getStringArrayListExtra("priceList");
            setAvailableSelectingOptions(acceptedWaste,priceList);
        }else {
            rl_organic.setVisibility(View.VISIBLE);
            rl_glass.setVisibility(View.VISIBLE);
            rl_plastic.setVisibility(View.VISIBLE);
            rl_eWaste.setVisibility(View.VISIBLE);
        }
    }
    public void setAvailableSelectingOptions(ArrayList<String>wateType,ArrayList<String>priceDetails){
        if (wateType.contains("plastic") || wateType.contains("polythene")) {
            int position=wateType.indexOf("plastic");
            String price=priceDetails.get(position);
            plastic_price=Double.parseDouble(price);
            rl_plastic.setVisibility(View.VISIBLE);
        }
        if (wateType.contains("organic")) {
            int position = wateType.indexOf("organic");
            String price = priceDetails.get(position);
            organic_price=Double.parseDouble(price);
            rl_organic.setVisibility(View.VISIBLE);
        }
        if (wateType.contains("e-waste")) {
            int position = wateType.indexOf("e-waste");
            String price = priceDetails.get(position);
            e_waste_price=Double.parseDouble(price);
            rl_eWaste.setVisibility(View.VISIBLE);
        }
        if (wateType.contains("glass")) {
            int position = wateType.indexOf("glass");
            String price = wateType.get(position);
            glass_price=Double.parseDouble(price);
            rl_glass.setVisibility(View.VISIBLE);
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_calculate:
                String organic_amount;
                String glass_amount;
                String plastic_amount;
                String e_waste_amount;
                if (!et_orgnic_amount.getText().toString().equals("")){
                   organic_amount=et_orgnic_amount.getText().toString();
                }else {
                    organic_amount="0.0";
                }
                if (!et_plastic_amount.getText().toString().equals("")){
                    plastic_amount=et_plastic_amount.getText().toString();
                }else {
                    plastic_amount="0.0";
                }
                if (!et_glass_amount.getText().toString().equals("")){
                    glass_amount=et_glass_amount.getText().toString();
                }else {
                    glass_amount="0.0";
                }
                if (!et_ewaste_amount.getText().toString().equals("")){
                    e_waste_amount=et_ewaste_amount.getText().toString();
                }else {
                    e_waste_amount="0.0";
                }
                Double points=Double.parseDouble(organic_amount)*organic_price+Double.parseDouble(plastic_amount)*plastic_price
                        +Double.parseDouble(glass_amount)*glass_price+Double.parseDouble(e_waste_amount)*e_waste_price;
                WBAlertUtils.showOKDialog(WBCommitTrashCalculation.this,"Points Earned","You Have Earned Rs. "+points+" Points");
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
