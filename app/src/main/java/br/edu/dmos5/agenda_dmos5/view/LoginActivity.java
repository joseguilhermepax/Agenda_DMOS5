package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private EditText usuarioEditText;
    private EditText senhaEditText;
    private CheckBox checkCheckBox;

    private Button loginButton;
    private Button cadastrarButton;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        usuarioDao = new UsuarioDao(getApplicationContext());

        sharedPreferences = this.getSharedPreferences(
            Constantes.KEY_PREFERENCE
            , MODE_PRIVATE
        );

        editor = sharedPreferences.edit();

        constraintLayout = findViewById(R.id.layout_login);
        usuarioEditText  = findViewById(R.id.edit_usuario);
        loginButton      = findViewById(R.id.button_login);
        senhaEditText    = findViewById(R.id.edit_senha);
        checkCheckBox    = findViewById(R.id.check_lembrar_usuario);
        cadastrarButton  = findViewById(R.id.button_cadastro);

        loginButton.setOnClickListener(this::login);

        cadastrarButton.setOnClickListener(this::cadastrarUser);
    }

    private void cadastrarUser(View view) {

        Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void login(View view) {

        Usuario user = validarLogin();

        if (user != null) {

            Usuario.login(user.getNome(), user.getLogin(), user.getSenha(), user.getId());

            salvarPreferencias();
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private Usuario validarLogin() {

        String login    = usuarioEditText.getText().toString();
        String senha    = senhaEditText.getText().toString();
        Usuario usuario = usuarioDao.find(login);

        if (usuario == null) {
            showSnackbar("Usuário inválido");
            return null;
        }
        else if (!senha.equals(usuario.getSenha())) {
            showSnackbar("Senha inválida");

            return null;
        }
        return usuario;
    }

    private void salvarPreferencias() {

        String login = usuarioEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        editor.putString(Constantes.KEY_PREFERENCE, "");
        editor.commit();

        JSONObject jsonObject = new JSONObject();

        if ( usuarioDao.find(login) != null && checkCheckBox.isChecked() ) {

            try {
                jsonObject.put(Constantes.KEY_CHECK, true);
                jsonObject.put(Constantes.KEY_USER, login);
                jsonObject.put(Constantes.KEY_SENHA, senha);
            }
            catch (JSONException e) {
                showToast("Erro ao salvar as preferências");
            }

            editor.putString(Constantes.KEY_USER_JSON, jsonObject.toString());
            editor.commit();
        }
    }

    private void showSnackbar(String mensagem){

        Snackbar snackbar;

        ConstraintLayout constraintLayout = findViewById(R.id.novo_contato);

        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    private void showToast(String mensagem){

        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}