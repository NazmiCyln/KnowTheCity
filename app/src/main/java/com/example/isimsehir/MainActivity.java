package com.example.isimsehir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void anaSayfaBtn(View v){

        switch (v.getId()){
            case R.id.btnNormalOyun:
                aktiviteGecis("NormalOyun");
                break;

            case R.id.btnSureliOyun:
                aktiviteGecis("SureliOyun");
                break;

            case R.id.btnCikis:
                cikisYap();
                break;
        }
    }

    private void aktiviteGecis(String aktiviteIsmi){
        if (aktiviteIsmi.equals("NormalOyun"))
            intent = new Intent(this, NormalOyunActivity.class);
        else
            intent = new Intent(this, SureliOyunActivity.class);

        startActivity(intent);
    }

    //Ana ekranda geri tuşuna basınca uygulamanın kapanması
    private void cikisYap(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        cikisYap();
    }
}