package br.com.bossini.threadsjsonads3anmca;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Marcelo Victor da Silva
 * RA: 816119006
 * ADSMCA3
 */

public class MainActivity extends AppCompatActivity {

    private TextView descricaoTextView,
                    minTextView,
                    maxTextView,
                    cidadeTextView;

    private EditText city;

    private PrevisaoDAO previsaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidadeTextView =
                findViewById(R.id.cidadeTextView);
        descricaoTextView =
                findViewById(R.id.descricaoTextView);
        minTextView =
                findViewById(R.id.minTextView);
        maxTextView =
                findViewById(R.id.maxTextView);
        city =
                findViewById(R.id.city);
        previsaoDAO = new PrevisaoDAO(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Previsao> previsoes = previsaoDAO.listar();

        if(previsoes.size() > 0){
            Previsao p = previsoes.get(previsoes.size()-1);
            cidadeTextView.setText(p.getCidade());
            minTextView.setText(Double.toString(p.getMin()));
            maxTextView.setText(Double.toString(p.getMax()));;
            descricaoTextView.setText(p.getDescricao());
        }
    }

    public void obterPrevisoes (View view){

        String cidade = city.getText().toString();

        if(cidade.contains(" ")){
           cidade = cidade.replace(" ", "%20");
        }

        new ConsomeWS().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + cidade + "&appid=ef0b0973b783e0614ac87612ec04344b&units=metric&lang=pt&cnt=1");
    }

    private class ConsomeWS extends AsyncTask <String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(strings[0])
                        .build();
            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject previsao = new JSONObject(json);

                JSONObject city =
                        previsao.getJSONObject("city");

                String cidade = city.getString("name");

                JSONArray list =
                        previsao.getJSONArray("list");

                JSONObject dia =
                        list.getJSONObject(0);

                double min = dia.getJSONObject("temp").
                        getDouble("min");

                double max = dia.getJSONObject("temp").
                        getDouble("max");

                JSONArray weather = dia.getJSONArray("weather");
                JSONObject detalhes = weather.getJSONObject(0);
                String description = detalhes.getString("description");
                Previsao p =
                        new Previsao (min, max, description, cidade);
                previsaoDAO.inserir(p);

                cidadeTextView.setText(cidade);
                descricaoTextView.setText(description);
                minTextView.setText(Double.toString(min));
                maxTextView.setText(Double.toString(max));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
