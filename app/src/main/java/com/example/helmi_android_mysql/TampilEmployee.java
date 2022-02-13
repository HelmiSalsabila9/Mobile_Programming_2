package com.example.helmi_android_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilEmployee extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextIdEmp;
    private EditText editTextNameEmp;
    private EditText editTextDesgEmp;
    private EditText editTextSalaryEmp;
    private Button buttonUpdate;
    private Button buttonDelete;
    private String id_employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tampil_employee);
        Intent intent = getIntent();
        id_employee = intent.getStringExtra(Konfigurasi.EMPLOYEE_ID);
        editTextIdEmp = (EditText) findViewById(R.id.editTextIdEmp);
        editTextNameEmp = (EditText) findViewById(R.id.editTextNameEmp);
        editTextDesgEmp = (EditText) findViewById(R.id.editTextDesgEmp);
        editTextSalaryEmp = (EditText) findViewById(R.id.editTextSalaryEmp);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        editTextIdEmp.setText(id_employee);
        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading =
                        ProgressDialog.show(TampilEmployee.this,"Fetching...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_EMP,id_employee);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }
    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Konfigurasi.TAG_NAMA);
            String desg = c.getString(Konfigurasi.TAG_POSISI);
            String sal = c.getString(Konfigurasi.TAG_GAJIH);
            editTextNameEmp.setText(name);
            editTextDesgEmp.setText(desg);
            editTextSalaryEmp.setText(sal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateEmployee(){
        final String name = editTextNameEmp.getText().toString().trim();
        final String desg = editTextDesgEmp.getText().toString().trim();
        final String salary = editTextSalaryEmp.getText().toString().trim();
        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading =
                        ProgressDialog.show(TampilEmployee.this,"Updating...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilEmployee.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_EMPLOYEE_ID,id_employee);
                hashMap.put(Konfigurasi.KEY_EMPLOYEE_NAMA,name);
                hashMap.put(Konfigurasi.KEY_EMPLOYEE_POSISI,desg);
                hashMap.put(Konfigurasi.KEY_EMPLOYEE_GAJIH,salary);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_EMP,hashMap);
                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        startActivity(new Intent(TampilEmployee.this, TampilSemuaEmp.class));
    }
    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilEmployee.this, "Updating nich...", "Tunggu yakk...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilEmployee.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE_EMP, id_employee);
                return s;
            }
        }
        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }
    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin di mau hapus nich..?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new
                                Intent(TampilEmployee.this, TampilSemuaEmp.class));
                    }
                });
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateEmployee();
        }
        if(v == buttonDelete){
            confirmDeleteEmployee();
        }
    }

        public void kembali(View view) {
        Intent i = new Intent(TampilEmployee.this, TampilSemuaEmp.class);
        startActivity(i);
    }
}