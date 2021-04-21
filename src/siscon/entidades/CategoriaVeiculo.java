
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;


public class CategoriaVeiculo {
    private int cod;
    private String descricao;

    public CategoriaVeiculo() {
        cod = 0;
        descricao = "";
    }

    public CategoriaVeiculo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public CategoriaVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString()
    {
        return descricao;
    }
    
    public CategoriaVeiculo getCategoria(int cod) 
    {    
        String sql = "select * from categoriaveiculo"
                +    " where cat_cod = $1";
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);
        CategoriaVeiculo cat = null;
        
        try
        {
            if(rs.next())
            {
                cat = new CategoriaVeiculo(rs.getInt("cat_cod"), rs.getString("cat_descricao"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cat;
    }
    
    public CategoriaVeiculo getCategoria(String categoria) 
    {    
        String sql = "select * from categoriaveiculo"
                +    " where cat_descricao LIKE '$1'";
        sql = sql.replace("$1", categoria + "");
        ResultSet rs = Banco.con.consultar(sql);
        CategoriaVeiculo cat = null;
        
        try
        {
            if(rs.next())
            {
                cat = new CategoriaVeiculo(rs.getInt("cat_cod"), rs.getString("cat_descricao"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cat;
    }
    
    public ArrayList <CategoriaVeiculo> getCategorias(String filtro)
    {
        ArrayList <CategoriaVeiculo> lista = new ArrayList();
        String sql = "select * from categoriaveiculo WHERE cat_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " AND "+filtro;
        
        sql += " order by cat_descricao";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new CategoriaVeiculo(rs.getInt("cat_cod"), rs.getString("cat_descricao")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean salvar() //alterar a pessoa em Banco
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("cat_descricao", "cat_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(getDescricao(),"A"));
        
        if(Banco.con.cmdInsert("categoriaveiculo", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() //alterar a pessoa em Banco
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("cat_cod","cat_descricao"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getDescricao()));
        
        if(Banco.con.cmdUpdate("categoriaveiculo", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean excluir() {
        return Banco.con.cmdDelete("categoriaveiculo", "cat_cod", cod);  
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cat_cod","cat_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("categoriaveiculo", campos, valores);
    }
    
    
}
