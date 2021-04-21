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
import javafx.scene.control.Alert;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class Caixa {
    private int cod;
    private String status;
    private String entrada, saida, total;
    private String data, hora_abriu, hora_fechou;
    private ArrayList <MovCaixa> movimentacoes_entrada = new ArrayList();
    private ArrayList <MovCaixa> movimentacoes_saida = new ArrayList();
    private Pessoa usuario;

    public Caixa() {
        cod = 0;
        total = "0";
        entrada = "0";
        saida = "0";
        hora_abriu = "";
        hora_fechou = "";
        usuario = new Pessoa();
        data = Funcoes.DateToString(LocalDate.now());
    }

    public Caixa(int cod, float entrada, float saida, float total, LocalDate data, Timestamp hora_abriu, Timestamp hora_fechou, Pessoa usuario, String status) {
        this.cod = cod;
        this.entrada = Funcoes.ValorMonetario(entrada);
        this.saida = Funcoes.ValorMonetario(saida);
        this.total = Funcoes.ValorMonetario(total);
        this.data = Funcoes.DateToString(data);
        if(hora_abriu != null)
           this.hora_abriu = Funcoes.DateToString(hora_abriu);
        if(hora_fechou != null)
           this.hora_fechou = Funcoes.DateToString(hora_fechou);
        this.usuario = usuario;
        this.status = status;
    }

    public Caixa(float entrada, float saida, float total, LocalDate data, Timestamp hora_abriu, Timestamp hora_fechou, Pessoa usuario, String status) {
        this.entrada = Funcoes.ValorMonetario(entrada);
        this.saida = Funcoes.ValorMonetario(saida);
        this.total = Funcoes.ValorMonetario(total);
        this.data = Funcoes.DateToString(data);
        if(hora_abriu != null)
           this.hora_abriu = Funcoes.DateToString(hora_abriu);
        if(hora_fechou != null)
           this.hora_fechou = Funcoes.DateToString(hora_fechou);
        this.usuario = usuario;
        this.status = status;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(float entrada) {
        this.entrada = Funcoes.ValorMonetario(entrada);
    }

    public String getSaida() {
        return saida;
    }
    
    public String getSaldoDia()
    {        
        return Funcoes.ValorMonetario(Float.parseFloat(entrada.replace(",", ".")) - Float.parseFloat(saida.replace(",", ".")));
    }

    public void setSaida(float saida) {
        this.saida = Funcoes.ValorMonetario(saida);
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = Funcoes.ValorMonetario(total);
    }

    public String getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = Funcoes.DateToString(data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHora_abriu() {
        return hora_abriu;
    }

    public void setHora_abriu(String hora_abriu) {
        this.hora_abriu = hora_abriu;
    }

    public String getHora_fechou() {
        return hora_fechou;
    }

    public void setHora_fechou(String hora_fechou) {
        this.hora_fechou = hora_fechou;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }
    
    public void setUsuario(int codigo) {
        this.usuario = usuario.getPessoa(codigo);
    }
    
    public boolean estaAberto()
    {
        return status.equals("Aberto");
    }
    
    public boolean estaAtualizado() //retorna verdadeiro se o respectivo objeto caixa tiver Data igual o dia de hoje
    {
        return Funcoes.StringToDate(data).equals(LocalDate.now());
    }
    
    public boolean temSaldo(float valor)
    {
        return valor <= Float.parseFloat(total.replace(",", "."));
    }    
    
    public ArrayList <MovCaixa> getMovimentacoesEntrada()
    {
        return movimentacoes_entrada;
    }
    
    public ArrayList <MovCaixa> getMovimentacoesSaida()
    {
        return movimentacoes_saida;
    }
    
    public void carregar_dados(String filtro) 
    {
        MovCaixa m = new MovCaixa();
        movimentacoes_entrada.clear();
        movimentacoes_saida.clear();
        
        movimentacoes_entrada.addAll(m.getMovimentacoesEntrada(filtro));
        movimentacoes_saida.addAll(m.getMovimentacoesSaida(filtro));
    }
    
    public boolean aporte(float valor)
    {
        float entrada_aux, total_aux;
        entrada_aux = Float.parseFloat(entrada.replace(",", "."));
        total_aux = Float.parseFloat(total.replace(",", "."));
        entrada_aux += valor;
        total_aux += valor;
        entrada = Funcoes.ValorMonetario(entrada_aux);
        total = Funcoes.ValorMonetario(total_aux);
        return true;
    }
    
    public boolean sangria(float valor)
    {
        if(!temSaldo(valor))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Saldo insuficiente em caixa.");
            alert.setContentText("Não foi possível realizar esta operação.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;
        }
        float saida_aux, total_aux;
        saida_aux = Float.parseFloat(saida.replace(",", "."));
        total_aux = Float.parseFloat(total.replace(",", "."));
        saida_aux += valor;
        total_aux -= valor;
        saida = Funcoes.ValorMonetario(saida_aux);
        total = Funcoes.ValorMonetario(total_aux);
        return true;
    }
    
    public Caixa getCaixa(int cod)
    {
        Caixa c = null;
        Pessoa p = new Pessoa();
        String status = "";
        String sql = "select * from caixa";
        sql += " where cai_cod = "+cod;        
        sql += " ORDER BY cai_cod";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {      
                status = "Fechado";
                if(rs.getString("cai_status").equals("A"))
                    status = "Aberto";
                c = new Caixa(rs.getInt("cai_cod"), rs.getFloat("cai_entrada"), rs.getFloat("cai_saida"), rs.getFloat("cai_total"), rs.getDate("cai_data").toLocalDate(), rs.getTimestamp("cai_horaabriu"), rs.getTimestamp("cai_horafechou"), p.getPessoa(rs.getInt("pes_cod")), status);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return c;
    }
    
    public Caixa getUltimoCaixa()
    {
        int codigo = Banco.con.getMaxPK("caixa", "cai_cod", "");
        return getCaixa(codigo);
    }
    
    public boolean fechar()
    {
        status = "Fechado";
        hora_fechou = new Timestamp(System.currentTimeMillis())+"";
        List <String> campos = new ArrayList<>(Arrays.asList("cai_cod", "cai_status", "cai_horafechou"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "F", hora_fechou));
        return Banco.con.cmdUpdate("caixa", campos, valores);
    }

    public boolean abrir() 
    {
        status = "Aberto";
        hora_abriu = new Timestamp(System.currentTimeMillis())+"";
        float entrada = 0, saida = 0, total;
        if(estaAtualizado()) //pego os valores de entrada e saida do ultimo caixa
        {
           entrada = Float.parseFloat(this.entrada.replace(",", "."));
           saida = Float.parseFloat(this.saida.replace(",", "."));
        }
        total = Float.parseFloat(this.total.replace(",", "."));
        List <String> campos = new ArrayList<>(Arrays.asList("cai_data","cai_entrada", "cai_saida", "cai_total", "cai_status", "pes_cod", "cai_horaabriu"));
        List <String> valores = new ArrayList<>(Arrays.asList(LocalDate.now()+"", entrada+"", saida+"", total+"", "A", usuario.getCod()+"", hora_abriu));
        if(Banco.con.cmdInsert("caixa", campos, valores))
        {
            setCod(Banco.con.getMaxPK("caixa", "cai_cod", ""));
            return true;
        }
        return false;
        
    }
    
    public boolean atualizar()
    {
        float entrada, saida, total;
        entrada = Float.parseFloat(this.entrada.replace(",", "."));
        saida = Float.parseFloat(this.saida.replace(",", "."));
        total = Float.parseFloat(this.total.replace(",", "."));
        List <String> campos = new ArrayList<>(Arrays.asList("cai_Cod", "cai_entrada", "cai_saida", "cai_total"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", entrada+"", saida+"", total+""));
        return Banco.con.cmdUpdate("caixa", campos, valores);
    }
    
    
}
