package com.facens.att4mobile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListagemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlunoAdapter alunoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listagem);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buscarAlunos();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void buscarAlunos() {
        AlunoService alunoApi = ApiClient.getClient().create(AlunoService.class);
        Call<List<Aluno>> call = alunoApi.getAluno();

        call.enqueue(new Callback<List<Aluno>>() {
            private Call<List<Aluno>> call;
            private Response<List<Aluno>> response;

            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                this.call = call;
                this.response = response;
                if (response.isSuccessful() && response.body() != null) {
                    List<Aluno> listaDeAlunos = response.body();
                    alunoAdapter = new AlunoAdapter(listaDeAlunos);
                    recyclerView.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListagemActivity.this, "Falha ao obter dados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListagemActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}