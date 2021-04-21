/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class MovCaixa {
    private int cod;
    private Caixa caixa = new Caixa();
    private String horario, valor, descricao;
    private AcertoCaixa acertocaixa = new AcertoCaixa();
    private ParcelaPag parcelapag = new ParcelaPag();
    private ParcelaRec parcelarec = new ParcelaRec();

    public MovCaixa() {
    }

    public MovCaixa(int cod, Caixa caixa, Timestamp horario, AcertoCaixa acertocaixa, ParcelaPag parcelapag, ParcelaRec parcelarec) 
    {
        this.cod = cod;
        this.caixa = caixa;
        this.horario = Funcoes.DateToString(horario);
        this.acertocaixa = acertocaixa;
        this.parcelapag = parcelapag;
        this.parcelarec = parcelarec;
        if(parcelarec != null) //parcela recebida 
        {
                valor = parcelarec.getValorpago();
                descricao = "Recebim.: Parcela Cód. "+parcelarec.getSeq();            
        }
        else 
          if(parcelapag != null) //parcela paga
          {
             valor = parcelapag.getValorpago();
             descricao = "Pagam.: Parcela Cód. "+parcelapag.getSeq(); 
          }
          else //acerto caixa
          {
                valor = acertocaixa.getValor();                
                descricao = acertocaixa.getDescricao();
          }
    }

    public MovCaixa(Caixa caixa, Timestamp horario, AcertoCaixa acertocaixa, ParcelaPag parcelapag, ParcelaRec parcelarec) {
        this.caixa = caixa;
        this.horario = Funcoes.DateToString(horario);
        this.acertocaixa = acertocaixa;
        this.parcelapag = parcelapag;
        this.parcelarec = parcelarec;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = Funcoes.DateToString(horario);
    }

    public ParcelaPag getParcelapag() {
        return parcelapag;
    }

    public void setParcelapag(ParcelaPag parcelapag) {
        this.parcelapag = parcelapag;
    }

    public AcertoCaixa getAcertocaixa() {
        return acertocaixa;
    }

    public void setAcertocaixa(AcertoCaixa acertocaixa) {
        this.acertocaixa = acertocaixa;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ParcelaRec getParcelarec() {
        return parcelarec;
    }

    public void setParcelarec(ParcelaRec parcelarec) {
        this.parcelarec = parcelarec;
    }
    
    public MovCaixa getMovimentacao(int cod)
    {
        MovCaixa mc = null;
        Caixa c = new Caixa();
        ParcelaRec pr = new ParcelaRec();
        ParcelaPag pp = new ParcelaPag();
        AcertoCaixa ac = new  AcertoCaixa();
        String sql = "select * from movcaixa where mov_cod = "+cod;
        
        sql += " ORDER BY mov_cod";        
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {     
                mc = new MovCaixa(rs.getInt("mov_cod"), c.getCaixa(rs.getInt("cai_cod")), rs.getTimestamp("mov_horario"), ac.getAcerto(rs.getInt("ac_cod")), pp.getParcela(rs.getInt("pp_seq")), pr.getParcela(rs.getInt("pr_seq")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return mc;
    }
    
    public MovCaixa getMovimentacao(String filtro)
    {
        MovCaixa mc = null;
        Caixa c = new Caixa();
        ParcelaRec pr = new ParcelaRec();
        ParcelaPag pp = new ParcelaPag();
        AcertoCaixa ac = new AcertoCaixa();
        String sql = "select * from movcaixa";
        
        if(!filtro.equals(""))
            sql += " where "+filtro;
        sql += " ORDER BY mov_cod";        
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next()) 
            {                
                mc = new MovCaixa(rs.getInt("mov_cod"), c.getCaixa(rs.getInt("cai_cod")), rs.getTimestamp("mov_horario"), ac.getAcerto(rs.getInt("ac_cod")), pp.getParcela(rs.getInt("pp_seq")), pr.getParcela(rs.getInt("pr_seq")));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return mc;
    }
    
    public ArrayList <MovCaixa> getMovimentacoesEntrada(String filtro)
    {
        Caixa c = new Caixa();
        ParcelaRec pr = new ParcelaRec();
        AcertoCaixa ac = new AcertoCaixa();
        ArrayList <MovCaixa> lista = new ArrayList();
        
        //String sql = "select * from (select pes_cod, MAX(mov_cod) as maior FROM movimentacao group by pes_cod) D INNER JOIN Movimentacao on D.pes_cod = Movimentacao.pes_cod and D.maior = Movimentacao.mov_cod INNER JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod";
        //até aqui vai exibir todos
        String sql = "select mov_cod, caixa.cai_cod, mov_horario, movcaixa.ac_cod, movcaixa.pr_seq from movcaixa INNER JOIN caixa ON movcaixa.cai_cod = caixa.cai_cod INNER JOIN ParcelaRec ON movcaixa.pr_seq = ParcelaRec.pr_seq";
        if(!filtro.equals(""))
            sql += " where "+filtro;
        sql += " UNION ALL select mov_cod, caixa.cai_cod, mov_horario, movcaixa.ac_cod, movcaixa.pr_seq from movcaixa INNER JOIN caixa ON movcaixa.cai_cod = caixa.cai_cod INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod";
        if(!filtro.equals(""))
            sql += " where "+filtro+ "and ac_tipo = 'A'";
        else
            sql += " where ac_tipo = 'A'";
        sql += " ORDER BY mov_cod";
        
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                lista.add(new MovCaixa(rs.getInt("mov_cod"), c.getCaixa(rs.getInt("cai_cod")), rs.getTimestamp("mov_horario"), ac.getAcerto(rs.getInt("ac_cod")), null, pr.getParcela(rs.getInt("pr_seq"))));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public ArrayList <MovCaixa> getMovimentacoesSaida(String filtro)
    {
        Caixa c = new Caixa();
        ParcelaPag pp = new ParcelaPag();
        AcertoCaixa ac = new AcertoCaixa();
        ArrayList <MovCaixa> lista = new ArrayList();
        
        //String sql = "select * from (select pes_cod, MAX(mov_cod) as maior FROM movimentacao group by pes_cod) D INNER JOIN Movimentacao on D.pes_cod = Movimentacao.pes_cod and D.maior = Movimentacao.mov_cod INNER JOIN veiculo ON movimentacao.vei_cod = veiculo.vei_cod";
        //até aqui vai exibir todos
        String sql = "select mov_cod, caixa.cai_cod, mov_horario, movcaixa.ac_cod, movcaixa.pp_seq from movcaixa INNER JOIN caixa ON movcaixa.cai_cod = caixa.cai_cod INNER JOIN ParcelaPag ON movcaixa.pp_seq = ParcelaPag.pp_seq";
        if(!filtro.equals(""))
            sql += " where "+filtro;
        sql += " UNION ALL select mov_cod, caixa.cai_cod, mov_horario, movcaixa.ac_cod, movcaixa.pp_seq from movcaixa INNER JOIN caixa ON movcaixa.cai_cod = caixa.cai_cod INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod";
        if(!filtro.equals(""))
            sql += " where "+filtro+ "and ac_tipo = 'S'";
        else
            sql += " where ac_tipo = 'S'";
        sql += " ORDER BY mov_cod";
        
        
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {                
                lista.add(new MovCaixa(rs.getInt("mov_cod"), c.getCaixa(rs.getInt("cai_cod")), rs.getTimestamp("mov_horario"), ac.getAcerto(rs.getInt("ac_cod")), pp.getParcela(rs.getInt("pp_seq")), null));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean inserir()
    {
        horario = new Timestamp(System.currentTimeMillis())+"";
        List <String> campos = new ArrayList<>(Arrays.asList("cai_cod", "mov_horario"));
        List <String> valores = new ArrayList<>(Arrays.asList(caixa.getCod()+"", horario));
        if(parcelapag != null)
        {
            campos.add("pp_seq");
            valores.add(parcelapag.getSeq()+"");        
        }
        
        if(parcelarec != null)
        {
            campos.add("pr_seq");
            valores.add(parcelarec.getSeq()+"");        
        }
        
        if(acertocaixa != null)
        {
            campos.add("ac_cod");
            valores.add(acertocaixa.getCod()+"");
        }
        if(Banco.con.cmdInsert("movcaixa", campos, valores))
        {
            setCod(Banco.con.getMaxPK("movcaixa", "mov_cod", ""));
            return true;
        }
        return false;
    }

    public boolean excluir() 
    {     
        return Banco.con.cmdDelete("movcaixa", "mov_cod", cod);   
    }
    
    
    
}
