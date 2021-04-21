/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import siscon.entidades.Cidade;
import siscon.entidades.Empresa;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrEmpresa {
    
    private Empresa empresa;

    public CtrEmpresa() {
        empresa = new Empresa();
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    
    public void novo()
    {
        empresa = new Empresa();
    }
    
   
    public ArrayList <Empresa> ListarEmpresas(String filtro)
    {
        String aux = ""; 
        
        
        if(!filtro.equals(""))
            aux = "upper(emp_nome) LIKE '%"+ filtro.toUpperCase()+"%'";
        
        String filtro_aux = filtro.replace("(", "");
        filtro_aux = filtro_aux.replace(")", "");
        filtro_aux = filtro_aux.replace("-", "");
        filtro_aux = filtro_aux.replace(".", "");
        filtro_aux = filtro_aux.replace("/", "");

        if(Funcoes.isFloat(filtro_aux) || (filtro.contains("-") && filtro.contains(".") && filtro.contains("/"))) //cnpj
           aux = aux.replace("upper(emp_nome)", "emp_cnpj");        
               
        return empresa.getEmpresas(aux);
    } 

    public boolean Salvar(String nome, String cnpj, String bairro, String tipo_log, String logradouro, String num, String telefone, String email, String cep, String cidade, String uf, String telefone2) {
        Cidade c = new Cidade();
        if(logradouro.equals(""))
            tipo_log = num = "";
        empresa = new Empresa(nome, cnpj, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), telefone2 );
        return empresa.salvar();  
    }
    
    public boolean Alterar(int cod, String nome, String cnpj, String bairro, String tipo_log, String logradouro, String num, String telefone, String email, String cep, String cidade, String uf, String telefone2) {
        Cidade c = new Cidade();
        if(logradouro.equals(""))
            tipo_log = num = "";
        empresa = new Empresa(cod, nome, cnpj, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), telefone2 );
        return empresa.alterar();  
    }
    
    public boolean Excluir()
    {        
        //return empresa.excluir();  
        return empresa.desativar();
    }
    
    public boolean SalvarImagem(BufferedImage img)
    {
        return empresa.salvarimagem(img);            
    }
    
    public BufferedImage LerImagem(int cod)
    {
        return empresa.lerimagem(cod);
    }

    
    
}
