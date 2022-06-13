package com.example.contentprovidertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.contactsView);
        adapter = new ContactsAdapter(MainActivity.this,R.layout.simple_test_list,contactsList);
        listView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            readContacts();
        }
    }
    //获取手机联系人的信息
    private void readContacts(){
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor!=null){
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex((ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex((ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    contactsList.add(displayName+"\n"+number);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this,"你拒绝了请求",Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }


    //访问另一程序的数据
//    private void readContacts(){
//        Cursor cursor = null;
//        try {
//            Uri uri = Uri.parse("content://com.example.databasetest.provider/contacts");
//            cursor = getContentResolver().query(uri,null,null,null,null);
//            if (cursor!=null){
//                while(cursor.moveToNext()){
//                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(("name")));
//                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(("gender")));
//                    @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(("number")));
//                    contactsList.add(name+"\n"+gender+"\n"+number);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (cursor!=null){
//                cursor.close();
//            }
//        }
//    }
}