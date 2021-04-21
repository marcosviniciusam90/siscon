/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import siscon.entidades.AcertoCaixa;
import siscon.entidades.Boleto;
import siscon.entidades.Caixa;
import siscon.entidades.CartaoCredito;
import siscon.entidades.Cheque;
import siscon.entidades.Deposito;
import siscon.entidades.MovCaixa;
import siscon.entidades.ParcelaPag;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrPagamento {
    
    private MovCaixa movcaixa;

    public CtrPagamento() {
        movcaixa = new MovCaixa();
    }

    public MovCaixa getMovCaixa() {
        return movcaixa;
    }
    
    public void setCaixa(Caixa caixa)
    {
       movcaixa.setCaixa(caixa);
    }
    
    public void setParcela(ParcelaPag parcela)
    {
        movcaixa.setParcelapag(parcela);
    }
    public void novo()
    {
        movcaixa = new MovCaixa();
    }  
    
    public ArrayList <ParcelaPag> ListarParcelas(String filtro)
    {
        return movcaixa.getParcelapag().getParcelas(filtro);
    }
    
    public boolean pagar(LocalDate data_pagamento, float valor_pago, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id, int cartao)
    {
        movcaixa.getParcelapag().setValorpago(valor_pago);
        valor_pago = movcaixa.getParcelapag().reajuste(); //verifica se o valor pago é maior que o valor da parcela
        if(!forma.equals("Cartão de Crédito") && !movcaixa.getCaixa().sangria(valor_pago)) //verifica se o caixa pode pagar o valor
           return false;     
            
        movcaixa.getParcelapag().setForma(forma);
        movcaixa.getParcelapag().setData_pagamento(Funcoes.DateToString(data_pagamento));
        
        if(forma.equals("Boleto"))
           movcaixa.getParcelapag().setBoleto(new Boleto(bol_numero));
        if(forma.equals("Cheque"))
           movcaixa.getParcelapag().setCheque(new Cheque(che_numero, che_conta, che_banco ));
        if(forma.equals("Depósito"))
           movcaixa.getParcelapag().setDeposito(new Deposito(dep_id));
        if(forma.equals("Cartão de Crédito"))
        {
            CartaoCredito cc = new CartaoCredito();
            movcaixa.getParcelapag().setCartao(cc.getCartaoCredito(cartao));
        }
        
        if(!movcaixa.getParcelapag().pagar()) //pagar a parcela
           return false;
        
        if(forma.equals("Cartão de Crédito"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Posteriormente, deve-se lançar a fatura do cartão e realizar o pagamento da mesma.\nSomente no pagamento da fatura que o valor será abatido no Fluxo de Caixa.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return true; //não faço movimentação de caixa e não atualizo o caixa se for Cartão de Crédito
        }
        
        movcaixa.setAcertocaixa(null);
        movcaixa.setParcelarec(null);
        if(!movcaixa.inserir()) //inserindo movimentação
           return false;
        
        return movcaixa.getCaixa().atualizar(); //atualizar o caixa
            
    }
    
    public boolean estornar()
    {
        float valor = Float.parseFloat(movcaixa.getParcelapag().getValorpago().replace(",", "."));
        if(!movcaixa.getParcelapag().getForma().equals("Cartão") && !movcaixa.getCaixa().aporte(valor)) //verifica se o caixa pode estornar o valor pago (aporte)
           return false; 
        
        int seq_aux = movcaixa.getParcelapag().getSeq();
        if(!movcaixa.getParcelapag().estornar()) //estornar a parcela e subsequentes (se houver), além de reinserir a mesma parcela sem o pagamento
           return false;  
        
        if(movcaixa.getParcelapag().getForma().equals("Cartão"))
           return true;
        
        AcertoCaixa ac = new AcertoCaixa(valor, "A", "Estorno: Parcela Cód. "+seq_aux);
        if(!ac.inserir())
            return false;
        
        movcaixa.setParcelarec(null);
        movcaixa.setParcelapag(null);
        movcaixa.setAcertocaixa(ac);
        
        if(!movcaixa.inserir()) //excluindo a movimentação de caixa
            return false;
        
        return movcaixa.getCaixa().atualizar(); //atualizar o caixa        
    }
    
}
