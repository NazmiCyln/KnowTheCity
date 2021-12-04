package com.example.isimsehir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class NormalOyunActivity extends AppCompatActivity {

    private TextView txtIlBilgi, txtIl, txtToplamPuan, txtSoruSayi;
    private EditText editTxtTahmin;

    private String[] iller = {"adana", "adıyaman", "afyonkarahisar", "ağrı", "aksaray", "amasya", "ankara", "antalya", "ardahan",
            "artvin", "aydın", "balıkesir", "bartın", "batman", "bayburt", "bilecik", "bingöl", "bitlis",
            "bolu", "burdur", "bursa", "çanakkale", "çankırı", "çorum", "denizli", "diyarbakır", "düzce",
            "elazığ", "edirne", "erzincan", "erzurum", "eskişehir", "gaziantep", "giresun", "gümüşhane", "hakkari",
            "hatay", "ığdır", "ısparta", "istanbul", "izmir", "kahramanmaraş", "karabük", "karaman", "kars",
            "kastamonu", "kayseri", "kırıkkale", "kırklareli", "kırşehir", "kilis", "kocaeli", "konya", "kütahya",
            "malatya", "manisa", "mardin", "mersin", "muğla", "muş", "nevşehir", "niğde", "ordu",
            "osmaniye", "rize", "sakarya", "samsun", "siirt", "sinop", "sivas", "şanlıurfa", "şırnak",
            "tekirdağ", "tokat", "trabzon", "tunceli", "uşak", "van", "yalova", "yozgat", "zonguldak"};


    private Random rndIl, rndHarf;
    private int rndIlNumber, rndNumberHarf, baslangicHarfSayi, toplamSoruSayi=0, SORU=13, soruSayisi=1;
    private String gelenIl, ilBoyut, editTxtGelenTahmin;
    private ArrayList<Character> ilHarfleri;
    private float maxPuan=10.0f, azalacakPuan, toplamPuan=0.0f, bolumToplamPuan=0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_oyun);

        txtIlBilgi=findViewById(R.id.txtBilgiN);
        txtIl=findViewById(R.id.txtIlN);
        editTxtTahmin=findViewById(R.id.edtTahminN);
        txtToplamPuan=findViewById(R.id.txtToplamPuanN);
        txtSoruSayi=findViewById(R.id.txtSoruSayiN);

        rndHarf = new Random();
        randomDegerBelirle();

    }

    private void randomDegerBelirle(){
        ilBoyut = "";

        rndIl = new Random();
        //0 ile 80 arasında değer döndürüp rndIlNumberin içerisine atacak
        rndIlNumber = rndIl.nextInt(iller.length);
        gelenIl = iller[rndIlNumber];
        System.out.println(gelenIl);

        //Şehrin kaç harfli olduğunu ekrana yazdırıyoruz.
        txtIlBilgi.setText(gelenIl.length()+ " Harfli İl");

        //Başlanıçta ilin uzunluğuna göre başlangıç harfi yazdırılıyor
        if (gelenIl.length() <=6)
            baslangicHarfSayi = 1;

        else if (gelenIl.length()>=7 && gelenIl.length()<10)
            baslangicHarfSayi=2;

        else if (gelenIl.length()>=10)
            baslangicHarfSayi=3;


        //Alt çizgilerin kaç tane olacağının ayarlanması
        for (int i = 0; i<gelenIl.length(); i++){
            if (i<gelenIl.length()-1){
                ilBoyut += "_ ";
            }
            else
                ilBoyut += "_";
        }
        txtIl.setText(ilBoyut);

        //ilHarfleri adında yeni array oluşturup gelen ildeki harfleri ilHarfleri arrayine atıyoruz
        ilHarfleri = new ArrayList<>();
        for (char c : gelenIl.toCharArray())
            ilHarfleri.add(c);

        //BaslangicHarfSayısına göre random Harf Al
        for (int c = 0; c<baslangicHarfSayi; c++)
            randomHarfAl();

        //Kaç puan azaltılacağını belirliyoruz
        azalacakPuan = maxPuan/ilHarfleri.size();
        toplamPuan = maxPuan;

    }

    private void randomHarfAl() {
        rndNumberHarf = rndHarf.nextInt(ilHarfleri.size());

        //Alt çizgilerin arasındaki boşlukları silerek sadece alt çizgileri alarak döndür
        String[] txtHarfler = txtIl.getText().toString().split(" ");
        //gelenIl in harflerini parçalayıp index index gezilebilmesini sağlıyoruz
        char[] gelenIlHarfler = gelenIl.toCharArray();

        for (int i = 0; i<gelenIl.length(); i++){
            //txtHarflerin i si '_' eşitse harfi oraya ekle - üst üste birden fazla harf eklenmesini engellemek için
            if (txtHarfler[i].equals("_") && gelenIlHarfler[i] == ilHarfleri.get(rndNumberHarf)){
                txtHarfler[i] = String.valueOf(ilHarfleri.get(rndNumberHarf));
                ilBoyut = "";

                for (int j = 0; j<gelenIl.length(); j++){
                    if (j == i)
                        ilBoyut += txtHarfler[j]+ " ";
                    else if (j<gelenIl.length() -1)
                        ilBoyut += txtHarfler[j] + " ";
                    else
                        ilBoyut += txtHarfler[j];
                }
                break;
            }
        }

        txtIl.setText(ilBoyut);

        //Daha önce alınan harfi ilHarfleri arrayinden siliyoruz ki birdaha gelmesin
        ilHarfleri.remove(rndNumberHarf);

    }


    public void btnTahminEtN(View v){
        editTxtGelenTahmin = editTxtTahmin.getText().toString();

        //Butona boş bastıysa uyarı ver değer girip doğruysa randomDegerBelirle'ye ilerle yanlış tahminse uyarı ver
        if (!TextUtils.isEmpty(editTxtGelenTahmin)){
            if (editTxtGelenTahmin.equals(gelenIl)){

                //Bölümün toplam puanının hesaplanması
                bolumToplamPuan+=toplamPuan;



                txtToplamPuan.setText("Toplam Puan: "+bolumToplamPuan+"/100");

                if (toplamSoruSayi<=10) {

                    txtSoruSayi.setText(soruSayisi+1 +" / "+ SORU);
                    soruSayisi++;

                    editTxtTahmin.setText("");
                    randomDegerBelirle();


                    toplamSoruSayi++;
                }
                //Alert Dialog gelecek 100 üzerinden toplam puan yazdırılacak
                else {
                    txtSoruSayi.setText("1 / 13");
                    txtToplamPuan.setText("Toplam Puan: 0/100");
                    toplamPuan=0;
                    bolumToplamPuan=0;
                    editTxtTahmin.setText("");
                    dialog();
                    soruSayisi=0;
                    toplamSoruSayi=0;
                }

            }
            else
                Toast.makeText(getApplicationContext(), "Yanlış Tahmin!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Bu Kısmı Boş Bırakamazsınız!", Toast.LENGTH_SHORT).show();

    }

    private void dialog() {

        new AlertDialog.Builder(NormalOyunActivity.this).setTitle("Finished!")
                .setMessage("Puan: "+ bolumToplamPuan)
                .setPositiveButton("Test Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        randomDegerBelirle();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        dialogInterface.cancel();
                    }
                }).show();

    }


    public void btnHarfAlN(View v){

        //Alınacak il harfleri 0 dan büyükse yazdır değilse Toast mesajı göster
        if (ilHarfleri.size()>0){
            randomHarfAl();
            toplamPuan-=azalacakPuan;

        }
        else
            Toast.makeText(NormalOyunActivity.this, "Alacak Başka Harf Kalmadı!", Toast.LENGTH_SHORT).show();
    }

}