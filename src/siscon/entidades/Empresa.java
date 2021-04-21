/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import siscon.util.Banco;


public class Empresa { 
    private int cod;
    private String nome, cnpj, bairro, tipoLog, logradouro, numero, fone, email, cep, fone2;
    private Cidade cidade;
    
    public Empresa() {
        cod = 0;
        nome = "";
        this.cnpj = "";
        this.bairro = "";
        this.tipoLog = "";
        this.logradouro = "";
        this.numero = "";
        this.fone = "";
        this.email = "";        
        this.cidade = null;
        this.fone2 = "";
    }
    
    public Empresa(int cod, String nome, String cnpj, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, String fone2) {
        this.cod = cod;
        this.nome = nome;
        this.cnpj = cnpj;
        this.bairro = bairro;
        this.tipoLog = tipoLog;
        this.logradouro = logradouro;
        this.numero = numero;
        this.fone = fone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.fone2 = fone2;
        
    }    
    
    public Empresa(String nome, String cnpj, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, String fone2) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.bairro = bairro;
        this.tipoLog = tipoLog;
        this.logradouro = logradouro;
        this.numero = numero;
        this.fone = fone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.fone2 = fone2;
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

    public String getNome()
    {
        return nome;
    }

    final public void setNome(String nome)
    {
        if (nome.length() > 100)
            nome = nome.substring(0, 99);
        this.nome = nome;
    }
    
    
    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }
    

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

   

    public String getCnpj()
    {
        return cnpj;
    }

    final public void setCnpj(String cnpj)
    {
        this.cnpj = cnpj;
    }

    public String getBairro()
    {
        return bairro;
    }

    final public void setBairro(String bairro)
    {
        if (bairro.length() > 25)
            bairro = bairro.substring(0, 24);
        this.bairro = bairro;
    }

    public String getTipoLog()
    {
        return tipoLog;
    }

    final public void setTipoLog(String tipoLog)
    {
        if (tipoLog.length() > 10)
            tipoLog = tipoLog.substring(0, 24);
        this.tipoLog = tipoLog;
    }

    public String getLogradouro()
    {
        return logradouro;
    }

    final public void setLogradouro(String logradouro)
    {
        if (logradouro.length() > 50)
            logradouro = logradouro.substring(0, 24);
        this.logradouro = logradouro;
    }

    public String getNumero()
    {
        return numero;
    }

    final public void setNumero(String numero)
    {
        if (numero.length() > 10)
            numero = numero.substring(0, 9);
        this.numero = numero;
    }

    public String getFone()
    {
        return fone;
    }

    final public void setFone(String fone)
    {
        this.fone = fone;
    }

    public String getEmail()
    {
        return email;
    }

    final public void setEmail(String email)
    {
        this.email = email;
    }

    public Cidade getCidade()
    {
        return cidade;
    }

    final public void setCidade(Cidade cidade)
    {
        this.cidade = cidade;
    }  
    
    @Override
    public String toString()
    {
        return nome;       
    }

    
    public Empresa getEmpresa(int cod) 
    {    
        String sql = "select * from empresa INNER JOIN cidade ON empresa.cid_cod = cidade.cid_cod where emp_cod = '"+cod+"'";
        Empresa e = null;
        Cidade cid = new Cidade();
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next())
            {
                e = new Empresa(rs.getInt("emp_cod"), rs.getString("emp_nome"), rs.getString("emp_cnpj"), rs.getString("emp_bairro") ,rs.getString("emp_tipo_log"), rs.getString("emp_logradouro"), rs.getString("emp_numero"), rs.getString("emp_fone"), rs.getString("emp_email"), rs.getString("emp_cep"), cid.getCidade(rs.getInt("cid_cod")), rs.getString("emp_fone2"));
                
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return e;
    }
    
    public ArrayList <Empresa> getEmpresas(String filtro)
    {
        Cidade cid = new Cidade();
        ArrayList <Empresa> lista = new ArrayList();
        String sql = "select * from empresa INNER JOIN cidade ON empresa.cid_cod = cidade.cid_cod";
        
        //STATUS, somente os ATIVADOS
        if(!filtro.equals(""))
           filtro += " and emp_status = 'A'";
        else
           filtro = "emp_status = 'A'";

        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " where "+filtro;
        
        sql += " order by emp_nome, emp_cnpj";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                lista.add(new Empresa(rs.getInt("emp_cod"), rs.getString("emp_nome"), rs.getString("emp_cnpj"), rs.getString("emp_bairro") ,rs.getString("emp_tipo_log"), rs.getString("emp_logradouro"), rs.getString("emp_numero"), rs.getString("emp_fone"), rs.getString("emp_email"), rs.getString("emp_cep"), cid.getCidade(rs.getInt("cid_cod")), rs.getString("emp_fone2")));
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
        List <String> campos = new ArrayList<>(Arrays.asList("emp_nome", "emp_bairro","emp_tipo_log","emp_logradouro","emp_numero","cid_cod","emp_fone","emp_email","emp_cep","emp_cnpj","emp_fone2", "emp_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(getNome(), getBairro(),getTipoLog(),getLogradouro(),getNumero(),getCidade().getCod()+"",getFone(),getEmail(),getCep(),getCnpj(), getFone2(), "A"));
           
        if(Banco.con.cmdInsert("empresa", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("empresa", "emp_cod", ""));
            return true;
        }
        
        return false;    
    }
    public boolean alterar()
    {
        List <String> campos = new ArrayList<>(Arrays.asList("emp_cod","emp_nome", "emp_bairro","emp_tipo_log","emp_logradouro","emp_numero","cid_cod","emp_fone","emp_email","emp_cep","emp_cnpj","emp_fone2"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"",getNome(), getBairro(),getTipoLog(),getLogradouro(),getNumero(),getCidade().getCod()+"",getFone(),getEmail(),getCep(),getCnpj(), getFone2()));
        
        if(Banco.con.cmdUpdate("empresa", campos, valores))         
        {
            return true;
        }        
        return false;    
    }
    
    public boolean excluir() 
    {     
        return Banco.con.cmdDelete("empresa", "emp_cod", cod);   
    }    
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("emp_cod","emp_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("empresa", campos, valores);
    }

    public boolean salvarimagem(BufferedImage img) {
        String sql = "";
        if(img != null)
        {
            try
            {
                sql = "update empresa set emp_foto= ? where emp_cod = ?";                
                PreparedStatement st = Banco.con.getConnect().prepareStatement(sql);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", baos);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                st.setBinaryStream(1, is, baos.toByteArray().length); //substituri ? numero 1 pela imagem
                st.setInt(2, getCod());

                st.executeUpdate();
                return true;
            }
            catch(Exception ex)
            {
                System.out.println("Erro: " + ex.getMessage());
                return false;
            }
        }
        else
        {
            try 
            {
                sql = "update empresa set emp_foto= ' ' where emp_cod = " + getCod();                
                return Banco.con.manipular(sql);           
            } 
            catch (Exception ex) 
            {
                System.out.println("Erro: " + ex.getMessage());
                return false;
            }   
        }
    }

    public BufferedImage lerimagem(int cod) {
        BufferedImage bimg = null;
        String sql = "", campo = "";
        
        try
        {     
            sql = "select emp_foto from empresa where emp_cod = " + cod;
            campo = "emp_foto";
            PreparedStatement ps = Banco.con.getConnect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                byte bytes[] = rs.getBytes(campo);
                InputStream is = new ByteArrayInputStream(bytes);
                bimg = ImageIO.read(is);
            }
        }
        catch(Exception ex)
        {
            //System.out.println("Erro: " + ex.getMessage());                    
        }
        return bimg;
    }
    
}
