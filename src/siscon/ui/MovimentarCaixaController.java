/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import siscon.controladorasinterface.CtrFluxoCaixa;
import siscon.util.Banco;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class MovimentarCaixaController implements Initializable {

    @FXML
    private AnchorPane menu;
    @FXML
    private AnchorPane movimentacao;
    @FXML
    private TextField tfValor;
    @FXML
    private TextField tfDescricao;
    @FXML
    private Label lbTipo;
    
    private CtrFluxoCaixa ctrFluxoCaixa = new CtrFluxoCaixa();
    @FXML
    private Label lbValor;
    @FXML
    private Label lbDescricao;
    @FXML
    private Button btAporte;
    @FXML
    private Button btSangria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskCampos.monetaryField(tfValor);
        btAporte.requestFocus();
        btAporte.setTooltip(new Tooltip("Movimentação de entrada no caixa."));
        btSangria.setTooltip(new Tooltip("Movimentação de retirada no caixa."));
        try 
        {
            if(!ctrFluxoCaixa.CarregarDados() || !ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("O caixa não está aberto.");   
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();  
                return;
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Erro: "+ex);
        }
        
    }    

    @FXML
    private void clkSangria(ActionEvent event) {
        movimentacao.setVisible(true);
        menu.setVisible(false);
        lbTipo.setText("Tipo: Sangria");
        lbTipo.setTextFill(Color.RED);
        lbDescricao.setTextFill(Color.BLACK);
        lbValor.setTextFill(Color.BLACK);
        tfValor.requestFocus();
    }

    @FXML
    private void clkAporte(ActionEvent event) {
        movimentacao.setVisible(true);
        menu.setVisible(false);
        lbTipo.setText("Tipo: Aporte");
        lbTipo.setTextFill(Color.GREEN);
        lbDescricao.setTextFill(Color.BLACK);
        lbValor.setTextFill(Color.BLACK);
        tfValor.requestFocus();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        boolean erro = false;
        if(tfDescricao.getText().equals(""))
        {
            erro = true;
            tfDescricao.requestFocus();
            lbDescricao.setTextFill(Color.RED);
        }
        else
            lbDescricao.setTextFill(Color.BLACK);
        if(tfValor.getText().equals("") || Float.parseFloat(tfValor.getText().replace(",", ".")) == 0)
        {
            erro = true;
            tfValor.requestFocus();
            lbValor.setTextFill(Color.RED);
        }
        else
            lbValor.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Movimentar");
        alert.setContentText("Confirmar movimentação ?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

        if(alert.showAndWait().get() == buttonTypeSim)
        {
            Banco.con.IniciarTransacao();
            String tipo = "A";
            if(lbTipo.getText().contains("Sangria"))
               tipo = "S";
            
            if(ctrFluxoCaixa.getCtrcaixa().movimentar(Float.parseFloat(tfValor.getText().replace(",", ".")), tipo, tfDescricao.getText()))
            {
               Banco.con.Commit("Movimentar caixa.");
               PrincipalController.TelaMovimentarCaixa.close();
            }
            else
            {
               Banco.con.Rollback("");
               return;
            }
        }
    }

    @FXML
    private void clkVoltar(ActionEvent event) {
        movimentacao.setVisible(false);
        menu.setVisible(true);
        tfDescricao.setText("");
        tfValor.setText("");
    }
    
}
