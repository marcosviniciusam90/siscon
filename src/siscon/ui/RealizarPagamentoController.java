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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import siscon.controladorasinterface.CtrRealizarPagamento;
import siscon.entidades.CartaoCredito;
import siscon.entidades.ParcelaPag;
import siscon.util.Banco;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RealizarPagamentoController implements Initializable {

    @FXML
    private TableView<ParcelaPag> tabela;
    @FXML
    private TextField tfConta;
    @FXML
    private TextField tfParcela;
    @FXML
    private TextField tfValor;
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
    
    private CtrRealizarPagamento ctrRealizarPagamento = new CtrRealizarPagamento();
    @FXML
    private TableColumn PAGAMENTOScolNumero;
    @FXML
    private TableColumn PAGAMENTOScolVencimento;
    @FXML
    private TableColumn PAGAMENTOScolValor;
    @FXML
    private TableColumn PAGAMENTOScolPagamento;
    @FXML
    private TableColumn PAGAMENTOScolValorPago;
    @FXML
    private TableColumn PAGAMENTOScolForma;
    @FXML
    private TextField tfPessoaEmpresa;
    @FXML
    private AnchorPane apCartao;
    @FXML
    private ComboBox<CartaoCredito> cbCartaoCredito;
    @FXML
    private Label lbCartaoCredito;
    @FXML
    private Text lbAvisoCartao;
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
        PAGAMENTOScolNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        PAGAMENTOScolVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        PAGAMENTOScolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PAGAMENTOScolPagamento.setCellValueFactory(new PropertyValueFactory("data_pagamento"));
        PAGAMENTOScolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        PAGAMENTOScolForma.setCellValueFactory(new PropertyValueFactory("forma"));
        
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getCaixa(PrincipalController.caixa));
        
        
        ctrRealizarPagamento.CarregarComboBox(cbFormaPagamento, "FORMAS");
        ctrRealizarPagamento.CarregarCartoesCredito(cbCartaoCredito);
        ctrRealizarPagamento.getCtrpagamento().setParcela(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getParcela(PrincipalController.parcelapag));
        ctrRealizarPagamento.CarregarTabela(tabela, "contapagar.cp_cod = "+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getCod());
        tfConta.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar()+" (Total: R$"+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getValor()+")");
        tfParcela.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getNumero()+" de "+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getNum_parcelas());
        if(tfParcela.getText().contains(" de 0"))
            tfParcela.setText("À vista");
        if(tfParcela.getText().contains("0 de"))
            tfParcela.setText(tfParcela.getText().replace("0 de", "Entrada +"));
        
        if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getPessoa() != null)
           tfPessoaEmpresa.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getPessoa().getNome()+" ("+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getPessoa().getCpf()+")");
        else
           tfPessoaEmpresa.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getEmpresa().getNome()+" ("+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getContapagar().getEmpresa().getCnpj()+")");
        
        if(tfPessoaEmpresa.getText().contains("()"))
            tfPessoaEmpresa.setText(tfPessoaEmpresa.getText().replace("()", ""));
        dpDataVencimento.setValue(Funcoes.StringToDate(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getData_vencimento()));
        tfValor.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getValor());
        SelecionarParcela();
        dpDataPagamento.requestFocus();
        
        if(!PrincipalController.TelaRealizarPagamento.getTitle().contains("Realizar")) //serve tanto para estornar quanto para ver Parcela
        {
            
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getData_pagamento() != null)
               dpDataPagamento.setValue(Funcoes.StringToDate(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getData_pagamento()));
            tfValorPago.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getValorpago());
            
            cbFormaPagamento.setValue(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getForma());
            if(cbFormaPagamento.getValue() != null && cbFormaPagamento.getValue().equals("Cartão"))
               cbFormaPagamento.setValue("Cartão de Crédito");
            
            MostrarFormaPagamento();
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCheque() != null)
            {
                tfChequeNumero.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCheque().getNumero());
                tfChequeBanco.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCheque().getBanco());
                tfChequeConta.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCheque().getConta());
            }
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getBoleto() != null)
                tfBoletoNumero.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getBoleto().getNumero());
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getDeposito() != null)
                tfDepositoIdentificacao.setText(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getDeposito().getIdentificacao());
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCartao() != null)
                cbCartaoCredito.setValue(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getCartao());
        }
        else
        {
            lbSaldoCaixa.setText("Caixa: ");
            if(Float.parseFloat(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
            {
                lbSaldoCaixa.setTextFill(Color.RED);
                lbSaldoCaixa.setText(lbSaldoCaixa.getText()+"-");
            }
            else
                lbSaldoCaixa.setTextFill(Color.GREEN);
            lbSaldoCaixa.setText(lbSaldoCaixa.getText()+"R$"+ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getSaldoDia().replace("-", ""));
            lbValorPago.setText("Valor pago:");
            tfValorPago.setPromptText("R$");
            dpDataPagamento.requestFocus();
            tfValorPago.getStyleClass().add("destaque");
            dpDataPagamento.getStyleClass().add("destaque");
            cbFormaPagamento.getStyleClass().add("destaque");
        }
        if(PrincipalController.TelaRealizarPagamento.getTitle().contains("Ver"))
        {
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getValorpago() == null) //parcela pendente
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
        if(PrincipalController.TelaRealizarPagamento.getTitle().contains("Realizar")) 
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
        
        componentes = apCartao.getChildren();
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
        apCartao.setVisible(false);
        lbAvisoCartao.setVisible(false);
        
        if(cbFormaPagamento.getValue().equals("Boleto"))
        {
            apBoleto.setVisible(true);
            tfBoletoNumero.requestFocus();
        }
        if(cbFormaPagamento.getValue().equals("Cheque"))
        {
            apCheque.setVisible(true);
            lbAvisoCartao.setText("O pagamento em Cheque deve ser registrado somente após o cheque ter sido debitado.");
            lbAvisoCartao.setVisible(true);
            tfChequeNumero.requestFocus();
        }
        if(cbFormaPagamento.getValue().equals("Depósito"))
        {
            apDeposito.setVisible(true);
            tfDepositoIdentificacao.requestFocus();
        }
        
        if(cbFormaPagamento.getValue().equals("Cartão de Crédito"))
        {
            apCartao.setVisible(true);
            lbAvisoCartao.setText("O pagamento/estorno de uma parcela feito no Cartão de Crédito não altera o Fluxo de Caixa.");
            lbAvisoCartao.setVisible(true);
            cbCartaoCredito.requestFocus();
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
        
        if(cbFormaPagamento.getValue() != null && cbFormaPagamento.getValue().equals("Cartão de Crédito"))
        { //Validar campos do boleto
          if(cbCartaoCredito.getValue() == null)
          {
              erro = true;
              lbCartaoCredito.setTextFill(Color.RED);
              cbCartaoCredito.requestFocus();
          }
          else
              lbCartaoCredito.setTextFill(Color.BLACK);  
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
        
        //validei os dados, EFETUAR PAGAMENTO
        
        String msg = "Deseja realmente realizar o pagamento ?";
        if(PrincipalController.TelaRealizarPagamento.getTitle().contains("Estornar"))
           msg = "Deseja realmente estornar o pagamento ?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(PrincipalController.TelaRealizarPagamento.getTitle());
        alert.setContentText(msg);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

        if(alert.showAndWait().get() == buttonTypeSim)
        {
            Banco.con.IniciarTransacao();
            if(PrincipalController.TelaRealizarPagamento.getTitle().contains("Realizar"))
            {
                int cartao = 0;
                if(cbFormaPagamento.getValue().equals("Cartão de Crédito"))
                    cartao = cbCartaoCredito.getValue().getCod();
                if(!ctrRealizarPagamento.getCtrpagamento().pagar(dpDataPagamento.getValue(), Float.parseFloat(tfValorPago.getText().replace(",", ".")), cbFormaPagamento.getValue(), tfBoletoNumero.getText(), tfChequeNumero.getText(), tfChequeConta.getText(), tfChequeBanco.getText(), tfDepositoIdentificacao.getText(), cartao))
                {
                    Banco.con.Rollback("Não foi possível realizar o pagamento.");
                    return;
                }
                else
                    Banco.con.Commit("Realizar pagamento.");
            }
            if(PrincipalController.TelaRealizarPagamento.getTitle().contains("Estornar"))
            {      
                if(!ctrRealizarPagamento.getCtrpagamento().estornar())
                {
                    Banco.con.Rollback("Não foi possível estornar o pagamento.");
                    return;
                }
                else
                    Banco.con.Commit("Estornar pagamento.");
            }
            Banco.con.FecharTransacao();
            PrincipalController.TelaRealizarPagamento.close();
        }
        
        
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        PrincipalController.TelaRealizarPagamento.close();
    }
    
    private void SelecionarParcela()
    {
        if(tabela.getItems().size() == 0)
            return;
        for(int i=0; i<tabela.getItems().size();i++)
        {
            if(tabela.getItems().get(i).getSeq() == ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getSeq())
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
