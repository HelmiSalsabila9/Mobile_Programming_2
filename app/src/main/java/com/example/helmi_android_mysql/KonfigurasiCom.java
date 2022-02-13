package com.example.helmi_android_mysql;

public class KonfigurasiCom {

    public static final String URL_ADD_COM = "http://192.168.100.15:8080/api_helmi_mysql_android/tambah_company.php";
    public static final String URL_GET_ALL_COM = "http://192.168.100.15:8080/api_helmi_mysql_android/tampil_semua_company.php";
    public static final String URL_GET_COM = "http://192.168.100.15:8080/api_helmi_mysql_android/tampil_company.php?id_company=";
    public static final String URL_UPDATE_COM = "http://192.168.100.15:8080/api_helmi_mysql_android/update_company.php";
    public static final String URL_DELETE_COM = "http://192.168.100.15:8080/api_helmi_mysql_android/hapus_company.php?id_company=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_COMPANY_ID = "id_company";
    public static final String KEY_COMPANY_NAMA = "nama_company";
    public static final String KEY_COMPANY_ALAMAT = "alamat";
    public static final String KEY_COMPANY_WEBSITE = "website";
    public static final String KEY_COMPANY_TELP = "no_telp";

    //JSON Tags
    public static final String TAG_JSON_ARRAY_COM = "result";
    public static final String TAG_ID_COMPANY = "id_company";
    public static final String TAG_NAMA_COM = "nama_company";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_WEBSITE = "website";
    public static final String TAG_TELP = "no_telp";

    //ID company
    //COM itu singkatan dari Company
    public static final String COMPANY_ID = "company_id";

}
