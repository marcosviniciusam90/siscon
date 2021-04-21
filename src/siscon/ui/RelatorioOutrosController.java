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
public class RelatorioOutrosController implements Initializable {

    
    @FXML
    private AnchorPane apRelatorio;
    @FXML
    private AnchorPane apMenu;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btGerar;    
    @FXML
    private AnchorPane apRelatorioCategoriasVeiculos;
    @FXML
    private RadioButton rbCores;
    @FXML
    private RadioButton rbMarcas;
    private ToggleGroup grupoFormato;    
    private ToggleGroup grupoFormatoConexoes;    
    @FXML
    private RadioButton rbCategoriasEventos;
    @FXML
    private RadioButton rbCategoriasVeiculos;
    @FXML
    private AnchorPane apRelatorioConexoes;
    @FXML
    private AnchorPane apRelatorioCategoriasVeiculos1;
    @FXML
    private RadioButton rbTipoCliente;
    @FXML
    private RadioButton rbTipoServidor;
    @FXML
    private RadioButton rbTipoTodasConexoes;
    @FXML
    private CheckBox CONEXOESckbFiltrarPeriodo;
    @FXML
    private AnchorPane CONEXOESapPeriodo;
    @FXML
    private DatePicker CONEXOESdpDataInicial;
    @FXML
    private DatePicker CONEXOESdpDataFinal;
    @FXML
    private Button btMenuRelConexoes;
    @FXML
    private Button btMenuRelEmpresas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
        ctr.getCtrpessoa().setUsuario(PrincipalController.usuario);
        if(ctr.getCtrpessoa().getUsuario().getNivel() == 2) //básico
        {
            btMenuRelConexoes.setVisible(false);
            btMenuRelEmpresas.setLayoutY(92);
            apMenu.setLayoutY(90);
        }
        
        rbCategoriasVeiculos.setTooltip(new Tooltip("Exibe uma lista de categorias de veículos."));
        rbCores.setTooltip(new Tooltip("Exibe uma lista de cores de veículos."));
        rbMarcas.setTooltip(new Tooltip("Exibe uma lista de marcas de veículos."));
        rbCategoriasEventos.setTooltip(new Tooltip("Exibe uma lista de categorias de eventos."));
        btMenuRelConexoes.setTooltip(new Tooltip("Exibe uma lista de conexões realizadas ao banco de dados."));
        grupoFormato = new ToggleGroup();
        grupoFormatoConexoes = new ToggleGroup();
        rbCategoriasVeiculos.setToggleGroup(grupoFormato);    
        rbCategoriasEventos.setToggleGroup(grupoFormato); 
        rbCores.setToggleGroup(grupoFormato); 
        rbMarcas.setToggleGroup(grupoFormato);     
        
        rbTipoCliente.setToggleGroup(grupoFormatoConexoes);   
        rbTipoServidor.setToggleGroup(grupoFormatoConexoes);   
        rbTipoTodasConexoes.setToggleGroup(grupoFormatoConexoes);   
    } 
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        TelaRelatorio(false); //volta pro menu
        
    }
 
    @FXML
    private void clkGerar(ActionEvent event) 
    {
        
        String query  = "";
        
        if(rbCategoriasEventos.isSelected())
        {        
            query = "select categoriaevento.cat_cod, cat_descricao, count(distinct(eve_cod)) as qtde_categorias from categoriaevento LEFT JOIN evento ON evento.cat_cod = categoriaevento.cat_cod " +
                    "WHERE cat_status = 'A'" +
                    "GROUP BY categoriaevento.cat_cod";
            Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_CategoriasEventos.jasper", "Relatório de Categorias de Eventos","");   
        }
        
        if(rbCategoriasVeiculos.isSelected())
        {        
            query = "select categoriaveiculo.cat_cod, cat_descricao, count(distinct(vei_cod)) as qtde_categorias from categoriaveiculo LEFT JOIN veiculo ON veiculo.cat_cod =categoriaveiculo.cat_cod " +
                    "WHERE cat_status = 'A'" +
                    "GROUP BY categoriaveiculo.cat_cod";
            Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_CategoriasVeiculos.jasper", "Relatório de Categorias de Veículos","");   
        }
        if(rbMarcas.isSelected())
        {        
            query = "select marca.mar_cod, mar_nome, count(distinct(vei_cod)) as qtde_veiculos from marca LEFT JOIN veiculo ON veiculo.mar_cod = marca.mar_cod " +
                    "WHERE mar_status = 'A' " +
                    "GROUP BY marca.mar_cod";
            Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_Marcas.jasper", "Relatório de Marcas de Veículos","");   
        }
        if(rbCores.isSelected())
        {        
            query = "select cor.cor_cod, cor_nome, count(distinct(vei_cod)) as qtde_cores from cor LEFT JOIN veiculo ON veiculo.cor_cod =cor.cor_cod " +
                    "WHERE cor_status = 'A' " +
                    "GROUP BY cor.cor_cod";
            Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_Cores.jasper", "Relatório de Cores de Veículos","");   
        }
        
            
        
    }
    
    
    
    private void TelaRelatorio(boolean status)
    {
        apRelatorio.setVisible(status);
        apRelatorioConexoes.setVisible(status);
        //btGerar.setVisible(status);
        //btCancelar.setVisible(status);        
        apMenu.setVisible(!status);
        if(!status)
        {   //estado original
            //PrincipalController.TelaRelatorioOutros.setTitle("Outros Relatórios");
            rbCategoriasEventos.setSelected(true);
            rbTipoTodasConexoes.setSelected(true);
            CONEXOESckbFiltrarPeriodo.setSelected(false);
            CONEXOESapPeriodo.setDisable(true);
            CONEXOESdpDataInicial.setValue(null);
            CONEXOESdpDataFinal.setValue(null);
            CONEXOESckbFiltrarPeriodo.setTextFill(Color.BLACK);
        }
    }


    

    @FXML
    private void clkMenuRelCategoriasVeiculos(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorio.setVisible(true);
        apRelatorioConexoes.setVisible(false);
    }

    @FXML
    private void clkMenuRelEmpresas(ActionEvent event) {
        String query = "select * from empresa WHERE emp_status = 'A'";
        Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_Empresas.jasper", "Relatório de Empresas","");   
    }

    @FXML
    private void clkMenuRelConexoes(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorio.setVisible(false);
        apRelatorioConexoes.setVisible(true);
    }

    @FXML
    private void CONEXOESclkFiltrarPeriodo(ActionEvent event) {
        CONEXOESapPeriodo.setDisable(!CONEXOESckbFiltrarPeriodo.isSelected());
        if(CONEXOESckbFiltrarPeriodo.isSelected())
            CONEXOESdpDataInicial.requestFocus();
    }
    
    @FXML
    private void clkGerarConexoes(ActionEvent event) {
        if(CONEXOESckbFiltrarPeriodo.isSelected() && (CONEXOESdpDataInicial.getValue() == null || CONEXOESdpDataFinal.getValue() == null || CONEXOESdpDataInicial.getValue().isAfter(LocalDate.now()) || CONEXOESdpDataInicial.getValue().isAfter(CONEXOESdpDataFinal.getValue())))
        {
            CONEXOESckbFiltrarPeriodo.setTextFill(Color.RED);
            CONEXOESckbFiltrarPeriodo.requestFocus();
            return;
        }
        CONEXOESckbFiltrarPeriodo.setTextFill(Color.BLACK);
        //validei
        
        String subtitulo = "";
        String condicao = "";
        
        if(rbTipoCliente.isSelected())
        {
            subtitulo += "Conexões remotas";
            condicao += "con_tipo = 'R'";            
        }
        if(rbTipoServidor.isSelected())
        {
            subtitulo += "Conexões locais";
            condicao += "con_tipo = 'L'";            
        }
        
        if(CONEXOESckbFiltrarPeriodo.isSelected())
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Período: "+Funcoes.DateToString(CONEXOESdpDataInicial.getValue())+" a "+Funcoes.DateToString(CONEXOESdpDataFinal.getValue());    
            if(!condicao.equals(""))
                condicao += " and ";
            condicao += "con_horario >= '"+CONEXOESdpDataInicial.getValue()+"' and con_horario < '"+CONEXOESdpDataFinal.getValue().plusDays(1)+"'";         
        }
            
        String query = "select * from conexao";
        if(!condicao.equals(""))
           query += " where "+condicao;
        Funcoes.gerarRelatorio(query,"relatorios/outros/Rel_Conexoes.jasper", "Relatório de Conexões ao Banco de Dados",subtitulo);  
    }

    
    

    

    
    
}
