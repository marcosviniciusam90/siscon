
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;


public class Cor {
    private int cod;
    private String nome;

    public Cor() {
        cod = 0;
        nome = "";
    }

    public Cor(int cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public Cor(String nome) {
        this.nome = nome;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String toString()
    {
        return nome;
    }
    
    public Cor getCor(int cod) 
    {    
        String sql = "select * from cor"
                +    " where cor_cod = $1";
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);
        Cor cor = null;
        
        try
        {
            if(rs.next())
            {
                cor = new Cor(rs.getInt("cor_cod"), rs.getString("cor_nome"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cor;
    }
    
    public Cor getCor(String cor) 
    {    
        String sql = "select * from cor"
                +    " where cor_nome LIKE '$1'";
        sql = sql.replace("$1", cor + "");
        ResultSet rs = Banco.con.consultar(sql);
        Cor c = null;
        
        try
        {
            if(rs.next())
            {
                c = new Cor(rs.getInt("cor_cod"), rs.getString("cor_nome"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return c;
    }
    
    public ArrayList <Cor> getCores(String filtro)
    {
        ArrayList <Cor> lista = new ArrayList();
        String sql = "select * from cor WHERE cor_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " and "+filtro;
        
        sql += " order by cor_nome";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Cor(rs.getInt("cor_cod"), rs.getString("cor_nome")));
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
        List <String> campos = new ArrayList<>(Arrays.asList("cor_nome", "cor_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(getNome(),"A"));
        
        if(Banco.con.cmdInsert("cor", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() //alterar a pessoa em Banco
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("cor_cod","cor_nome"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getNome()));
        
        if(Banco.con.cmdUpdate("cor", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean excluir() {
        return Banco.con.cmdDelete("cor", "cor_cod", cod);  
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cor_cod","cor_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("cor", campos, valores);
    }
}
