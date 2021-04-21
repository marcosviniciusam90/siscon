/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.entidades;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import siscon.util.Banco;


public class Pessoa { 
    private int cod, nivel;
    private String nome, categoria, cpf, bairro, tipoLog, logradouro, numero, fone, email, cep, fone2, condutor, usuario, senha, ultimamov, acesso;
    private Cidade cidade;
    private LocalDate DataNasc;
    private ArrayList <Dependente> dependentes = new ArrayList();
    private ArrayList <Veiculo> veiculos = new ArrayList();
    private ArrayList <Lote> lotes = new ArrayList();
    
    public Pessoa() {
        cod = 0;
        nome = "";
        categoria = "";
        this.cpf = "";
        this.bairro = "";
        this.tipoLog = "";
        this.logradouro = "";
        this.numero = "";
        this.fone = "";
        this.email = "";
        this.condutor = "";
        this.usuario = "";
        this.senha = "";
        this.ultimamov = "";
        this.nivel = 0;
        this.cidade = null;
        this.DataNasc = null;
        this.fone2 = "";
        this.acesso = "";
    }
    
    public Pessoa(int cod, String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso) {
        this.cod = cod;
        this.nome = nome;
        this.categoria = categoria;
        this.cpf = cpf;
        this.bairro = bairro;
        this.tipoLog = tipoLog;
        this.logradouro = logradouro;
        this.numero = numero;
        this.fone = fone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.DataNasc = DataNasc;
        this.fone2 = fone2;
        this.condutor = condutor;
        this.usuario = usuario;
        this.senha = senha;
        this.nivel = nivel;
        this.ultimamov = ultimamov;
        setAcesso(acesso);
    }    
    
    public Pessoa(String nome, String categoria, String cpf, String bairro, String tipoLog, String logradouro, String numero, String fone, String email, String cep, Cidade cidade, LocalDate DataNasc, String fone2, String condutor, String usuario, String senha, int nivel, String ultimamov, String acesso) {
        this.nome = nome;
        this.categoria = categoria;
        this.cpf = cpf;
        this.bairro = bairro;
        this.tipoLog = tipoLog;
        this.logradouro = logradouro;
        this.numero = numero;
        this.fone = fone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.DataNasc = DataNasc;
        this.fone2 = fone2;
        this.condutor = condutor;
        this.usuario = usuario;
        this.senha = senha;
        this.nivel = nivel;
        this.ultimamov = ultimamov;
        setAcesso(acesso);
    } 

    public void setDependentes(ArrayList<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public void setLotes(ArrayList<Lote> lotes) {
        this.lotes = lotes;
    }
    
    

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        if(acesso.equals("Livre Acesso"))
           this.acesso = "LA";
        else
          if(acesso.equals("LA"))
             this.acesso = "Livre Acesso";
        
        if(acesso.equals("Mediante Autorização"))
           this.acesso = "MA";
        else
          if(acesso.equals("MA"))
             this.acesso = "Mediante Autorização";
        
        if(acesso.equals("Não Autorizado"))
           this.acesso = "NA";
        else
          if(acesso.equals("NA"))
             this.acesso = "Não Autorizado";
    }    
    
    
    public int getCod()
    {
        return cod;
    }

    final public void setCod(int cod)
    {
        //if (cod > 0)
            this.cod = cod;
    }

    public String getNome()
    {
        return nome;
    }

    final public void setNome(String nome)
    {
        if (nome.length() > 100)
            nome = nome.substring(0, 99);
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }

    final public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }
    

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDate getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(LocalDate DataNasc) {
        this.DataNasc = DataNasc;
    }  

    public String getCpf()
    {
        return cpf;
    }

    final public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }

    public String getBairro()
    {
        return bairro;
    }

    final public void setBairro(String bairro)
    {
        if (bairro.length() > 25)
            bairro = bairro.substring(0, 24);
        this.bairro = bairro;
    }

    public String getTipoLog()
    {
        return tipoLog;
    }

    final public void setTipoLog(String tipoLog)
    {
        if (tipoLog.length() > 10)
            tipoLog = tipoLog.substring(0, 24);
        this.tipoLog = tipoLog;
    }

    public String getLogradouro()
    {
        return logradouro;
    }

    final public void setLogradouro(String logradouro)
    {
        if (logradouro.length() > 50)
            logradouro = logradouro.substring(0, 24);
        this.logradouro = logradouro;
    }

    public String getNumero()
    {
        return numero;
    }

    final public void setNumero(String numero)
    {
        if (numero.length() > 10)
            numero = numero.substring(0, 9);
        this.numero = numero;
    }

    public String getFone()
    {
        return fone;
    }

    final public void setFone(String fone)
    {
        this.fone = fone;
    }

    public String getEmail()
    {
        return email;
    }

    final public void setEmail(String email)
    {
        this.email = email;
    }

    public Cidade getCidade()
    {
        return cidade;
    }

    final public void setCidade(Cidade cidade)
    {
        this.cidade = cidade;
    }  

    public String getUltimamov() {
        return ultimamov;
    }

    public void setUltimamov(String ultimamov) {
        this.ultimamov = ultimamov;
    }
    
    @Override
    public String toString()
    {
        return nome;       
    }

    public String getCondutor() {
        return condutor;
    }

    public void setCondutor(String condutor) {
        this.condutor = condutor;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    public void add(Veiculo vei)
    {
        veiculos.add(vei);
    }
    
    public void add(Dependente dep)
    {
        dependentes.add(dep);
    }
    
    public void add(Lote lote)
    {
        lotes.add(lote);
    }
    
    public ArrayList <Veiculo> getVeiculos()
    {
        return veiculos;
    }
    
    public ArrayList <Dependente> getDependentes()
    {
        return dependentes;
    }
    
    public ArrayList <Lote> getLotes()
    {
        return lotes;
    } 
    
    
    public String ObterDados(int codigo)
    {
        String dados = "";
        Pessoa pessoa = getPessoa(codigo);
        if(pessoa != null)
        {
            dados = "Nome: "+pessoa.getNome()+"\nCategoria: "+pessoa.getCategoria();

            if(pessoa instanceof Dependente)
                dados += "\nDescrição: " + ((Dependente)pessoa).getDescricao() + " de "+((Dependente)pessoa).getSupervisor().getNome().split(" ")[0];

            if(!pessoa.getCpf().equals(""))
                dados += "\nCPF: "+pessoa.getCpf();

            if(!pessoa.getFone().equals("") && !pessoa.getFone2().equals(""))
                dados += "\nTelefone: "+pessoa.getFone() + " / " + pessoa.getFone2();
            else
              if(!pessoa.getFone().equals(""))
                  dados += "\nTelefone: "+pessoa.getFone();
              else
                if(!pessoa.getFone2().equals(""))
                    dados += "\nTelefone: "+pessoa.getFone2();

            if(pessoa instanceof Associado && !(((Associado)(pessoa)).getInterfone().equals("")))
                dados += "\nInterfone: "+(((Associado)(pessoa)).getInterfone());

            if(pessoa instanceof Dependente && ((Dependente)pessoa).getSupervisor() instanceof Associado && !(((Associado)(((Dependente)pessoa).getSupervisor())).getInterfone().equals("")))
                dados += "\nInterfone: "+(((Associado)(((Dependente)pessoa).getSupervisor())).getInterfone());
            
            if(!pessoa.getLogradouro().equals(""))
            {
                dados += "\nEndereço: "+pessoa.getTipoLog() + " "+pessoa.getLogradouro();
                if(!pessoa.getNumero().equals(""))
                   dados += ", "+pessoa.getNumero(); 
                if(!pessoa.getBairro().equals(""))
                    dados += " - "+pessoa.getBairro();
            }
        }
        return dados;
    }
    
    
    
    public boolean login(String usuario, String senha)
    {
        String sql = "select * from pessoa where pes_usuario LIKE '"+usuario+"' and pes_senha LIKE '"+senha+"' and pes_status <> 'D' and pes_acesso not like 'NA'";
        try
        {
            ResultSet rs = Banco.con.consultar(sql);
            if(rs.next()) //achou, login aprovado
            {
               cod = rs.getInt("pes_cod");
               return true;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return false;            
    }
    
    public boolean UsuarioExistente()
    {
        if(usuario.equals(""))
           return false;
        
        String sql = "select * from pessoa where pes_usuario LIKE '"+usuario+"' and pes_cod <> '"+cod+"'";
        try
        {
            ResultSet rs = Banco.con.consultar(sql);
            if(rs.next()) //achou, usuario já existe
               return true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
    
    public boolean ExisteAdministrador() //existe admin além da pessoa corrente?
    {   
        String sql = "select * from pessoa where pes_cod <>'"+cod+"' and pes_nivel = '1' and pes_status <> 'D' and pes_acesso not like 'NA'";
        try
        {
            ResultSet rs = Banco.con.consultar(sql);
            if(rs.next()) //achou, restou algum administrador
               return true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
    
    public boolean VerificarUsuario() 
    {
        String erro = "";
        int min = 5, max = 12;
        if(!usuario.equals("") && !senha.equals("")) //ambos preenchidos
        {
            if(usuario.length() < min || usuario.length() > max)
              erro += "\nO usuário precisa ter de 5 a 12 caracteres.";
            if(senha.length() < min || senha.length() > max)
              erro += "\nA senha precisa ter de 5 a 12 caracteres.";
            
        }
        else
          if(usuario.equals("") && senha.equals("")) //ambos em branco, OK!!!
          {
             nivel = 0; 
          }
          else //um preenchido, um branco
          {
              if(usuario.equals(""))
              {
                 erro += "\nPreencha o campo do usuário, ou deixe todos os campos em branco para apagar o registro.";              
                 if(senha.length() < min || senha.length() > max)                   
                    erro += "\nA senha precisa ter de 5 a 12 caracteres.";  
              }
              
              if(senha.equals(""))
              {
                 erro += "\nPreencha o campo da senha, ou deixe todos os campos em branco para apagar o registro.";
                 if(usuario.length() < min || usuario.length() > max)
                    erro += "\nO usuário precisa ter de 5 a 12 caracteres.";
              }
          }
        
        if(UsuarioExistente()) //verifica se é possível salvar o usuário
                 erro += "\nO nome de usuário já existe.";
        if(!ExisteAdministrador() && nivel != 1) //não existe admin além da pessoa corrente e neste caso estará apagando o usuario ou mudando pro nivel 2
        {
            erro += "\nO sistema precisa ter pelo menos um administrador.\nPreencha todos os campos na guia \"Usuário/senha\" e marque-o como Administrador.";
        }   
        
        if(!erro.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText(erro);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
        }
        return erro.equals("");
    }
    
    
    //PARTE QUE USA BANCO DE DADOS    
    
    public void carregar_dados() //carregar veiculos, dependentes e lotes da pessoa diretamente do BANCO
    {
        Veiculo v = new Veiculo();
        veiculos.addAll(v.getVeiculos("pes_cod = "+cod));
        Lote l = new Lote();
        lotes.addAll(l.getLotes("pes_cod = "+cod));
        Dependente d = new Dependente();
        dependentes.addAll(d.getDependentes("pes_supervisor = "+cod));
    }
    
    
    public Pessoa getPessoa(int cod) 
    {    
        String sql = "select * from pessoa LEFT JOIN cidade ON pessoa.cid_cod = cidade.cid_cod where pes_cod = '"+cod+"'";
        Pessoa p = null;
        Cidade cid = new Cidade();
        ResultSet rs = Banco.con.consultar(sql);
        try
        {
            if(rs.next())
            {
                LocalDate data = null;
                if(rs.getDate("pes_nasc") != null)
                   data = rs.getDate("pes_nasc").toLocalDate();
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Associado"))
                   p = new Associado(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), rs.getString("pes_interfone"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Funcionário"))
                {
                    LocalDate dataad = null, datade = null;
                    if(rs.getDate("pes_dataadmissao") != null)
                       dataad = rs.getDate("pes_dataadmissao").toLocalDate();
                    if(rs.getDate("pes_datademissao") != null)
                       datade = rs.getDate("pes_datademissao").toLocalDate();
                   p = new Funcionario(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), dataad, datade, rs.getFloat("pes_salario"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                }
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Visitante"))
                   p = new Visitante(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                if(rs.getInt("pes_supervisor") != 0) //Dependente
                {
                   p = new Dependente(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), null, rs.getString("pes_descricao"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                   ((Dependente)p).setSupervisor(p.getPessoa(rs.getInt("pes_supervisor")));
                }
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return p;
    }
    
    public ArrayList <Pessoa> getPessoas(String filtro)
    {
        Pessoa p = null;
        Cidade cid = new Cidade();
        ArrayList <Pessoa> lista = new ArrayList();
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
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Associado"))
                   p = new Associado(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), rs.getString("pes_interfone"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Funcionário"))
                {
                    LocalDate dataad = null, datade = null;
                    if(rs.getDate("pes_dataadmissao") != null)
                       dataad = rs.getDate("pes_dataadmissao").toLocalDate();
                    if(rs.getDate("pes_datademissao") != null)
                       datade = rs.getDate("pes_datademissao").toLocalDate();
                   p = new Funcionario(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), dataad, datade, rs.getFloat("pes_salario"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                }
                if(rs.getInt("pes_supervisor") == 0 && rs.getString("pes_categoria").equals("Visitante"))
                   p = new Visitante(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                if(rs.getInt("pes_supervisor") != 0) //Dependente
                {
                    p = new Dependente(rs.getInt("pes_cod"), rs.getString("pes_nome"), rs.getString("pes_categoria"), rs.getString("pes_cpf"), rs.getString("pes_bairro") ,rs.getString("pes_tipo_log"), rs.getString("pes_logradouro"), rs.getString("pes_numero"), rs.getString("pes_fone"), rs.getString("pes_email"), rs.getString("pes_cep"), cid.getCidade(rs.getInt("cid_cod")), data, rs.getString("pes_fone2"), null, rs.getString("pes_descricao"), rs.getString("pes_condutor"), rs.getString("pes_usuario"), rs.getString("pes_senha"), rs.getInt("pes_nivel"), rs.getString("pes_ultimamov"), rs.getString("pes_acesso"));
                    ((Dependente)p).setSupervisor(p.getPessoa(rs.getInt("pes_supervisor")));
                }
                lista.add(p);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public boolean salvar() //gravar a pessoa em Banco
    {
        if(!VerificarUsuario()) 
           return false;
        String nivel = "null";
        if(getNivel() != 0)
            nivel = getNivel()+"";
        
        List <String> campos = new ArrayList<>(Arrays.asList("pes_nome", "pes_categoria", "pes_bairro","pes_tipo_log","pes_logradouro","pes_numero","pes_fone","pes_email","pes_cep","pes_cpf","pes_nasc","pes_fone2", "pes_condutor", "pes_usuario", "pes_senha", "pes_nivel", "pes_status", "pes_ultimamov", "pes_acesso"));
        List <String> valores = new ArrayList<>(Arrays.asList(getNome(), getCategoria(), getBairro(),getTipoLog(),getLogradouro(),getNumero(),getFone(),getEmail(),getCep(),getCpf(),getDataNasc()+"", getFone2(), getCondutor(),getUsuario(), getSenha(), nivel, "A", getUltimamov(), getAcesso()));
                
        if(getCidade() != null)
        {
            campos.add("cid_cod");
            valores.add(getCidade().getCod()+"");
        }
        
        String interfone = "", supervisor = "", descricao = "", condutor = "";
        if(this instanceof Associado)
        {
            interfone = ((Associado)this).getInterfone();
            campos.add("pes_interfone");
            valores.add(interfone);
        }
        if(this instanceof Funcionario)
        {
            if(((Funcionario)this).getData_admissao() != null)
            {
                campos.add("pes_dataadmissao");
                valores.add(((Funcionario)this).getData_admissao()+"");
            }
            if(((Funcionario)this).getData_demissao() != null)
            {
                campos.add("pes_datademissao");
                valores.add(((Funcionario)this).getData_demissao()+"");
            }
            if(!((Funcionario)this).getSalario().equals("0,00"))
            {
                campos.add("pes_salario");
                valores.add(((Funcionario)this).getSalario().replace(",", "."));
            }
        }
        
        if(this instanceof Dependente)
        {
            supervisor = ((Dependente)this).getSupervisor().getCod()+"";
            descricao = ((Dependente)this).getDescricao();
            condutor = ((Dependente)this).getCondutor();
            campos.add("pes_supervisor");
            valores.add(supervisor);
            campos.add("pes_descricao");
            valores.add(descricao);
            if(((Dependente)this).getSupervisor() instanceof Associado)
            {
               interfone = ((Associado)(((Dependente)this).getSupervisor())).getInterfone();
               campos.add("pes_interfone");
               valores.add(interfone);
            }
        }
        
        if(Banco.con.cmdInsert("pessoa", campos, valores))         
        {
            setCod(Banco.con.getMaxPK("pessoa", "pes_cod", ""));
            return true;
        }
        
        return false;    
    }
    
    public boolean alterar() //alterar a pessoa em Banco
    {
        if(!VerificarUsuario()) 
           return false;
        String nivel = "null";
        if(getNivel() != 0)
            nivel = getNivel()+"";
        List <String> campos = new ArrayList<>(Arrays.asList("pes_cod","pes_nome", "pes_categoria", "pes_bairro","pes_tipo_log","pes_logradouro","pes_numero","pes_fone","pes_email","pes_cep","pes_cpf","pes_nasc", "pes_fone2", "pes_condutor", "pes_usuario", "pes_senha", "pes_nivel", "pes_ultimamov", "pes_acesso"));
        List <String> valores = new ArrayList<>(Arrays.asList(getCod()+"", getNome(), getCategoria(), getBairro(),getTipoLog(),getLogradouro(),getNumero(),getFone(),getEmail(),getCep(),getCpf(),getDataNasc()+"", getFone2(), getCondutor(), getUsuario(), getSenha(), nivel, getUltimamov(), getAcesso()));
         
        if(getCidade() != null)
        {
            campos.add("cid_cod");
            valores.add(getCidade().getCod()+"");
        }
        
        String interfone = "", supervisor = "", descricao = "", condutor = "";
        if(this instanceof Associado)
        {
            interfone = ((Associado)this).getInterfone();
            campos.add("pes_interfone");
            valores.add(interfone);
            campos.add("pes_dataadmissao");
            campos.add("pes_datademissao");
            campos.add("pes_salario");               
            valores.add("null");
            valores.add("null");
            valores.add("null");
        }
        
        if(this instanceof Funcionario) 
        {
            campos.add("pes_interfone");
            campos.add("pes_dataadmissao");
            campos.add("pes_datademissao");
            campos.add("pes_salario");  
            valores.add("");
            valores.add(((Funcionario)this).getData_admissao()+"");
            valores.add(((Funcionario)this).getData_demissao()+"");
            valores.add(((Funcionario)this).getSalario().replace(",", "."));
            if(!((Funcionario)this).getSalario().equals("0,00"))
                valores.add(((Funcionario)this).getSalario().replace(",", "."));
            else
                valores.add("null");
        }
        
        if(this instanceof Visitante)
        {  
            campos.add("pes_interfone");
            campos.add("pes_dataadmissao");
            campos.add("pes_datademissao");
            campos.add("pes_salario");               
            valores.add("");
            valores.add("null");
            valores.add("null");
            valores.add("null");
        }
        
        
        if(this instanceof Dependente)
        {
            supervisor = ((Dependente)this).getSupervisor().getCod()+"";
            descricao = ((Dependente)this).getDescricao();
            condutor = ((Dependente)this).getCondutor();
            campos.add("pes_supervisor");
            valores.add(supervisor);
            campos.add("pes_descricao");
            valores.add(descricao);
            campos.add("pes_interfone");
            if(getCategoria().equals("Associado"))
            {
               interfone = ((Associado)(((Dependente)this).getSupervisor())).getInterfone();               
               valores.add(interfone);
            }
            else
               valores.add("");
        }
        
        if(Banco.con.cmdUpdate("pessoa", campos, valores))         
        {
            return true;
        }
        
        return false;    
    }
    
    public boolean excluir() 
    {     
        if(!ExisteAdministrador())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não foi possível excluir o registro desta pessoa.\nO sistema precisa ter pelo menos um administrador.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;
        }
        for(Veiculo v:veiculos)
            v.excluir();
        for(Lote l:lotes)
            l.excluir();
        for(Dependente d:dependentes)
            d.excluir();
        if(Banco.con.cmdDelete("pessoa", "pes_cod", cod))         
        {
            return true;
        }        
        return false;    
    }
    
    public boolean desativar() 
    {     
        if(!(this instanceof Dependente) && !ExisteAdministrador())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não foi possível desativar o registro desta pessoa.\nO sistema precisa ter pelo menos um administrador.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return false;
        }
        for(Veiculo v:veiculos)
            if(!v.desativar())
                return false;
        for(Lote l:lotes)
            if(!l.desativar())
                return false;
        for(Dependente d:dependentes)
            if(!d.desativar())
                return false;
        
        List <String> campos = new ArrayList<>(Arrays.asList("pes_cod","pes_status"));
        List <String> valores = new ArrayList<>(Arrays.asList(cod+"", "D"));
        return Banco.con.cmdUpdate("pessoa", campos, valores);
    }
    
    
    public boolean excluir_dependentes()
    {
        Dependente d = new Dependente();
        boolean achou;
        ArrayList <Dependente> dependentes_banco = d.getDependentes("pes_supervisor = "+cod);
        for(Dependente aux:dependentes_banco)
        {
            achou = false;
            for(Dependente dep:dependentes)
                if(!achou && dep.getCod() != 0 && dep.getCod() == aux.getCod()) //achou, não precisa excluí-lo
                    achou = true;
            if(!achou) //não achou o dependente anterior q estava em banco nesta nova tabela, preciso excluí-lo
                if(!aux.desativar())
                    return false;
        }
        return true;
    }
    
    public boolean excluir_veiculos()
    {
        Veiculo v = new Veiculo();
        boolean achou;
        ArrayList <Veiculo> veiculos_banco = v.getVeiculos("pes_cod = "+cod);
        for(Veiculo aux:veiculos_banco)
        {
            achou = false;
            for(Veiculo vei:veiculos)
                if(!achou && vei.getCod() != 0 && vei.getCod() == aux.getCod()) //achou, não precisa excluí-lo
                    achou = true;
            if(!achou) //não achou o dependente anterior q estava em banco nesta nova tabela, preciso excluí-lo
                if(!aux.desativar())
                    return false;
        }
        return true;
    }
    
    public boolean excluir_lotes()
    {
        Lote l = new Lote();
        boolean achou;
        ArrayList <Lote> lotes_banco = l.getLotes("pes_cod = "+cod);
        for(Lote aux:lotes_banco)
        {
            achou = false;
            for(Lote lot:lotes)
                if(!achou && lot.getCod() != 0 && lot.getCod() == aux.getCod()) //achou, não precisa excluí-lo
                    achou = true;
            if(!achou) //não achou o dependente anterior q estava em banco nesta nova tabela, preciso excluí-lo
                if(!aux.desativar())
                    return false;
        }
        return true;
    }

    public boolean salvarimagem(BufferedImage img) {
        String sql = "";
        if(img != null)
        {
            try
            {
                sql = "update pessoa set pes_foto= ? where pes_cod = ?";                
                PreparedStatement st = Banco.con.getConnect().prepareStatement(sql);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", baos);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                st.setBinaryStream(1, is, baos.toByteArray().length); //substituri ? numero 1 pela imagem
                st.setInt(2, getCod());

                st.executeUpdate();
                return true;
            }
            catch(Exception ex)
            {
                System.out.println("Erro: " + ex.getMessage());
                return false;
            }
        }
        else
        {
            try 
            {
                sql = "update pessoa set pes_foto= ' ' where pes_cod = " + getCod();                
                return Banco.con.manipular(sql);           
            } 
            catch (Exception ex) 
            {
                System.out.println("Erro: " + ex.getMessage());
                return false;
            }   
        }
    }

    public BufferedImage lerimagem(int cod) {
        BufferedImage bimg = null;
        String sql = "", campo = "";
        
        try
        {     
            sql = "select pes_foto from pessoa where pes_cod = " + cod;
            campo = "pes_foto";
            PreparedStatement ps = Banco.con.getConnect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                byte bytes[] = rs.getBytes(campo);
                InputStream is = new ByteArrayInputStream(bytes);
                bimg = ImageIO.read(is);
            }
        }
        catch(Exception ex)
        {
            //System.out.println("Erro: " + ex.getMessage());                    
        }
        return bimg;
    }
    
    public boolean salvar_dependentes()
    {
        for(Dependente d:dependentes)
        {
            d.setCategoria(getCategoria());
            d.setBairro(getBairro());
            d.setTipoLog(getTipoLog());
            d.setLogradouro(getLogradouro());
            d.setNumero(getNumero());
            d.setCep(getCep());
            d.setSupervisor(this);
            d.setCidade(getCidade());
            d.setAcesso(acesso);
            d.setAcesso(d.getAcesso());
            d.setUltimamov(ultimamov);
            if(!d.salvar())
                return false;
        }
        return true;
    }
    
    public boolean salvar_veiculos()
    {
        for(Veiculo v:veiculos)
        {
            v.setPessoa(this);
            if(!v.salvar())
               return false;
        }
        return true;
    }
    
    public boolean salvar_lotes()
    {
        for(Lote l:lotes)
        {
            l.setPessoa(this);
            if(!l.salvar())
                return false;
        }
        return true;
    }
    
    public boolean alterar_dependentes()
    {
        for(Dependente d:dependentes)
        {
            d.setCategoria(getCategoria());
            if(d.getCod() == 0) //o dependente tá sendo adicionado durante a alteração da PESSOA
            { //neste caso, recebe o mesmo endereço do seu supervisor
                d.setBairro(getBairro());
                d.setTipoLog(getTipoLog());
                d.setLogradouro(getLogradouro());
                d.setNumero(getNumero());
                d.setCep(getCep());
                d.setCidade(getCidade());                    
                d.setSupervisor(this);
                d.setAcesso(acesso);
                d.setAcesso(d.getAcesso());
                d.setUltimamov(ultimamov);
                if(!d.salvar())
                    return false;
            } 
            else
            {
               d.setAcesso(d.getAcesso());
               if(!d.alterar())
                   return false;
                       
            }
        }
        return true;
    }
    
    public boolean alterar_veiculos()
    {
        for(Veiculo v:veiculos)
        {
            if(v.getCod() == 0)
            {
               v.setPessoa(this);
               if(!v.salvar())
                   return false;
            }
            else
               if(!v.alterar())
                  return false;
        }
        return true;
    }
    
    public boolean alterar_lotes()
    {
        for(Lote l:lotes)
        {
            if(l.getCod() == 0)
            {
               l.setPessoa(this);
               if(!l.salvar())
                   return false;
            }
            else
               if(!l.alterar())
                   return false;
        }
        return true;
    }
    
}
