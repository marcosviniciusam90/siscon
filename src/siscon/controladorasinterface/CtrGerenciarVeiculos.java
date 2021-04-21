
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

public class CtrGerenciarVeiculos
{
    private CtrVeiculo ctrveiculo;
    
    public CtrGerenciarVeiculos() {
        ctrveiculo = new CtrVeiculo();
    }

    public CtrVeiculo getCtrveiculo() {
        return ctrveiculo;
    }
    
    public void Novo()
    {
        ctrveiculo.novo();
    }
    
    public void CarregarTabelaVeiculos(TableView tabela, String filtro, String categoria)
    {   
        ArrayList<Veiculo> res = null;
        res = ctrveiculo.ListarVeiculos(filtro, categoria);  
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Veiculo> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
   

    public void CarregarComboBox(ComboBox combobox, String tabela, String filtro) {
        ArrayList<String> res = null;        
        
        if(tabela.equals("categoriaveiculo"))
           res = ctrveiculo.ListarCategorias(filtro);
        if(tabela.equals("cor"))
           res = ctrveiculo.ListarCores(filtro);
        if(tabela.equals("marca"))
           res = ctrveiculo.ListarMarcas(filtro);
        
        if(combobox.getId().equals("VEICULOScbFiltrarCategoria"))
            res.add(0, "Exibir todos");
        ///////////////////////////////////////////////////////////////////////        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
        ////////////
        if(combobox.getId().equals("VEICULOScbFiltrarCategoria"))
            combobox.getSelectionModel().select(0);
    }    
    
   
}
