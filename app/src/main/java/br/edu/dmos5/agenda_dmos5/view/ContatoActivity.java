package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.model.Contato;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class ContatoActivity extends AppCompatActivity {

    private ListView ListViewContatos;
    private List<Contato> listaContatos;
    private ContatoDao contatoDao;
    private UsuarioDao usuarioDao;

    private TextView realizarLoginTextView;

    private FloatingActionButton addContatoButton;
    private FloatingActionButton loginButton;
    private FloatingActionButton logoutButton;

    private RecyclerView contatoRecyclerView;
    private ItemContatoAdapter contatoAdapter;
    private SharedPreferences shardPreferences;
    private SharedPreferences.Editor editor;

    protected static final int REQUEST_CODE_NOVO_CONTATO  = 98;
    protected static final int REQUEST_CODE_USUARIO_LOGIN = 96;
    protected static final int DETALHE_CONTATO            = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contato);

        contatoRecyclerView   = findViewById(R.id.lista_contatos);
        realizarLoginTextView = findViewById(R.id.usuario_nao_logado);
        addContatoButton      = findViewById(R.id.adicionar_contato);
        loginButton           = findViewById(R.id.button_login);
        logoutButton          = findViewById(R.id.button_logout);

        contatoRecyclerView.addItemDecoration(new ItemDecoration(this));

        addContatoButton.setOnClickListener(this::adicionarContato);
        loginButton.setOnClickListener(this::logar);
        logoutButton.setOnClickListener(this::logout);

//        ListViewContatos = findViewById(R.id.lista_contatos);

        contatoDao = new ContatoDao(this);
        usuarioDao = new UsuarioDao(this);

        shardPreferences = this.getSharedPreferences(
            Constantes.KEY_PREFERENCE
            , MODE_PRIVATE
        );

        editor = shardPreferences.edit();

        iniciar();
    }

    public void iniciar() {

        if (checkLogin()) {

            exibirContatos();
            exibirTelaContatos();
        }
        else {
            realizarLogin();
        }
    }

    private void exibirContatos() {

        listaContatos = contatoDao.recuperateAll();

        contatoAdapter = new ItemContatoAdapter(listaContatos,getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        contatoRecyclerView.setLayoutManager(layoutManager);
        contatoRecyclerView.setAdapter(contatoAdapter);

        contatoAdapter.setClickListener(this::detalhesContato);
    }

    public void adicionarContato(View view) {

        Intent intent = new Intent(this, NovoContatoActivity.class);

        startActivityForResult(intent, REQUEST_CODE_NOVO_CONTATO);
    }

    private void logar(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_USUARIO_LOGIN);
    }

    private void detalhesContato(int position) {

        Bundle argumentos = new Bundle();

        argumentos.putString(Constantes.KEY_NOME,     listaContatos.get(position).getNome());
        argumentos.putString(Constantes.KEY_TELEFONE, listaContatos.get(position).getTelefone());
        argumentos.putString(Constantes.KEY_CELULAR,  listaContatos.get(position).getCelular());

        Intent intent = new Intent(getApplicationContext(), DetalheContatoActivity.class);
        intent.putExtras(argumentos);

        startActivityForResult(intent, DETALHE_CONTATO);
    }

    private void exibirTelaContatos() {

        contatoRecyclerView.setVisibility(View.VISIBLE);
        realizarLoginTextView.setVisibility(View.INVISIBLE);
        addContatoButton.show();
        logoutButton.show();
        loginButton.hide();
    }

    private void realizarLogin() {

        contatoRecyclerView.setVisibility(View.INVISIBLE);
        realizarLoginTextView.setVisibility(View.VISIBLE);
        addContatoButton.hide();
    }

    private boolean checkLogin() {

        String userPreferenceJsonToString = shardPreferences.getString(
            Constantes.KEY_USER_JSON
            , ""
        );

        if (Usuario.getUserLogado() != null) {
            return true;
        }

        try {

            JSONObject userPreferenceJson = new JSONObject(userPreferenceJsonToString);

            String preferenceLogin  = userPreferenceJson.getString(Constantes.KEY_USER);
            String preferenceSenha  = userPreferenceJson.getString(Constantes.KEY_SENHA);
            boolean preferenceCheck = userPreferenceJson.getBoolean(Constantes.KEY_CHECK);

            if (preferenceCheck == true) {

                Usuario user = usuarioDao.find(preferenceLogin);

                Usuario.login(user.getNome(), user.getLogin(), user.getSenha(), user.getId());

                if (preferenceSenha.equals(user.getSenha()) && user != null) {
                    return true;
                }
            }

            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    private void logout(View view) {

        listaContatos = null;

        Usuario.logout();

        editor.putString(Constantes.KEY_USER_JSON, "");

        editor.commit();

        finish();

        startActivity(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_NOVO_CONTATO: {

                if (resultCode == Activity.RESULT_OK) {

                    this.listaContatos.clear();
                    this.listaContatos.addAll(listaContatos);

                    contatoRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
            }
            case REQUEST_CODE_USUARIO_LOGIN: {

                if (resultCode == RESULT_OK) {

                    Toast.makeText(getApplicationContext(), Usuario.getUserLogado().getNome(), Toast.LENGTH_SHORT).show();
                    exibirContatos();
                    exibirTelaContatos();
                }
                break;
            }
        }
    }

}