
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
import siscon.controladoras.CtrRecebimento;
import siscon.entidades.ParcelaRec;


public class CtrRealizarRecebimento
{
    private CtrRecebimento ctrrecebimento;
    
    public CtrRealizarRecebimento() {
        ctrrecebimento = new CtrRecebimento();
    }

    public CtrRecebimento getCtrrecebimento() {
        return ctrrecebimento;
    }
    
    public void Novo()
    {
        ctrrecebimento.novo();
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
        ArrayList<ParcelaRec> res = null;
        res = ctrrecebimento.getMovCaixa().getParcelarec().getParcelas(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<ParcelaRec> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private ArrayList <String> ListarOpcoes(String tipo)
    {
        ArrayList <String> lista = new ArrayList();  
        
        if(tipo.equals("FILTROS")) //listar categorias de pessoas
        {
            lista.add("Exibir todas as parcelas");
            lista.add("Exibir somente parcelas recebidas");
            lista.add("Exibir somente parcelas vencidas");
            lista.add("Exibir parcelas que vencem hoje");
        }
        
        if(tipo.equals("FORMAS")) //listar categorias de pessoas
        {
            lista.add("Boleto");
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
}
