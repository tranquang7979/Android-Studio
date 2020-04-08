package com.example.databasetutorials;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasetutorials.dao.*;

import java.util.ArrayList;

public class AccountManagementActivity extends AppCompatActivity {

    ListView lv_accounts;
    AccountDAO accDAO ;

    TextView tv_createnew;
    ArrayList<Account> accounts;
    AccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        lv_accounts = findViewById(R.id.lv_accounts);
        accDAO = new AccountDAO(this);

        accounts = accDAO.getAllAccounts();

        adapter =new AccountAdapter(this, accounts);
        lv_accounts.setAdapter(adapter);

        tv_createnew =findViewById(R.id.tv_createnew);

        tv_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //explicit intent

                Intent it = new Intent(AccountManagementActivity.this, AddAccountActivity.class);
                startActivityForResult(it,1);

            }
        });



    }


    public void refresh(){
        accounts = accDAO.getAllAccounts();
        adapter = new AccountAdapter(this, accounts);
        lv_accounts.setAdapter(adapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
         refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class AccountAdapter extends BaseAdapter
    {
        private ArrayList<Account> items;
        private LayoutInflater flater;
        public AccountAdapter(Context ctx,  ArrayList<Account> items)
        {
            this.items =items;
            flater = LayoutInflater.from(ctx);
        }
        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = flater.inflate(R.layout.account_item,null);
            TextView tv_accountname = convertView.findViewById(R.id.tv_accountname);
            TextView tv_rolename = convertView.findViewById(R.id.tv_role);
            tv_accountname.setText(items.get(position).getUsername());
            tv_rolename.setText(items.get(position).getRole());

            Button btn_del =  convertView.findViewById(R.id.btn_delete);
            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = items.get(position).getUsername();
                    String id = items.get(position).getId()+"";
                    //delete from db
                    accDAO.deleteAccount(id);
                    Toast.makeText(AccountManagementActivity.this,"Account "+username +" is deleted.", Toast.LENGTH_LONG).show();
                    accounts = accDAO.getAllAccounts();
                    notifyDataSetChanged();

                    refresh();
                }
            });
            return convertView;
        }
    }

}
