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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javax.imageio.ImageIO;
import siscon.entidades.Cidade;
import siscon.entidades.Associado;
import siscon.entidades.CategoriaEvento;
import siscon.entidades.Dependente;
import siscon.entidades.Evento;
import siscon.entidades.EventoParticipante;
import siscon.entidades.Funcionario;
import siscon.entidades.Movimentacao;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrEvento {
    
    private Evento evento;

    public CtrEvento() {
        evento = new Evento();
    }

    public Evento getEvento() {
        return evento;
    }
    
    public void setEvento(Evento evento)
    {
        this.evento = evento;
    }
    public void novo()
    {
        evento = new Evento();
    }
    
    public void CarregarDados(int cod)
    {
        evento = evento.getEvento(cod);
        evento.carregar_participantes();
    }
    
    public ArrayList <Evento> ListarEventos(String filtro, String categoria)
    {
        String aux = ""; 
        
        
        if(!filtro.equals(""))
        {
            if(Funcoes.isInt(filtro)) //pesquisar por código da conta
                aux = "eve_cod = "+filtro;
            else //descrição ou pessoa
                aux = "(upper(eve_descricao) LIKE '%"+filtro.toUpperCase()+"%'"+ " OR upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%')";
        }
        
        if(!categoria.equals("Exibir todos"))
        {
            if(!aux.equals(""))
               aux = aux + " and ";
            aux += "cat_cod = "+evento.getCategoria().getCategoria(categoria).getCod();
        }
        
        return evento.getEventos(aux);
    } 
    
    public ArrayList <String> ListarCategorias(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        CategoriaEvento cat = new CategoriaEvento();
        
        ArrayList <CategoriaEvento> categorias = cat.getCategorias(filtro);
        for(int i=0; i<categorias.size();i++)
            lista.add(categorias.get(i).getDescricao());
        return lista;
    }
    
    public void AdicionarParticipante (TableView <EventoParticipante> tabela, int cod_part, String autorizado, LocalDate data_inicio, LocalDate data_fim)
    {
        for(int i=0; i < tabela.getItems().size(); i++)
            if(tabela.getItems().get(i).getParticipante().getCod() == cod_part) //já foi adicionado anteriormente
            { //erro
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Atenção");
                alert.setContentText("Este participante já foi adicionado.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
                alert.showAndWait();
                return;
            }
        tabela.getItems().add(new EventoParticipante(evento.getResponsavel().getPessoa(cod_part), autorizado, data_inicio, data_fim));
    }
    
    public ArrayList <EventoParticipante> AlterarParticipante(ObservableList <EventoParticipante> parcs, int indice, String autorizado, LocalDate data_inicio, LocalDate data_fim)
    {
        return evento.alterar_participante(parcs, indice, autorizado, data_inicio, data_fim);
    }   
    
    public boolean SalvarParticipantes(List <EventoParticipante> participantes)
    {
        evento.setParticipantes(new ArrayList <EventoParticipante>(participantes));        
        return true;
    }
    
    public ArrayList <EventoParticipante> AjustarParticipantes(ObservableList <EventoParticipante> parcs, ObservableList indices, LocalDate data_inicio, LocalDate data_fim)
    {
        return evento.ajustar_participantes(parcs, indices, data_inicio, data_fim);
    }
    
    public boolean Registrar(String status, String descricao, String categoria, int responsavel, LocalDate data_inicio, LocalDate data_fim, String observacao)
    {
        if(!evento.validar_participantes(evento.getParticipantes(), data_inicio, data_fim))
           return false;
        evento.setStatus(status);
        evento.setDescricao(descricao);
        evento.setCategoria(evento.getCategoria().getCategoria(categoria));
        evento.setResponsavel(evento.getResponsavel().getPessoa(responsavel));
        evento.setData_inicial(data_inicio);
        evento.setData_final(data_fim);
        evento.setObservacao(observacao);
        if(!evento.registrar())
            return false;
        
        return evento.adicionar_participantes();        
    }
    
    public boolean Alterar(String status, String descricao, String categoria, int responsavel, LocalDate data_inicio, LocalDate data_fim, String observacao)
    {
        if(!evento.validar_participantes(evento.getParticipantes(), data_inicio, data_fim))
           return false;
        evento.setStatus(status);
        evento.setDescricao(descricao);
        evento.setCategoria(evento.getCategoria().getCategoria(categoria));
        evento.setResponsavel(evento.getResponsavel().getPessoa(responsavel));
        evento.setData_inicial(data_inicio);
        evento.setData_final(data_fim);
        evento.setObservacao(observacao);
        if(!evento.alterar())
            return false;
        
        if(!evento.remover_participantes())
            return false;
        
        return evento.adicionar_participantes();        
    }
    
    public boolean Desativar()
    {
        return evento.desativar();
    }
    
    
    
}
