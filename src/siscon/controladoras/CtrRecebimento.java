/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import siscon.entidades.AcertoCaixa;
import siscon.entidades.Boleto;
import siscon.entidades.Caixa;
import siscon.entidades.Cheque;
import siscon.entidades.Deposito;
import siscon.entidades.MovCaixa;
import siscon.entidades.ParcelaRec;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrRecebimento {
    
    private MovCaixa movcaixa;

    public CtrRecebimento() {
        movcaixa = new MovCaixa();
    }

    public MovCaixa getMovCaixa() {
        return movcaixa;
    }
    
    public void setCaixa(Caixa caixa)
    {
       movcaixa.setCaixa(caixa);
    }
    
    public void setParcela(ParcelaRec parcela)
    {
        movcaixa.setParcelarec(parcela);
    }
    public void novo()
    {
        movcaixa = new MovCaixa();
    }  
    
    public ArrayList <ParcelaRec> ListarParcelas(String filtro)
    {
        return movcaixa.getParcelarec().getParcelas(filtro);
    }
    
    public boolean receber(LocalDate data_pagamento, float valor_pago, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id)
    {
        movcaixa.getParcelarec().setValorpago(valor_pago);
        valor_pago = movcaixa.getParcelarec().reajuste(); //verifica se o valor pago é maior que o valor da parcela
        if(!movcaixa.getCaixa().aporte(valor_pago)) //verifica se o caixa pode receber o valor
           return false;     
            
        movcaixa.getParcelarec().setForma(forma);
        movcaixa.getParcelarec().setData_pagamento(Funcoes.DateToString(data_pagamento));
        
        if(forma.equals("Boleto"))
           movcaixa.getParcelarec().setBoleto(new Boleto(bol_numero));
        if(forma.equals("Cheque"))
           movcaixa.getParcelarec().setCheque(new Cheque(che_numero, che_conta, che_banco ));
        if(forma.equals("Depósito"))
           movcaixa.getParcelarec().setDeposito(new Deposito(dep_id));
        
        if(!movcaixa.getParcelarec().receber()) //receber a parcela
           return false;       
        
        movcaixa.setAcertocaixa(null);
        movcaixa.setParcelapag(null);
        if(!movcaixa.inserir()) //inserindo movimentação
           return false;
        
        return movcaixa.getCaixa().atualizar(); //atualizar o caixa
            
    }
    
    public boolean estornar()
    {
        float valor = Float.parseFloat(movcaixa.getParcelarec().getValorpago().replace(",", "."));
        if(!movcaixa.getCaixa().sangria(valor)) //verifica se o caixa pode estornar o valor
           return false; 
        
        AcertoCaixa ac = new AcertoCaixa(valor, "S", "Estorno: Parcela Cód. "+movcaixa.getParcelarec().getSeq());
        if(!ac.inserir())
            return false;
        
        if(!movcaixa.getParcelarec().estornar()) //estornar a parcela e subsequentes (se houver)
           return false;  
        
        movcaixa.setParcelarec(null);
        movcaixa.setParcelapag(null);
        movcaixa.setAcertocaixa(ac);
        
        if(!movcaixa.inserir()) //excluindo a movimentação de caixa
            return false;
        
        return movcaixa.getCaixa().atualizar(); //atualizar o caixa        
    }
    
}
