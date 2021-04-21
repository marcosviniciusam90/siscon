
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
import siscon.controladoras.CtrCaixa;
import siscon.controladoras.CtrPagamento;
import siscon.entidades.CartaoCredito;
import siscon.entidades.ParcelaPag;


public class CtrRealizarPagamento
{
    private CtrPagamento ctrpagamento;
    
    public CtrRealizarPagamento() {
        ctrpagamento = new CtrPagamento();
    }

    public CtrPagamento getCtrpagamento() {
        return ctrpagamento;
    }
    
    public void Novo()
    {
        ctrpagamento.novo();
    }
    
    /*
    public boolean CarregarDados()
    {
        return ctrcaixa.CarregarDados();
        
    }
    */
    
    public void CarregarTabela(TableView tabela, String filtro)
    {   
        tabela.getItems().clear();
        ArrayList<ParcelaPag> res = null;
        res = ctrpagamento.getMovCaixa().getParcelapag().getParcelas(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaPag> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private ArrayList <String> ListarOpcoes(String tipo)
    {
        ArrayList <String> lista = new ArrayList();  
        
        if(tipo.equals("FILTROS")) //listar categorias de pessoas
        {
            lista.add("Exibir todas as parcelas");
            lista.add("Exibir somente parcelas pagas");
            lista.add("Exibir somente parcelas vencidas");
            lista.add("Exibir parcelas que vencem hoje");
        }
        
        if(tipo.equals("FORMAS")) //listar categorias de pessoas
        {
            lista.add("Boleto");
            lista.add("Cartão de Crédito");
            lista.add("Cheque");
            lista.add("Depósito");
            lista.add("Dinheiro");
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
    
    public void CarregarCartoesCredito(ComboBox combobox) {
        combobox.getItems().clear();
        ArrayList<CartaoCredito> res = null;
        CartaoCredito cc = new CartaoCredito();
        res = cc.getCartoesCredito("");
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<CartaoCredito> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
    }
}
