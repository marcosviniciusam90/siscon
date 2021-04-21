
package siscon.controladorasinterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import siscon.controladoras.CtrCaixa;
import siscon.controladoras.CtrContaReceber;
import siscon.entidades.ContaReceber;
import siscon.entidades.ParcelaRec;
import siscon.util.Funcoes;


public class CtrLancarContaReceber
{
    private CtrContaReceber ctrcontareceber;
    
    public CtrLancarContaReceber() {
        ctrcontareceber = new CtrContaReceber();
    }

    public CtrContaReceber getCtrcontareceber() {
        return ctrcontareceber;
    }
    
    public void Novo()
    {
        ctrcontareceber.novo();
    }
    
    public void CarregarTabela(TableView tabela, String filtro)
    {   
        tabela.getItems().clear();
        ArrayList<ContaReceber> res = null;
        res = ctrcontareceber.ListarContas(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ContaReceber> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void CarregarParcelas(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList<ParcelaRec> res = new ArrayList();
        res.addAll(ctrcontareceber.getConta().getParcelas());
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaRec> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        
    }
    public void AlterarParcela(TableView <ParcelaRec> tabela, int indice, LocalDate data_vencimento, float valor)
    {   
        
        ArrayList<ParcelaRec> res = null;
        res = ctrcontareceber.AlterarParcela(tabela.getItems(), indice, data_vencimento, valor);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaRec> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public boolean SalvarParcelas(TableView tabela, float valor, float valor_entrada, int numero_parcelas) //vou validar a soma das parcelas + valor de entrada se é igual ao valor da conta
    {
        if(!ctrcontareceber.getConta().validar_parcelas(tabela.getItems(), valor, valor_entrada, numero_parcelas))
            return false;
        
        //salvar informações acerca das parcelas
        return ctrcontareceber.SalvarParcelas(tabela.getItems(), valor, valor_entrada);
    }
    
     public void AjustarParcelas(TableView <ParcelaRec> tabela, ObservableList indices, float valor, float valorentrada)
    {   
        ArrayList<ParcelaRec> res = null;
        res = ctrcontareceber.AjustarParcelas(tabela.getItems(), indices, valor, valorentrada);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaRec> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void GerarParcelas(TableView tabela, float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {   
        tabela.getItems().clear();
        ArrayList<ParcelaRec> res = null;
        res = ctrcontareceber.GerarParcelas(valor, valor_entrada, numero_parcelas, data_inicio);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaRec> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private ArrayList <String> ListarOpcoes(String tipo)
    {
        ArrayList <String> lista = new ArrayList();  
       
        if(tipo.equals("FORMAS")) //listar categorias de pessoas
        {
            lista.add("À vista");
            lista.add("A prazo");
        }
        
        return lista;
    }
    
    public void CarregarComboBox(ComboBox combobox, String tipo) {
        combobox.getItems().clear();
        ArrayList<String> res = null;
        res = ListarOpcoes(tipo);         
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
    }
}
