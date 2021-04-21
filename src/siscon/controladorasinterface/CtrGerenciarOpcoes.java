
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

public class CtrGerenciarOpcoes
{
    private CtrVeiculo ctrveiculo;
    
    public CtrGerenciarOpcoes() {
        ctrveiculo = new CtrVeiculo();
    }

    public CtrVeiculo getCtrveiculo() {
        return ctrveiculo;
    }
    
    public void CarregarTabela(TableView tabela, String filtro, String categoria)
    {   
        ArrayList<String> res = null;
        if(categoria.contains("Marcas"))
           res = ctrveiculo.ListarMarcas(filtro);
        if(categoria.contains("Categorias"))
           res = ctrveiculo.ListarCategorias(filtro);
        if(categoria.contains("Cores"))
           res = ctrveiculo.ListarCores(filtro);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
   
}
