/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarPessoas;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class AjudaMenuController implements Initializable {

    @FXML
    private Button btCaixa;
    @FXML
    private Button btContas;
    @FXML
    private Button btEventos;
    @FXML
    private Button btMovimentacoes;
    @FXML
    private AnchorPane apMenuPrincipal;
    @FXML
    private AnchorPane apContas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
        ctr.getCtrpessoa().setUsuario(PrincipalController.usuario);
        if(ctr.getCtrpessoa().getUsuario().getNivel() == 2) //básico
        {
            btCaixa.setVisible(false);
            btContas.setVisible(false);
            btEventos.setLayoutY(276);
            btMovimentacoes.setLayoutY(332);
        }
    }    

    @FXML
    private void clkDependentes(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Dependentes");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaDependentes.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkEmpresas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Empresas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaEmpresas.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkPessoas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Pessoas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaPessoas.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkVeiculos(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Veículos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaVeiculos.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkEventos(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Eventos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaEventos.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkMovimentacoes(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Movimentações de E/S");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaMovimentacoes.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkVoltar(ActionEvent event) {
        apContas.setVisible(false);
        apMenuPrincipal.setVisible(true);
    }

    @FXML
    private void clkLancarContaPagar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Contas a Pagar");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaContasPagar.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkLancarContaReceber(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Contas a Receber");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaContasReceber.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkRealizarPagamento(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Pagamento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaPagamento.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkRealizarRecebimento(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Recebimento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaRecebimento.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkCaixa(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Caixa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaCaixa.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkContas(ActionEvent event) {
        apContas.setVisible(true);
        apMenuPrincipal.setVisible(false);
    }
    
}
