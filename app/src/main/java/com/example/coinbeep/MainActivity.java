package com.example.coinbeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permite conexão com a Internet na Thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void carregarValores(String base_param, String quote_param) throws IOException, JSONException {
        Moeda moeda =  AtualizacaoValores.atualizarValor(base_param,quote_param,"");
        TextView moedaValor = (TextView) findViewById(R.id.moedaValor);


        Float moedaFloat = Float.parseFloat(moeda.getRate());
        String rate = String.format("%.2f", moedaFloat);
        moedaValor.setText(moeda.getRate());
    }

    //ALTERAÇÃO DE MOEDA NOME PARA MOEDA ISO CODE
    private String getCountryByCode(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.moedas_name)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.moedas_iso_code_array)[i];
    }

    public void btAtualizarOnClickView(View v) {

        // PEGAR O VALOR SELECIONADO NO SPINNER
        Spinner criptomoeda = (Spinner) findViewById(R.id.spCriptomoeda);
        Spinner moeda = (Spinner) findViewById(R.id.spMoeda);

        String base = (String) criptomoeda.getSelectedItem();
        String quote = (String) moeda.getSelectedItem();

        quote = getCountryByCode(quote);

        Toast.makeText(this, quote, Toast.LENGTH_LONG).show();
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
            } else {
                carregarValores(base, quote);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // PEGAR O VALOR SELECIONADO NO SPINNER
                    Spinner criptomoeda = (Spinner) findViewById(R.id.spCriptomoeda);
                    Spinner moeda = (Spinner) findViewById(R.id.spMoeda);

                    String base = (String) criptomoeda.getSelectedItem();
                    String quote = (String) moeda.getSelectedItem();

                    quote = getCountryByCode(quote);

                    try {
                        carregarValores(base, quote);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
