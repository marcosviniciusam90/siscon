
package siscon.entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;
import siscon.util.Funcoes;

public class Funcionario extends Pessoa
{
    private LocalDate data_admissao, data_demissao;
    private String salario;

    public Funcionario()
    {
        super();
        data_admissao = null;
        data_demissao = null;
        salario = "";
    }
    
    public Funcionario(int cod, String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, LocalDate data_admissao, LocalDate data_demissao, float salario, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(cod, nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.data_admissao = data_admissao;
        this.data_demissao = data_demissao;
        this.salario = Funcoes.ValorMonetario(salario);
    }
    
    public Funcionario(String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, LocalDate data_admissao, LocalDate data_demissao, float salario, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.data_admissao = data_admissao;
        this.data_demissao = data_demissao;
        this.salario = Funcoes.ValorMonetario(salario);
    }  

    public LocalDate getData_admissao() {
        return data_admissao;
    }

    public void setData_admissao(LocalDate data_admissao) {
        this.data_admissao = data_admissao;
    }

    public LocalDate getData_demissao() {
        return data_demissao;
    }

    public void setData_demissao(LocalDate data_demissao) {
        this.data_demissao = data_demissao;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = Funcoes.ValorMonetario(salario);
    }

    //Override
    public String toString()
    {
        return super.getNome();
    }
}
