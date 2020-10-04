package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class NovoContatoActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private EditText telefoneEditText;
    private EditText celularEditText;
    private EditText nomeEditText;

    private Button adicionarContatoButton;

    private ContatoDao contatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_novo_contato);

        adicionarContatoButton = findViewById(R.id.button_salvar_contato);
        telefoneEditText       = findViewById(R.id.edittext_telefone);
        celularEditText        = findViewById(R.id.edittext_celular);
        nomeEditText           = findViewById(R.id.edittext_nome);

        adicionarContatoButton.setOnClickListener(this::adicionarContato);

        constraintLayout = findViewById(R.id.novo_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finalizar(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void adicionarContato(View view){

        String telefone;
        String celular;
        String nome;

        telefone = telefoneEditText.getText().toString();
        celular  = celularEditText.getText().toString();
        nome     = nomeEditText.getText().toString();

        if(nome.isEmpty() && (telefone.isEmpty() || celular.isEmpty())){
            showSnackbar(getString(R.string.erro_dados_vazios));
        }
        else {

            contatoDao = new ContatoDao(getApplicationContext());

            try {
                contatoDao.add(new Contato(nome, telefone, celular, Usuario.getUserLogado()));

                finalizar(true);
            }
            catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_contato_1));
            }
            catch (Exception e) {
                showSnackbar(getString(R.string.erro_contato_2));
            }
        }
    }

    private void finalizar(boolean resultado){

        if(resultado){

            Intent result = new Intent();

            result.putExtra(Constantes.KEY_NOME,     nomeEditText.getText().toString());
            result.putExtra(Constantes.KEY_TELEFONE, telefoneEditText.getText().toString());
            result.putExtra(Constantes.KEY_CELULAR,  celularEditText.getText().toString());

            setResult(Activity.RESULT_OK, result);
        }
        else{
            setResult(Activity.RESULT_CANCELED);
        }

        finish();
    }

    private void showSnackbar(String mensagem){

        Snackbar snackbar;

        ConstraintLayout constraintLayout = findViewById(R.id.novo_contato);

        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }


}