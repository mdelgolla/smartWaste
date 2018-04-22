package com.wastebanking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wastebanking.R;

import java.util.ArrayList;

/**
 * Created by user1 on 4/22/2018.
 */

public class WBTrashCalculator extends AppCompatActivity implements View.OnClickListener{
    private CheckBox cb_organic_waste,cb_glass_waste,cb_plastic_waste,cb_e_waste;
    private EditText et_organic_waste,et_glass_waste,et_plastic_waste,et_e_waste;
    private Button btn_next;
    private Toolbar my_toolbar;
    private RelativeLayout rl_orgnaic_waste,rl_glass_waste,rl_plastic_waste,rl_e_waste;
    private ArrayList<String> acceptedWaste;
    private ArrayList<String>priceList;
    private boolean trashCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_trash_calculator);


        initViews();
    }
    public void initViews(){
        rl_orgnaic_waste=(RelativeLayout)findViewById(R.id.rl_orgnaic_waste);
        rl_glass_waste=(RelativeLayout)findViewById(R.id.rl_glass_waste);
        rl_plastic_waste=(RelativeLayout)findViewById(R.id.rl_plastic_waste);
        rl_e_waste=(RelativeLayout)findViewById(R.id.rl_e_waste);

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitle("Trash Calculator");
        setSupportActionBar(my_toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        cb_organic_waste=(CheckBox)findViewById(R.id.cb_organic_waste);
        cb_glass_waste=(CheckBox)findViewById(R.id.cb_glass_waste);
        cb_plastic_waste=(CheckBox)findViewById(R.id.cb_plastic_waste);
        cb_e_waste=(CheckBox)findViewById(R.id.cb_e_waste);
        et_organic_waste=(EditText)findViewById(R.id.et_organic_waste);
        et_glass_waste=(EditText)findViewById(R.id.et_glass_waste);
        et_plastic_waste=(EditText)findViewById(R.id.et_plastic_waste);
        et_e_waste=(EditText)findViewById(R.id.et_e_waste);
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        Intent intent = getIntent();
        if (getIntent().getExtras()!=null){
            acceptedWaste = new ArrayList<String>();
            acceptedWaste = intent.getStringArrayListExtra("acceptedWaste");
            priceList = new ArrayList<String>();
            priceList=intent.getStringArrayListExtra("priceList");
            trashCalculator=true;
            setAvailableSelectingOptions(acceptedWaste,priceList);
        }else {
            trashCalculator=false;
            rl_orgnaic_waste.setVisibility(View.VISIBLE);
            rl_glass_waste.setVisibility(View.VISIBLE);
            rl_plastic_waste.setVisibility(View.VISIBLE);
            rl_e_waste.setVisibility(View.VISIBLE);
        }


    }

    public void setAvailableSelectingOptions(ArrayList<String>wateType,ArrayList<String>priceDetails){
        if (wateType.contains("plastic") || wateType.contains("polythene")) {
            int position=wateType.indexOf("plastic");
            String price=priceDetails.get(position);
            rl_plastic_waste.setVisibility(View.VISIBLE);
            et_plastic_waste.setText("Rs "+ price+"/Kg");
        }
        if (wateType.contains("organic")) {
            int position = wateType.indexOf("organic");
            String price = priceDetails.get(position);
            rl_orgnaic_waste.setVisibility(View.VISIBLE);
            et_organic_waste.setText("Rs "+ price+"/Kg");
        }
        if (wateType.contains("e-waste")) {
            int position = wateType.indexOf("e-waste");
            String price = priceDetails.get(position);
            rl_e_waste.setVisibility(View.VISIBLE);
            et_e_waste.setText("Rs "+ price+"/Kg");
        }
        if (wateType.contains("glass")) {
            int position = wateType.indexOf("glass");
            String price = wateType.get(position);
            rl_glass_waste.setVisibility(View.VISIBLE);
            et_glass_waste.setText("Rs "+ price+"/Kg");
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_next:
                if (trashCalculator==true){

                    Intent intent = new Intent(this,WBCommitTrashCalculation.class);
                    intent.putExtra("acceptedWaste", acceptedWaste);
                    intent.putExtra("priceList",priceList);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this,WBCommitTrashCalculation.class);
                    startActivity(intent);
                }

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
