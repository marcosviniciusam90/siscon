/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import siscon.util.Banco;

/**
 *
 * @author Marcos Vinícius
 */
public class CartaoCredito {
    private int cod;
    private String numero, banco, titular, bandeira;

    public CartaoCredito() {
        cod = 0;
        numero =  banco = titular = bandeira = "";
    }

    public CartaoCredito(int cod, String numero, String banco, String titular, String bandeira) {
        this.cod = cod;
        this.numero = numero;
        this.banco = banco;
        this.titular = titular;
        this.bandeira = bandeira;
    }

    public CartaoCredito(String numero, String banco, String titular, String bandeira) {
        this.numero = numero;
        this.banco = banco;
        this.titular = titular;
        this.bandeira = bandeira;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }
    
    public String toString()
    {
        return banco+" ("+numero+")";
    }
    
    public CartaoCredito getCartaoCredito(int cod)
    {
        CartaoCredito c = null;
        
        String sql = "select * from cartaocredito";
        sql += " where car_cod = "+cod;        
        sql += " ORDER BY car_banco, car_numero";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                c = new CartaoCredito(rs.getInt("car_cod"), rs.getString("car_numero"), rs.getString("car_banco"), rs.getString("car_titular"), rs.getString("car_bandeira"));
                
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return c;
    }  
    
    public ArrayList <CartaoCredito> getCartoesCredito(String filtro)
    {
        ArrayList <CartaoCredito> lista = new ArrayList();
        
        String sql = "select * from cartaocredito WHERE car_status = 'A'";
        if(!filtro.equals(""))
            sql += " and "+filtro;       
        sql += " ORDER BY car_banco, car_numero";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                lista.add(new CartaoCredito(rs.getInt("car_cod"), rs.getString("car_numero"), rs.getString("car_banco"), rs.getString("car_titular"), rs.getString("car_bandeira")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    } 
    
    
    
    
    
}
