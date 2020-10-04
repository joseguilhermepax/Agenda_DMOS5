package br.edu.dmos5.agenda_dmos5.model;

public class Usuario {

    private Integer id;
    private String nome, login, senha;

    private static Usuario usuario;

    public static synchronized Usuario login(String nome, String login, String senha, Integer id) {

        if (usuario == null) {
            usuario = new Usuario(nome, login, senha, id);
        }

        return usuario;
    }

    public static Usuario getUserLogado(){

        return usuario;
    }

    public static void logout() {

        usuario = null;
    }

    private Usuario(String nome, String login, String senha, Integer id){

        this.id    = id;
        this.nome  = nome;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(String nome, String login, String senha) {

        this.nome  = nome;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    @Override
    public String toString() {

        return nome;
    }
}