/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class AjudaMovimentacoesController implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label lbRealizarMovimentacao;
    @FXML
    private Label lbVeiculoNaoAparece;
    @FXML
    private Label lbPassageiroNaoConsta;
    @FXML
    private Label lbPessoaNaoEncontrada;
    @FXML
    private Label lbMovimentacaoNaoRealizada;
    @FXML
    private AnchorPane apTexto;
    @FXML
    private Label lbDesfazer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList <Node> componentes = apTexto.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
            }
        }
    }    
    
    private void remove_estilo()
    {
        ObservableList <Node> componentes = apTexto.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof Label)
            {
                if(((Label)n).getStyleClass().size() > 0)
                   ((Label)n).getStyleClass().remove(0);
            }
        }
    }

    @FXML
    private void clkRealizarMovimentacao(ActionEvent event) {
        scrollpane.setVvalue(0);
        remove_estilo();
        lbRealizarMovimentacao.getStyleClass().add("grifar");
    }
    
    @FXML
    private void clkDesfazer(ActionEvent event) {
        scrollpane.setVvalue(0.48);
        remove_estilo();
        lbDesfazer.getStyleClass().add("grifar");
    }

    @FXML
    private void clkVeiculoConduzido(ActionEvent event) {
        scrollpane.setVvalue(0.63);
        remove_estilo();
        lbVeiculoNaoAparece.getStyleClass().add("grifar");
    }

    @FXML
    private void clkPassageiroNaoConsta(ActionEvent event) {
        scrollpane.setVvalue(0.87);
        remove_estilo();
        lbPassageiroNaoConsta.getStyleClass().add("grifar");
    }

    @FXML
    private void clkPessoaNaoEncontrada(ActionEvent event) {
        scrollpane.setVvalue(1);
        remove_estilo();
        lbPessoaNaoEncontrada.getStyleClass().add("grifar");
    }

    @FXML
    private void clkMovimentacaoNaoRealizada(ActionEvent event) {
        scrollpane.setVvalue(1);
        remove_estilo();
        lbMovimentacaoNaoRealizada.getStyleClass().add("grifar");
    }

    
    
}
