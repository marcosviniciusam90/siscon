
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;


public class Marca {
    private int cod;
    private String nome;

    public Marca() {
        cod = 0;
        nome = "";
    }

    public Marca(int cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public Marca(String nome) {
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
    
    public Marca getMarca(int cod) 
    {    
        String sql = "select * from marca"
                +    " where mar_cod = $1";
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);
        Marca mar = null;
        
        try
        {
            if(rs.next())
            {
                mar = new Marca(rs.getInt("mar_cod"), rs.getString("mar_nome"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return mar;
    }
    
    public Marca getMarca(String marca) 
    {    
        String sql = "select * from marca"
                +    " where mar_nome LIKE '$1'";
        sql = sql.replace("$1", marca + "");
        ResultSet rs = Banco.con.consultar(sql);
        Marca mar = null;
        
        try
        {
            if(rs.next())
            {
                mar = new Marca(rs.getInt("mar_cod"), rs.getString("mar_nome"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return mar;
    }
    
    public ArrayList <Marca> getMarcas(String filtro)
    {
        ArrayList <Marca> lista = new ArrayList();
        String sql = "select * from marca WHERE mar_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " AND "+filtro;
        
        sql += " order by mar_nome";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Marca(rs.getInt("mar_cod"), rs.getString("mar_nome")));
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
        List <String> campos = new ArrayList<>(Arrays.asList("mar_nome", "mar_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(getNome(),"A"));
        
        if(Banco.con.cmdInsert("marca", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() //alterar a pessoa em Banco
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("mar_cod","mar_nome"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getNome()));
        
        if(Banco.con.cmdUpdate("marca", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }

    public boolean excluir() {
        return Banco.con.cmdDelete("marca", "mar_cod", cod);  
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("mar_cod","mar_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("marca", campos, valores);
    }
    
}
