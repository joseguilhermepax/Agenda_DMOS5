package br.edu.dmos5.agenda_dmos5.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contato.db";

    private Context context;

    public static final String CREATE_TABLE =
        "CREATE TABLE " + Constantes.NOME_TABELA + "("
        + BaseColumns._ID + " INTEGER PRIMARY KEY,"
        + Constantes.COLUNA_NOME + " TEXT NOT NULL,"
        + Constantes.COLUNA_TELEFONE + " TEXT,"
        + Constantes.COLUNA_CELULAR + " TEXT NOT NULL,"
        + Constantes.COLUNA_ID_USUARIO + " INTEGER REFERENCES "+ Constantes.NOME_TABELA_USUARIO + "(_id) );";

    public static final String ALTER_TABLE_CONTATO =
        "ALTER TABLE " + Constantes.NOME_TABELA
        + " ADD " + Constantes.COLUNA_ID_USUARIO + " INTEGER REFERENCES "
        + Constantes.NOME_TABELA_USUARIO + "(_id);";

    public static final String UPDATE_CONTATO_SETAR_USUARIO =
        "UPDATE " + Constantes.NOME_TABELA + " SET " + Constantes.COLUNA_ID_USUARIO + " = 1;";

    public SQLiteHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Context getContext(){

        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(UsuarioBDDao.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 1:
                db.execSQL(UsuarioBDDao.CREATE_TABLE);
                db.execSQL(UsuarioBDDao.INSERT_USER);
                db.execSQL(ALTER_TABLE_CONTATO);
                db.execSQL(UPDATE_CONTATO_SETAR_USUARIO);
        }
    }
}