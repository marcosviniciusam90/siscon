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
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import siscon.controladorasinterface.CtrGerenciarEmpresas;
import siscon.entidades.Empresa;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author MarcosVinícius
 */
public class SelecionarEmpresaController implements Initializable {
    @FXML
    private Button btConfirmar;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colTelefone;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfEndereco;
    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfUF;
    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfBairro;
    
    public static int empresa;
    
    @FXML
    private ImageView img;
    private CtrGerenciarEmpresas ctrGerenciarEmpresas = new CtrGerenciarEmpresas();
    @FXML
    private AnchorPane apDadosCadastrais;
    @FXML
    private TableColumn colCNPJ;
    @FXML
    private TextField tfCNPJ;
    @FXML
    private TableView<Empresa> tabela;
    @FXML
    private TextField tfPesquisarCNPJ;

    
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
        
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colCNPJ.setCellValueFactory(new PropertyValueFactory("cnpj"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("fone"));
        MaskCampos.cnpjField(tfCNPJ);
        MaskCampos.cnpjField(tfPesquisarCNPJ);
        
        ctrGerenciarEmpresas.CarregarTabelaEmpresas(tabela, tfPesquisar.getText());
        EstadoOriginal(); 
    } 
    
    private void EstadoOriginal()
    {
        apDadosCadastrais.setDisable(true);
        tabela.getSelectionModel().clearSelection();
        empresa = 0;
        btConfirmar.setDisable(true);
        tfBairro.setText("");
        tfCNPJ.setText("");
        tfCidade.setText("");
        tfEndereco.setText("");
        tfNome.setText("");
        tfTelefone.setText("");
        tfUF.setText("");
        java.net.URL url = getClass().getResource("imagens/logotipo.png");
        img.setImage(new Image(url.toString()));
        img.setId("1"); 
    }
    
    @FXML
    private void clickConfirmar(ActionEvent event) throws IOException {        
        if(tabela.getSelectionModel().getSelectedItem() != null)
        {
           empresa = tabela.getSelectionModel().getSelectedItem().getCod();
           PrincipalController.TelaSelecionarEmpresa.close();
        }
        else
        {
            empresa = 0;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Nenhuma empresa selecionada.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());
            alert.showAndWait();
        }
        
    }
    
    private void Pesquisar()
    {
        EstadoOriginal();        
        
        String filtro = tfPesquisar.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        filtro = filtro.replace("/", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cnpj
        {
            tfPesquisarCNPJ.setText(filtro);
            ctrGerenciarEmpresas.CarregarTabelaEmpresas(tabela, tfPesquisarCNPJ.getText());
        }
        else
            ctrGerenciarEmpresas.CarregarTabelaEmpresas(tabela, tfPesquisar.getText());
    }

    @FXML
    private void clickPesquisar(ActionEvent event) 
    {
        Pesquisar();
    }
    
    private void ClickTabela()
    {
        apDadosCadastrais.setDisable(tabela.getSelectionModel().getSelectedItem() == null);
        
        if(tabela.getSelectionModel().getSelectedItem() != null)
        {
           btConfirmar.setDisable(false);
           tfBairro.setText(tabela.getSelectionModel().getSelectedItem().getBairro());
           tfCNPJ.setText(tabela.getSelectionModel().getSelectedItem().getCnpj());
           tfCidade.setText(tabela.getSelectionModel().getSelectedItem().getCidade().getNome());
           tfEndereco.setText(tabela.getSelectionModel().getSelectedItem().getTipoLog()+" "+tabela.getSelectionModel().getSelectedItem().getLogradouro()+", "+tabela.getSelectionModel().getSelectedItem().getNumero());
           if(tfEndereco.getText().contains("null") || tfEndereco.getText().equals(" , "))
               tfEndereco.setText("");
           tfNome.setText(tabela.getSelectionModel().getSelectedItem().getNome());
           tfTelefone.setText(tabela.getSelectionModel().getSelectedItem().getFone());
           tfUF.setText(tabela.getSelectionModel().getSelectedItem().getCidade().getEstado().getSigla());
           
           
           java.net.URL url = getClass().getResource("imagens/logotipo.png");
           img.setImage(new Image(url.toString()));
           img.setId("1"); 
           //img.setVisible(false);
           
           //Ler Imagem
           BufferedImage bimg = ctrGerenciarEmpresas.getCtrempresa().LerImagem(tabela.getSelectionModel().getSelectedItem().getCod());
           if(bimg != null)
           {
              img.setImage(SwingFXUtils.toFXImage(bimg, null));
              img.setId("0");
           }
        }
    }

    @FXML
    private void clickTabela(MouseEvent event) {
        ClickTabela();
            
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        if(!tfNome.getText().equals(""))
            EstadoOriginal();
        else
        {
            empresa = 0;
            PrincipalController.TelaSelecionarEmpresa.close();
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
