
package siscon.controladoras;

import java.util.ArrayList;
import siscon.entidades.CategoriaVeiculo;
import siscon.entidades.Cor;
import siscon.entidades.Marca;
import siscon.entidades.Veiculo;


public class CtrVeiculo {
    private Veiculo veiculo;

    public CtrVeiculo() {
        veiculo = new Veiculo();
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }
    
    public void novo()
    {
        veiculo = new Veiculo();
    }
  
    public ArrayList <Veiculo> ListarVeiculos(String filtro, String categoria)
    {
        filtro = filtro.toUpperCase();
        String aux = "";
        if(!filtro.equals(""))
            aux += "(upper(vei_modelo) LIKE '%"+filtro+"%' OR upper(mar_nome) LIKE '%"+filtro+"%' OR upper(vei_placa) LIKE '%"+filtro+"%')";
        if(!categoria.equals("Exibir todos"))
        {
            if(!aux.equals(""))
               aux = aux + " and ";
           aux += "cat_cod = "+veiculo.getCategoria().getCategoria(categoria).getCod();
        }
        ArrayList <Veiculo> veiculos = veiculo.getVeiculos(aux);
        return veiculos;
        /*ArrayList <String> lista = new ArrayList(); 
        for(int i=0; i<veiculos.size();i++)
            lista.add(veiculos.get(i).getModelo());
        return lista;*/
    }
    
    public ArrayList <String> ListarCores(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        Cor cor = new Cor();
        
        ArrayList <Cor> cores = cor.getCores(filtro);
        for(int i=0; i<cores.size();i++)
            lista.add(cores.get(i).getNome());
        return lista;
    }
    
    public ArrayList <String> ListarMarcas(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        Marca mar = new Marca();
        
        ArrayList <Marca> marcas = mar.getMarcas(filtro);
        for(int i=0; i<marcas.size();i++)
            lista.add(marcas.get(i).getNome());
        return lista;
    }
    
    public ArrayList <String> ListarCategorias(String filtro)
    {
        ArrayList <String> lista = new ArrayList();  
        CategoriaVeiculo cat = new CategoriaVeiculo();
        
        ArrayList <CategoriaVeiculo> categorias = cat.getCategorias(filtro);
        for(int i=0; i<categorias.size();i++)
            lista.add(categorias.get(i).getDescricao());
        return lista;
    }
    
    public boolean SalvarOpcao(String nome, String opcao)
    {
        if(opcao.contains("Marcas"))
        {
           veiculo.setMarca(new Marca(nome));
           return veiculo.getMarca().salvar();
        }
        if(opcao.contains("Categorias"))
        {
           veiculo.setCategoria(new CategoriaVeiculo(nome));
           return veiculo.getCategoria().salvar();
        }
        if(opcao.contains("Cores"))
        {
           veiculo.setCor(new Cor(nome));
           return veiculo.getCor().salvar();
        }
        return false;
    }
    
    public boolean AlterarOpcao(int cod, String nome, String opcao)
    {
        if(opcao.contains("Marcas"))
        {
           veiculo.setMarca(new Marca(cod, nome));
           return veiculo.getMarca().alterar();
        }
        if(opcao.contains("Categorias"))
        {
           veiculo.setCategoria(new CategoriaVeiculo(cod, nome));
           return veiculo.getCategoria().alterar();
        }
        if(opcao.contains("Cores"))
        {
           veiculo.setCor(new Cor(cod, nome));
           return veiculo.getCor().alterar();
        }
        return false;
    }
    
    public boolean ExcluirOpcao(String opcao)
    {     
        if(opcao.contains("Marcas"))
           return veiculo.getMarca().excluir();
        if(opcao.contains("Categorias"))
           return veiculo.getCategoria().excluir();
        if(opcao.contains("Cores"))
           return veiculo.getCor().excluir();
        return false;
    }
    
    public boolean DesativarOpcao(String opcao)
    {     
        if(opcao.contains("Marcas"))
           return veiculo.getMarca().desativar();
        if(opcao.contains("Categorias"))
           return veiculo.getCategoria().desativar();
        if(opcao.contains("Cores"))
           return veiculo.getCor().desativar();
        return false;
    }
    
    public boolean Salvar(String categoria, String marca, String modelo, String cor, String placa, int codigo_proprietario, String ultima_mov, String autorizado)
    {
        veiculo = new Veiculo(modelo, placa, veiculo.getPessoa().getPessoa(codigo_proprietario), veiculo.getCor().getCor(cor), veiculo.getMarca().getMarca(marca), veiculo.getCategoria().getCategoria(categoria), ultima_mov, autorizado);
        return veiculo.salvar();
    }
    
    public boolean Alterar(int codigo, String categoria, String marca, String modelo, String cor, String placa, int codigo_proprietario, String ultima_mov, String autorizado)
    {
        veiculo = new Veiculo(codigo, modelo, placa, veiculo.getPessoa().getPessoa(codigo_proprietario), veiculo.getCor().getCor(cor), veiculo.getMarca().getMarca(marca), veiculo.getCategoria().getCategoria(categoria), ultima_mov, autorizado);
        return veiculo.alterar();
    }
    
    public boolean Excluir()
    {        
        //return veiculo.excluir();      
        return veiculo.desativar();
    }

}
