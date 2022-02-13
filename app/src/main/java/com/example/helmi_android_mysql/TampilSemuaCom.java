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

public class TampilSemuaCom extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tampil_semua_com);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }
    private void showCompany(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiCom.TAG_JSON_ARRAY_COM);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_company = jo.getString(KonfigurasiCom.TAG_ID_COMPANY);
                String nama_company = jo.getString(KonfigurasiCom.TAG_NAMA_COM);
                String alamat = jo.getString(KonfigurasiCom.TAG_ALAMAT);
                String website = jo.getString(KonfigurasiCom.TAG_WEBSITE);
                String no_telp = jo.getString(KonfigurasiCom.TAG_TELP);
                HashMap<String,String> company = new HashMap<>();
                company.put(KonfigurasiCom.TAG_ID_COMPANY, id_company);
                company.put(KonfigurasiCom.TAG_NAMA_COM, nama_company);
                company.put(KonfigurasiCom.TAG_ALAMAT, alamat);
                company.put(KonfigurasiCom.TAG_WEBSITE, website);
                company.put(KonfigurasiCom.TAG_TELP, no_telp);
                list.add(company);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(TampilSemuaCom.this, list, R.layout.list_item_com, new String[]{KonfigurasiCom.TAG_ID_COMPANY, KonfigurasiCom.TAG_NAMA_COM, KonfigurasiCom.TAG_ALAMAT, KonfigurasiCom.TAG_WEBSITE, KonfigurasiCom.TAG_TELP}, new int[]{R.id.id_company, R.id.nama_company, R.id.alamat, R.id.website, R.id.no_telp});
        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaCom.this,"Lagi ambil Data nich..","Tunggu bentar yak...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showCompany();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCom rh = new RequestHandlerCom();
                String s = rh.sendGetRequest(KonfigurasiCom.URL_GET_ALL_COM);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilCompany.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String companyId = map.get(KonfigurasiCom.TAG_ID_COMPANY).toString();
        intent.putExtra(KonfigurasiCom.COMPANY_ID,companyId);
        startActivity(intent);
    }
}