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
public class EscolherTipoAjusteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static int opcao;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        opcao = 0;
        
    }    

    @FXML
    private void clkCancelar(ActionEvent event) {
        opcao = 0;
        PrincipalController.TelaEscolherTipoAjuste.close();
        
    }

    @FXML
    private void clkAmbas(ActionEvent event) {
        opcao = 1;
        PrincipalController.TelaEscolherTipoAjuste.close();
    }

    @FXML
    private void clkInicial(ActionEvent event) {
        opcao = 2;
        PrincipalController.TelaEscolherTipoAjuste.close();
    }

    @FXML
    private void clkFinal(ActionEvent event) {
        opcao = 3;
        PrincipalController.TelaEscolherTipoAjuste.close();
    }
    
}
