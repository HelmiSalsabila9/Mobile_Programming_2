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

import com.example.helmi_android_mysql.ui.home.HomeFragment;

import java.util.HashMap;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextNameEmp;
    private EditText editTextDesgEmp;
    private EditText editTextSalarayEmp;
    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inisialisasi dari View
        editTextNameEmp = (EditText) findViewById(R.id.editTextNameEmp);
        editTextDesgEmp = (EditText) findViewById(R.id.editTextDesgEmp);
        editTextSalarayEmp = (EditText) findViewById(R.id.editTextSalaryEmp);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee() {
        final String name = editTextNameEmp.getText().toString().trim();
        final String desg = editTextDesgEmp.getText().toString().trim();
        final String sal = editTextSalarayEmp.getText().toString().trim();
        class AddEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InsertActivity.this, "Menambahkan nich...", "Tunggu yak...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(InsertActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_EMPLOYEE_NAMA, name);
                params.put(Konfigurasi.KEY_EMPLOYEE_POSISI, desg);
                params.put(Konfigurasi.KEY_EMPLOYEE_GAJIH, sal);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd) {
            addEmployee();
        }
        if (v == buttonView) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}