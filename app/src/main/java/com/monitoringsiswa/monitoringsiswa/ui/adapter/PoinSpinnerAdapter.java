package com.monitoringsiswa.monitoringsiswa.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.pojo.KategoriPelanggaran;
import com.monitoringsiswa.monitoringsiswa.pojo.PoinPelanggaran;

import java.util.List;

/**
 * Created by rahardyan on 29/04/16.
 */
public class PoinSpinnerAdapter extends BaseAdapter{
    private List<PoinPelanggaran> poinPelanggaren;
    private LayoutInflater inflater;

    public PoinSpinnerAdapter(List<PoinPelanggaran> poinPelanggaren, Context context) {
        this.poinPelanggaren = poinPelanggaren;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return poinPelanggaren.size();
    }

    @Override
    public Object getItem(int position) {
        return poinPelanggaren.get(position);
    }

    @Override
    public long getItemId(int position) {
        return poinPelanggaren.get(position).getId();
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

        holder.spinnerValue.setText(poinPelanggaren.get(position).getNamaPoinPelanggaran());
        return convertView;
    }

    static class ViewHolder{
        TextView spinnerValue;
    }
}
