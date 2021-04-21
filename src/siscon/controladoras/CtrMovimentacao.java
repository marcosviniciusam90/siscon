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
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import siscon.entidades.Cidade;
import siscon.entidades.Associado;
import siscon.entidades.Dependente;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.Movimentacao;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrMovimentacao {
    
    private Movimentacao movimentacao;
    private Pessoa usuario;

    public CtrMovimentacao() {
        movimentacao = new Movimentacao();
        usuario = new Pessoa();
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }
    
    public void setMovimentacao(Movimentacao m)
    {
        movimentacao = m;
    }
    
    public Pessoa getUsuario()
    {
        return usuario;
    }
    
    public void novo()
    {
        movimentacao = new Movimentacao();
    }
    
    public void setCondutor(int cod)
    {
        movimentacao.setCondutor(movimentacao.getCondutor().getPessoa(cod));        
    }
    
    public void setUsuario(int cod)
    {
        movimentacao.setUsuario(movimentacao.getUsuario().getPessoa(cod));        
    }
    
    public void setVeiculo(int cod)
    {
        if(cod == 0)
            movimentacao.setVeiculo(null);
        else
            movimentacao.setVeiculo(movimentacao.getVeiculo().getVeiculo(cod));
    }
    
    public void setTipo()
    {
        if(movimentacao.getCondutor().getUltimamov().equals("E"))
           movimentacao.setTipo("S");
        else
           movimentacao.setTipo("E");
    }
    
    public ArrayList <Movimentacao> ListarMovimentacoes(String filtro, String categoria, String tipo, boolean ultimas)
    {
        String aux = "";
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "(upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' or upper(vei_modelo) LIKE '%"+ filtro.toUpperCase()+"%' or upper(mar_nome) LIKE '%"+ filtro.toUpperCase()+"%' or upper(vei_placa) LIKE '%"+ filtro.toUpperCase()+"%')";
        /*
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"'";
        */

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        if(!aux.equals(""))
           aux += " and ";
        aux += "mov_tipo = '"+tipo+"'";
        if(!ultimas)
           return movimentacao.getMovimentacoes(aux);
        else
            return movimentacao.getUltimasMovimentacoes(aux);
    } 
    
    public ArrayList <Pessoa> ListarCondutores(int proprietario_cod, String tipomov)
    {
        return movimentacao.getCondutor().getPessoas("pes_condutor LIKE 'Sim' and pes_ultimamov LIKE '"+tipomov+"' and (pes_cod = "+proprietario_cod+" OR pes_supervisor = "+proprietario_cod+")");
    }
    
    public ArrayList <Veiculo> ListarVeiculos()
    {
        if(movimentacao.getCondutor()instanceof Dependente)
           return movimentacao.getVeiculo().getVeiculos("pes_cod = "+((Dependente)(movimentacao.getCondutor())).getSupervisor().getCod()+" and vei_ultimamov = '"+movimentacao.getCondutor().getUltimamov()+"'"); 
        return movimentacao.getVeiculo().getVeiculos("pes_cod = "+movimentacao.getCondutor().getCod()+" and vei_ultimamov = '"+movimentacao.getCondutor().getUltimamov()+"'");
    }
    
    public void CarregarDados()
    {
        movimentacao.carregar_dados();
    }
    
    public void AdicionarPassageiro(ObservableList indices)
    {
        movimentacao.add(indices);
    }
    
    public void RemoverPassageiro(ObservableList indices)
    {
        movimentacao.remove(indices);
    }
    
    public void AdicionarDisponivel(int cod)
    {
        movimentacao.adicionar_disponivel(cod);
    }
    
    public boolean Movimentar(int condutor, int veiculo, int usuario)
    {
        setCondutor(condutor);
        setUsuario(usuario);
        setVeiculo(veiculo);
        if(movimentacao.getTipo().equals("E") && !movimentacao.autorizar())
            return false;
        return movimentacao.realizar();
    
    }
    
    public boolean Desfazer(int condutor, int veiculo)
    {
        setCondutor(condutor);
        setVeiculo(veiculo);
        return movimentacao.desfazer();
    
    }
    
    

    

    
    
}
