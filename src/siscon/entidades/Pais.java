package siscon.entidades;

public class Pais
{
    private int cod;
    private String sigla, nome;

    public Pais()
    {
        this.cod = 0;
        this.sigla = "";
        this.nome = "";
    }
    
    public Pais(int cod, String sigla, String nome)
    {
        this.cod = cod;
        this.sigla = sigla;
        this.nome = nome;
    }

    public int getCod()
    {
        return cod;
    }

    final public void setCod(int cod)
    {
        if (cod > 0)
            this.cod = cod;
    }

    public String getSigla()
    {
        return sigla;
    }

    final public void setSigla(String sigla)
    {
        this.sigla = sigla;
    }

    public String getNome()
    {
        return nome;
    }

    final public void setNome(String nome)
    {
        this.nome = nome;
    }    
}
