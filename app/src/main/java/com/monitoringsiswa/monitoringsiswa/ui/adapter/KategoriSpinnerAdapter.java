package com.monitoringsiswa.monitoringsiswa.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.databinding.ItemSpinnerBinding;
import com.monitoringsiswa.monitoringsiswa.pojo.KategoriPelanggaran;

import java.util.List;

/**
 * Created by rahardyan on 29/04/16.
 */
public class KategoriSpinnerAdapter extends BaseAdapter{
    private List<KategoriPelanggaran> kategoriPelanggaren;
    private LayoutInflater inflater;

    public KategoriSpinnerAdapter(List<KategoriPelanggaran> kategoriPelanggaren, Context context) {
        this.kategoriPelanggaren = kategoriPelanggaren;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return kategoriPelanggaren.size();
    }

    @Override
    public Object getItem(int position) {
        return kategoriPelanggaren.get(position);
    }

    @Override
    public long getItemId(int position) {
        return kategoriPelanggaren.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_spinner, null);
            holder = new ViewHolder();
            holder.spinnerValue = (TextView) convertView.findViewById(R.id.tv_value);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.spinnerValue.setText(kategoriPelanggaren.get(position).getNamaKategoriPelanggaran());
        return convertView;
    }

    static class ViewHolder{
        TextView spinnerValue;
    }
}
