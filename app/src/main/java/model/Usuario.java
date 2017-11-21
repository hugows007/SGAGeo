package model;

/**
 * Created by Hugo on 04/10/2017.
 */

public class Usuario {
    private  int idUsuario, idEmpresa;
    private String Usuario, Senha;

    public Usuario(int id, int empresa, String usuario, String senha) {
        idUsuario = id;
        idEmpresa = empresa;
        Usuario = usuario;
        Senha = senha;
    }

    public int getId() {
        return idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getSenha() {
        return Senha;
    }

}
