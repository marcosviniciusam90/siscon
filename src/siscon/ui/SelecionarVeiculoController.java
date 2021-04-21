/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.SpinnerNumberModel;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import siscon.entidades.Veiculo;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author MarcosVinícius
 */
public class SelecionarVeiculoController implements Initializable {
    @FXML
    private Button btConfirmar;
    @FXML
    private TextField tfPesquisar;
    
    @FXML
    private Button btCancelar;
    public static int veiculo;
    @FXML
    private TextField tfPesquisarCPF;
    private CtrGerenciarVeiculos ctrGerenciarVeiculos = new CtrGerenciarVeiculos();
    @FXML
    private ComboBox<String> cbFiltrarCategoria;
    @FXML
    private TableView<Veiculo> tabelaPESSOAS;
    @FXML
    private AnchorPane apDadosCadastrais;
    @FXML
    private Label lbObservacao;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colCor;
    @FXML
    private TableColumn colPlaca;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TextField tfPlaca;
    @FXML
    private TextField tfProprietario;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
        ObservableList <Node> componentes = apDadosCadastrais.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(false);
                ((TextInputControl)n).setFocusTraversable(false);
                ((TextInputControl)n).setMouseTransparent(true);
            }
        }
        
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colCor.setCellValueFactory(new PropertyValueFactory("cor"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
        
        cbFiltrarCategoria.setId("VEICULOScbFiltrarCategoria");
        
        ctrGerenciarVeiculos.CarregarComboBox(cbFiltrarCategoria, "categoriaveiculo", "");        
          
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(tabelaPESSOAS, tfPesquisar.getText(), cbFiltrarCategoria.getValue());
        EstadoOriginal(); 
    } 
    
    private void EstadoOriginal()
    {
        apDadosCadastrais.setDisable(true);
        tabelaPESSOAS.getSelectionModel().clearSelection();
        veiculo = 0;
        btConfirmar.setDisable(true);
        tfDescricao.setText("");
        tfPlaca.setText("");
        tfProprietario.setText("");
        
    }
    
    @FXML
    private void clickConfirmar(ActionEvent event) throws IOException {        
        if(tabelaPESSOAS.getSelectionModel().getSelectedItem() != null)
        {
           veiculo = tabelaPESSOAS.getSelectionModel().getSelectedItem().getCod();
           RelatorioMovimentacoesController.TelaSelecionarVeiculo.close();
        }
        else
        {
            veiculo = 0;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Nenhum veículo selecionado.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());
            alert.showAndWait();
        }
        
    }
    
    private void Pesquisar()
    {
        EstadoOriginal();        
        
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(tabelaPESSOAS, tfPesquisar.getText(), cbFiltrarCategoria.getValue());
    }

    @FXML
    private void clickPesquisar(ActionEvent event) 
    {
        Pesquisar();
    }
    
    private void ClickTabela()
    {
        apDadosCadastrais.setDisable(tabelaPESSOAS.getSelectionModel().getSelectedItem() == null);
        if(tabelaPESSOAS.getSelectionModel().getSelectedItem() != null)
        {
           btConfirmar.setDisable(false);
           tfDescricao.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getMarca()+" "+tabelaPESSOAS.getSelectionModel().getSelectedItem().getModelo()+" "+tabelaPESSOAS.getSelectionModel().getSelectedItem().getCor());
           tfPlaca.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getPlaca());
           
           tfProprietario.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getPessoa().getNome()+" ("+tabelaPESSOAS.getSelectionModel().getSelectedItem().getPessoa().getCpf()+")");
           if(tfProprietario.getText().contains("()"))
               tfProprietario.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getPessoa().getNome());
           
        }
    }

    @FXML
    private void clickTabela(MouseEvent event) {
        ClickTabela();
            
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        if(!tfDescricao.getText().equals(""))
            EstadoOriginal();
        else
        {
            veiculo = 0;
            RelatorioMovimentacoesController.TelaSelecionarVeiculo.close();
        }
    }

    @FXML
    private void kpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           Pesquisar();
    }

    @FXML
    private void kpTabela(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           ClickTabela();
    }

   
    
}
