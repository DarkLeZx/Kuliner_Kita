package com.example.kuliner_kita.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuliner_kita.API.APIRequestData;
import com.example.kuliner_kita.API.RetroServer;
import com.example.kuliner_kita.Activity.MainActivity;
import com.example.kuliner_kita.Activity.UbahActivity;
import com.example.kuliner_kita.Model.ModelKulinar;
import com.example.kuliner_kita.Model.ModelResponse;
import com.example.kuliner_kita.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKulinar extends RecyclerView.Adapter<AdapterKulinar.VHKulinar>{
    private Context ctx;
    private List<ModelKulinar> listKulinar;

    public AdapterKulinar(Context ctx, List<ModelKulinar> listKulinar) {
        this.ctx = ctx;
        this.listKulinar = listKulinar;
    }

    @NonNull
    @Override
    public VHKulinar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kulinar,parent,false);
        return new VHKulinar(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHKulinar holder, int position) {
        ModelKulinar MK = listKulinar.get(position);
        holder.tvId.setText(MK.getId());
        holder.tvNama.setText(MK.getNama());
        holder.tvAsal.setText(MK.getAsal());
        holder.tvDeskripsiSingkat.setText(MK.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listKulinar.size();
    }

    public class VHKulinar extends RecyclerView.ViewHolder{
    TextView tvId, tvNama, tvAsal, tvDeskripsiSingkat;
        public VHKulinar(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama =itemView.findViewById(R.id.tv_nama);
            tvAsal = itemView.findViewById(R.id.tv_asal);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_deskripsi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian!!");
                    pesan.setMessage("Operasi yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusKulinar(tvId.getText().toString());
                            dialog.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pindah = new Intent(ctx, UbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xAsal", tvAsal.getText().toString());
                            pindah.putExtra("xDeskripsiSingkat", tvDeskripsiSingkat.getText().toString());
                            ctx.startActivity(pindah);

                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }
        private void hapusKulinar(String idKulinar){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ARD.ardDelete(idKulinar);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode: " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retrieveKulinar();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
