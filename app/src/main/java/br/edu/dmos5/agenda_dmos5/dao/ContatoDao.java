package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class ContatoDao {

    private Context context;

    public ContatoDao(Context context) {
        this.context = context;
    }

    public void add(Contato contato) {

        if (contato == null) throw new NullPointerException();

        SQLiteHelper dbHelper = new SQLiteHelper(this.context);

        SQLiteDatabase dbObject = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constantes.COLUNA_NOME,       contato.getNome());
        values.put(Constantes.COLUNA_TELEFONE,   contato.getTelefone());
        values.put(Constantes.COLUNA_CELULAR,    contato.getCelular());
        values.put(Constantes.COLUNA_ID_USUARIO, Usuario.getUserLogado().getId());

        long teste = dbObject.insert(Constantes.NOME_TABELA, null, values);

        Log.i("TAG Teste insert", String.valueOf(teste));


        dbObject.close();
    }

    public List<Contato> recuperateAll(){

        List<Contato> contatos;
        Contato contato;
        Cursor cursor;

        contatos = new ArrayList<Contato>();

        SQLiteHelper dbHelper = new SQLiteHelper(this.context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String colunas[] = new String[]{
            BaseColumns._ID,
            Constantes.COLUNA_NOME,
            Constantes.COLUNA_TELEFONE,
            Constantes.COLUNA_CELULAR,
            Constantes.COLUNA_ID_USUARIO,
        };

        String orderBy = Constantes.COLUNA_NOME + " ASC";
        String where = Constantes.COLUNA_ID_USUARIO + " = ?";
        String[] argumentos = {String.valueOf(Usuario.getUserLogado().getId())};

        cursor = db.query(
            Constantes.NOME_TABELA,
            colunas,
            where,
            argumentos,
            null,
            null,
            orderBy
        );

        while (cursor.moveToNext()){

            contato = new Contato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Usuario.getUserLogado()
            );

            Log.i("Lista: Nome: ", contato.getNome());
            Log.i("Lista: Telefone: ", contato.getTelefone());
            Log.i("Lista: Celular: ", contato.getCelular());
            Log.i("Lista: ID: ", Usuario.getUserLogado().toString());


            contatos.add(contato);
        }

        cursor.close();
        db.close();

        return contatos;
    }
}