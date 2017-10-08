package model;

/**
 * Created by Hugo on 04/10/2017.
 */

public class Usuario {
    private String Usuario, Senha;

    public Usuario(String usuario, String senha) {
        Usuario = usuario;
        Senha = senha;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getSenha() {
        return Senha;
    }
}
