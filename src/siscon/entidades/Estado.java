package siscon.entidades;

public class Estado
{
    private int cod;
    private String sigla, nome;
    private Pais pais;

    public Estado()
    {
        cod = 0;
        sigla = "";
        nome = "";
        pais = null;
    }
    
    public Estado(int cod, String sigla, String nome, Pais pais)
    {
        this.cod = cod;
        this.sigla = sigla;
        this.nome = nome;
        this.pais = pais;
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
        if (nome.length() > 50)
            nome = nome.substring(0, 49);
        this.nome = nome;
    }

    public Pais getPais()
    {
        return pais;
    }

    final public void setPais(Pais pais)
    {
        this.pais = pais;
    }
    
    public String toString()
    {
        return sigla;
    }
    
    
    
    
}
