package com.example.helmi_android_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class InsertCompany extends AppCompatActivity implements View.OnClickListener {

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextNameCom;
    private EditText editTextAlamatCom;
    private EditText editTextWebCom;
    private EditText editTextTelpCom;
    private Button buttonAddCom;
    private Button buttonViewCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_company);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inisialisasi dari View
        editTextNameCom = (EditText) findViewById(R.id.editTextNameCom);
        editTextAlamatCom = (EditText) findViewById(R.id.editTextDesgEmp);
        editTextWebCom = (EditText) findViewById(R.id.editTextWebCom);
        editTextTelpCom = (EditText) findViewById(R.id.editTextTelpCom);
        buttonAddCom = (Button) findViewById(R.id.buttonAddCom);
        buttonViewCom = (Button) findViewById(R.id.buttonViewCom);

        //Setting listeners to button
        buttonAddCom.setOnClickListener(this);
        buttonViewCom.setOnClickListener(this);
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Company (CREATE)
    private void addCompany() {
        final String nama_company = editTextNameCom.getText().toString().trim();
        final String alamat = editTextAlamatCom.getText().toString().trim();
        final String website = editTextWebCom.getText().toString().trim();
        final String no_telp = editTextTelpCom.getText().toString().trim();

        class AddCompany extends AsyncTask<Void, Void, String> {
            ProgressDialog loadingCom;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingCom = ProgressDialog.show(InsertCompany.this, "Menambahkan nich...", "Tunggu bentar yak...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingCom.dismiss();
                Toast.makeText(InsertCompany.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiCom.KEY_COMPANY_NAMA, nama_company);
                params.put(KonfigurasiCom.KEY_COMPANY_ALAMAT, alamat);
                params.put(KonfigurasiCom.KEY_COMPANY_WEBSITE, website);
                params.put(KonfigurasiCom.KEY_COMPANY_TELP, no_telp);
                RequestHandlerCom rhc = new RequestHandlerCom();
                String resCom = rhc.sendPostRequest(KonfigurasiCom.URL_ADD_COM, params);
                return resCom;
            }
        }
        AddCompany ac = new AddCompany();
        ac.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAddCom) {
            addCompany();
        }
        if (v == buttonViewCom) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}