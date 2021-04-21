
package siscon.controladoras;

import java.util.ArrayList;
import siscon.entidades.Cidade;
import siscon.entidades.Estado;


public class CtrCidade {
    private Cidade cidade;

    public CtrCidade() {
        cidade = new Cidade();
    }

    public Cidade getCidade() {
        return cidade;
    }
    
    public ArrayList <String> ListarCidades(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        
        ArrayList <Cidade> cidades = cidade.getCidades(filtro);
        for(int i=0; i<cidades.size();i++)
            lista.add(cidades.get(i).getNome());
        return lista;
    }
    
    public ArrayList <String> ListarEstados(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        
        ArrayList <Estado> estados = cidade.getEstados(filtro);
        for(int i=0; i<estados.size();i++)
            lista.add(estados.get(i).getSigla());
        return lista;
    }
    
}
