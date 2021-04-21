
package siscon.entidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;

public class Dependente extends Pessoa
{
    private String descricao;
    private Pessoa supervisor;    

    public Dependente()
    {
        super();
        descricao = "";
        supervisor = null;
    }
    
    public Dependente(int cod, String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, Pessoa supervisor, String descricao, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(cod, nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.supervisor = supervisor;
        this.descricao = descricao;
    }
    
    public Dependente(String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, Pessoa supervisor, String descricao, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.supervisor = supervisor;
        this.descricao = descricao;
    }  

    public Pessoa getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Pessoa supervisor) {
        this.supervisor = supervisor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    //Override
    public String toString()
    {
        return super.getNome();
    }
    
    public ArrayList <Dependente> getDependentes(String filtro)
    {
        Cidade cid = new Cidade();
        ArrayList <Dependente> lista = new ArrayList();
        String sql = "select * from pessoa LEFT JOIN cidade ON pessoa.cid_cod = cidade.cid_cod";
        
        //STATUS, somente os ATIVADOS
        if(!filtro.equals(""))
           filtro += " and pes_status = 'A'";
        else
           filtro = "pes_status = 'A'";

        //até aqui vai exibir todos
        if(!filtro.equals(""))
            sql += " where "+filtro;
        
        sql += " order by pes_nome, pes_cpf";
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            while(rs.next()) 
            {
                LocalDate data = null;
                if(rs.getDate("pes_nasc") != null)
                    data = rs.getDate("pes_nasc").toLocalDate();
                Dependente d = new Dependente(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), null, rs.getString("pes_descricao"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                d.setSupervisor(d.getPessoa(rs.getInt("pes_supervisor")));
                lista.add(d);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
}
