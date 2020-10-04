package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.model.Contato;

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

        values.put(Constantes.COLUNA_NOME,     contato.getNome());
        values.put(Constantes.COLUNA_TELEFONE, contato.getTelefone());
        values.put(Constantes.COLUNA_CELULAR,  contato.getCelular());

        dbObject.insert(Constantes.NOME_TABELA, null, values);

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
            Constantes.COLUNA_CELULAR
        };

        String orderBy = Constantes.COLUNA_NOME + " ASC";

        cursor = db.query(
            Constantes.NOME_TABELA,
            colunas,
            null,
            null,
            null,
            null,
            orderBy
        );

        while (cursor.moveToNext()){

            contato = new Contato(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            );

            contatos.add(contato);
        }

        cursor.close();
        db.close();

        return contatos;
    }
}