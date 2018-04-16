package com.wastebanking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wastebanking.R;
import com.wastebanking.WBMapActivity;

/**
 * Created by user1 on 4/13/2018.
 */

public class WBLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username,et_password;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_login_activity);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                Intent intent=new Intent(this, WBMapActivity.class);
                startActivity(intent);
        }
    }
}
