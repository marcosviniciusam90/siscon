/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.util;

import java.io.Serializable;

/**
 *
 * @author Marcos Vinícius
 */
public class Parametros implements Serializable
{
    private String driver, servidor, porta , usuario , senha, base_dados ;

    public Parametros() {
        driver = "";
        servidor = "";
        porta = "";
        usuario = "";
        senha = "";
        base_dados = "";
    }

    public Parametros(String driver, String servidor, String porta, String usuario, String senha, String base_dados) {
        this.driver = driver;
        this.servidor = servidor;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.base_dados = base_dados;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBase_dados() {
        return base_dados;
    }

    public void setBase_dados(String base_dados) {
        this.base_dados = base_dados;
    }
    
    public void redefinir()
    {
        driver = "org.postgresql.Driver";
        servidor = "localhost";
        porta = "5432";
        usuario = "postgres";
        senha = "postgres123";
        base_dados = "siscon";
    }
}
