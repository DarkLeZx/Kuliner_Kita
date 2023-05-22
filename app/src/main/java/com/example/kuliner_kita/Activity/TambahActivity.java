package com.example.kuliner_kita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuliner_kita.API.APIRequestData;
import com.example.kuliner_kita.API.RetroServer;
import com.example.kuliner_kita.Model.ModelResponse;
import com.example.kuliner_kita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private String nama, asal, deskripsiSingkat;
    private EditText etNama, etAsal, etDeskripsiSingkat;
    private Button btnSimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama =findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsiSingkat);

        btnSimpan =findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskripsiSingkat = etDeskripsiSingkat.getText().toString();

                if (nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong");
                } else if (asal.trim().isEmpty()) {
                    etAsal.setError("Asal Tidak Boleh Kosong");
                } else if (deskripsiSingkat.trim().isEmpty()) {
                    etDeskripsiSingkat.setError("Deskripsi Singkat Tidak Boleh Kosong");
                }else {
                    tambahKulinar();
                }
            }
        });
    }

    private void tambahKulinar(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(nama, asal, deskripsiSingkat); //Ambil dari Main Activity, Auto Import

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
