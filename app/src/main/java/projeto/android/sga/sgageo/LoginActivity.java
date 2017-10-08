package projeto.android.sga.sgageo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.SharedPrefManager;
import model.URLs;
import model.Usuario;
import model.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, InicioActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.GONE);
                userLogin();
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String login = editTextUsername.getText().toString();
        final String senha = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(login)) {
            editTextUsername.setError("Insira seu login");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            editTextPassword.setError("Insira sua senha");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.contains("usuarioinvalido")){
                                Toast.makeText(getApplicationContext(), "Usuário ou senha inválido.", Toast.LENGTH_LONG).show();

                            }else if(response.contains("usuarionaotecnico")){
                                Toast.makeText(getApplicationContext(), "O usuário não é um técnico.", Toast.LENGTH_LONG).show();

                            }else if(response.contains("erro")){
                                Toast.makeText(getApplicationContext(), "Algo deu errado. Tente novamente.", Toast.LENGTH_LONG).show();

                            }else if(!response.contains("usuarioinvalido") || !response.contains("usuarionaotecnico")){
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("usuario");

                                //creating a new user object
                                Usuario user = new Usuario(
                                        userJson.getInt("Id"),
                                        userJson.getString("Login"),
                                        userJson.getString("Senha")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), InicioActivity.class));
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Algo deu errado. Tente novamente.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro no servidor. Tente novamente.", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("senha", senha);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
