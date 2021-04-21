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
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class EventoParticipante {
    private Evento evento;
    private Pessoa participante;
    private String autorizado, data_inicio, data_fim, periodo;

    public EventoParticipante() {
        evento = new Evento();
        participante = new Pessoa();
        autorizado = data_inicio = periodo = data_fim  = "";     
    }

    public EventoParticipante(Pessoa participante, String autorizado, LocalDate data_inicio, LocalDate data_fim) {
        this.participante = participante;
        setAutorizado(autorizado);
        this.data_inicio = Funcoes.DateToString(data_inicio);
        this.data_fim = Funcoes.DateToString(data_fim);
        setPeriodo();
    }
    
    public EventoParticipante(Evento evento, Pessoa participante, String autorizado, LocalDate data_inicio, LocalDate data_fim) {
        this.evento = evento;
        this.participante = participante;
        setAutorizado(autorizado);
        this.data_inicio = Funcoes.DateToString(data_inicio);
        this.data_fim = Funcoes.DateToString(data_fim); 
        setPeriodo();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo() {
        if(data_inicio.equals(data_fim))
           this.periodo = data_inicio;
        else
           this.periodo = data_inicio + "-" + data_fim;        
    }
    public Pessoa getParticipante() {
        return participante;
    }

    public void setParticipante(Pessoa participante) {
        this.participante = participante;
    }

    public String getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(String autorizado) {
        if(autorizado.equals("S"))
           this.autorizado = "Sim";
        else
          if(autorizado.equals("Sim"))
             this.autorizado = "S";
        
        if(autorizado.equals("N"))
           this.autorizado = "Não";
        else
          if(autorizado.equals("Não"))
             this.autorizado = "N";
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }
    
    public String toString()
    {
        return participante.getNome();
    }
    
    public ArrayList <EventoParticipante> getEventoParticipantes(int codigo_evento)
    {
        ArrayList <EventoParticipante> ep = new ArrayList();
        Pessoa p = new Pessoa();
        Evento ev = new Evento();
        String sql = "select * from eventoparticipantes INNER JOIN pessoa ON eventoparticipantes.pes_cod = pessoa.pes_cod WHERE eve_cod = "+codigo_evento;
        sql += " ORDER BY pes_nome, ep_datainicio";    
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {         
                p = p.getPessoa(rs.getInt("pes_cod"));
                ev = ev.getEvento(rs.getInt("eve_cod"));
                ep.add(new EventoParticipante(ev, p, rs.getString("ep_autorizado"), rs.getDate("ep_datainicio").toLocalDate(),rs.getDate("ep_datafim").toLocalDate()));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return ep;
    } 
    
    public boolean inserir() //
    {
        String aut = "N";
        if(autorizado.equals("Sim"))
            aut = "S";
        List <String> campos = new ArrayList<>(Arrays.asList("eve_cod", "pes_cod", "ep_autorizado","ep_datainicio","ep_datafim"));
        List <String> valores = new ArrayList<>(Arrays.asList(evento.getCod()+"", participante.getCod()+"", aut, Funcoes.StringToDate(data_inicio)+"", Funcoes.StringToDate(data_fim)+""));
        
        return Banco.con.cmdInsert("eventoparticipantes", campos, valores);       
        
    }
    
    
    
    
}
