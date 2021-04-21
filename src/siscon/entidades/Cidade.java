package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import siscon.util.Banco;

public class Cidade
{
    private int cod;
    private Estado estado;
    private String nome;

    public Cidade()
    {
        this.cod = 0;
        this.estado = null;
        this.nome = "";
    }

    public Cidade(int cod, String nome, Estado estado)
    {
        this.cod = cod;
        this.estado = estado;
        this.nome = nome;
    }
    
    public int getCod()
    {
        return cod;
    }

    final public void setCod(int cod)
    {
        if (cod > 0)
            this.cod = cod;
    }

    public Estado getEstado()
    {
        return estado;
    }

    final public void setEstado(Estado estado)
    {
        this.estado = estado;
    }

    public String getNome()
    {
        return nome;
    }

    final public void setNome(String nome)
    {
        this.nome = nome;
    }    
    
    public String toString()
    {
        return nome;
    }
    
    public Cidade getCidade(int cod) 
    {    
        String sql = "select * from cidade INNER JOIN estado ON cidade.est_cod = estado.est_cod"
                +    " INNER JOIN pais ON estado.pais_cod = pais.pais_cod"
                +    " where cid_cod = $1";
        Cidade cid = null;
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);

        try
        {
            if(rs.next())
            {
                cid = new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome"), 
                                 new Estado(rs.getInt("est_cod"), rs.getString("est_sgl"), rs.getString("est_nome"), 
                                            new Pais(rs.getInt("pais_cod"), rs.getString("pais_sgl"), rs.getString("pais_nome"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cid;
    }
    
    public Cidade getCidade(String nome, String uf) 
    {    
        String sql = "select * from cidade INNER JOIN estado ON cidade.est_cod = estado.est_cod"
                +    " INNER JOIN pais ON estado.pais_cod = pais.pais_cod" +
                     " where CID_NOME LIKE '$1' AND EST_SGL LIKE '$2'";
        Cidade cid = null;
        if(nome == null)
            return null;
        sql = sql.replace("$1", nome);
        sql = sql.replace("$2", uf);
        ResultSet rs = Banco.con.consultar(sql);

        try
        {
            if(rs.next())
            {
                cid = new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome"), 
                                 new Estado(rs.getInt("est_cod"), rs.getString("est_sgl"), rs.getString("est_nome"), 
                                            new Pais(rs.getInt("pais_cod"), rs.getString("pais_sgl"), rs.getString("pais_nome"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return cid;
    }
    
    public ArrayList <Cidade> getCidades(String filtro)
    {
        ArrayList <Cidade> lista = new ArrayList();
        String sql = "select * from cidade INNER JOIN estado" +
                     " ON cidade.est_cod = estado.est_cod";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " WHERE "+filtro;
        
        sql += " order by cid_nome";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome"), new Estado(rs.getInt("est_cod"), rs.getString("est_sgl"), rs.getString("est_nome"), null)));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public ArrayList <Estado> getEstados(String filtro)
    {
        ArrayList <Estado> lista = new ArrayList();
        String sql = "select * from estado";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " where "+filtro;
        
        sql += " order by est_sgl";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Estado(rs.getInt("est_cod"), rs.getString("est_sgl"), rs.getString("est_nome"), null));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
}
