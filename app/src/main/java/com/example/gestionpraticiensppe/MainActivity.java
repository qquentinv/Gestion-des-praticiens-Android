package com.example.gestionpraticiensppe;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView listDep;

    private static final String TAG_cp = "PRA_CP";

    ArrayList<departement> deplist = new ArrayList<departement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONAsynchrone().execute();
            }

        });

    }

    private class JSONAsynchrone extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Je charge les données ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.1.15/gestionpraticiensPPE/TousPraticiensSwissJson.php").build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            pDialog.dismiss();

            JSONObject c = null;
            try {
                // Getting JSON Arrayfrom URL
                JSONArray android = new JSONArray(json);
                for (int i = 0; i < android.length(); i++) {
                    c = android.getJSONObject(i);
                    String cp = c.getString(TAG_cp);

                    Integer LeDepartement = 0;
                    if(cp.length() == 5 ){
                        LeDepartement= Integer.parseInt(cp.substring(0,2));
                    }
                    else {
                        if(cp.length() == 4){
                            String strDep = cp.substring(0,1);
                            LeDepartement= Integer.parseInt(strDep);
                        }
                        else {}
                    }
                    departement dep = new departement(LeDepartement);
                    boolean b = true;

                    for(int k=0; k<deplist.size(); k++){

                        if(deplist.get(k).getNumDep() == dep.getNumDep()){
                            b = false;
                         }
                    }

                    if(b)
                        deplist.add(dep);

                } // fin du for

                Collections.sort(deplist, departement.ComparatorNum);


                listDep = (ListView) findViewById(R.id.listDep);
                final ArrayAdapter<departement> adapter = new ArrayAdapter<departement>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, deplist);
                listDep.setAdapter(adapter);

                listDep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(MainActivity.this, "Vous avez cliqué le département " + deplist.get(+position).numDep, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent((getBaseContext()), InfosVisiteur.class);
                        intent.putExtra("DEPARTEMENT", deplist.get(+position).getNumDep());
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("e", e.toString());
            }
        }
    }
}

