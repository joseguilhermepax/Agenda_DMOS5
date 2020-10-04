package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText usuarioEditText;
    private EditText senhaEditText;
    private Button   buttonSalvar;

    private UsuarioDao usuarioDao;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro_usuario);

        usuarioDao = new UsuarioDao(getApplicationContext());

        constraintLayout = findViewById(R.id.layout_cadastro);
        usuarioEditText  = findViewById(R.id.edit_usuario);
        senhaEditText    = findViewById(R.id.edit_senha);
        nomeEditText     = findViewById(R.id.edit_usuario);

        buttonSalvar = findViewById(R.id.button_salvar);
        buttonSalvar.setOnClickListener(this::adicionar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void adicionar(View view) {

        Usuario usuario = checarDados();

        if (usuario != null) {
            usuarioDao.add(usuario);
            finish();
        }
    }

    private Usuario checarDados() {

        String nome  = nomeEditText.getText().toString();
        String login = usuarioEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        Usuario usuario = usuarioDao.find(login);

        if (usuario != null) {
            mensagem("Este usuário já está sendo utilizado");
            return null;
        }
        else if (senha.length() < 0 ) {
            mensagem("Insira uma senha válida");
            return null;
        }

        return new Usuario(nome, login, senha);
    }

    private void mensagem(String mensagem) {

        Snackbar snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
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