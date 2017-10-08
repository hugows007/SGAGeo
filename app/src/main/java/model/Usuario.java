package model;

/**
 * Created by Hugo on 04/10/2017.
 */

public class Usuario {
    private  int idUsuario;
    private String Usuario, Senha;

    public Usuario(int id, String usuario, String senha) {
        idUsuario = id;
        Usuario = usuario;
        Senha = senha;
    }

    public int getId() {
        return idUsuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getSenha() {
        return Senha;
    }
}
