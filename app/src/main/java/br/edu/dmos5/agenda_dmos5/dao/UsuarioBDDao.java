package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

import br.edu.dmos5.agenda_dmos5.Constantes.Constantes;

public class UsuarioBDDao {

    public static final String CREATE_TABLE = "CREATE TABLE " + Constantes.NOME_TABELA_USUARIO + "("
        + BaseColumns._ID + " INTEGER PRIMARY KEY,"
        + Constantes.COLUNA_NOME_USUARIO + " TEXT NOT NULL,"
        + Constantes.COLUNA_LOGIN_USUARIO + " TEXT NOT NULL UNIQUE,"
        + Constantes.COLUNA_SENHA_USUARIO + " TEXT NOT NULL );";

    public static final String INSERT_USER = "INSERT INTO " + Constantes.NOME_TABELA_USUARIO + " ("
        + Constantes.COLUNA_NOME_USUARIO + ","
        + Constantes.COLUNA_LOGIN_USUARIO + ","
        + Constantes.COLUNA_SENHA_USUARIO + ") VALUES ('José Guilherme', 'jsantos', '3000397');";


}
