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
import siscon.entidades.Cheque;
import siscon.entidades.ContaReceber;
import siscon.entidades.Dependente;
import siscon.entidades.Deposito;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.MovCaixa;
import siscon.entidades.Movimentacao;
import siscon.entidades.ParcelaRec;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrContaReceber {
    
    private ContaReceber conta;

    public CtrContaReceber() {
        conta = new ContaReceber();
    }

    public ContaReceber getConta() {
        return conta;
    }
    
    public void setConta(ContaReceber conta)
    {
        this.conta = conta;
    }
    public void novo()
    {
        conta = new ContaReceber();
    }
    
    public void CarregarDados(int cod)
    {
        conta = conta.getContaReceber(cod);
        if(conta.getNum_parcelas() != 0) //parcelado
           conta.carregar_parcelas();
        
        
    }
    
    public ArrayList <ContaReceber> ListarContas(String filtro)
    {
        String aux = ""; 
        
        
        if(!filtro.equals(""))
        {
            if(Funcoes.isInt(filtro)) //pesquisar por código da conta
                aux = "cr_cod = "+filtro;
            else //descrição ou pessoa
                aux = "(upper(cr_descricao) LIKE '%"+filtro.toUpperCase()+"%'"+ " OR upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%')";
        }
        return conta.getContasReceber(aux);
    } 
    
    public ArrayList <ParcelaRec> GerarParcelas(float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {
        return conta.gerar_parcelas(valor, valor_entrada, numero_parcelas, data_inicio);
    }
    
    public ArrayList <ParcelaRec> AlterarParcela(ObservableList <ParcelaRec> parcs, int indice, LocalDate data_vencimento, float valor)
    {
        return conta.alterar_parcela(parcs, indice, data_vencimento, valor);
    }
    
    public boolean SalvarParcelas(List <ParcelaRec> parcelas, float valor, float valor_entrada)
    {
        conta.setParcelas(new ArrayList <ParcelaRec>(parcelas));
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(parcelas.size());
        conta.setData_vencimento(null);
        return true;
    }
    
    public ArrayList <ParcelaRec> AjustarParcelas(ObservableList <ParcelaRec> parcs, ObservableList indices, float valor, float valorentrada)
    {
        return conta.ajustar_parcelas(parcs, indices, valor, valorentrada);
    }
    
    public boolean Atualizar(String descricao, int pessoa, String observacoes)
    {
        Pessoa p = new Pessoa();
        conta.setDescricao(descricao);
        conta.setPessoa(p.getPessoa(pessoa));
        conta.setObservacao(observacoes);  
        return conta.atualizar();        
    }
    
    public boolean Atualizar(String descricao, int pessoa, String observacoes, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id)
    { //atualizar os dados do pagamento também, além da conta
        Pessoa p = new Pessoa();
        conta.setDescricao(descricao);
        conta.setPessoa(p.getPessoa(pessoa));
        conta.setObservacao(observacoes);  
        
        if(!conta.atualizar())
            return false;

        //vou buscar no banco de dados a parcela referente ao valor à vista ou valor de entrada (se parcelado)
        ParcelaRec pr = conta.getPrimeiraParcela();  
        if(pr == null)
            return false;

        //vou atualizar os dados de pagamento da parcela
        pr.setForma(forma);
        pr.setBoleto(null);
        pr.setCheque(null);
        pr.setDeposito(null);
        //pr.setData_pagamento(Funcoes.DateToString(data_pagamento));
        if(forma.equals("Boleto"))
           pr.setBoleto(new Boleto(bol_numero));
        if(forma.equals("Cheque"))
           pr.setCheque(new Cheque(che_numero, che_conta, che_banco ));
        if(forma.equals("Depósito"))
           pr.setDeposito(new Deposito(dep_id));

        return pr.atualizar(); //atualizando os dados de pagamento da parcela
    }
    
    public boolean Salvar(String descricao, float valor, float valor_entrada, int numero_parcelas, int pessoa, String forma, LocalDate data_vencimento, String observacoes)
    {
        if(!conta.validar_parcelas(conta.getParcelas(), valor, valor_entrada, numero_parcelas))
           return false;
       
        Pessoa p = new Pessoa();
        conta.setDescricao(descricao);
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(numero_parcelas);
        conta.setPessoa(p.getPessoa(pessoa));
        conta.setObservacao(observacoes);  
        conta.setData_vencimento(data_vencimento);
        
        if(!conta.inserir())
            return false;
        
        return conta.inserir_parcelas();
    }
    
    public boolean Salvar_Receber(String descricao, float valor, float valor_entrada, int numero_parcelas, int pessoa, LocalDate data_vencimento, String observacoes, String forma, String bol_numero, String che_numero, String che_conta, String che_banco, String dep_id)
    {
        ParcelaRec pr = null;
        
        if(numero_parcelas == 0) //à vista
        {
            conta.setParcelas(new ArrayList());
            pr = new ParcelaRec(1, valor, valor, data_vencimento, data_vencimento, "", conta, null);
        }
        else //a prazo
        {
            pr = new ParcelaRec(0, valor_entrada, valor_entrada, data_vencimento, data_vencimento, "", conta, null);    
            data_vencimento = null;
        }
        
        
        if(numero_parcelas != 0 && !conta.validar_parcelas(conta.getParcelas(), valor, valor_entrada, numero_parcelas))
           return false;
        
        Pessoa p = new Pessoa();
        conta.setDescricao(descricao);
        conta.setValor(valor);
        conta.setValor_entrada(valor_entrada);
        conta.setNum_parcelas(numero_parcelas);
        conta.setPessoa(p.getPessoa(pessoa));
        conta.setObservacao(observacoes);  
        conta.setData_vencimento(data_vencimento);
        if(!conta.inserir())
            return false;
        
        pr.setContareceber(conta);
        
        if(!pr.inserir())
            return false;
            
        CtrRecebimento ctrrecebimento = new CtrRecebimento();
        ctrrecebimento.setParcela(pr);
        ctrrecebimento.setCaixa(ctrrecebimento.getMovCaixa().getCaixa().getUltimoCaixa()); //na interface eu garanti que o Caixa estará aberto aqui
        if(!ctrrecebimento.receber(Funcoes.StringToDate(pr.getData_pagamento()), Float.parseFloat(pr.getValorpago().replace(",", ".")), forma, bol_numero, che_numero, che_conta, che_banco, dep_id))
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
