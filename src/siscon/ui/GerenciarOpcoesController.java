/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarOpcoes;
import siscon.util.Banco;

/**
 * FXML Controller class
 *
 * @author Antonina
 */
public class GerenciarOpcoesController implements Initializable {

    @FXML
    private TextField txPesquisa;
    @FXML
    private TableView<String> tabela;
    @FXML
    private TableColumn <String,String> colNome;
    @FXML
    private Button btNovo;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btApagar;
    @FXML
    private VBox apDados;
    @FXML
    private AnchorPane aux_dados;
    @FXML
    private Label lbCod;
    @FXML
    private TextField tfCod;
    @FXML
    private Label lbNome;
    @FXML
    private TextField tfNome;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btConfirmar;
    @FXML
    private AnchorPane apEsquerdo;
    private CtrGerenciarOpcoes ctrGerenciarOpcoes = new CtrGerenciarOpcoes();
    private String tipo = "";
    @FXML
    private Label lbCamposObrigatorios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        apDados.setDisable(true);
        apEsquerdo.setDisable(false);

        btAlterar.setDisable(true);
        btApagar.setDisable(true);
        ctrGerenciarOpcoes.CarregarTabela(tabela, txPesquisa.getText(), PrincipalController.TelaGerenciarOpcoes.getTitle());
        btConfirmar.setTooltip(new Tooltip());
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Marcas"))
           tipo = "marca";
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Categorias"))
           tipo = "categoria";
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Cores"))
           tipo = "cor";
    }  
   
    private void Pesquisar()
    {
        btAlterar.setDisable(true);
        btApagar.setDisable(true);
        tabela.getSelectionModel().clearSelection();
        if(txPesquisa.getText().equals(""))
            ctrGerenciarOpcoes.CarregarTabela(tabela, txPesquisa.getText(), PrincipalController.TelaGerenciarOpcoes.getTitle());
        else
        {
            String filtro = "";
            if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Marcas"))
               filtro = "upper(mar_nome) LIKE '%"+txPesquisa.getText().toUpperCase()+"%'";
            if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Categorias"))
               filtro = "upper(cat_descricao) LIKE '%"+txPesquisa.getText().toUpperCase()+"%'";
            if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Cores"))
               filtro = "upper(cor_nome) LIKE '%"+txPesquisa.getText().toUpperCase()+"%'";
            ctrGerenciarOpcoes.CarregarTabela(tabela, filtro, PrincipalController.TelaGerenciarOpcoes.getTitle());
        }                
    }
    
    @FXML
    private void kpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           Pesquisar();
        
    }

    @FXML
    private void clkIr(ActionEvent event) {
        Pesquisar();
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null)
        {
            btAlterar.setDisable(false);
            btApagar.setDisable(false);
        }
    }
    
    private void Novo()
    {
        lbCamposObrigatorios.setVisible(true);
        btConfirmar.getTooltip().setText("Cadastrar "+tipo);
        apEsquerdo.setDisable(true);
        apDados.setDisable(false);
        btCancelar.setDisable(false);
        btConfirmar.setDisable(false);
        tfCod.setDisable(true);
        //ctrGerenciarOpcoes.Novo();
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        Novo();
    }
    
    private void CarregarDados()
    {
        String codigo = "";
        String nome = "";
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Marcas"))
        {            
            codigo = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getMarca().getMarca(tabela.getSelectionModel().getSelectedItem()).getCod()+"";
            nome = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getMarca().getMarca(tabela.getSelectionModel().getSelectedItem()).getNome();
            ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getMarca().setCod(Integer.parseInt(codigo));
        }
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Categorias"))
        {
            codigo = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCategoria().getCategoria(tabela.getSelectionModel().getSelectedItem()).getCod()+"";
            nome = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCategoria().getCategoria(tabela.getSelectionModel().getSelectedItem()).getDescricao();
            ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCategoria().setCod(Integer.parseInt(codigo));
        }
        if(PrincipalController.TelaGerenciarOpcoes.getTitle().contains("Cores"))
        {
            codigo = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCor().getCor(tabela.getSelectionModel().getSelectedItem()).getCod()+"";
            nome = ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCor().getCor(tabela.getSelectionModel().getSelectedItem()).getNome();
            ctrGerenciarOpcoes.getCtrveiculo().getVeiculo().getCor().setCod(Integer.parseInt(codigo));
        }
        tfCod.setText(codigo);
        tfNome.setText(nome);
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        lbCamposObrigatorios.setVisible(true);
        btConfirmar.getTooltip().setText("Alterar "+tipo);
        CarregarDados();        
        apEsquerdo.setDisable(true);
        apDados.setDisable(false);
        btCancelar.setDisable(false);
        btConfirmar.setDisable(false); 
        tfCod.setDisable(true);
    }

    @FXML
    private void clkApagar(ActionEvent event) {
        lbCamposObrigatorios.setVisible(false);
        btConfirmar.getTooltip().setText("Desativar "+tipo);
        CarregarDados();        
        apEsquerdo.setDisable(true);
        apDados.setDisable(false);
        btCancelar.setDisable(false);
        btConfirmar.setDisable(false); 
        tfCod.setDisable(false);        
        tfCod.setEditable(false);
        tfCod.setFocusTraversable(false);
        tfCod.setMouseTransparent(true);
        tfNome.setEditable(false);
        tfNome.setFocusTraversable(false);
        tfNome.setMouseTransparent(true);
    }
    
    private void estadoOriginal()
    {  
        lbCamposObrigatorios.setVisible(false);
        apEsquerdo.setDisable(false);
        apDados.setDisable(true);
        btConfirmar.setDisable(true);
        btCancelar.setDisable(true);
        btApagar.setDisable(true);
        btAlterar.setDisable(true);
        btNovo.setDisable(false);        
        
        //---------------Limpa Os Textos da Tela---------------------------
        
        
        ObservableList <Node> componentes = aux_dados.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setDisable(false);
                ((TextInputControl)n).setText("");
            }
           
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
            }
            
        }
        tfNome.setEditable(true);
        tfNome.setFocusTraversable(true);
        tfNome.setMouseTransparent(false);
        Pesquisar();
      
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        estadoOriginal();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        if(btConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrGerenciarOpcoes.getCtrveiculo().DesativarOpcao(PrincipalController.TelaGerenciarOpcoes.getTitle()))
                {
                   Banco.con.Commit("Desativar " + tipo+".");
                   estadoOriginal();
                   Pesquisar();
                }
                else
                   Banco.con.Rollback("");
            }
            return;
        }
        
          
        boolean erro = false;


        if(tfNome.getText().equals(""))
        {
            erro = true;
            tfNome.requestFocus();
            lbNome.setTextFill(Color.RED);
        }
        else
            lbNome.setTextFill(Color.BLACK);

        if(erro)
            return;

        Banco.con.IniciarTransacao();
        if(btConfirmar.getTooltip().getText().contains("Cadastrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrGerenciarOpcoes.getCtrveiculo().SalvarOpcao(tfNome.getText(), PrincipalController.TelaGerenciarOpcoes.getTitle());
        }
        else //Alterando
        {                
            erro = !ctrGerenciarOpcoes.getCtrveiculo().AlterarOpcao(Integer.parseInt(tfCod.getText()), tfNome.getText(), PrincipalController.TelaGerenciarOpcoes.getTitle());
        }

        if(!erro)
        {      
           Banco.con.Commit("Cadastrar/alterar "+tipo+".");
           estadoOriginal();
           Pesquisar();
        }
        else
           Banco.con.Rollback("");


        
    }
    
}
