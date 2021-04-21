
package siscon.controladorasinterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import siscon.controladoras.CtrCidade;
import siscon.controladoras.CtrEmpresa;
import siscon.entidades.Empresa;
import siscon.ui.PrincipalController;
import sun.misc.BASE64Encoder;

public class CtrGerenciarEmpresas
{
    private CtrEmpresa ctrempresa;
    private CtrCidade ctrcidade;
    
    public CtrGerenciarEmpresas() {
        ctrempresa = new CtrEmpresa();
        ctrcidade = new CtrCidade();
    }

    public CtrEmpresa getCtrempresa() {
        return ctrempresa;
    }
    
    public CtrCidade getCtrcidade() {
        return ctrcidade;
    }
    
    public void Novo()
    {
        ctrempresa.novo();
    }
    
    public void CarregarTabelaEmpresas(TableView tabela, String filtro)
    {   
        ArrayList<Empresa> res = null;
        res = ctrempresa.ListarEmpresas(filtro);  
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Empresa> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
   

    private ArrayList <String> ListarCategorias(String tipo)
    {
        ArrayList <String> lista = new ArrayList();  
        
        if(tipo.equals("TIPOLOGRADOURO")) //listar categorias de pessoas
        {
            lista.add("Avenida");
            lista.add("Praça");
            lista.add("Rua");
            lista.add("Travessa");
        }
       
        return lista;
    }
    
    public void CarregarComboBox(ComboBox combobox, String opcao, String filtro) {
        ArrayList<String> res = null;
        
        //NÃO ESTÁ EM BANCO, são informações pré-definidas
        if(opcao.equals("TIPOLOGRADOURO"))
           res = ListarCategorias(opcao);         
        ///// aonde vou chamar as funções para consultar as respectivas tabelas
        if(opcao.equals("cidade"))
           res = ctrcidade.ListarCidades(filtro);
        if(opcao.equals("estado"))
           res = ctrcidade.ListarEstados(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);        
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
