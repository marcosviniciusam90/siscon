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
public class RelatorioVeiculosController implements Initializable {

    @FXML
    private TextField tfPessoa;
    @FXML
    private Button btSelecionar;
    @FXML
    private RadioButton rbFormatoPadrao;
    private ToggleGroup grupoFormato;
    private ToggleGroup grupoTipo;
    @FXML
    private TextField tfVeiculo;
    @FXML
    private Button btSelecionarVeiculo;
    @FXML
    private RadioButton rbTipoTodos;
    @FXML
    private RadioButton rbTipoDentro;
    @FXML
    private RadioButton rbTipoFora;
    @FXML
    private RadioButton rbFormatoCategoria;
    @FXML
    private RadioButton rbFormatoCor;
    @FXML
    private RadioButton rbFormatoMarca;
    @FXML
    private RadioButton rbFormatoVeiculo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFormatoVeiculo.setTooltip(new Tooltip("Exibe os veículos e seus condutores."));    
        rbFormatoPadrao.setTooltip(new Tooltip("Exibe uma lista de veículos.")); 
        rbFormatoMarca.setTooltip(new Tooltip("Exibe os veículos agrupados por marca.")); 
        rbFormatoCor.setTooltip(new Tooltip("Exibe os veículos agrupados por cor.")); 
        rbFormatoCategoria.setTooltip(new Tooltip("Exibe os veículos agrupados por categoria.")); 
        SelecionarPessoaController.pessoa = 0;
        SelecionarVeiculoController.veiculo = 0;
        grupoTipo = new ToggleGroup();
        rbTipoTodos.setToggleGroup(grupoTipo);
        rbTipoFora.setToggleGroup(grupoTipo);
        rbTipoDentro.setToggleGroup(grupoTipo);
        
        grupoFormato = new ToggleGroup();
        rbFormatoCategoria.setToggleGroup(grupoFormato);
        rbFormatoCor.setToggleGroup(grupoFormato);
        rbFormatoPadrao.setToggleGroup(grupoFormato);
        rbFormatoMarca.setToggleGroup(grupoFormato);
        rbFormatoVeiculo.setToggleGroup(grupoFormato);
        
    }    

    
    @FXML
    private void clkSelecionar(ActionEvent event) throws IOException {
        if(btSelecionar.getText().equals("SELECIONAR"))
        {
            SelecionarPessoaController.pessoa = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Proprietário");
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
                //ModelosRelatorios();
            }
        }
        else //remover
        {
            
            SelecionarPessoaController.pessoa = 0;
            tfPessoa.setText("");
            btSelecionar.setText("SELECIONAR");
            //ModelosRelatorios();
        }
    }
    
   
    @FXML
    private void clkCancelar(ActionEvent event) {
        PrincipalController.TelaRelatorioVeiculos.close();
    }
 
    @FXML
    private void clkGerar(ActionEvent event) {
        
        String condicao = "vei_status = 'A'";
        String subtitulo = "";
        
        
        if(rbTipoDentro.isSelected())
        {
            subtitulo += "Véiculos dentro do condomínio";
            condicao += " and vei_ultimamov = 'E'";
        }
        if(rbTipoFora.isSelected())
        {
            subtitulo += "Véiculos fora do condomínio";
            condicao += " and vei_ultimamov = 'S'";
        }
        
        if(SelecionarPessoaController.pessoa != 0)
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Proprietário: "+tfPessoa.getText().split(" ")[0];
            if(!condicao.equals(""))
               condicao += " and ";
            condicao += "cod = "+SelecionarPessoaController.pessoa;
        }
        
        if(SelecionarVeiculoController.veiculo != 0)
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Veículo: "+tfVeiculo.getText();
            if(!condicao.equals(""))
               condicao += " and ";
            condicao += "vei_cod = "+SelecionarVeiculoController.veiculo;
        }
        
        //MODELOS de Relatórios
        String query = "";        
        if(!rbFormatoVeiculo.isSelected())
        {
            query = "select * from " +
                    "(select * from veiculo INNER JOIN marca ON veiculo.mar_cod = marca.mar_cod " +
                    "INNER JOIN cor ON veiculo.cor_cod = cor.cor_cod " +
                    "INNER JOIN categoriaveiculo ON categoriaveiculo.cat_cod = veiculo.cat_cod " +
                    "INNER JOIN (select pes_cod as cod, pes_nome from pessoa) P ON veiculo.pes_cod = cod) T1";
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
             
             if(rbFormatoCategoria.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/veiculos/Rel_VeiculosCategoria.jasper", "Relatório de Veículos por Categoria",subtitulo);
             if(rbFormatoCor.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/veiculos/Rel_VeiculosCor.jasper", "Relatório de Veículos por Cor",subtitulo);
             if(rbFormatoMarca.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/veiculos/Rel_VeiculosMarca.jasper", "Relatório de Veículos por Marca",subtitulo);
             if(rbFormatoPadrao.isSelected())
                Funcoes.gerarRelatorio(query,"relatorios/veiculos/Rel_Veiculos.jasper", "Relatório de Veículos",subtitulo);
        }
        else
        {
            
            query = "select * from " +
                    "(select * from veiculo INNER JOIN marca ON veiculo.mar_cod = marca.mar_cod " +
                    "INNER JOIN categoriaveiculo ON veiculo.cat_cod = categoriaveiculo.cat_cod " +
                    "INNER JOIN cor ON veiculo.cor_cod = cor.cor_cod" +
                    " LEFT JOIN (select pes_cod as cod, pes_nome, pes_categoria from pessoa) P ON veiculo.pes_cod = cod" +
                    " INNER JOIN (select pes_cod as codigo, pes_condutor as condutor, pes_supervisor, pes_nome as nome, pes_cpf as cpf, pes_interfone as interfone, pes_fone as telefone from pessoa where pes_status = 'A') Dep ON cod = Dep.codigo OR cod = Dep.pes_supervisor WHERE condutor LIKE 'Sim') T1";
                    
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
             Funcoes.gerarRelatorio(query,"relatorios/veiculos/Rel_VeiculosVeiculo.jasper", "Relatório de Veículos X Condutores",subtitulo);
             
        }
        
        
    }
    
    private void ModelosRelatorios()
    {
        rbFormatoCategoria.setDisable(!tfVeiculo.getText().equals(""));
        rbFormatoCor.setDisable(!tfVeiculo.getText().equals(""));
        rbFormatoMarca.setDisable(!tfVeiculo.getText().equals(""));
        rbFormatoVeiculo.setDisable(!tfVeiculo.getText().equals(""));
        rbFormatoPadrao.setDisable(!tfVeiculo.getText().equals(""));
        
        rbTipoDentro.setDisable(!tfVeiculo.getText().equals(""));
        rbTipoFora.setDisable(!tfVeiculo.getText().equals(""));
        rbTipoTodos.setDisable(!tfVeiculo.getText().equals(""));
        
        if(!tfVeiculo.getText().equals(""))
        {
            rbFormatoVeiculo.setDisable(false);
            rbFormatoVeiculo.setSelected(true);
            rbTipoTodos.setDisable(false);
            rbTipoTodos.setSelected(true);
        }
        
    }

    @FXML
    private void clkSelecionarVeiculo(ActionEvent event) throws IOException {
        if(btSelecionarVeiculo.getText().equals("SELECIONAR"))
        {
            SelecionarVeiculoController.veiculo = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Veículo");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RelatorioMovimentacoesController.TelaSelecionarVeiculo = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarVeiculo.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();
            
            if(SelecionarVeiculoController.veiculo != 0) //retorn o código da pessoa, 0 se não retornou!
            { 
                CtrGerenciarVeiculos ctr = new CtrGerenciarVeiculos();
                tfVeiculo.setText(ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getMarca()+" "+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getModelo()+" "+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getCor()+" ("+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getPlaca()+")");
                btSelecionarVeiculo.setText("REMOVER");
                ModelosRelatorios();
            }
        }
        else //remover
        {
            
            SelecionarVeiculoController.veiculo = 0;
            tfVeiculo.setText("");
            btSelecionarVeiculo.setText("SELECIONAR");
            ModelosRelatorios();
        }
    }

    

    
    
}
