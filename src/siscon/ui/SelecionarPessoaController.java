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
import javafx.scene.control.CheckBox;
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
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.entidades.Pessoa;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author MarcosVinícius
 */
public class SelecionarPessoaController implements Initializable {
    @FXML
    private Button btConfirmar;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colCPF;
    @FXML
    private TableColumn colTelefone;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfCPF;
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
    public static int pessoa;
    @FXML
    private TextField tfPesquisarCPF;
    @FXML
    private ImageView img;
    private CtrGerenciarPessoas ctrGerenciarPessoas = new CtrGerenciarPessoas();
    @FXML
    private ComboBox<String> cbFiltrarCategoria;
    @FXML
    private TableView<Pessoa> tabelaPESSOAS;
    @FXML
    private AnchorPane apDadosCadastrais;
    @FXML
    private Label lbObservacao;
    @FXML
    private CheckBox ckbCondutores;

    
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
        colCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("fone"));
        MaskCampos.cpfField(tfCPF);
        MaskCampos.cpfField(tfPesquisarCPF);
        
        if(RealizarMovimentacaoController.TelaRealizarMovimentacao != null)
           PrincipalController.TelaSelecionarPessoa = RealizarMovimentacaoController.TelaRealizarMovimentacao;
        
        
        if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Responsável"))
           ctrGerenciarPessoas.CarregarComboBox(cbFiltrarCategoria, "", "SUPERVISOR");
        else //proprietarios de veiculos e condutores
           ctrGerenciarPessoas.CarregarComboBox(cbFiltrarCategoria, "", "PESSOA");
        
        if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Organizador"))
           tabelaPESSOAS.setId("ASSOCIADOS");
        else
        if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Responsável"))
           tabelaPESSOAS.setId("POSSIVEISSUPERVISORES");
        else
           if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Condutor"))
           {
              tabelaPESSOAS.setId("CONDUTORES"); //e se a pessoa não for condutora e quiser sair a pé? sistema não permitiria  
              ckbCondutores.setVisible(true);
              tfPesquisar.setPrefWidth(217);
              cbFiltrarCategoria.setPrefWidth(116);
              cbFiltrarCategoria.setLayoutX(245);
              if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Entrada"))  //movimentação de entrada 
              {
                   tabelaPESSOAS.setId("CONDUTORESFORA"); //todas as pessoas q sairam
                   lbObservacao.setText("Movimentação de entrada, somente quem está fora do condomínio será listado.");
              }
              else
                if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Saída"))
                {
                   tabelaPESSOAS.setId("CONDUTORESDENTRO"); //todas as pessoas q entraram
                   lbObservacao.setText("Movimentação de saída, somente quem está dentro do condomínio será listado.");
                }
           }
           else 
             if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Proprietário"))  //proprietários de veículos
                tabelaPESSOAS.setId("SUPERVISORES");   //apenas os supervisores são indicados como proprietários de veículos, dependentes NÃO!
             else
               if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Passageiro"))  //adicionar pessoas disponiveis para movimentação  
               {
                   if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Entrada"))  //movimentação de entrada 
                   {
                      tabelaPESSOAS.setId("FORA"); //todas as pessoas q sairam
                      lbObservacao.setText("Movimentação de entrada, somente quem está fora do condomínio será listado.");
                   }
                   else
                   {
                      tabelaPESSOAS.setId("DENTRO"); //todas as pessoas q entraram
                      lbObservacao.setText("Movimentação de saída, somente quem está dentro do condomínio será listado.");
                   }
               }
               else
                  tabelaPESSOAS.setId("TODOS");
                 
        ctrGerenciarPessoas.CarregarTabela(tabelaPESSOAS, tfPesquisar.getText(), cbFiltrarCategoria.getValue());
        EstadoOriginal(); 
        if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Organizador"))
        {
            cbFiltrarCategoria.setFocusTraversable(false);
            cbFiltrarCategoria.setMouseTransparent(true);
            cbFiltrarCategoria.getSelectionModel().select(1);     //Associado       
        }
        
        PrincipalController.TelaSelecionarPessoa.setTitle(PrincipalController.TelaSelecionarPessoa.getTitle().replace("Condutor", "Pessoa"));
        PrincipalController.TelaSelecionarPessoa.setTitle(PrincipalController.TelaSelecionarPessoa.getTitle().replace("Passageiro", "Pessoa"));
    } 
    
    private void EstadoOriginal()
    {
        apDadosCadastrais.setDisable(true);
        tabelaPESSOAS.getSelectionModel().clearSelection();
        pessoa = 0;
        btConfirmar.setDisable(true);
        tfBairro.setText("");
        tfCPF.setText("");
        tfCidade.setText("");
        tfEndereco.setText("");
        tfNome.setText("");
        tfTelefone.setText("");
        tfUF.setText("");
        java.net.URL url = getClass().getResource("imagens/usuario.png");
        img.setImage(new Image(url.toString()));
        img.setId("1"); 
    }
    
    @FXML
    private void clickConfirmar(ActionEvent event) throws IOException {        
        if(tabelaPESSOAS.getSelectionModel().getSelectedItem() != null)
        {
           pessoa = tabelaPESSOAS.getSelectionModel().getSelectedItem().getCod();
           PrincipalController.TelaSelecionarPessoa.close();
        }
        else
        {
            pessoa = 0;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Nenhuma pessoa selecionada.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());
            alert.showAndWait();
        }
        
    }
    
    private void Pesquisar()
    {
        if(ckbCondutores.isVisible()) //tela de adicionar condutor
        {
            if(ckbCondutores.isSelected())
            {
                if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Entrada"))
                    tabelaPESSOAS.setId("CONDUTORESFORA");
                else
                  if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Saída"))
                    tabelaPESSOAS.setId("CONDUTORESDENTRO");
                  else                
                    tabelaPESSOAS.setId("CONDUTORES");
            }
            else
            {
                if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Entrada"))
                    tabelaPESSOAS.setId("FORA");
                else
                  if(PrincipalController.TelaSelecionarPessoa.getTitle().contains("Saída"))
                    tabelaPESSOAS.setId("DENTRO");
                  else                
                    tabelaPESSOAS.setId("TODOS");
            }
        }
        
        EstadoOriginal();        
        
        String filtro = tfPesquisar.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cpf
        {
            tfPesquisarCPF.setText(filtro);
            ctrGerenciarPessoas.CarregarTabela(tabelaPESSOAS, tfPesquisarCPF.getText(), cbFiltrarCategoria.getValue());
        }
        else
            ctrGerenciarPessoas.CarregarTabela(tabelaPESSOAS, tfPesquisar.getText(), cbFiltrarCategoria.getValue());
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
           tfBairro.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getBairro());
           tfCPF.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getCpf());
           if(tabelaPESSOAS.getSelectionModel().getSelectedItem().getCidade() != null)
           {
                tfCidade.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getCidade().getNome());
                tfUF.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getCidade().getEstado().getSigla());
           }
           else
           {
               tfCidade.setText("");
               tfUF.setText("");
           }
           tfEndereco.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getTipoLog()+" "+tabelaPESSOAS.getSelectionModel().getSelectedItem().getLogradouro()+", "+tabelaPESSOAS.getSelectionModel().getSelectedItem().getNumero());
           if(tfEndereco.getText().contains("null") || tfEndereco.getText().equals(" , "))
               tfEndereco.setText("");
           tfNome.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getNome());
           tfTelefone.setText(tabelaPESSOAS.getSelectionModel().getSelectedItem().getFone());
           
           
           
           java.net.URL url = getClass().getResource("imagens/usuario.png");
           img.setImage(new Image(url.toString()));
           img.setId("1"); 
           //img.setVisible(false);
           
           //Ler Imagem
           BufferedImage bimg = ctrGerenciarPessoas.getCtrpessoa().LerImagem(tabelaPESSOAS.getSelectionModel().getSelectedItem().getCod());
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
            pessoa = 0;
            PrincipalController.TelaSelecionarPessoa.close();
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
