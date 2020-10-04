package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class UsuarioDao {

    private Context context;

    public UsuarioDao(Context context) {
        this.context = context;
    }

    public void add(Usuario usuario) {

        if (usuario == null) throw new NullPointerException();

        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase dbObject = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constantes.COLUNA_NOME_USUARIO,  usuario.getNome());
        values.put(Constantes.COLUNA_LOGIN_USUARIO, usuario.getLogin());
        values.put(Constantes.COLUNA_SENHA_USUARIO, usuario.getSenha());

        dbObject.insert(Constantes.NOME_TABELA_USUARIO, null, values);
        dbObject.close();
    }

    public List<Usuario> listAll() {

        List<Usuario> usuarios = new ArrayList<>();

        SQLiteHelper dbHelper = new SQLiteHelper(this.context);

        SQLiteDatabase dbObject = dbHelper.getReadableDatabase();

        String colunas[] = new String[]{
            BaseColumns._ID,
            Constantes.COLUNA_NOME_USUARIO,
            Constantes.COLUNA_LOGIN_USUARIO,
            Constantes.COLUNA_SENHA_USUARIO
        };

        String orderBy = Constantes.COLUNA_ID_USUARIO + " ASC";

        Cursor cursor = dbObject.query(
            Constantes.NOME_TABELA,
            colunas,
            null,
            null,
            null,
            null,
            orderBy
        );

        Usuario usuario;

        while (cursor.moveToNext()) {

            usuario = new Usuario( cursor.getString(1), cursor.getString(2), cursor.getString(3));

            usuario.setId(cursor.getInt(0));

            usuarios.add(usuario);
        }

        cursor.close();

        dbObject.close();

        return usuarios;
    }

    public Usuario find(String arg) {

        Usuario usuario = null;

        SQLiteHelper dbHelper = new SQLiteHelper(this.context);

        SQLiteDatabase dbObject = dbHelper.getReadableDatabase();

        String colunas[] = new String[]{
            BaseColumns._ID,
            Constantes.COLUNA_NOME_USUARIO,
            Constantes.COLUNA_LOGIN_USUARIO,
            Constantes.COLUNA_SENHA_USUARIO
        };

        String where = Constantes.COLUNA_LOGIN_USUARIO + " = ?";

        String[] argumentos = {arg};

        Cursor cursor = dbObject.query(
            Constantes.NOME_TABELA_USUARIO,
            colunas,
            where,
            argumentos,
            null,
            null,
            null
        );

        if (cursor.moveToFirst()) {

            usuario = new Usuario( cursor.getString(1), cursor.getString(2), cursor.getString(3) );

            usuario.setId(cursor.getInt(0));
        }

        cursor.close();

        dbObject.close();

        return usuario;
    }
}