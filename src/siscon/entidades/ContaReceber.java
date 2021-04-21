/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class ContaReceber {
    private int cod, num_parcelas;
    private String data_vencimento, descricao, valor, observacao, valor_entrada, hora_registro;
    private Pessoa pessoa;
    private ArrayList <ParcelaRec> parcelas = new ArrayList();

    public ContaReceber() {
        cod = 0;
        num_parcelas = 0;
        valor_entrada = "";
        pessoa = new Pessoa();
    }

    public ContaReceber(int cod, int num_parcelas, LocalDate data_vencimento, String descricao, float valor, String observacao, float valor_entrada, Timestamp hora_registro, Pessoa pessoa) {
        this.cod = cod;
        this.num_parcelas = num_parcelas;
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
        this.descricao = descricao;
        this.valor = Funcoes.ValorMonetario(valor);
        this.observacao = observacao;
        this.valor_entrada = Funcoes.ValorMonetario(valor_entrada);
        if(hora_registro != null)
           this.hora_registro = Funcoes.DateToString(hora_registro);
        this.pessoa = pessoa;
    }
    
    public ContaReceber(int num_parcelas, LocalDate data_vencimento, String descricao, float valor, String observacao, float valor_entrada, Timestamp hora_registro, Pessoa pessoa) {
        this.num_parcelas = num_parcelas;
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
        this.descricao = descricao;
        this.valor = Funcoes.ValorMonetario(valor);
        this.observacao = observacao;
        this.valor_entrada = Funcoes.ValorMonetario(valor_entrada);
        if(hora_registro != null)
           this.hora_registro = Funcoes.DateToString(hora_registro);
        this.pessoa = pessoa;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = null;
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = Funcoes.ValorMonetario(valor);
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(float valor_entrada) {
        this.valor_entrada = Funcoes.ValorMonetario(valor_entrada);
    }

    public String getHora_registro() {
        return hora_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getNum_parcelas() {
        return num_parcelas;
    }

    public void setNum_parcelas(int num_parcelas) {
        this.num_parcelas = num_parcelas;
    }
    
    public void carregar_parcelas()
    {
        ParcelaRec p = new ParcelaRec();
        parcelas = p.getParcelas("parcelarec.cr_cod = "+cod+" and par_numero <> 0"); //não quero a parcela referente ao valor de entrada
    }
    
    public boolean temParcelaPag()
    {
        ParcelaRec p = new ParcelaRec();
        return p.getParcelas("parcelarec.cr_cod = " + cod + " and par_dtpagamento is not null").size() > 0;
    }
    
    public ArrayList <ParcelaRec> ajustar_parcelas(ObservableList <ParcelaRec> parcs, ObservableList indices, float valor, float valorentrada)
    {
        //vou ajustar o valor das demais parcelas tomando por base a parcela selecionada
        ArrayList <ParcelaRec> parcelas_aux = new ArrayList();
        float soma = 0;
        for(int i=0; i<parcs.size();i++)
        {
            boolean achou = false;
            for(int k=0; k<indices.size();k++)
                if(i == (int)indices.get(k)) //achou, mesmo indice, parcela foi selecionada
                    achou = true;
            if(!achou)
               soma += Float.parseFloat(parcs.get(i).getValor().replace(",", "."));            
        }
        float novovalor = (valor - valorentrada - soma) / indices.size();
        soma = valorentrada;
        
        for(int i=0; i<parcs.size();i++)
        {
            boolean achou = false;
            for(int k=0; k<indices.size();k++)
                if(i == (int)indices.get(k)) //achou, mesmo indice, parcela foi selecionada
                    achou = true;
            if(!achou)
            {
               parcelas_aux.add(new ParcelaRec(parcs.get(i).getSeq(), parcs.get(i).getNumero(), Float.parseFloat(parcs.get(i).getValor().replace(",", ".")), 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContareceber(), null, parcs.get(i).getSequencia()));
            }
            else
               parcelas_aux.add(new ParcelaRec(parcs.get(i).getSeq(), parcs.get(i).getNumero(), novovalor, 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContareceber(), null, parcs.get(i).getSequencia()));         
            
            soma += Float.parseFloat(parcelas_aux.get(i).getValor().replace(",", "."));
        }
        parcelas_aux.get((int)indices.get(indices.size()-1)).setValor(Float.parseFloat(parcelas_aux.get((int)indices.get(indices.size()-1)).getValor().replace(",", "."))+(valor-soma));
        return parcelas_aux;
    }
    
    public ArrayList <ParcelaRec> gerar_parcelas(float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {
        ArrayList <ParcelaRec> novasparcelas = new ArrayList();
        //vou criar as parcelas da conta tomando por base a data de início e o número de parcelas informado
        float total = valor;
        float soma = valor_entrada;
        valor = (valor - valor_entrada) / numero_parcelas;
        
        for(int i=1; i<=numero_parcelas; i++)
        {
            novasparcelas.add(new ParcelaRec(i, valor, 0, data_inicio.plusMonths(i-1), null, "", null, null));
            soma += Float.parseFloat(novasparcelas.get(i-1).getValor().replace(",", "."));
        }
        
        novasparcelas.get(numero_parcelas-1).setValor(Float.parseFloat(novasparcelas.get(numero_parcelas-1).getValor().replace(",", ".")) + (total-soma));
        return novasparcelas;
    }
    
    public ArrayList <ParcelaRec> alterar_parcela(ObservableList <ParcelaRec> parcs, int indice, LocalDate data_vencimento, float valor)
    {
        ArrayList <ParcelaRec> parcelas_aux = new ArrayList();
        for(int i=0; i<parcs.size(); i++)
        {
            if(i != indice)
            {
               parcelas_aux.add(new ParcelaRec(parcs.get(i).getSeq(), parcs.get(i).getNumero(), Float.parseFloat(parcs.get(i).getValor().replace(",", ".")), 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContareceber(), null, parcs.get(i).getSequencia()));
            }
            else
               parcelas_aux.add(new ParcelaRec(parcs.get(i).getSeq(), parcs.get(i).getNumero(), valor, 0, data_vencimento, null, "", parcs.get(i).getContareceber(), null, parcs.get(i).getSequencia()));
            
        }
        return parcelas_aux;
    }
    
    public float soma_parcelas(List <ParcelaRec> parcelas, float valor_entrada)
    {
        float soma = valor_entrada;
        for(int i=0; i<parcelas.size(); i++)
            soma += Float.parseFloat(parcelas.get(i).getValor().replace(",", "."));
        
        soma = (float)Funcoes.truncate(soma);
        return soma;
    }
    
    public boolean validar_parcelas(List <ParcelaRec> parcelas, float valor, float valor_entrada, int numero_parcelas)
    {
        if(valor_entrada >= valor)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("O valor de entrada é maior ou igual ao valor total.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;            
        }
        if(numero_parcelas != parcelas.size())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("O número de parcelas informado é diferente do número de parcelas geradas.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;            
        }
        
        for(int i=0; i<parcelas.size(); i++)
        {   
            for(int k=i+1; k<parcelas.size(); k++)
                if(Funcoes.StringToDate(parcelas.get(i).getData_vencimento()).isAfter(Funcoes.StringToDate(parcelas.get(k).getData_vencimento())) || Funcoes.StringToDate(parcelas.get(i).getData_vencimento()).isEqual(Funcoes.StringToDate(parcelas.get(k).getData_vencimento())))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Erro");
                    alert.setContentText("Existem parcelas com inconsistência na data de vencimento.");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                    alert.showAndWait();
                    return false; 
                }  
        }
        float soma = soma_parcelas(parcelas, valor_entrada);
        if(soma == valor)
           return true;
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText("A soma do valor das parcelas com o valor de entrada não corresponde ao valor total.\n\nSoma: R$"+Funcoes.ValorMonetario(soma)+"\nTotal: R$"+Funcoes.ValorMonetario(valor));
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
        alert.showAndWait();
        return false;       
    }

    public ArrayList<ParcelaRec> getParcelas() {
        return parcelas;
    }

    public void setParcelas(ArrayList<ParcelaRec> parcelas) {
        this.parcelas = new ArrayList();
        this.parcelas.addAll(parcelas);
    }
    
    public String toString()
    {
        return cod+" - "+descricao;
    }
            
    public ContaReceber getContaReceber(int cod)
    {
        ContaReceber c = null;
        Pessoa p = new Pessoa();
        String sql = "select * from contareceber";
        sql += " where cr_cod = "+cod;        
        sql += " ORDER BY cr_dtvencimento, cr_cod";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                LocalDate dvenc = null;
                if(rs.getDate("cr_dtvencimento") != null)
                   dvenc = rs.getDate("cr_dtvencimento").toLocalDate();
                c = new ContaReceber(rs.getInt("cr_cod"), rs.getInt("cr_numparcelas"), dvenc, rs.getString("cr_descricao"), rs.getFloat("cr_valor"), rs.getString("cr_observacao"), rs.getFloat("cr_valorentrada"), rs.getTimestamp("cr_horaregistro"), p.getPessoa(rs.getInt("pes_cod")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return c;
    }  
    
    public ArrayList <ContaReceber> getContasReceber(String filtro)
    {
        ArrayList <ContaReceber> lista = new ArrayList();
        Pessoa p = new Pessoa();
        String sql = "select * from contareceber INNER JOIN pessoa ON contareceber.pes_cod = pessoa.pes_cod WHERE cr_status = 'A'";
        if(!filtro.equals(""))
            sql += " and "+filtro;       
        sql += " ORDER BY cr_descricao, pes_nome, cr_dtvencimento, cr_cod";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                LocalDate dvenc = null;
                if(rs.getDate("cr_dtvencimento") != null)
                   dvenc = rs.getDate("cr_dtvencimento").toLocalDate();
                lista.add(new ContaReceber(rs.getInt("cr_cod"), rs.getInt("cr_numparcelas"), dvenc, rs.getString("cr_descricao"), rs.getFloat("cr_valor"), rs.getString("cr_observacao"), rs.getFloat("cr_valorentrada"), rs.getTimestamp("cr_horaregistro"), p.getPessoa(rs.getInt("pes_cod"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    } 
    
    public ParcelaRec getPrimeiraParcela() //serve tanto para obter a parcela referente ao valor de entrada, ou se não houver, obtém a primeira parcela da conta
    {
        ParcelaRec pr = new ParcelaRec();
        pr = pr.getParcela("cr_cod = "+cod+" and par_numero = 0 and par_dtestorno is null and par_status = 'A'");
        if(pr == null) //não tem valor de entrada, a conta é à vista
        {
           pr = new ParcelaRec();
           pr = pr.getParcela("cr_cod = "+cod+" and par_numero = 1 and par_dtestorno is null and par_status = 'A'");
        }
        return pr;
    }
    
    public boolean inserir()
    {
        hora_registro = new Timestamp(System.currentTimeMillis())+"";
        List <String> campos = new ArrayList<>(Arrays.asList("cr_descricao", "cr_valor", "cr_observacao", "cr_horaregistro", "pes_cod", "cr_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(descricao, valor.replace(",", "."), observacao, hora_registro, pessoa.getCod()+"", "A"));
        if(num_parcelas != 0) //parcelado
        {
            campos.add("cr_numparcelas");
            valores.add(num_parcelas+"");        
        }
        
        if(data_vencimento != null)
        {
            campos.add("cr_dtvencimento");
            valores.add(Funcoes.StringToDate(data_vencimento)+"");        
        }
        
        if(Float.parseFloat(valor_entrada.replace(",", ".")) != 0)
        {
           campos.add("cr_valorentrada");
           valores.add(valor_entrada.replace(",", "."));  
        }
        
        
        if(Banco.con.cmdInsert("contareceber", campos, valores))
        {
            setCod(Banco.con.getMaxPK("contareceber", "cr_cod", ""));
            return true;
        }
        return false;
    }
    
    public boolean atualizar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cr_cod","cr_descricao", "cr_observacao", "pes_cod"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", descricao, observacao, pessoa.getCod()+""));
        return Banco.con.cmdUpdate("contareceber", campos, valores);
    }
    
    public boolean inserir_parcelas()
    {
        for(int i=0; i<parcelas.size(); i++)
        {
            parcelas.get(i).setContareceber(this);
            if(!parcelas.get(i).inserir())
                return false;
        }
        
        return true;
    }
    
    public boolean excluir() 
    {  
        for(int i=0; i<parcelas.size();i++)
            if(!parcelas.get(i).excluir()) //excluindo as parcelas e suas movimentações (se houver)
               return false;
        return Banco.con.cmdDelete("contareceber", "cr_cod", cod);   
    }
    
    public boolean desativar() //desativando a conta e as parcelas vigentes (desconsidera parcelas estornadas anteriormente)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cr_cod","cr_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("contareceber", campos, valores))
            return false;
        for(int i=0; i<parcelas.size();i++)
            if(!parcelas.get(i).desativar()) 
               return false;
        
        return true;        
    }
    
    public boolean desativarTudo() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cr_cod","cr_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("contareceber", campos, valores))
            return false;
        
        campos = new ArrayList<>(Arrays.asList("cr_cod","par_status"));
        valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("parcelarec", campos, valores))
            return false;        
        
        return true;        
    }

    
    
}
