
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
import siscon.controladoras.CtrContaPagar;
import siscon.entidades.ContaPagar;
import siscon.entidades.ParcelaPag;
import siscon.util.Funcoes;


public class CtrLancarContaPagar
{
    private CtrContaPagar ctrcontapagar;
    
    public CtrLancarContaPagar() {
        ctrcontapagar = new CtrContaPagar();
    }

    public CtrContaPagar getCtrcontapagar() {
        return ctrcontapagar;
    }
    
    public void Novo()
    {
        ctrcontapagar.novo();
    }
    
    public void CarregarTabela(TableView tabela, String filtro)
    {   
        tabela.getItems().clear();
        ArrayList<ContaPagar> res = null;
        res = ctrcontapagar.ListarContas(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ContaPagar> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void CarregarParcelas(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList<ParcelaPag> res = new ArrayList();
        res.addAll(ctrcontapagar.getConta().getParcelas());
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaPag> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        
    }
    public void AlterarParcela(TableView <ParcelaPag> tabela, int indice, LocalDate data_vencimento, float valor)
    {   
        
        ArrayList<ParcelaPag> res = null;
        res = ctrcontapagar.AlterarParcela(tabela.getItems(), indice, data_vencimento, valor);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaPag> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public boolean SalvarParcelas(TableView tabela, float valor, float valor_entrada, int numero_parcelas) //vou validar a soma das parcelas + valor de entrada se é igual ao valor da conta
    {
        if(!ctrcontapagar.getConta().validar_parcelas(tabela.getItems(), valor, valor_entrada, numero_parcelas))
            return false;
        
        //salvar informações acerca das parcelas
        return ctrcontapagar.SalvarParcelas(tabela.getItems(), valor, valor_entrada);
    }
    
     public void AjustarParcelas(TableView <ParcelaPag> tabela, ObservableList indices, float valor, float valorentrada)
    {   
        ArrayList<ParcelaPag> res = null;
        res = ctrcontapagar.AjustarParcelas(tabela.getItems(), indices, valor, valorentrada);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaPag> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void GerarParcelas(TableView tabela, float valor, float valor_entrada, int numero_parcelas, LocalDate data_inicio)
    {   
        tabela.getItems().clear();
        ArrayList<ParcelaPag> res = null;
        res = ctrcontapagar.GerarParcelas(valor, valor_entrada, numero_parcelas, data_inicio);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaPag> modelo;
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
