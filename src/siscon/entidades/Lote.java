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

/**
 *
 * @author Marcos Vinícius
 */
public class Lote {
    private int cod, numero, quadra;
    private String descricao, residencia;
    private Pessoa pessoa;

    public Lote() {
        cod = 0;
        numero = 0;
        quadra = 0;
        descricao = "";
        residencia = "";
        pessoa = null;
    }

    public Lote(int numero, int quadra, String descricao, String residencia, Pessoa pessoa) {
        this.numero = numero;
        this.quadra = quadra;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.residencia = residencia;
    }

    public Lote(int cod, int numero, int quadra, String descricao, String residencia, Pessoa pessoa) {
        this.cod = cod;
        this.numero = numero;
        this.quadra = quadra;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.residencia = residencia;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getQuadra() {
        return quadra;
    }

    public void setQuadra(int quadra) {
        this.quadra = quadra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }
    

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    public ArrayList <Lote> getLotes(String filtro)
    {
        ArrayList <Lote> lista = new ArrayList();
        String sql = "select * from lote";
        Pessoa p = new Pessoa();
        
        //STATUS, somente os ATIVADOS
        if(!filtro.equals(""))
           filtro += " and lot_status = 'A'";
        else
           filtro = "lot_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " WHERE "+filtro;
        
        sql += " order by lot_numero, lot_quadra";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Lote(rs.getInt("lot_cod"), rs.getInt("lot_numero"), rs.getInt("lot_quadra"), rs.getString("lot_descricao"), rs.getString("lot_residencia"), p.getPessoa(rs.getInt("pes_cod"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean salvar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("lot_numero", "lot_quadra", "lot_descricao", "lot_residencia", "pes_cod", "lot_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(getNumero()+"", getQuadra()+"", getDescricao(), getResidencia(), getPessoa().getCod()+"", "A"));
        //if(Banco.con.manipular(sql));
        if(Banco.con.cmdInsert("lote", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("lote", "lot_cod", ""));
            return true;
        }
        
        return false;    
    }   
    
    public boolean alterar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("lot_cod","lot_numero", "lot_quadra", "lot_descricao", "lot_residencia", "pes_cod"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getNumero()+"", getQuadra()+"", getDescricao(), getResidencia(), getPessoa().getCod()+""));
        //if(Banco.con.manipular(sql));
        if(Banco.con.cmdUpdate("lote", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }  
    
    public boolean excluir() 
    {     
        return Banco.con.cmdDelete("lote", "lot_cod", cod);   
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("lot_cod","lot_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("lote", campos, valores);       
    }
}
