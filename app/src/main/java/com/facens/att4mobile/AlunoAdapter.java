package com.facens.att4mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlunoAdapter  extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.textRa.setText("RA: " + aluno.getRa());
        holder.textNome.setText("Nome: " + aluno.getNome());
        holder.textEndereco.setText("Endereço: " + aluno.getLogradouro() + ", " +
                aluno.getBairro() + ", " + aluno.getCidade() + " - " + aluno.getUf());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView textRa, textNome, textEndereco;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textRa = itemView.findViewById(R.id.text_ra);
            textNome = itemView.findViewById(R.id.text_nome);
            textEndereco = itemView.findViewById(R.id.text_endereco);
        }
    }
}
