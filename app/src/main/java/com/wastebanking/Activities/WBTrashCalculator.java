package com.wastebanking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wastebanking.R;

/**
 * Created by user1 on 4/22/2018.
 */

public class WBTrashCalculator extends AppCompatActivity {
    private CheckBox cb_organic_waste,cb_glass_waste,cb_plastic_waste,cb_e_waste;
    private EditText et_organic_waste,et_glass_waste,et_plastic_waste,et_e_waste;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_trash_calculator);
        initViews();
    }
    public void initViews(){
        cb_organic_waste=(CheckBox)findViewById(R.id.cb_organic_waste);
        cb_glass_waste=(CheckBox)findViewById(R.id.cb_glass_waste);
        cb_plastic_waste=(CheckBox)findViewById(R.id.cb_plastic_waste);
        cb_e_waste=(CheckBox)findViewById(R.id.cb_e_waste);
        et_organic_waste=(EditText)findViewById(R.id.et_organic_waste);
        et_glass_waste=(EditText)findViewById(R.id.et_glass_waste);
        et_plastic_waste=(EditText)findViewById(R.id.et_plastic_waste);
        et_e_waste=(EditText)findViewById(R.id.et_e_waste);
        btn_next=(Button)findViewById(R.id.btn_next);
    }
}
