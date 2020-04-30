package com.example.coinbeep;


//CLASSE QUE REPRESENTA O RETORNO DA API

public class Moeda {

    private String id_base;
    private String id_quote;
    private String rate;

    public Moeda(String base, String quote, String idrate) {
        this.id_base = base;
        this.id_quote = quote;
        this.rate = idrate;
    }

    public String getId_base() {
        return id_base;
    }

    public String getId_quote() {
        return id_quote;
    }

    public String getRate() {
        return rate;
    }

}
