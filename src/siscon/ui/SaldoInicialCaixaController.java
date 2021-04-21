/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import siscon.controladorasinterface.CtrFluxoCaixa;
import siscon.util.Banco;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class SaldoInicialCaixaController implements Initializable {

    @FXML
    private TextField tfSaldoInicial;
    @FXML
    private Label lbSaldoInicial;
    @FXML
    private AnchorPane apAviso;
    @FXML
    private Text lbAviso;
    
    public static boolean status = false;

    private CtrFluxoCaixa ctr = new CtrFluxoCaixa();
    @FXML
    private AnchorPane apDados;
    @FXML
    private AnchorPane apPrimeiroAviso;
    @FXML
    private Text lbAviso1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(ctr.getCtrcaixa().getCaixa().getUltimoCaixa() != null) //confirmando q não existe caixa nenhum
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");            
            
            if(ctr.getCtrcaixa().getCaixa().getUltimoCaixa().estaAberto())
            {
                status = true;
                alert.setContentText("O Fluxo de Caixa já foi iniciado.\nNo momento, o caixa está aberto!");
            }
            else
            {
                status = false;
                alert.setContentText("O Fluxo de Caixa já foi iniciado.\nNo momento, o caixa está fechado!");
            }
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());  
            alert.showAndWait();
            PrincipalController.TelaSaldoInicial.close();            
        }
        
        MaskCampos.monetaryField(tfSaldoInicial);
    }    

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        if(tfSaldoInicial.getText().equals(""))
        {
            lbSaldoInicial.setTextFill(Color.RED);
            tfSaldoInicial.requestFocus();
            return;
        }
        lbSaldoInicial.setTextFill(Color.BLACK);
        
        //VALIDEI, abro o caixa com o valor inicial indicado
        ctr.getCtrcaixa().getCaixa().setUsuario(PrincipalController.usuario);
        
        Banco.con.IniciarTransacao();
        if(ctr.getCtrcaixa().getCaixa().abrir())
        {
            if(Float.parseFloat(tfSaldoInicial.getText().replace(",", ".")) > 0) //preciso movimentar
            {
               if(!ctr.getCtrcaixa().movimentar(Float.parseFloat(tfSaldoInicial.getText().replace(",", ".")), "A", "saldo inicial"))
               {
                   Banco.con.Rollback("Não foi possível abrir o caixa.\nTente novamente mais tarde!");
                   status = false;
               }
               else
               {
                   Banco.con.Commit("Abrir primeiro caixa.");
                   status = true;
               }
            }
            else
            {
                Banco.con.Commit("Abrir primeiro caixa.");
                status = true;
            }
        }
        else
        {
            Banco.con.Rollback("Não foi possível abrir o caixa.\nTente novamente mais tarde!");
            status = false;
        }
        
        PrincipalController.TelaSaldoInicial.close();   
    }

    @FXML
    private void clkEntendi(ActionEvent event) {
        apAviso.setVisible(false);
        apDados.setVisible(true);
        tfSaldoInicial.requestFocus();
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        status = false;
        PrincipalController.TelaSaldoInicial.close();  
    }

    @FXML
    private void clkProximo(ActionEvent event) {
        apPrimeiroAviso.setVisible(false);
        apAviso.setVisible(true);
    }
    
}
