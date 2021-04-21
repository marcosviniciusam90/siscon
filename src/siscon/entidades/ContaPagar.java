/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
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
public class ContaPagar {
    private int cod, num_parcelas;
    private String data_vencimento, descricao, valor, observacao, valor_entrada, hora_registro, pessoaempresa;
    private Pessoa pessoa;
    private Empresa empresa;
    private ArrayList <ParcelaPag> parcelas = new ArrayList();

    public ContaPagar() {
        cod = 0;
        num_parcelas = 0;
        valor_entrada = "";
        pessoa = new Pessoa();
        empresa = new Empresa();
    }

    public ContaPagar(int cod, int num_parcelas, LocalDate data_vencimento, String descricao, float valor, String observacao, float valor_entrada, Timestamp hora_registro)
    {
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
    }
    
    public ContaPagar(int num_parcelas, LocalDate data_vencimento, String descricao, float valor, String observacao, float valor_entrada, Timestamp hora_registro)
    {
        this.num_parcelas = num_parcelas;
        if(data_vencimento != null)
           this.data_vencimento = Funcoes.DateToString(data_vencimento);
        this.descricao = descricao;
        this.valor = Funcoes.ValorMonetario(valor);
        this.observacao = observacao;
        this.valor_entrada = Funcoes.ValorMonetario(valor_entrada);
        if(hora_registro != null)
           this.hora_registro = Funcoes.DateToString(hora_registro);
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
        if(pessoa != null)
           pessoaempresa = pessoa.getNome();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        if(empresa != null)
           pessoaempresa = empresa.getNome();
    }
    
    
    public int getNum_parcelas() {
        return num_parcelas;
    }

    public void setNum_parcelas(int num_parcelas) {
        this.num_parcelas = num_parcelas;
    }
    
    public void carregar_parcelas()
    {
        ParcelaPag p = new ParcelaPag();
        parcelas = p.getParcelas("parcelapag.cp_cod = "+cod+" and par_numero <> 0"); //não quero a parcela referente ao valor de entrada
    }
    
    public boolean temParcelaPag()
    {
        ParcelaPag p = new ParcelaPag();
        return p.getParcelas("parcelapag.cp_cod = " + cod + " and par_dtpagamento is not null").size() > 0;
    }
    
    public ArrayList <ParcelaPag> ajustar_parcelas(ObservableList <ParcelaPag> parcs, ObservableList indices, float valor, float valorentrada)
    {
        //vou ajustar o valor das demais parcelas tomando por base a parcela selecionada
        ArrayList <ParcelaPag> parcelas_aux = new ArrayList();
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
               parcelas_aux.add(new ParcelaPag(parcs.get(i).getSeq(), parcs.get(i).getNumero(), Float.parseFloat(parcs.get(i).getValor().replace(",", ".")), 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContapagar(), null, parcs.get(i).getSequencia()));
            }
            else
               parcelas_aux.add(new ParcelaPag(parcs.get(i).getSeq(), parcs.get(i).getNumero(), novovalor, 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContapagar(), null, parcs.get(i).getSequencia()));         
            
            soma += Float.parseFloat(parcelas_aux.get(i).getValor().replace(",", "."));
        }
        parcelas_aux.get((int)indices.get(indices.size()-1)).setValor(Float.parseFloat(parcelas_aux.get((int)indices.get(indices.size()-1)).getValor().replace(",", "."))+(valor-soma));
        return parcelas_aux;
    }
            
    public ArrayList <ParcelaPag> gerar_parcelas(float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {
        ArrayList <ParcelaPag> novasparcelas = new ArrayList();
        //vou criar as parcelas da conta tomando por base a data de início e o número de parcelas informado
        float total = valor;
        float soma = valor_entrada;
        valor = (valor - valor_entrada) / numero_parcelas;
        
        for(int i=1; i<=numero_parcelas; i++)
        {            
            novasparcelas.add(new ParcelaPag(i, valor, 0, data_inicio.plusMonths(i-1), null, "", null, null));
            soma += Float.parseFloat(novasparcelas.get(i-1).getValor().replace(",", "."));
        }
        
        novasparcelas.get(numero_parcelas-1).setValor(Float.parseFloat(novasparcelas.get(numero_parcelas-1).getValor().replace(",", ".")) + (total-soma));
        return novasparcelas;
    }
    
    public ArrayList <ParcelaPag> alterar_parcela(ObservableList <ParcelaPag> parcs, int indice, LocalDate data_vencimento, float valor)
    {
        ArrayList <ParcelaPag> parcelas_aux = new ArrayList();
        for(int i=0; i<parcs.size(); i++)
        {
            if(i != indice)
            {
               parcelas_aux.add(new ParcelaPag(parcs.get(i).getSeq(), parcs.get(i).getNumero(), Float.parseFloat(parcs.get(i).getValor().replace(",", ".")), 0, Funcoes.StringToDate(parcs.get(i).getData_vencimento()), null, "", parcs.get(i).getContapagar(), null, parcs.get(i).getSequencia()));
            }
            else
               parcelas_aux.add(new ParcelaPag(parcs.get(i).getSeq(), parcs.get(i).getNumero(), valor, 0, data_vencimento, null, "", parcs.get(i).getContapagar(), null, parcs.get(i).getSequencia()));
            
        }
        return parcelas_aux;
    }

    public float soma_parcelas(List <ParcelaPag> parcelas, float valor_entrada)
    {
        float soma = valor_entrada;
        for(int i=0; i<parcelas.size(); i++)
            soma += Float.parseFloat(parcelas.get(i).getValor().replace(",", "."));
        
        soma = (float)Funcoes.truncate(soma);
        return soma;
    }
    
    public boolean validar_parcelas(List <ParcelaPag> parcelas, float valor, float valor_entrada, int numero_parcelas)
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

    public ArrayList<ParcelaPag> getParcelas() {
        return parcelas;
    }

    public void setParcelas(ArrayList<ParcelaPag> parcelas) {
        this.parcelas = new ArrayList();
        this.parcelas.addAll(parcelas);
    }

    public String getPessoaempresa() {
        return pessoaempresa;
    }
    
    public String toString()
    {
        return cod+" - "+descricao;
    }
            
    public ContaPagar getContaPagar(int cod)
    {
        ContaPagar c = null;
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        String sql = "select * from contapagar";
        sql += " where cp_cod = "+cod;        
        sql += " ORDER BY cp_dtvencimento, cp_cod";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                LocalDate dvenc = null;
                if(rs.getDate("cp_dtvencimento") != null)
                   dvenc = rs.getDate("cp_dtvencimento").toLocalDate();
                c = new ContaPagar(rs.getInt("cp_cod"), rs.getInt("cp_numparcelas"), dvenc, rs.getString("cp_descricao"), rs.getFloat("cp_valor"), rs.getString("cp_observacao"), rs.getFloat("cp_valorentrada"), rs.getTimestamp("cp_horaregistro"));
                if(rs.getInt("pes_cod") != 0)
                    c.setPessoa(p.getPessoa(rs.getInt("pes_cod")));
                if(rs.getInt("emp_cod") != 0)
                    c.setEmpresa(e.getEmpresa(rs.getInt("emp_cod")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return c;
    }  
    
    public ArrayList <ContaPagar> getContasPagar(String filtro)
    {
        ArrayList <ContaPagar> lista = new ArrayList();
        ContaPagar cp = null;
        Pessoa p = new Pessoa();
        Empresa e = new Empresa();
        String sql = "select * from contapagar FULL JOIN pessoa ON contapagar.pes_cod = pessoa.pes_cod FULL JOIN empresa ON contapagar.emp_cod = empresa.emp_cod WHERE cp_status = 'A'";
        if(!filtro.equals(""))
            sql += " and "+filtro;       
        sql += " ORDER BY cp_descricao, cp_dtvencimento, cp_cod";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                LocalDate dvenc = null;
                if(rs.getDate("cp_dtvencimento") != null)
                   dvenc = rs.getDate("cp_dtvencimento").toLocalDate();
                cp = new ContaPagar(rs.getInt("cp_cod"), rs.getInt("cp_numparcelas"), dvenc, rs.getString("cp_descricao"), rs.getFloat("cp_valor"), rs.getString("cp_observacao"), rs.getFloat("cp_valorentrada"), rs.getTimestamp("cp_horaregistro"));
                if(rs.getInt("pes_cod") != 0)
                    cp.setPessoa(p.getPessoa(rs.getInt("pes_cod")));
                if(rs.getInt("emp_cod") != 0)
                    cp.setEmpresa(e.getEmpresa(rs.getInt("emp_cod")));
                lista.add(cp);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    } 
    
    public ParcelaPag getPrimeiraParcela() //serve tanto para obter a parcela referente ao valor de entrada, ou se não houver, obtém a primeira parcela da conta
    {
        ParcelaPag pp = new ParcelaPag();
        pp = pp.getParcela("cp_cod = "+cod+" and par_numero = 0 and par_dtestorno is null and par_status = 'A'");
        if(pp == null) //não tem valor de entrada, a conta é à vista
        {
           pp = new ParcelaPag();
           pp = pp.getParcela("cp_cod = "+cod+" and par_numero = 1 and par_dtestorno is null and par_status = 'A'");
        }
        return pp;
    }
    
    public boolean inserir()
    {
        hora_registro = new Timestamp(System.currentTimeMillis())+"";
        List <String> campos = new ArrayList<>(Arrays.asList("cp_descricao", "cp_valor", "cp_observacao", "cp_horaregistro", "cp_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(descricao, valor.replace(",", "."), observacao, hora_registro, "A"));
        
        if(pessoa != null)
        {
            campos.add("pes_cod");
            valores.add(pessoa.getCod()+"");
        }
        if(empresa != null)
        {
            campos.add("emp_cod");
            valores.add(empresa.getCod()+"");
        }
        if(num_parcelas != 0) //parcelado
        {
            campos.add("cp_numparcelas");
            valores.add(num_parcelas+"");        
        }
        
        if(data_vencimento != null)
        {
            campos.add("cp_dtvencimento");
            valores.add(Funcoes.StringToDate(data_vencimento)+"");        
        }
        
        if(Float.parseFloat(valor_entrada.replace(",", ".")) != 0)
        {
           campos.add("cp_valorentrada");
           valores.add(valor_entrada.replace(",", "."));  
        }
        
        
        if(Banco.con.cmdInsert("contapagar", campos, valores))
        {
            setCod(Banco.con.getMaxPK("contapagar", "cp_cod", ""));
            return true;
        }
        return false;
    }
    
    public boolean atualizar() 
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cp_cod","cp_descricao", "cp_observacao"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", descricao, observacao));
        if(pessoa != null)
        {
            campos.add("pes_cod");
            valores.add(pessoa.getCod()+"");
            campos.add("emp_cod");
            valores.add("null");
        }
        if(empresa != null)
        {
            campos.add("emp_cod");
            valores.add(empresa.getCod()+"");
            campos.add("pes_cod");
            valores.add("null");
        }
        return Banco.con.cmdUpdate("contapagar", campos, valores);
    }
    
    public boolean inserir_parcelas()
    {
        for(int i=0; i<parcelas.size(); i++)
        {
            parcelas.get(i).setContapagar(this);
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
        return Banco.con.cmdDelete("contapagar", "cp_cod", cod);   
    }
    
    public boolean desativar() //desativando a conta e as parcelas vigentes (desconsidera parcelas estornadas anteriormente)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cp_cod","cp_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("contapagar", campos, valores))
            return false;
        for(int i=0; i<parcelas.size();i++)
            if(!parcelas.get(i).desativar()) 
               return false;
        
        return true;        
    }
    
    public boolean desativarTudo() //desativando a conta e todas as parcelas (parcelas estornadas também são desativadas)
    {
        List <String> campos = new ArrayList<>(Arrays.asList("cp_cod","cp_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("contapagar", campos, valores))
            return false;
        
        campos = new ArrayList<>(Arrays.asList("cp_cod","par_status"));
        valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        if(!Banco.con.cmdUpdate("parcelapag", campos, valores))
            return false;        
        
        return true;        
    }

    
    
}
