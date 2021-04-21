/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

/**
 *
 * @author Marcos Vinícius
 */
public class Cheque {
    private String numero, conta, banco;

    public Cheque() {
    }

    public Cheque(String numero, String conta, String banco) {
        this.numero = numero;
        this.conta = conta;
        this.banco = banco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
    
    
    
}
