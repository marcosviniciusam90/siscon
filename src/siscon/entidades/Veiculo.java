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


public class Veiculo {
    private int cod;
    private String modelo, placa, ultimamov, autorizado;
    private Pessoa pessoa;
    private Cor cor;
    private Marca marca;
    private CategoriaVeiculo categoria;

    public Veiculo() {
        cod = 0;
        modelo = "";
        placa = "";
        ultimamov = "";
        autorizado = "";
        pessoa = new Pessoa();
        cor = new Cor();
        marca = new Marca();
        categoria = new CategoriaVeiculo();
    }

    public Veiculo(String modelo, String placa, Pessoa pessoa, Cor cor, Marca marca, CategoriaVeiculo categoria, String ultimamov, String autorizado) {
        this.modelo = modelo;
        this.placa = placa.toUpperCase();
        this.pessoa = pessoa;
        this.cor = cor;
        this.marca = marca;
        this.categoria = categoria;
        this.ultimamov = ultimamov;
        this.autorizado = autorizado;
    }

    public Veiculo(int cod, String modelo, String placa, Pessoa pessoa, Cor cor, Marca marca, CategoriaVeiculo categoria, String ultimamov, String autorizado) {
        this.cod = cod;
        this.modelo = modelo;
        this.placa = placa.toUpperCase();
        this.pessoa = pessoa;
        this.cor = cor;
        this.marca = marca;
        this.categoria = categoria;
        this.ultimamov = ultimamov;
        this.autorizado = autorizado;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa.toUpperCase();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public CategoriaVeiculo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaVeiculo categoria) {
        this.categoria = categoria;
    }

    public String getUltimamov() {
        return ultimamov;
    }

    public void setUltimamov(String ultimamov) {
        this.ultimamov = ultimamov;
    }

    public String getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(String autorizado) {
        this.autorizado = autorizado;
    }
    
    
    
    public String toString()
    {
        return marca.getNome() + " "+modelo+" "+ cor.getNome()+" ("+placa+")";
    }
    
    public Veiculo getVeiculo(int cod) 
    {    
        String sql = "select * from veiculo inner join marca on veiculo.mar_cod = marca.mar_cod"
                +    " where vei_cod = $1";
        sql = sql.replace("$1", cod + "");
        ResultSet rs = Banco.con.consultar(sql);
        Veiculo vei = null;
        Pessoa p = new Pessoa();
        Cor cor = new Cor();
        Marca mar = new Marca();
        CategoriaVeiculo cat = new CategoriaVeiculo();
        try
        {
            if(rs.next())
            {
                vei = new Veiculo(rs.getInt("vei_cod"), rs.getString("vei_modelo"), rs.getString("vei_placa"), p.getPessoa(rs.getInt("pes_cod")), cor.getCor(rs.getInt("cor_cod")), mar.getMarca(rs.getInt("mar_cod")), cat.getCategoria(rs.getInt("cat_cod")), rs.getString("vei_ultimamov"), rs.getString("vei_autorizado"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return vei;
    }
    
    public ArrayList <Veiculo> getVeiculos(String filtro)
    {
        ArrayList <Veiculo> lista = new ArrayList();
        String sql = "select * from veiculo inner join marca on veiculo.mar_cod = marca.mar_cod";
        Pessoa p = new Pessoa();
        Cor cor = new Cor();
        Marca mar = new Marca();
        CategoriaVeiculo cat = new CategoriaVeiculo();
        
        //STATUS, somente os ATIVADOS
        if(!filtro.equals(""))
           filtro += " and vei_status = 'A'";
        else
           filtro = "vei_status = 'A'";
        
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " WHERE "+filtro;
        
        sql += " order by vei_modelo";
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                lista.add(new Veiculo(rs.getInt("vei_cod"), rs.getString("vei_modelo"), rs.getString("vei_placa"), p.getPessoa(rs.getInt("pes_cod")), cor.getCor(rs.getInt("cor_cod")), mar.getMarca(rs.getInt("mar_cod")), cat.getCategoria(rs.getInt("cat_cod")), rs.getString("vei_ultimamov"),rs.getString("vei_autorizado")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean salvar() //gravar o veiculo em Banco
    {
        List <String> campos = new ArrayList<>(Arrays.asList("vei_modelo", "vei_placa", "cat_cod","mar_cod","cor_cod","pes_cod", "vei_status", "vei_ultimamov", "vei_autorizado"));
        List <String> valores = new ArrayList<>(Arrays.asList(getModelo(), getPlaca(), getCategoria().getCod()+"",getMarca().getCod()+"",getCor().getCod()+"", getPessoa().getCod()+"", "A", getUltimamov(), autorizado));
        
        if(Banco.con.cmdInsert("veiculo", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("veiculo", "vei_cod", ""));
            return true;
        }
        
        return false;    
    }
    
    
    public boolean alterar()
    {
        List <String> campos = new ArrayList<>(Arrays.asList("vei_cod" ,"vei_modelo", "vei_placa", "cat_cod","mar_cod","cor_cod","pes_cod", "vei_ultimamov", "vei_autorizado"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getModelo(), getPlaca(), getCategoria().getCod()+"",getMarca().getCod()+"",getCor().getCod()+"", getPessoa().getCod()+"", getUltimamov(), autorizado));
        
        if(Banco.con.cmdUpdate("veiculo", campos, valores))         
        {
            return true;
        }        
        return false;    
    }
    
    public boolean excluir() 
    {     
        return Banco.con.cmdDelete("veiculo", "vei_cod", cod);   
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("vei_cod","vei_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("veiculo", campos, valores);
    }
    
    
}
