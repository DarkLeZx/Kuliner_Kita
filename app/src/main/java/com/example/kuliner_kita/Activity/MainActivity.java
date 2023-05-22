package com.example.kuliner_kita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kuliner_kita.API.APIRequestData;
import com.example.kuliner_kita.API.RetroServer;
import com.example.kuliner_kita.Adapter.AdapterKulinar;
import com.example.kuliner_kita.Model.ModelKulinar;
import com.example.kuliner_kita.Model.ModelResponse;
import com.example.kuliner_kita.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvKulinar;
    private FloatingActionButton fabTambah;
    private ProgressBar pbKulinar;
    private RecyclerView.Adapter adKulinar;
    private RecyclerView.LayoutManager lmKulinar;
    private List<ModelKulinar> listKulinar = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvKulinar =findViewById(R.id.rv_kulinar);
        fabTambah = findViewById(R.id.fab_tambah);
        pbKulinar =findViewById(R.id.pb_kulinar);

        lmKulinar = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKulinar.setLayoutManager(lmKulinar);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveKulinar();
    }

    public void retrieveKulinar (){
        pbKulinar.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
            String kode =response.body().getKode();
            String pesan =response.body().getPesan();
            listKulinar = response.body().getData();
            adKulinar =new AdapterKulinar(MainActivity.this, listKulinar);
            rvKulinar.setAdapter(adKulinar);

            pbKulinar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t)
            {Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            pbKulinar.setVisibility(View.GONE);
            }
        });

    }
}