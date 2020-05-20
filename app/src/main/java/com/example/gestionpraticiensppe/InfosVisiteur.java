package com.example.gestionpraticiensppe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class InfosVisiteur extends AppCompatActivity {

    public TextView textView;
    ListView listPraticien;

    private static final String TAG_matricule= "PRA_NUM";
    private static final String TAG_nom= "PRA_NOM";
    private static final String TAG_prenom= "PRA_PRENOM";
    private static final String TAG_adresse= "PRA_ADRESSE";
    private static final String TAG_ville= "PRA_VILLE";
    private static final String TAG_cp= "PRA_CP";

    ArrayList<Praticien> oslist= new ArrayList<Praticien>();

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos_visiteur);
        textView = (TextView) findViewById((R.id.textViewDep));
        new JSONAsynchrone().execute();
    }



    private class JSONAsynchrone extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.1.15/gestionpraticiensPPE/TousPraticiensSwissJson.php").build();
            Response response = null;
            try{
                response = client.newCall(request).execute();
                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {

            Integer depTransmis = getIntent().getIntExtra("DEPARTEMENT",0);

            if (depTransmis < 10){
                textView.setText("Le département choisi est : 0" + depTransmis);
            }
            else{
                textView.setText("Le département choisi est : " + depTransmis);
            }

            JSONObject c = null;
            try{
                // Getting JSON Arrayfrom URL
                JSONArray android = new JSONArray(json);
                for(int i = 0; i <android.length(); i++){
                    c = android.getJSONObject(i);
                    //VIS_MATRICULE, VIS_NOM, VIS_PRENOM, VIS_ADRESSE, VIS_VILLE, VIS_CP
                    String id = c.getString(TAG_matricule);
                    String nom = c.getString(TAG_nom);
                    String prenom = c.getString(TAG_prenom);
                    String  adresse = c.getString(TAG_adresse);
                    String ville = c.getString(TAG_ville);
                    String cp = c.getString(TAG_cp);


                    Integer LeDepartement = 0;
                    if(cp.length() == 5 ){
                        LeDepartement= Integer.parseInt(cp.substring(0,2));
                    }
                    else {
                        if(cp.length() == 4){
                            String strDep = cp.substring(0,1);
                            LeDepartement= Integer.parseInt(strDep);
                            cp = "0" + cp;
                        }
                        else {}
                    }

                    if(LeDepartement == depTransmis){
                        Praticien pra = new Praticien(id,nom,prenom,adresse,ville,cp);
                        oslist.add(pra);
                    }
                    else {}

                }	// fin du for
                listPraticien=(ListView)findViewById(R.id.listPra);

                CustomAdapter monAdapter = new CustomAdapter( getBaseContext(), oslist);

                listPraticien.setAdapter(monAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("e",e.toString());
            }

        }
    }
}
