package com.example.coinbeep;

//ENVIARA DADOS E RETORNARA DADOS

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AtualizacaoValores {
    private static String readStream(InputStream in) {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Moeda atualizarValor(String id_base, String id_quote, String rate) throws JSONException, IOException {
        String api_key = "84B39D25-999C-48C8-AB21-7759D07D8071";
        String resposta = request("https://rest.coinapi.io/v1/exchangerate/" + id_base + "/" + id_quote + "?apikey=" + api_key);
        JSONObject obj = new JSONObject(resposta);
        String idbase = obj.getString("asset_id_base");
        String idquote = obj.getString("asset_id_quote");
        String idrate = obj.getString("rate");

        return new Moeda(idbase, idquote, idrate);
    }

    // URL TESTE
    //VALIDO
    // https://rest.coinapi.io/v1/exchangerate/BTC/USD?apikey=84B39D25-999C-48C8-AB21-7759D07D8071

    //INVALIDO
    // https://rest.coinapi.io/v1/exchangerate/BTC/AFN?apikey=84B39D25-999C-48C8-AB21-7759D07D8071

}
