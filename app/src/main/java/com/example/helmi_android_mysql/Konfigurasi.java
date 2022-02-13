package com.example.helmi_android_mysql;

public class Konfigurasi {

    public static final String URL_ADD = "http://192.168.100.15:8080/api_helmi_mysql_android/tambah_employee.php";
    public static final String URL_GET_ALL = "http://192.168.100.15:8080/api_helmi_mysql_android/tampil_semua_employee.php";
    public static final String URL_GET_EMP = "http://192.168.100.15:8080/api_helmi_mysql_android/tampil_employee.php?id_employee=";
    public static final String URL_UPDATE_EMP = "http://192.168.100.15:8080/api_helmi_mysql_android/update_employee.php";
    public static final String URL_DELETE_EMP = "http://192.168.100.15:8080/api_helmi_mysql_android/hapus_employee.php?id_employee=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMPLOYEE_ID = "id_employee";
    public static final String KEY_EMPLOYEE_NAMA = "name";
    public static final String KEY_EMPLOYEE_POSISI = "desg"; //desg itu variabel untuk posisi
    public static final String KEY_EMPLOYEE_GAJIH = "salary"; //salary itu variabel untuk gajih

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID_EMPLOYEE = "id_employee";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJIH = "salary";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMPLOYEE_ID = "employee_id";

}
