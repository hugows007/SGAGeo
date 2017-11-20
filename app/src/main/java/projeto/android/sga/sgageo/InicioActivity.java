package projeto.android.sga.sgageo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import model.GPS;
import model.GPSTracker;
import model.SharedPrefManager;
import model.URLs;
import model.Usuario;
import model.VolleySingleton;

public class InicioActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    private TextView bemVindo;
    GPSTracker gps;
    GPS geo = new GPS();
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting the current user
        user = SharedPrefManager.getInstance(this).getUser();

        bemVindo = (TextView) findViewById(R.id.textViewBemVindo);
        bemVindo.setText("Bem vindo " + user.getUsuario());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geoPost();
                handler.postDelayed(this, 10000); //60000 = 1m
            }
        }, 10000);

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }

    private void geoPost() {
        geo = getLocalizacao();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GEO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.contains("ok")) {
                                Toast.makeText(getApplicationContext(), "Atualizado.", Toast.LENGTH_LONG).show();
                            }else if(response.contains("errogravacao")) {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao gravar os dados no servidor. Informe ao suporte.", Toast.LENGTH_LONG).show();
                            }else if(response.contains("dadosinvalidos")) {
                                Toast.makeText(getApplicationContext(), "Dados inválidos.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Algo deu errado. Informe ao suporte.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Algo deu errado. Informe ao suporte.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro no servidor. Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idusr", String.valueOf(user.getId()));
                params.put("latitude", String.valueOf(geo.getLatitude()));
                params.put("longitude", String.valueOf(geo.getLongitude()));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public GPS getLocalizacao() {
        gps = new GPSTracker(InicioActivity.this);

        if (gps.canGetLocation()) {
            geo.setLatitude(gps.getLatitude());
            geo.setLongitude(gps.getLongitude());
            gps.stopUsingGPS();
            return geo;
        } else {
            gps.stopUsingGPS();
            gps.showSettingsAlert();
            return geo;
        }
    }
}