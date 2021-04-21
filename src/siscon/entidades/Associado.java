
package siscon.entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;

public class Associado extends Pessoa
{
    private String interfone;

    public Associado()
    {
        super();
        interfone = "";
    }
    
    public Associado(int cod, String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String interfone, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(cod, nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.interfone = interfone;
    }
    
    public Associado(String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String interfone, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
        this.interfone = interfone;
    }  

    public String getInterfone() {
        return interfone;
    }

    public void setInterfone(String interfone) {
        this.interfone = interfone;
    }
    
    //Override
    public String toString()
    {
        return super.getNome();
    }
}
