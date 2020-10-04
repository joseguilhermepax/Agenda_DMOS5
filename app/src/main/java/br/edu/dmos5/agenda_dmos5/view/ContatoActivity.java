package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContatoActivity extends AppCompatActivity {

    private ListView ListViewContatos;
    private List<Contato> contatos;
    private ContatoDao contatoDao;
    private ArrayAdapter<Contato> contatoAdapter;
    private FloatingActionButton addContatoButton;



    protected static final int REQUEST_CODE_NOVO_CONTATO = 98;
    protected static final int DETALHE_CONTATO           = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contato);

        addContatoButton = findViewById(R.id.adicionar_contato);
        addContatoButton.setOnClickListener(this::adicionarContato);

        ListViewContatos = findViewById(R.id.lista_contatos);
        contatoDao = new ContatoDao(this);
        contatos = contatoDao.recuperateAll();

        contatoAdapter = new ItemContatoAdapter(this, contatos);
        ListViewContatos.setAdapter(contatoAdapter);

        ListViewContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle argumentos = new Bundle();

                argumentos.putString(Constantes.KEY_NOME,     contatos.get(i).getNome());
                argumentos.putString(Constantes.KEY_TELEFONE, contatos.get(i).getTelefone());
                argumentos.putString(Constantes.KEY_CELULAR,  contatos.get(i).getCelular());

                Intent intent = new Intent(getApplicationContext(), DetalheContatoActivity.class);
                intent.putExtras(argumentos);

                startActivityForResult(intent, DETALHE_CONTATO);
            }
        });
    }

    public void adicionarContato(View view) {

        Intent intent = new Intent(this, NovoContatoActivity.class);

        startActivityForResult(intent, REQUEST_CODE_NOVO_CONTATO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_NOVO_CONTATO:
                if (resultCode == RESULT_OK) {
                    atualizaLista(contatoDao.recuperateAll());
                }
                break;
        }
    }

    private void atualizaLista(List<Contato> listaContatos){

        contatoAdapter.clear();
        contatoAdapter.addAll(listaContatos);
        contatoAdapter.notifyDataSetChanged();
    }
}