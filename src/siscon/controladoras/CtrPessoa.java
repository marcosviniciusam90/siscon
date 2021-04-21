/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import siscon.entidades.Cidade;
import siscon.entidades.Associado;
import siscon.entidades.Dependente;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrPessoa {
    
    private Pessoa pessoa;
    private Pessoa usuario;

    public CtrPessoa() {
        pessoa = new Pessoa();
        usuario = new Pessoa();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    
    public Pessoa getUsuario()
    {
        return usuario;
    }
    
    public void setUsuario(int codigo)
    {
        usuario = usuario.getPessoa(codigo);
    }
    
    public void novo()
    {
        pessoa = new Pessoa();
    }
    
    public int login(String usuario, String senha) { 
        if(this.usuario.login(usuario, senha))
        {
           this.usuario = this.usuario.getPessoa(this.usuario.getCod()); //já pego os dados completo do usuário como sendo uma Pessoa
           return this.usuario.getNivel();
        }
        return 0; //retorna o nível do usuário se encontrar, se não retorna 0
    }
    
    public void AdicionarVeiculo(String categoria, String marca, String modelo, String cor, String placa)
    {
        Veiculo v = new Veiculo();
        v.setModelo(modelo);
        v.setPlaca(placa);
        v.setCategoria(v.getCategoria().getCategoria(categoria));
        v.setMarca(v.getMarca().getMarca(marca));
        v.setCor(v.getCor().getCor(cor));
        v.setUltimamov("E");
        v.setAutorizado("S");
        pessoa.add(v);
    }
    
    public void AlterarVeiculo(int indice, String categoria, String marca, String modelo, String cor, String placa)
    {
        Veiculo v = new Veiculo();
        pessoa.getVeiculos().get(indice).setModelo(modelo);
        pessoa.getVeiculos().get(indice).setPlaca(placa);
        pessoa.getVeiculos().get(indice).setCategoria(v.getCategoria().getCategoria(categoria));
        pessoa.getVeiculos().get(indice).setMarca(v.getMarca().getMarca(marca));
        pessoa.getVeiculos().get(indice).setCor(v.getCor().getCor(cor));
    }
    
    public void ExcluirVeiculo(int indice)
    {
        pessoa.getVeiculos().remove(indice);
    }
    
    public void AdicionarDependente(String nome, String descricao, String condutor)
    {
        Dependente d = new Dependente();
        d.setNome(nome);
        d.setDescricao(descricao);
        d.setCondutor(condutor);
        d.setUltimamov("E");
        
        pessoa.add(d);
    }
    
    public void AlterarDependente(int indice, String nome, String descricao, String condutor)
    {
        pessoa.getDependentes().get(indice).setNome(nome);
        pessoa.getDependentes().get(indice).setDescricao(descricao);
        pessoa.getDependentes().get(indice).setCondutor(condutor);
        
    }
    
    public void ExcluirDependente(int indice)
    {
        pessoa.getDependentes().remove(indice);
    }
    
    public void AdicionarLote(int numero, int quadra, String descricao, String residencia)
    {
        Lote l = new Lote();
        l.setNumero(numero);
        l.setQuadra(quadra);
        l.setDescricao(descricao);
        l.setResidencia(residencia);
        
        pessoa.add(l);
    }
    
    public void AlterarLote(int indice, int numero, int quadra, String descricao, String residencia)
    {
        pessoa.getLotes().get(indice).setNumero(numero);
        pessoa.getLotes().get(indice).setQuadra(quadra);
        pessoa.getLotes().get(indice).setDescricao(descricao);
        pessoa.getLotes().get(indice).setResidencia(residencia);
    }
    
    public void ExcluirLote(int indice)
    {
        pessoa.getLotes().remove(indice);
    }
    
    public ArrayList <Pessoa> ListarAssociados(String filtro)
    {
        String aux = "pes_categoria LIKE 'Associado'"; 
        if(!filtro.equals(""))
            aux += " and upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarCondutores(String filtro, String categoria)
    {
        String aux = "pes_condutor LIKE 'Sim'"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_condutor LIKE 'Sim'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarCondutoresFora(String filtro, String categoria)
    { 
        String aux = "pes_condutor LIKE 'Sim' and pes_ultimamov = 'S'"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'S'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'S'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'S'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    }
    
    public ArrayList <Pessoa> ListarCondutoresDentro(String filtro, String categoria)
    { 
        String aux = "pes_condutor LIKE 'Sim' and pes_ultimamov = 'E'"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'E'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'E'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_condutor LIKE 'Sim' and pes_ultimamov = 'E'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    }
    
    public ArrayList <Pessoa> ListarDependentes(String filtro, String categoria)
    {
        String aux = "pes_supervisor is not null"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_supervisor is not null";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_supervisor is not null";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_supervisor is not null";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarSupervisores(String filtro, String categoria)
    {
        String aux = "pes_supervisor is null"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_supervisor is null";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_supervisor is null";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_supervisor is null";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarPossiveisSupervisores(String filtro, String categoria)
    {
        String aux = "pes_categoria NOT LIKE 'Funcionário' and pes_supervisor is null"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_categoria NOT LIKE 'Funcionário' and pes_supervisor is null";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_categoria NOT LIKE 'Funcionário' and pes_supervisor is null";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_categoria NOT LIKE 'Funcionário' and pes_supervisor is null";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarTodosDentro(String filtro, String categoria)
    {
        String aux = "pes_ultimamov = 'E'"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_ultimamov = 'E'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_ultimamov = 'E'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_ultimamov = 'E'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    }  
    
    public ArrayList <Pessoa> ListarTodosFora(String filtro, String categoria)
    {
        String aux = "pes_ultimamov = 'S'"; 
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%' and pes_ultimamov = 'S'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"' and pes_ultimamov = 'S'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"' and pes_ultimamov = 'S'";

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");
        return pessoa.getPessoas(aux);
    } 
    
    public ArrayList <Pessoa> ListarTodos(String filtro, String categoria)
    {
        String aux = ""; 
        
        if(categoria.equals("Exibir todos"))
           categoria = "";
        if(!filtro.equals("") && categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+ filtro.toUpperCase()+"%'";
        if(filtro.equals("") && !categoria.equals(""))
            aux = "pes_categoria LIKE '"+ categoria+"'";
        if(!filtro.equals("") && !categoria.equals(""))
            aux = "upper(pes_nome) LIKE '%"+filtro.toUpperCase()+"%' and pes_categoria LIKE '"+ categoria+"'";
        

        if(Funcoes.isFloat(filtro) || (filtro.contains("-") && filtro.contains("."))) //cpf
           aux = aux.replace("upper(pes_nome)", "pes_cpf");        
               
        return pessoa.getPessoas(aux);
    } 

    public boolean Salvar(String nome, String categoria, String cpf, String bairro, String tipo_log, String logradouro, String num, String telefone, String email, String cep, String cidade, String uf, LocalDate data, String telefone2, String interfone, LocalDate data_admissao, LocalDate data_demissao, String salario, String condutor, String usuario, String senha, int nivel, int codigo_supervisor, String descricao, String ultima_mov, String acesso) {
        if(logradouro.equals(""))
            tipo_log = num = "";
        if(!categoria.equals("Visitante")) 
        {
            pessoa.setCod(0);
            pessoa.setNivel(nivel);
            pessoa.setUsuario(usuario);
            pessoa.setSenha(senha);
            if(!pessoa.VerificarUsuario())
               return false;
            nivel = pessoa.getNivel();            
        }
        Cidade c = new Cidade();
        ArrayList <Dependente> dependentes = pessoa.getDependentes();
        ArrayList <Veiculo> veiculos = pessoa.getVeiculos();
        ArrayList <Lote> lotes = pessoa.getLotes();
        if(codigo_supervisor == 0)
        {
            if(categoria.equals("Associado"))
               pessoa = new Associado(nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, interfone, condutor, usuario, senha, nivel, ultima_mov, acesso);
            if(categoria.equals("Funcionário"))
            {
               float sal = 0;
               if(!salario.equals(""))
                   sal = Float.parseFloat(salario);               
               pessoa = new Funcionario(nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, data_admissao, data_demissao, sal, condutor, usuario, senha, nivel, ultima_mov, acesso);
            }
            if(categoria.equals("Visitante"))
               pessoa = new Visitante(nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, condutor, "", "", 0, ultima_mov, acesso);
        }
        else
        {
            pessoa = new Dependente(nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, null, descricao, condutor, usuario, senha, nivel, ultima_mov, acesso);
            ((Dependente)pessoa).setSupervisor(pessoa.getPessoa(codigo_supervisor));
        }
        
        pessoa.setDependentes(dependentes);
        pessoa.setVeiculos(veiculos);
        pessoa.setLotes(lotes);
          
        if(!pessoa.salvar())
            return false;
        
        if(!pessoa.salvar_dependentes())
            return false;
        
        if(!pessoa.salvar_veiculos())
            return false;
        
        return pessoa.salvar_lotes();
    }
    
    public boolean Alterar(int cod, String nome, String categoria, String cpf, String bairro, String tipo_log, String logradouro, String num, String telefone, String email, String cep, String cidade, String uf, LocalDate data, String telefone2, String interfone, LocalDate data_admissao, LocalDate data_demissao, String salario, String condutor, String usuario, String senha, int nivel, int codigo_supervisor, String descricao, String ultima_mov, String acesso) {
        if(logradouro.equals(""))
            tipo_log = num = "";
        if(!categoria.equals("Visitante")) 
        {
            pessoa.setCod(cod);
            pessoa.setNivel(nivel);
            pessoa.setUsuario(usuario);
            pessoa.setSenha(senha);
            if(!pessoa.VerificarUsuario())
               return false;
            nivel = pessoa.getNivel();
        }
        Cidade c = new Cidade();
        ArrayList <Dependente> dependentes = pessoa.getDependentes();
        ArrayList <Veiculo> veiculos = pessoa.getVeiculos();
        ArrayList <Lote> lotes = pessoa.getLotes();
        if(codigo_supervisor == 0)
        {
            if(categoria.equals("Associado"))
               pessoa = new Associado(cod, nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, interfone, condutor, usuario, senha, nivel, ultima_mov, acesso);        
            if(categoria.equals("Funcionário"))
            {
                //se alterar a categoria, não posso deixar nenhum registro inconsistente de outra categoria
                for(Dependente d:dependentes)
                    if(!d.desativar()) //removendo elementos do banco
                       return false;
                for(Lote l:lotes)
                    if(!l.desativar()) //removendo elementos do banco
                       return false;
                
                float sal = 0;
                if(!salario.equals(""))
                   sal = Float.parseFloat(salario);  
                pessoa = new Funcionario(cod, nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, data_admissao, data_demissao, sal, condutor, usuario, senha, nivel, ultima_mov, acesso);
            }
            if(categoria.equals("Visitante"))
            {
               //se alterar a categoria, não posso deixar nenhum registro inconsistente de outra categoria
               for(Lote l:lotes) //removendo elementos do banco
                   if(!l.desativar())
                       return false;
               pessoa = new Visitante(cod, nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, condutor, "", "", 0, ultima_mov, acesso);
            }
        }
        else
        {
            pessoa = new Dependente(cod, nome, categoria, cpf, bairro, tipo_log, logradouro, num, telefone, email, cep, c.getCidade(cidade, uf), data, telefone2, null, descricao, condutor, usuario, senha, nivel, ultima_mov, acesso);
            ((Dependente)pessoa).setSupervisor(pessoa.getPessoa(codigo_supervisor));
        }
        
        pessoa.setDependentes(dependentes);
        pessoa.setVeiculos(veiculos);
        pessoa.setLotes(lotes);
        if(!pessoa.alterar()) 
            return false;
        
        if(!pessoa.alterar_dependentes())
            return false;
        
        if(!pessoa.alterar_veiculos())
            return false;
        
        if(!pessoa.alterar_lotes())
            return false;
            
        //vou excluir todos os dependentes, veiculos, lotes que foram EXCLUIDOS das tabelas pelo usuário
        if(!pessoa.excluir_dependentes())
            return false;
        
        if(!pessoa.excluir_veiculos())
            return false;
        
        return pessoa.excluir_lotes();
    }
    
    public boolean Excluir()
    {        
        //return pessoa.excluir();   
        return pessoa.desativar();
    }
    
    public boolean SalvarImagem(BufferedImage img)
    {
        return pessoa.salvarimagem(img);            
    }
    
    public BufferedImage LerImagem(int cod)
    {
        return pessoa.lerimagem(cod);
    }

    
    
}
