
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
import siscon.controladoras.CtrEvento;
import siscon.entidades.Evento;
import siscon.entidades.EventoParticipante;
import siscon.util.Funcoes;


public class CtrRegistrarEvento
{
    private CtrEvento ctrevento;
    
    public CtrRegistrarEvento() {
        ctrevento = new CtrEvento();
    }

    public CtrEvento getCtrevento() {
        return ctrevento;
    }
    
    public void Novo()
    {
        ctrevento.novo();
    }
    
    public void CarregarTabela(TableView tabela, String filtro, String categoria)
    {   
        tabela.getItems().clear();
        ArrayList<Evento> res = null;
        res = ctrevento.ListarEventos(filtro, categoria);
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Evento> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void CarregarParticipantes(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList<EventoParticipante> res = new ArrayList();
        res.addAll(ctrevento.getEvento().getParticipantes());
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<EventoParticipante> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        
    }
    
    public void AdicionarParticipante(TableView <EventoParticipante> tabela, int cod_part, String autorizado, LocalDate data_inicio, LocalDate data_fim)
    {   
        
        ctrevento.AdicionarParticipante(tabela, cod_part, autorizado, data_inicio, data_fim);
    }
    
    public void AlterarParticipante(TableView <EventoParticipante> tabela, int indice, String autorizado, LocalDate data_inicio, LocalDate data_fim)
    {   
        
        ArrayList<EventoParticipante> res = null;
        res = ctrevento.AlterarParticipante(tabela.getItems(), indice, autorizado, data_inicio, data_fim);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<EventoParticipante> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    
     public void AjustarParticipantes(TableView <EventoParticipante> tabela, ObservableList indices, LocalDate data_inicio, LocalDate data_fim)
    {   
        ArrayList<EventoParticipante> res = null;
        res = ctrevento.AjustarParticipantes(tabela.getItems(), indices, data_inicio, data_fim);
        tabela.getItems().clear();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<EventoParticipante> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
     
    
    public boolean SalvarParticipantes(TableView tabela, LocalDate data_minima, LocalDate data_maxima) 
    {
        if(!ctrevento.getEvento().validar_participantes(tabela.getItems(), data_minima, data_maxima))
            return false;
        
        //salvar informações acerca dos participantes
        return ctrevento.SalvarParticipantes(tabela.getItems());
    }
    
    public void CarregarComboBox(ComboBox combobox, String filtro) {
        ArrayList<String> res = null;        
        
        res = ctrevento.ListarCategorias(filtro);
        
        
        if(combobox.getId().equals("EVENTOcbFiltrarCategoria"))
            res.add(0, "Exibir todos");
        ///////////////////////////////////////////////////////////////////////        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
        ////////////
        if(combobox.getId().equals("EVENTOcbFiltrarCategoria"))
            combobox.getSelectionModel().select(0);
    }  
}
