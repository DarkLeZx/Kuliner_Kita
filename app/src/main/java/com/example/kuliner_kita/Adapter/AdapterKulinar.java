package com.example.kuliner_kita.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuliner_kita.Model.ModelKulinar;
import com.example.kuliner_kita.R;

import java.util.List;

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
        }
    }
}
