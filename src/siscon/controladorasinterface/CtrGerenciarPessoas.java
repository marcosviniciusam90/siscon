
package siscon.controladorasinterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import siscon.controladoras.CtrCidade;
import siscon.controladoras.CtrPessoa;
import siscon.controladoras.CtrVeiculo;
import siscon.entidades.Dependente;
import siscon.entidades.Estado;
import siscon.entidades.Lote;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.ui.PrincipalController;
import sun.misc.BASE64Encoder;

public class CtrGerenciarPessoas
{
    private CtrPessoa ctrpessoa;
    private CtrCidade ctrcidade;
    private CtrVeiculo ctrveiculo;
    
    public CtrGerenciarPessoas() {
        ctrpessoa = new CtrPessoa();
        ctrcidade = new CtrCidade();
        ctrveiculo = new CtrVeiculo();
    }

    public CtrPessoa getCtrpessoa() {
        return ctrpessoa;
    }

    public CtrCidade getCtrcidade() {
        return ctrcidade;
    }

    public CtrVeiculo getCtrveiculo() {
        return ctrveiculo;
    }
    
    public void Novo()
    {
        ctrpessoa.novo();
    }
    
    public void CarregarTabela(TableView tabela, String filtro, String categoria)
    {   
        ArrayList<Pessoa> res = null;
        if(tabela.getId().contains("ASSOCIADOS"))
           res = ctrpessoa.ListarAssociados(filtro);   
        if(tabela.getId().equals("CONDUTORES"))
           res = ctrpessoa.ListarCondutores(filtro, categoria); 
        if(tabela.getId().contains("CONDUTORESDENTRO"))
           res = ctrpessoa.ListarCondutoresDentro(filtro, categoria); 
        if(tabela.getId().contains("CONDUTORESFORA"))
           res = ctrpessoa.ListarCondutoresFora(filtro, categoria); 
        if(tabela.getId().contains("DEPENDENTES"))
           res = ctrpessoa.ListarDependentes(filtro, categoria);   
        if(tabela.getId().contains("SUPERVISORES"))
           res = ctrpessoa.ListarSupervisores(filtro, categoria); 
        if(tabela.getId().contains("POSSIVEISSUPERVISORES"))
           res = ctrpessoa.ListarPossiveisSupervisores(filtro, categoria); 
        if(tabela.getId().equals("DENTRO"))
           res = ctrpessoa.ListarTodosDentro(filtro, categoria);  
        if(tabela.getId().equals("FORA"))
           res = ctrpessoa.ListarTodosFora(filtro, categoria);  
        if(tabela.getId().contains("TODOS"))
           res = ctrpessoa.ListarTodos(filtro, categoria);  
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Pessoa> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private ArrayList <String> ListarCategorias(String tipo)
    {
        ArrayList <String> lista = new ArrayList();  
        
        if(tipo.equals("PESSOA")) //listar categorias de pessoas
        {
            lista.add("Associado");
            lista.add("Funcionário");
            lista.add("Visitante");
        }
        if(tipo.equals("DEPENDENTE")) //listar categorias de dependentes
        {
            lista.add("Associado");
            lista.add("Visitante");
        }
        if(tipo.equals("USUÁRIO")) //listar categorias de dependentes
        {
            lista.add("Associado");
            lista.add("Funcionário");
        }
        if(tipo.equals("SUPERVISOR")) //listar categorias de dependentes
        {
            lista.add("Associado");
            lista.add("Visitante");
        }
        if(tipo.equals("TIPOLOGRADOURO")) //listar categorias de pessoas
        {
            lista.add("Avenida");
            lista.add("Praça");
            lista.add("Rua");
            lista.add("Travessa");
        }
       
        return lista;
    }
   

    public void CarregarComboBox(ComboBox combobox, String tabela, String filtro) {
        ArrayList<String> res = null;
        
        //NÃO ESTÁ EM BANCO, são informações pré-definidas
        if(filtro.equals("PESSOA"))
           res = ListarCategorias(filtro); 
        if(filtro.equals("DEPENDENTE"))
           res = ListarCategorias(filtro);
        if(filtro.equals("USUÁRIO"))
           res = ListarCategorias(filtro);
        if(filtro.equals("SUPERVISOR"))
           res = ListarCategorias(filtro); 
        if(filtro.equals("CONDUTOR"))
           res = ListarCategorias(filtro); 
        if(filtro.equals("TIPOLOGRADOURO"))
           res = ListarCategorias(filtro); 
        
        ///// aonde vou chamar as funções para consultar as respectivas tabelas
        if(tabela.equals("cidade"))
           res = ctrcidade.ListarCidades(filtro);
        if(tabela.equals("estado"))
           res = ctrcidade.ListarEstados(filtro);
        
        
        ///////////////////////////////////////////////////////////////////////
        
        //acrescentar opção "Exibir todos"
        if(combobox.getId().equals("PESSOAScbFiltrarCategoria") || combobox.getId().equals("DEPENDENTEScbFiltrarCategoria") || combobox.getId().equals("cbFiltrarCategoria"))
            res.add(0, "Exibir todos");
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
        
        //selecionar primeira opção do menu (opção "Exibir todos")
        if(combobox.getId().equals("PESSOAScbFiltrarCategoria") || combobox.getId().equals("DEPENDENTEScbFiltrarCategoria") || combobox.getId().equals("cbFiltrarCategoria"))
           combobox.getSelectionModel().select(0);
    }
    
    public void CarregarDados(TableView tabVeiculos, TableView tabDependentes, TableView tabLotes, int pes_cod)
    {
        Novo();
        ctrpessoa.getPessoa().setCod(pes_cod); //mudando apenas o código, com propósito de carregar dados das tabelas Dependentes, Veiculos, Lotes
        ctrpessoa.getPessoa().carregar_dados(); //carregar os veiculos, dependentes e lotes do BANCO
        CarregarVeiculos(tabVeiculos);
        CarregarDependentes(tabDependentes);
        CarregarLotes(tabLotes);
    }
    
    public void AdicionarVeiculo(TableView tabela, String categoria, String marca, String modelo, String cor, String placa)
    {        
        ctrpessoa.AdicionarVeiculo(categoria, marca, modelo, cor, placa);
        CarregarVeiculos(tabela);        
    }
    
    public void AlterarVeiculo(TableView tabela, String categoria, String marca, String modelo, String cor, String placa)
    {        
        ctrpessoa.AlterarVeiculo(tabela.getSelectionModel().getSelectedIndex(), categoria, marca, modelo, cor, placa);
        CarregarVeiculos(tabela);        
    }
    
    public void ExcluirVeiculo(TableView tabela)
    {        
        ctrpessoa.ExcluirVeiculo(tabela.getSelectionModel().getSelectedIndex());
        CarregarVeiculos(tabela);
    }
    
    public void CarregarVeiculos(TableView tabela)
    {
        tabela.getItems().clear();
        ObservableList<Veiculo> ol;
        ol = FXCollections.observableArrayList(ctrpessoa.getPessoa().getVeiculos());
        tabela.setItems(ol);
    }
    
    public void AdicionarDependente(TableView tabela, String nome, String descricao, String condutor)
    {        
        ctrpessoa.AdicionarDependente(nome, descricao, condutor);
        CarregarDependentes(tabela);
    }
    
    public void AlterarDependente(TableView tabela, String nome, String descricao, String condutor)
    {        
        ctrpessoa.AlterarDependente(tabela.getSelectionModel().getSelectedIndex(), nome, descricao, condutor);
        CarregarDependentes(tabela);
    }
    
    public void ExcluirDependente(TableView tabela)
    {        
        ctrpessoa.ExcluirDependente(tabela.getSelectionModel().getSelectedIndex());
        CarregarDependentes(tabela);
    }
    
    public void CarregarDependentes(TableView tabela)
    {
        tabela.getItems().clear();
        ObservableList<Dependente> ol;
        ol = FXCollections.observableArrayList(ctrpessoa.getPessoa().getDependentes());
        tabela.setItems(ol);
    }
    
    public void AdicionarLote(TableView tabela, int numero, int quadra, String descricao, String residencia)
    {        
        ctrpessoa.AdicionarLote(numero, quadra, descricao, residencia);
        CarregarLotes(tabela);
    }
    
    public void AlterarLote(TableView tabela, int numero, int quadra, String descricao, String residencia)
    {        
        ctrpessoa.AlterarLote(tabela.getSelectionModel().getSelectedIndex(), numero, quadra, descricao, residencia);
        CarregarLotes(tabela);
    }
    
    public void ExcluirLote(TableView tabela)
    {        
        ctrpessoa.ExcluirLote(tabela.getSelectionModel().getSelectedIndex());
        CarregarLotes(tabela);
    }
    
    public void CarregarLotes(TableView tabela)
    {
        tabela.getItems().clear();
        ObservableList<Lote> ol;
        ol = FXCollections.observableArrayList(ctrpessoa.getPessoa().getLotes());
        tabela.setItems(ol);
    }   
    
    //Web Service de CEP 
    public static String consultaCep(String cep, String formato, String username, String password, String proxyHost, String proxyPort)
    {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato="+formato);
            String userpass = username + ":" + password;
            System.setProperty("http.proxyHost", proxyHost);
            System.setProperty("http.proxyPort", proxyPort);
            URLConnection con = url.openConnection();
            String encodedLogin = new BASE64Encoder().encode(userpass.getBytes());
            con.setRequestProperty("Proxy-Authorization", "Basic " + encodedLogin);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                 dados.append(s);
            br.close();
        } catch (Exception ex) 
        {
            System.out.println(ex);
        }
        return dados.toString();
    }
     
    //Web Service de CEP 
    public static String consultaCep(String cep, String formato) 
    {
        String urlString = "http://cep.republicavirtual.com.br/web_cep.php";
        urlString += "?cep=" + cep + "&formato=" + formato;
        try {
            URL url = new URL(urlString);
            // cria o objeto httpurlconnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // seta para modo GET
            con.setRequestProperty("Request-Method", "GET");
            // conecta com a url destino
            con.connect();
            // abre a conexão para leitura
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            // le ate o final dos dados
            StringBuffer dados = new StringBuffer();
            String s = "";
            while (null != ((s = br.readLine()))) {
                dados.append(s);
            }
            br.close();
            return dados.toString();
        } 
        catch (Exception e)
        {
            return "Erro: " + e.getMessage();
        }
    }
   
}
