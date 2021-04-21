/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class Movimentacao {
    
    private int cod;
    private Pessoa condutor;
    private Pessoa usuario;
    private Veiculo veiculo;
    private String tipo;
    private String data;
    private ArrayList <Pessoa> passageiros = new ArrayList();
    private ArrayList <Pessoa> disponiveis = new ArrayList();

    public Movimentacao() {
        cod = 0;
        condutor = new Pessoa();
        usuario = new Pessoa();
        veiculo = new Veiculo();
        tipo = "";
        data = null;        
    }

    public Movimentacao(int cod, Pessoa condutor, Veiculo veiculo, String tipo, Timestamp data, Pessoa usuario) {
        this.cod = cod;
        this.condutor = condutor;
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.tipo = tipo;
        this.data = Funcoes.DateToString(data);
    }

    public Movimentacao(Pessoa condutor, Veiculo veiculo, String tipo, Timestamp data, Pessoa usuario) {
        this.condutor = condutor;
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.tipo = tipo;
        this.data = Funcoes.DateToString(data);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Pessoa getCondutor() {
        return condutor;
    }  

    public void setCondutor(Pessoa condutor) {
        this.condutor = condutor;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }
    
    

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = Funcoes.DateToString(data);
    }
    
    
    public void adicionar_disponivel(int cod)
    {
        if(cod == condutor.getCod()) //a pessoa já está como condutor da movimentação
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não foi possível adicionar esta pessoa.\nA pessoa em questão já está definida como condutora da movimentação.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return;
        }
        for(int i=0; i<disponiveis.size();i++)
            if(disponiveis.get(i).getCod() == cod)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("Não foi possível adicionar esta pessoa.\nA pessoa em questão já está na lista de pessoas sugeridas.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return;                
            }
        for(int i=0; i<passageiros.size();i++)
            if(passageiros.get(i).getCod() == cod)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("Não foi possível adicionar esta pessoa.\nA pessoa em questão já está na lista de passageiros.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return;                
            }
        disponiveis.add(condutor.getPessoa(cod));
    }
    
    public void adicionar_passageiro(int cod)
    {
        passageiros.add(condutor.getPessoa(cod));
    }
    
    public void add(ObservableList indices) //adicionando passageiros
    {
        for(int k=0; k<indices.size();k++)
            passageiros.add(disponiveis.get((int)indices.get(k)));
                    
        for(int k=indices.size()-1; k>=0; k--)
            disponiveis.remove((int)indices.get(k));
    }
    
    public void remove(ObservableList indices) //removendo passageiros
    {
        for(int k=0; k<indices.size();k++)
            disponiveis.add(passageiros.get((int)indices.get(k)));
                    
        for(int k=indices.size()-1; k>=0; k--)
            passageiros.remove((int)indices.get(k));
        
    }
    
    public ArrayList <Pessoa> getPassageiros()
    {
        return passageiros;
    } 
    
    public ArrayList <Pessoa> getDisponiveis()
    {
        return disponiveis;
    } 
    
    public boolean ExisteMovimentacao()
    {
        return Banco.con.getMaxPK("movimentacao", "mov_cod", "") > 0;
    }
    
    public void carregar_dados() 
    {
        disponiveis.clear();
        String filtro = "";
        if(condutor instanceof Dependente)
           filtro = "pes_cod <> "+condutor.getCod()+" and pes_ultimamov = '"+condutor.getUltimamov()+"'" + " and (pes_supervisor = " +((Dependente)condutor).getSupervisor().getCod()+ " or pes_cod = "+((Dependente)condutor).getSupervisor().getCod()+")";
        else
           filtro = "pes_supervisor = "+condutor.getCod()+ " and pes_ultimamov = '"+condutor.getUltimamov()+"'";
        disponiveis.addAll(condutor.getPessoas(filtro));
    }
    
    public void CarregarPassageiros()
    {
        passageiros.clear();
        Pessoa p = new Pessoa();
        String sql = "select * from movimentacaopassageiros FULL JOIN pessoa ON movimentacaopassageiros.pes_cod = pessoa.pes_cod where mov_cod = "+cod;
        
        sql += " ORDER BY pes_nome";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                passageiros.add(p.getPessoa(rs.getInt("pes_cod")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public Movimentacao getMovimentacao(int cod)
    {
        Movimentacao m = new Movimentacao();
        Pessoa p = new Pessoa();
        Veiculo v = new Veiculo();
        String sql = "select * from movimentacao FULL JOIN pessoa ON movimentacao.pes_cod = pessoa.pes_cod FULL JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod";
        sql += " where mov_cod = "+cod;        
        sql += " ORDER BY mov_data";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                m = new Movimentacao(rs.getInt("mov_cod"), p.getPessoa(rs.getInt("pes_cod")), v.getVeiculo(rs.getInt("vei_cod")), rs.getString("mov_tipo"), rs.getTimestamp("mov_data"), p.getPessoa(rs.getInt("usu_cod")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return m;
    }
    
    public Movimentacao getUltimaMovimentacao()
    {
        int codigo = Banco.con.getMaxPK("movimentacao", "mov_cod", "");
        return getMovimentacao(codigo);
    }
    
    public ArrayList <Movimentacao> getMovimentacoes(String filtro)
    {
        Pessoa p = new Pessoa();
        Veiculo v = new Veiculo();
        ArrayList <Movimentacao> lista = new ArrayList();
        String sql = "select * from movimentacao FULL JOIN pessoa ON movimentacao.pes_cod = pessoa.pes_cod FULL JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod FULL JOIN Marca ON Veiculo.mar_cod = Marca.mar_cod";
        
        

        //String sql = "select * from (select pes_cod, MAX(mov_cod) as maior FROM movimentacao group by pes_cod) D INNER JOIN Movimentacao on D.pes_cod = Movimentacao.pes_cod and D.maior = Movimentacao.mov_cod INNER JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod";
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " where "+filtro;
        
        sql += " ORDER BY mov_data";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                lista.add(new Movimentacao(rs.getInt("mov_cod"), p.getPessoa(rs.getInt("pes_cod")), v.getVeiculo(rs.getInt("vei_cod")), rs.getString("mov_tipo"), rs.getTimestamp("mov_data"), p.getPessoa(rs.getInt("usu_cod"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public ArrayList <Movimentacao> getUltimasMovimentacoes(String filtro)
    {
        Pessoa p = new Pessoa();
        Veiculo v = new Veiculo();
        ArrayList <Movimentacao> lista = new ArrayList();
        //String sql = "select * from movimentacao INNER JOIN pessoa ON movimentacao.pes_cod = pessoa.pes_cod INNER JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod";
        
        

        String sql = "select * from (select pessoa.pes_cod, pessoa.pes_nome, pessoa.pes_cpf, MAX(mov_cod) as maior FROM movimentacao INNER JOIN pessoa ON movimentacao.pes_cod = pessoa.pes_cod group by pessoa.pes_cod, pessoa.pes_nome ) D INNER JOIN Movimentacao on D.pes_cod = Movimentacao.pes_cod and D.maior = Movimentacao.mov_cod FULL JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod  FULL JOIN Marca ON Veiculo.mar_cod = Marca.mar_cod";
        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " where "+filtro;
        
        sql += " ORDER BY mov_data";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {   
                lista.add(new Movimentacao(rs.getInt("mov_cod"), p.getPessoa(rs.getInt("pes_cod")), v.getVeiculo(rs.getInt("vei_cod")), rs.getString("mov_tipo"), rs.getTimestamp("mov_data"), p.getPessoa(rs.getInt("usu_cod"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean autorizar()
    {
        String erro = "", psg = "";
        if(condutor.getAcesso().equals("Não Autorizado"))
            erro += "Condutor não autorizado.\n";
        if(veiculo!=null && veiculo.getAutorizado().equals("N"))
            erro += "Veículo não autorizado.\n";
        for(int i=0; i<passageiros.size();i++)
            if(passageiros.get(i).getAcesso().equals("Não Autorizado"))
               psg += passageiros.get(i).getNome()+"\n";
        if(!psg.equals(""))
            erro += "Passageiro(s) não autorizado(s):\n"+psg;
        
        if(!erro.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Entrada não autorizada.");
            alert.setContentText(erro);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;
        }
        
        if(condutor.getAcesso().equals("Livre Acesso")) //ok
           return true;
        
        //Mediante Autorização
        if(PartipanteDeEvento())
           return true;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Autorizar entrada");
        alert.setContentText("Deseja autorizar a movimentação ?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
        return alert.showAndWait().get() == buttonTypeSim;        
    }
    
    public boolean PartipanteDeEvento()
    {
        String sql = "select * from pessoa INNER JOIN eventoparticipantes ON pessoa.pes_cod = eventoparticipantes.pes_cod INNER JOIN evento ON evento.eve_cod = eventoparticipantes.eve_cod" +
                     " WHERE eventoparticipantes.pes_cod = "+condutor.getCod()+" and eve_status = 'A' and ep_autorizado = 'S' and ep_datainicio <= '"+LocalDate.now()+"' and ep_datafim >= '"+LocalDate.now()+"'";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                return true;
            }
        }
        catch(SQLException ex)
        {
            return false;
        }
        return false;
    }

    public boolean realizar() {
        String horario = new Timestamp(System.currentTimeMillis()) + "";
        List <String> campos = new ArrayList<>(Arrays.asList("mov_data", "mov_tipo", "pes_cod", "usu_cod"));
        List <String> valores = new ArrayList<>(Arrays.asList(horario, tipo, condutor.getCod()+"", usuario.getCod()+""));
        if(veiculo != null)
        {
            campos.add("vei_cod");
            valores.add(veiculo.getCod()+"");
        }
        if(Banco.con.cmdInsert("movimentacao", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("movimentacao", "mov_cod", ""));
            condutor.setAcesso(condutor.getAcesso());
            condutor.setUltimamov(tipo);
            if(!condutor.alterar())
                return false;
            
            if(veiculo != null)
            {                
                veiculo.setUltimamov(tipo);
                if(!veiculo.alterar())
                    return false;
            }
        }
        else
           return false;
        
        for(int i=0; i<passageiros.size(); i++)
        {      
            passageiros.get(i).setAcesso(passageiros.get(i).getAcesso());
            campos = new ArrayList<>(Arrays.asList("mov_cod", "pes_cod"));
            valores = new ArrayList<>(Arrays.asList(cod+"", passageiros.get(i).getCod()+""));
            if(!Banco.con.cmdInsert("movimentacaopassageiros", campos, valores))
                return false;
            else
            {
                passageiros.get(i).setUltimamov(tipo);
                if(!passageiros.get(i).alterar())
                    return false;
            }
        }
        return true;
    }
    
    public boolean desfazer() {        
        if(tipo.equals("E"))
            tipo = "S";
        else
            tipo = "E";
        condutor.setUltimamov(tipo);
        condutor.setAcesso(condutor.getAcesso());
        if(!condutor.alterar())
            return false;
        
        if(veiculo != null)
        {
            veiculo.setUltimamov(tipo);
            if(!veiculo.alterar())
                return false;
        }
        
        for(int i=0; i<passageiros.size(); i++)
        {            
                passageiros.get(i).setAcesso(passageiros.get(i).getAcesso());
                passageiros.get(i).setUltimamov(tipo);
                if(!passageiros.get(i).alterar())
                    return false;
        }
        Banco.con.cmdDelete("movimentacaopassageiros", "mov_cod", cod);
        return Banco.con.cmdDelete("movimentacao", "mov_cod", cod);
    }
    
    
    
    
}
