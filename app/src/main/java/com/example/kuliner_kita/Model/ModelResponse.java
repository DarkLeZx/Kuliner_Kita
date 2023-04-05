package com.example.kuliner_kita.Model;

import java.util.List;

public class ModelResponse {
    private  String kode, pesan;
    private List<ModelKulinar> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelKulinar> getData() {
        return data;
    }
}
