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

public class TampilCompany extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextIdCom;
    private EditText editTextNameCom;
    private EditText editTextAlamatCom;
    private EditText editTextWebCom;
    private EditText editTextTelpCom;

    private Button buttonUpdateCom;
    private Button buttonDeleteCom;
    private String id_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tampil_company);
        Intent intent = getIntent();
        id_company = intent.getStringExtra(KonfigurasiCom.COMPANY_ID);
        editTextIdCom = (EditText) findViewById(R.id.editTextIdCom);
        editTextNameCom = (EditText) findViewById(R.id.editTextNameCom);
        editTextAlamatCom = (EditText) findViewById(R.id.editTextAlamatCom);
        editTextWebCom = (EditText) findViewById(R.id.editTextWebCom);
        editTextTelpCom = (EditText) findViewById(R.id.editTextTelpCom);

        buttonUpdateCom = (Button) findViewById(R.id.buttonUpdateCom);
        buttonDeleteCom = (Button) findViewById(R.id.buttonDeleteCom);
        buttonUpdateCom.setOnClickListener(this);
        buttonDeleteCom.setOnClickListener(this);
        editTextIdCom.setText(id_company);
        getCompany();
    }

    private void getCompany(){
        class GetCompany extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading =
                        ProgressDialog.show(TampilCompany.this,"Fetching...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showCompany(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCom rh = new RequestHandlerCom();
                String s = rh.sendGetRequestParam(KonfigurasiCom.URL_GET_COM, id_company);
                return s;
            }
        }
        GetCompany ge = new GetCompany();
        ge.execute();
    }
    private void showCompany(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiCom.TAG_JSON_ARRAY_COM);
            JSONObject c = result.getJSONObject(0);
            String nama_company = c.getString(KonfigurasiCom.TAG_NAMA_COM);
            String alamat = c.getString(KonfigurasiCom.TAG_ALAMAT);
            String website = c.getString(KonfigurasiCom.TAG_WEBSITE);
            String telp = c.getString(KonfigurasiCom.TAG_TELP);

            editTextNameCom.setText(nama_company);
            editTextAlamatCom.setText(alamat);
            editTextWebCom.setText(website);
            editTextTelpCom.setText(telp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateCompany(){
        final String nama_company = editTextNameCom.getText().toString().trim();
        final String alamat = editTextAlamatCom.getText().toString().trim();
        final String website = editTextWebCom.getText().toString().trim();
        final String no_telp = editTextTelpCom.getText().toString().trim();
        class UpdateCompany extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilCompany.this,"Updating...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilCompany.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(KonfigurasiCom.KEY_COMPANY_ID,id_company);
                hashMap.put(KonfigurasiCom.KEY_COMPANY_NAMA,nama_company);
                hashMap.put(KonfigurasiCom.KEY_COMPANY_ALAMAT,alamat);
                hashMap.put(KonfigurasiCom.KEY_COMPANY_WEBSITE,website);
                hashMap.put(KonfigurasiCom.KEY_COMPANY_TELP,no_telp);
                RequestHandlerCom rh = new RequestHandlerCom();
                String s = rh.sendPostRequest(KonfigurasiCom.URL_UPDATE_COM,hashMap);
                return s;
            }
        }
        UpdateCompany ue = new UpdateCompany();
        ue.execute();
        startActivity(new Intent(TampilCompany.this, TampilSemuaCom.class));
    }
    private void deleteCompany(){
        class DeleteCompany extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilCompany.this, "Updating nich...", "Tunggu bentar yakk...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilCompany.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCom rh = new RequestHandlerCom();
                String s = rh.sendGetRequestParam(KonfigurasiCom.URL_DELETE_COM, id_company);
                return s;
            }
        }
        DeleteCompany de = new DeleteCompany();
        de.execute();
    }
    private void confirmDeleteCompany(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin di mau hapus nich..?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteCompany();
                        startActivity(new Intent(TampilCompany.this, TampilSemuaCom.class));
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
        if(v == buttonUpdateCom){
            updateCompany();
        }
        if(v == buttonDeleteCom){
            confirmDeleteCompany();
        }
    }

    public void kembaliCom(View view) {
        Intent i = new Intent(TampilCompany.this, TampilSemuaCom.class);
        startActivity(i);
    }
}