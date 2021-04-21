/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class EscolherOpcaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static int opcao;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        opcao = 0; //não escolheu nenhuma por padrão
        // TODO
    }    

    @FXML
    private void clkSelecionarEmpresa(ActionEvent event) {
        opcao = 1;
        PrincipalController.TelaEscolherOpcao.close();
    }

    @FXML
    private void clkSelecionarPessoa(ActionEvent event) {
        opcao = 2;
        PrincipalController.TelaEscolherOpcao.close();
    }
    
}
