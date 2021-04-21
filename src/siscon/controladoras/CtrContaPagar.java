/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javax.imageio.ImageIO;
import siscon.entidades.AcertoCaixa;
import siscon.entidades.Cidade;
import siscon.entidades.Associado;
import siscon.entidades.Boleto;
import siscon.entidades.Caixa;
import siscon.entidades.CartaoCredito;
import siscon.entidades.Cheque;
import siscon.entidades.ContaPagar;
import siscon.entidades.Dependente;
import siscon.entidades.Deposito;
import siscon.entidades.Empresa;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.MovCaixa;
import siscon.entidades.Movimentacao;
import siscon.entidades.ParcelaPag;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrContaPagar {
    
    private ContaPagar conta;

    public CtrContaPagar() {
        conta = new ContaPagar();
    }

    public ContaPagar getConta() {
        return conta;
    }
    
    public void setConta(ContaPagar conta)
    {
        this.conta = conta;
    }
    public void novo()
    {
        conta = new ContaPagar();
    }
    
    public void CarregarDados(int cod)
    {
        conta = conta.getContaPagar(cod);
        if(conta.getNum_parcelas() != 0) //parcelado
           conta.carregar_parcelas();
        
        
    }
    
    public ArrayList <ContaPagar> ListarContas(String filtro)
    {
        String aux = ""; 
        
        
        if(!filtro.equals(""))
        {
            if(Funcoes.isInt(filtro)) //pesquisar por código da conta
                aux = "cp_cod = "+filtro;
            else //descrição ou pessoa
                aux = "(upper(cp_descricao) LIKE '%"+filtro.toUpperCase()+"%'"+ " OR upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%'"+" OR upper(emp_nome) LIKE '%"+filtro.toUpperCase()+"%')";
        }
        return conta.getContasPagar(aux);
    } 
    
    public ArrayList <ParcelaPag> GerarParcelas(float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {
        return conta.gerar_parcelas(valor, valor_entrada, numero_parcelas, data_inicio);
    }
    
    public ArrayList <ParcelaPag> AlterarParcela(ObservableList <ParcelaPag> parcs, int indice, LocalDate data_vencimento, float valor)
    {
        return conta.alterar_parcela(parcs, indice, data_vencimento, valor);
    }
    
    public boolean SalvarParcelas(List <ParcelaPag> parcelas, float valor, float valor_entrada)
    {
        conta.setParcelas(new ArrayList <ParcelaPag>(parcelas));
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(parcelas.size());
        conta.setData_vencimento(null);
        return true;
    }
    
    public ArrayList <ParcelaPag> AjustarParcelas(ObservableList <ParcelaPag> parcs, ObservableList indices, float valor, float valorentrada)
    {
        return conta.ajustar_parcelas(parcs, indices, valor, valorentrada);
    }
    
    public boolean Atualizar(String descricao, int pessoa, int empresa, String observacoes) 
    {
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        conta.setDescricao(descricao);
        
        conta.setPessoa(p.getPessoa(pessoa));        
        conta.setEmpresa(e.getEmpresa(empresa));
        
        conta.setObservacao(observacoes);  
        return conta.atualizar();        
    }
    
    public boolean Atualizar(String descricao, int pessoa, int empresa, String observacoes, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id, int cartao)
    { //atualizar os dados do pagamento também, além da conta
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        conta.setDescricao(descricao);
        
        conta.setPessoa(p.getPessoa(pessoa));        
        conta.setEmpresa(e.getEmpresa(empresa));
        
        conta.setObservacao(observacoes);  
        
        if(!conta.atualizar())
            return false;

        //vou buscar no banco de dados a parcela referente ao valor à vista ou valor de entrada (se parcelado)
        ParcelaPag pp = conta.getPrimeiraParcela();  
        if(pp == null)
            return false;

        //vou atualizar os dados de pagamento da parcela
        pp.setForma(forma);
        pp.setBoleto(null);
        pp.setCheque(null);
        pp.setDeposito(null);
        if(forma.equals("Boleto"))
           pp.setBoleto(new Boleto(bol_numero));
        if(forma.equals("Cheque"))
           pp.setCheque(new Cheque(che_numero, che_conta, che_banco ));
        if(forma.equals("Depósito"))
           pp.setDeposito(new Deposito(dep_id));
        if(forma.equals("Cartão de Crédito"))
        {
            CartaoCredito cc = new CartaoCredito();
            pp.setCartao(cc.getCartaoCredito(cartao));
        }

        return pp.atualizar(); //atualizando os dados de pagamento da parcela
    }
    
    public boolean Salvar(String descricao, float valor, float valor_entrada, int numero_parcelas, int pessoa, int empresa, String forma, LocalDate data_vencimento, String observacoes)
    {
        if(!conta.validar_parcelas(conta.getParcelas(), valor, valor_entrada, numero_parcelas))
           return false;
       
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        conta.setDescricao(descricao);
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(numero_parcelas);
        
        conta.setPessoa(p.getPessoa(pessoa));        
        conta.setEmpresa(e.getEmpresa(empresa));
        
        conta.setObservacao(observacoes);  
        conta.setData_vencimento(data_vencimento);
        
        if(!conta.inserir())
            return false;
        
        return conta.inserir_parcelas();
    }
    
    public boolean Salvar_Pagar(String descricao, float valor, float valor_entrada, int numero_parcelas, int pessoa, int empresa, LocalDate data_vencimento, String observacoes, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id, int cartao)
    {
        ParcelaPag pp = null;
        
        if(numero_parcelas == 0) //à vista
        {
            conta.setParcelas(new ArrayList());
            pp = new ParcelaPag(1, valor, valor, data_vencimento, data_vencimento, "", conta, null);
        }
        else //a prazo
        {
            pp = new ParcelaPag(0, valor_entrada, valor_entrada, data_vencimento, data_vencimento, "", conta, null);    
            data_vencimento = null;
        }
        
        
        if(numero_parcelas != 0 && !conta.validar_parcelas(conta.getParcelas(), valor, valor_entrada, numero_parcelas))
           return false;
        
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        conta.setDescricao(descricao);
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(numero_parcelas);
        
        conta.setPessoa(p.getPessoa(pessoa));        
        conta.setEmpresa(e.getEmpresa(empresa));
        
        conta.setObservacao(observacoes);  
        conta.setData_vencimento(data_vencimento);
        if(!conta.inserir())
            return false;
        
        pp.setContapagar(conta);
        
        if(!pp.inserir())
            return false;
            
        CtrPagamento ctrpagamento = new CtrPagamento();
        ctrpagamento.setParcela(pp);
        ctrpagamento.setCaixa(ctrpagamento.getMovCaixa().getCaixa().getUltimoCaixa()); //na interface eu garanti que o Caixa estará aberto aqui
        if(!ctrpagamento.pagar(Funcoes.StringToDate(pp.getData_pagamento()), Float.parseFloat(pp.getValorpago().replace(",", ".")), forma, bol_numero, che_numero, che_conta, che_banco, dep_id, cartao))
            return false;
        
        
        return conta.inserir_parcelas();
    }
    
    public boolean Desativar()
    {
        if(conta.temParcelaPag())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não é possível desativar a conta.\nVerifique se a conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return false;
        }
        return conta.desativarTudo();
    }
    
    public boolean Excluir()
    {
        if(conta.temParcelaPag())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não é possível excluir a conta.\nVerifique se a conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return false;
        }
        return conta.excluir();
    }
    
}
