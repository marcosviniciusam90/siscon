
package siscon.entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import siscon.util.Banco;

public class Visitante extends Pessoa
{
    public Visitante()
    {
        super();
    }
    
    public Visitante(int cod, String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(cod, nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
    }
    
    public Visitante(String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso)
    {
        super(nome, categoria, cpf, bairro, tipoLog,logradouro, numero, fone, email, cep, cidade, DataNasc, fone2, condutor, usuario, senha, nivel, ultimamov, acesso);
    }  

    //Override
    public String toString()
    {
        return super.getNome();
    }
}
