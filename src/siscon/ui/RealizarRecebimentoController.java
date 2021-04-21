/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import siscon.controladorasinterface.CtrRealizarRecebimento;
import siscon.entidades.ParcelaRec;
import siscon.util.Banco;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RealizarRecebimentoController implements Initializable {

    @FXML
    private TableView<ParcelaRec> tabela;
    @FXML
    private TableColumn RECEBIMENTOScolNumero;
    @FXML
    private TableColumn RECEBIMENTOScolVencimento;
    @FXML
    private TableColumn RECEBIMENTOScolValor;
    @FXML
    private TableColumn RECEBIMENTOScolPagamento;
    @FXML
    private TableColumn RECEBIMENTOScolValorPago;
    @FXML
    private TableColumn RECEBIMENTOScolForma;
    @FXML
    private TextField tfConta;
    @FXML
    private TextField tfParcela;
    @FXML
    private TextField tfValor;
    @FXML
    private TextField tfPessoa;
    @FXML
    private DatePicker dpDataVencimento;
    @FXML
    private Label lbDataPagamento;
    @FXML
    private TextField tfValorPago;
    @FXML
    private Label lbValorPago;
    @FXML
    private DatePicker dpDataPagamento;
    @FXML
    private ComboBox<String> cbFormaPagamento;
    @FXML
    private Label lbFormaPagamento;
    @FXML
    private AnchorPane apCheque;
    @FXML
    private TextField tfChequeNumero;
    @FXML
    private Label lbChequeNumero;
    @FXML
    private TextField tfChequeConta;
    @FXML
    private Label lbChequeConta;
    @FXML
    private TextField tfChequeBanco;
    @FXML
    private Label lbChequeBanco;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btCancelar;
    @FXML
    private AnchorPane apBoleto;
    @FXML
    private TextField tfBoletoNumero;
    @FXML
    private Label lbBoletoNumero;
    @FXML
    private AnchorPane apDeposito;
    @FXML
    private TextField tfDepositoIdentificacao;
    @FXML
    private Label lbDepositoIdentificacao;
    
    private CtrRealizarRecebimento ctrRealizarRecebimento = new CtrRealizarRecebimento();
    @FXML
    private Text lbAvisoCheque;
    @FXML
    private AnchorPane apDados;
    @FXML
    private Label lbPagamentoPendente;
    @FXML
    private Label lbSaldoCaixa;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskCampos.monetaryField(tfValorPago);
        MaskCampos.monetaryField(tfValor);
        MaskCampos.dateField(dpDataPagamento.getEditor());
        ModoExibicao();
        RECEBIMENTOScolNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        RECEBIMENTOScolVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        RECEBIMENTOScolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        RECEBIMENTOScolPagamento.setCellValueFactory(new PropertyValueFactory("data_pagamento"));
        RECEBIMENTOScolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        RECEBIMENTOScolForma.setCellValueFactory(new PropertyValueFactory("forma"));
        
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getCaixa(PrincipalController.caixa));
        
        
        ctrRealizarRecebimento.CarregarComboBox(cbFormaPagamento, "FORMAS");
        ctrRealizarRecebimento.getCtrrecebimento().setParcela(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getParcela(PrincipalController.parcelarec));
        ctrRealizarRecebimento.CarregarTabela(tabela, "contareceber.cr_cod = "+ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber().getCod());
        tfConta.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber()+" (Total: R$"+ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber().getValor()+")");
        tfParcela.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getNumero()+" de "+ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber().getNum_parcelas());
        if(tfParcela.getText().contains(" de 0"))
            tfParcela.setText("À vista");
        if(tfParcela.getText().contains("0 de"))
            tfParcela.setText(tfParcela.getText().replace("0 de", "Entrada +"));
        tfPessoa.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber().getPessoa().getNome()+" ("+ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getContareceber().getPessoa().getCpf()+")");
        if(tfPessoa.getText().contains("()"))
            tfPessoa.setText(tfPessoa.getText().replace("()", ""));
        dpDataVencimento.setValue(Funcoes.StringToDate(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getData_vencimento()));
        tfValor.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getValor());
        SelecionarParcela();
        dpDataPagamento.requestFocus();
        
        if(!PrincipalController.TelaRealizarRecebimento.getTitle().contains("Realizar")) //serve tanto para estornar quanto para ver Parcela
        {
            
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getData_pagamento() != null)
               dpDataPagamento.setValue(Funcoes.StringToDate(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getData_pagamento()));
            tfValorPago.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getValorpago());
            cbFormaPagamento.setValue(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getForma());
            MostrarFormaPagamento();
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getCheque() != null)
            {
                tfChequeNumero.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getCheque().getNumero());
                tfChequeBanco.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getCheque().getBanco());
                tfChequeConta.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getCheque().getConta());
            }
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getBoleto() != null)
                tfBoletoNumero.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getBoleto().getNumero());
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getDeposito() != null)
                tfDepositoIdentificacao.setText(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getDeposito().getIdentificacao());
        }
        else
        {
            lbValorPago.setText("Valor pago:");
            tfValorPago.setPromptText("R$");
            dpDataPagamento.requestFocus();
            tfValorPago.getStyleClass().add("destaque");
            dpDataPagamento.getStyleClass().add("destaque");
            cbFormaPagamento.getStyleClass().add("destaque");
        }
        
        if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Ver"))
        {
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getValorpago() == null) //parcela pendente
            {
                lbPagamentoPendente.setVisible(true);
                lbDataPagamento.setVisible(false);
                lbValorPago.setVisible(false);
                lbFormaPagamento.setVisible(false);
                dpDataPagamento.setVisible(false);
                tfValorPago.setVisible(false);
                cbFormaPagamento.setVisible(false);
            }
            btConfirmar.setVisible(false);
            btConfirmar.setDisable(true);
            btCancelar.setText("FECHAR");
            btCancelar.setLayoutX(379);
            cbFormaPagamento.setPromptText("");
        }
        
        if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Estornar"))
        {
            lbSaldoCaixa.setText("Caixa: ");
            if(Float.parseFloat(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
            {
                lbSaldoCaixa.setTextFill(Color.RED);
                lbSaldoCaixa.setText(lbSaldoCaixa.getText()+"-");
            }
            else
                lbSaldoCaixa.setTextFill(Color.GREEN);
            lbSaldoCaixa.setText(lbSaldoCaixa.getText()+"R$"+ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getSaldoDia().replace("-", ""));
        }
        
    }  
    
    private void ModoExibicao()
    {
        ObservableList <Node> componentes = apDados.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                
                ((TextInputControl)n).setEditable(false);
                ((TextInputControl)n).setFocusTraversable(false);
                ((TextInputControl)n).setMouseTransparent(true);
               
                
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(false);
                ((ComboBox)n).setMouseTransparent(true);
                
                    
            }            
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(false);
                ((DatePicker)n).setFocusTraversable(false);
                ((DatePicker)n).setMouseTransparent(true);  
            }            
            
        }
        if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Realizar")) 
        {
            tfValorPago.setEditable(true);
            tfValorPago.setFocusTraversable(true);
            tfValorPago.setMouseTransparent(false);
            cbFormaPagamento.setFocusTraversable(true);
            cbFormaPagamento.setMouseTransparent(false);
            dpDataPagamento.setEditable(true);
            dpDataPagamento.setFocusTraversable(true);
            dpDataPagamento.setMouseTransparent(false);
            return;
        }
                
        componentes = apBoleto.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                
                ((TextInputControl)n).setEditable(false);
                ((TextInputControl)n).setFocusTraversable(false);
                ((TextInputControl)n).setMouseTransparent(true);
               
                
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(false);
                ((ComboBox)n).setMouseTransparent(true);
                
                    
            }  
        }        
        
        componentes = apCheque.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                
                ((TextInputControl)n).setEditable(false);
                ((TextInputControl)n).setFocusTraversable(false);
                ((TextInputControl)n).setMouseTransparent(true);
               
                
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(false);
                ((ComboBox)n).setMouseTransparent(true);
                
                    
            }  
        }
        componentes = apDeposito.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                
                ((TextInputControl)n).setEditable(false);
                ((TextInputControl)n).setFocusTraversable(false);
                ((TextInputControl)n).setMouseTransparent(true);
               
                
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(false);
                ((ComboBox)n).setMouseTransparent(true);
                
                    
            }  
        }
        
    }
    
    private void MostrarFormaPagamento()
    {
        if(cbFormaPagamento.getValue() == null)
            return;
        
        apBoleto.setVisible(false);
        apCheque.setVisible(false);
        apDeposito.setVisible(false);
        lbAvisoCheque.setVisible(false);
        
        if(cbFormaPagamento.getValue().equals("Boleto"))
        {
            apBoleto.setVisible(true);
            tfBoletoNumero.requestFocus();
        }
        if(cbFormaPagamento.getValue().equals("Cheque"))
        {
            apCheque.setVisible(true);
            lbAvisoCheque.setVisible(true);
            tfChequeNumero.requestFocus();
        }
        if(cbFormaPagamento.getValue().equals("Depósito"))
        {
            apDeposito.setVisible(true);
            tfDepositoIdentificacao.requestFocus();
        }
    }

    @FXML
    private void clkFormaPagamento(ActionEvent event) {
        MostrarFormaPagamento();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        //VALIDAR
        boolean erro = false;
        if(cbFormaPagamento.getValue() != null && cbFormaPagamento.getValue().equals("Boleto"))
        { //Validar campos do boleto
          if(tfBoletoNumero.getText().equals(""))
          {
              erro = true;
              lbBoletoNumero.setTextFill(Color.RED);
              tfBoletoNumero.requestFocus();
          }
          else
              lbBoletoNumero.setTextFill(Color.BLACK);  
        }
        
        if(cbFormaPagamento.getValue() != null && cbFormaPagamento.getValue().equals("Cheque"))
        { //Validar campos do boleto
          if(tfChequeBanco.getText().equals(""))
          {
              erro = true;
              lbChequeBanco.setTextFill(Color.RED);
              tfChequeBanco.requestFocus();
          }
          else
              lbChequeBanco.setTextFill(Color.BLACK);  
          if(tfChequeConta.getText().equals(""))
          {
              erro = true;
              lbChequeConta.setTextFill(Color.RED);
              tfChequeConta.requestFocus();
          }
          else
              lbChequeConta.setTextFill(Color.BLACK);  
          if(tfChequeNumero.getText().equals(""))
          {
              erro = true;
              lbChequeNumero.setTextFill(Color.RED);
              tfChequeNumero.requestFocus();
          }
          else
              lbChequeNumero.setTextFill(Color.BLACK); 
        }
        
        if(cbFormaPagamento.getValue() != null && cbFormaPagamento.getValue().equals("Depósito"))
        { //Validar campos do boleto
          if(tfDepositoIdentificacao.getText().equals(""))
          {
              erro = true;
              lbDepositoIdentificacao.setTextFill(Color.RED);
              tfDepositoIdentificacao.requestFocus();
          }
          else
              lbDepositoIdentificacao.setTextFill(Color.BLACK);  
        }
        
        if(cbFormaPagamento.getValue() == null)
        {
            erro = true;
            lbFormaPagamento.setTextFill(Color.RED);
            cbFormaPagamento.requestFocus();
        }
        else
            lbFormaPagamento.setTextFill(Color.BLACK);
        if(tfValorPago.getText().equals("") || Float.parseFloat(tfValorPago.getText().replace(",", ".")) == 0)
        {
            erro = true;
            lbValorPago.setTextFill(Color.RED);
            tfValorPago.requestFocus();
        }
        else
            lbValorPago.setTextFill(Color.BLACK);
        
        if(dpDataPagamento.getValue() == null || dpDataPagamento.getValue().isAfter(LocalDate.now()))
        {
            erro = true;
            lbDataPagamento.setTextFill(Color.RED);
            dpDataPagamento.requestFocus();
        }
        else
            lbDataPagamento.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        //validei os dados, EFETUAR RECEBIMENTO
        
        String msg = "Deseja realmente realizar o recebimento ?";
        if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Estornar"))
           msg = "Deseja realmente estornar o recebimento ?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(PrincipalController.TelaRealizarRecebimento.getTitle());
        alert.setContentText(msg);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

        if(alert.showAndWait().get() == buttonTypeSim)
        {
            Banco.con.IniciarTransacao();
            if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Realizar"))
            {
                if(!ctrRealizarRecebimento.getCtrrecebimento().receber(dpDataPagamento.getValue(), Float.parseFloat(tfValorPago.getText().replace(",", ".")), cbFormaPagamento.getValue(), tfBoletoNumero.getText(), tfChequeNumero.getText(), tfChequeConta.getText(), tfChequeBanco.getText(), tfDepositoIdentificacao.getText()))
                {
                    Banco.con.Rollback("Não foi possível realizar o recebimento.");
                    return;
                }
                else
                    Banco.con.Commit("Realizar recebimento.");
            }
            if(PrincipalController.TelaRealizarRecebimento.getTitle().contains("Estornar"))
            {      
                if(!ctrRealizarRecebimento.getCtrrecebimento().estornar())
                {
                    Banco.con.Rollback("Não foi possível estornar o recebimento.");
                    return;
                }
                else
                    Banco.con.Commit("Estornar recebimento.");
            }
            Banco.con.FecharTransacao();
            PrincipalController.TelaRealizarRecebimento.close();
        }
        
        
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        PrincipalController.TelaRealizarRecebimento.close();
    }
    
    private void SelecionarParcela()
    {
        if(tabela.getItems().size() == 0)
            return;
        for(int i=0; i<tabela.getItems().size();i++)
        {
            if(tabela.getItems().get(i).getSeq() == ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getSeq())
            {
                tabela.getSelectionModel().select(i);
                tabela.scrollTo(i);                
                return;
            }
        }
    }    

    @FXML
    private void clkTabela(MouseEvent event) {
        SelecionarParcela();
        
    }

}
