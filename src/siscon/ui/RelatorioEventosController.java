/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RelatorioEventosController implements Initializable {

    @FXML
    private TextField tfPessoa;
    @FXML
    private Button btSelecionar;
    @FXML
    private RadioButton rbFormatoPadrao;
    private ToggleGroup grupoFormato;
    private ToggleGroup grupoTipo;    
    @FXML
    private RadioButton rbTipoTodos;
    @FXML
    private RadioButton rbFormatoCategoria;    
    @FXML
    private RadioButton rbFormatoOrganizador;
    @FXML
    private RadioButton rbFormatoEvento;
    @FXML
    private RadioButton rbTipoAndamento;
    @FXML
    private RadioButton rbTipoFinalizado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFormatoEvento.setTooltip(new Tooltip("Exibe os eventos e seus participantes."));
        rbFormatoCategoria.setTooltip(new Tooltip("Exibe os eventos agrupados por categoria."));
        rbFormatoOrganizador.setTooltip(new Tooltip("Exibe os eventos agrupados por organizador."));
        rbFormatoPadrao.setTooltip(new Tooltip("Exibe uma lista de eventos."));
        SelecionarPessoaController.pessoa = 0;
        grupoTipo = new ToggleGroup();
        rbTipoTodos.setToggleGroup(grupoTipo);
        rbTipoAndamento.setToggleGroup(grupoTipo);
        rbTipoFinalizado.setToggleGroup(grupoTipo);
        
        grupoFormato = new ToggleGroup();
        rbFormatoCategoria.setToggleGroup(grupoFormato);
        rbFormatoEvento.setToggleGroup(grupoFormato);
        rbFormatoPadrao.setToggleGroup(grupoFormato);
        rbFormatoOrganizador.setToggleGroup(grupoFormato);
        
    }    

    
    @FXML
    private void clkSelecionar(ActionEvent event) throws IOException {
        if(btSelecionar.getText().equals("SELECIONAR"))
        {
            SelecionarPessoaController.pessoa = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Organizador");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            PrincipalController.TelaSelecionarPessoa = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();
            
            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            {      
                
                CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                tfPessoa.setText(ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome());
                btSelecionar.setText("REMOVER");
                ModelosRelatorios();
            }
        }
        else //remover
        {
            
            SelecionarPessoaController.pessoa = 0;
            tfPessoa.setText("");
            btSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
        }
    }
    
   
    @FXML
    private void clkCancelar(ActionEvent event) {
        PrincipalController.TelaRelatorioEventos.close();
    }
 
    @FXML
    private void clkGerar(ActionEvent event) {
        
        String condicao = "eve_status <> 'D'";
        String subtitulo = "";
        
        
        if(rbTipoAndamento.isSelected())
        {
            subtitulo += "Eventos em andamento";
            condicao += " and eve_status = 'A'";
        }
        if(rbTipoFinalizado.isSelected())
        {
            subtitulo += "Eventos finalizados";
            condicao += " and eve_status = 'F'";
        }
        
        if(SelecionarPessoaController.pessoa != 0)
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Organizador: "+tfPessoa.getText().split(" ")[0];
            if(!condicao.equals(""))
               condicao += " and ";
            condicao += "pessoa.pes_cod = "+SelecionarPessoaController.pessoa;
        }
        
        
        //MODELOS de Relatórios
        String query = "";        
        if(!rbFormatoEvento.isSelected())
        {
            query = "select * from evento INNER JOIN categoriaevento ON evento.cat_cod = categoriaevento.cat_cod INNER JOIN pessoa ON evento.pes_cod = pessoa.pes_cod";
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
             
             if(rbFormatoCategoria.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/eventos/Rel_EventosCategoria.jasper", "Relatório de Eventos por Categoria",subtitulo);
             if(rbFormatoOrganizador.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/eventos/Rel_EventosOrganizador.jasper", "Relatório de Eventos por Organizador",subtitulo);
             if(rbFormatoPadrao.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/eventos/Rel_Eventos.jasper", "Relatório de Eventos",subtitulo);
        }
        else
        {
            
            query = "select * from evento INNER JOIN categoriaevento ON evento.cat_cod = categoriaevento.cat_cod INNER JOIN pessoa ON evento.pes_cod = pessoa.pes_cod LEFT JOIN eventoparticipantes ON evento.eve_cod = eventoparticipantes.eve_cod " +
                    "LEFT JOIN " +
                    "(select pes_cod as par_cod, pes_nome as par_nome, pes_fone as par_fone, pes_interfone as par_interfone from pessoa) Part ON eventoparticipantes.pes_cod = par_cod";
            query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/eventos/Rel_EventosParticipantes.jasper", "Relatório de Eventos X Participantes",subtitulo);
             
        }
        
        
    }
    
    private void ModelosRelatorios()
    {
        rbFormatoCategoria.setDisable(!tfPessoa.getText().equals(""));
        rbFormatoEvento.setDisable(!tfPessoa.getText().equals(""));
        rbFormatoOrganizador.setDisable(!tfPessoa.getText().equals(""));
        rbFormatoPadrao.setDisable(!tfPessoa.getText().equals(""));
        
        
        
        if(!tfPessoa.getText().equals(""))
        {
            rbFormatoOrganizador.setDisable(false);
            rbFormatoEvento.setDisable(false);
            if(!rbFormatoEvento.isSelected())
               rbFormatoOrganizador.setSelected(true);
        }
        
    }

    
}
