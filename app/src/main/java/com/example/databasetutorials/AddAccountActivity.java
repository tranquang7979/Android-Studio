package com.example.databasetutorials;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasetutorials.dao.AccountDAO;

public class AddAccountActivity extends AppCompatActivity {

    String roles[] = {"Administrator","Customer"};
    Spinner sp_roles;

    EditText ed_username, ed_password,ed_confirmpassword;
    AccountDAO accDAO;

    Button btn_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        sp_roles =findViewById(R.id.sp_roles);
        ArrayAdapter<String> role_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        sp_roles.setAdapter(role_adapter);

        accDAO =new AccountDAO(this);

        ed_username =findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        ed_confirmpassword = findViewById(R.id.ed_confirmpassword);
        btn_done = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    boolean isExisting = accDAO.checkExistingUsername(ed_username.getText().toString());
                if(isExisting){
                    Toast.makeText(AddAccountActivity.this,"Username existed!!!", Toast.LENGTH_LONG).show();

                }else
                {
                                          if(ed_password.getText().toString().equals(ed_confirmpassword.getText().toString())){
                            //insert db
                            boolean result = accDAO.createAccount(ed_username.getText().toString(),
                                    ed_password.getText().toString(),
                                    sp_roles.getSelectedItem().toString());

                    if(result){
                        // back to AccountManagementActivity
                        Intent it = new Intent(AddAccountActivity.this, AccountManagementActivity.class);
                        setResult(Activity.RESULT_OK,it);

                        finish();

                    }else{
                        Toast.makeText(AddAccountActivity.this,"Oops!!! something wrong", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(AddAccountActivity.this,"Pls. check your password and confirm password", Toast.LENGTH_LONG).show();

                }
            }
            }
        });
    }
}
