
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;


public class CategoriaEvento {
    private int cod;
    private String descricao;

    public CategoriaEvento() {
        cod = 0;
        descricao = "";
    }

    public CategoriaEvento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public CategoriaEvento(String descricao) {
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
    
    public CategoriaEvento getCategoria(int cod) 
    {    
        String sql = "select * from categoriaevento"
                +    " where cat_cod = $1";
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);
        CategoriaEvento cat = null;
        
        try
        {
            if(rs.next())
            {
                cat = new CategoriaEvento(rs.getInt("cat_cod"), rs.getString("cat_descricao"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cat;
    }
    
    public CategoriaEvento getCategoria(String categoria) 
    {    
        String sql = "select * from categoriaevento"
                +    " where cat_descricao LIKE '$1'";
        sql = sql.replace("$1", categoria + "");
        ResultSet rs = Banco.con.consultar(sql);
        CategoriaEvento cat = null;
        
        try
        {
            if(rs.next())
            {
                cat = new CategoriaEvento(rs.getInt("cat_cod"), rs.getString("cat_descricao"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cat;
    }
    
    public ArrayList <CategoriaEvento> getCategorias(String filtro)
    {
        ArrayList <CategoriaEvento> lista = new ArrayList();
        String sql = "select * from categoriaevento WHERE cat_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " AND "+filtro;
        
        sql += " order by cat_descricao";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new CategoriaEvento(rs.getInt("cat_cod"), rs.getString("cat_descricao")));
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
        List <String> valores = new ArrayList<>(Arrays.asList(getDescricao(), "A"));
        
        if(Banco.con.cmdInsert("categoriaevento", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() //alterar a pessoa em Banco
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("cat_cod","cat_descricao"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getDescricao()));
        
        if(Banco.con.cmdUpdate("categoriaevento", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean excluir() {
        return Banco.con.cmdDelete("categoriaevento", "cat_cod", cod);  
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cat_cod","cat_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("categoriaevento", campos, valores);
    }
    
    
}
