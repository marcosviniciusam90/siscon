/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class Evento {
    private int cod;
    private String descricao, data_inicial, data_final, observacao, status;
    private Pessoa responsavel = new Pessoa();
    private CategoriaEvento categoria = new CategoriaEvento();
    private ArrayList <EventoParticipante> participantes = new ArrayList();

    public Evento() {
        cod = 0;
        descricao = "";
    }

    public Evento(int cod, String descricao, LocalDate data_inicial, LocalDate data_final, String observacao, Pessoa responsavel, CategoriaEvento categoria, String status) {
        this.cod = cod;
        this.descricao = descricao;
        this.data_inicial = Funcoes.DateToString(data_inicial);
        this.data_final = Funcoes.DateToString(data_final);
        this.observacao = observacao;
        this.responsavel = responsavel;
        this.categoria = categoria;
        setStatus(status);
    }
    
    public Evento(String descricao, LocalDate data_inicial, LocalDate data_final, String observacao, Pessoa responsavel, CategoriaEvento categoria, String status) {
        this.descricao = descricao;
        this.data_inicial = Funcoes.DateToString(data_inicial);
        this.data_final = Funcoes.DateToString(data_final);
        this.observacao = observacao;
        this.responsavel = responsavel;
        this.categoria = categoria;
        setStatus(status);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("A"))
           this.status = "Andamento";
        else
          if(status.equals("Andamento"))
             this.status = "A";
        
        if(status.equals("F"))
           this.status = "Finalizado";
        else
          if(status.equals("Finalizado"))
             this.status = "F";
    }

    public String getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(LocalDate data_inicial) {
        this.data_inicial = Funcoes.DateToString(data_inicial);
    }

    public String getData_final() {
        return data_final;
    }

    public void setData_final(LocalDate data_final) {
        this.data_final = Funcoes.DateToString(data_final);
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    public ArrayList<EventoParticipante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<EventoParticipante> participantes) {
        this.participantes = participantes;
    }
    
    public String toString()
    {
        return descricao;
    }
    
    public ArrayList <EventoParticipante> alterar_participante(ObservableList <EventoParticipante> parcs, int indice, String autorizado, LocalDate data_inicio, LocalDate data_fim)
    {
        ArrayList <EventoParticipante> participantes_aux = new ArrayList();
        for(int i=0; i<parcs.size(); i++)
        {
            if(i != indice)
            {
               parcs.get(i).setAutorizado(parcs.get(i).getAutorizado());
               participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), parcs.get(i).getAutorizado(), Funcoes.StringToDate(parcs.get(i).getData_inicio()), Funcoes.StringToDate(parcs.get(i).getData_fim())));
            }
            else
               participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), autorizado, data_inicio, data_fim));
            
        }
        return participantes_aux;
    }
    
    public ArrayList <EventoParticipante> ajustar_participantes(ObservableList <EventoParticipante> parcs, ObservableList indices, LocalDate data_inicio, LocalDate data_fim)
    {
        ArrayList <EventoParticipante> participantes_aux = new ArrayList();        
        
        for(int i=0; i<parcs.size();i++)
        {
            boolean achou = false;
            for(int k=0; k<indices.size();k++)
                if(i == (int)indices.get(k)) //achou, mesmo indice, participante foi selecionado
                    achou = true;
            parcs.get(i).setAutorizado(parcs.get(i).getAutorizado());
            if(!achou)
            {
               participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), parcs.get(i).getAutorizado(), Funcoes.StringToDate(parcs.get(i).getData_inicio()), Funcoes.StringToDate(parcs.get(i).getData_fim())));
            }
            else
            {
               if(data_inicio != null && data_fim != null)
                  participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), parcs.get(i).getAutorizado(), data_inicio, data_fim));    
               if(data_inicio != null && data_fim == null)
                  participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), parcs.get(i).getAutorizado(), data_inicio, Funcoes.StringToDate(parcs.get(i).getData_fim())));  
               if(data_inicio == null && data_fim != null)
                  participantes_aux.add(new EventoParticipante(parcs.get(i).getEvento(), parcs.get(i).getParticipante(), parcs.get(i).getAutorizado(), Funcoes.StringToDate(parcs.get(i).getData_inicio()), data_fim));  
            }
        }
        return participantes_aux;
    }
    
    public boolean validar_participantes(List <EventoParticipante> participantes, LocalDate data_minima, LocalDate data_maxima)
    {
        
        for(int i=0; i<participantes.size(); i++)
        {   
            if(Funcoes.StringToDate(participantes.get(i).getData_inicio()).isBefore(data_minima) || Funcoes.StringToDate(participantes.get(i).getData_fim()).isAfter(data_maxima))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("O período de participação dos participantes deve estar dentro do período do evento.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return false; 
            }  
        }
        return true;
    }
    
    public Evento getEvento(int cod)
    {
        Evento e = null;
        Pessoa p = new Pessoa();
        CategoriaEvento ce = new CategoriaEvento();
        String sql = "select * from evento";
        sql += " where eve_cod = "+cod;        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {         
                p = p.getPessoa(rs.getInt("pes_cod"));
                ce = ce.getCategoria(rs.getInt("cat_cod"));
                e = new Evento(rs.getInt("eve_cod"), rs.getString("eve_descricao"), rs.getDate("eve_datainicio").toLocalDate(),rs.getDate("eve_datafim").toLocalDate(),rs.getString("eve_observacao"), p ,ce, rs.getString("eve_status"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return e;
    } 
    
    public ArrayList <Evento> getEventos(String filtro)
    {
        ArrayList <Evento> e = new ArrayList();
        Pessoa p = new Pessoa();
        CategoriaEvento ce = new CategoriaEvento();
        String sql = "select * from evento INNER JOIN pessoa ON evento.pes_cod = pessoa.pes_cod WHERE eve_status <> 'D'";
        if(!filtro.equals(""))
            sql += " and "+filtro;       
        sql += " ORDER BY eve_descricao, pes_nome, eve_cod";    
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {         
                p = p.getPessoa(rs.getInt("pes_cod"));
                ce = ce.getCategoria(rs.getInt("cat_cod"));
                e.add(new Evento(rs.getInt("eve_cod"), rs.getString("eve_descricao"), rs.getDate("eve_datainicio").toLocalDate(),rs.getDate("eve_datafim").toLocalDate(),rs.getString("eve_observacao"), p ,ce, rs.getString("eve_status")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return e;
    } 
    
    public void carregar_participantes()
    {
        EventoParticipante ep = new EventoParticipante();
        participantes = ep.getEventoParticipantes(cod);
    }
    
    public boolean desativar() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("eve_cod","eve_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("evento", campos, valores);
           
    }
    
    public boolean adicionar_participantes()
    {
        for(int i=0; i<participantes.size(); i++)
        {
            participantes.get(i).setEvento(this);
            if(!participantes.get(i).inserir())
                return false;
        }
        return true;
    }
    
    public boolean remover_participantes()
    {
        Banco.con.cmdDelete("eventoparticipantes", "eve_cod", cod);
        return true;
    }
    
    public boolean registrar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("eve_descricao", "pes_cod", "eve_datainicio","eve_datafim","eve_status","eve_observacao", "cat_cod"));
        List <String> valores = new ArrayList<>(Arrays.asList(descricao, responsavel.getCod()+"", Funcoes.StringToDate(data_inicial)+"", Funcoes.StringToDate(data_final)+"", status, observacao, categoria.getCod()+""));
        
        if(Banco.con.cmdInsert("evento", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("evento", "eve_cod", ""));
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("eve_cod","eve_descricao", "pes_cod", "eve_datainicio","eve_datafim","eve_status","eve_observacao", "cat_cod"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", descricao, responsavel.getCod()+"", Funcoes.StringToDate(data_inicial)+"", Funcoes.StringToDate(data_final)+"", status, observacao, categoria.getCod()+""));
        
        return Banco.con.cmdUpdate("evento", campos, valores);   
    }
    
    public boolean finalizar_eventos_passados() 
    {
        Banco.con.manipular("update evento set eve_status = 'F' WHERE eve_status = 'A' and eve_datafim < '"+LocalDate.now()+"'");
        return true;
    }
    
}
