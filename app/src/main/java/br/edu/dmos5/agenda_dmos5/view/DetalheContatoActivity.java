package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class DetalheContatoActivity extends AppCompatActivity {

    private Contato contato;

    private TextView telefoneTextView;
    private TextView celularTextView;
    private TextView nomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhe_contato);

        telefoneTextView = findViewById(R.id.textview_telefone);
        celularTextView  = findViewById(R.id.textview_celular);
        nomeTextView     = findViewById(R.id.textview_nome);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getArgumentos();
        exibirDados();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void exibirDados(){

        telefoneTextView.setText(contato.getTelefone());
        celularTextView.setText(contato.getCelular());
        nomeTextView.setText(contato.getNome());
    }

    private void getArgumentos(){

        Intent intent = getIntent();

        Bundle argumentos = intent.getExtras();

        if(argumentos != null){

            String telefone = argumentos.getString(Constantes.KEY_TELEFONE);
            String celular  = argumentos.getString(Constantes.KEY_CELULAR);
            String nome     = argumentos.getString(Constantes.KEY_NOME);

            contato = new Contato(nome, telefone, celular);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}