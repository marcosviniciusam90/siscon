/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class AcertoCaixa {
    
    private int cod;
    private String valor, tipo, descricao;

    public AcertoCaixa() {
        cod = 0;
    }

    public AcertoCaixa(int cod, float valor, String tipo, String descricao) {
        this.cod = cod;
        this.valor = Funcoes.ValorMonetario(valor);
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public AcertoCaixa(float valor, String tipo, String descricao) {
        this.valor = Funcoes.ValorMonetario(valor);
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = Funcoes.ValorMonetario(valor);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public AcertoCaixa getAcerto(int cod)
    {
        AcertoCaixa p = null;
        String sql = "select * from acertocaixa";
        sql += " where ac_cod = "+cod;        
        sql += " ORDER BY ac_tipo";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                p = new AcertoCaixa(rs.getInt("ac_cod"), rs.getFloat("ac_valor"), rs.getString("ac_tipo"), rs.getString("ac_descricao"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return p;
    }
    
    public boolean inserir()
    {
        List <String> campos = new ArrayList<>(Arrays.asList("ac_valor", "ac_tipo", "ac_descricao"));
        List <String> valores = new ArrayList<>(Arrays.asList(valor.replace(",", "."), tipo, descricao));
        if(Banco.con.cmdInsert("acertocaixa", campos, valores))
        {
            setCod(Banco.con.getMaxPK("acertocaixa", "ac_cod", ""));
            return true;
        }
        return false;
        
    }
    
    
    
    
    
    
    
}
