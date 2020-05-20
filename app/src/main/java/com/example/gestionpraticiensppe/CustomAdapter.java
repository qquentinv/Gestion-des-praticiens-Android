package com.example.gestionpraticiensppe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Praticien> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomAdapter(Context aContext, ArrayList<Praticien>listData) {
        this.context= aContext;
        this.listData= listData;
        layoutInflater= LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.details_visiteur, null);
            holder = new ViewHolder();
            holder.matriculeView= (TextView) convertView.findViewById(R.id.textViewMatricule);
            holder.NameView= (TextView) convertView.findViewById(R.id.textViewNomVisiteur);
            holder.PrenomView= (TextView) convertView.findViewById(R.id.textViewPrenomVisiteur);
            holder.AdresseView= (TextView) convertView.findViewById(R.id.textViewAdresse);
            holder.VilleView= (TextView) convertView.findViewById(R.id.textViewVille);
            holder.CpView= (TextView) convertView.findViewById(R.id.textViewCp);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        Praticien pra;
        pra = this.listData.get(position);
        holder.matriculeView.setText(pra.getMatriculePra());
        holder.NameView.setText(pra.getNomPra());
        holder.PrenomView.setText(pra.getPrenomPra());
        holder.AdresseView.setText(pra.getAdressePra());
        holder.VilleView.setText(pra.getVillePra());
        holder.CpView.setText(pra.getCpPra());
        return convertView;
    }
    // classe nestée dans la classe CustomAdapter
    static class ViewHolder {
        TextView matriculeView;
        TextView NameView;
        TextView PrenomView;
        TextView AdresseView;
        TextView VilleView;
        TextView CpView;
    }

}
