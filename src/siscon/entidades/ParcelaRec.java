/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class ParcelaRec {
    
    private int seq, numero;
    private String n;
    private String valor, valorpago, data_vencimento, data_pagamento, forma, estornada, sequencia;
    private ContaReceber contareceber = new ContaReceber();
    private Boleto boleto = null;
    private Cheque cheque = null;
    private Deposito deposito = null;

    public ParcelaRec() {
        seq = 0;
    }

    public ParcelaRec(int seq, int numero, float valor, float valorpago, LocalDate data_vencimento, LocalDate data_pagamento, String forma, ContaReceber contareceber, LocalDate estornada, String sequencia) {
        this.seq = seq;
        this.numero = numero;
        setN(numero);
        this.valor = Funcoes.ValorMonetario(valor);
        if(valorpago != 0)
           this.valorpago = Funcoes.ValorMonetario(valorpago);
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
        if(data_pagamento != null)
           this.data_pagamento = Funcoes.DateToString(data_pagamento);
        this.forma = forma;
        this.contareceber = contareceber;
        if(estornada != null)
           this.estornada = Funcoes.DateToString(estornada);
        this.sequencia = sequencia;
    }
    

    public ParcelaRec(int numero, float valor, float valorpago, LocalDate data_vencimento, LocalDate data_pagamento, String forma, ContaReceber contareceber, LocalDate estornada) {
        this.numero = numero;
        setN(numero);
        this.valor = Funcoes.ValorMonetario(valor);
        if(valorpago != 0)
           this.valorpago = Funcoes.ValorMonetario(valorpago);    
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
        if(data_pagamento != null)
           this.data_pagamento = Funcoes.DateToString(data_pagamento);
        this.forma = forma;
        this.contareceber = contareceber;
        if(estornada != null)
           this.estornada = Funcoes.DateToString(estornada);
        sequencia = "N";
    }

    public String getSequencia() {
        return sequencia;
    }
    
    public String getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n+"";
        if(n == 0)
           this.n = "E";
    } 

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = Funcoes.ValorMonetario(valor);
    }

    public String getValorpago() {
        return valorpago;
    }

    public void setValorpago(float valorpago) {
        this.valorpago = Funcoes.ValorMonetario(valorpago);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public ContaReceber getContareceber() {
        return contareceber;
    }

    public void setContareceber(ContaReceber contareceber) {
        this.contareceber = contareceber;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        if(forma != null)
        {
            this.forma = forma;
            if(forma.equals("Dinheiro"))
               this.forma = "Din";
            else
              if(forma.equals("Din"))
                 this.forma = "Dinheiro";
            
            if(forma.equals("Cartão de Crédito"))
               this.forma = "CC";
            else
              if(forma.equals("CC"))
                this.forma = "Cartão";
            
            if(forma.equals("Cheque"))
               this.forma = "Che";
            else
              if(forma.equals("Che"))
                 this.forma = "Cheque";
            
            if(forma.equals("Depósito"))
               this.forma = "Dep";
            else
              if(forma.equals("Dep"))
                 this.forma = "Depósito";
            
            if(forma.equals("Boleto"))
               this.forma = "Bol";
            else
              if(forma.equals("Bol"))
                 this.forma = "Boleto";
        }
        
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public String getEstornada() {
        return estornada;
    }

    public void setEstornada(String estornada) {
        this.estornada = estornada;
    }    
    
    public String toString()
    {
        return valorpago+"";
    }
    
    public float reajuste() //verifica se está pagando a mais, reajustar valor
    {
        float valorpagoaux = Float.parseFloat(this.valorpago.replace(",", "."));
        float valoraux = Float.parseFloat(this.valor.replace(",", ".")); 
        if(valorpagoaux > valoraux) //PAGOU A MAIS, fazer reajuste e informar o troco
        {   
            float troco = valorpagoaux - valoraux;          
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Recebimento");
            alert.setContentText("Valor da parcela: R$"+valor+"\nValor recebido: R$"+valorpago+"\nTROCO: R$"+Funcoes.ValorMonetario(troco));
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            this.valorpago = this.valor;
            alert.showAndWait();
            return valoraux;
        }
        return valorpagoaux;
    }
    
    public boolean podeEstornar()
    {        
        /*
        int maior = Banco.con.getMaxPK("movcaixa", "mov_cod", "pr_seq is not null"); //vai trazer ultima movimentação de recebimento
        MovCaixa mc = new MovCaixa();
        mc = mc.getMovimentacao(maior);
        return seq == mc.getParcelarec().getSeq(); //foi a ultima parcela recebida dentre TODAS
        */
        int maior = Banco.con.getMaxPK("parcelarec", "pr_seq", "cr_cod = "+contareceber.getCod() + " and par_numero = "+numero+" and par_dtpagamento is not null and par_dtestorno is null and par_status = 'A'");
        return seq == maior;
    }
    
    public boolean temParcelaAnteriorAberta()
    {
        return getParcelas("parcelarec.cr_cod = " + contareceber.getCod() + " and par_numero < "+numero+" and par_dtpagamento is null").size() > 0;        
    }
    
    public ParcelaRec getParcela(int seq)
    {
        ParcelaRec p = null;
        ContaReceber cr = new ContaReceber();
        String sql = "select * from parcelarec";
        sql += " where pr_seq = "+seq;        
        sql += " ORDER BY par_dtvencimento, par_numero, pr_seq";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {    
                LocalDate dvenc = null, dpag = null, dest = null;
                if(rs.getDate("par_dtvencimento") != null)
                   dvenc = rs.getDate("par_dtvencimento").toLocalDate();
                if(rs.getDate("par_dtpagamento") != null)
                   dpag = rs.getDate("par_dtpagamento").toLocalDate();
                if(rs.getDate("par_dtestorno") != null)
                   dest = rs.getDate("par_dtestorno").toLocalDate();
                p = new ParcelaRec(rs.getInt("pr_seq"), rs.getInt("par_numero"), rs.getFloat("par_valor"), rs.getFloat("par_valorpago"), dvenc, dpag, rs.getString("par_frmpgto"), cr.getContaReceber(rs.getInt("cr_cod")), dest, rs.getString("par_sequencia"));
                p.setForma(rs.getString("par_frmpgto"));
                
                if(rs.getString("par_bolnumero") != null)
                    p.setBoleto(new Boleto(rs.getString("par_bolnumero")));
                if(rs.getString("par_chequenumero") != null)
                    p.setCheque(new Cheque(rs.getString("par_chequenumero"), rs.getString("par_chequeconta"), rs.getString("par_chequebanco")));
                if(rs.getString("par_depid") != null)
                    p.setDeposito(new Deposito(rs.getString("par_depid")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return p;
    }    
    
    public ParcelaRec getParcela(String filtro)
    {
        ParcelaRec p = null;
        ContaReceber cr = new ContaReceber();
        String sql = "select * from parcelarec";
        if(!filtro.equals(""))
           sql += " where "+filtro;        
        sql += " ORDER BY par_dtvencimento, par_numero, pr_seq";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {    
                LocalDate dvenc = null, dpag = null, dest = null;
                if(rs.getDate("par_dtvencimento") != null)
                   dvenc = rs.getDate("par_dtvencimento").toLocalDate();
                if(rs.getDate("par_dtpagamento") != null)
                   dpag = rs.getDate("par_dtpagamento").toLocalDate();
                if(rs.getDate("par_dtestorno") != null)
                   dest = rs.getDate("par_dtestorno").toLocalDate();
                
                p = new ParcelaRec(rs.getInt("pr_seq"), rs.getInt("par_numero"), rs.getFloat("par_valor"), rs.getFloat("par_valorpago"), dvenc, dpag, rs.getString("par_frmpgto"), cr.getContaReceber(rs.getInt("cr_cod")), dest, rs.getString("par_sequencia"));
                p.setForma(rs.getString("par_frmpgto"));
                
                if(rs.getString("par_bolnumero") != null)
                    p.setBoleto(new Boleto(rs.getString("par_bolnumero")));
                if(rs.getString("par_chequenumero") != null)
                    p.setCheque(new Cheque(rs.getString("par_chequenumero"), rs.getString("par_chequeconta"), rs.getString("par_chequebanco")));
                if(rs.getString("par_depid") != null)
                    p.setDeposito(new Deposito(rs.getString("par_depid")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return p;
    } 
    
    public ArrayList <ParcelaRec> getParcelas(String filtro)
    {
        ArrayList <ParcelaRec> lista = new ArrayList();
        ParcelaRec p = null;
        ContaReceber cr = new ContaReceber();
        String sql = "select * from parcelarec INNER JOIN contareceber ON parcelarec.cr_cod = contareceber.cr_cod WHERE par_dtestorno is null and par_status = 'A'";
        if(!filtro.equals(""))
            sql += " and "+filtro;   
        sql += " ORDER BY par_dtvencimento, par_numero, pr_seq";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {    
                LocalDate dvenc = null, dpag = null, dest = null;
                if(rs.getDate("par_dtvencimento") != null)
                   dvenc = rs.getDate("par_dtvencimento").toLocalDate();
                if(rs.getDate("par_dtpagamento") != null)
                   dpag = rs.getDate("par_dtpagamento").toLocalDate();
                if(rs.getDate("par_dtestorno") != null)
                   dest = rs.getDate("par_dtestorno").toLocalDate();
                p = new ParcelaRec(rs.getInt("pr_seq"), rs.getInt("par_numero"), rs.getFloat("par_valor"), rs.getFloat("par_valorpago"), dvenc, dpag, rs.getString("par_frmpgto"), cr.getContaReceber(rs.getInt("cr_cod")), dest, rs.getString("par_sequencia"));
                p.setForma(rs.getString("par_frmpgto"));
                if(rs.getString("par_bolnumero") != null)
                    p.setBoleto(new Boleto(rs.getString("par_bolnumero")));
                if(rs.getString("par_chequenumero") != null)
                    p.setCheque(new Cheque(rs.getString("par_chequenumero"), rs.getString("par_chequeconta"), rs.getString("par_chequebanco")));
                if(rs.getString("par_depid") != null)
                    p.setDeposito(new Deposito(rs.getString("par_depid")));
                lista.add(p);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    } 
    
    public boolean atualizar() 
    {        
        List <String> campos = new ArrayList<>(Arrays.asList("pr_seq","par_frmpgto"));
        List <String> valores = new ArrayList<>(Arrays.asList(seq+"", forma));
        if(boleto != null)
        {
            campos.add("par_bolnumero");
            valores.add(boleto.getNumero());
            campos.add("par_chequenumero");
            campos.add("par_chequebanco");
            campos.add("par_chequeconta");
            valores.add("null");
            valores.add("null");
            valores.add("null");
            campos.add("par_depid");
            valores.add("null");
        }
        
        if(cheque != null)
        {
            campos.add("par_chequenumero");
            campos.add("par_chequebanco");
            campos.add("par_chequeconta");
            valores.add(cheque.getNumero());
            valores.add(cheque.getBanco());
            valores.add(cheque.getConta());
            campos.add("par_bolnumero");
            valores.add("null");
            campos.add("par_depid");
            valores.add("null");
        }
        
        if(deposito != null)
        {
            campos.add("par_depid");
            valores.add(deposito.getIdentificacao());
            campos.add("par_bolnumero");
            valores.add("null");
            campos.add("par_chequenumero");
            campos.add("par_chequebanco");
            campos.add("par_chequeconta");
            valores.add("null");
            valores.add("null");
            valores.add("null");
            
        }
        
        return Banco.con.cmdUpdate("parcelarec", campos, valores);
    }
    
    public boolean inserir()
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cr_cod", "par_numero", "par_valor", "par_status", "par_sequencia"));
        List <String> valores = new ArrayList<>(Arrays.asList(contareceber.getCod()+"", numero+"", valor.replace(",", "."), "A", sequencia));
        
        if(data_vencimento != null)
        {
            campos.add("par_dtvencimento");
            valores.add(Funcoes.StringToDate(data_vencimento)+"");
        }
        
        if(Banco.con.cmdInsert("parcelarec", campos, valores))
        {
            setSeq(Banco.con.getMaxPK("parcelarec", "pr_seq", ""));
            return true;
        }
        return false;
    }
    
    public boolean excluir() 
    {    
        Banco.con.cmdDelete("movcaixa", "pr_seq", seq);  //excluindo movimentação da parcela se houver
        return Banco.con.cmdDelete("parcelarec", "pr_seq", seq);   
    }
    
    public boolean desativar()
    {
        List <String> campos = new ArrayList<>(Arrays.asList("pr_seq","par_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(seq+"", "D"));
        return Banco.con.cmdUpdate("parcelarec", campos, valores);
    }

    public boolean receber() 
    {        
        float valorpagoaux = Float.parseFloat(this.valorpago.replace(",", "."));
        float valoraux = Float.parseFloat(this.valor.replace(",", "."));        
        
        List <String> campos = new ArrayList<>(Arrays.asList("pr_seq","par_dtpagamento", "par_valorpago", "par_frmpgto"));
        List <String> valores = new ArrayList<>(Arrays.asList(seq+"", Funcoes.StringToDate(data_pagamento)+"", valorpago.replace(",", "."), forma));
        if(boleto != null)
        {
            campos.add("par_bolnumero");
            valores.add(boleto.getNumero());
        }
        
        if(cheque != null)
        {
            campos.add("par_chequenumero");
            campos.add("par_chequebanco");
            campos.add("par_chequeconta");
            valores.add(cheque.getNumero());
            valores.add(cheque.getBanco());
            valores.add(cheque.getConta());
        }
        
        if(deposito != null)
        {
            campos.add("par_depid");
            valores.add(deposito.getIdentificacao());
        }
        
        if(!Banco.con.cmdUpdate("parcelarec", campos, valores))
            return false;
        
        //verificar se há possibilidade do pagamento ter sido PARCIAL
         if(valorpagoaux < valoraux) //PAGOU A MENOS, PAGAMENTO PARCIAL, GERAR NOVA PARCELA COM O VALOR RESTANTE
         {
            float valornovo = valoraux - valorpagoaux;          
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Recebimento parcial");
            alert.setContentText("Valor da parcela: R$"+valor+"\nValor recebido: R$"+valorpago+"\nValor restante: R$"+Funcoes.ValorMonetario(valornovo));
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            this.valor = Funcoes.ValorMonetario(valornovo);
            alert.showAndWait();
            int seqaux = seq;
            sequencia = "S";
            if(!inserir()) //criando a sequencia da parcela com o valor restante
                return false;
            seq = seqaux;
         }
        
        return true;        
    }   
    
    public boolean estornar()
    {
        String sql = "delete from parcelarec where cr_cod = "+contareceber.getCod()+
                     " and par_numero = "+numero+" and pr_seq > "+seq+" and par_dtestorno is null and par_status = 'A'";  
        Banco.con.manipular(sql);
        //apaguei a ultima parcela (se houver), subsequente a parcela a ser estornada  
        
        List <String> campos = new ArrayList<>(Arrays.asList("pr_seq","par_dtestorno"));
        List <String> valores = new ArrayList<>(Arrays.asList(seq+"", LocalDate.now()+""));
        if(!Banco.con.cmdUpdate("parcelarec", campos, valores))
           return false;
           
        return inserir();
    }
    
    
    
}
