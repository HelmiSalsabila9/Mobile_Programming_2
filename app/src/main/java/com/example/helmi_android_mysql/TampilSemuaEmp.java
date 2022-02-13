package com.example.helmi_android_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.HashMap;

public class TampilSemuaEmp extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tampil_semua_emp);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID_EMPLOYEE);
                String name = jo.getString(Konfigurasi.TAG_NAMA);
                String desg = jo.getString(Konfigurasi.TAG_POSISI);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Konfigurasi.TAG_ID_EMPLOYEE, id);
                employees.put(Konfigurasi.TAG_NAMA, name);
                employees.put(Konfigurasi.TAG_POSISI, desg);
                list.add(employees);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(TampilSemuaEmp.this, list, R.layout.list_item, new String[]{Konfigurasi.TAG_ID_EMPLOYEE, Konfigurasi.TAG_NAMA, Konfigurasi.TAG_POSISI}, new int[]{R.id.id, R.id.name, R.id.desg});
        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaEmp.this,"Lagi ambil Data nich..","Tunggu yak...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilEmployee.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String employeeId = map.get(Konfigurasi.TAG_ID_EMPLOYEE).toString();
        intent.putExtra(Konfigurasi.EMPLOYEE_ID,employeeId);
        startActivity(intent);
    }
}