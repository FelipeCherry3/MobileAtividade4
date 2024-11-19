package com.facens.att4mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class CadastroActivity extends AppCompatActivity {

    private EditText raInput, nomeInput, cepInput, logradouroInput, complementoInput, bairroInput, cidadeInput, ufInput;
    private Button buscarCepButton, salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        raInput = findViewById(R.id.raInput);
        nomeInput = findViewById(R.id.nomeInput);
        cepInput = findViewById(R.id.cepInput);
        logradouroInput = findViewById(R.id.logradouroInput);
        complementoInput = findViewById(R.id.complementoInput);
        bairroInput = findViewById(R.id.bairroInput);
        cidadeInput = findViewById(R.id.cidadeInput);
        ufInput = findViewById(R.id.ufInput);

        buscarCepButton = findViewById(R.id.buscarCepButton);
        salvarButton = findViewById(R.id.salvarButton);

        buscarCepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEnderecoPorCep(cepInput.getText().toString());
            }
        });

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });
    }

    private void buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            logradouroInput.setText(response.getString("logradouro"));
                            complementoInput.setText(response.getString("complemento"));
                            bairroInput.setText(response.getString("bairro"));
                            cidadeInput.setText(response.getString("localidade"));
                            ufInput.setText(response.getString("uf"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CadastroActivity.this, "Erro ao processar dados do CEP", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void salvarAluno() {
        Aluno aluno = new Aluno(
                Integer.parseInt(raInput.getText().toString()),
                nomeInput.getText().toString(),
                cepInput.getText().toString(),
                logradouroInput.getText().toString(),
                complementoInput.getText().toString(),
                bairroInput.getText().toString(),
                cidadeInput.getText().toString(),
                ufInput.getText().toString()
        );

        AlunoService alunoApi = ApiClient.getClient().create(AlunoService.class);
        Call<Aluno> call = alunoApi.postAluno(aluno);

        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, retrofit2.Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    // Limpar campos ou voltar à lista, conforme desejado
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}