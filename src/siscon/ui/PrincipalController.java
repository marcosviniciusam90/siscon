
package siscon.ui;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import siscon.controladorasinterface.CtrFluxoCaixa;
import siscon.controladorasinterface.CtrGerenciarEmpresas;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import siscon.controladorasinterface.CtrLancarContaPagar;
import siscon.controladorasinterface.CtrLancarContaReceber;
import siscon.controladorasinterface.CtrRealizarMovimentacao;
import siscon.controladorasinterface.CtrRealizarPagamento;
import siscon.controladorasinterface.CtrRealizarRecebimento;
import siscon.controladorasinterface.CtrRegistrarEvento;
import siscon.entidades.Associado;
import siscon.entidades.CartaoCredito;
import siscon.entidades.ContaPagar;
import siscon.entidades.ContaReceber;
import siscon.entidades.Dependente;
import siscon.entidades.Empresa;
import siscon.entidades.Evento;
import siscon.entidades.EventoParticipante;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.MovCaixa;
import siscon.entidades.Movimentacao;
import siscon.entidades.ParcelaPag;
import siscon.entidades.ParcelaRec;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Banco;
import siscon.util.ComboBoxAutoComplete;
import siscon.util.Conexao;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;
import siscon.util.Parametros;

/**
 *
 * @author Marcos Vinícius
 */
public class PrincipalController implements Initializable {

    @FXML
    private AnchorPane apEsquerdo;
    @FXML
    private AnchorPane apDireito;
    @FXML
    private ImageView imvRelatorios;
    @FXML
    private AnchorPane apEventos;
    private AnchorPane apHistorico;
    @FXML
    private ImageView imvCadastros;
    @FXML
    private ImageView imvMovimentacao;
    @FXML
    private ImageView imvConfiguracoes;
    private ImageView imvAjuda;
    @FXML
    private AnchorPane apSubMovimentacoes;
    @FXML
    private ImageView imvEventos;
    @FXML
    private ImageView imvMenu;
    @FXML
    private AnchorPane apPessoas;        
    @FXML
    private AnchorPane apSubCadastros;
    @FXML
    private ImageView imvPessoas;
    @FXML
    private TextField PESSOAStxPesquisa;
    @FXML
    private TableView <Pessoa> PESSOAStabela;
    @FXML
    private TableColumn PESSOAScolNome;
    @FXML
    private TableColumn PESSOAScolCPF;
    @FXML
    private Button PESSOASbtNovo;
    @FXML
    private Button PESSOASbtAlterar;
    @FXML
    private Button PESSOASbtApagar;
    @FXML
    private Label PESSOASlbCod;
    @FXML
    private TextField PESSOAStfCod;
    @FXML
    private Label PESSOASlbCpf;
    @FXML
    private TextField PESSOAStfCPF;
    @FXML
    private Label PESSOASlbNome;
    @FXML
    private TextField PESSOAStfNome;
    @FXML
    private DatePicker PESSOASdpData;
    @FXML
    private ComboBox<String> PESSOAScbTipoLog;
    @FXML
    private Label PESSOASlbLog;
    @FXML
    private TextField PESSOAStfLogradouro;
    @FXML
    private Label PESSOASlbNumero;
    @FXML
    private TextField PESSOAStfNum;
    @FXML
    private TextField PESSOAStfCEP;
    @FXML
    private Label PESSOASlbBairro;
    @FXML
    private TextField PESSOAStfBairro;
    @FXML
    private Label PESSOASlbCidade;
    @FXML
    private Label PESSOASlbCep;
    @FXML
    private Label PESSOASlbTipoLog;
    @FXML
    private ComboBox<String> PESSOAScbUF;
    @FXML
    private Label PESSOASlbUf;
    @FXML
    private ComboBox<String> PESSOAScbCidade;
    @FXML
    private Label PESSOASlbTelefone;
    @FXML
    private TextField PESSOAStfTelfone;
    @FXML
    private TextField PESSOAStfTelfone2;
    @FXML
    private Label PESSOASlbEmail;
    @FXML
    private TextField PESSOAStfEmail;
    @FXML
    private Button PESSOASbtCarregarIMG;
    @FXML
    private Button PESSOASbtApagarIMG;
    @FXML
    private ImageView PESSOASimg;
    @FXML
    private Label PESSOASlbData;
    @FXML
    private Label PESSOASlbTelefone2;
    @FXML
    private TextField PESSOAStxPesquisarCPF;
    @FXML
    private Button PESSOASbtCancelar;
    @FXML
    private Button PESSOASbtConfirmar;
    @FXML
    private TextField PESSOAStfInterfone;
    @FXML
    private Label PESSOASlbInterfone;
    @FXML
    private ImageView PESSOASbtnCamera;
    @FXML
    private ComboBox<String> PESSOAScbCategoria;
    @FXML
    private Label PESSOASlbCategoria;
    @FXML
    private Label PESSOASlbVeiculos;
    @FXML
    private TableView<Veiculo> PESSOAStabelaVeiculos;
    @FXML
    private Button PESSOASbtAdicionarVeiculo;
    @FXML
    private Button PESSOASbtAlterarVeiculo;
    @FXML
    private Button PESSOASbtExcluirVeiculo;
    @FXML
    private Group apTabelaPessoas;
    @FXML
    private Group apVeiculos;    
    @FXML
    private ComboBox<String> VEICULOScbCategoria;
    @FXML
    private Label VEICULOSlbCategoria;
    @FXML
    private Label VEICULOSlbMarca;
    @FXML
    private Label VEICULOSlbModelo;
    @FXML
    private TextField VEICULOStfModelo;
    @FXML
    private ComboBox<String> VEICULOScbMarca;    
    @FXML
    private Label VEICULOSlbPlaca;
    @FXML
    private TextField VEICULOStfPlaca;
    @FXML
    private Label VEICULOSlbCor;
    @FXML
    private ComboBox<String> VEICULOScbCor;
    @FXML
    private Button VEICULOSbtCancelar;
    @FXML
    private Button VEICULOSbtConfirmar;
    @FXML
    private VBox apDadosPessoas;
    @FXML
    private Label VEICULOSlbNomePessoa;
    @FXML
    private ScrollPane scrollDadosPessoas;
    private ImageView imvEventosM;
    @FXML
    private Group apDependentes;
    @FXML
    private TextField DEPENDENTEStfNome;
    @FXML
    private RadioButton DEPENDENTESrbSim;
    @FXML
    private RadioButton DEPENDENTESrbNao;
    @FXML
    private Button DEPENDENTESbtCancelar;
    @FXML
    private Button DEPENDENTESbtConfirmar;
    @FXML
    private ComboBox<String> PESSOAScbFiltrarCategoria;
    @FXML
    private AnchorPane aux_dadospessoas;
    @FXML
    private Label PESSOASlbDependentes;
    @FXML
    private TableView<Pessoa> PESSOAStabelaDependentes;
    @FXML
    private Button PESSOASbtAdicionarDependente;
    @FXML
    private Button PESSOASbtAlterarDependente;
    @FXML
    private Button PESSOASbtExcluirDependente;    
    @FXML
    private TableColumn PESSOAScolMarca;
    @FXML
    private TableColumn PESSOAScolModelo;
    @FXML
    private TableColumn PESSOAScolCor;
    @FXML
    private TableColumn PESSOAScolPlaca;    
    @FXML
    private Label DEPENDENTESlbNomePessoa;
    @FXML
    private Label DEPENDENTESlbNome;
    @FXML
    private Label DEPENDENTESlbDescricao;
    @FXML
    private TextField DEPENDENTEStfDescricao;
    @FXML
    private Label DEPENDENTESlbCondutor;
    @FXML
    private TableColumn PESSOAScolNomeDep;
    @FXML
    private TableColumn PESSOAScolDescricaoDep;
    @FXML
    private TableColumn PESSOAScolCondutorDep;
    @FXML
    private Group apLotes;
    @FXML
    private Label LOTESlbNomePessoa;
    @FXML
    private Label LOTESlbNumero;
    @FXML
    private TextField LOTEStfNumero;
    @FXML
    private Label LOTESlbQuadra;
    @FXML
    private TextField LOTEStfQuadra;
    @FXML
    private Label LOTESlbDescricao;
    @FXML
    private TextField LOTEStfDescricao;
    @FXML
    private Label LOTESlbResidencia;
    @FXML
    private RadioButton LOTESrbSim;
    @FXML
    private RadioButton LOTESrbNao;
    @FXML
    private Button LOTESbtCancelar;
    @FXML
    private Button LOTESbtConfirmar;    
    @FXML
    private TableView<Lote> PESSOAStabelaLotes;
    @FXML
    private Button PESSOASbtAdicionarLote;
    @FXML
    private Button PESSOASbtAlterarLote;
    @FXML
    private Button PESSOASbtExcluirLote;
    @FXML
    private Label PESSOASlbLotes;
    @FXML
    private TableColumn PESSOAScolNumeroLote;
    @FXML
    private TableColumn PESSOAScolQuadraLote;
    @FXML
    private TableColumn PESSOAScolDescricaoLote;
    @FXML
    private TableColumn PESSOAScolResidenciaLote;
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab tabDadosPessoais;
    @FXML
    private AnchorPane PESSOASapDadosFuncionario;
    @FXML
    private Label PESSOASlbDataAdmissao;
    @FXML
    private Label PESSOASlbDataDemissao;
    @FXML
    private Label PESSOASlbSalario;
    @FXML
    private TextField PESSOAStfSalario;
    @FXML
    private DatePicker PESSOASdpDataAdmissao;
    @FXML
    private DatePicker PESSOASdpDataDemissao;
    @FXML
    private CheckBox PESSOASckbCondutor;
    @FXML
    private Tab tabDadosUsuario;
    @FXML
    private AnchorPane aux_dadosusuario;
    @FXML
    private Label USUARIOlbNome;
    @FXML
    private TextField USUARIOtfNome;
    @FXML
    private PasswordField USUARIOtfSenha;
    @FXML
    private Label USUARIOlbNivel;
    @FXML
    private RadioButton USUARIOrbAdministrador;
    @FXML
    private RadioButton USUARIOrbBasico;
    @FXML
    private Label USUARIOlbSenha;    
    @FXML
    private Label PESSOASlbVerSenha;
    @FXML
    private AnchorPane apDependentesTela;
    @FXML
    private Group apTabelaDependentes;
    @FXML
    private TextField DEPENDENTEStxPesquisa;
    @FXML
    private ComboBox<String> DEPENDENTEScbFiltrarCategoria;
    @FXML
    private TableView<Pessoa> DEPENDENTEStabela;
    @FXML
    private TableColumn DEPENDENTEScolNome;
    @FXML
    private TableColumn DEPENDENTEScolCPF;
    @FXML
    private Button DEPENDENTESbtNovo;
    @FXML
    private Button DEPENDENTESbtAlterar;
    @FXML
    private Button DEPENDENTESbtApagar;
    @FXML
    private VBox apDadosDependentes;
    @FXML
    private ScrollPane scrollDadosDependentes;
    @FXML
    private AnchorPane aux_dadosdependentes;
    @FXML
    private Label DEPENDENTESlbCategoria;
    @FXML
    private ComboBox<String> DEPENDENTEScbCategoria;
    @FXML
    private Label DEPENDENTESlbCod;
    @FXML
    private TextField DEPENDENTEStfCod;
    @FXML
    private Label DEPENDENTESlbCpf;
    @FXML
    private TextField DEPENDENTEStfCPF;
    @FXML
    private Button DEPENDENTESbtCarregarIMG;
    @FXML
    private ImageView DEPENDENTESimg;
    @FXML
    private ImageView DEPENDENTESbtnCamera;
    @FXML
    private DatePicker DEPENDENTESdpData;
    @FXML
    private ComboBox<String> DEPENDENTEScbTipoLog;
    @FXML
    private Label DEPENDENTESlbLog;
    @FXML
    private TextField DEPENDENTEStfLogradouro;
    @FXML
    private Label DEPENDENTESlbNumero;
    @FXML
    private TextField DEPENDENTEStfNum;
    @FXML
    private TextField DEPENDENTEStfCEP;
    @FXML
    private Label DEPENDENTESlbBairro;
    @FXML
    private TextField DEPENDENTEStfBairro;
    @FXML
    private Label DEPENDENTESlbCidade;
    @FXML
    private Label DEPENDENTESlbCep;
    @FXML
    private Label DEPENDENTESlbTipoLog;
    @FXML
    private ComboBox<String> DEPENDENTEScbUF;
    @FXML
    private Label DEPENDENTESlbUf;
    @FXML
    private ComboBox<String> DEPENDENTEScbCidade;
    @FXML
    private Label DEPENDENTESlbTelefone;
    @FXML
    private TextField DEPENDENTEStfTelfone;
    @FXML
    private TextField DEPENDENTEStfTelfone2;
    @FXML
    private TextField DEPENDENTEStfInterfone;
    @FXML
    private Label DEPENDENTESlbInterfone;
    @FXML
    private Label DEPENDENTESlbEmail;
    @FXML
    private TextField DEPENDENTEStfEmail;
    @FXML
    private Label DEPENDENTESlbData;
    @FXML
    private Label DEPENDENTESlbTelefone2;
    @FXML
    private TextField DEPENDENTEStxPesquisarCPF;
    @FXML
    private CheckBox DEPENDENTESckbCondutor;
    @FXML
    private Button DEPENDENTESTelabtCancelar;
    @FXML
    private Button DEPENDENTESTelabtConfirmar;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfSenha;
    @FXML
    private Button btLogin;
    @FXML
    private Label lbUsuario;
    @FXML
    private Label lbSenha;
    @FXML
    private ImageView imvLoginAviso;
    @FXML
    private ImageView PESSOASbtVerSenha;
    @FXML
    private ImageView imvDependentes;
    @FXML
    private AnchorPane apSubConfiguracoes;
    private ImageView imvSair;
    @FXML
    private Button DEPENDENTESbtApagarIMG;
    @FXML
    private TextField DEPENDENTESTelatfNome;
    @FXML
    private Label DEPENDENTESTelalbNome;
    @FXML
    private Label DEPENDENTESlbSupervisor;
    @FXML
    private TextField DEPENDENTEStfSupervisor;
    @FXML
    private Button DEPENDENTESbtSupervisor;
    @FXML
    private Label DEPENDENTESTelalbDescricao;
    @FXML
    private TextField DEPENDENTESTelatfDescricao;    
    @FXML
    private AnchorPane apTopo;
    @FXML
    private Label lbBemVindo;
    @FXML
    private Button btSair;
    @FXML
    private Button VEICULOSbtCategoria;
    @FXML
    private Button VEICULOSbtMarca;
    @FXML
    private Button VEICULOSbtCor;
    @FXML
    private AnchorPane apVeiculosTela;
    @FXML
    private Group apTabelaVeiculos;
    @FXML
    private TextField VEICULOStxPesquisa;
    @FXML
    private TableView<Veiculo> VEICULOStabela;
    @FXML
    private TableColumn VEICULOScolPlaca;
    @FXML
    private Button VEICULOSbtNovo;
    @FXML
    private Button VEICULOSbtAlterar;
    @FXML
    private Button VEICULOSbtApagar;
    @FXML
    private VBox apDadosVeiculos;
    @FXML
    private AnchorPane aux_dadosveiculos;
    @FXML
    private Label VEICULOSTelalbCategoria;
    @FXML
    private ComboBox<String> VEICULOSTelacbCategoria;
    @FXML
    private Label VEICULOSlbCod;
    @FXML
    private TextField VEICULOStfCod;
    @FXML
    private Button VEICULOSTelabtCancelar;
    @FXML
    private Button VEICULOSTelabtConfirmar;
    @FXML
    private ImageView imvVeiculos;
    @FXML
    private Label VEICULOSTelalbMarca;
    @FXML
    private ComboBox<String> VEICULOSTelacbMarca;
    @FXML
    private Label VEICULOSTelalbModelo;
    @FXML
    private TextField VEICULOSTelatfModelo;
    @FXML
    private Label VEICULOSTelalbCor;
    @FXML
    private ComboBox<String> VEICULOSTelacbCor;
    @FXML
    private Label VEICULOSTelalbPlaca;
    @FXML
    private TextField VEICULOSTelatfPlaca;
    @FXML
    private Label VEICULOSlbProprietario;
    @FXML
    private TextField VEICULOStfProprietario;
    @FXML
    private Button VEICULOSbtProprietario;
    @FXML
    private TableColumn VEICULOScolModelo;
    @FXML
    private TableColumn VEICULOScolMarca;
    @FXML
    private TableColumn VEICULOScolCor;
    @FXML
    private ComboBox<String> VEICULOScbFiltrarCategoria;
    @FXML
    private Button VEICULOSTelabtCategoria;
    @FXML
    private Button VEICULOSTelabtMarca;
    @FXML
    private Button VEICULOSTelabtCor;
    @FXML
    private TableView<Movimentacao> tabelaEntrada;
    @FXML
    private TableView<Movimentacao> tabelaSaida;
    @FXML
    private TableColumn ENTRADAcolNome;
    @FXML
    private TableColumn ENTRADAcolVeiculo;
    @FXML
    private TableColumn ENTRADAcolData;
    @FXML
    private TableColumn SAIDAcolNome;
    @FXML
    private TableColumn SAIDAcolVeiculo;
    @FXML
    private TableColumn SAIDAcolData;
    @FXML
    private CheckBox ckbUltimaMov;
    @FXML
    private Label lbEntrada;
    @FXML
    private Label lbSaida;
    @FXML
    private Button btDesfazer;
    @FXML
    private Button btVerDadosPessoais;
    @FXML
    private TextField txPesquisarEntrada;
    @FXML
    private TextField txPesquisarSaida;
    @FXML
    private AnchorPane apCaixa;
    @FXML
    private Group apTabelaVeiculos1;
    @FXML
    private Label CAIXAlbData;
    @FXML
    private Label CAIXAlbSaldo;
    @FXML
    private Label CAIXAlbSituacao;
    @FXML
    private Button CAIXAbtAbrir;
    @FXML
    private TableView<MovCaixa> CAIXAtabelaEntrada;
    @FXML
    private TableView<MovCaixa> CAIXAtabelaSaida;
    @FXML
    private Label CAIXAlbEntrada;
    @FXML
    private Label CAIXAlbSaida;
    @FXML
    private TableColumn CAIXAcolValorSaida;
    @FXML
    private TableColumn CAIXAcolHorarioSaida;
    @FXML
    private Button CAIXAbtMovimentar;
    @FXML
    private Label CAIXAlbHoje;
    @FXML
    private TableColumn CAIXAcolDescricaoSaida;
    @FXML
    private TableColumn CAIXAcolDescricaoEntrada;
    @FXML
    private TableColumn CAIXAcolValorEntrada;
    @FXML
    private TableColumn CAIXAcolHorarioEntrada;
    @FXML
    private ImageView imvCaixa;
    @FXML
    private AnchorPane apRecebimentos;
    @FXML
    private TableView<ParcelaRec> tabelaParcelasReceber;
    @FXML
    private ComboBox<String> cbFiltrarParcelasReceber;
    @FXML
    private TextField tfPessoa;
    @FXML
    private AnchorPane apDatasReceber;
    @FXML
    private DatePicker dpDataInicial;
    @FXML
    private DatePicker dpDataFinal;
    @FXML
    private CheckBox ckbFiltrarPeriodoReceber;
    @FXML
    private CheckBox ckbFiltrarPorPessoa;
    @FXML
    private Button btReceber;
    @FXML
    private Button btEstornarRecebimento;
    @FXML
    private Button btLimparRecebimento;
    @FXML
    private ImageView imvRecebimentos;
    @FXML
    private TableColumn RECEBIMENTOScolConta;
    @FXML
    private TableColumn RECEBIMENTOScolNumero;
    @FXML
    private TableColumn RECEBIMENTOScolVencimento;
    @FXML
    private TableColumn RECEBIMENTOScolValor;
    @FXML
    private TableColumn RECEBIMENTOScolPagamento;
    @FXML
    private TableColumn RECEBIMENTOScolValorPago;
    @FXML
    private TableColumn RECEBIMENTOScolForma;
    
    //váriaveis globais     
    private CtrGerenciarPessoas ctrGerenciarPessoas = new CtrGerenciarPessoas();
    private CtrGerenciarEmpresas ctrGerenciarEmpresas = new CtrGerenciarEmpresas();
    private CtrGerenciarVeiculos ctrGerenciarVeiculos = new CtrGerenciarVeiculos();
    private CtrRealizarMovimentacao ctrRealizarMovimentacao = new CtrRealizarMovimentacao();
    private CtrFluxoCaixa ctrFluxoCaixa = new CtrFluxoCaixa();
    private CtrRealizarRecebimento ctrRealizarRecebimento = new CtrRealizarRecebimento();
    private CtrLancarContaReceber ctrLancarContaReceber = new CtrLancarContaReceber();
    private CtrRealizarPagamento ctrRealizarPagamento = new CtrRealizarPagamento();
    private CtrLancarContaPagar ctrLancarContaPagar = new CtrLancarContaPagar();
    private CtrRegistrarEvento ctrRegistrarEvento = new CtrRegistrarEvento();
    private Webcam webcam;   
    private ToggleGroup grupoCondutor;
    private ToggleGroup grupoResidencia;
    private ToggleGroup grupoNivel;
    private ToggleGroup grupoAutorizado;
    private ToggleGroup grupoSituacao;
    private ToggleGroup grupoAcesso;
    private ToggleGroup grupoUltimaMovimentacao;
    private int codigo_supervisor;
    private int codigo_proprietario;
    private int codigo_pessoa;
    private int codigo_participante;
    private int codigo_empresa;
    public static int movimentacao = 0;
    public static int usuario = 0;
    public static int parcelarec = 0;
    public static int parcelapag = 0;
    public static int caixa = 0;
    private int parcela_alterar = 0;
    private int pr_seq = 0;
    private int pp_seq = 0;
    private Parametros params = null;
    public static Stage TelaSelecionarPessoa = null;
    public static Stage TelaSelecionarEmpresa = null;
    public static Stage TelaEscolherOpcao = null;
    public static Stage TelaGerenciarOpcoes = null;
    public static Stage TelaRealizarMovimentacao = null;
    public static Stage TelaMovimentarCaixa = null;
    public static Stage TelaRealizarRecebimento = null;
    public static Stage TelaRealizarPagamento = null;
    public static Stage TelaSaldoInicial = null;
    public static Stage TelaRelatorioMovimentacoes = null;
    public static Stage TelaRelatorioPessoas = null;
    public static Stage TelaRelatorioVeiculos = null;
    public static Stage TelaRelatorioOutros = null;
    public static Stage TelaRelatorioContas = null;
    public static Stage TelaRelatorioEventos = null;
    public static Stage TelaEscolherTipoAjuste = null;
    private String erros = "";
    @FXML
    private Button btVer;
    @FXML
    private AnchorPane apContasReceber;
    @FXML
    private Group apTabelaContasReceber;
    @FXML
    private TextField CONTARECEBERtxPesquisa;
    @FXML
    private TableView<ContaReceber> CONTARECEBERtabela;
    @FXML
    private Button CONTARECEBERbtNovo;
    @FXML
    private Button CONTARECEBERbtAlterar;
    @FXML
    private Button CONTARECEBERbtApagar;
    @FXML
    private VBox apDadosContaReceber;
    @FXML
    private AnchorPane aux_dadoscontareceber;
    @FXML
    private Label CONTARECEBERlbCod;
    @FXML
    private TextField CONTARECEBERtfCod;
    @FXML
    private Label CONTARECEBERlbDescricao;
    @FXML
    private Label CONTARECEBERlbPessoa;
    @FXML
    private TextField CONTARECEBERtfDescricao;
    @FXML
    private TextField CONTARECEBERtfValor;
    @FXML
    private TextField CONTARECEBERtfPessoa;
    @FXML
    private Label CONTARECEBERlbForma;
    @FXML
    private ComboBox<String> CONTARECEBERcbForma;
    @FXML
    private Button CONTARECEBERbtConfigurar;
    @FXML
    private Label CONTARECEBERlbDataVencimento;
    @FXML
    private Label CONTARECEBERlbValor;
    @FXML
    private DatePicker CONTARECEBERdpDataVencimento;
    @FXML
    private TextArea CONTARECEBERtfObservacoes;
    @FXML
    private Button CONTARECEBERbtCancelar;
    @FXML
    private Button CONTARECEBERbtConfirmar;
    @FXML
    private ImageView imvContas;
    @FXML
    private TableColumn CONTARECEBERcolDescricao;
    @FXML
    private TableColumn CONTARECEBERcolPessoa;
    @FXML
    private TableColumn CONTARECEBERcolValor;
    @FXML
    private Button CONTARECEBERbtSelecionarPessoa;
    @FXML
    private Group apParcelasReceber;
    @FXML
    private Label PARCELASRECEBERlbValorEntrada;
    @FXML
    private TextField PARCELASRECEBERtfValorEntrada;
    @FXML
    private Label PARCELASRECEBERlbNumeroParcelas;
    @FXML
    private Spinner<Integer> PARCELASRECEBERtfNumeroParcelas;
    @FXML
    private Label PARCELASRECEBERlbDataInicio;
    @FXML
    private DatePicker PARCELASRECEBERdpDataInicio;
    @FXML
    private Button PARCELASRECEBERbtCancelar;
    @FXML
    private Button PARCELASRECEBERbtConfirmar;
    @FXML
    private Label PARCELASRECEBERlbParcelas;
    @FXML
    private TableView<ParcelaRec> PARCELASRECEBERtabela;
    @FXML
    private Label PARCELASRECEBERlbDoisCliques;
    @FXML
    private Button PARCELASRECEBERbtGerar;
    @FXML
    private TableColumn PARCELASRECEBERcolNumero;
    @FXML
    private TableColumn PARCELASRECEBERcolDataVencimento;
    @FXML
    private TableColumn PARCELASRECEBERcolValor;
    @FXML
    private AnchorPane apParcelasReceberaux;
    private AnchorPane aux_dadosrecebimento;
    @FXML
    private Label RECEBIMENTOlbFormaPagamento;
    @FXML
    private ComboBox<String> RECEBIMENTOcbFormaPagamento;
    @FXML
    private AnchorPane RECEBIMENTOapCheque;
    @FXML
    private TextField RECEBIMENTOtfChequeNumero;
    @FXML
    private Label RECEBIMENTOlbChequeNumero;
    @FXML
    private TextField RECEBIMENTOtfChequeConta;
    @FXML
    private Label RECEBIMENTOlbChequeConta;
    @FXML
    private TextField RECEBIMENTOtfChequeBanco;
    @FXML
    private Label RECEBIMENTOlbChequeBanco;
    @FXML
    private AnchorPane RECEBIMENTOapBoleto;
    @FXML
    private TextField RECEBIMENTOtfBoletoNumero;
    @FXML
    private Label RECEBIMENTOlbBoletoNumero;
    @FXML
    private AnchorPane RECEBIMENTOapDeposito;
    @FXML
    private TextField RECEBIMENTOtfDepositoIdentificacao;
    @FXML
    private Label RECEBIMENTOlbDepositoIdentificacao;
    @FXML
    private Group apFormaRecebimento;
    @FXML
    private AnchorPane apFormaRecebimentoaux;
    @FXML
    private Button RECEBIMENTObtCancelar;
    @FXML
    private Button RECEBIMENTObtConfirmar;
    @FXML
    private TableColumn PARCELASRECEBERcolValorPago;
    @FXML
    private AnchorPane PARCELASRECEBERapAlterarParcela;
    @FXML
    private TextField PARCELASRECEBERtfParcelaNumero;
    @FXML
    private Label PARCELASRECEBERlbParcelaNumero;
    @FXML
    private DatePicker PARCELASRECEBERdpParcelaData;
    @FXML
    private Label PARCELASRECEBERlbParcelaData;
    @FXML
    private TextField PARCELASRECEBERtfParcelaValor;
    @FXML
    private Label PARCELASRECEBERlbParcelaValor;
    @FXML
    private AnchorPane PARCELASRECEBERapGerarParcela;
    @FXML
    private Button PARCELASRECEBERbtCancelarParcela;
    @FXML
    private Button PARCELASRECEBERbtConfirmarParcela;
    @FXML
    private Button PARCELASRECEBERbtAjustar;
    @FXML
    private AnchorPane apContas;
    @FXML
    private Group apTabelaContas;
    @FXML
    private AnchorPane apContasPagar;
    @FXML
    private Group apTabelaContasPagar;
    @FXML
    private TextField CONTAPAGARtxPesquisa;
    @FXML
    private TableView<ContaPagar> CONTAPAGARtabela;
    @FXML
    private TableColumn CONTAPAGARcolDescricao;
    @FXML
    private TableColumn CONTAPAGARcolPessoa;
    @FXML
    private TableColumn CONTAPAGARcolValor;
    @FXML
    private Button CONTAPAGARbtNovo;
    @FXML
    private Button CONTAPAGARbtAlterar;
    @FXML
    private Button CONTAPAGARbtApagar;
    @FXML
    private Group apParcelasPagar;
    @FXML
    private AnchorPane apParcelasPagaraux;
    @FXML
    private AnchorPane PARCELASPAGARapAlterarParcela;
    @FXML
    private TextField PARCELASPAGARtfParcelaNumero;
    @FXML
    private Label PARCELASPAGARlbParcelaNumero;
    @FXML
    private DatePicker PARCELASPAGARdpParcelaData;
    @FXML
    private Label PARCELASPAGARlbParcelaData;
    @FXML
    private TextField PARCELASPAGARtfParcelaValor;
    @FXML
    private Label PARCELASPAGARlbParcelaValor;
    @FXML
    private Button PARCELASPAGARbtCancelarParcela;
    @FXML
    private Button PARCELASPAGARbtConfirmarParcela;
    @FXML
    private AnchorPane PARCELASPAGARapGerarParcela;
    @FXML
    private Label PARCELASPAGARlbValorEntrada;
    @FXML
    private TextField PARCELASPAGARtfValorEntrada;
    @FXML
    private Label PARCELASPAGARlbNumeroParcelas;
    @FXML
    private Spinner<Integer> PARCELASPAGARtfNumeroParcelas;
    @FXML
    private Label PARCELASPAGARlbDataInicio;
    @FXML
    private DatePicker PARCELASPAGARdpDataInicio;
    @FXML
    private Button PARCELASPAGARbtGerar;
    @FXML
    private Button PARCELASPAGARbtAjustar;
    @FXML
    private Label PARCELASPAGARlbParcelas;
    @FXML
    private Label PARCELASPAGARlbDoisCliques;
    @FXML
    private TableView<ParcelaPag> PARCELASPAGARtabela;
    @FXML
    private TableColumn PARCELASPAGARcolNumero;
    @FXML
    private TableColumn PARCELASPAGARcolDataVencimento;
    @FXML
    private TableColumn PARCELASPAGARcolValor;
    @FXML
    private TableColumn PARCELASPAGARcolValorPago;
    @FXML
    private Button PARCELASPAGARbtCancelar;
    @FXML
    private Button PARCELASPAGARbtConfirmar;
    @FXML
    private Group apFormaPagamento;
    @FXML
    private AnchorPane apFormaPagamentoaux;
    @FXML
    private Label PAGAMENTOlbFormaPagamento;
    @FXML
    private ComboBox<String> PAGAMENTOcbFormaPagamento;
    @FXML
    private AnchorPane PAGAMENTOapCheque;
    @FXML
    private TextField PAGAMENTOtfChequeNumero;
    @FXML
    private Label PAGAMENTOlbChequeNumero;
    @FXML
    private TextField PAGAMENTOtfChequeConta;
    @FXML
    private Label PAGAMENTOlbChequeConta;
    @FXML
    private TextField PAGAMENTOtfChequeBanco;
    @FXML
    private Label PAGAMENTOlbChequeBanco;
    @FXML
    private AnchorPane PAGAMENTOapBoleto;
    @FXML
    private TextField PAGAMENTOtfBoletoNumero;
    @FXML
    private Label PAGAMENTOlbBoletoNumero;
    @FXML
    private AnchorPane PAGAMENTOapDeposito;
    @FXML
    private TextField PAGAMENTOtfDepositoIdentificacao;
    @FXML
    private Label PAGAMENTOlbDepositoIdentificacao;
    @FXML
    private Button PAGAMENTObtCancelar;
    @FXML
    private Button PAGAMENTObtConfirmar;
    @FXML
    private VBox apDadosContaPagar;
    @FXML
    private AnchorPane aux_dadoscontapagar;
    @FXML
    private Label CONTAPAGARlbCod;
    @FXML
    private TextField CONTAPAGARtfCod;
    @FXML
    private Label CONTAPAGARlbDescricao;
    @FXML
    private Label CONTAPAGARlbPessoa;
    @FXML
    private TextField CONTAPAGARtfDescricao;
    @FXML
    private TextField CONTAPAGARtfValor;
    @FXML
    private TextField CONTAPAGARtfPessoa;
    @FXML
    private Button CONTAPAGARbtSelecionarPessoaEmpresa;
    @FXML
    private Label CONTAPAGARlbForma;
    @FXML
    private ComboBox<String> CONTAPAGARcbForma;
    @FXML
    private Button CONTAPAGARbtConfigurar;
    @FXML
    private Label CONTAPAGARlbDataVencimento;
    @FXML
    private Label CONTAPAGARlbValor;
    @FXML
    private DatePicker CONTAPAGARdpDataVencimento;
    @FXML
    private TextArea CONTAPAGARtfObservacoes;
    @FXML
    private Button CONTAPAGARbtCancelar;
    @FXML
    private Button CONTAPAGARbtConfirmar;
    @FXML
    private AnchorPane apPagamentos;
    @FXML
    private TableView<ParcelaPag> tabelaParcelasPagar;
    @FXML
    private TableColumn PAGAMENTOScolConta;
    @FXML
    private TableColumn PAGAMENTOScolNumero;
    @FXML
    private TableColumn PAGAMENTOScolVencimento;
    @FXML
    private TableColumn PAGAMENTOScolValor;
    @FXML
    private TableColumn PAGAMENTOScolPagamento;
    @FXML
    private TableColumn PAGAMENTOScolValorPago;
    @FXML
    private TableColumn PAGAMENTOScolForma;
    @FXML
    private ComboBox<String> cbFiltrarParcelasPagar;
    @FXML
    private TextField tfPessoaEmpresa;
    @FXML
    private AnchorPane apDatasPagar;
    @FXML
    private DatePicker dpDataInicialPagar;
    @FXML
    private DatePicker dpDataFinalPagar;
    @FXML
    private CheckBox ckbFiltrarPeriodoPagar;
    @FXML
    private CheckBox ckbFiltrarPorPessoaEmpresa;
    @FXML
    private Button btPagar;
    @FXML
    private Button btEstornarPagamento;
    @FXML
    private Button btVerPagamento;
    @FXML
    private Button btLimparPagamento;
    @FXML
    private AnchorPane apEmpresas;
    @FXML
    private Group apTabelaEmpresas;
    @FXML
    private TextField EMPRESAStxPesquisa;
    @FXML
    private TableView<Empresa> EMPRESAStabela;
    @FXML
    private TableColumn EMPRESAScolNome;
    @FXML
    private TableColumn EMPRESAScolCNPJ;
    @FXML
    private Button EMPRESASbtNovo;
    @FXML
    private Button EMPRESASbtAlterar;
    @FXML
    private Button EMPRESASbtApagar;
    @FXML
    private VBox apDadosEmpresas;
    @FXML
    private ScrollPane scrollDadosEmpresas;
    @FXML
    private AnchorPane aux_dadosempresas;
    @FXML
    private Label EMPRESASlbCod;
    @FXML
    private TextField EMPRESAStfCod;
    @FXML
    private Label EMPRESASlbCnpj;
    @FXML
    private TextField EMPRESAStfCNPJ;
    @FXML
    private Label EMPRESASlbNome;
    @FXML
    private TextField EMPRESAStfNome;
    @FXML
    private Button EMPRESASbtCarregarIMG;
    @FXML
    private Button EMPRESASbtApagarIMG;
    @FXML
    private ImageView EMPRESASimg;
    @FXML
    private ImageView EMPRESASbtnCamera;
    @FXML
    private Label EMPRESASlbTipoLog;
    @FXML
    private ComboBox<String> EMPRESAScbTipoLog;
    @FXML
    private Label EMPRESASlbLog;
    @FXML
    private TextField EMPRESAStfLogradouro;
    @FXML
    private Label EMPRESASlbNumero;
    @FXML
    private TextField EMPRESAStfNum;
    @FXML
    private Label EMPRESASlbCep;
    @FXML
    private TextField EMPRESAStfCEP;
    @FXML
    private Label EMPRESASlbBairro;
    @FXML
    private TextField EMPRESAStfBairro;
    @FXML
    private Label EMPRESASlbCidade;
    @FXML
    private ComboBox<String> EMPRESAScbUF;
    @FXML
    private Label EMPRESASlbUf;
    @FXML
    private ComboBox<String> EMPRESAScbCidade;
    @FXML
    private Label EMPRESASlbTelefone2;
    @FXML
    private Label EMPRESASlbTelefone;
    @FXML
    private TextField EMPRESAStfTelfone;
    @FXML
    private TextField EMPRESAStfTelfone2;
    @FXML
    private Label EMPRESASlbEmail;
    @FXML
    private TextField EMPRESAStfEmail;
    @FXML
    private TextField EMPRESAStxPesquisarCNPJ;
    @FXML
    private Button EMPRESASbtCancelar;
    @FXML
    private Button EMPRESASbtConfirmar;
    @FXML
    private ImageView imvEmpresas;
    @FXML
    private AnchorPane PAGAMENTOapCartao;
    @FXML
    private ComboBox<CartaoCredito> PAGAMENTOcbCartao;
    @FXML
    private Label PAGAMENTOlbCartao;
    @FXML
    private Text PAGAMENTOlbAviso;
    @FXML
    private Text RECEBIMENTOlbAviso;
    @FXML
    private ImageView ajudaPESSOASlote;
    @FXML
    private ImageView ajudaPESSOASveiculo;
    @FXML
    private ImageView ajudaPESSOASdependente;
    @FXML
    private ImageView ajudaPESSOASDepDescricao;
    @FXML
    private ImageView ajudaPESSOASLoteDescricao;
    @FXML
    private ImageView ajudaPESSOASDataNasc;
    @FXML
    private ImageView ajudaDEPENDENTESDescricao;
    @FXML
    private ImageView ajudaCAIXAEntrada;
    @FXML
    private ImageView ajudaCAIXASaida;
    @FXML
    private Button PESSOASbtCEP;
    @FXML
    private Button DEPENDENTESbtCEP;
    @FXML
    private Button EMPRESASbtCEP;
    @FXML
    private Button btSelecionarPessoa;
    @FXML
    private Button btSelecionarPessoaEmpresa;
    @FXML
    private ImageView iconeerro;
    @FXML
    private ImageView msgerro;
    @FXML
    private Label PARCELASRECEBERlbSoma;
    @FXML
    private Label PARCELASPAGARlbSoma;
    @FXML
    private ImageView imvContasaPagar;
    @FXML
    private ImageView imvContasaReceber;
    @FXML
    private ImageView imvRealizarPagamento;
    @FXML
    private ImageView imvRealizarRecebimento;
    @FXML
    private AnchorPane apSubRelatorios;
    @FXML
    private ImageView imvRelMovimentacoes;
    @FXML
    private ImageView imvRelPessoas;
    @FXML
    private ImageView imvRelVeiculos;
    @FXML
    private ImageView imvRelOutros;
    @FXML
    private ImageView imvRelFinanceiro;
    @FXML
    private Group apTabelaEventos;
    @FXML
    private TextField EVENTOtxPesquisa;
    @FXML
    private TableView<Evento> EVENTOtabela;
    @FXML
    private TableColumn EVENTOcolDescricao;
    @FXML
    private TableColumn EVENTOcolPessoa;
    @FXML
    private TableColumn EVENTOcolSituacao;
    @FXML
    private Button EVENTObtNovo;
    @FXML
    private Button EVENTObtAlterar;
    @FXML
    private Button EVENTObtApagar;
    @FXML
    private Group apParticipantes;
    @FXML
    private AnchorPane apParticipantesaux;
    @FXML
    private Label ADICIONARPARTICIPANTElbDataFinal;
    @FXML
    private TableView<EventoParticipante> PARTICIPANTEtabela;
    @FXML
    private TableColumn PARTICIPANTEcolNome;
    @FXML
    private TableColumn PARTICIPANTEcolPeriodo;
    @FXML
    private VBox apDadosEvento;
    @FXML
    private AnchorPane aux_dadosevento;
    @FXML
    private Label EVENTOlbCod;
    @FXML
    private TextField EVENTOtfCod;
    @FXML
    private Label EVENTOlbDescricao;
    @FXML
    private Label EVENTOlbPessoa;
    @FXML
    private TextField EVENTOtfDescricao;
    @FXML
    private TextField EVENTOtfPessoa;
    @FXML
    private Button EVENTObtSelecionarPessoa;
    @FXML
    private Label EVENTOlbParticipantes;
    @FXML
    private TextField EVENTOtfParticipantes;
    @FXML
    private Button EVENTObtConfigurar;
    @FXML
    private Label EVENTOlbPeriodo;
    @FXML
    private DatePicker EVENTOdpDataInicial;
    @FXML
    private TextArea EVENTOtfObservacoes;
    @FXML
    private Button EVENTObtCancelar;
    @FXML
    private Button EVENTObtConfirmar;
    @FXML
    private DatePicker EVENTOdpDataFinal;
    @FXML
    private AnchorPane PARTICIPANTEapAdicionar;
    @FXML
    private TextField PARTICIPANTEtfParticipante;
    @FXML
    private Button PARTICIPANTEbtSelecionar;
    @FXML
    private DatePicker PARTICIPANTEdpDataInicial;
    @FXML
    private DatePicker PARTICIPANTEdpDataFinal;
    @FXML
    private Button PARTICIPANTEbtCancelar;
    @FXML
    private Button PARTICIPANTEbtAdicionar;
    @FXML
    private AnchorPane PARTICIPANTEapTabela;
    @FXML
    private Button PARTICIPANTETelabtAdicionar;
    @FXML
    private Button PARTICIPANTETelabtAlterar;
    @FXML
    private Button PARTICIPANTETelabtExcluir;
    @FXML
    private Button PARTICIPANTETelabtCancelar;
    @FXML
    private Button PARTICIPANTETelabtConfirmar;
    @FXML
    private CheckBox PARTICIPANTEckbTodoEvento;
    @FXML
    private Label PARTICIPANTElbParticipante;
    @FXML
    private Label PARTICIPANTElbPeriodo;
    @FXML
    private RadioButton PARTICIPANTErbSim;
    @FXML
    private RadioButton PARTICIPANTErbNao;
    @FXML
    private Label PARTICIPANTElbAutorizado;
    @FXML
    private AnchorPane EVENTOapSituacao;
    @FXML
    private RadioButton EVENTOrbAndamento;
    @FXML
    private RadioButton EVENTOrbFinalizado;
    @FXML
    private ComboBox<String> EVENTOcbFiltrarCategoria;
    @FXML
    private Label EVENTOlbCategoria;
    @FXML
    private ComboBox<String> EVENTOcbCategoria;
    @FXML
    private Button PARTICIPANTEbtAjustar;
    @FXML
    private TableColumn PARTICIPANTEcolAutorizado;
    @FXML
    private AnchorPane PESSOASapAcesso;
    @FXML
    private RadioButton PESSOASrbLivreAcesso;
    @FXML
    private RadioButton PESSOASrbMedianteAutorizacao;
    @FXML
    private RadioButton PESSOASrbNaoAutorizado;
    @FXML
    private Label PESSOASlbAcesso;
    @FXML
    private Label DEPENDENTESlbAcesso;
    @FXML
    private AnchorPane DEPENDENTESapAcesso;
    @FXML
    private RadioButton DEPENDENTESrbLivreAcesso;
    @FXML
    private RadioButton DEPENDENTESrbMedianteAutorizacao;
    @FXML
    private RadioButton DEPENDENTESrbNaoAutorizado;
    @FXML
    private ImageView imvRelEventos;
    @FXML
    private ImageView imvAjudaEventos;
    @FXML
    private ImageView imvAjudaDependentes;
    @FXML
    private ImageView imvAjudaPessoas;
    @FXML
    private ImageView imvAjudaEmpresas;
    @FXML
    private ImageView imvAjudaVeiculos;
    @FXML
    private ImageView imvIniciarAviso;
    @FXML
    private Button btRestaurar;
    @FXML
    private Button btIniciar;
    @FXML
    private AnchorPane apBackup;
    @FXML
    private Group apTabelaBackup;
    @FXML
    private Button BACKUPbtSelecionar;
    @FXML
    private Label BACKUPlbArquivo;
    @FXML
    private ImageView imvBackup;
    @FXML
    private ImageView imvRestauracao;
    @FXML
    private TextField BACKUPtfArquivo;
    @FXML
    private AnchorPane apRestauracao;
    @FXML
    private Group apTabelaRestauracao;
    @FXML
    private TextField RESTAURACAOtfArquivo;
    @FXML
    private Label RESTAURACAOlbArquivo;
    @FXML
    private Button RESTAURACAObtSelecionar;
    @FXML
    private ImageView imvAjudaMenu;
    @FXML
    private ImageView imvSistema;
    @FXML
    private ImageView imvAjudaContasPagar;
    @FXML
    private ImageView imvAjudaContasReceber;
    @FXML
    private ImageView imvAjudaPagamento;
    @FXML
    private ImageView imvAjudaRecebimento;
    @FXML
    private Label lbDriver;
    @FXML
    private ComboBox<String> cbDriver;
    @FXML
    private TextField tfServidor;
    @FXML
    private Label lbServidor;
    @FXML
    private TextField tfPorta;
    @FXML
    private Label lbPorta;
    @FXML
    private Label lbBase;
    @FXML
    private TextField tfUsuarioServidor;
    @FXML
    private TextField tfSenhaServidor;
    @FXML
    private Label lbUsuarioServidor;
    @FXML
    private Label lbSenhaServidor;
    @FXML
    private TextField tfBase;
    @FXML
    private AnchorPane apServidor;
    @FXML
    private ImageView imvBancoDados;
    @FXML
    private AnchorPane apTitulo;
    @FXML
    private AnchorPane apBancoDados;
    @FXML
    private Label lbBancodeDados;
    @FXML
    private AnchorPane apConteudo;
    @FXML
    private CheckBox ckbServidorLocal;
    @FXML
    private Hyperlink lbIP;
    @FXML
    private ProgressIndicator piCarregando;
    @FXML
    private ImageView imvAjudaCaixa;
    @FXML
    private RadioButton PESSOASrbEntrada;
    @FXML
    private RadioButton PESSOASrbSaida;
    @FXML
    private AnchorPane PESSOASapUltimaMovimentacao;
    @FXML
    private Label CONTAPAGARlbSaldo;
    @FXML
    private Label PARCELASPAGARlbSaldo;
    @FXML
    private AnchorPane DEPENDENTESapUltimaMovimentacao;
    @FXML
    private RadioButton DEPENDENTESrbEntrada;
    @FXML
    private RadioButton DEPENDENTESrbSaida;
    @FXML
    private AnchorPane VEICULOSapUltimaMovimentacao;
    @FXML
    private RadioButton VEICULOSrbEntrada;
    @FXML
    private RadioButton VEICULOSrbSaida;
    @FXML
    private Label VEICULOSlbAutorizado;
    @FXML
    private AnchorPane VEICULOSapAutorizado;
    @FXML
    private RadioButton VEICULOSrbSim;
    @FXML
    private RadioButton VEICULOSrbNao;
    @FXML
    private Label VEICULOSlbCamposObrigatorios;
    @FXML
    private Label PESSOASlbCamposObrigatorios;
    @FXML
    private Label DEPENDENTESlbCamposObrigatorios;
    @FXML
    private Label EMPRESASlbCamposObrigatorios;
    @FXML
    private Label CONTARECEBERlbCamposObrigatorios;
    @FXML
    private Label CONTAPAGARlbCamposObrigatorios;
    @FXML
    private Label EVENTOlbCamposObrigatorios;
    @FXML
    private ImageView imvDicaBancoDados;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {  
        CarregarDrivers();
        MaskCampos.placaField(VEICULOStfPlaca);
        MaskCampos.placaField(VEICULOSTelatfPlaca);
        DEPENDENTEStfSupervisor.setEditable(false);
        DEPENDENTEStfSupervisor.setFocusTraversable(false);
        DEPENDENTEStfSupervisor.setMouseTransparent(true);
        
        PARCELASRECEBERtabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        PARCELASPAGARtabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        PARTICIPANTEtabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Funcoes.updateTooltipBehavior(0, 30000, 0, true); //30 segundos de visibilidade dos componentes TOOLTIP
        
        PARCELASRECEBERtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        PARCELASPAGARtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        MaskCampos.monetaryField(PARCELASRECEBERtfValorEntrada);
        MaskCampos.monetaryField(CONTARECEBERtfValor);
        MaskCampos.monetaryField(PARCELASPAGARtfValorEntrada);
        MaskCampos.monetaryField(CONTAPAGARtfValor);
        MaskCampos.numericField(PARCELASRECEBERtfNumeroParcelas.getEditor(), 2); 
        MaskCampos.numericField(PARCELASPAGARtfNumeroParcelas.getEditor(), 2); 
        MaskCampos.numericField(tfPorta, 5);
        
        //Mascaras para Data
        MaskCampos.dateField(PESSOASdpData.getEditor());
        MaskCampos.dateField(PESSOASdpDataAdmissao.getEditor());
        MaskCampos.dateField(PESSOASdpDataDemissao.getEditor());
        MaskCampos.dateField(DEPENDENTESdpData.getEditor());
        MaskCampos.dateField(CONTAPAGARdpDataVencimento.getEditor());
        MaskCampos.dateField(CONTARECEBERdpDataVencimento.getEditor());
        MaskCampos.dateField(PARCELASPAGARdpDataInicio.getEditor());
        MaskCampos.dateField(PARCELASPAGARdpParcelaData.getEditor());
        MaskCampos.dateField(PARCELASRECEBERdpDataInicio.getEditor());
        MaskCampos.dateField(PARCELASRECEBERdpParcelaData.getEditor());        
        MaskCampos.dateField(dpDataInicial.getEditor());
        MaskCampos.dateField(dpDataFinal.getEditor());
        MaskCampos.dateField(dpDataInicialPagar.getEditor());
        MaskCampos.dateField(dpDataFinalPagar.getEditor());
        
        MaskCampos.dateField(EVENTOdpDataInicial.getEditor());
        MaskCampos.dateField(EVENTOdpDataFinal.getEditor());
        MaskCampos.dateField(PARTICIPANTEdpDataInicial.getEditor());
        MaskCampos.dateField(PARTICIPANTEdpDataFinal.getEditor());
        
        ENTRADAcolNome.setCellValueFactory(new PropertyValueFactory("condutor"));
        ENTRADAcolVeiculo.setCellValueFactory(new PropertyValueFactory("veiculo"));
        ENTRADAcolData.setCellValueFactory(new PropertyValueFactory("data"));
        
        SAIDAcolNome.setCellValueFactory(new PropertyValueFactory("condutor"));
        SAIDAcolVeiculo.setCellValueFactory(new PropertyValueFactory("veiculo"));
        SAIDAcolData.setCellValueFactory(new PropertyValueFactory("data"));
        
        PESSOAScolNome.setCellValueFactory(new PropertyValueFactory("nome"));
        PESSOAScolCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        DEPENDENTEScolNome.setCellValueFactory(new PropertyValueFactory("nome"));
        DEPENDENTEScolCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        
        EMPRESAScolNome.setCellValueFactory(new PropertyValueFactory("nome"));
        EMPRESAScolCNPJ.setCellValueFactory(new PropertyValueFactory("cnpj"));
        
        EVENTOcolDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        EVENTOcolPessoa.setCellValueFactory(new PropertyValueFactory("responsavel"));
        EVENTOcolSituacao.setCellValueFactory(new PropertyValueFactory("status"));
        PARTICIPANTEcolNome.setCellValueFactory(new PropertyValueFactory("participante"));
        PARTICIPANTEcolPeriodo.setCellValueFactory(new PropertyValueFactory("periodo"));
        PARTICIPANTEcolAutorizado.setCellValueFactory(new PropertyValueFactory("autorizado"));
        
        
        VEICULOScolModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        VEICULOScolPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
        VEICULOScolCor.setCellValueFactory(new PropertyValueFactory("cor"));
        VEICULOScolMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        
        
        PESSOAScolMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        PESSOAScolModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        PESSOAScolCor.setCellValueFactory(new PropertyValueFactory("cor"));
        PESSOAScolPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
        
        PESSOAScolNomeDep.setCellValueFactory(new PropertyValueFactory("nome"));
        PESSOAScolDescricaoDep.setCellValueFactory(new PropertyValueFactory("descricao"));
        PESSOAScolCondutorDep.setCellValueFactory(new PropertyValueFactory("condutor"));
        
        PESSOAScolNumeroLote.setCellValueFactory(new PropertyValueFactory("numero"));
        PESSOAScolQuadraLote.setCellValueFactory(new PropertyValueFactory("quadra"));
        PESSOAScolDescricaoLote.setCellValueFactory(new PropertyValueFactory("descricao"));
        PESSOAScolResidenciaLote.setCellValueFactory(new PropertyValueFactory("residencia"));
        
        CAIXAcolValorSaida.setCellValueFactory(new PropertyValueFactory("valor"));
        CAIXAcolHorarioSaida.setCellValueFactory(new PropertyValueFactory("horario"));
        CAIXAcolDescricaoSaida.setCellValueFactory(new PropertyValueFactory("descricao"));
        
        CAIXAcolValorEntrada.setCellValueFactory(new PropertyValueFactory("valor"));
        CAIXAcolHorarioEntrada.setCellValueFactory(new PropertyValueFactory("horario"));
        CAIXAcolDescricaoEntrada.setCellValueFactory(new PropertyValueFactory("descricao"));
        
        RECEBIMENTOScolConta.setCellValueFactory(new PropertyValueFactory("contareceber"));
        RECEBIMENTOScolNumero.setCellValueFactory(new PropertyValueFactory("n"));
        RECEBIMENTOScolVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        RECEBIMENTOScolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        RECEBIMENTOScolPagamento.setCellValueFactory(new PropertyValueFactory("data_pagamento"));
        RECEBIMENTOScolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        RECEBIMENTOScolForma.setCellValueFactory(new PropertyValueFactory("forma"));
        
        PAGAMENTOScolConta.setCellValueFactory(new PropertyValueFactory("contapagar"));
        PAGAMENTOScolNumero.setCellValueFactory(new PropertyValueFactory("n"));
        PAGAMENTOScolVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        PAGAMENTOScolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PAGAMENTOScolPagamento.setCellValueFactory(new PropertyValueFactory("data_pagamento"));
        PAGAMENTOScolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        PAGAMENTOScolForma.setCellValueFactory(new PropertyValueFactory("forma"));
                
        CONTARECEBERcolDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        CONTARECEBERcolPessoa.setCellValueFactory(new PropertyValueFactory("pessoa"));
        CONTARECEBERcolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PARCELASRECEBERcolNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        PARCELASRECEBERcolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PARCELASRECEBERcolDataVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        PARCELASRECEBERcolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        
        CONTAPAGARcolDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        CONTAPAGARcolPessoa.setCellValueFactory(new PropertyValueFactory("pessoaempresa")); //ou empresa (preciso adaptar)
        CONTAPAGARcolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PARCELASPAGARcolNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        PARCELASPAGARcolValor.setCellValueFactory(new PropertyValueFactory("valor"));
        PARCELASPAGARcolDataVencimento.setCellValueFactory(new PropertyValueFactory("data_vencimento"));
        PARCELASPAGARcolValorPago.setCellValueFactory(new PropertyValueFactory("valorpago"));
        
        grupoUltimaMovimentacao = new ToggleGroup();
        PESSOASrbEntrada.setToggleGroup(grupoUltimaMovimentacao);
        PESSOASrbSaida.setToggleGroup(grupoUltimaMovimentacao);
        DEPENDENTESrbEntrada.setToggleGroup(grupoUltimaMovimentacao);
        DEPENDENTESrbSaida.setToggleGroup(grupoUltimaMovimentacao);
        VEICULOSrbEntrada.setToggleGroup(grupoUltimaMovimentacao);
        VEICULOSrbSaida.setToggleGroup(grupoUltimaMovimentacao);
        
        grupoAcesso = new ToggleGroup();
        PESSOASrbLivreAcesso.setToggleGroup(grupoAcesso);
        PESSOASrbMedianteAutorizacao.setToggleGroup(grupoAcesso);
        PESSOASrbNaoAutorizado.setToggleGroup(grupoAcesso);
        DEPENDENTESrbLivreAcesso.setToggleGroup(grupoAcesso);
        DEPENDENTESrbMedianteAutorizacao.setToggleGroup(grupoAcesso);
        DEPENDENTESrbNaoAutorizado.setToggleGroup(grupoAcesso);
        
        grupoCondutor = new ToggleGroup();
        DEPENDENTESrbSim.setToggleGroup(grupoCondutor);
        DEPENDENTESrbNao.setToggleGroup(grupoCondutor);
        
        grupoResidencia = new ToggleGroup();
        LOTESrbSim.setToggleGroup(grupoResidencia);
        LOTESrbNao.setToggleGroup(grupoResidencia);
        
        grupoNivel = new ToggleGroup();
        USUARIOrbAdministrador.setToggleGroup(grupoNivel);
        USUARIOrbBasico.setToggleGroup(grupoNivel);
        
        grupoAutorizado = new ToggleGroup();
        PARTICIPANTErbSim.setToggleGroup(grupoAutorizado);
        PARTICIPANTErbNao.setToggleGroup(grupoAutorizado);
        
        grupoSituacao = new ToggleGroup();
        EVENTOrbAndamento.setToggleGroup(grupoSituacao);
        EVENTOrbFinalizado.setToggleGroup(grupoSituacao);
        VEICULOSrbSim.setToggleGroup(grupoSituacao);
        VEICULOSrbNao.setToggleGroup(grupoSituacao);
        
        MaskCampos.cpfField(PESSOAStfCPF);        
        MaskCampos.cpfField(PESSOAStxPesquisarCPF);
        MaskCampos.cepField(PESSOAStfCEP);
        MaskCampos.PhoneField(PESSOAStfTelfone);
        MaskCampos.PhoneField(PESSOAStfTelfone2);
        MaskCampos.monetaryField(PESSOAStfSalario);
        
        MaskCampos.cpfField(DEPENDENTEStfCPF);        
        MaskCampos.cpfField(DEPENDENTEStxPesquisarCPF);
        MaskCampos.cepField(DEPENDENTEStfCEP);
        MaskCampos.PhoneField(DEPENDENTEStfTelfone);
        MaskCampos.PhoneField(DEPENDENTEStfTelfone2);
        
        MaskCampos.cnpjField(EMPRESAStfCNPJ);
        MaskCampos.cnpjField(EMPRESAStxPesquisarCNPJ);
        MaskCampos.cepField(EMPRESAStfCEP);
        MaskCampos.PhoneField(EMPRESAStfTelfone);
        MaskCampos.PhoneField(EMPRESAStfTelfone2);
        
        MaskCampos.monetaryField(PARCELASRECEBERtfParcelaValor);
        MaskCampos.monetaryField(PARCELASPAGARtfParcelaValor);
        
        PESSOASbtConfirmar.setTooltip(new Tooltip());
        EMPRESASbtConfirmar.setTooltip(new Tooltip());
        DEPENDENTESTelabtConfirmar.setTooltip(new Tooltip());
        VEICULOSTelabtConfirmar.setTooltip(new Tooltip());
        VEICULOSbtConfirmar.setTooltip(new Tooltip());
        VEICULOSTelabtCategoria.setTooltip(new Tooltip("Configurar categorias"));
        VEICULOSTelabtCor.setTooltip(new Tooltip("Configurar cores"));
        VEICULOSTelabtMarca.setTooltip(new Tooltip("Configurar marcas"));
        VEICULOSbtCategoria.setTooltip(new Tooltip("Configurar categorias")); //exibe texto de dica sobre o componente
        VEICULOSbtCor.setTooltip(new Tooltip("Configurar cores"));
        VEICULOSbtMarca.setTooltip(new Tooltip("Configurar marcas")); 
        VEICULOSbtProprietario.setTooltip(new Tooltip("Selecionar proprietário"));
        DEPENDENTESbtSupervisor.setTooltip(new Tooltip("Selecionar responsável"));
        EVENTObtSelecionarPessoa.setTooltip(new Tooltip("Selecionar organizador"));
        PARTICIPANTEbtSelecionar.setTooltip(new Tooltip());
        PARCELASRECEBERbtAjustar.setTooltip(new Tooltip("Você pode selecionar mais de uma parcela, basta segurar a tecla CTRL ou SHIFT e selecionar com o MOUSE."));
        PARCELASPAGARbtAjustar.setTooltip(new Tooltip("Você pode selecionar mais de uma parcela, basta segurar a tecla CTRL ou SHIFT e selecionar com o MOUSE."));
        PARTICIPANTEbtAjustar.setTooltip(new Tooltip("Você pode selecionar mais de um participante, basta segurar a tecla CTRL ou SHIFT e selecionar com o MOUSE."));
        PESSOASbtCEP.setTooltip(new Tooltip("Faça uma pesquisa do CEP informado, retornando a localização do mesmo."));
        EMPRESASbtCEP.setTooltip(new Tooltip("Faça uma pesquisa do CEP informado, retornando a localização do mesmo."));
        DEPENDENTESbtCEP.setTooltip(new Tooltip("Faça uma pesquisa do CEP informado, retornando a localização do mesmo."));
        PESSOASbtCarregarIMG.setTooltip(new Tooltip("Carregar foto do computador."));
        EMPRESASbtCarregarIMG.setTooltip(new Tooltip("Carregar foto do computador."));
        DEPENDENTESbtCarregarIMG.setTooltip(new Tooltip("Carregar foto do computador."));
        CONTAPAGARbtSelecionarPessoaEmpresa.setTooltip(new Tooltip("Selecionar empresa/pessoa"));
        CONTARECEBERbtSelecionarPessoa.setTooltip(new Tooltip("Selecionar pessoa"));
        CONTARECEBERbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        CONTAPAGARbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        btSelecionarPessoa.setTooltip(new Tooltip("Selecionar pessoa"));
        btSelecionarPessoaEmpresa.setTooltip(new Tooltip("Selecionar empresa/pessoa"));
        BACKUPbtSelecionar.setTooltip(new Tooltip("Salvar backup"));
        RESTAURACAObtSelecionar.setTooltip(new Tooltip("Selecionar backup"));
        DEPENDENTESbtConfirmar.setTooltip(new Tooltip());
        LOTESbtConfirmar.setTooltip(new Tooltip());
        CONTARECEBERbtConfirmar.setTooltip(new Tooltip());
        CONTAPAGARbtConfirmar.setTooltip(new Tooltip());
        EVENTObtConfirmar.setTooltip(new Tooltip());
        Tooltip.install(ajudaPESSOASlote, new Tooltip("Indique os lotes da pessoa no condomínio. Ex: residências, terrenos, etc."));
        Tooltip.install(ajudaPESSOASveiculo, new Tooltip("Indique os veículos utilizados pela pessoa e seus dependentes."));        
        Tooltip.install(ajudaPESSOASdependente, new Tooltip("Indique os dependentes, quem mora com ela. Ex: esposa, filho, etc."));        
        Tooltip.install(ajudaPESSOASDepDescricao, new Tooltip("Ex: esposa, filho, etc."));        
        Tooltip.install(ajudaPESSOASLoteDescricao, new Tooltip("Pode ser o endereço ou alguma observação."));        
        Tooltip.install(ajudaPESSOASDataNasc, new Tooltip("Idade mínima de 18 anos."));   
        Tooltip.install(ajudaDEPENDENTESDescricao, new Tooltip("Informe o vínculo desta pessoa com seu responsável. Ex: esposa, filho, etc."));    
        Tooltip.install(ajudaCAIXAEntrada, new Tooltip("Exibe as movimentações de entrada do caixa desta sessão. Ex: aportes, recebimentos, etc."));    
        Tooltip.install(ajudaCAIXASaida, new Tooltip("Exibe as movimentações de retirada do caixa desta sessão. Ex: sangrias, pagamentos, etc."));    
        Tooltip.install(PESSOASbtnCamera, new Tooltip("Tirar foto usando a câmera."));
        Tooltip.install(DEPENDENTESbtnCamera, new Tooltip("Tirar foto usando a câmera."));
        Tooltip.install(EMPRESASbtnCamera, new Tooltip("Tirar foto usando a câmera."));
        Tooltip.install(imvAjudaEventos, new Tooltip("Obter ajuda")); 
        Tooltip.install(imvAjudaDependentes, new Tooltip("Obter ajuda")); 
        Tooltip.install(imvAjudaPessoas, new Tooltip("Obter ajuda")); 
        Tooltip.install(imvAjudaEmpresas, new Tooltip("Obter ajuda")); 
        Tooltip.install(imvAjudaVeiculos, new Tooltip("Obter ajuda")); 
        Tooltip.install(imvAjudaContasPagar, new Tooltip("Obter ajuda"));
        Tooltip.install(imvAjudaContasReceber, new Tooltip("Obter ajuda"));
        Tooltip.install(imvAjudaPagamento, new Tooltip("Obter ajuda"));
        Tooltip.install(imvAjudaRecebimento, new Tooltip("Obter ajuda"));
        Tooltip.install(imvAjudaCaixa, new Tooltip("Obter ajuda"));
        
        TelaOriginal(true); //tela padrão: Histórico
        MenuOriginal(true); //menu no estado original
        tfUsuario.setVisible(false);
        tfSenha.setVisible(false);        
        btLogin.setVisible(false);
        lbUsuario.setVisible(false);
        lbSenha.setVisible(false);
        imvLoginAviso.setVisible(false);
        webcam = Webcam.getDefault();
        
        if(Banco.carregar_servidor() == null) //servidor não foi configurado ainda, exibir tela de configuração
        {
            TelaServidor(true);                        
        }
        else
        {
            try 
            {
                if(!Banco.conectar(Banco.carregar_servidor()))
                {
                    if(!Banco.criarBD())
                    {
                        //JOptionPane.showMessageDialog(null, Banco.con.getMensagemErro());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Não Conectado");
                        alert.setContentText("A conexão falhou.");
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());
                        alert.showAndWait();
                        TelaServidor(true);
                        Banco.con = null;
                        return;
                    }
                    
                    String arquivo = "";
                    try
                    {
                        Banco.Restaurar(arquivo);
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(!Banco.conectar())
                    {
                        //JOptionPane.showMessageDialog(null, Banco.con.getMensagemErro());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Não Conectado");
                        alert.setContentText("A conexão falhou.");
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());
                        alert.showAndWait();
                        TelaServidor(true);
                        Banco.con = null;
                        return;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            params = Banco.con.getParametros();
            //verificar se existe administrador, se não primeiro uso! (FAZER CADASTRO DE PESSOA COM USUÁRIO ADMINISTRADOR)
            if(!ctrGerenciarPessoas.getCtrpessoa().getUsuario().ExisteAdministrador())
                TelaInicial(true);
            else
                TelaLogin(true);
        }
        
        
    }
    
    private void CarregarDrivers()
    {
        ArrayList <String> drivers = new ArrayList();        
        drivers.add("org.postgresql.Driver");
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(drivers);
        cbDriver.setItems(modelo);
    }
    
    @FXML
    private void clkRestaurar(ActionEvent event) throws IOException, InterruptedException 
    {
        Node source = (Node) event.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("backups"));
        fileChooser.setTitle("Selecione o Backup");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Backup (.backup)", "*.backup"));
        File arq = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (arq != null) 
            if(Banco.Restaurar(arq.getCanonicalPath()))
            {
                if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().ExisteAdministrador())
                   TelaLogin(true);
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Atenção");
                    alert.setHeaderText("Atenção");
                    alert.setContentText("O sistema não identificou um administrador.");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
                    alert.showAndWait();
                    btIniciar.requestFocus();
                }
            }
        
    }

    @FXML
    private void clkIniciar(ActionEvent event) throws SQLException {
        PrimeiroUso(true);
    }
    
    
    private void TelaInicial(boolean status)
    {
        ObservableList <Node> componentes = apEsquerdo.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        componentes = apDireito.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        btSair.setVisible(!status);
        lbBemVindo.setVisible(!status);
        lbBemVindo.setText("");
                
        btIniciar.setVisible(status);
        btRestaurar.setVisible(status);   
        imvIniciarAviso.setVisible(status);        
        apEsquerdo.setDisable(false);
        iconeerro.setVisible(false);
        msgerro.setVisible(false);
        if(status)
        {
            ctrGerenciarPessoas.getCtrpessoa().getUsuario().setCod(0);
            usuario = 0;
        }
    }
    
    private void TelaLogin(boolean status)
    {
        ObservableList <Node> componentes = apEsquerdo.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        componentes = apDireito.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        btSair.setVisible(!status);
        lbBemVindo.setVisible(!status);
        lbBemVindo.setText("");
                
        tfUsuario.setVisible(status);
        tfSenha.setVisible(status);        
        btLogin.setVisible(status);
        lbUsuario.setVisible(status);
        lbSenha.setVisible(status);
        imvLoginAviso.setVisible(status);
        tfUsuario.requestFocus();
        tfUsuario.setText("");
        tfSenha.setText("");  
        apEsquerdo.setDisable(false);
        iconeerro.setVisible(false);
        msgerro.setVisible(false);
        btRestaurar.setVisible(false);
        btIniciar.setVisible(false);
        imvIniciarAviso.setVisible(false);
        if(status)
        {
            ctrGerenciarPessoas.getCtrpessoa().getUsuario().setCod(0);
            usuario = 0;
        }
    }
    
    private void TelaServidor(boolean status)
    {
        ObservableList <Node> componentes = apEsquerdo.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        componentes = apDireito.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setVisible(!status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setVisible(!status);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(!status);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setVisible(!status);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setVisible(!status);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setVisible(!status);
            if(n instanceof CheckBox)
                ((CheckBox)n).setVisible(!status);
            if(n instanceof Button)
            {
                ((Button)n).setVisible(!status);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(!status);
            }   
            if(n instanceof ImageView)
            {
                ((ImageView)n).setVisible(!status);
            } 
        } 
        btSair.setVisible(!status);
        lbBemVindo.setVisible(!status);
        lbBemVindo.setText("");
                
        apServidor.setVisible(status);
        apBancoDados.setVisible(status);
        imvDicaBancoDados.setVisible(status);
        apServidor.setLayoutX(-180);
        
        cbDriver.getSelectionModel().select(0);
        tfServidor.setText("localhost");
        tfServidor.setDisable(status);
        tfPorta.setText("5432");
        tfUsuarioServidor.setText("postgres");
        tfSenhaServidor.setText("postgres123");
        tfBase.setText("siscon");        
        apDireito.setDisable(false);
        tfUsuario.setVisible(false);
        tfSenha.setVisible(false);        
        btLogin.setVisible(false);
        lbUsuario.setVisible(false);
        lbSenha.setVisible(false);
        imvLoginAviso.setVisible(false);
        iconeerro.setVisible(false);
        msgerro.setVisible(false);
        btRestaurar.setVisible(false);
        btIniciar.setVisible(false);
        imvIniciarAviso.setVisible(false);
        cbDriver.requestFocus();
    }
    
    private void PrimeiroUso(boolean status) throws SQLException
    {
        HabilitaTela(imvPessoas);
        if(status)
        {
            imvIniciarAviso.setVisible(false);
            btRestaurar.setVisible(false);
            btIniciar.setVisible(false);
            apPessoas.setLayoutX(-280);
            apPessoas.setLayoutY(67);
            PESSOASbtCancelar.setText("VOLTAR");
        }
        else
        {
            apPessoas.setLayoutX(7);
            apPessoas.setLayoutY(163);
            PESSOASbtCancelar.setText("CANCELAR");
            
        }
        
        ctrGerenciarPessoas.CarregarComboBox(PESSOAScbCategoria, "", "USUÁRIO");        
        Novo();        
        USUARIOrbAdministrador.setSelected(status);
        USUARIOrbBasico.setDisable(status);
        PESSOASrbMedianteAutorizacao.setDisable(status);
        PESSOASrbNaoAutorizado.setDisable(status);
        if(status)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Cadastre uma pessoa e um usuário de acesso ao sistema.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            alert.showAndWait();  
        }
    }
    
    
    /************* INICIO TRATAMENTO INTERFACE MENUS ************\ 
     */     
    
    //quando clicar em um menu item, voltar todas as outras imagens originais
    private void MenuOriginal(boolean status)
    {
        //vou mostrar todas os menus primários em suas devidas posições no layout
        //ainda restaurar imagens originais, esconder submenus
        
        imvCadastros.setVisible(status);
        imvMovimentacao.setVisible(status);
        imvRelatorios.setVisible(status);        //COLOQUE TODO ESSE CONJUNTO DE LINHAS EM COMENTÁRIO
        imvConfiguracoes.setVisible(status);     //DESCOMENTE O CONJUNTO ABAIXO PARA OBTER O MENU COMPLETO
        imvMenu.setVisible(false);
        /* 
        imvCadastros.setVisible(status);
        imvMovimentacao.setVisible(status);
        imvRelatorios.setVisible(status);        //TIRE OS COMENTÁRIOS PARA RESTAURAR O MENU COMPLETO
        imvConfiguracoes.setVisible(status);
        imvMenu.setVisible(!status);
        */
        
        //reposiciona menus na posição original
        imvCadastros.setLayoutX(9);        
        imvMovimentacao.setLayoutX(148);        
        imvRelatorios.setLayoutX(287);                
        imvConfiguracoes.setLayoutX(426); 
        
        //esconder todos os submenus
        apSubCadastros.setVisible(false);
        apSubMovimentacoes.setVisible(false);
        apSubConfiguracoes.setVisible(false);
        apSubRelatorios.setVisible(false);        
        // ...falta esconder os futuros submenus...
        
    }
    
    private void HabilitaMenu(ImageView menu)
    {
        MenuOriginal(false); //pra esconder todos menus
        menu.setVisible(true); //habilito apenas o menu desejado
        menu.setLayoutX(9); //reposiciono o menu à esquerda
        imvMenu.setVisible(true);
        
        if(menu.getId().contains("Cadastros")) //menu de cadastro
        {
            apSubCadastros.setVisible(true);
        }
        if(menu.getId().contains("Movimentacao")) 
        {
            apSubMovimentacoes.setVisible(true);
        }
        if(menu.getId().contains("Relatorios")) 
        {
            apSubRelatorios.setVisible(true);
        }
        if(menu.getId().contains("Configuracoes"))
        {
            apSubConfiguracoes.setVisible(true);
        }
        if(menu.getId().contains("Ajuda")) 
        {
            //
        } 
    }
    
    private void TelaOriginal(boolean status)
    {
        //tela padrão
        apPessoas.setVisible(status); 
        
        
        //demais telas
        apVeiculosTela.setVisible(!status);
        apDependentesTela.setVisible(!status);
        apEmpresas.setVisible(!status);
        apCaixa.setVisible(!status);
        apRecebimentos.setVisible(!status);
        apPagamentos.setVisible(!status);
        apContas.setVisible(!status);
        apContasReceber.setVisible(!status);
        apContasPagar.setVisible(!status);
        apEventos.setVisible(!status);
        apBackup.setVisible(!status);
        apRestauracao.setVisible(!status);
        apBancoDados.setVisible(!status);
        lbBancodeDados.setVisible(!status);
        imvDicaBancoDados.setVisible(!status);
        lbIP.setVisible(!status);
        // ...acrescentar futuras telas
        
    }     
    
    private void HabilitaTela(ImageView submenu) throws SQLException
    {
        TelaOriginal(true);
        apPessoas.setVisible(false);
        //identico o submenu escolhido e mostro sua respectiva tela
        if(submenu.getId().equals("imvSair"))
        {
            TelaLogin(true);
            return;
        }  
        
        if(submenu.getId().equals("imvBancoDados"))
        {
            apServidor.setVisible(true);
            apServidor.setLayoutX(0);
            apTitulo.setVisible(false);
            apBancoDados.setVisible(true);
            apBancoDados.setLayoutX(12);     
            apBancoDados.setLayoutY(159);
            lbBancodeDados.setLayoutX(12);     
            lbBancodeDados.setLayoutY(124);
            lbBancodeDados.setVisible(true);
            imvDicaBancoDados.setVisible(true);
            imvDicaBancoDados.setLayoutX(312);
            imvDicaBancoDados.setLayoutY(159);
            lbIP.setVisible(true);
            
            //Carregar Banco de Dados que está sendo utilizado no momento
            cbDriver.getSelectionModel().select(params.getDriver());
            tfBase.setText(params.getBase_dados());
            tfPorta.setText(params.getPorta());
            tfSenhaServidor.setText(params.getSenha());
            tfServidor.setText(params.getServidor());
            tfUsuarioServidor.setText(params.getUsuario());
            ckbServidorLocal.setSelected(tfServidor.getText().equals("localhost"));
            DetectaServidorLocal();
                
            
            cbDriver.requestFocus();
        }  
        
        if(submenu.getId().equals("imvEventos"))
        {
            apEventos.setVisible(true);
            EVENTOtxPesquisa.setText("");
            apDadosEvento.setDisable(true);
            apTabelaEventos.setDisable(false);
            
            EVENTObtAlterar.setDisable(true);
            EVENTObtApagar.setDisable(true);
            ctrRegistrarEvento.CarregarComboBox(EVENTOcbCategoria,  "");
            ctrRegistrarEvento.CarregarComboBox(EVENTOcbFiltrarCategoria,  "");
            
            ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
            estadoOriginalEventos();
            EVENTObtNovo.requestFocus();
        }
        
        if(submenu.getId().equals("imvRecebimentos"))
        {
            apRecebimentos.setVisible(true); 
            if(cbFiltrarParcelasReceber.getItems().size() == 0)
               ctrRealizarRecebimento.CarregarComboBox(cbFiltrarParcelasReceber, "FILTROS");
            estadoOriginalRecebimentos();
            Filtrar();
            cbFiltrarParcelasReceber.requestFocus();
        }
        
        if(submenu.getId().equals("imvPagamentos"))
        {
            apPagamentos.setVisible(true); 
            if(cbFiltrarParcelasPagar.getItems().size() == 0)
               ctrRealizarPagamento.CarregarComboBox(cbFiltrarParcelasPagar, "FILTROS");
            estadoOriginalPagamentos();
            FiltrarPagar();
            cbFiltrarParcelasPagar.requestFocus();
        }
        
        if(submenu.getId().equals("imvBackup"))
        {
            //estado original
            BACKUPbtSelecionar.setText("SALVAR");
            BACKUPbtSelecionar.getTooltip().setText("Salvar backup");
            BACKUPtfArquivo.setText("");
            
            apBackup.setVisible(true);           
        }
        
        if(submenu.getId().equals("imvRestauracao"))
        {
            //estado original
            RESTAURACAObtSelecionar.setText("SELECIONAR");
            RESTAURACAObtSelecionar.getTooltip().setText("Selecionar backup");
            RESTAURACAOtfArquivo.setText("");
            
            apRestauracao.setVisible(true);           
        }
        
        if(submenu.getId().equals("imvContas"))
        {
           apContas.setVisible(true);
        }
        
        if(submenu.getId().equals("imvContasPagar"))
        {
            ctrRealizarPagamento.CarregarComboBox(PAGAMENTOcbFormaPagamento, "FORMAS");
            
            apContasPagar.setVisible(true); 
            CONTAPAGARtxPesquisa.setText("");
            apDadosContaPagar.setDisable(true);
            apTabelaContasPagar.setDisable(false);
            
            CONTAPAGARbtAlterar.setDisable(true);
            CONTAPAGARbtApagar.setDisable(true);
            ctrLancarContaPagar.CarregarComboBox(CONTAPAGARcbForma, "FORMAS");
            
            ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
            estadoOriginalContaPagar();
            CONTAPAGARbtNovo.requestFocus();
        }
        
        if(submenu.getId().equals("imvContasReceber"))
        {
            ctrRealizarRecebimento.CarregarComboBox(RECEBIMENTOcbFormaPagamento, "FORMAS");
            
            apContasReceber.setVisible(true); 
            CONTARECEBERtxPesquisa.setText("");
            apDadosContaReceber.setDisable(true);
            apTabelaContasReceber.setDisable(false);
            
            CONTARECEBERbtAlterar.setDisable(true);
            CONTARECEBERbtApagar.setDisable(true);
            ctrLancarContaReceber.CarregarComboBox(CONTARECEBERcbForma, "FORMAS");
            
            ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
            estadoOriginalContaReceber();
            CONTARECEBERbtNovo.requestFocus();
        }
        
        if(submenu.getId().equals("imvCaixa")) //Caixa
        {            
            apCaixa.setVisible(true);
            ctrFluxoCaixa.Novo();
            CAIXAlbData.setVisible(false);
            CAIXAlbSaldo.setVisible(true);
            CAIXAlbSaldo.setText("Nenhum registro no Fluxo de Caixa.");
            CAIXAlbHoje.setVisible(false);
            CAIXAlbSituacao.setTextFill(Color.RED);
            if(!ctrFluxoCaixa.CarregarDados()) //se não carregar é porque não existe Caixa em Banco
                return;
            CAIXAlbData.setVisible(true);
            //CAIXAlbSaldo.setVisible(true);
            CAIXAlbHoje.setVisible(true);
            CAIXAlbData.setText("Data: "+Funcoes.DateToString(LocalDate.now()));
            CAIXAlbSaldo.setText("Saldo: R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getTotal());
            
            CAIXAlbSituacao.setText(ctrFluxoCaixa.getCtrcaixa().getCaixa().getStatus());
            
            if(ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto() && ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAtualizado())
            {
                CAIXAlbHoje.setVisible(true);
                CAIXAlbHoje.setText("Saldo do dia: ");
                if(Float.parseFloat(ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
                {
                    CAIXAlbHoje.setTextFill(Color.RED);
                    CAIXAlbHoje.setText(CAIXAlbHoje.getText()+"-");
                }
                else
                    CAIXAlbHoje.setTextFill(Color.GREEN);
                CAIXAlbHoje.setText(CAIXAlbHoje.getText()+"R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace("-", ""));
                CAIXAlbHoje.setText(CAIXAlbHoje.getText()+ " (Entrada: R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getEntrada()+ " - Retirada: R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaida()+")");
            }
            else
                CAIXAlbHoje.setVisible(false);
            
            if(ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
            {
                CAIXAlbSituacao.setText(CAIXAlbSituacao.getText()+" às "+ctrFluxoCaixa.getCtrcaixa().getCaixa().getHora_abriu());
                CAIXAlbSituacao.setTextFill(Color.GREEN);                
                CAIXAbtAbrir.setText("FECHAR");
            }
            else
            {
                CAIXAlbSituacao.setText(CAIXAlbSituacao.getText()+" às "+ctrFluxoCaixa.getCtrcaixa().getCaixa().getHora_fechou());
                CAIXAlbSituacao.setTextFill(Color.RED);
                CAIXAbtAbrir.setText("ABRIR");
            }
            CAIXAbtMovimentar.setDisable(CAIXAbtAbrir.getText().equals("ABRIR"));
            ctrFluxoCaixa.CarregarTabela(CAIXAtabelaSaida);
            ctrFluxoCaixa.CarregarTabela(CAIXAtabelaEntrada);
            CAIXAbtAbrir.requestFocus();
            
        } 
        if(submenu.getId().equals("imvPessoas"))  
        {          
            apPessoas.setVisible(true);
            PESSOAStxPesquisa.setText("");
            apDadosPessoas.setDisable(true);
            apTabelaPessoas.setDisable(false);
            
            PESSOASbtAlterar.setDisable(true);
            PESSOASbtApagar.setDisable(true);
            ctrGerenciarPessoas.CarregarComboBox(PESSOAScbFiltrarCategoria, "", "PESSOA");
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbFiltrarCategoria, "", "DEPENDENTE");
            ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbFiltrarCategoria, "categoriaveiculo", "");
            
            PESSOAStabela.setId("SUPERVISORES");
            ctrGerenciarPessoas.CarregarTabela(PESSOAStabela, PESSOAStxPesquisa.getText(), PESSOAScbFiltrarCategoria.getValue());
            
            ctrGerenciarPessoas.CarregarComboBox(PESSOAScbCategoria, "", "PESSOA");
            ctrGerenciarPessoas.CarregarComboBox(PESSOAScbUF, "estado", "");
            new ComboBoxAutoComplete<String>(PESSOAScbUF);
            ctrGerenciarPessoas.CarregarComboBox(PESSOAScbTipoLog, "", "TIPOLOGRADOURO");
            estadoOriginal();  
            PESSOASbtNovo.requestFocus();
        }
        if(submenu.getId().equals("imvDependentes"))  
        {
            apDependentesTela.setVisible(true);
            DEPENDENTEStxPesquisa.setText("");
            apDadosDependentes.setDisable(true);
            apTabelaDependentes.setDisable(false);
            
            DEPENDENTESbtAlterar.setDisable(true);
            DEPENDENTESbtApagar.setDisable(true);
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbFiltrarCategoria, "", "DEPENDENTE");
            ctrGerenciarPessoas.CarregarTabela(DEPENDENTEStabela, DEPENDENTEStxPesquisa.getText(), DEPENDENTEScbFiltrarCategoria.getValue());
            
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbCategoria, "", "DEPENDENTE");
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbUF, "estado", "");
            new ComboBoxAutoComplete<String>(DEPENDENTEScbUF);
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbTipoLog, "", "TIPOLOGRADOURO");
            estadoOriginalDependente();
            DEPENDENTESbtNovo.requestFocus();
        } 
        
        if(submenu.getId().equals("imvEmpresas"))  
        {
            apEmpresas.setVisible(true);
            EMPRESAStxPesquisa.setText("");
            apDadosEmpresas.setDisable(true);
            apTabelaEmpresas.setDisable(false);
            
            EMPRESASbtAlterar.setDisable(true);
            EMPRESASbtApagar.setDisable(true);
            ctrGerenciarEmpresas.CarregarTabelaEmpresas(EMPRESAStabela, EMPRESAStxPesquisa.getText());
            
            ctrGerenciarEmpresas.CarregarComboBox(EMPRESAScbUF, "estado", "");
            new ComboBoxAutoComplete<String>(EMPRESAScbUF);
            ctrGerenciarEmpresas.CarregarComboBox(EMPRESAScbTipoLog, "TIPOLOGRADOURO", "");
            estadoOriginalEmpresa();
            EMPRESASbtNovo.requestFocus();
        } 
        
        
        if(submenu.getId().equals("imvVeiculos"))  
        {
            apVeiculosTela.setVisible(true);
            VEICULOStxPesquisa.setText("");
            apDadosVeiculos.setDisable(true);
            apTabelaVeiculos.setDisable(false);
            
            VEICULOSbtAlterar.setDisable(true);
            VEICULOSbtApagar.setDisable(true);
            ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), "Exibir todos");
            ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCategoria, "categoriaveiculo", "");
            ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbFiltrarCategoria, "categoriaveiculo", "");
            ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCor, "cor", "");
            ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbMarca, "marca", "");
            estadoOriginalVeiculo();
            VEICULOSbtNovo.requestFocus();
        } 
        if(submenu.getId().equals("imvEventosM"))  
        {
            apEventos.setVisible(true);
        } 
        
        // ...acrescentar futuras telas
    }    
    
    //CLICKS BOTÕES DE MENU e SUBMENUS
    /////////////////////////////////////////
    @FXML
    private void clickMenuRelatorio(MouseEvent event) {        
        HabilitaMenu(imvRelatorios);
        java.net.URL url = getClass().getResource("botoes/botao_relatorio2.png");
        imvRelatorios.setImage(new Image(url.toString()));
    }
    @FXML
    private void clickMenuCadastros(MouseEvent event) {        
        HabilitaMenu(imvCadastros);
        java.net.URL url = getClass().getResource("botoes/botao_cadastros2.png");
        imvCadastros.setImage(new Image(url.toString()));
    }
    @FXML
    private void clickMenuMovimentacao(MouseEvent event) {        
        HabilitaMenu(imvMovimentacao);
        java.net.URL url = getClass().getResource("botoes/botao_movimentacoes2.png");
        imvMovimentacao.setImage(new Image(url.toString()));
    }
    @FXML
    private void clickMenuConfiguracoes(MouseEvent event) {        
        HabilitaMenu(imvConfiguracoes);
        java.net.URL url = getClass().getResource("botoes/botao_configuracoes2.png");
        imvConfiguracoes.setImage(new Image(url.toString()));
    }
    
    
    
    
    //CLICKS SUBMENUS ITEMS
    ///////////////////////////
    @FXML
    private void onMouseExitVeiculos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subVeiculos.png");
        imvVeiculos.setImage(new Image(url.toString()));
        imvRelVeiculos.setImage(new Image(url.toString()));
        
    }


    @FXML
    private void OnMouseOverVeiculos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subVeiculos2.png");
        imvVeiculos.setImage(new Image(url.toString()));
        imvRelVeiculos.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void clickMenuVeiculos(MouseEvent event) throws SQLException {
        HabilitaTela(imvVeiculos);
    }
    
    @FXML
    private void onMouseExitContas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subContas.png");
        imvContas.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuContas(MouseEvent event) throws SQLException {
        imvContas.setId("imvContas");
        HabilitaTela(imvContas);
    }

    @FXML
    private void OnMouseOverContas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subContas2.png");
        imvContas.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void onMouseExitRecebimentos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subRecebimentos.png");
        imvRecebimentos.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuRecebimentos(MouseEvent event) throws SQLException {
        HabilitaTela(imvRecebimentos);
    }

    @FXML
    private void OnMouseOverRecebimentos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subRecebimentos2.png");
        imvRecebimentos.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void clickMenuPessoas(MouseEvent event) throws SQLException {
        HabilitaTela(imvPessoas);
    }
    
    
    
    @FXML
    private void clickMenuDependentes(MouseEvent event) throws SQLException {
        HabilitaTela(imvDependentes);
    }
    
    @FXML
    private void clickMenuEventos(MouseEvent event) throws SQLException {
        HabilitaTela(imvEventos);
    }
    

    @FXML
    private void clickMenu(MouseEvent event) {
        MenuOriginal(true);
    }

    @FXML
    private void onMouseExitMenu(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/menu.png");
        imvMenu.setImage(new Image(url.toString()));  
    }
    
    @FXML
    private void OnMouseOverMenu(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/menu2.png");
        imvMenu.setImage(new Image(url.toString()));        
    }    

    @FXML
    private void onMouseExitCadastros(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_cadastros.png");
        imvCadastros.setImage(new Image(url.toString()));         
    }

    @FXML
    private void OnMouseOverCadastros(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_cadastros2.png");
        imvCadastros.setImage(new Image(url.toString()));        
    }

    @FXML
    private void onMouseExitMovimentacao(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_movimentacoes.png");
        imvMovimentacao.setImage(new Image(url.toString())); 
    }

    @FXML
    private void OnMouseOverMovimentacao(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_movimentacoes2.png");
        imvMovimentacao.setImage(new Image(url.toString())); 
    }

    @FXML
    private void onMouseExitRelatorios(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_relatorio.png");
        imvRelatorios.setImage(new Image(url.toString())); 
    }

    @FXML
    private void OnMouseOverRelatorios(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_relatorio2.png");
        imvRelatorios.setImage(new Image(url.toString())); 
    }
    
    @FXML
    private void onMouseExitRelMovimentacoes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subMovimentacoes.png");
        imvRelMovimentacoes.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickMenuRelMovimentacoes(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        stage.setTitle("Emitir Relatório de Movimentações");
        TelaRelatorioMovimentacoes = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioMovimentacoes.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
    }

    @FXML
    private void OnMouseOverRelMovimentacoes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subMovimentacoes2.png");
        imvRelMovimentacoes.setImage(new Image(url.toString())); 
    }

    @FXML
    private void onMouseExitConfiguracoes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_configuracoes.png");
        imvConfiguracoes.setImage(new Image(url.toString())); 
    }

    @FXML
    private void OnMouseOverConfiguracoes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_configuracoes2.png");
        imvConfiguracoes.setImage(new Image(url.toString())); 
    }

    private void onMouseExitAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_ajuda.png");
        imvAjuda.setImage(new Image(url.toString())); 
    }

    private void OnMouseOverAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/botao_ajuda2.png");
        imvAjuda.setImage(new Image(url.toString())); 
    }

    @FXML
    private void onMouseExitEventos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEventos.png");
        imvEventos.setImage(new Image(url.toString()));
        imvRelEventos.setImage(new Image(url.toString())); 
    }

    @FXML
    private void OnMouseOverEventos(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEventos2.png");
        imvEventos.setImage(new Image(url.toString())); 
        imvRelEventos.setImage(new Image(url.toString())); 
    }
    
    @FXML
    private void onMouseExitCaixa(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subCaixa.png");
        imvCaixa.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuCaixa(MouseEvent event) throws SQLException {
        HabilitaTela(imvCaixa);
    }

    @FXML
    private void OnMouseOverCaixa(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subCaixa2.png");
        imvCaixa.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void onMouseExitItemContasaPagar(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_lancarcontapagar.png");
        imvContasaPagar.setImage(new Image(url.toString()));
    }

    @FXML
    private void OnMouseOverItemContasaPagar(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_lancarcontapagar2.png");
        imvContasaPagar.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void onMouseExitItemContasaReceber(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_lancarcontareceber.png");
        imvContasaReceber.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void OnMouseOverItemContasaReceber(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_lancarcontareceber2.png");
        imvContasaReceber.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void onMouseExitItemRealizarPagamento(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_pagamento.png");
        imvRealizarPagamento.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void OnMouseOverItemRealizarPagamento(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_pagamento2.png");
        imvRealizarPagamento.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void onMouseExitItemRealizarRecebimento(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_recebimento.png");
        imvRealizarRecebimento.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void OnMouseOverItemRealizarRecebimento(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/itens_menu/item_recebimento2.png");
        imvRealizarRecebimento.setImage(new Image(url.toString()));
    }
    
    private void onMouseExitEventosM(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEventos.png");
        imvEventosM.setImage(new Image(url.toString()));
    }

    
    private void OnMouseOverEventosM(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEventos2.png");
        imvEventosM.setImage(new Image(url.toString())); 
    }
    
    @FXML
    private void onMouseExitIconeErro(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/icone-erro.png");
        iconeerro.setImage(new Image(url.toString()));
        url = getClass().getResource("icones/msg-erro.png");
        msgerro.setImage(new Image(url.toString()));
    }

    @FXML
    private void OnMouseOverIconeErro(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/icone-erro2.png");
        iconeerro.setImage(new Image(url.toString()));
        url = getClass().getResource("icones/msg-erro2.png");
        msgerro.setImage(new Image(url.toString()));
    }

    @FXML
    private void onMouseExitMsgErro(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/msg-erro.png");
        msgerro.setImage(new Image(url.toString()));
        url = getClass().getResource("icones/icone-erro.png");
        iconeerro.setImage(new Image(url.toString()));
    }

    @FXML
    private void OnMouseOverMsgErro(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/msg-erro2.png");
        msgerro.setImage(new Image(url.toString()));
        url = getClass().getResource("icones/icone-erro2.png");
        iconeerro.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void clickRelatorioErros(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Relatório de Erros");
        alert.setHeaderText("O que deu errado ?");
        alert.setContentText(erros);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
        alert.showAndWait();
    }
    
    @FXML
    private void onMouseExitEmpresas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEmpresas.png");
        imvEmpresas.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuEmpresas(MouseEvent event) throws SQLException {
        HabilitaTela(imvEmpresas);
    }

    @FXML
    private void OnMouseOverEmpresas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subEmpresas2.png");
        imvEmpresas.setImage(new Image(url.toString()));
    }

    
    @FXML
    private void onMouseExitPessoas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subPessoas.png");
        imvPessoas.setImage(new Image(url.toString()));
        imvRelPessoas.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void OnMouseOverPessoas(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subPessoas2.png");
        imvPessoas.setImage(new Image(url.toString()));
        imvRelPessoas.setImage(new Image(url.toString()));
    } 
    
    @FXML
    private void onMouseExitDependentes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subDependentes.png");
        imvDependentes.setImage(new Image(url.toString()));
    }


    @FXML
    private void OnMouseOverDependentes(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subDependentes2.png");
        imvDependentes.setImage(new Image(url.toString()));
    }
    /************* FIM TRATAMENTO INTERFACE MENUS ************\ 
     */
    

    //EVENTOS DE COMPONENTES
    //......................
    
    /************* EVENTOS TELA CADASTRO DE PESSOAS *****************\
     */
    private void ExibirCamposPorCategoria(String categoria)
    {
        PESSOASlbData.setVisible(true);
        ajudaPESSOASDataNasc.setVisible(true);
        ajudaPESSOASdependente.setVisible(false);
        ajudaPESSOASlote.setVisible(false);
        ajudaPESSOASveiculo.setVisible(false);
        ObservableList <Node> componentes = aux_dadospessoas.getChildren();
        PESSOASlbData.setTextFill(Color.BLACK);
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setVisible(true);
            if(n instanceof ComboBox)
                ((ComboBox)n).setVisible(true);
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(true);
            }
            if(n instanceof DatePicker)
                ((DatePicker)n).setVisible(true);
            if(n instanceof TableView)
                ((TableView)n).setVisible(true);
            if(n instanceof Button)
                ((Button)n).setVisible(true);
            if(n instanceof AnchorPane)
                ((AnchorPane)n).setVisible(true);
            
        }
        
        tabDadosUsuario.setDisable(false);   
        if(categoria.equals("Funcionário"))
        {
            if(!PESSOASbtConfirmar.getTooltip().getText().contains("Alterar") && !PESSOASrbNaoAutorizado.isSelected())
               PESSOASrbLivreAcesso.setSelected(true);
            
            ajudaPESSOASveiculo.setVisible(true);
            PESSOASlbVeiculos.setVisible(true);
            if(TabPane.getTabs().size() == 1)
              TabPane.getTabs().add(tabDadosUsuario);
            PESSOASlbInterfone.setVisible(false);
            PESSOAStfInterfone.setVisible(false);
            
            PESSOASlbDependentes.setVisible(false);
            PESSOAStabelaDependentes.setVisible(false);
            PESSOASbtAdicionarDependente.setVisible(false);
            PESSOASbtAlterarDependente.setVisible(false);
            PESSOASbtExcluirDependente.setVisible(false);
            
            PESSOASlbLotes.setVisible(false);
            PESSOAStabelaLotes.setVisible(false);
            PESSOASbtAdicionarLote.setVisible(false);
            PESSOASbtAlterarLote.setVisible(false);
            PESSOASbtExcluirLote.setVisible(false);
            aux_dadospessoas.setMinHeight(820);
            TabPane.setMinHeight(1000);
            
        }
        
        if(categoria.equals("Associado"))
        {
            if(!PESSOASbtConfirmar.getTooltip().getText().contains("Alterar") && !PESSOASrbNaoAutorizado.isSelected())
               PESSOASrbLivreAcesso.setSelected(true);
            ajudaPESSOASveiculo.setVisible(true);
            PESSOASlbVeiculos.setVisible(true);
            ajudaPESSOASdependente.setVisible(true);
            PESSOASlbDependentes.setVisible(true);
            ajudaPESSOASlote.setVisible(true);
            PESSOASlbLotes.setVisible(true);
            if(TabPane.getTabs().size() == 1)
              TabPane.getTabs().add(tabDadosUsuario);
           PESSOASapDadosFuncionario.setVisible(false);
           aux_dadospessoas.setMinHeight(1188);
           TabPane.setMinHeight(1213);
        }
        
        
        if(categoria.equals("Visitante"))
        {
            if(!PESSOASbtConfirmar.getTooltip().getText().contains("Alterar") && !PESSOASrbNaoAutorizado.isSelected())
               PESSOASrbMedianteAutorizacao.setSelected(true);
            ajudaPESSOASveiculo.setVisible(true);
            PESSOASlbVeiculos.setVisible(true);
            ajudaPESSOASdependente.setVisible(true);
            PESSOASlbDependentes.setVisible(true);
            TabPane.getTabs().remove(tabDadosUsuario);
            PESSOASapDadosFuncionario.setVisible(false);
            PESSOASlbInterfone.setVisible(false);
            PESSOAStfInterfone.setVisible(false);
            
            PESSOASlbLotes.setVisible(false);
            PESSOAStabelaLotes.setVisible(false);
            PESSOASbtAdicionarLote.setVisible(false);
            PESSOASbtAlterarLote.setVisible(false);
            PESSOASbtExcluirLote.setVisible(false);
            aux_dadospessoas.setMinHeight(1003);
            TabPane.setMinHeight(1028);
        }
        
        PESSOAStxPesquisarCPF.setVisible(false);
    }
    
    private void estadoOriginal()
    {  
        PESSOASlbCamposObrigatorios.setVisible(false);
        PESSOAScbCidade.setPromptText("Selecione o UF");
        PESSOASlbCategoria.setLayoutY(10);
        PESSOAScbCategoria.setLayoutY(30);
        PESSOASlbCategoria.setLayoutX(21);
        PESSOAScbCategoria.setLayoutX(21);
        
        PESSOASrbLivreAcesso.setSelected(false);
        PESSOASrbMedianteAutorizacao.setSelected(false);
        PESSOASrbNaoAutorizado.setSelected(false);
        PESSOASrbEntrada.setSelected(true);
        
        PESSOASlbData.setTextFill(Color.BLACK);
        PESSOASlbLotes.setTextFill(Color.BLACK);
        
        iconeerro.setVisible(false);
        msgerro.setVisible(false);
        
        estadoOriginalTelaAux();
        apTabelaPessoas.setVisible(true);
        apVeiculos.setVisible(false); 
        apLotes.setVisible(false);
        apDependentes.setVisible(false);
        aux_dadospessoas.setMinHeight(423);
        TabPane.setMinHeight(423);
        scrollDadosPessoas.setVvalue(0);
        apTabelaPessoas.setDisable(false);
        apDadosPessoas.setDisable(true);
        PESSOASbtConfirmar.setDisable(true);
        PESSOASbtCancelar.setDisable(true);
        PESSOASbtApagar.setDisable(true);
        PESSOASbtAlterar.setDisable(true);
        PESSOASbtNovo.setDisable(false);
        PESSOASdpData.setDisable(false);
        PESSOASdpData.setValue(null);
        //mudando para a guia "Dados Pessoais"
        SingleSelectionModel<Tab> selectionModel = TabPane.getSelectionModel();
        selectionModel.select(0);
        TabPane.getTabs().remove(tabDadosUsuario);
        PESSOASckbCondutor.setSelected(false);
        USUARIOtfNome.setText("");
        USUARIOtfSenha.setText("");
        USUARIOrbBasico.setSelected(true);
        USUARIOlbNome.setTextFill(Color.BLACK);
        USUARIOlbSenha.setTextFill(Color.BLACK);
        USUARIOlbNivel.setTextFill(Color.BLACK);
        PESSOASlbData.setVisible(false);
        ajudaPESSOASDataNasc.setVisible(false);
        ajudaPESSOASdependente.setVisible(false);
        ajudaPESSOASlote.setVisible(false);
        ajudaPESSOASveiculo.setVisible(false);
        
        //---------------Limpa Os Textos da Tela---------------------------
        PESSOAScbUF.getSelectionModel().clearSelection();
        PESSOAScbCidade.getSelectionModel().clearSelection();
        ObservableList <Node> componentes = PESSOASapDadosFuncionario.getChildren();
        for(Node n : componentes)
        {
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
                ((DatePicker)n).setValue(null);
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");            
        }
        
        componentes = aux_dadospessoas.getChildren();
      
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setDisable(false);
                ((TextInputControl)n).setVisible(false);
            }
                     
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setDisable(false);
                ((ComboBox)n).setVisible(false);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(false);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setDisable(false);
                ((DatePicker)n).setVisible(false);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setDisable(false);
                ((TableView)n).setVisible(false);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setDisable(false);
            if(n instanceof CheckBox)
                ((CheckBox)n).setDisable(false);
            if(n instanceof Button && !n.equals(PESSOASbtConfirmar) && !n.equals(PESSOASbtCancelar))
            {
                ((Button)n).setDisable(false);
                ((Button)n).setVisible(false);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(false);
            }   
        }
        ajudaPESSOASdependente.setVisible(false);
        ajudaPESSOASveiculo.setVisible(false);
        ajudaPESSOASlote.setVisible(false);
        //Campo foto
        PESSOASbtnCamera.setDisable(false);
        PESSOASbtApagarIMG.setDisable(false);
        PESSOASbtCarregarIMG.setDisable(false);
        PESSOASapDadosFuncionario.setDisable(false);
        
        
        USUARIOrbAdministrador.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1);
        USUARIOrbBasico.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1);
        
        USUARIOtfNome.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());
        USUARIOtfSenha.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());
        PESSOASbtVerSenha.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());        
        
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        
        java.net.URL url = getClass().getResource("imagens/usuario.png");
        PESSOASimg.setImage(new Image(url.toString()));
        PESSOASimg.setId("1");      
        PESSOAStabela.setId("SUPERVISORES");
        ctrGerenciarPessoas.CarregarTabela(PESSOAStabela, PESSOAStxPesquisa.getText(), PESSOAScbFiltrarCategoria.getValue());
        PESSOASlbVerSenha.setTextFill(Color.RED);
        PESSOASlbVerSenha.setVisible(false);
        apPessoas.requestFocus();
        PESSOASModoExibicao(false);
        PESSOASbtAlterarDependente.setDisable(true);
        PESSOASbtAlterarLote.setDisable(true);
        PESSOASbtAlterarVeiculo.setDisable(true);
        PESSOASbtExcluirDependente.setDisable(true);
        PESSOASbtExcluirLote.setDisable(true);
        PESSOASbtExcluirVeiculo.setDisable(true);
    }
    
    private void estadoOriginalPagamentos()
    {
        tfPessoaEmpresa.setEditable(false);
        tfPessoaEmpresa.setFocusTraversable(false);
        tfPessoaEmpresa.setMouseTransparent(true);        
        tfPessoaEmpresa.setDisable(false);
        tfPessoaEmpresa.setText("");
        ckbFiltrarPorPessoaEmpresa.setSelected(false);
        ckbFiltrarPorPessoaEmpresa.setDisable(true);
        cbFiltrarParcelasPagar.getSelectionModel().select(3);
        ckbFiltrarPeriodoPagar.setSelected(false);
        ckbFiltrarPeriodoPagar.setTextFill(Color.BLACK);
        dpDataInicialPagar.setValue(null);
        dpDataFinalPagar.setValue(null);
        dpDataInicialPagar.setDisable(true);
        dpDataFinalPagar.setDisable(true);
        tabelaParcelasPagar.getSelectionModel().clearSelection();
        btEstornarPagamento.setDisable(true);
        btPagar.setDisable(true);
        btVerPagamento.setDisable(true);
    }
    
    private void estadoOriginalRecebimentos()
    {
        tfPessoa.setEditable(false);
        tfPessoa.setFocusTraversable(false);
        tfPessoa.setMouseTransparent(true);        
        tfPessoa.setDisable(false);
        tfPessoa.setText("");
        ckbFiltrarPorPessoa.setSelected(false);
        ckbFiltrarPorPessoa.setDisable(true);
        cbFiltrarParcelasReceber.getSelectionModel().select(3);
        ckbFiltrarPeriodoReceber.setSelected(false);
        ckbFiltrarPeriodoReceber.setTextFill(Color.BLACK);
        dpDataInicial.setValue(null);
        dpDataFinal.setValue(null);
        dpDataInicial.setDisable(true);
        dpDataFinal.setDisable(true);
        tabelaParcelasReceber.getSelectionModel().clearSelection();
        btEstornarRecebimento.setDisable(true);
        btReceber.setDisable(true);
        btVer.setDisable(true);
    }
    
    private void estadoOriginalEventos()
    {  
        EVENTOlbCamposObrigatorios.setVisible(false);
        EVENTObtConfigurar.setText("DEFINIR");
        PARTICIPANTEbtSelecionar.getTooltip().setText("Selecionar participante");
        EVENTOlbPeriodo.setTextFill(Color.BLACK);
        EVENTOlbPessoa.setTextFill(Color.BLACK);
        
        iconeerro.setVisible(false);
        msgerro.setVisible(false);        
        
        apTabelaEventos.setVisible(true);
        apParticipantes.setVisible(false); 
        PARTICIPANTEapAdicionar.setVisible(false);
        PARTICIPANTEapTabela.setVisible(true);        
        apTabelaEventos.setDisable(false);
        apDadosEvento.setDisable(true);
        EVENTObtConfirmar.setDisable(true);
        EVENTObtCancelar.setDisable(true);
        EVENTObtApagar.setDisable(true);
        EVENTObtAlterar.setDisable(true);
        EVENTObtNovo.setDisable(false);
        EVENTOdpDataInicial.setDisable(false);
        EVENTOdpDataInicial.setValue(null);
        EVENTOdpDataFinal.setDisable(false);
        EVENTOdpDataFinal.setValue(null);
        EVENTOrbAndamento.setSelected(true);
        
        /*ajudaPESSOASDataNasc.setVisible(false);
        ajudaPESSOASdependente.setVisible(false);
        ajudaPESSOASlote.setVisible(false);
        ajudaPESSOASveiculo.setVisible(false);*/
        
        //---------------Limpa Os Textos da Tela---------------------------
        
        ObservableList <Node> componentes = aux_dadosevento.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setDisable(false);
                ((TextInputControl)n).setText("");
            }
                     
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setDisable(false);
                ((ComboBox)n).getSelectionModel().clearSelection();
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setDisable(false);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setDisable(false);
                ((TableView)n).getItems().clear();
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setDisable(false);
            if(n instanceof CheckBox)
                ((CheckBox)n).setDisable(false);
            if(n instanceof Button && !n.equals(EVENTObtConfirmar) && !n.equals(EVENTObtCancelar))
            {
                ((Button)n).setDisable(false);
            }
        }
        /*ajudaPESSOASdependente.setVisible(false);
        ajudaPESSOASveiculo.setVisible(false);
        ajudaPESSOASlote.setVisible(false);*/
        
        
        ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
        PARTICIPANTEtabela.getItems().clear();
        
        apEventos.requestFocus();
        EVENTOSModoExibicao(false);
        
    }
    
    private void estadoOriginalDependente()
    {   
        DEPENDENTESlbCamposObrigatorios.setVisible(false);
        DEPENDENTEScbCidade.setPromptText("Selecione o UF");
        DEPENDENTESrbLivreAcesso.setSelected(false);
        DEPENDENTESrbMedianteAutorizacao.setSelected(false);
        DEPENDENTESrbNaoAutorizado.setSelected(false);
        DEPENDENTESlbCategoria.setLayoutY(10);
        DEPENDENTEScbCategoria.setLayoutY(30);
        DEPENDENTESlbCategoria.setLayoutX(21);
        DEPENDENTEScbCategoria.setLayoutX(21);
        DEPENDENTESTelalbDescricao.setTextFill(Color.BLACK);
        DEPENDENTESTelalbDescricao.setVisible(false);
        ajudaDEPENDENTESDescricao.setVisible(false);
        DEPENDENTESrbEntrada.setSelected(true);
        aux_dadosdependentes.setMinHeight(423);
        scrollDadosDependentes.setVvalue(0);
        apTabelaDependentes.setDisable(false);
        apDadosDependentes.setDisable(true);
        DEPENDENTESTelabtConfirmar.setDisable(true);
        DEPENDENTESTelabtCancelar.setDisable(true);
        DEPENDENTESbtApagar.setDisable(true);
        DEPENDENTESbtAlterar.setDisable(true);
        DEPENDENTESbtNovo.setDisable(false);
        DEPENDENTESdpData.setDisable(false);
        DEPENDENTESdpData.setValue(null);        
        DEPENDENTESckbCondutor.setSelected(false);
        
        //---------------Limpa Os Textos da Tela---------------------------
        DEPENDENTEScbUF.getSelectionModel().clearSelection();
        DEPENDENTEScbCidade.getSelectionModel().clearSelection();      
        
        ObservableList <Node> componentes = aux_dadosdependentes.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setDisable(false);
                ((TextInputControl)n).setVisible(false);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setDisable(false);
                ((ComboBox)n).setVisible(false);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(false);
            }
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setDisable(false);
                ((DatePicker)n).setVisible(false);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setDisable(false);
                ((TableView)n).setVisible(false);
            }
            if(n instanceof RadioButton)
                ((RadioButton)n).setDisable(false);
            if(n instanceof CheckBox)
                ((CheckBox)n).setDisable(false);
            if(n instanceof Button && !n.equals(DEPENDENTESTelabtConfirmar) && !n.equals(DEPENDENTESTelabtCancelar))
            {
                ((Button)n).setDisable(false);
                ((Button)n).setVisible(false);
            }
            if(n instanceof AnchorPane)
            {
                ((AnchorPane)n).setVisible(false);
            }   
        }
        //Campo foto
        DEPENDENTESbtnCamera.setDisable(false);
        DEPENDENTESbtApagarIMG.setDisable(false);
        DEPENDENTESbtCarregarIMG.setDisable(false);      
               
        
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        java.net.URL url = getClass().getResource("imagens/usuario.png");
        DEPENDENTESimg.setImage(new Image(url.toString()));
        DEPENDENTESimg.setId("1");      
        
        ctrGerenciarPessoas.CarregarTabela(DEPENDENTEStabela, DEPENDENTEStxPesquisa.getText(), DEPENDENTEScbFiltrarCategoria.getValue());
        apDependentesTela.requestFocus();        
        DEPENDENTESModoExibicao(false);
    }
    
    private void estadoOriginalEmpresa()
    {       
        EMPRESASlbCamposObrigatorios.setVisible(false);
        EMPRESAScbCidade.setPromptText("Selecione o UF");
        scrollDadosEmpresas.setVvalue(0);
        apTabelaEmpresas.setDisable(false);
        apDadosEmpresas.setDisable(true);
        EMPRESASbtConfirmar.setDisable(true);
        EMPRESASbtCancelar.setDisable(true);
        EMPRESASbtApagar.setDisable(true);
        EMPRESASbtAlterar.setDisable(true);
        EMPRESASbtNovo.setDisable(false);         
        
        //---------------Limpa Os Textos da Tela---------------------------
        EMPRESAScbUF.getSelectionModel().clearSelection();
        EMPRESAScbCidade.getSelectionModel().clearSelection();      
        
        //Campo foto
        EMPRESASbtnCamera.setDisable(false);
        EMPRESASbtApagarIMG.setDisable(false);
        EMPRESASbtCarregarIMG.setDisable(false);      
               
        ObservableList <Node> componentes = aux_dadosempresas.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setText("");
                ((TextInputControl)n).setDisable(false);
            }
            
            if(n instanceof ComboBox)
            {
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
                ((ComboBox)n).setDisable(false);
            }
            
            if(n instanceof Button)
               ((Button)n).setDisable(false); 
            
            if(n instanceof TableView)
            {
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
                ((TableView)n).setDisable(false);
            }
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        java.net.URL url = getClass().getResource("imagens/logotipo.png");
        EMPRESASimg.setImage(new Image(url.toString()));
        EMPRESASimg.setId("1");      
        
        ctrGerenciarEmpresas.CarregarTabelaEmpresas(EMPRESAStabela, EMPRESAStxPesquisa.getText());
        EMPRESASModoExibicao(false);
        apEmpresas.requestFocus();
    }
    
    private void estadoOriginalTelaAux()
    {
        VEICULOScbCategoria.getSelectionModel().clearSelection();
        VEICULOScbMarca.getSelectionModel().clearSelection();
        VEICULOScbCor.getSelectionModel().clearSelection();
        VEICULOStfModelo.setText("");
        VEICULOStfPlaca.setText("");
        DEPENDENTEStfNome.setText("");
        DEPENDENTEStfDescricao.setText("");
        LOTEStfNumero.setText("");
        LOTEStfQuadra.setText("");
        LOTEStfDescricao.setText("");
        
        VEICULOSlbCategoria.setTextFill(Color.BLACK);
        VEICULOSlbMarca.setTextFill(Color.BLACK);
        VEICULOSlbCor.setTextFill(Color.BLACK);
        VEICULOSlbModelo.setTextFill(Color.BLACK);
        VEICULOSlbPlaca.setTextFill(Color.BLACK);
        DEPENDENTESlbNome.setTextFill(Color.BLACK);
        DEPENDENTESlbDescricao.setTextFill(Color.BLACK);
        LOTESlbNumero.setTextFill(Color.BLACK);
        LOTESlbQuadra.setTextFill(Color.BLACK);
        LOTESlbDescricao.setTextFill(Color.BLACK);
    }
    
    private void estadoOriginalContaPagar()
    {   
        CONTAPAGARlbCamposObrigatorios.setVisible(false);
        CONTAPAGARlbSaldo.setText("");
        CONTAPAGARlbDataVencimento.setText("Data de vencimento:");
        CONTAPAGARtfCod.setText("");
        ExibirTelaFormasPagamento(false);
        ctrLancarContaPagar.CarregarComboBox(CONTAPAGARcbForma, "FORMAS");
        apTabelaContasPagar.setDisable(false);
        apTabelaContasPagar.setVisible(true);
        apDadosContaPagar.setDisable(true);
        CONTAPAGARbtConfirmar.setDisable(true);
        CONTAPAGARbtCancelar.setDisable(true);
        CONTAPAGARbtApagar.setDisable(true);
        CONTAPAGARbtAlterar.setDisable(true);
        CONTAPAGARbtNovo.setDisable(false);
        CONTAPAGARdpDataVencimento.setDisable(true);
        CONTAPAGARdpDataVencimento.setValue(null); 
        CONTAPAGARbtConfigurar.setDisable(true);
        
        //---------------Limpa Os Textos da Tela---------------------------
        CONTAPAGARcbForma.getSelectionModel().clearSelection();      
        
        ObservableList <Node> componentes = aux_dadoscontapagar.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        
        componentes = apParcelasPagaraux.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        PARCELASPAGARdpDataInicio.setValue(null);
        PARCELASPAGARtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
        apParcelasPagar.setVisible(false);
        apContasPagar.requestFocus();
        CONTAPAGARModoExibicao(false);
    }
    
    private void estadoOriginalContaReceber()
    {   
        CONTARECEBERlbCamposObrigatorios.setVisible(false);
        CONTARECEBERlbDataVencimento.setText("Data de vencimento:");
        CONTARECEBERtfCod.setText("");
        ExibirTelaFormasRecebimento(false);
        ctrLancarContaReceber.CarregarComboBox(CONTARECEBERcbForma, "FORMAS");
        apTabelaContasReceber.setDisable(false);
        apTabelaContasReceber.setVisible(true);
        apDadosContaReceber.setDisable(true);
        CONTARECEBERbtConfirmar.setDisable(true);
        CONTARECEBERbtCancelar.setDisable(true);
        CONTARECEBERbtApagar.setDisable(true);
        CONTARECEBERbtAlterar.setDisable(true);
        CONTARECEBERbtNovo.setDisable(false);
        CONTARECEBERdpDataVencimento.setDisable(true);
        CONTARECEBERdpDataVencimento.setValue(null); 
        CONTARECEBERbtConfigurar.setDisable(true);
        
        //---------------Limpa Os Textos da Tela---------------------------
        CONTARECEBERcbForma.getSelectionModel().clearSelection();      
        
        ObservableList <Node> componentes = aux_dadoscontareceber.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        
        componentes = apParcelasReceberaux.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setText("");
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).getSelectionModel().clearSelection();
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).getItems().clear();
            
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
        }
        PARCELASRECEBERdpDataInicio.setValue(null);
        PARCELASRECEBERtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
        apParcelasReceber.setVisible(false);
        apContasReceber.requestFocus();
        CONTARECEBERModoExibicao(false);
    }
    
    private void CarregarDados()
    {
        PESSOASlbCategoria.setLayoutX(257);
        PESSOAScbCategoria.setLayoutX(257);
        PESSOASlbCategoria.setLayoutY(183);
        PESSOAScbCategoria.setLayoutY(203);
        
        
        if(PESSOAStabela.getSelectionModel().getSelectedItem().getUltimamov().equals("E"))
            PESSOASrbEntrada.setSelected(true);
        else
            PESSOASrbSaida.setSelected(true);
        PESSOAStfCod.setText(PESSOAStabela.getSelectionModel().getSelectedItem().getCod() + "");
        PESSOAStfBairro.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getBairro());
        PESSOAStfCEP.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getCep());
        PESSOAStfCPF.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getCpf() + "");
        PESSOAStfEmail.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getEmail());
        PESSOAStfLogradouro.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getLogradouro());
        PESSOAStfNome.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getNome());
        PESSOAStfNum.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getNumero());
        PESSOAStfTelfone.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getFone());
        PESSOAStfTelfone2.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getFone2());        
        PESSOASdpData.setValue((PESSOAStabela.getSelectionModel().getSelectedItem()).getDataNasc());
        PESSOASckbCondutor.setSelected(PESSOAStabela.getSelectionModel().getSelectedItem().getCondutor().equals("Sim"));
        
        
        //PESSOAStaHistorico.setText((PESSOAStabela.getSelectionModel().getSelectedItem()).getHistorico());
        
        if(PESSOAStabela.getSelectionModel().getSelectedItem() instanceof Associado)            
           PESSOAStfInterfone.setText(((Associado)PESSOAStabela.getSelectionModel().getSelectedItem()).getInterfone());
        if(PESSOAStabela.getSelectionModel().getSelectedItem() instanceof Funcionario)
        {
            if(!((Funcionario)PESSOAStabela.getSelectionModel().getSelectedItem()).getSalario().equals("0,00"))
               PESSOAStfSalario.setText(((Funcionario)PESSOAStabela.getSelectionModel().getSelectedItem()).getSalario());
            PESSOASdpDataAdmissao.setValue(((Funcionario)PESSOAStabela.getSelectionModel().getSelectedItem()).getData_admissao());
            PESSOASdpDataDemissao.setValue(((Funcionario)PESSOAStabela.getSelectionModel().getSelectedItem()).getData_demissao());
        }
        
        if(PESSOAStabela.getSelectionModel().getSelectedItem() instanceof Visitante == false && PESSOAStabela.getSelectionModel().getSelectedItem() instanceof Dependente == false) //não é visitante, nem dependente
        {
            USUARIOtfNome.setText(PESSOAStabela.getSelectionModel().getSelectedItem().getUsuario());
            USUARIOtfSenha.setText(PESSOAStabela.getSelectionModel().getSelectedItem().getSenha());
            if(PESSOAStabela.getSelectionModel().getSelectedItem().getNivel() == 1)
               USUARIOrbAdministrador.setSelected(true);
            else
               USUARIOrbBasico.setSelected(true);
        }
        
        USUARIOtfNome.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());
        USUARIOtfSenha.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());
        PESSOASbtVerSenha.setDisable(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected());
         
        PESSOAScbCategoria.getSelectionModel().select((PESSOAStabela.getSelectionModel().getSelectedItem()).getCategoria().toString());
        PESSOAScbTipoLog.getSelectionModel().select((PESSOAStabela.getSelectionModel().getSelectedItem()).getTipoLog());
        if((PESSOAStabela.getSelectionModel().getSelectedItem()).getCidade() != null)
        {
            String uf = (PESSOAStabela.getSelectionModel().getSelectedItem()).getCidade().getEstado().toString();
            PESSOAScbUF.getSelectionModel().select(uf);
            ctrGerenciarPessoas.CarregarComboBox(PESSOAScbCidade, "cidade", "estado.est_sgl LIKE '"+uf+"'");
            PESSOAScbCidade.getSelectionModel().select((PESSOAStabela.getSelectionModel().getSelectedItem()).getCidade().toString());
        }
        //PESSOAScbCategoria.onActionProperty();
        
        
        BufferedImage bimg = ctrGerenciarPessoas.getCtrpessoa().LerImagem(PESSOAStabela.getSelectionModel().getSelectedItem().getCod());
        if(bimg != null)
        {
            PESSOASimg.setImage(SwingFXUtils.toFXImage(bimg, null));
            PESSOASimg.setId("0");
        }
        
        ctrGerenciarPessoas.CarregarDados(PESSOAStabelaVeiculos, PESSOAStabelaDependentes, PESSOAStabelaLotes, PESSOAStabela.getSelectionModel().getSelectedItem().getCod());
        if(PESSOAStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Livre Acesso"))
           PESSOASrbLivreAcesso.setSelected(true);
        if(PESSOAStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Mediante Autorização"))
           PESSOASrbMedianteAutorizacao.setSelected(true);
        if(PESSOAStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Não Autorizado"))
           PESSOASrbNaoAutorizado.setSelected(true);
    }
    
    private void CarregarDadosContaPagar()
    {
        codigo_pessoa = 0;
        codigo_empresa = 0;
        if(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getPessoa() != null)
           codigo_pessoa = CONTAPAGARtabela.getSelectionModel().getSelectedItem().getPessoa().getCod();
        if(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getEmpresa() != null)
           codigo_empresa = CONTAPAGARtabela.getSelectionModel().getSelectedItem().getEmpresa().getCod();
        
        ctrLancarContaPagar.getCtrcontapagar().CarregarDados(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getCod());
        
        CONTAPAGARtfCod.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getCod() + "");
        CONTAPAGARtfDescricao.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getDescricao());
        CONTAPAGARtfValor.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getValor() + ""); 
        
        if(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getPessoa() != null)
           CONTAPAGARtfPessoa.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getPessoa().getNome()+" ("+CONTAPAGARtabela.getSelectionModel().getSelectedItem().getPessoa().getCpf()+")");
        else
           CONTAPAGARtfPessoa.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getEmpresa().getNome()+" ("+CONTAPAGARtabela.getSelectionModel().getSelectedItem().getEmpresa().getCnpj()+")");
            
        if(CONTAPAGARtfPessoa.getText().contains("()"))
           CONTAPAGARtfPessoa.setText(CONTAPAGARtfPessoa.getText().replace("()", ""));
            
        CONTAPAGARcbForma.getSelectionModel().select(0);
        if(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getNum_parcelas() != 0)
        {
           CONTAPAGARcbForma.getItems().remove(1);
           String info = "A prazo (";
           if(!CONTAPAGARtabela.getSelectionModel().getSelectedItem().getValor_entrada().equals(""))
               info += "R$"+CONTAPAGARtabela.getSelectionModel().getSelectedItem().getValor_entrada()+" + ";
           info += CONTAPAGARtabela.getSelectionModel().getSelectedItem().getNum_parcelas()+" parcelas)";
           CONTAPAGARcbForma.getItems().add(info);
           CONTAPAGARcbForma.getSelectionModel().select(1); 
           CONTAPAGARbtConfigurar.setDisable(false);
           CONTAPAGARdpDataVencimento.setDisable(true);
        }
        else //à vista
        {
            CONTAPAGARbtConfigurar.setDisable(true);
            CONTAPAGARdpDataVencimento.setDisable(false);
            if(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getData_vencimento() != null)
               CONTAPAGARdpDataVencimento.setValue(Funcoes.StringToDate(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getData_vencimento()));
        }
        
        CONTAPAGARtfObservacoes.setText(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getObservacao());
        //ctrGerenciarPessoas.CarregarDados(PESSOAStabelaVeiculos, PESSOAStabelaDependentes, PESSOAStabelaLotes, PESSOAStabela.getSelectionModel().getSelectedItem().getCod());
    }
    
    private void CarregarDadosContaReceber()
    {
        codigo_pessoa = CONTARECEBERtabela.getSelectionModel().getSelectedItem().getPessoa().getCod();
        ctrLancarContaReceber.getCtrcontareceber().CarregarDados(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getCod());
        
        CONTARECEBERtfCod.setText(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getCod() + "");
        CONTARECEBERtfDescricao.setText(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getDescricao());
        CONTARECEBERtfValor.setText(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getValor() + ""); 
        CONTARECEBERtfPessoa.setText(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getPessoa().getNome()+" ("+CONTARECEBERtabela.getSelectionModel().getSelectedItem().getPessoa().getCpf()+")");
            if(CONTARECEBERtfPessoa.getText().contains("()"))
               CONTARECEBERtfPessoa.setText(CONTARECEBERtfPessoa.getText().replace("()", ""));
            
        CONTARECEBERcbForma.getSelectionModel().select(0);
        if(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getNum_parcelas() != 0)
        {
           CONTARECEBERcbForma.getItems().remove(1);
           String info = "A prazo (";
           if(!CONTARECEBERtabela.getSelectionModel().getSelectedItem().getValor_entrada().equals(""))
               info += "R$"+CONTARECEBERtabela.getSelectionModel().getSelectedItem().getValor_entrada()+" + ";
           info += CONTARECEBERtabela.getSelectionModel().getSelectedItem().getNum_parcelas()+" parcelas)";
           CONTARECEBERcbForma.getItems().add(info);
           CONTARECEBERcbForma.getSelectionModel().select(1); 
           CONTARECEBERbtConfigurar.setDisable(false);
           CONTARECEBERdpDataVencimento.setDisable(true);
        }
        else //à vista
        {
            CONTARECEBERbtConfigurar.setDisable(true);
            CONTARECEBERdpDataVencimento.setDisable(false);
            if(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getData_vencimento() != null)
               CONTARECEBERdpDataVencimento.setValue(Funcoes.StringToDate(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getData_vencimento()));
        }
        
        CONTARECEBERtfObservacoes.setText(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getObservacao());
        //ctrGerenciarPessoas.CarregarDados(PESSOAStabelaVeiculos, PESSOAStabelaDependentes, PESSOAStabelaLotes, PESSOAStabela.getSelectionModel().getSelectedItem().getCod());
    }
    
    private void CarregarDadosDependente()
    {
        DEPENDENTESlbCategoria.setLayoutX(21);
        DEPENDENTEScbCategoria.setLayoutX(21);
        DEPENDENTESlbCategoria.setLayoutY(245);
        DEPENDENTEScbCategoria.setLayoutY(265);
        
        if(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getUltimamov().equals("E"))
            DEPENDENTESrbEntrada.setSelected(true);
        else
            DEPENDENTESrbSaida.setSelected(true);
        
        DEPENDENTEStfCod.setText(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getCod() + "");
        DEPENDENTEStfBairro.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getBairro());
        DEPENDENTEStfCEP.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCep());
        DEPENDENTEStfCPF.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCpf() + "");
        DEPENDENTEStfEmail.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getEmail());
        DEPENDENTEStfLogradouro.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getLogradouro());
        DEPENDENTESTelatfNome.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getNome());
        DEPENDENTEStfNum.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getNumero());
        DEPENDENTEStfTelfone.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getFone());
        DEPENDENTEStfTelfone2.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getFone2());        
        DEPENDENTESdpData.setValue((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getDataNasc());
        DEPENDENTESckbCondutor.setSelected(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getCondutor().equals("Sim"));
        DEPENDENTESTelatfDescricao.setText(((Dependente)(DEPENDENTEStabela.getSelectionModel().getSelectedItem())).getDescricao()); 
        DEPENDENTEStfSupervisor.setText(((Dependente)(DEPENDENTEStabela.getSelectionModel().getSelectedItem())).getSupervisor().getNome()); 
        codigo_supervisor = ((Dependente)(DEPENDENTEStabela.getSelectionModel().getSelectedItem())).getSupervisor().getCod();
        //DEPENDENTEStaHistorico.setText((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getHistorico());
        
        if(((Dependente)DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getSupervisor() instanceof Associado)   //supervisor é associado         
           DEPENDENTEStfInterfone.setText(((Associado)(((Dependente)DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getSupervisor())).getInterfone());
        
         
        DEPENDENTEScbCategoria.getSelectionModel().select((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCategoria().toString());
        DEPENDENTEScbTipoLog.getSelectionModel().select((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getTipoLog());
        if((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCidade() != null)
        {
            String uf = (DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCidade().getEstado().toString();
            DEPENDENTEScbUF.getSelectionModel().select(uf);
            ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbCidade, "cidade", "estado.est_sgl LIKE '"+uf+"'");
            DEPENDENTEScbCidade.getSelectionModel().select((DEPENDENTEStabela.getSelectionModel().getSelectedItem()).getCidade().toString());
        }
        //DEPENDENTEScbCategoria.onActionProperty();
        
        
        BufferedImage bimg = ctrGerenciarPessoas.getCtrpessoa().LerImagem(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getCod());
        if(bimg != null)
        {
            DEPENDENTESimg.setImage(SwingFXUtils.toFXImage(bimg, null));
            DEPENDENTESimg.setId("0");
        }
        
        ctrGerenciarPessoas.Novo();
        ctrGerenciarPessoas.getCtrpessoa().getPessoa().setCod(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getCod());
        if(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Livre Acesso"))
           DEPENDENTESrbLivreAcesso.setSelected(true);
        if(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Mediante Autorização"))
           DEPENDENTESrbMedianteAutorizacao.setSelected(true);
        if(DEPENDENTEStabela.getSelectionModel().getSelectedItem().getAcesso().equals("Não Autorizado"))
           DEPENDENTESrbNaoAutorizado.setSelected(true);
    }
    
    @FXML
    private void PESSOASkpPesquisar(KeyEvent event) {
        //implementar se o Usuário apertar ENTER
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           Pesquisar();
        
    }
    
    private void Pesquisar()
    {
        PESSOASbtAlterar.setDisable(true);
        PESSOASbtApagar.setDisable(true);
        PESSOAStabela.setId("SUPERVISORES");
        String filtro = PESSOAStxPesquisa.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cpf
        {
            PESSOAStxPesquisarCPF.setText(filtro);
            ctrGerenciarPessoas.CarregarTabela(PESSOAStabela, PESSOAStxPesquisarCPF.getText(), PESSOAScbFiltrarCategoria.getValue());
        }
        else
            ctrGerenciarPessoas.CarregarTabela(PESSOAStabela, PESSOAStxPesquisa.getText(), PESSOAScbFiltrarCategoria.getValue());
        
    }
    
    private void PesquisarDependente()
    {
        DEPENDENTESbtAlterar.setDisable(true);
        DEPENDENTESbtApagar.setDisable(true);
        String filtro = DEPENDENTEStxPesquisa.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cpf
        {
            DEPENDENTEStxPesquisarCPF.setText(filtro);
            ctrGerenciarPessoas.CarregarTabela(DEPENDENTEStabela, DEPENDENTEStxPesquisarCPF.getText(), DEPENDENTEScbFiltrarCategoria.getValue());
        }
        else
            ctrGerenciarPessoas.CarregarTabela(DEPENDENTEStabela, DEPENDENTEStxPesquisa.getText(), DEPENDENTEScbFiltrarCategoria.getValue());
    }
    
    private void Usuario_Acesso()
    {
        imvCaixa.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        imvContas.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        imvRelFinanceiro.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        imvBackup.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        imvBancoDados.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        imvRestauracao.setVisible(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1);
        if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 2) //básico
        {
           imvEventos.setLayoutX(0);
           imvRelMovimentacoes.setLayoutX(139);
           imvRelOutros.setLayoutX(278);
           imvRelPessoas.setLayoutX(417);
           imvRelPessoas.setLayoutY(0);
           imvRelVeiculos.setLayoutX(0);
           imvSistema.setLayoutX(139);
           imvSistema.setLayoutY(0);
        }
        else
        {
           imvEventos.setLayoutX(278);
           imvRelMovimentacoes.setLayoutX(278);
           imvRelOutros.setLayoutX(417);
           imvRelPessoas.setLayoutX(0);
           imvRelPessoas.setLayoutY(44);
           imvRelVeiculos.setLayoutX(139);
           imvSistema.setLayoutX(0);
           imvSistema.setLayoutY(44);
        }
    }
    
    private void Login() throws SQLException, IOException
    {
        boolean erro = false;
            
            if(tfSenha.getText().equals(""))
            {
                erro = true;
                tfSenha.requestFocus();
                lbSenha.setTextFill(Color.RED);
            }
            else
                lbSenha.setTextFill(Color.BLACK);
            if(tfUsuario.getText().equals(""))
            {
                erro = true;
                tfUsuario.requestFocus();
                lbUsuario.setTextFill(Color.RED);
            }
            else
                lbUsuario.setTextFill(Color.BLACK);
            
            if(erro)
               return;
        if(ctrGerenciarPessoas.getCtrpessoa().login(tfUsuario.getText(), tfSenha.getText()) == 0) //retorna o nivel do Usuário, se 0 login inválido!
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Usuário e/ou senha incorreto(s).");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        Usuario_Acesso();
        apPessoas.setLayoutX(7);
        apPessoas.setLayoutY(163);
        PESSOASbtCancelar.setText("CANCELAR");        
        usuario = ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod();
        Banco.con.IniciarTransacao();
        if(ctrRegistrarEvento.getCtrevento().getEvento().finalizar_eventos_passados())
            Banco.con.Commit("Finalizar eventos passados.");
        else
            Banco.con.Rollback("");
        
        //Fechando Caixa se estiver aberto, pois pode ter trocado de usuário, nova sessão
        ctrFluxoCaixa.Novo();
        if(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa() != null) //já existia um caixa
        {
            ctrFluxoCaixa.getCtrcaixa().setCaixa(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa());
            if(ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
            {
               Banco.con.IniciarTransacao();
               if(ctrFluxoCaixa.getCtrcaixa().getCaixa().fechar())
                  Banco.con.Commit("Fechar caixa.");
               else
               {
                   Banco.con.Rollback("Não foi possível fechar o caixa.");
                   return;
               }
            }
        }/*
        else //NÃO EXISTE CAIXA, pedir para fazer movimentação inicial
            if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() == 1) //só permito Administradores mexer no Fluxo de Caixa
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Iniciar Caixa");
                alert.setContentText("Não existe nenhum registro no Fluxo de Caixa.\nDeseja iniciar o caixa ?");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
                ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
                if(alert.showAndWait().get() == buttonTypeSim)
                   InformarSaldoInicial();
            }        
        */
        
        TelaLogin(false);
        TelaOriginal(true); //tela padrão: Pessoas
        MenuOriginal(true); //menu no estado original
        btSair.setVisible(true);
        lbBemVindo.setVisible(true);        
        lbBemVindo.setText("Bem-vindo "+ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNome().split(" ")[0]);        
        HabilitaTela(imvPessoas);
        apEsquerdo.setDisable(false);
        //Carregando movimentações entrada/saída
        lbEntrada.setTextFill(Color.BLUE);
        lbSaida.setTextFill(Color.RED);        
        ckbUltimaMov.setSelected(false);
        txPesquisarEntrada.setText("");
        txPesquisarSaida.setText("");
        PesquisarEntrada();
        PesquisarSaida();
        btVerDadosPessoais.setDisable(true);        
        apServidor.setVisible(false);
        PESSOASrbMedianteAutorizacao.setDisable(false);
        PESSOASrbNaoAutorizado.setDisable(false);
    }
    
    @FXML
    private void kpUsuario(KeyEvent event) throws SQLException, IOException {       
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);        
        if(ENTER.match(event))
           Login();
    }

    @FXML
    private void kpSenha(KeyEvent event) throws SQLException, IOException {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           Login();
    }
        
    @FXML
    private void clkLogin(ActionEvent event) throws SQLException, IOException 
    {
        Login();           
    }

    @FXML
    private void clkSair(ActionEvent event) throws SQLException {
        
        Banco.con.IniciarTransacao();
        if(ctrRegistrarEvento.getCtrevento().getEvento().finalizar_eventos_passados())
            Banco.con.Commit("Finalizar eventos passados.");
        else
            Banco.con.Rollback("");
        
        //Fechando Caixa se estiver aberto, pois pode ter trocado de usuário, nova sessão
        ctrFluxoCaixa.Novo();
        if(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa() != null) //já existia um caixa
        {
            ctrFluxoCaixa.getCtrcaixa().setCaixa(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa());
            if(ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
            {
               Banco.con.IniciarTransacao();
               if(ctrFluxoCaixa.getCtrcaixa().getCaixa().fechar())
                  Banco.con.Commit("Fechar caixa.");
               else
               {
                   Banco.con.Rollback("Não foi possível fechar o caixa.");
                   return;
               }
            }
        }
        TelaLogin(true);     
    }

    @FXML
    private void PESSOASclkIr(ActionEvent event) {
        Pesquisar();
    }
    @FXML
    private void PESSOASclkTabela(MouseEvent event) {
        if(PESSOAStabela.getSelectionModel().getSelectedItem() != null)
        {
            PESSOASbtAlterar.setDisable(false);
            PESSOASbtApagar.setDisable(false);
        }                  
    }
    
    private void NovaContaPagar() throws SQLException
    {    
        CONTAPAGARlbCamposObrigatorios.setVisible(true);
        if(ctrFluxoCaixa.CarregarDados())
        {
            CONTAPAGARlbSaldo.setText("Caixa: ");
            if(Float.parseFloat(ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
            {
                CONTAPAGARlbSaldo.setTextFill(Color.RED);
                CONTAPAGARlbSaldo.setText(CONTAPAGARlbSaldo.getText()+"-");
            }
            else
                CONTAPAGARlbSaldo.setTextFill(Color.GREEN);
            CONTAPAGARlbSaldo.setText(CONTAPAGARlbSaldo.getText()+"R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace("-", ""));
        }    
        
        CONTAPAGARdpDataVencimento.setDisable(true);
        CONTAPAGARbtConfigurar.setDisable(true);
        codigo_pessoa = 0;
        codigo_empresa = 0;
        CONTAPAGARbtConfirmar.getTooltip().setText("Lançar conta");
        apTabelaContasPagar.setDisable(true);
        apDadosContaPagar.setDisable(false);
        CONTAPAGARbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        
        CONTAPAGARbtCancelar.setDisable(false);
        CONTAPAGARbtConfirmar.setDisable(false);
        ctrLancarContaPagar.Novo();
        ObservableList <Node> componentes = aux_dadoscontapagar.getChildren();
        
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setDisable(false);
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).setDisable(false);
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).setDisable(false);
            
            if(n instanceof Label)
                ((Label)n).setDisable(false);
        }
        CONTAPAGARtfCod.setDisable(true);
        CONTAPAGARbtSelecionarPessoaEmpresa.setDisable(false);
        CONTAPAGARtfDescricao.requestFocus();        
    }
    
    private void NovaContaReceber()
    {
        CONTARECEBERlbCamposObrigatorios.setVisible(true);
        CONTARECEBERdpDataVencimento.setDisable(true);
        CONTARECEBERbtConfigurar.setDisable(true);
        codigo_pessoa = 0;
        CONTARECEBERbtConfirmar.getTooltip().setText("Lançar conta");
        apTabelaContasReceber.setDisable(true);
        apDadosContaReceber.setDisable(false);
        CONTARECEBERbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        
        CONTARECEBERbtCancelar.setDisable(false);
        CONTARECEBERbtConfirmar.setDisable(false);
        ctrLancarContaReceber.Novo();
        ObservableList <Node> componentes = aux_dadoscontareceber.getChildren();
        
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setDisable(false);
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).setDisable(false);
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).setDisable(false);
            
            if(n instanceof Label)
                ((Label)n).setDisable(false);
        }
        CONTARECEBERtfCod.setDisable(true);
        CONTARECEBERbtSelecionarPessoa.setDisable(false);
        CONTARECEBERtfDescricao.requestFocus();        
    }
    
    private void Novo()
    {
        PESSOASlbCamposObrigatorios.setVisible(true);
        PESSOASbtConfirmar.getTooltip().setText("Cadastrar pessoa");
        apTabelaPessoas.setDisable(true);
        apDadosPessoas.setDisable(false);
        PESSOASbtCancelar.setDisable(false);
        PESSOASbtConfirmar.setDisable(true);
        PESSOAStfCod.setDisable(true);
        PESSOASckbCondutor.setSelected(true);
        PESSOAScbCidade.setPromptText("Selecione o UF");
        PESSOAScbCategoria.setPromptText("Escolha");
        PESSOAScbCategoria.setVisible(true);
        PESSOASlbCategoria.setVisible(true);
        aux_dadospessoas.setMinHeight(423);
        TabPane.setMinHeight(423);
        TabPane.getTabs().remove(tabDadosUsuario);
        //tabDadosUsuario.setDisable(true);
        ctrGerenciarPessoas.Novo();
        //PESSOAScbUF.getSelectionModel().select("PR");
        //PESSOAScbCidade.getSelectionModel().select("Colorado");
        PESSOAScbCategoria.requestFocus();
    }
    
    @FXML
    private void PESSOASclkNovo(ActionEvent event) {
        Novo();
    }
    @FXML
    private void PESSOASclkAlterar(ActionEvent event) {
        PESSOASlbCamposObrigatorios.setVisible(true);
        PESSOASbtConfirmar.getTooltip().setText("Alterar pessoa");
        CarregarDados();
        apTabelaPessoas.setDisable(true);
        apDadosPessoas.setDisable(false);
        PESSOASbtCancelar.setDisable(false);
        PESSOASbtConfirmar.setDisable(false); 
        PESSOAStfCod.setDisable(true);
        PESSOASapAcesso.requestFocus();
    }
    
    private void PESSOASModoExibicao(boolean status)
    {
        ObservableList <Node> componentes = aux_dadospessoas.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
            }
            if(n instanceof Button && !n.equals(PESSOASbtConfirmar) && !n.equals(PESSOASbtCancelar))
                ((Button)n).setDisable(status);
            
        }
        
        componentes = PESSOASapAcesso.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        
        }
        
        componentes = PESSOASapUltimaMovimentacao.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        
        }
        
        //Campo foto
        PESSOASbtnCamera.setDisable(status);
        PESSOASbtApagarIMG.setDisable(status);
        PESSOASbtCarregarIMG.setDisable(status);
        
        componentes = aux_dadosusuario.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }                
            
            if(n instanceof RadioButton)
            {
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status); 
            }
            
        }
        componentes = PESSOASapDadosFuncionario.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }                
            
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            
        }
        PESSOASlbVerSenha.setTextFill(Color.RED);
    }
    
    private void EVENTOSModoExibicao(boolean status)
    {
        ObservableList <Node> componentes = aux_dadosevento.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
            }
            if(n instanceof Button && !n.equals(EVENTObtConfirmar) && !n.equals(EVENTObtCancelar))
                ((Button)n).setDisable(status);
            
        }
        
        EVENTOtfPessoa.setEditable(false);
        EVENTOtfPessoa.setFocusTraversable(false);
        EVENTOtfPessoa.setMouseTransparent(true);
        EVENTOtfParticipantes.setEditable(false);
        EVENTOtfParticipantes.setFocusTraversable(false);
        EVENTOtfParticipantes.setMouseTransparent(true);
        
        componentes = EVENTOapSituacao.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            
        }
        if(status)
           EVENTObtConfigurar.setDisable(false);
    }
    
    private void DEPENDENTESModoExibicao(boolean status)
    {
        DEPENDENTEStfSupervisor.setDisable(!status);
        ObservableList <Node> componentes = aux_dadosdependentes.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
            }
            if(n instanceof Button && !n.equals(DEPENDENTESTelabtConfirmar) && !n.equals(DEPENDENTESTelabtCancelar))
                ((Button)n).setDisable(status);
            
        }
        
        componentes = DEPENDENTESapAcesso.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        
        }
        
        componentes = DEPENDENTESapUltimaMovimentacao.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        
        }
        //Campo foto
        DEPENDENTESbtnCamera.setDisable(status);
        DEPENDENTESbtApagarIMG.setDisable(status);
        DEPENDENTESbtCarregarIMG.setDisable(status);
        DEPENDENTEStfSupervisor.setEditable(false);
        DEPENDENTEStfSupervisor.setFocusTraversable(false);
        DEPENDENTEStfSupervisor.setMouseTransparent(true);        
        DEPENDENTEStfSupervisor.setDisable(false);
    }
    
    private void VEICULOSModoExibicao(boolean status)
    {
        VEICULOStfProprietario.setDisable(!status);
        ObservableList <Node> componentes = aux_dadosveiculos.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
            }
            if(n instanceof Button && !n.equals(VEICULOSTelabtConfirmar) && !n.equals(VEICULOSTelabtCancelar))
                ((Button)n).setDisable(status);
            
        }
        
        componentes = VEICULOSapUltimaMovimentacao.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        }
        
        componentes = VEICULOSapAutorizado.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
        }
        VEICULOStfProprietario.setEditable(false);
        VEICULOStfProprietario.setFocusTraversable(false);
        VEICULOStfProprietario.setMouseTransparent(true);        
        VEICULOStfProprietario.setDisable(false);
       
    }
    
    private void CONTAPAGARModoExibicao(boolean status)
    {
        CONTAPAGARtfPessoa.setDisable(!status);
        ObservableList <Node> componentes = aux_dadoscontapagar.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
                ((TextInputControl)n).setDisable(false);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
                ((ComboBox)n).setDisable(false);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);    
                ((DatePicker)n).setDisable(false);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
                ((TableView)n).setDisable(false);
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
                ((RadioButton)n).setDisable(false);
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
                ((CheckBox)n).setDisable(false);
            }
            if(n instanceof Button && !n.equals(CONTAPAGARbtConfirmar) && !n.equals(CONTAPAGARbtCancelar))
                ((Button)n).setDisable(status);
            
        }
        CONTAPAGARtfPessoa.setEditable(false);
        CONTAPAGARtfPessoa.setFocusTraversable(false);
        CONTAPAGARtfPessoa.setMouseTransparent(true);        
        CONTAPAGARtfPessoa.setDisable(false);
    }
    
    private void CONTARECEBERModoExibicao(boolean status)
    {
        CONTARECEBERtfPessoa.setDisable(!status);
        ObservableList <Node> componentes = aux_dadoscontareceber.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
                ((TextInputControl)n).setDisable(false);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
                ((ComboBox)n).setDisable(false);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);    
                ((DatePicker)n).setDisable(false);
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
                ((TableView)n).setDisable(false);
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
                ((RadioButton)n).setDisable(false);
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
                ((CheckBox)n).setDisable(false);
            }
            if(n instanceof Button && !n.equals(CONTARECEBERbtConfirmar) && !n.equals(CONTARECEBERbtCancelar))
                ((Button)n).setDisable(status);
            
        }
        CONTARECEBERtfPessoa.setEditable(false);
        CONTARECEBERtfPessoa.setFocusTraversable(false);
        CONTARECEBERtfPessoa.setMouseTransparent(true);        
        CONTARECEBERtfPessoa.setDisable(false);
    }
    
    private void EMPRESASModoExibicao(boolean status)
    {
        ObservableList <Node> componentes = aux_dadosempresas.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setEditable(!status);
                ((TextInputControl)n).setFocusTraversable(!status);
                ((TextInputControl)n).setMouseTransparent(status);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).setEditable(false);
                ((ComboBox)n).setFocusTraversable(!status);
                ((ComboBox)n).setMouseTransparent(status);
            }
            if(n instanceof Label)
                ((Label)n).setTextFill(Color.BLACK);
            if(n instanceof DatePicker)
            {
                ((DatePicker)n).setEditable(!status);
                ((DatePicker)n).setFocusTraversable(!status);
                ((DatePicker)n).setMouseTransparent(status);                
            }
            if(n instanceof TableView)
            {
                ((TableView)n).setEditable(!status);
                ((TableView)n).setFocusTraversable(!status);
                ((TableView)n).setMouseTransparent(status);   
            }
            if(n instanceof RadioButton)
            {
                //(RadioButton)n).setEditable(!status);
                ((RadioButton)n).setFocusTraversable(!status);
                ((RadioButton)n).setMouseTransparent(status);  
            }
            if(n instanceof CheckBox)
            {
                //((CheckBox)n).setEditable(!status);
                ((CheckBox)n).setFocusTraversable(!status);
                ((CheckBox)n).setMouseTransparent(status);
            }
            if(n instanceof Button && !n.equals(EMPRESASbtConfirmar) && !n.equals(EMPRESASbtCancelar))
                ((Button)n).setDisable(status);
            
        }
        //Campo foto
        EMPRESASbtnCamera.setDisable(status);
        EMPRESASbtApagarIMG.setDisable(status);
        EMPRESASbtCarregarIMG.setDisable(status);
    }
    
    @FXML
    private void PESSOASclkApagar(ActionEvent event) {
        PESSOASlbCamposObrigatorios.setVisible(false);
        PESSOAScbCidade.setPromptText("");
        PESSOASbtConfirmar.getTooltip().setText("Desativar pessoa");
        CarregarDados();
        apTabelaPessoas.setDisable(true);
        apDadosPessoas.setDisable(false);
        
        PESSOASModoExibicao(true);
        
        PESSOASbtCancelar.setDisable(false);
        PESSOASbtConfirmar.setDisable(false);
    }
    
    @FXML
    private void PESSOASclkCategoria(ActionEvent event) 
    {
        if(PESSOAScbCategoria.getValue() != null)
        {
           PESSOASlbCategoria.setLayoutX(257);
           PESSOAScbCategoria.setLayoutX(257);
           PESSOASlbCategoria.setLayoutY(183);
           PESSOAScbCategoria.setLayoutY(203);
           ExibirCamposPorCategoria(PESSOAScbCategoria.getValue());
           PESSOASapAcesso.requestFocus();
           PESSOASbtConfirmar.setDisable(false);
        }
    }
    
    
    @FXML
    private void PESSOASclkBuscarCep(ActionEvent event) {
        if(PESSOAStfCEP.getText().equals(""))
        {
            PESSOAStfCEP.requestFocus();
            PESSOASlbCep.setTextFill(Color.RED);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Digite um CEP válido para buscar a localização do mesmo.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;  
        }
        PESSOASlbCep.setTextFill(Color.BLACK);
        
        PESSOAStfBairro.setText("");
        PESSOAStfLogradouro.setText("");
        PESSOAScbTipoLog.getSelectionModel().clearSelection();
        PESSOAScbUF.getSelectionModel().clearSelection();
        PESSOAScbCidade.getSelectionModel().clearSelection();
        
        String endereco = ctrGerenciarPessoas.consultaCep(PESSOAStfCEP.getText(),"json", "261641425", "64669093", "177.131.35.1", "3128"); //com proxy
        //String endereco = consultaCep(tfCEP.getText(), "json"); //sem proxy
        
        //System.out.println(endereco);
        JSONObject jdados = new JSONObject(endereco);
        //System.out.println(obj.getString("cidade")); //cidade é o campo chave, retorna o nome da cidade
        PESSOAStfBairro.setText(jdados.getString("bairro"));
        PESSOAStfLogradouro.setText(jdados.getString("logradouro"));
        
        
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        int achou = 0;
        for(int i=0; achou == 0 && i<PESSOAScbTipoLog.getItems().size();i++)
            if(PESSOAScbTipoLog.getItems().get(i).equals(jdados.getString("tipo_logradouro"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               PESSOAScbTipoLog.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        
        
        
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        achou = 0;
        for(int i=0; achou == 0 && i<PESSOAScbUF.getItems().size();i++)
            if(PESSOAScbUF.getItems().get(i).equals(jdados.getString("uf"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               PESSOAScbUF.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        achou  = 0;
        for(int i=0; achou == 0 && i<PESSOAScbCidade.getItems().size();i++)
            if(PESSOAScbCidade.getItems().get(i).equals(jdados.getString("cidade"))) //achei o cara
            {
               //cbCidade.setValue(cbCidade.getItems().get(i));
               PESSOAScbCidade.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        
        //if(achou == 0) //nao achou cep, cep inválido!
        
    }

    @FXML
    private void PESSOASclkUF(ActionEvent event) {
        if(PESSOAScbUF.getValue() != null)
        {
           PESSOAScbCidade.setPromptText("Escolha a Cidade");
           PESSOAScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarPessoas.CarregarComboBox(PESSOAScbCidade, "cidade", "estado.est_sgl LIKE '"+PESSOAScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(PESSOAScbCidade);
        
    }    
    
    @FXML
    private void PESSOASkpUF(KeyEvent event) {
        if(PESSOAScbUF.getValue() != null)
        {
           PESSOAScbCidade.setPromptText("Escolha a Cidade");
           PESSOAScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarPessoas.CarregarComboBox(PESSOAScbCidade, "cidade", "estado.est_sgl LIKE '"+PESSOAScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(PESSOAScbCidade);
    }

    @FXML
    private void PESSOASclkCarregaIMG(ActionEvent event) {
        Image foto = null;
        FileChooser fc = new FileChooser();
        Node source = (Node) event.getSource();
        fc.setTitle("Selecione a Imagem"); 
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Imagens (.gif, .jpg, .png)", "*.jpg", "*.png", "*.gif"));
        File arq=fc.showOpenDialog(source.getScene().getWindow());
        if(arq!=null)
        {
            foto = new Image(arq.toURI().toString());
            PESSOASimg.setImage(foto);
            PESSOASimg.setId("0");
        }
    }

    @FXML
    private void PESSOASclkApagaIMG(ActionEvent event) {
        java.net.URL url = getClass().getResource("imagens/usuario.png");
        PESSOASimg.setImage(new Image(url.toString()));
        PESSOASimg.setId("1");
    }


    @FXML
    private void PESSOASclkCancelar(ActionEvent event) {
        if(!PESSOASbtCancelar.getText().equals("VOLTAR"))
           estadoOriginal();
        else
        {
            TelaInicial(true);
        }
    }

    @FXML
    private void PESSOASclkConfirmar(ActionEvent event) throws SQLException 
    {        
        if(PESSOASbtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");            
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            
            if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getNivel() != 1 && USUARIOrbAdministrador.isSelected())
            { //o usuário do sistema é BÁSICO e está tentando excluir um Administrador, PROIBIDO!!!
               Alert alert2 = new Alert(Alert.AlertType.ERROR);
               alert2.setHeaderText("Erro");            
               alert2.setContentText("Você não tem permissão para desativar um administrador do sistema."); 
               alert2.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
               alert2.showAndWait();
               return;
            }
            if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod() == ctrGerenciarPessoas.getCtrpessoa().getPessoa().getCod()) //usuário tá se auto-excluindo
               alert.setContentText("Você está prestes a desativar o próprio registro do sistema, ficando sem acesso ao mesmo.\nDeseja realmente desativar ?");   
            
            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrGerenciarPessoas.getCtrpessoa().Excluir())
                {
                   Banco.con.Commit("Desativar pessoa.");
                   estadoOriginal();
                   Pesquisar();
                   if(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod() == ctrGerenciarPessoas.getCtrpessoa().getPessoa().getCod()) //usuário tá se auto-excluindo
                      TelaLogin(true);
                }
                else
                   Banco.con.Rollback("");
            }
            return;
        }
        
        erros = "";
        boolean erro = false;
        SingleSelectionModel<Tab> selectionModel = TabPane.getSelectionModel();
        if(PESSOAScbCategoria.getValue() != null && PESSOAScbCategoria.getValue().equals("Funcionário") && PESSOASdpDataDemissao.getValue() != null && PESSOASdpDataDemissao.getValue().isAfter(LocalDate.now()))
        {
            erro = true;
            erros = "Data de demissão: inválida, posterior ao dia de hoje.\n" + erros;
            selectionModel.select(0);
            PESSOASdpDataAdmissao.requestFocus();
            
            PESSOASlbDataAdmissao.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbDataAdmissao.setTextFill(Color.BLACK);
        }
        if(PESSOAScbCategoria.getValue() != null && PESSOAScbCategoria.getValue().equals("Funcionário") && PESSOASdpDataAdmissao.getValue() != null && PESSOASdpDataDemissao.getValue() != null && PESSOASdpDataAdmissao.getValue().isAfter(PESSOASdpDataDemissao.getValue()))
        {
            erro = true;
            erros = "Data de admissão: inválida, posterior a data de demissão.\n" + erros;
            selectionModel.select(0);
            PESSOASdpDataDemissao.requestFocus();
            
            PESSOASlbDataDemissao.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbDataDemissao.setTextFill(Color.BLACK);
        }

        if(PESSOAScbCategoria.getValue() != null && PESSOAScbCategoria.getValue().equals("Funcionário") && PESSOASdpDataAdmissao.getValue() != null && PESSOASdpDataAdmissao.getValue().isAfter(LocalDate.now()))
        {
            erro = true;
            erros = "Data de admissão: inválida, posterior ao dia de hoje.\n" + erros;
            selectionModel.select(0);
            PESSOASdpDataAdmissao.requestFocus();
            
            PESSOASlbDataAdmissao.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbDataAdmissao.setTextFill(Color.BLACK);
        }

        if(PESSOAScbCategoria.getValue() != null && PESSOAScbCategoria.getValue().equals("Funcionário") && !PESSOAStfSalario.getText().equals("") && Float.parseFloat(PESSOAStfSalario.getText().replace(",", ".")) == 0)
        {
            erro = true;
            erros = "Salário: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfSalario.requestFocus();
            
            PESSOASlbSalario.setTextFill(Color.RED);
        }
        else
        {
            PESSOASlbSalario.setTextFill(Color.BLACK);
            
        }
        
        if(!PESSOAStfEmail.getText().equals("") && !Funcoes.isEmail(PESSOAStfEmail.getText()))
        {
            erro = true;
            erros = "Email: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfEmail.requestFocus();
            
            PESSOASlbEmail.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbEmail.setTextFill(Color.BLACK);
        }
        if((PESSOAScbCategoria.getValue() != null && PESSOAScbCategoria.getValue().equals("Associado")) && !PESSOAStfInterfone.getText().equals("") && !Funcoes.isInt(PESSOAStfInterfone.getText()))
        {
            erro = true;
            erros = "Interfone: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfInterfone.requestFocus();
            
            PESSOASlbInterfone.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbInterfone.setTextFill(Color.BLACK);
        }
        if(!PESSOAStfTelfone2.getText().equals("") && !Funcoes.ValidaTelefone(PESSOAStfTelfone2.getText()))
        {
            erro = true;
            erros = "Telefone 2: não foi preenchido corretamente ou inválido.\n" + erros;
            selectionModel.select(0);
            PESSOAStfTelfone2.requestFocus();
            
            PESSOASlbTelefone2.setTextFill(Color.RED);

        }
        else
        {
            
            PESSOASlbTelefone2.setTextFill(Color.BLACK);
        }
        if(!PESSOAStfTelfone.getText().equals("") && !Funcoes.ValidaTelefone(PESSOAStfTelfone.getText()))
        {
            erro = true;
            erros = "Telefone 1: não foi preenchido corretamente ou inválido.\n" + erros;
            selectionModel.select(0);
            PESSOAStfTelfone.requestFocus();
            
            PESSOASlbTelefone.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbTelefone.setTextFill(Color.BLACK);
        }
        /*if(PESSOAScbCidade.getValue() == null)
        {
            erro = true;
            erros = "Cidade: não foi informada.\n" + erros;
            selectionModel.select(0);
            PESSOAScbCidade.requestFocus();
            
            PESSOASlbCidade.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbCidade.setTextFill(Color.BLACK);
        }
        if(PESSOAScbUF.getValue() == null)
        {
            erro = true;
            erros = "UF: não foi informado.\n" + erros;
            selectionModel.select(0);
            PESSOAScbUF.requestFocus();
            
            PESSOASlbUf.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbUf.setTextFill(Color.BLACK);
        }*/
        
        /*if(PESSOAStfBairro.getText().equals(""))
        {
            erro = true;
            erros = "Bairro: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfBairro.requestFocus();
            
            PESSOASlbBairro.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbBairro.setTextFill(Color.BLACK);
        }*/
        if(!PESSOAStfCEP.getText().equals("") && !Funcoes.ValidaCEP(PESSOAStfCEP.getText()))
        {
            erro = true;
            erros = "CEP: não foi preenchido corretamente ou inválido.\n" + erros;
            selectionModel.select(0);
            PESSOAStfCEP.requestFocus();
            
            PESSOASlbCep.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbCep.setTextFill(Color.BLACK);
        }
        if(!PESSOAStfNum.getText().equals("") && !Funcoes.isInt(PESSOAStfNum.getText()))
        {
            erro = true;
            erros = "Número: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfNum.requestFocus();
            
            PESSOASlbNumero.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbNumero.setTextFill(Color.BLACK);
        }
        /*if(PESSOAStfLogradouro.getText().equals(""))
        {
            erro = true;
            erros = "Logradouro: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfLogradouro.requestFocus();
            
            PESSOASlbLog.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbLog.setTextFill(Color.BLACK);
        }*/
        /*if(PESSOAScbTipoLog.getSelectionModel().isEmpty())
        {
            erros = "Tipo logradouro: não foi informado.\n" + erros;
            erro = true;
            selectionModel.select(0);
            PESSOAScbTipoLog.requestFocus();
            
            PESSOASlbTipoLog.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbTipoLog.setTextFill(Color.BLACK);
        }*/
        if(PESSOASdpData.getValue() != null && PESSOASdpData.getValue().isAfter(LocalDate.now().minusYears(18))) 
        {   //idade mínima 18 anos
            erro = true;
            erros = "Data de nascimento: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOASdpData.requestFocus();
            
            PESSOASlbData.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbData.setTextFill(Color.BLACK);
        }
        
        if(!PESSOAStfCPF.getText().equals("") && !Funcoes.ValidaCPF(PESSOAStfCPF.getText()))
        {
            erro = true;
            erros = "CPF: não foi preenchido corretamente ou inválido.\n" + erros;
            selectionModel.select(0);
            PESSOAStfCPF.requestFocus();
            
            PESSOASlbCpf.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbCpf.setTextFill(Color.BLACK);
        }
        if(PESSOAScbCategoria.getSelectionModel().isEmpty())
        {                
            erro = true;
            erros = "Categoria: não foi informada.\n" + erros;
            selectionModel.select(0);
            PESSOAScbCategoria.requestFocus();
            
            PESSOASlbCategoria.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbCategoria.setTextFill(Color.BLACK);
        }
        
        if(PESSOAStfNome.getText().equals(""))
        {
            erro = true;
            erros = "Nome: não foi preenchido corretamente.\n" + erros;
            selectionModel.select(0);
            PESSOAStfNome.requestFocus();
            
            PESSOASlbNome.setTextFill(Color.RED);
        }
        else
        {
            
            PESSOASlbNome.setTextFill(Color.BLACK);
        }
        

        //iconeerro.setVisible(erro);
        //msgerro.setVisible(erro);
        
        if(erro)      
            return;


        String condutor = "Não", usuario = "", senha = "";
        int nivel = 0;
        if(PESSOASckbCondutor.isSelected())
            condutor = "Sim";

        if(!PESSOAScbCategoria.getValue().equals("Visitante"))
        {
            usuario = USUARIOtfNome.getText();
            senha = USUARIOtfSenha.getText();
            if(USUARIOrbAdministrador.isSelected())
               nivel = 1;
            else
               nivel = 2; //usuário básico
        }
        String acesso = "", ultima = "";
        if(PESSOASrbLivreAcesso.isSelected())
           acesso = "Livre Acesso";
        if(PESSOASrbMedianteAutorizacao.isSelected())
           acesso = "Mediante Autorização";
        if(PESSOASrbNaoAutorizado.isSelected())
           acesso = "Não Autorizado";
        
        if(PESSOASrbEntrada.isSelected())
           ultima = "E";
        else
           ultima = "S";
        Banco.con.IniciarTransacao();
        if(PESSOASbtConfirmar.getTooltip().getText().contains("Cadastrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrGerenciarPessoas.getCtrpessoa().Salvar(PESSOAStfNome.getText(), PESSOAScbCategoria.getValue(), PESSOAStfCPF.getText(), PESSOAStfBairro.getText(), PESSOAScbTipoLog.getValue(), PESSOAStfLogradouro.getText(), PESSOAStfNum.getText(), PESSOAStfTelfone.getText(), PESSOAStfEmail.getText(), PESSOAStfCEP.getText(), PESSOAScbCidade.getValue(),PESSOAScbUF.getValue(), PESSOASdpData.getValue(), PESSOAStfTelfone2.getText(), PESSOAStfInterfone.getText(), PESSOASdpDataAdmissao.getValue(), PESSOASdpDataDemissao.getValue(), PESSOAStfSalario.getText().replace(",", "."), condutor, usuario, senha, nivel, 0, "", ultima, acesso);
        }
        else //Alterando
        {   
            ctrGerenciarPessoas.getCtrpessoa().getPessoa().setCod(Integer.parseInt(PESSOAStfCod.getText()));
            if(acesso.equals("Não Autorizado") && !ctrGerenciarPessoas.getCtrpessoa().getPessoa().ExisteAdministrador())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("Não foi possível alterar o registro desta pessoa, pois proibindo o acesso dela ao condomínio, perde também acesso ao sistema.\nO sistema precisa ter pelo menos um administrador.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return;
            }
            erro = !ctrGerenciarPessoas.getCtrpessoa().Alterar(Integer.parseInt(PESSOAStfCod.getText()), PESSOAStfNome.getText(), PESSOAScbCategoria.getValue(), PESSOAStfCPF.getText(), PESSOAStfBairro.getText(), PESSOAScbTipoLog.getValue(), PESSOAStfLogradouro.getText(), PESSOAStfNum.getText(), PESSOAStfTelfone.getText(), PESSOAStfEmail.getText(), PESSOAStfCEP.getText(), PESSOAScbCidade.getValue(),PESSOAScbUF.getValue(), PESSOASdpData.getValue(), PESSOAStfTelfone2.getText(), PESSOAStfInterfone.getText(), PESSOASdpDataAdmissao.getValue(), PESSOASdpDataDemissao.getValue(), PESSOAStfSalario.getText().replace(",", "."), condutor, usuario, senha, nivel, 0, "", ultima, acesso);
        }

        if(!PESSOASimg.getId().equals("1"))
        {
            if(!ctrGerenciarPessoas.getCtrpessoa().SalvarImagem(SwingFXUtils.fromFXImage(PESSOASimg.getImage(), null)))
            {
                Banco.con.Rollback("Não foi possível realizar esta operação.");
                return;
            }

        }           
        else
        {
            if(!ctrGerenciarPessoas.getCtrpessoa().SalvarImagem(null))
            {
                Banco.con.Rollback("Não foi possível realizar esta operação.");
                return;
            }
        }

        if(!erro)
        {
            Banco.con.Commit("Cadastrar/alterar pessoa.");
            if(PESSOASbtCancelar.getText().equals("VOLTAR")) //primeiro uso
            {
                PrimeiroUso(false);
                TelaLogin(true);
                return;
            }
           estadoOriginal();
           Pesquisar();
        }
        else
            Banco.con.Rollback("Não foi possível realizar esta operação.");
            
    }    
    
    @FXML
    private void PESSOASclkCamera(MouseEvent event) {
        if(webcam == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Câmera não detectada.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        if(!webcam.isOpen())
        {
            webcam.setViewSize(new Dimension(320,240));
            webcam.open();
        } 
                
        
        BufferedImage bimage = webcam.getImage();
        Image imagem;
        imagem = SwingFXUtils.toFXImage(bimage, null);
        
        PESSOASimg.setImage(imagem);
        PESSOASimg.setId("0");
        
        webcam.close();
    }
    
    @FXML
    private void PESSOASclkTabelaVeiculos(MouseEvent event) {
        if(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem() != null)
        {
            PESSOASbtAlterarVeiculo.setDisable(false);
            PESSOASbtExcluirVeiculo.setDisable(false);
        }
    }
    
    @FXML
    private void PESSOASclkAdicionarVeiculo(ActionEvent event) {
        VEICULOSbtConfirmar.getTooltip().setText("Adicionar veículo");
        
        apTabelaPessoas.setVisible(false);
        apDependentes.setVisible(false);
        apLotes.setVisible(false);
        apVeiculos.setVisible(true);
        apDadosPessoas.setDisable(true);
        
        //carregando comboboxs
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCategoria, "categoriaveiculo", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCor, "cor", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbMarca, "marca", "");
        VEICULOScbCategoria.requestFocus();
    }

    @FXML
    private void PESSOASclkAlterarVeiculo(ActionEvent event) {
        VEICULOSbtConfirmar.getTooltip().setText("Alterar veículo");
        
        apTabelaPessoas.setVisible(false);
        apDependentes.setVisible(false);
        apLotes.setVisible(false);
        apVeiculos.setVisible(true);
        apDadosPessoas.setDisable(true);
        
        //carregando comboboxs
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCategoria, "categoriaveiculo", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCor, "cor", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbMarca, "marca", "");
        VEICULOScbCategoria.requestFocus();
        //carrega dados do veiculo selecionado e pessoa
        VEICULOScbCategoria.getSelectionModel().select(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem().getCategoria().toString());
        VEICULOScbCor.getSelectionModel().select(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem().getCor().toString());
        VEICULOScbMarca.getSelectionModel().select(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem().getMarca().toString());
        VEICULOStfModelo.setText(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem().getModelo());
        VEICULOStfPlaca.setText(PESSOAStabelaVeiculos.getSelectionModel().getSelectedItem().getPlaca());
    }

    @FXML
    private void PESSOASclkExcluirVeiculo(ActionEvent event) {
        ctrGerenciarPessoas.ExcluirVeiculo(PESSOAStabelaVeiculos);
        PESSOAStabelaVeiculos.getSelectionModel().clearSelection();
        PESSOASbtAlterarVeiculo.setDisable(true);
        PESSOASbtExcluirVeiculo.setDisable(true);
        PESSOAStabelaVeiculos.requestFocus(); 
    }
    @FXML
    private void PESSOASclkTabelaDependentes(MouseEvent event) {
        if(PESSOAStabelaDependentes.getSelectionModel().getSelectedItem() != null)
        {
            PESSOASbtAlterarDependente.setDisable(false);
            PESSOASbtExcluirDependente.setDisable(false);
        }
    }

    @FXML
    private void PESSOASclkAdicionarDependente(ActionEvent event) {
        DEPENDENTESbtConfirmar.getTooltip().setText("Adicionar dependente");
        apTabelaPessoas.setVisible(false);
        apVeiculos.setVisible(false);
        apLotes.setVisible(false);
        apDependentes.setVisible(true);
        apDadosPessoas.setDisable(true);
        
        DEPENDENTESrbNao.setSelected(true);
        DEPENDENTEStfNome.requestFocus();
    }

    @FXML
    private void PESSOASclkAlterarDependente(ActionEvent event) {
        DEPENDENTESbtConfirmar.getTooltip().setText("Alterar dependente");
        
        apTabelaPessoas.setVisible(false);
        apDependentes.setVisible(true);
        apLotes.setVisible(false);
        apVeiculos.setVisible(false);
        apDadosPessoas.setDisable(true);        
        
        DEPENDENTEStfNome.requestFocus();
        DEPENDENTEStfNome.setText(PESSOAStabelaDependentes.getSelectionModel().getSelectedItem().getNome());        
        DEPENDENTEStfDescricao.setText(((Dependente)PESSOAStabelaDependentes.getSelectionModel().getSelectedItem()).getDescricao());
        
        if(((Dependente)PESSOAStabelaDependentes.getSelectionModel().getSelectedItem()).getCondutor().equals("Sim"))
            DEPENDENTESrbSim.setSelected(true);
        else
            DEPENDENTESrbNao.setSelected(true);
    }

    @FXML
    private void PESSOASclkExcluirDependente(ActionEvent event) {
        ctrGerenciarPessoas.ExcluirDependente(PESSOAStabelaDependentes);
        PESSOAStabelaDependentes.getSelectionModel().clearSelection();
        PESSOASbtAlterarDependente.setDisable(true);
        PESSOASbtExcluirDependente.setDisable(true);
        PESSOAStabelaDependentes.requestFocus();
    }
    
     @FXML
    private void PESSOASclkTabelaLotes(MouseEvent event) {
        if(PESSOAStabelaLotes.getSelectionModel().getSelectedItem() != null)
        {
            PESSOASbtAlterarLote.setDisable(false);
            PESSOASbtExcluirLote.setDisable(false);
        }
    }    

    @FXML
    private void PESSOASclkAdicionarLote(ActionEvent event) {
        LOTESbtConfirmar.getTooltip().setText("Adicionar lote");
        apTabelaPessoas.setVisible(false);
        apVeiculos.setVisible(false);
        apDependentes.setVisible(false);
        apLotes.setVisible(true);
        apDadosPessoas.setDisable(true);
        
        LOTESrbNao.setSelected(true);
        LOTEStfNumero.requestFocus();
    }

    @FXML
    private void PESSOASclkAlterarLote(ActionEvent event) {
        LOTESbtConfirmar.getTooltip().setText("Alterar lote");
        
        apTabelaPessoas.setVisible(false);
        apDependentes.setVisible(false);
        apLotes.setVisible(true);
        apVeiculos.setVisible(false);
        apDadosPessoas.setDisable(true);        
        
        LOTEStfNumero.requestFocus();
        LOTEStfNumero.setText(PESSOAStabelaLotes.getSelectionModel().getSelectedItem().getNumero()+"");        
        LOTEStfQuadra.setText(PESSOAStabelaLotes.getSelectionModel().getSelectedItem().getQuadra()+""); 
        LOTEStfDescricao.setText(PESSOAStabelaLotes.getSelectionModel().getSelectedItem().getDescricao()); 
        
        if(PESSOAStabelaLotes.getSelectionModel().getSelectedItem().getResidencia().equals("Sim"))
            LOTESrbSim.setSelected(true);
        else
            LOTESrbNao.setSelected(true);
    }

    @FXML
    private void PESSOASclkExcluirLote(ActionEvent event) {
        ctrGerenciarPessoas.ExcluirLote(PESSOAStabelaLotes);
        PESSOAStabelaLotes.getSelectionModel().clearSelection();
        PESSOASbtAlterarLote.setDisable(true);
        PESSOASbtExcluirLote.setDisable(true);
        PESSOAStabelaLotes.requestFocus();        
    }
    
    @FXML
    private void VEICULOSclkCor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Configurar Cores");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaGerenciarOpcoes = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("GerenciarOpcoes.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        //carregando comboboxs
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCor, "cor", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCor, "cor", "");
        //VEICULOScbCor.requestFocus();
    }

    @FXML
    private void VEICULOSclkMarca(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Configurar Marcas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaGerenciarOpcoes = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("GerenciarOpcoes.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        //carregando comboboxs
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbMarca, "marca", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbMarca, "marca", "");
        //VEICULOScbMarca.requestFocus();
    }
    
    
    
    
    @FXML
    private void VEICULOSclkCategoria(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Configurar Categorias");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaGerenciarOpcoes = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("GerenciarOpcoes.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();//carregando comboboxs
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbCategoria, "categoriaveiculo", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCategoria, "categoriaveiculo", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOScbFiltrarCategoria, "categoriaveiculo", "");
        //VEICULOScbCategoria.requestFocus();
        
    }
    
    @FXML
    private void VEICULOSclkCancelar(ActionEvent event) {
        apTabelaPessoas.setVisible(true);
        apVeiculos.setVisible(false);
        apDadosPessoas.setDisable(false);
        estadoOriginalTelaAux();
        PESSOAStabelaVeiculos.requestFocus();
    }

    @FXML
    private void VEICULOSclkConfirmar(ActionEvent event) {
        //VALIDAÇÃO
        boolean erro = false;
        
        if(VEICULOStfPlaca.getText().equals("") || VEICULOStfPlaca.getLength() != 8)
        {
            erro = true;
            VEICULOStfPlaca.requestFocus();
            VEICULOSlbPlaca.setTextFill(Color.RED);
        }
        else
            VEICULOSlbPlaca.setTextFill(Color.BLACK);
            
        if(VEICULOScbCor.getValue() == null)
        {
            erro = true;
            VEICULOScbCor.requestFocus();
            VEICULOSlbCor.setTextFill(Color.RED);
        }
        else
            VEICULOSlbCor.setTextFill(Color.BLACK);
        
        if(VEICULOStfModelo.getText().equals(""))
        {
            erro = true;
            VEICULOStfModelo.requestFocus();
            VEICULOSlbModelo.setTextFill(Color.RED);
        }
        else
            VEICULOSlbModelo.setTextFill(Color.BLACK);
        
        if(VEICULOScbMarca.getValue() == null)
        {
            erro = true;
            VEICULOScbMarca.requestFocus();
            VEICULOSlbMarca.setTextFill(Color.RED);
        }
        else
            VEICULOSlbMarca.setTextFill(Color.BLACK);
        
        if(VEICULOScbCategoria.getValue() == null)
        {
            erro = true;
            VEICULOScbCategoria.requestFocus();
            VEICULOSlbCategoria.setTextFill(Color.RED);
        }
        else
            VEICULOSlbCategoria.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        //carrega novo veiculo na tabela de veiculos no cadastro da pessoa
        if(VEICULOSbtConfirmar.getTooltip().getText().contains("Alterar"))
            ctrGerenciarPessoas.AlterarVeiculo(PESSOAStabelaVeiculos, VEICULOScbCategoria.getValue(), VEICULOScbMarca.getValue(), VEICULOStfModelo.getText(), VEICULOScbCor.getValue(), VEICULOStfPlaca.getText());
        else
            ctrGerenciarPessoas.AdicionarVeiculo(PESSOAStabelaVeiculos, VEICULOScbCategoria.getValue(), VEICULOScbMarca.getValue(), VEICULOStfModelo.getText(), VEICULOScbCor.getValue(), VEICULOStfPlaca.getText());
        
        apTabelaPessoas.setVisible(true);
        apVeiculos.setVisible(false);
        apDadosPessoas.setDisable(false);
        //limpar campos
        estadoOriginalTelaAux();
        PESSOAStabelaVeiculos.getSelectionModel().clearSelection();
        PESSOASbtAlterarVeiculo.setDisable(true);
        PESSOASbtExcluirVeiculo.setDisable(true);
        PESSOAStabelaVeiculos.requestFocus();
        
    }
    
    

    @FXML
    private void DEPENDENTESclkCancelar(ActionEvent event) {
        apTabelaPessoas.setVisible(true);
        apDependentes.setVisible(false);
        apDadosPessoas.setDisable(false);
        estadoOriginalTelaAux();
        PESSOAStabelaDependentes.requestFocus();
    }

    @FXML
    private void DEPENDENTESclkConfirmar(ActionEvent event) {
        //VALIDAÇÃO
        boolean erro = false;
            
        if(DEPENDENTEStfDescricao.getText().equals(""))
        {
            erro = true;
            DEPENDENTEStfDescricao.requestFocus();
            DEPENDENTESlbDescricao.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbDescricao.setTextFill(Color.BLACK);
        
        if(DEPENDENTEStfNome.getText().equals(""))
        {
            erro = true;
            DEPENDENTEStfNome.requestFocus();
            DEPENDENTESlbNome.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbNome.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        //carrega novo veiculo na tabela de veiculos no cadastro da pessoa
        String condutor = "Não";
        if(DEPENDENTESrbSim.isSelected())
            condutor = "Sim";
            
        if(DEPENDENTESbtConfirmar.getTooltip().getText().contains("Alterar"))
            ctrGerenciarPessoas.AlterarDependente(PESSOAStabelaDependentes, DEPENDENTEStfNome.getText(), DEPENDENTEStfDescricao.getText(), condutor);
        else
            ctrGerenciarPessoas.AdicionarDependente(PESSOAStabelaDependentes, DEPENDENTEStfNome.getText(), DEPENDENTEStfDescricao.getText(), condutor);
        
        apTabelaPessoas.setVisible(true);
        apDependentes.setVisible(false);
        apDadosPessoas.setDisable(false);
        estadoOriginalTelaAux();
        PESSOAStabelaDependentes.getSelectionModel().clearSelection();
        PESSOASbtAlterarDependente.setDisable(true);
        PESSOASbtExcluirDependente.setDisable(true);
        PESSOAStabelaDependentes.requestFocus();
    }  

    @FXML
    private void LOTESclkCancelar(ActionEvent event) {
        apTabelaPessoas.setVisible(true);
        apLotes.setVisible(false);
        apDadosPessoas.setDisable(false);
        estadoOriginalTelaAux();
        PESSOAStabelaLotes.requestFocus();
    }

    @FXML
    private void LOTESclkConfirmar(ActionEvent event) {
        //VALIDAÇÃO
        boolean erro = false;
            
        if(LOTEStfDescricao.getText().equals(""))
        {
            erro = true;
            LOTEStfDescricao.requestFocus();
            LOTESlbDescricao.setTextFill(Color.RED);
        }
        else
            LOTESlbDescricao.setTextFill(Color.BLACK);
        
        if(LOTEStfQuadra.getText().equals("") || !Funcoes.isInt(LOTEStfQuadra.getText()))
        {
            erro = true;
            LOTEStfQuadra.requestFocus();
            LOTESlbQuadra.setTextFill(Color.RED);
        }
        else
            LOTESlbQuadra.setTextFill(Color.BLACK);
        
        if(LOTEStfNumero.getText().equals("") || !Funcoes.isInt(LOTEStfNumero.getText()))
        {
            erro = true;
            LOTEStfNumero.requestFocus();
            LOTESlbNumero.setTextFill(Color.RED);
        }
        else
            LOTESlbNumero.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        //carrega novo veiculo na tabela de veiculos no cadastro da pessoa
        String residencia = "Não";
        if(LOTESrbSim.isSelected())
            residencia = "Sim";
           
        if(LOTESbtConfirmar.getTooltip().getText().contains("Alterar"))
            ctrGerenciarPessoas.AlterarLote(PESSOAStabelaLotes, Integer.parseInt(LOTEStfNumero.getText()), Integer.parseInt(LOTEStfQuadra.getText()), LOTEStfDescricao.getText(), residencia);
        else
            ctrGerenciarPessoas.AdicionarLote(PESSOAStabelaLotes, Integer.parseInt(LOTEStfNumero.getText()), Integer.parseInt(LOTEStfQuadra.getText()), LOTEStfDescricao.getText(), residencia);
        
        apTabelaPessoas.setVisible(true);
        apLotes.setVisible(false);
        apDadosPessoas.setDisable(false);
        estadoOriginalTelaAux();
        PESSOAStabelaLotes.getSelectionModel().clearSelection();
        PESSOASbtAlterarLote.setDisable(true);
        PESSOASbtExcluirLote.setDisable(true);
        PESSOAStabelaLotes.requestFocus();
    }

    @FXML
    private void PESSOASclkTabUsuario(Event event) {
        if(PESSOAScbCategoria.getValue().equals("Visitante")) //não deixa mudar
        {
            SingleSelectionModel<Tab> selectionModel = TabPane.getSelectionModel();
            selectionModel.select(0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erro");
            alert.setContentText("Não é permitido cadastrar usuário para um visitante.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
           
        }
        else
        {
            aux_dadosusuario.setMinHeight(423);
            TabPane.setMinHeight(423);
        }
            
    }

    @FXML
    private void PESSOASclkTabUDadosPessoais(Event event) {
        int op = 0;
        
        if(PESSOASrbLivreAcesso.isSelected())
           op = 1;
        if(PESSOASrbMedianteAutorizacao.isSelected())
           op = 2;
        
        if(PESSOAScbCategoria.getValue() != null)
           ExibirCamposPorCategoria(PESSOAScbCategoria.getValue());
        
        if(op == 1)
           PESSOASrbLivreAcesso.setSelected(true);
        if(op == 2)
           PESSOASrbMedianteAutorizacao.setSelected(true);
    }

    @FXML
    private void PESSOASclkVerSenha(MouseEvent event) {
        if(PESSOASlbVerSenha.isVisible())
        {
           PESSOASlbVerSenha.setVisible(false);
           PESSOASlbVerSenha.setText("");
        }  
        else //invisivel
        {
           PESSOASlbVerSenha.setVisible(true);
           PESSOASlbVerSenha.setText(USUARIOtfSenha.getText());
        } 
    }

    @FXML
    private void PESSOASkpSenha(KeyEvent event) {
        PESSOASlbVerSenha.setVisible(false);
        PESSOASlbVerSenha.setText("");
    }
    
    /************* FIM EVENTOS TELA CADASTRO DE PESSOAS *****************\
     */
    

    @FXML
    private void DEPENDENTESkpPesquisar(KeyEvent event) {
        //implementar se o Usuário apertar ENTER
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           PesquisarDependente();
    }

    @FXML
    private void DEPENDENTESclkIr(ActionEvent event) {
        PesquisarDependente();
    }

    @FXML
    private void DEPENDENTESclkTabela(MouseEvent event) {
        if(DEPENDENTEStabela.getSelectionModel().getSelectedItem() != null)
        {
            DEPENDENTESbtAlterar.setDisable(false);
            DEPENDENTESbtApagar.setDisable(false);
        }
        
    }
    
    private void NovoDependente()
    {
        DEPENDENTESlbCamposObrigatorios.setVisible(true);
        DEPENDENTESTelabtConfirmar.getTooltip().setText("Cadastrar dependente");
        apTabelaDependentes.setDisable(true);
        apDadosDependentes.setDisable(false);
        DEPENDENTESTelabtCancelar.setDisable(false);
        DEPENDENTESTelabtConfirmar.setDisable(true);
        DEPENDENTEStfCod.setDisable(true);
        DEPENDENTEScbCidade.setPromptText("Selecione o UF");
        DEPENDENTEScbCategoria.setPromptText("Escolha");
        DEPENDENTEScbCategoria.setVisible(true);
        DEPENDENTESlbCategoria.setVisible(true);
        aux_dadosdependentes.setMinHeight(423);
        //TabPane.setMinHeight(423);
        DEPENDENTEStfInterfone.setDisable(true);
        ctrGerenciarPessoas.Novo();
        codigo_supervisor = 0;
        //DEPENDENTEScbUF.getSelectionModel().select("PR");
        //DEPENDENTEScbCidade.getSelectionModel().select("Colorado");
        DEPENDENTEScbCategoria.requestFocus();
    }

    @FXML
    private void DEPENDENTESclkNovo(ActionEvent event) {
        NovoDependente();
    }

    @FXML
    private void DEPENDENTESclkAlterar(ActionEvent event) {
        DEPENDENTESlbCamposObrigatorios.setVisible(true);
        DEPENDENTESTelabtConfirmar.getTooltip().setText("Alterar dependente");
        CarregarDadosDependente();
        apTabelaDependentes.setDisable(true);
        apDadosDependentes.setDisable(false);
        DEPENDENTESTelabtCancelar.setDisable(false);
        DEPENDENTESTelabtConfirmar.setDisable(false); 
        DEPENDENTEStfCod.setDisable(true);
        DEPENDENTEScbCategoria.setDisable(true);
        DEPENDENTEStfInterfone.setDisable(true);
        DEPENDENTESapAcesso.requestFocus();
    }

    @FXML
    private void DEPENDENTESclkApagar(ActionEvent event) {
        DEPENDENTESlbCamposObrigatorios.setVisible(false);
        DEPENDENTEScbCidade.setPromptText("");
        DEPENDENTESTelabtConfirmar.getTooltip().setText("Desativar dependente");
        CarregarDadosDependente();
        apTabelaDependentes.setDisable(true);
        apDadosDependentes.setDisable(false);
        
        DEPENDENTESModoExibicao(true);
        
        DEPENDENTESTelabtCancelar.setDisable(false);
        DEPENDENTESTelabtConfirmar.setDisable(false);
        
    }
    
    private void ExibirCamposPorCategoriaDependente(String categoria)
    {
        DEPENDENTESTelalbDescricao.setVisible(true);
        ajudaDEPENDENTESDescricao.setVisible(true);
        DEPENDENTESTelalbDescricao.setTextFill(Color.BLACK);
        ObservableList <Node> componentes = aux_dadosdependentes.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setVisible(true);
            if(n instanceof ComboBox)
                ((ComboBox)n).setVisible(true);
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
                ((Label)n).setVisible(true);
            }
            if(n instanceof DatePicker)
                ((DatePicker)n).setVisible(true);
            if(n instanceof TableView)
                ((TableView)n).setVisible(true);
            if(n instanceof Button)
                ((Button)n).setVisible(true);
            if(n instanceof AnchorPane)
                ((AnchorPane)n).setVisible(true);
            
        }
        aux_dadosdependentes.setMinHeight(723);       
        
        if(categoria.equals("Visitante"))
        {
            if(!DEPENDENTESbtConfirmar.getTooltip().getText().contains("Alterar") && !DEPENDENTESrbNaoAutorizado.isSelected())
               DEPENDENTESrbMedianteAutorizacao.setSelected(true);
            DEPENDENTESlbInterfone.setVisible(false);
            DEPENDENTEStfInterfone.setVisible(false);            
        }
        else
        {
            if(!DEPENDENTESbtConfirmar.getTooltip().getText().contains("Alterar") && !DEPENDENTESrbNaoAutorizado.isSelected())
               DEPENDENTESrbLivreAcesso.setSelected(true);
        }
        
        DEPENDENTEStxPesquisarCPF.setVisible(false);
    }

    @FXML
    private void DEPENDENTESclkCategoria(ActionEvent event) {
        if(DEPENDENTEScbCategoria.getValue() != null)
        {
           DEPENDENTESlbCategoria.setLayoutX(21);
           DEPENDENTEScbCategoria.setLayoutX(21);
           DEPENDENTESlbCategoria.setLayoutY(245);
           DEPENDENTEScbCategoria.setLayoutY(265);
           ExibirCamposPorCategoriaDependente(DEPENDENTEScbCategoria.getValue());
           DEPENDENTESapAcesso.requestFocus();
           DEPENDENTESTelabtConfirmar.setDisable(false);
        }
    }

    @FXML
    private void DEPENDENTESclkCarregaIMG(ActionEvent event) {
        Image foto = null;
        FileChooser fc = new FileChooser();
        Node source = (Node) event.getSource();
        fc.setTitle("Selecione a Imagem");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Imagens (.gif, .jpg, .png)", "*.jpg", "*.png", "*.gif"));        
        
        File arq=fc.showOpenDialog(source.getScene().getWindow());
        if(arq!=null)
        {
            foto = new Image(arq.toURI().toString());
            DEPENDENTESimg.setImage(foto);
            DEPENDENTESimg.setId("0");
        }
    }

    @FXML
    private void DEPENDENTESclkApagaIMG(ActionEvent event) {
        java.net.URL url = getClass().getResource("imagens/usuario.png");
        DEPENDENTESimg.setImage(new Image(url.toString()));
        DEPENDENTESimg.setId("1");
    }

    @FXML
    private void DEPENDENTESclkCamera(MouseEvent event) {
        if(webcam == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Câmera não detectada.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        if(!webcam.isOpen())
        {
            webcam.setViewSize(new Dimension(320,240));
            webcam.open();
        } 
                
        
        BufferedImage bimage = webcam.getImage();
        Image imagem;
        imagem = SwingFXUtils.toFXImage(bimage, null);
        
        DEPENDENTESimg.setImage(imagem);
        DEPENDENTESimg.setId("0");
        
        webcam.close();
        
    }

    @FXML
    private void DEPENDENTESclkBuscarCep(ActionEvent event) {
        if(DEPENDENTEStfCEP.getText().equals(""))
        {
            DEPENDENTEStfCEP.requestFocus();
            DEPENDENTESlbCep.setTextFill(Color.RED);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Digite um CEP válido para buscar a localização do mesmo.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;  
        }
        DEPENDENTESlbCep.setTextFill(Color.BLACK);
        
        DEPENDENTEStfBairro.setText("");
        DEPENDENTEStfLogradouro.setText("");
        DEPENDENTEScbTipoLog.getSelectionModel().clearSelection();
        DEPENDENTEScbUF.getSelectionModel().clearSelection();
        DEPENDENTEScbCidade.getSelectionModel().clearSelection();
        
        String endereco = ctrGerenciarPessoas.consultaCep(DEPENDENTEStfCEP.getText(),"json", "261641425", "64669093", "177.131.35.1", "3128"); //com proxy
        //String endereco = consultaCep(tfCEP.getText(), "json"); //sem proxy
        
        //System.out.println(endereco);
        JSONObject jdados = new JSONObject(endereco);
        //System.out.println(obj.getString("cidade")); //cidade é o campo chave, retorna o nome da cidade
        DEPENDENTEStfBairro.setText(jdados.getString("bairro"));
        DEPENDENTEStfLogradouro.setText(jdados.getString("logradouro"));
        
        
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        int achou = 0;
        for(int i=0; achou == 0 && i<DEPENDENTEScbTipoLog.getItems().size();i++)
            if(DEPENDENTEScbTipoLog.getItems().get(i).equals(jdados.getString("tipo_logradouro"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               DEPENDENTEScbTipoLog.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        
        
        
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        achou = 0;
        for(int i=0; achou == 0 && i<DEPENDENTEScbUF.getItems().size();i++)
            if(DEPENDENTEScbUF.getItems().get(i).equals(jdados.getString("uf"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               DEPENDENTEScbUF.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        achou  = 0;
        for(int i=0; achou == 0 && i<DEPENDENTEScbCidade.getItems().size();i++)
            if(DEPENDENTEScbCidade.getItems().get(i).equals(jdados.getString("cidade"))) //achei o cara
            {
               //cbCidade.setValue(cbCidade.getItems().get(i));
               DEPENDENTEScbCidade.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        
        //if(achou == 0) //nao achou cep, cep inválido!
        
    }

    @FXML
    private void DEPENDENTESkpUF(KeyEvent event) {
        if(DEPENDENTEScbUF.getValue() != null)
        {
           DEPENDENTEScbCidade.setPromptText("Escolha a Cidade");
           DEPENDENTEScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbCidade, "cidade", "estado.est_sgl LIKE '"+DEPENDENTEScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(DEPENDENTEScbCidade);
        
    }

    @FXML
    private void DEPENDENTESclkUF(ActionEvent event) {
        if(DEPENDENTEScbUF.getValue() != null)
        {
           DEPENDENTEScbCidade.setPromptText("Escolha a Cidade");
           DEPENDENTEScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarPessoas.CarregarComboBox(DEPENDENTEScbCidade, "cidade", "estado.est_sgl LIKE '"+DEPENDENTEScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(DEPENDENTEScbCidade);
    }

    @FXML
    private void DEPENDENTESTelaclkCancelar(ActionEvent event) {
        estadoOriginalDependente();
    }

    @FXML
    private void DEPENDENTESTelaclkConfirmar(ActionEvent event) throws SQLException {
        if(DEPENDENTESTelabtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrGerenciarPessoas.getCtrpessoa().Excluir())
                {
                   Banco.con.Commit("Desativar dependente."); 
                   estadoOriginalDependente();
                   PesquisarDependente();
                }
                else
                   Banco.con.Rollback("Não foi possível realizar esta operação.");
            }
            return;
        }        

        boolean erro = false;

        
        if(DEPENDENTEStfSupervisor.getText().equals(""))
        {
            erro = true;
            DEPENDENTESbtSupervisor.requestFocus();
            DEPENDENTESlbSupervisor.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbSupervisor.setTextFill(Color.BLACK);

        if(!DEPENDENTEStfEmail.getText().equals("") && !Funcoes.isEmail(DEPENDENTEStfEmail.getText()))
        {
            erro = true;
            DEPENDENTEStfEmail.requestFocus();
            DEPENDENTESlbEmail.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbEmail.setTextFill(Color.BLACK);

        if(!DEPENDENTEStfTelfone2.getText().equals("") && !Funcoes.ValidaTelefone(DEPENDENTEStfTelfone2.getText()))
        {
            erro = true;
            DEPENDENTEStfTelfone2.requestFocus();
            DEPENDENTESlbTelefone2.setTextFill(Color.RED);

        }
        else
            DEPENDENTESlbTelefone2.setTextFill(Color.BLACK);
        if(!DEPENDENTEStfTelfone.getText().equals("") && !Funcoes.ValidaTelefone(DEPENDENTEStfTelfone.getText()))
        {
            erro = true;
            DEPENDENTEStfTelfone.requestFocus();
            DEPENDENTESlbTelefone.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbTelefone.setTextFill(Color.BLACK);
        /*if(DEPENDENTEScbCidade.getValue() == null)
        {
            erro = true;
            DEPENDENTEScbCidade.requestFocus();
            DEPENDENTESlbCidade.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbCidade.setTextFill(Color.BLACK);
        if(DEPENDENTEScbUF.getValue() == null)
        {
            erro = true;
            DEPENDENTEScbUF.requestFocus();
            DEPENDENTESlbUf.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbUf.setTextFill(Color.BLACK);*/
        /*if(DEPENDENTEStfBairro.getText().equals(""))
        {
            erro = true;
            DEPENDENTEStfBairro.requestFocus();
            DEPENDENTESlbBairro.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbBairro.setTextFill(Color.BLACK);*/
        if(!DEPENDENTEStfCEP.getText().equals("") && !Funcoes.ValidaCEP(DEPENDENTEStfCEP.getText()))
        {
            erro = true;
            DEPENDENTEStfCEP.requestFocus();
            DEPENDENTESlbCep.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbCep.setTextFill(Color.BLACK);
        if(!DEPENDENTEStfNum.getText().equals("") && !Funcoes.isInt(DEPENDENTEStfNum.getText()))
        {
            erro = true;
            DEPENDENTEStfNum.requestFocus();
            DEPENDENTESlbNumero.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbNumero.setTextFill(Color.BLACK);
        /*if(DEPENDENTEStfLogradouro.getText().equals(""))
        {
            erro = true;
            DEPENDENTEStfLogradouro.requestFocus();
            DEPENDENTESlbLog.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbLog.setTextFill(Color.BLACK);
        if(DEPENDENTEScbTipoLog.getSelectionModel().isEmpty())
        {
            erro = true;
            DEPENDENTEScbTipoLog.requestFocus();
            DEPENDENTESlbTipoLog.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbTipoLog.setTextFill(Color.BLACK);*/
        if(DEPENDENTESdpData.getValue() != null && DEPENDENTESdpData.getValue().isAfter(LocalDate.now())) 
        {   //dependente permite qualquer idade
            erro = true;
            DEPENDENTESdpData.requestFocus();
            DEPENDENTESlbData.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbData.setTextFill(Color.BLACK);
        
        if(!DEPENDENTEStfCPF.getText().equals("") && !Funcoes.ValidaCPF(DEPENDENTEStfCPF.getText()))
        {
            erro = true;
            DEPENDENTEStfCPF.requestFocus();
            DEPENDENTESlbCpf.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbCpf.setTextFill(Color.BLACK);
        
        if(DEPENDENTEScbCategoria.getSelectionModel().isEmpty())
        {
            erro = true;
            DEPENDENTEScbCategoria.requestFocus();
            DEPENDENTESlbCategoria.setTextFill(Color.RED);
        }
        else
            DEPENDENTESlbCategoria.setTextFill(Color.BLACK);
        
        if(DEPENDENTESTelatfDescricao.getText().equals(""))
        {
            erro = true;
            DEPENDENTESTelatfDescricao.requestFocus();
            DEPENDENTESTelalbDescricao.setTextFill(Color.RED);
        }
        else
            DEPENDENTESTelalbDescricao.setTextFill(Color.BLACK);
        
        if(DEPENDENTESTelatfNome.getText().equals(""))
        {
            erro = true;
            DEPENDENTESTelatfNome.requestFocus();
            DEPENDENTESTelalbNome.setTextFill(Color.RED);
        }
        else
            DEPENDENTESTelalbNome.setTextFill(Color.BLACK);
        
        

        if(erro)
            return;


        String condutor = "Não";
        if(DEPENDENTESckbCondutor.isSelected())
            condutor = "Sim";

        String acesso = "", ultima = "";
        if(DEPENDENTESrbLivreAcesso.isSelected())
           acesso = "Livre Acesso";
        if(DEPENDENTESrbMedianteAutorizacao.isSelected())
           acesso = "Mediante Autorização";
        if(DEPENDENTESrbNaoAutorizado.isSelected())
           acesso = "Não Autorizado";
        
        if(DEPENDENTESrbEntrada.isSelected())
           ultima = "E";
        else
           ultima = "S";
        
        Banco.con.IniciarTransacao();
        if(DEPENDENTESTelabtConfirmar.getTooltip().getText().contains("Cadastrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrGerenciarPessoas.getCtrpessoa().Salvar(DEPENDENTESTelatfNome.getText(), DEPENDENTEScbCategoria.getValue(), DEPENDENTEStfCPF.getText(), DEPENDENTEStfBairro.getText(), DEPENDENTEScbTipoLog.getValue(), DEPENDENTEStfLogradouro.getText(), DEPENDENTEStfNum.getText(), DEPENDENTEStfTelfone.getText(), DEPENDENTEStfEmail.getText(), DEPENDENTEStfCEP.getText(), DEPENDENTEScbCidade.getValue(), DEPENDENTEScbUF.getValue(), DEPENDENTESdpData.getValue(), DEPENDENTEStfTelfone2.getText(), DEPENDENTEStfInterfone.getText(), null, null, "", condutor, "", "", 0, codigo_supervisor, DEPENDENTESTelatfDescricao.getText(), ultima, acesso);
        }
        else //Alterando
        {                
            erro = !ctrGerenciarPessoas.getCtrpessoa().Alterar(Integer.parseInt(DEPENDENTEStfCod.getText()), DEPENDENTESTelatfNome.getText(), DEPENDENTEScbCategoria.getValue(), DEPENDENTEStfCPF.getText(), DEPENDENTEStfBairro.getText(), DEPENDENTEScbTipoLog.getValue(), DEPENDENTEStfLogradouro.getText(), DEPENDENTEStfNum.getText(), DEPENDENTEStfTelfone.getText(), DEPENDENTEStfEmail.getText(), DEPENDENTEStfCEP.getText(), DEPENDENTEScbCidade.getValue(),DEPENDENTEScbUF.getValue(), DEPENDENTESdpData.getValue(), DEPENDENTEStfTelfone2.getText(), DEPENDENTEStfInterfone.getText(),  null, null, "", condutor, "", "", 0, codigo_supervisor, DEPENDENTESTelatfDescricao.getText(), ultima, acesso);
        }

        if(!DEPENDENTESimg.getId().equals("1"))
        {            
            if(!ctrGerenciarPessoas.getCtrpessoa().SalvarImagem(SwingFXUtils.fromFXImage(DEPENDENTESimg.getImage(), null)))
            {
                Banco.con.Rollback("Não foi possível realizar esta operação.");
                return;
            }
        }
        else
        {
            if(!ctrGerenciarPessoas.getCtrpessoa().SalvarImagem(null))
            {
                Banco.con.Rollback("Não foi possível realizar esta operação.");
                return;
            }
        }

        if(!erro)
        {     
           Banco.con.Commit("Cadastrar/alterar dependente.");
           estadoOriginalDependente();
           PesquisarDependente();
        }
        else
           Banco.con.Rollback("Não foi possível realizar esta operação.");
        
        
    }
    
    private boolean InformarSaldoInicial() throws IOException
    {
        Stage stage = new Stage();
        stage.setTitle("Saldo Inicial do Caixa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaSaldoInicial = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SaldoInicialCaixa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        return SaldoInicialCaixaController.status;
    }

    @FXML
    private void DEPENDENTESclkSupervisor(ActionEvent event) throws IOException {
        SelecionarPessoaController.pessoa = 0;
        Stage stage = new Stage();
        stage.setTitle("Selecionar Responsável");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaSelecionarPessoa = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
        { //retornou alguma pessoa
          codigo_supervisor = SelecionarPessoaController.pessoa;
          DEPENDENTEStfSupervisor.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(codigo_supervisor).getNome());  
          String categoria = ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(codigo_supervisor).getCategoria();
          DEPENDENTEScbCategoria.setValue(categoria);
          DEPENDENTEScbCategoria.setDisable(true);
          if(categoria.equals("Visitante"))             
            DEPENDENTEStfInterfone.setText(""); 
          else //Associado
             DEPENDENTEStfInterfone.setText(((Associado)ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(codigo_supervisor)).getInterfone()); 
          
        }
    }  
    /************* FIM EVENTOS TELA CADASTRO DE DEPENDENTES *****************\
    * 
    */

    @FXML
    private void VEICULOSkpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
        {
           VEICULOSbtAlterar.setDisable(true);
           VEICULOSbtApagar.setDisable(true);
           ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
        }
    }

    @FXML
    private void VEICULOSclkIr(ActionEvent event) {
        VEICULOSbtAlterar.setDisable(true);
        VEICULOSbtApagar.setDisable(true);
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
    }

    @FXML
    private void VEICULOSclkTabela(MouseEvent event) {
        if(VEICULOStabela.getSelectionModel().getSelectedItem() != null)
        {
            VEICULOSbtAlterar.setDisable(false);
            VEICULOSbtApagar.setDisable(false);
        }
        
    }
    
    private void NovoVeiculo()
    {
        VEICULOSlbCamposObrigatorios.setVisible(true);
        VEICULOSTelabtConfirmar.getTooltip().setText("Cadastrar veículo");
        apTabelaVeiculos.setDisable(true);
        apDadosVeiculos.setDisable(false);
        VEICULOSTelabtCancelar.setDisable(false);
        VEICULOSTelabtConfirmar.setDisable(false);
        VEICULOStfCod.setDisable(true);
        ctrGerenciarVeiculos.Novo(); 
        codigo_proprietario = 0;
        VEICULOScbCategoria.requestFocus();
    }

    @FXML
    private void VEICULOSclkNovo(ActionEvent event) {
        NovoVeiculo();
    }
    
    private void CarregarDadosVeiculo()
    {
        if(VEICULOStabela.getSelectionModel().getSelectedItem().getUltimamov().equals("E"))
            VEICULOSrbEntrada.setSelected(true);
        else
            VEICULOSrbSaida.setSelected(true);
        if(VEICULOStabela.getSelectionModel().getSelectedItem().getAutorizado().equals("S"))
            VEICULOSrbSim.setSelected(true);
        else
            VEICULOSrbNao.setSelected(true);
        
        VEICULOStfCod.setText(VEICULOStabela.getSelectionModel().getSelectedItem().getCod() + "");
        VEICULOSTelatfModelo.setText((VEICULOStabela.getSelectionModel().getSelectedItem()).getModelo());
        VEICULOSTelatfPlaca.setText((VEICULOStabela.getSelectionModel().getSelectedItem()).getPlaca());
         
        VEICULOStfProprietario.setText((VEICULOStabela.getSelectionModel().getSelectedItem()).getPessoa().getNome());
        codigo_proprietario = VEICULOStabela.getSelectionModel().getSelectedItem().getPessoa().getCod();
        
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCategoria, "categoriaveiculo", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbCor, "cor", "");
        ctrGerenciarVeiculos.CarregarComboBox(VEICULOSTelacbMarca, "marca", "");
         
        VEICULOSTelacbCategoria.getSelectionModel().select((VEICULOStabela.getSelectionModel().getSelectedItem()).getCategoria().toString());
        VEICULOSTelacbMarca.getSelectionModel().select((VEICULOStabela.getSelectionModel().getSelectedItem()).getMarca().toString());
        VEICULOSTelacbCor.getSelectionModel().select((VEICULOStabela.getSelectionModel().getSelectedItem()).getCor().toString());
        
        ctrGerenciarVeiculos.Novo();
        ctrGerenciarVeiculos.getCtrveiculo().getVeiculo().setCod(VEICULOStabela.getSelectionModel().getSelectedItem().getCod());
    }

    @FXML
    private void VEICULOSclkAlterar(ActionEvent event) {
        VEICULOSlbCamposObrigatorios.setVisible(true);
        VEICULOSTelabtConfirmar.getTooltip().setText("Alterar veículo");
        CarregarDadosVeiculo();
        apTabelaVeiculos.setDisable(true);
        apDadosVeiculos.setDisable(false);
        VEICULOSTelabtCancelar.setDisable(false);
        VEICULOSTelabtConfirmar.setDisable(false); 
        VEICULOStfCod.setDisable(true);
    }

    @FXML
    private void VEICULOSclkApagar(ActionEvent event) {
        VEICULOSlbCamposObrigatorios.setVisible(false);
        VEICULOSTelabtConfirmar.getTooltip().setText("Desativar veículo");
        CarregarDadosVeiculo();
        apTabelaVeiculos.setDisable(true);
        apDadosVeiculos.setDisable(false);
        
        VEICULOSModoExibicao(true);
        
        VEICULOSTelabtCancelar.setDisable(false);
        VEICULOSTelabtConfirmar.setDisable(false);
    }
    
    private void estadoOriginalVeiculo()
    {  
        VEICULOSlbCamposObrigatorios.setVisible(false);
        VEICULOSrbEntrada.setSelected(true);
        VEICULOSrbSim.setSelected(true);
        apTabelaVeiculos.setDisable(false);
        apDadosVeiculos.setDisable(true);
        VEICULOSTelabtConfirmar.setDisable(true);
        VEICULOSTelabtCancelar.setDisable(true);
        VEICULOSbtApagar.setDisable(true);
        VEICULOSbtAlterar.setDisable(true);
        VEICULOSbtNovo.setDisable(false);
        
        //---------------Limpa Os Textos da Tela---------------------------
        ObservableList <Node> componentes = aux_dadosveiculos.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
            {
                ((TextInputControl)n).setText("");
                ((TextInputControl)n).setDisable(false);
            }
            if(n instanceof ComboBox)
            {
                ((ComboBox)n).getSelectionModel().clearSelection();
                ((ComboBox)n).setDisable(false);
            }
            if(n instanceof Label)
            {
                ((Label)n).setTextFill(Color.BLACK);
            }
            
            if(n instanceof Button && !n.equals(VEICULOSTelabtConfirmar) && !n.equals(VEICULOSTelabtCancelar))
            {
                ((Button)n).setDisable(false);
            }
        }
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
        apVeiculosTela.requestFocus();
        VEICULOSModoExibicao(false);
    }

    @FXML
    private void VEICULOSclkProprietario(ActionEvent event) throws IOException {
        SelecionarPessoaController.pessoa = 0;
        Stage stage = new Stage();
        stage.setTitle("Selecionar Proprietário");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaSelecionarPessoa = stage;
        RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
        { //retornou alguma pessoa
          codigo_proprietario = SelecionarPessoaController.pessoa;
          VEICULOStfProprietario.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(codigo_proprietario).getNome());  
        }
    }

    @FXML
    private void VEICULOSTelaclkCancelar(ActionEvent event) {
        estadoOriginalVeiculo();
        
    }

    @FXML
    private void VEICULOSTelaclkConfirmar(ActionEvent event) throws SQLException {
        if(VEICULOSTelabtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrGerenciarVeiculos.getCtrveiculo().Excluir())
                {
                   Banco.con.Commit("Desativar veículo.");
                   estadoOriginalVeiculo();
                   ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
                }
                else
                   Banco.con.Rollback("Não foi possível realizar esta operação.");
            }
            return;
        }
        
          
        boolean erro = false;

        if(VEICULOStfProprietario.getText().equals(""))
        {
            erro = true;
            VEICULOSbtProprietario.requestFocus();
            VEICULOSlbProprietario.setTextFill(Color.RED);
        }
        else
            VEICULOSlbProprietario.setTextFill(Color.BLACK);

        if(VEICULOSTelatfPlaca.getText().equals("")  || VEICULOSTelatfPlaca.getLength() != 8)
        {
            erro = true;
            VEICULOSTelatfPlaca.requestFocus();
            VEICULOSTelalbPlaca.setTextFill(Color.RED);
        }
        else
            VEICULOSTelalbPlaca.setTextFill(Color.BLACK);

        if(VEICULOSTelacbCor.getValue() == null)
        {
            erro = true;
            VEICULOSTelacbCor.requestFocus();
            VEICULOSTelalbCor.setTextFill(Color.RED);
        }
        else
            VEICULOSTelalbCor.setTextFill(Color.BLACK);

        if(VEICULOSTelatfModelo.getText().equals(""))
        {
            erro = true;
            VEICULOSTelatfModelo.requestFocus();
            VEICULOSTelalbModelo.setTextFill(Color.RED);
        }
        else
            VEICULOSTelalbModelo.setTextFill(Color.BLACK);

        if(VEICULOSTelacbMarca.getValue() == null)
        {
            erro = true;
            VEICULOSTelacbMarca.requestFocus();
            VEICULOSTelalbMarca.setTextFill(Color.RED);
        }
        else
            VEICULOSTelalbMarca.setTextFill(Color.BLACK);

        if(VEICULOSTelacbCategoria.getValue() == null)
        {
            erro = true;
            VEICULOSTelacbCategoria.requestFocus();
            VEICULOSTelalbCategoria.setTextFill(Color.RED);
        }
        else
            VEICULOSTelalbCategoria.setTextFill(Color.BLACK);

        if(erro)
            return;

        String ultima = "", autorizado = "";
        if(VEICULOSrbEntrada.isSelected())
           ultima = "E";
        else
           ultima = "S";
        
        if(VEICULOSrbSim.isSelected())
           autorizado = "S";
        else
           autorizado = "N";

        Banco.con.IniciarTransacao();
        if(VEICULOSTelabtConfirmar.getTooltip().getText().contains("Cadastrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrGerenciarVeiculos.getCtrveiculo().Salvar(VEICULOSTelacbCategoria.getValue(), VEICULOSTelacbMarca.getValue(), VEICULOSTelatfModelo.getText(), VEICULOSTelacbCor.getValue(), VEICULOSTelatfPlaca.getText(), codigo_proprietario, ultima, autorizado);
        }
        else //Alterando
        {                
            erro = !ctrGerenciarVeiculos.getCtrveiculo().Alterar(Integer.parseInt(VEICULOStfCod.getText()), VEICULOSTelacbCategoria.getValue(), VEICULOSTelacbMarca.getValue(), VEICULOSTelatfModelo.getText(), VEICULOSTelacbCor.getValue(), VEICULOSTelatfPlaca.getText(), codigo_proprietario, ultima, autorizado);
        }

        if(!erro)
        {     
           Banco.con.Commit("Cadastrar/alterar veículo.");
           estadoOriginalVeiculo();
           ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
        }
        else
           Banco.con.Rollback("Não foi possível realizar esta operação.");


        
        
    }


   /************* FIM EVENTOS TELA CADASTRO DE VEICULOS *****************\
    * 
    */
    
    @FXML
    private void clkRealizarMovimentacao(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Realizar Movimentação");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRealizarMovimentacao = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarMovimentacao.fxml")));
        stage.setScene(scene);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        PesquisarEntrada();
        PesquisarSaida();
        Pesquisar();
        PesquisarDependente();
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
        btVerDadosPessoais.setDisable(true);
    }

    @FXML
    private void clkMostrarUltimasMovimentacoes(ActionEvent event) {
        PesquisarEntrada();
        PesquisarSaida();
        btVerDadosPessoais.setDisable(true);
    }
    
    @FXML
    private void clkTabelaEntrada(MouseEvent event) {
        if(tabelaEntrada.getSelectionModel().getSelectedItem() != null)
        {
            btVerDadosPessoais.setDisable(false);
            tabelaSaida.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void clkTabelaSaida(MouseEvent event) {
        if(tabelaSaida.getSelectionModel().getSelectedItem() != null)
        {
            btVerDadosPessoais.setDisable(false);
            tabelaEntrada.getSelectionModel().clearSelection();
        }
    }
    
    
    
    private void PesquisarEntrada()
    {
        tabelaEntrada.getItems().clear();
        //tabelaSaida.getSelectionModel().clearSelection();
        String filtro = txPesquisarEntrada.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cpf
        {
            PESSOAStxPesquisarCPF.setText(filtro);
            ctrRealizarMovimentacao.CarregarTabela(tabelaEntrada, PESSOAStxPesquisarCPF.getText(), "", ckbUltimaMov.isSelected());
        }
        else
            ctrRealizarMovimentacao.CarregarTabela(tabelaEntrada, txPesquisarEntrada.getText(), "", ckbUltimaMov.isSelected());
        btVerDadosPessoais.setDisable(tabelaEntrada.getSelectionModel().getSelectedItem() == null && tabelaSaida.getSelectionModel().getSelectedItem() == null);
    }
    
    private void PesquisarSaida()
    {
        tabelaSaida.getItems().clear();
        //tabelaEntrada.getSelectionModel().clearSelection();
        String filtro = txPesquisarSaida.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cpf
        {
            PESSOAStxPesquisarCPF.setText(filtro);
            ctrRealizarMovimentacao.CarregarTabela(tabelaSaida, PESSOAStxPesquisarCPF.getText(), "", ckbUltimaMov.isSelected());
        }
        else
            ctrRealizarMovimentacao.CarregarTabela(tabelaSaida, txPesquisarSaida.getText(), "", ckbUltimaMov.isSelected());
        btVerDadosPessoais.setDisable(tabelaEntrada.getSelectionModel().getSelectedItem() == null && tabelaSaida.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void clkIrEntrada(ActionEvent event) {
        PesquisarEntrada();
    }

    @FXML
    private void clkIrSaida(ActionEvent event) {
        PesquisarSaida();
    }

    @FXML
    private void clkDesfazer(ActionEvent event) throws IOException {
        if(!ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().ExisteMovimentacao())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não existem movimentações.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Desfazer Última Movimentação");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRealizarMovimentacao = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarMovimentacao.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        PesquisarEntrada();
        PesquisarSaida();
        Pesquisar();
        PesquisarDependente();
        ctrGerenciarVeiculos.CarregarTabelaVeiculos(VEICULOStabela, VEICULOStxPesquisa.getText(), VEICULOScbFiltrarCategoria.getValue());
        btVerDadosPessoais.setDisable(true);
    }

    @FXML
    private void clkVerDadosPessoais(ActionEvent event) throws IOException {
        if(tabelaEntrada.getSelectionModel().getSelectedItem() == null && tabelaSaida.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Selecione uma movimentação.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Visualizar Movimentação");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRealizarMovimentacao = stage;
        if(tabelaEntrada.getSelectionModel().getSelectedItem() != null)
            movimentacao = tabelaEntrada.getSelectionModel().getSelectedItem().getCod();
        else
            movimentacao = tabelaSaida.getSelectionModel().getSelectedItem().getCod();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarMovimentacao.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        
    }

    @FXML
    private void kpPesquisarEntrada(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           PesquisarEntrada();
    }

    @FXML
    private void kpPesquisarSaida(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           PesquisarSaida();
    }

    @FXML
    private void CAIXAclkAbrir(ActionEvent event) throws SQLException, IOException 
    {
        boolean erro;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(CAIXAbtAbrir.getText().equals("ABRIR"))
        {
            alert.setHeaderText("Abrir Caixa");
            alert.setContentText("Deseja realmente abrir o caixa ?");
        }
        else
        {
            alert.setHeaderText("Fechar Caixa");
            alert.setContentText("Deseja realmente fechar o caixa ?");
        }
        
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
        if(alert.showAndWait().get() != buttonTypeSim)
            return;
        Banco.con.IniciarTransacao();
        ctrFluxoCaixa.Novo();
        ctrFluxoCaixa.getCtrcaixa().setCaixa(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa());
        if(CAIXAbtAbrir.getText().equals("ABRIR"))
        {
            if(ctrFluxoCaixa.getCtrcaixa().getCaixa() != null) //existe caixa anterior
            {
                ctrFluxoCaixa.getCtrcaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());            
                erro = !ctrFluxoCaixa.getCtrcaixa().getCaixa().abrir();  
            }
            else //abrir primeiro caixa
                erro = !InformarSaldoInicial();
                
        }
        else
            erro = !ctrFluxoCaixa.getCtrcaixa().getCaixa().fechar();
        if(!Banco.con.getConnect().getAutoCommit())
        {
            if(!erro)
               Banco.con.Commit("Abrir/fechar caixa.");
            else
               Banco.con.Rollback("");
        }
        
        HabilitaTela(imvCaixa);
        
        
    }

    @FXML
    private void CAIXAclkMovimentar(ActionEvent event) throws IOException, SQLException {
        
        if(ctrFluxoCaixa.getCtrcaixa().getCaixa() == null || !ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("O Caixa está fechado.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;   
        }
        Stage stage = new Stage();
        stage.setTitle("Movimentar Caixa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaMovimentarCaixa = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MovimentarCaixa.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
        HabilitaTela(imvCaixa);
    }

    @FXML
    private void clkSelecionarPessoa(ActionEvent event) throws IOException {
        int pes_aux = SelecionarPessoaController.pessoa;
        Stage stage = new Stage();
        stage.setTitle("Selecionar Pessoa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaSelecionarPessoa = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        
        if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
        { //retornou alguma pessoa
            tfPessoa.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome()+" ("+ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getCpf()+")");
            if(tfPessoa.getText().contains("()"))
                tfPessoa.setText(tfPessoa.getText().replace("()", ""));
            ckbFiltrarPorPessoa.setDisable(false);
            ckbFiltrarPorPessoa.setSelected(true);
        }
        else
            SelecionarPessoaController.pessoa = pes_aux;
        
        Filtrar();
    }

    @FXML
    private void clkFiltrarPeriodo(ActionEvent event) {        
        dpDataInicial.setDisable(!ckbFiltrarPeriodoReceber.isSelected());
        dpDataFinal.setDisable(!ckbFiltrarPeriodoReceber.isSelected());
        Filtrar();
    }
    
    @FXML
    private void clkDataInicial(ActionEvent event) {
        if(dpDataInicial.getValue() != null)
           Filtrar();
    }

    @FXML
    private void clkDataFinal(ActionEvent event) {
        if(dpDataFinal.getValue() != null)
           Filtrar();
    }

    @FXML
    private void clkFiltrarPorPessoa(ActionEvent event) {
        Filtrar();
    }
    
    
    @FXML
    private void clkFiltrarParcelas(ActionEvent event) {
        if(cbFiltrarParcelasReceber.getValue().contains("hoje"))
        {
            ckbFiltrarPeriodoReceber.setSelected(false);
            ckbFiltrarPeriodoReceber.setDisable(true);            
        }
        else
        {
            ckbFiltrarPeriodoReceber.setDisable(false);            
        }
        dpDataInicial.setDisable(!ckbFiltrarPeriodoReceber.isSelected());
        dpDataFinal.setDisable(!ckbFiltrarPeriodoReceber.isSelected());
        Filtrar();
    }
    
    private void Filtrar()
    {
        btVer.setDisable(true);
        btEstornarRecebimento.setDisable(true);
        btReceber.setDisable(true);
        String filtro = "";
        
        if(ckbFiltrarPorPessoa.isSelected())
           filtro = "pes_cod = "+SelecionarPessoaController.pessoa;
        
        if(cbFiltrarParcelasReceber.getValue().contains("recebidas"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is not null";
        }
        if(cbFiltrarParcelasReceber.getValue().contains("vencidas"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is null and par_dtvencimento < '"+LocalDate.now()+"'";
        }
        if(cbFiltrarParcelasReceber.getValue().contains("hoje"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is null and par_dtvencimento = '"+LocalDate.now()+"'";
        }
        
        if(ckbFiltrarPeriodoReceber.isSelected())
        {
            //preciso validar antes
            if(dpDataInicial.getValue() != null && dpDataFinal.getValue() != null && !dpDataInicial.getValue().isAfter(dpDataFinal.getValue()))
            {
                ckbFiltrarPeriodoReceber.setTextFill(Color.BLACK);
                //tenho 3 situações aqui
                if(cbFiltrarParcelasReceber.getValue().contains("todas")) //exibir todas as parcelas com vencimento no periodo definido, pagas ou não
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtvencimento >= '"+dpDataInicial.getValue()+"' and par_dtvencimento <= '"+dpDataFinal.getValue()+"'";
                }
                if(cbFiltrarParcelasReceber.getValue().contains("recebidas")) //exibir somente parcelas pagas no periodo definido para o pagamento
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtpagamento >= '"+dpDataInicial.getValue()+"' and par_dtpagamento <= '"+dpDataFinal.getValue()+"'";
                }
                if(cbFiltrarParcelasReceber.getValue().contains("vencidas")) //exibir somente parcelas vencidas no periodo definido para o vencimento
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtvencimento >= '"+dpDataInicial.getValue()+"' and par_dtvencimento <= '"+dpDataFinal.getValue()+"'";
                }
            }
            else
            {
                ckbFiltrarPeriodoReceber.setTextFill(Color.RED);
                return;
            }            
        }
        ctrRealizarRecebimento.CarregarTabela(tabelaParcelasReceber, filtro);      
    }
    
    private void FiltrarPagar()
    {
        btVerPagamento.setDisable(true);
        btEstornarPagamento.setDisable(true);
        btPagar.setDisable(true);
        String filtro = "";
        
        if(ckbFiltrarPorPessoaEmpresa.isSelected())
        {
           if(SelecionarPessoaController.pessoa != 0)
              filtro = "pes_cod = "+SelecionarPessoaController.pessoa;
           else //filtrar por empresa
              filtro = "emp_cod = "+SelecionarEmpresaController.empresa;
        }
        
        if(cbFiltrarParcelasPagar.getValue().contains("pagas"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is not null";
        }
        if(cbFiltrarParcelasPagar.getValue().contains("vencidas"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is null and par_dtvencimento < '"+LocalDate.now()+"'";
        }
        if(cbFiltrarParcelasPagar.getValue().contains("hoje"))
        {
            if(!filtro.equals(""))
                filtro += " and ";
            filtro += "par_dtpagamento is null and par_dtvencimento = '"+LocalDate.now()+"'";
        }
        
        if(ckbFiltrarPeriodoPagar.isSelected())
        {
            //preciso validar antes
            if(dpDataInicialPagar.getValue() != null && dpDataFinalPagar.getValue() != null && !dpDataInicialPagar.getValue().isAfter(dpDataFinalPagar.getValue()))
            {
                ckbFiltrarPeriodoPagar.setTextFill(Color.BLACK);
                //tenho 3 situações aqui
                if(cbFiltrarParcelasPagar.getValue().contains("todas")) //exibir todas as parcelas com vencimento no periodo definido, pagas ou não
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtvencimento >= '"+dpDataInicialPagar.getValue()+"' and par_dtvencimento <= '"+dpDataFinalPagar.getValue()+"'";
                }
                if(cbFiltrarParcelasPagar.getValue().contains("pagas")) //exibir somente parcelas pagas no periodo definido para o pagamento
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtpagamento >= '"+dpDataInicialPagar.getValue()+"' and par_dtpagamento <= '"+dpDataFinalPagar.getValue()+"'";
                }
                if(cbFiltrarParcelasPagar.getValue().contains("vencidas")) //exibir somente parcelas vencidas no periodo definido para o vencimento
                {
                    if(!filtro.equals(""))
                        filtro += " and ";
                    filtro += "par_dtvencimento >= '"+dpDataInicialPagar.getValue()+"' and par_dtvencimento <= '"+dpDataFinalPagar.getValue()+"'";
                }
            }
            else
            {
                ckbFiltrarPeriodoPagar.setTextFill(Color.RED);
                return;
            }            
        }
        ctrRealizarPagamento.CarregarTabela(tabelaParcelasPagar, filtro);
    }

    @FXML
    private void clkReceber(ActionEvent event) throws IOException, SQLException {
        if(tabelaParcelasReceber.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            return;
        }
        if(tabelaParcelasReceber.getSelectionModel().getSelectedItem().temParcelaAnteriorAberta())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Existem parcelas anteriores à esta com recebimento pendente.\nDeseja realmente realizar o recebimento desta parcela ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() != buttonTypeSim)
                return;
        }
        //CAIXA ABERTO?
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() == null || !ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Caixa fechado");
            alert.setContentText("O caixa está fechado.\nDeseja abrir o caixa ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() != null)
                {
                    ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                    if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().abrir()) 
                       Banco.con.Commit("Abrir caixa.");
                    else
                    {
                        Banco.con.Rollback("Não foi possível abrir o caixa.");
                        return; 
                    }
                }
                else
                    if(!InformarSaldoInicial()) //se retornar falso é pq o caixa está fechado
                        return;
            }
            else
                return;           
        }
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        //VALIDEI
        //FAÇO RECEBIMENTO        
        parcelarec = tabelaParcelasReceber.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Realizar Recebimento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarRecebimento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarRecebimento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();  
        Filtrar();
        btEstornarRecebimento.setDisable(true);
        btReceber.setDisable(true);
        btVer.setDisable(true);
    }

    @FXML
    private void clkEstornarRecebimento(ActionEvent event) throws IOException, SQLException {
        if(tabelaParcelasReceber.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            return;
        }
        //CAIXA ABERTO?
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() == null || !ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Caixa fechado");
            alert.setContentText("O caixa está fechado.\nDeseja abrir o caixa ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() != null)
                {
                    ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                    if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().abrir()) 
                       Banco.con.Commit("Abrir caixa.");
                    else
                    {
                        Banco.con.Rollback("Não foi possível abrir o caixa.");
                        return; 
                    }
                }
                else
                    if(!InformarSaldoInicial()) //se retornar falso é pq o caixa está fechado
                        return;
            }
            else
                return;           
        }
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        //FAÇO ESTORNO DO RECEBIMENTO
        //perguntar se tem saldo suficiente em caixa para quando efetuar o estorno do recebimento????????? (ver com o orientador)
        float valor_pago = Float.parseFloat(tabelaParcelasReceber.getSelectionModel().getSelectedItem().getValorpago().replace(",", "."));
        if(!ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().temSaldo(valor_pago))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Saldo insuficiente em caixa.");
            alert.setContentText("Não é possível estornar o recebimento.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }
        //validei, faço o estorno! valido novamente lá dentro da função, pois o acesso ao sistema é concorrente
        parcelarec = tabelaParcelasReceber.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Estornar Recebimento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarRecebimento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarRecebimento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait(); 
        Filtrar();
        btEstornarRecebimento.setDisable(true);
        btReceber.setDisable(true);
        btVer.setDisable(true);
    }
    
    
    @FXML
    private void clkVerRecebimento(ActionEvent event) throws IOException {
        if(tabelaParcelasReceber.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            return;
        }
        
        parcelarec = tabelaParcelasReceber.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Ver Recebimento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarRecebimento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarRecebimento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait(); 
    }

    @FXML
    private void clkLimparRecebimento(ActionEvent event) 
    {
        estadoOriginalRecebimentos();
    }

    @FXML
    private void clkTabelaParcelasReceber(MouseEvent event) 
    {
        if(tabelaParcelasReceber.getSelectionModel().getSelectedItem() != null)
        {
            btVer.setDisable(false);
            ctrRealizarRecebimento.getCtrrecebimento().setParcela(tabelaParcelasReceber.getSelectionModel().getSelectedItem());
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().getData_pagamento() == null) //não está pago
            {
               btReceber.setDisable(false);
               btEstornarRecebimento.setDisable(true);
            }
            else //está pago
            {
                btReceber.setDisable(true);
                //posso estornar?
                btEstornarRecebimento.setDisable(!ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getParcelarec().podeEstornar());                
            }
                
        }
    }

    @FXML
    private void CONTARECEBERkpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
        {
           CONTARECEBERbtAlterar.setDisable(true);
           CONTARECEBERbtApagar.setDisable(true);
           ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
        }
    }

    @FXML
    private void CONTARECEBERclkIr(ActionEvent event) {
        CONTARECEBERbtAlterar.setDisable(true);
        CONTARECEBERbtApagar.setDisable(true);
        ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
    }

    @FXML
    private void CONTARECEBERclkTabela(MouseEvent event) {
        if(CONTARECEBERtabela.getSelectionModel().getSelectedItem() != null)
        {
            CONTARECEBERbtAlterar.setDisable(false);
            CONTARECEBERbtApagar.setDisable(false);
        }
    }

    @FXML
    private void CONTARECEBERclkNovo(ActionEvent event) {
        NovaContaReceber();
    }

    @FXML
    private void CONTARECEBERclkAlterar(ActionEvent event) {
        CONTARECEBERlbCamposObrigatorios.setVisible(true);
        CONTARECEBERbtConfirmar.getTooltip().setText("Alterar conta");
        CarregarDadosContaReceber();
        apTabelaContasReceber.setDisable(true);
        apDadosContaReceber.setDisable(false);
        CONTARECEBERbtCancelar.setDisable(false);
        CONTARECEBERbtConfirmar.setDisable(false); 
        CONTARECEBERbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        
        ObservableList <Node> componentes = aux_dadoscontareceber.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setDisable(false);
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).setDisable(false);
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).setDisable(false);
            
            if(n instanceof Label)
                ((Label)n).setDisable(false);
        }
        CONTARECEBERtfCod.setDisable(true);
        CONTARECEBERbtSelecionarPessoa.setDisable(false);
        CONTARECEBERtfValor.setDisable(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        CONTARECEBERcbForma.setDisable(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        CONTARECEBERdpDataVencimento.setDisable(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag() || CONTARECEBERtabela.getSelectionModel().getSelectedItem().getNum_parcelas() != 0);
        if(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Não será permitido alterar algumas informações da conta.\nA conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
        }
    }

    @FXML
    private void CONTARECEBERclkApagar(ActionEvent event) {
        CONTARECEBERlbCamposObrigatorios.setVisible(false);
        CONTARECEBERbtConfirmar.getTooltip().setText("Desativar conta");
        CONTARECEBERtfCod.setDisable(false);        
        apTabelaContasReceber.setDisable(true);
        apDadosContaReceber.setDisable(false);
        CONTARECEBERbtConfigurar.setTooltip(new Tooltip("Ver parcelas"));
        
        CONTARECEBERModoExibicao(true);
        CarregarDadosContaReceber();
        CONTARECEBERbtConfigurar.setDisable(CONTARECEBERtabela.getSelectionModel().getSelectedItem().getNum_parcelas() == 0);
        CONTARECEBERbtCancelar.setDisable(false);
        CONTARECEBERbtConfirmar.setDisable(false);
    }

    @FXML
    private void CONTARECEBERclkSelecionarPessoa(ActionEvent event) throws IOException {
        SelecionarPessoaController.pessoa = 0;
        Stage stage = new Stage();
        stage.setTitle("Selecionar Pessoa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaSelecionarPessoa = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        
        if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
        { //retornou alguma pessoa
            codigo_pessoa = SelecionarPessoaController.pessoa;
            CONTARECEBERtfPessoa.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome()+" ("+ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getCpf()+")");
            if(CONTARECEBERtfPessoa.getText().contains("()"))
                CONTARECEBERtfPessoa.setText(CONTARECEBERtfPessoa.getText().replace("()", ""));
            
        }
        
    }

    @FXML
    private void CONTARECEBERclkFormaPagamento(ActionEvent event) {
        if(CONTARECEBERcbForma.getValue() == null )
            return;
        
        if(CONTARECEBERcbForma.getValue().contains("vista"))
        {
            CONTARECEBERlbDataVencimento.setText("Data de vencimento (*):");
            CONTARECEBERbtConfigurar.setDisable(true);
            CONTARECEBERdpDataVencimento.setDisable(false);
            if(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"))
                CONTARECEBERdpDataVencimento.requestFocus();
        }
        else //a prazo
        {
            CONTARECEBERlbDataVencimento.setText("Data de vencimento:");
            CONTARECEBERbtConfigurar.setDisable(false);
            CONTARECEBERdpDataVencimento.setDisable(true);
            if(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"))
               CONTARECEBERbtConfigurar.requestFocus();
        }
    }

    @FXML
    private void CONTARECEBERclkConfigurar(ActionEvent event) {  
        if(CONTARECEBERtfValor.getText().equals("") || Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")) == 0) //erro, usuário não informou o valor
        {
           CONTARECEBERlbValor.setTextFill(Color.RED);
           CONTARECEBERtfValor.requestFocus();
           return;
        }
        else
           CONTARECEBERlbValor.setTextFill(Color.BLACK); 
        
        PARCELASRECEBERtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        PARCELASRECEBERtfNumeroParcelas.getEditor().setText("1");
        PARCELASRECEBERdpDataInicio.setValue(null);
        
        ctrLancarContaReceber.CarregarParcelas(PARCELASRECEBERtabela);
        PARCELASRECEBERtabela.refresh();
        
        //TEM PARCELA PAGA? SE SIM, não deixo gerar novas parcelas
        PARCELASRECEBERbtGerar.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        PARCELASRECEBERbtAjustar.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        
        PARCELASRECEBERtfValorEntrada.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        PARCELASRECEBERtfNumeroParcelas.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        PARCELASRECEBERdpDataInicio.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        PARCELASRECEBERlbDoisCliques.setVisible(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") && !ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag() && !(PARCELASRECEBERtabela.getItems().size() == 0));
        PARCELASRECEBERlbSoma.setVisible(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") && !ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag() && !(PARCELASRECEBERtabela.getItems().size() == 0));
        if(PARCELASRECEBERtabela.getItems().size() > 0 && CONTARECEBERbtConfirmar.getTooltip().getText().contains("Lançar"))
           PARCELASRECEBERlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaReceber.getCtrcontareceber().getConta().soma_parcelas(PARCELASRECEBERtabela.getItems(), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")))));
        PARCELASRECEBERbtConfirmar.setDisable(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag());
        PARCELASRECEBERbtCancelar.setVisible(true);
        PARCELASRECEBERbtConfirmar.setVisible(true);
        PARCELASRECEBERapAlterarParcela.setVisible(false);
        PARCELASRECEBERapGerarParcela.setVisible(true);
        if(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag() && CONTARECEBERbtConfirmar.getTooltip().getText().contains("Alterar"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Não será permitido alterar as parcelas da conta.\nA conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
        }
        
        
        
        //CARREGAR DADOS
        
        PARCELASRECEBERtfValorEntrada.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada());
        if(ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas() > 0)
        {
           PARCELASRECEBERtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas()));
           PARCELASRECEBERtfNumeroParcelas.getEditor().setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas()+"");
        }
        if(ctrLancarContaReceber.getCtrcontareceber().getConta().getParcelas().size() > 0 && ctrLancarContaReceber.getCtrcontareceber().getConta().getParcelas().get(0).getData_vencimento() != null)
        {
           PARCELASRECEBERdpDataInicio.setValue(Funcoes.StringToDate(ctrLancarContaReceber.getCtrcontareceber().getConta().getParcelas().get(0).getData_vencimento()));
           
        }
        
        PARCELASRECEBERlbValorEntrada.setTextFill(Color.BLACK);
        PARCELASRECEBERlbDataInicio.setTextFill(Color.BLACK);
        PARCELASRECEBERlbParcelas.setTextFill(Color.BLACK);
        
        apTabelaContasReceber.setVisible(false);  
        apFormaRecebimento.setVisible(false);
        apParcelasReceber.setVisible(true);        
        apDadosContaReceber.setDisable(true);
        
        PARCELASRECEBERlbDoisCliques.setTextFill(Color.RED);
        PARCELASRECEBERtabela.setDisable(PARCELASRECEBERtabela.getItems().size() == 0);
        PARCELASRECEBERtfValorEntrada.requestFocus();
        
        if(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"))
        {
            PARCELASRECEBERtfValorEntrada.setDisable(false);
            PARCELASRECEBERdpDataInicio.setDisable(false);
            PARCELASRECEBERtfNumeroParcelas.setDisable(false);
        }
        PARCELASRECEBERtfValorEntrada.setEditable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERtfValorEntrada.setFocusTraversable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERtfValorEntrada.setMouseTransparent(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERdpDataInicio.setEditable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERdpDataInicio.setFocusTraversable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERdpDataInicio.setMouseTransparent(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERtfNumeroParcelas.setEditable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERtfNumeroParcelas.setFocusTraversable(!CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASRECEBERtfNumeroParcelas.setMouseTransparent(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar"));
    }

    @FXML
    private void CONTARECEBERclkCancelar(ActionEvent event) {
        if(apFormaRecebimento.isVisible())
        {
            ExibirTelaFormasRecebimento(false);
            
            return;
        }
        estadoOriginalContaReceber();
    }
    
    private void ExibirTelaFormasPagamento(boolean status)
    {
        
        ctrRealizarPagamento.CarregarComboBox(PAGAMENTOcbFormaPagamento, "FORMAS");
        ctrRealizarPagamento.CarregarCartoesCredito(PAGAMENTOcbCartao);
        PAGAMENTOtfBoletoNumero.setText("");
        PAGAMENTOtfChequeNumero.setText("");
        PAGAMENTOtfChequeBanco.setText("");
        PAGAMENTOtfChequeConta.setText("");
        PAGAMENTOtfDepositoIdentificacao.setText("");
        PAGAMENTOcbCartao.getSelectionModel().clearSelection();
        PAGAMENTOcbCartao.setValue(null);
        PAGAMENTOcbFormaPagamento.setDisable(false);
        if(!CONTAPAGARtfCod.getText().equals("")) //alteração, vou carregar dados do banco
        {
            PAGAMENTOcbFormaPagamento.setValue(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getForma());
            if(PAGAMENTOcbFormaPagamento.getValue() != null && PAGAMENTOcbFormaPagamento.getValue().equals("Cartão"))
            {
               PAGAMENTOcbFormaPagamento.setValue("Cartão de Crédito");
               PAGAMENTOcbFormaPagamento.setDisable(true);
            }
            else
            {
                PAGAMENTOcbFormaPagamento.getItems().remove(1); //remove a opção de cartão de crédito
            }
            
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCheque() != null)
            {
                PAGAMENTOtfChequeNumero.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCheque().getNumero());
                PAGAMENTOtfChequeBanco.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCheque().getBanco());
                PAGAMENTOtfChequeConta.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCheque().getConta());
            }
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getBoleto() != null)
                PAGAMENTOtfBoletoNumero.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getBoleto().getNumero());
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getDeposito() != null)
                PAGAMENTOtfDepositoIdentificacao.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getDeposito().getIdentificacao());
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCartao() != null)            
               for(int i =0; i < PAGAMENTOcbCartao.getItems().size(); i++)
                   if(PAGAMENTOcbCartao.getItems().get(i).getCod() == ctrLancarContaPagar.getCtrcontapagar().getConta().getPrimeiraParcela().getCartao().getCod())
                      PAGAMENTOcbCartao.getSelectionModel().select(i);
        }
        apTabelaContasPagar.setVisible(!status);  
        apFormaPagamento.setVisible(status);
        apParcelasPagar.setVisible(false);        
        apDadosContaPagar.setDisable(status);
        PAGAMENTOcbFormaPagamento.requestFocus();
        
        PAGAMENTOapBoleto.setVisible(false);
        PAGAMENTOapCheque.setVisible(false);
        PAGAMENTOapDeposito.setVisible(false);  
        PAGAMENTOapCartao.setVisible(false);
        
        MostrarFormaPagamento();
    }
    
    private void ExibirTelaFormasRecebimento(boolean status)
    {
        
        ctrRealizarRecebimento.CarregarComboBox(RECEBIMENTOcbFormaPagamento, "FORMAS");
        RECEBIMENTOtfBoletoNumero.setText("");
        RECEBIMENTOtfChequeNumero.setText("");
        RECEBIMENTOtfChequeBanco.setText("");
        RECEBIMENTOtfChequeConta.setText("");
        RECEBIMENTOtfDepositoIdentificacao.setText("");
        if(!CONTARECEBERtfCod.getText().equals("")) //alteração, vou carregar dados do banco
        {
            RECEBIMENTOcbFormaPagamento.setValue(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getForma());
            if(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getCheque() != null)
            {
                RECEBIMENTOtfChequeNumero.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getCheque().getNumero());
                RECEBIMENTOtfChequeBanco.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getCheque().getBanco());
                RECEBIMENTOtfChequeConta.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getCheque().getConta());
            }
            if(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getBoleto() != null)
                RECEBIMENTOtfBoletoNumero.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getBoleto().getNumero());
            if(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getDeposito() != null)
                RECEBIMENTOtfDepositoIdentificacao.setText(ctrLancarContaReceber.getCtrcontareceber().getConta().getPrimeiraParcela().getDeposito().getIdentificacao());
        }
        apTabelaContasReceber.setVisible(!status);  
        apFormaRecebimento.setVisible(status);
        apParcelasReceber.setVisible(false);        
        apDadosContaReceber.setDisable(status);
        RECEBIMENTOcbFormaPagamento.requestFocus();
        
        RECEBIMENTOapBoleto.setVisible(false);
        RECEBIMENTOapCheque.setVisible(false);
        RECEBIMENTOapDeposito.setVisible(false);  
        
        MostrarFormaRecebimento();
    }

    @FXML
    private void CONTARECEBERclkConfirmar(ActionEvent event) throws SQLException
    {          
        if(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {   
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrLancarContaReceber.getCtrcontareceber().Desativar()) //desativando apenas, para manter histórico, não precisando excluir movimentações em caixa, manter CONSISTÊNCIA
                {   
                    Banco.con.Commit("Desativar conta a receber.");
                    estadoOriginalContaReceber();
                    ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
                }
                else
                    Banco.con.Rollback(""); 
            }
            return;
        }


        boolean erro = false;

        if(CONTARECEBERcbForma.getValue() != null && CONTARECEBERcbForma.getValue().contains("vista") && (CONTARECEBERdpDataVencimento.getValue() == null || CONTARECEBERdpDataVencimento.getValue().isAfter(LocalDate.now())))
        {
            erro = true;
            CONTARECEBERdpDataVencimento.requestFocus();
            CONTARECEBERlbDataVencimento.setTextFill(Color.RED);
        }
        else
            CONTARECEBERlbDataVencimento.setTextFill(Color.BLACK);

        if(CONTARECEBERcbForma.getValue() != null && CONTARECEBERcbForma.getValue().contains("prazo") && ctrLancarContaReceber.getCtrcontareceber().getConta().getParcelas().size() == 0)
        {
            erro = true;
            CONTARECEBERbtConfigurar.requestFocus();
            PARCELASRECEBERlbParcelas.setTextFill(Color.RED);
        }
        else
            PARCELASRECEBERlbParcelas.setTextFill(Color.BLACK);

        if(CONTARECEBERcbForma.getValue() == null)
        {
            erro = true;
            CONTARECEBERcbForma.requestFocus();
            CONTARECEBERlbForma.setTextFill(Color.RED);
        }
        else
            CONTARECEBERlbForma.setTextFill(Color.BLACK);

        if(CONTARECEBERtfPessoa.getText().equals(""))
        {
            erro = true;
            CONTARECEBERbtSelecionarPessoa.requestFocus();
            CONTARECEBERlbPessoa.setTextFill(Color.RED);
        }
        else
            CONTARECEBERlbPessoa.setTextFill(Color.BLACK);

        if(CONTARECEBERtfValor.getText().equals("") || Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")) == 0)
        {
            erro = true;
            CONTARECEBERtfValor.requestFocus();
            CONTARECEBERlbValor.setTextFill(Color.RED);
        }
        else
            CONTARECEBERlbValor.setTextFill(Color.BLACK);

        if(CONTARECEBERtfDescricao.getText().equals(""))
        {
            erro = true;
            CONTARECEBERtfDescricao.requestFocus();
            CONTARECEBERlbDescricao.setTextFill(Color.RED);
        }
        else
            CONTARECEBERlbDescricao.setTextFill(Color.BLACK);


        if(erro)
            return;

        if(CONTARECEBERcbForma.getValue().contains("vista") || (CONTARECEBERcbForma.getValue().contains("prazo") && !ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada().equals("") && Float.parseFloat(ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada().replace(",", ".")) > 0))
        {
            ExibirTelaFormasRecebimento(true); //farei o recebimento a vista pela tela de pagamentos, após informar dados do pagamento (dinheiro, cheque, deposito, boleto)
            String msg = "";
            if(CONTARECEBERcbForma.getValue().contains("vista"))                
                msg = "Informe os dados do recebimento que será feito à vista.";
            else
                msg = "Informe os dados do recebimento referentes ao valor de entrada.";  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText(msg);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return;
        }

        //NÃO TEM RECEBIMENTO A VISTA, NEM VALOR DE ENTRADA, SERÁ TUDO PARCELADO A PRAZO!
        Banco.con.IniciarTransacao();
        if(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Lançar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrLancarContaReceber.getCtrcontareceber().Salvar(CONTARECEBERtfDescricao.getText(), Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), 0, ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas(), codigo_pessoa, "", null, CONTARECEBERtfObservacoes.getText());
        }
        else //Alterando
        {     
            if(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag()) //só permito atualizar os dados                
               erro = !ctrLancarContaReceber.getCtrcontareceber().Atualizar(CONTARECEBERtfDescricao.getText(), codigo_pessoa, CONTARECEBERtfObservacoes.getText());
            else  
            {
               if(!ctrLancarContaReceber.getCtrcontareceber().Desativar()) //vou desativar a conta e as parcelas antigas
               {   
                   Banco.con.Rollback("");
                   return;
               }
               //agora vou inserir a nova conta e as parcelas atuais
               erro = !ctrLancarContaReceber.getCtrcontareceber().Salvar(CONTARECEBERtfDescricao.getText(), Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), 0, ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas(), codigo_pessoa, "", null, CONTARECEBERtfObservacoes.getText());
            }
        }

        if(!erro)
        {       
           Banco.con.Commit("Cadastrar/alterar conta a receber.");
           estadoOriginalContaReceber();
           ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
        } 
        else
           Banco.con.Rollback("");
        
        
    }
    
    private void ExibirTelaAlterarParcelaPagar(boolean status)
    {
        PARCELASPAGARtfParcelaNumero.setEditable(false);
        PARCELASPAGARtfParcelaNumero.setFocusTraversable(false);
        PARCELASPAGARtfParcelaNumero.setMouseTransparent(true);        
        PARCELASPAGARtfParcelaNumero.setDisable(false);
        PARCELASPAGARlbParcelaData.setTextFill(Color.BLACK);
        PARCELASPAGARlbParcelaValor.setTextFill(Color.BLACK);
        PARCELASPAGARapAlterarParcela.setVisible(status);
        PARCELASPAGARapGerarParcela.setVisible(!status);
        PARCELASPAGARbtConfirmar.setVisible(!status);
        PARCELASPAGARbtCancelar.setVisible(!status);
        if(status) //carregar dados
        {
           parcela_alterar = PARCELASPAGARtabela.getSelectionModel().getSelectedIndex();
           pp_seq = PARCELASPAGARtabela.getSelectionModel().getSelectedItem().getSeq();
           PARCELASPAGARtfParcelaNumero.setText(PARCELASPAGARtabela.getSelectionModel().getSelectedItem().getNumero()+"");
           PARCELASPAGARtfParcelaValor.setText(PARCELASPAGARtabela.getSelectionModel().getSelectedItem().getValor());
           PARCELASPAGARdpParcelaData.setValue(Funcoes.StringToDate(PARCELASPAGARtabela.getSelectionModel().getSelectedItem().getData_vencimento()));
           PARCELASPAGARdpParcelaData.requestFocus();
        }
    }
    
        
    private void ExibirTelaAlterarParcela(boolean status)
    {
        PARCELASRECEBERtfParcelaNumero.setEditable(false);
        PARCELASRECEBERtfParcelaNumero.setFocusTraversable(false);
        PARCELASRECEBERtfParcelaNumero.setMouseTransparent(true);        
        PARCELASRECEBERtfParcelaNumero.setDisable(false);
        PARCELASRECEBERlbParcelaData.setTextFill(Color.BLACK);
        PARCELASRECEBERlbParcelaValor.setTextFill(Color.BLACK);        
        PARCELASRECEBERapAlterarParcela.setVisible(status);
        PARCELASRECEBERapGerarParcela.setVisible(!status);
        PARCELASRECEBERbtConfirmar.setVisible(!status);
        PARCELASRECEBERbtCancelar.setVisible(!status);
        if(status) //carregar dados
        {
           parcela_alterar = PARCELASRECEBERtabela.getSelectionModel().getSelectedIndex();
           pr_seq = PARCELASRECEBERtabela.getSelectionModel().getSelectedItem().getSeq();
           PARCELASRECEBERtfParcelaNumero.setText(PARCELASRECEBERtabela.getSelectionModel().getSelectedItem().getNumero()+"");
           PARCELASRECEBERtfParcelaValor.setText(PARCELASRECEBERtabela.getSelectionModel().getSelectedItem().getValor());
           PARCELASRECEBERdpParcelaData.setValue(Funcoes.StringToDate(PARCELASRECEBERtabela.getSelectionModel().getSelectedItem().getData_vencimento()));
           PARCELASRECEBERdpParcelaData.requestFocus();
        }
    }

    @FXML
    private void PARCELASRECEBERclkTabela(MouseEvent event) //evento para DUPLO CLIQUE (onMousePressed)
    {        
        if (!ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag() && !CONTARECEBERbtConfirmar.getTooltip().getText().contains("Desativar") && PARCELASRECEBERtabela.getSelectionModel().getSelectedItem() != null && event.isPrimaryButtonDown() && event.getClickCount() == 2) 
        {
            ExibirTelaAlterarParcela(true);
        }
    }         

    @FXML
    private void PARCELASRECEBERclkCancelar(ActionEvent event) {
        PARCELASRECEBERtabela.getItems().clear();
        PARCELASRECEBERtabela.refresh();
        apTabelaContasReceber.setVisible(true);
        apParcelasReceber.setVisible(false);
        apDadosContaReceber.setDisable(false);
        CONTARECEBERbtConfigurar.requestFocus();        
    }

    @FXML
    private void PARCELASRECEBERclkConfirmar(ActionEvent event) {
        //faz validação
        boolean erro = false;
        if(PARCELASRECEBERtabela.getItems().size() == 0)
        {            
            erro = true;
            PARCELASRECEBERlbParcelas.setTextFill(Color.RED);  
            PARCELASRECEBERbtGerar.requestFocus();
        }
        else
            PARCELASRECEBERlbParcelas.setTextFill(Color.BLACK);  
        
        /*if(PARCELASRECEBERdpDataInicio.getValue() == null || PARCELASRECEBERdpDataInicio.getValue().isBefore(LocalDate.now().plusDays(1)))
        {
            erro = true;
            PARCELASRECEBERlbDataInicio.setTextFill(Color.RED);
            PARCELASRECEBERdpDataInicio.requestFocus();
        }
        else
            PARCELASRECEBERlbDataInicio.setTextFill(Color.BLACK);
        */
        
        if(!Funcoes.isInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) || Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) < 1 || Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) > 12)
        {
            erro = true;
            PARCELASRECEBERlbNumeroParcelas.setTextFill(Color.RED);
            PARCELASRECEBERtfNumeroParcelas.requestFocus();
        }
        else
            PARCELASRECEBERlbNumeroParcelas.setTextFill(Color.BLACK);
        
        if(PARCELASRECEBERtfValorEntrada.getText().equals("") || Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")) >= Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASRECEBERlbValorEntrada.setTextFill(Color.RED);
            PARCELASRECEBERtfValorEntrada.requestFocus();
        }
        else
            PARCELASRECEBERlbValorEntrada.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        if(!ctrLancarContaReceber.SalvarParcelas(PARCELASRECEBERtabela, Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")), Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText())))
           return;
        
        //VALIDOU E SALVOU AS PARCELAS
        if(ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas() != 0)
        {
           CONTARECEBERcbForma.getItems().remove(1);
           String info = "A prazo (";
           if(!ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada().equals(""))
               info += "R$"+ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada()+" + ";
           info += ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas()+" parcelas)";
           
           CONTARECEBERcbForma.getItems().add(info);
           CONTARECEBERcbForma.getSelectionModel().select(1); 
           CONTARECEBERbtConfigurar.setDisable(false);
           CONTARECEBERdpDataVencimento.setDisable(true);
        }
        apTabelaContasReceber.setVisible(true);
        apParcelasReceber.setVisible(false);
        apDadosContaReceber.setDisable(false);
        CONTARECEBERcbForma.requestFocus();
    }

    @FXML
    private void PARCELASRECEBERclkGerar(ActionEvent event) {
        boolean erro = false;
        if(PARCELASRECEBERdpDataInicio.getValue() == null || PARCELASRECEBERdpDataInicio.getValue().isBefore(LocalDate.now().plusDays(1)))
        {
            erro = true;
            PARCELASRECEBERlbDataInicio.setTextFill(Color.RED);
            PARCELASRECEBERdpDataInicio.requestFocus();
        }
        else
            PARCELASRECEBERlbDataInicio.setTextFill(Color.BLACK);
        
        if(!Funcoes.isInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) || Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) < 1 || Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()) > 12)
        {
            erro = true;
            PARCELASRECEBERlbNumeroParcelas.setTextFill(Color.RED);
            PARCELASRECEBERtfNumeroParcelas.requestFocus();
        }
        else
            PARCELASRECEBERlbNumeroParcelas.setTextFill(Color.BLACK);
        
        if(PARCELASRECEBERtfValorEntrada.getText().equals("") || Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")) >= Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASRECEBERlbValorEntrada.setTextFill(Color.RED);
            PARCELASRECEBERtfValorEntrada.requestFocus();
        }
        else
            PARCELASRECEBERlbValorEntrada.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        //validei
        //Gerar parcelas
        if(PARCELASRECEBERtabela.getItems().size() != 0)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Gerar parcelas");
            alert.setContentText("As parcelas existentes serão substituidas.\nDeseja realmente gerar novas parcelas ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() != buttonTypeSim)
               return;
        }
        PARCELASRECEBERlbParcelas.setTextFill(Color.BLACK);
        ctrLancarContaReceber.GerarParcelas(PARCELASRECEBERtabela, Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")), Integer.parseInt(PARCELASRECEBERtfNumeroParcelas.getEditor().getText()), PARCELASRECEBERdpDataInicio.getValue());
        PARCELASRECEBERlbDoisCliques.setVisible(!(PARCELASRECEBERtabela.getItems().size() == 0));
        PARCELASRECEBERlbSoma.setVisible(!(PARCELASRECEBERtabela.getItems().size() == 0));
        PARCELASRECEBERlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaReceber.getCtrcontareceber().getConta().soma_parcelas(PARCELASRECEBERtabela.getItems(), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")))));
        PARCELASRECEBERtabela.setDisable(PARCELASRECEBERtabela.getItems().size() == 0);
        PARCELASRECEBERtabela.requestFocus();
        
    }

    @FXML
    private void RECEBIMENTOclkFormaPagamento(ActionEvent event) {
        MostrarFormaRecebimento();
        
    }
    
    private void MostrarFormaPagamento()
    {
        PAGAMENTOapBoleto.setVisible(false);
        PAGAMENTOapCheque.setVisible(false);
        PAGAMENTOapDeposito.setVisible(false);
        PAGAMENTOapCartao.setVisible(false);
        PAGAMENTOlbAviso.setVisible(false);
        
        if(PAGAMENTOcbFormaPagamento.getValue() == null)
            return;
        
        if(PAGAMENTOcbFormaPagamento.getValue().equals("Boleto"))
        {
            PAGAMENTOapBoleto.setVisible(true);
            PAGAMENTOtfBoletoNumero.requestFocus();
        }
        if(PAGAMENTOcbFormaPagamento.getValue().equals("Cheque"))
        {
            PAGAMENTOlbAviso.setText("O pagamento em Cheque deve ser registrado somente após o cheque ter sido debitado.");
            PAGAMENTOlbAviso.setVisible(true);
            PAGAMENTOlbAviso.setLayoutY(282);
            PAGAMENTOapCheque.setVisible(true);
            PAGAMENTOtfChequeNumero.requestFocus();
        }
        if(PAGAMENTOcbFormaPagamento.getValue().equals("Depósito"))
        {
            PAGAMENTOapDeposito.setVisible(true);
            PAGAMENTOtfDepositoIdentificacao.requestFocus();
        }
        if(PAGAMENTOcbFormaPagamento.getValue().equals("Cartão de Crédito"))
        {
            PAGAMENTOlbAviso.setText("O pagamento de uma parcela feito no Cartão de Crédito não altera o Fluxo de Caixa.");
            PAGAMENTOlbAviso.setVisible(true);
            PAGAMENTOlbAviso.setLayoutY(160);
            PAGAMENTOapCartao.setVisible(true);
            PAGAMENTOcbCartao.requestFocus();
        }
    }
    
    private void MostrarFormaRecebimento()
    {
        RECEBIMENTOapBoleto.setVisible(false);
        RECEBIMENTOapCheque.setVisible(false);
        RECEBIMENTOapDeposito.setVisible(false);
        RECEBIMENTOlbAviso.setVisible(false);
        
        if(RECEBIMENTOcbFormaPagamento.getValue() == null)
            return;
        
        if(RECEBIMENTOcbFormaPagamento.getValue().equals("Boleto"))
        {
            RECEBIMENTOapBoleto.setVisible(true);
            RECEBIMENTOtfBoletoNumero.requestFocus();
        }
        if(RECEBIMENTOcbFormaPagamento.getValue().equals("Cheque"))
        {
            RECEBIMENTOapCheque.setVisible(true);
            RECEBIMENTOlbAviso.setVisible(true);
            RECEBIMENTOtfChequeNumero.requestFocus();
        }
        if(RECEBIMENTOcbFormaPagamento.getValue().equals("Depósito"))
        {
            RECEBIMENTOapDeposito.setVisible(true);
            RECEBIMENTOtfDepositoIdentificacao.requestFocus();
        }
    }

    @FXML
    private void RECEBIMENTOclkCancelar(ActionEvent event) {
        ExibirTelaFormasRecebimento(false);
    }

    @FXML
    private void RECEBIMENTOclkConfirmar(ActionEvent event) throws SQLException, IOException {        
        //VALIDAR
        boolean erro = false;
        if(RECEBIMENTOcbFormaPagamento.getValue() != null && RECEBIMENTOcbFormaPagamento.getValue().equals("Boleto"))
        { //Validar campos do boleto
          if(RECEBIMENTOtfBoletoNumero.getText().equals(""))
          {
              erro = true;
              RECEBIMENTOlbBoletoNumero.setTextFill(Color.RED);
              RECEBIMENTOtfBoletoNumero.requestFocus();
          }
          else
              RECEBIMENTOlbBoletoNumero.setTextFill(Color.BLACK);  
        }
        
        if(RECEBIMENTOcbFormaPagamento.getValue() != null && RECEBIMENTOcbFormaPagamento.getValue().equals("Cheque"))
        { //Validar campos do boleto
          if(RECEBIMENTOtfChequeBanco.getText().equals(""))
          {
              erro = true;
              RECEBIMENTOlbChequeBanco.setTextFill(Color.RED);
              RECEBIMENTOtfChequeBanco.requestFocus();
          }
          else
              RECEBIMENTOlbChequeBanco.setTextFill(Color.BLACK);  
          if(RECEBIMENTOtfChequeConta.getText().equals(""))
          {
              erro = true;
              RECEBIMENTOlbChequeConta.setTextFill(Color.RED);
              RECEBIMENTOtfChequeConta.requestFocus();
          }
          else
              RECEBIMENTOlbChequeConta.setTextFill(Color.BLACK);  
          if(RECEBIMENTOtfChequeNumero.getText().equals(""))
          {
              erro = true;
              RECEBIMENTOlbChequeNumero.setTextFill(Color.RED);
              RECEBIMENTOtfChequeNumero.requestFocus();
          }
          else
              RECEBIMENTOlbChequeNumero.setTextFill(Color.BLACK); 
        }
        
        if(RECEBIMENTOcbFormaPagamento.getValue() != null && RECEBIMENTOcbFormaPagamento.getValue().equals("Depósito"))
        { //Validar campos do boleto
          if(RECEBIMENTOtfDepositoIdentificacao.getText().equals(""))
          {
              erro = true;
              RECEBIMENTOlbDepositoIdentificacao.setTextFill(Color.RED);
              RECEBIMENTOtfDepositoIdentificacao.requestFocus();
          }
          else
              RECEBIMENTOlbDepositoIdentificacao.setTextFill(Color.BLACK);  
        }
        
        if(RECEBIMENTOcbFormaPagamento.getValue() == null)
        {
            erro = true;
            RECEBIMENTOlbFormaPagamento.setTextFill(Color.RED);
            RECEBIMENTOcbFormaPagamento.requestFocus();
        }
        else
            RECEBIMENTOlbFormaPagamento.setTextFill(Color.BLACK);
        
        
        /*if(dpDataPagamento.getValue() == null || dpDataPagamento.getValue().isAfter(LocalDate.now()))
        {
            erro = true;
            lbDataPagamento.setTextFill(Color.RED);
            dpDataPagamento.requestFocus();
        }
        else
            lbDataPagamento.setTextFill(Color.BLACK);*/
        
        if(erro)
            return;
        
        //CAIXA ABERTO?
        
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() == null || !ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Banco.con.IniciarTransacao();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Caixa fechado");
            alert.setContentText("O caixa está fechado.\nEle será aberto para realizar o recebimento.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            
            if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa() != null)
            {
                ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                if(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().abrir()) 
                   Banco.con.Commit("Abrir caixa.");
                else
                {
                    Banco.con.Rollback("Não foi possível abrir o caixa.");
                    return; 
                }
            }
            else
                if(!InformarSaldoInicial())
                   return;
                       
        }
        ctrRealizarRecebimento.Novo();
        ctrRealizarRecebimento.getCtrrecebimento().setCaixa(ctrRealizarRecebimento.getCtrrecebimento().getMovCaixa().getCaixa().getUltimoCaixa());
        
        //À vista
        float valor_entrada = 0;
        int numero_parcelas = 0;
        LocalDate data_vencimento = CONTARECEBERdpDataVencimento.getValue();
        
        if(!CONTARECEBERcbForma.getValue().contains("vista")) // valor de entrada
        {
            data_vencimento = LocalDate.now();
            valor_entrada = Float.parseFloat(ctrLancarContaReceber.getCtrcontareceber().getConta().getValor_entrada().replace(",", "."));
            numero_parcelas = ctrLancarContaReceber.getCtrcontareceber().getConta().getNum_parcelas();
        }
        
        Banco.con.IniciarTransacao();
        if(CONTARECEBERbtConfirmar.getTooltip().getText().contains("Lançar"))
           erro = !ctrLancarContaReceber.getCtrcontareceber().Salvar_Receber(CONTARECEBERtfDescricao.getText(), Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), valor_entrada, numero_parcelas, codigo_pessoa, data_vencimento, CONTARECEBERtfObservacoes.getText(), RECEBIMENTOcbFormaPagamento.getValue(), RECEBIMENTOtfBoletoNumero.getText(), RECEBIMENTOtfChequeNumero.getText(), RECEBIMENTOtfChequeConta.getText(), RECEBIMENTOtfChequeBanco.getText(), RECEBIMENTOtfDepositoIdentificacao.getText());            
        else //alterar
        {
            if(ctrLancarContaReceber.getCtrcontareceber().getConta().temParcelaPag()) //só permito atualizar os dados do pagamento e da conta                
                   erro = !ctrLancarContaReceber.getCtrcontareceber().Atualizar(CONTARECEBERtfDescricao.getText(), codigo_pessoa, CONTARECEBERtfObservacoes.getText(), RECEBIMENTOcbFormaPagamento.getValue(), RECEBIMENTOtfBoletoNumero.getText(), RECEBIMENTOtfChequeNumero.getText(), RECEBIMENTOtfChequeConta.getText(), RECEBIMENTOtfChequeBanco.getText(), RECEBIMENTOtfDepositoIdentificacao.getText());
            else  
            {
               if(!ctrLancarContaReceber.getCtrcontareceber().Desativar()) //vou desativar a conta e as parcelas antigas
               {
                   Banco.con.Rollback("");
                   return;
               }
               //agora vou inserir a nova conta e as parcelas atuais
               erro = !ctrLancarContaReceber.getCtrcontareceber().Salvar_Receber(CONTARECEBERtfDescricao.getText(), Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), valor_entrada, numero_parcelas, codigo_pessoa, data_vencimento, CONTARECEBERtfObservacoes.getText(), RECEBIMENTOcbFormaPagamento.getValue(), RECEBIMENTOtfBoletoNumero.getText(), RECEBIMENTOtfChequeNumero.getText(), RECEBIMENTOtfChequeConta.getText(), RECEBIMENTOtfChequeBanco.getText(), RECEBIMENTOtfDepositoIdentificacao.getText()); 
            }
        }
        
        if(!erro)
        { 
            Banco.con.Commit("Cadastrar/alterar conta a receber fazendo recebimento.");
            estadoOriginalContaReceber();
            ctrLancarContaReceber.CarregarTabela(CONTARECEBERtabela, CONTARECEBERtxPesquisa.getText());
        }
        else
            Banco.con.Rollback("");
        
        
    }

    @FXML
    private void PARCELASRECEBERclkCancelarParcela(ActionEvent event) {
        ExibirTelaAlterarParcela(false);
        PARCELASRECEBERtabela.requestFocus();
    }

    @FXML
    private void PARCELASRECEBERclkConfirmarParcela(ActionEvent event) {
        //faço validações, datas, etc
        LocalDate vencimento_anterior  = Funcoes.StringToDate(PARCELASRECEBERtabela.getItems().get(parcela_alterar).getData_vencimento());
        boolean erro = false;
        if(PARCELASRECEBERtfParcelaValor.getText().equals("") || Float.parseFloat(PARCELASRECEBERtfParcelaValor.getText().replace(",", ".")) == 0 || Float.parseFloat(PARCELASRECEBERtfParcelaValor.getText().replace(",", ".")) > Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASRECEBERtfParcelaValor.requestFocus();
            PARCELASRECEBERlbParcelaValor.setTextFill(Color.RED);
        }
        else
            PARCELASRECEBERlbParcelaValor.setTextFill(Color.BLACK);
        
        if(PARCELASRECEBERdpParcelaData.getValue() == null || (pr_seq == 0 && PARCELASRECEBERdpParcelaData.getValue().isBefore(LocalDate.now().plusDays(1))) || PARCELASRECEBERdpParcelaData.getValue().isAfter(vencimento_anterior.plusDays(30)) || PARCELASRECEBERdpParcelaData.getValue().isBefore(vencimento_anterior.minusDays(30)))
        {
            erro = true;
            PARCELASRECEBERdpParcelaData.requestFocus();
            PARCELASRECEBERlbParcelaData.setTextFill(Color.RED);
        }
        else
            PARCELASRECEBERlbParcelaData.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        ctrLancarContaReceber.AlterarParcela(PARCELASRECEBERtabela, parcela_alterar, PARCELASRECEBERdpParcelaData.getValue(), Float.parseFloat(PARCELASRECEBERtfParcelaValor.getText().replace(",", ".")));
        
        
        ExibirTelaAlterarParcela(false);
        if(parcela_alterar == 0) //parcela de número 1
           PARCELASRECEBERdpDataInicio.setValue(Funcoes.StringToDate(PARCELASRECEBERtabela.getItems().get(0).getData_vencimento()));
        PARCELASRECEBERtabela.getSelectionModel().select(parcela_alterar);
        PARCELASRECEBERtabela.requestFocus();
        PARCELASRECEBERlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaReceber.getCtrcontareceber().getConta().soma_parcelas(PARCELASRECEBERtabela.getItems(), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")))));
    }

    @FXML
    private void PARCELASRECEBERclkAjustar(ActionEvent event) {
        if(PARCELASRECEBERtabela.getItems().size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Não existem parcelas para esta conta.\nGere as parcelas primeiro.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            PARCELASRECEBERbtGerar.requestFocus();
            return;
        }
        if(PARCELASRECEBERtabela.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione a(s) parcela(s) que você deseja reajustar o valor para totalizar o valor da conta.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();      
            PARCELASRECEBERtabela.requestFocus();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Ajustar parcelas");
        alert.setContentText("Deseja realmente ajustar o valor da(s) parcela(s) selecionada(s) ?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
        if(alert.showAndWait().get() == buttonTypeSim)            
        {
           ctrLancarContaReceber.AjustarParcelas(PARCELASRECEBERtabela, PARCELASRECEBERtabela.getSelectionModel().getSelectedIndices(), Float.parseFloat(CONTARECEBERtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")));
           PARCELASRECEBERtabela.requestFocus();
           PARCELASRECEBERlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaReceber.getCtrcontareceber().getConta().soma_parcelas(PARCELASRECEBERtabela.getItems(), Float.parseFloat(PARCELASRECEBERtfValorEntrada.getText().replace(",", ".")))));
        }
        
    }

    @FXML
    private void CONTAPAGARkpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
        {
           CONTAPAGARbtAlterar.setDisable(true);
           CONTAPAGARbtApagar.setDisable(true);
           ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
        }
    }

    @FXML
    private void CONTAPAGARclkIr(ActionEvent event) {
        CONTAPAGARbtAlterar.setDisable(true);
        CONTAPAGARbtApagar.setDisable(true);
        ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
    }

    @FXML
    private void CONTAPAGARclkTabela(MouseEvent event) {
        if(CONTAPAGARtabela.getSelectionModel().getSelectedItem() != null)
        {
            CONTAPAGARbtAlterar.setDisable(false);
            CONTAPAGARbtApagar.setDisable(false);
        }
    }

    @FXML
    private void CONTAPAGARclkNovo(ActionEvent event) throws SQLException {
        NovaContaPagar();
    }

    @FXML
    private void CONTAPAGARclkAlterar(ActionEvent event) throws SQLException {
        CONTAPAGARlbCamposObrigatorios.setVisible(true);
        CONTAPAGARbtConfirmar.getTooltip().setText("Alterar conta");
        CarregarDadosContaPagar();
        apTabelaContasPagar.setDisable(true);
        apDadosContaPagar.setDisable(false);
        CONTAPAGARbtCancelar.setDisable(false);
        CONTAPAGARbtConfirmar.setDisable(false); 
        CONTAPAGARbtConfigurar.setTooltip(new Tooltip("Configurar parcelas"));
        
        
        ObservableList <Node> componentes = aux_dadoscontapagar.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setDisable(false);
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).setDisable(false);
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).setDisable(false);
            
            if(n instanceof Label)
                ((Label)n).setDisable(false);
        }
        
        if(!ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && ctrFluxoCaixa.CarregarDados())
        {
            CONTAPAGARlbSaldo.setText("Caixa: ");
            if(Float.parseFloat(ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
            {
                CONTAPAGARlbSaldo.setTextFill(Color.RED);
                CONTAPAGARlbSaldo.setText(CONTAPAGARlbSaldo.getText()+"-");
            }
            else
                CONTAPAGARlbSaldo.setTextFill(Color.GREEN);
            CONTAPAGARlbSaldo.setText(CONTAPAGARlbSaldo.getText()+"R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace("-", ""));
        } 
        
        
        CONTAPAGARtfCod.setDisable(true);
        CONTAPAGARbtSelecionarPessoaEmpresa.setDisable(false);
        CONTAPAGARtfValor.setDisable(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        CONTAPAGARcbForma.setDisable(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        CONTAPAGARdpDataVencimento.setDisable(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() || CONTAPAGARtabela.getSelectionModel().getSelectedItem().getNum_parcelas() != 0);
        if(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Não será permitido alterar algumas informações da conta.\nA conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
        }
    }

    @FXML
    private void CONTAPAGARclkApagar(ActionEvent event) {
        CONTAPAGARlbCamposObrigatorios.setVisible(false);
        CONTAPAGARbtConfirmar.getTooltip().setText("Desativar conta");
        CONTAPAGARtfCod.setDisable(false);
        apTabelaContasPagar.setDisable(true);
        apDadosContaPagar.setDisable(false);
        CONTAPAGARbtConfigurar.setTooltip(new Tooltip("Ver parcelas"));
        
        CONTAPAGARModoExibicao(true);
        CarregarDadosContaPagar();
        CONTAPAGARbtConfigurar.setDisable(CONTAPAGARtabela.getSelectionModel().getSelectedItem().getNum_parcelas() == 0);
        CONTAPAGARbtCancelar.setDisable(false);
        CONTAPAGARbtConfirmar.setDisable(false);
    }

    @FXML
    private void PARCELASPAGARclkCancelarParcela(ActionEvent event) {
        ExibirTelaAlterarParcelaPagar(false);
        PARCELASPAGARtabela.requestFocus();
    }

    @FXML
    private void PARCELASPAGARclkConfirmarParcela(ActionEvent event) {
        //faço validações, datas, etc
        LocalDate vencimento_anterior  = Funcoes.StringToDate(PARCELASPAGARtabela.getItems().get(parcela_alterar).getData_vencimento());
        boolean erro = false;
        if(PARCELASPAGARtfParcelaValor.getText().equals("") || Float.parseFloat(PARCELASPAGARtfParcelaValor.getText().replace(",", ".")) == 0 || Float.parseFloat(PARCELASPAGARtfParcelaValor.getText().replace(",", ".")) > Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASPAGARtfParcelaValor.requestFocus();
            PARCELASPAGARlbParcelaValor.setTextFill(Color.RED);
        }
        else
            PARCELASPAGARlbParcelaValor.setTextFill(Color.BLACK);
        
        if(PARCELASPAGARdpParcelaData.getValue() == null || (pp_seq == 0 && PARCELASPAGARdpParcelaData.getValue().isBefore(LocalDate.now().plusDays(1))) || PARCELASPAGARdpParcelaData.getValue().isAfter(vencimento_anterior.plusDays(30)) || PARCELASPAGARdpParcelaData.getValue().isBefore(vencimento_anterior.minusDays(30)))
        {
            erro = true;
            PARCELASPAGARdpParcelaData.requestFocus();
            PARCELASPAGARlbParcelaData.setTextFill(Color.RED);
        }
        else
            PARCELASPAGARlbParcelaData.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        ctrLancarContaPagar.AlterarParcela(PARCELASPAGARtabela, parcela_alterar, PARCELASPAGARdpParcelaData.getValue(), Float.parseFloat(PARCELASPAGARtfParcelaValor.getText().replace(",", ".")));
        
        
        ExibirTelaAlterarParcelaPagar(false);
        if(parcela_alterar == 0) //parcela de número 1
           PARCELASPAGARdpDataInicio.setValue(Funcoes.StringToDate(PARCELASPAGARtabela.getItems().get(0).getData_vencimento()));
        PARCELASPAGARtabela.getSelectionModel().select(parcela_alterar);
        PARCELASPAGARtabela.requestFocus();
        PARCELASPAGARlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaPagar.getCtrcontapagar().getConta().soma_parcelas(PARCELASPAGARtabela.getItems(), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")))));
    }

    @FXML
    private void PARCELASPAGARclkGerar(ActionEvent event) {
        boolean erro = false;
        if(PARCELASPAGARdpDataInicio.getValue() == null || PARCELASPAGARdpDataInicio.getValue().isBefore(LocalDate.now().plusDays(1)))
        {
            erro = true;
            PARCELASPAGARlbDataInicio.setTextFill(Color.RED);
            PARCELASPAGARdpDataInicio.requestFocus();
        }
        else
            PARCELASPAGARlbDataInicio.setTextFill(Color.BLACK);
        
        if(!Funcoes.isInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) || Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) < 1 || Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) > 12)
        {
            erro = true;
            PARCELASPAGARlbNumeroParcelas.setTextFill(Color.RED);
            PARCELASPAGARtfNumeroParcelas.requestFocus();
        }
        else
            PARCELASPAGARlbNumeroParcelas.setTextFill(Color.BLACK);
        
        if(PARCELASPAGARtfValorEntrada.getText().equals("") || Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")) >= Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASPAGARlbValorEntrada.setTextFill(Color.RED);
            PARCELASPAGARtfValorEntrada.requestFocus();
        }
        else
            PARCELASPAGARlbValorEntrada.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        //validei
        //Gerar parcelas
        if(PARCELASPAGARtabela.getItems().size() != 0)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Gerar parcelas");
            alert.setContentText("As parcelas existentes serão substituidas.\nDeseja realmente gerar novas parcelas ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() != buttonTypeSim)
               return;
        }
        PARCELASPAGARlbParcelas.setTextFill(Color.BLACK);
        ctrLancarContaPagar.GerarParcelas(PARCELASPAGARtabela, Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")), Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()), PARCELASPAGARdpDataInicio.getValue());
        PARCELASPAGARlbDoisCliques.setVisible(!(PARCELASPAGARtabela.getItems().size() == 0));
        PARCELASPAGARlbSoma.setVisible(!(PARCELASPAGARtabela.getItems().size() == 0));
        PARCELASPAGARlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaPagar.getCtrcontapagar().getConta().soma_parcelas(PARCELASPAGARtabela.getItems(), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")))));
        
        PARCELASPAGARtabela.setDisable(PARCELASPAGARtabela.getItems().size() == 0);
        PARCELASPAGARtabela.requestFocus();
        
    }

    @FXML
    private void PARCELASPAGARclkAjustar(ActionEvent event) 
    {
        if(PARCELASPAGARtabela.getItems().size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Não existem parcelas para esta conta.\nGere as parcelas primeiro.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            PARCELASPAGARbtGerar.requestFocus();
            return;
        }
        if(PARCELASPAGARtabela.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione a(s) parcela(s) que você deseja reajustar o valor para totalizar o valor da conta.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();      
            PARCELASPAGARtabela.requestFocus();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Ajustar parcelas");
        alert.setContentText("Deseja realmente ajustar o valor da(s) parcela(s) selecionada(s) ?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
        if(alert.showAndWait().get() == buttonTypeSim)
        {
           ctrLancarContaPagar.AjustarParcelas(PARCELASPAGARtabela, PARCELASPAGARtabela.getSelectionModel().getSelectedIndices(), Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")));
           PARCELASPAGARtabela.requestFocus();
           PARCELASPAGARlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaPagar.getCtrcontapagar().getConta().soma_parcelas(PARCELASPAGARtabela.getItems(), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")))));
        }
    }

    @FXML
    private void PARCELASPAGARclkTabela(MouseEvent event) {//evento para DUPLO CLIQUE (onMousePressed)
        if (!ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && !CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") && PARCELASPAGARtabela.getSelectionModel().getSelectedItem() != null && event.isPrimaryButtonDown() && event.getClickCount() == 2) 
        {
            ExibirTelaAlterarParcelaPagar(true);
        }
    }

    @FXML
    private void PARCELASPAGARclkCancelar(ActionEvent event) {
        PARCELASPAGARtabela.getItems().clear();
        PARCELASPAGARtabela.refresh();
        apTabelaContasPagar.setVisible(true);
        apParcelasPagar.setVisible(false);
        apDadosContaPagar.setDisable(false);
        CONTAPAGARbtConfigurar.requestFocus(); 
    }

    @FXML
    private void PARCELASPAGARclkConfirmar(ActionEvent event) {
        //faz validação
        boolean erro = false;
        if(PARCELASPAGARtabela.getItems().size() == 0)
        {            
            erro = true;
            PARCELASPAGARlbParcelas.setTextFill(Color.RED);  
            PARCELASPAGARbtGerar.requestFocus();
        }
        else
            PARCELASPAGARlbParcelas.setTextFill(Color.BLACK);  
        
        /*if(PARCELASRECEBERdpDataInicio.getValue() == null || PARCELASRECEBERdpDataInicio.getValue().isBefore(LocalDate.now().plusDays(1)))
        {
            erro = true;
            PARCELASRECEBERlbDataInicio.setTextFill(Color.RED);
            PARCELASRECEBERdpDataInicio.requestFocus();
        }
        else
            PARCELASRECEBERlbDataInicio.setTextFill(Color.BLACK);
        */
        
        if(!Funcoes.isInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) || Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) < 1 || Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText()) > 12)
        {
            erro = true;
            PARCELASPAGARlbNumeroParcelas.setTextFill(Color.RED);
            PARCELASPAGARtfNumeroParcelas.requestFocus();
        }
        else
            PARCELASPAGARlbNumeroParcelas.setTextFill(Color.BLACK);
        
        if(PARCELASPAGARtfValorEntrada.getText().equals("") || Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")) >= Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")))
        {
            erro = true;
            PARCELASPAGARlbValorEntrada.setTextFill(Color.RED);
            PARCELASPAGARtfValorEntrada.requestFocus();
        }
        else
            PARCELASPAGARlbValorEntrada.setTextFill(Color.BLACK);
        
        if(erro)
            return;
        
        if(!ctrLancarContaPagar.SalvarParcelas(PARCELASPAGARtabela, Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")), Integer.parseInt(PARCELASPAGARtfNumeroParcelas.getEditor().getText())))
           return;
        
        //VALIDOU E SALVOU AS PARCELAS
        if(ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas() != 0)
        {
           CONTAPAGARcbForma.getItems().remove(1);
           String info = "A prazo (";
           if(!ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada().equals(""))
               info += "R$"+ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada()+" + ";
           info += ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas()+" parcelas)";
           
           CONTAPAGARcbForma.getItems().add(info);
           CONTAPAGARcbForma.getSelectionModel().select(1); 
           CONTAPAGARbtConfigurar.setDisable(false);
           CONTAPAGARdpDataVencimento.setDisable(true);
        }
        apTabelaContasPagar.setVisible(true);
        apParcelasPagar.setVisible(false);
        apDadosContaPagar.setDisable(false);
        CONTAPAGARcbForma.requestFocus();
    }

    @FXML
    private void PAGAMENTOclkFormaPagamento(ActionEvent event) {
        MostrarFormaPagamento();
        
    }

    @FXML
    private void PAGAMENTOclkCancelar(ActionEvent event) {
        ExibirTelaFormasPagamento(false);
    }

    @FXML
    private void PAGAMENTOclkConfirmar(ActionEvent event) throws SQLException, IOException {
        //VALIDAR
        boolean erro = false;
        if(PAGAMENTOcbFormaPagamento.getValue() != null && PAGAMENTOcbFormaPagamento.getValue().equals("Boleto"))
        { //Validar campos do boleto
          if(PAGAMENTOtfBoletoNumero.getText().equals(""))
          {
              erro = true;
              PAGAMENTOlbBoletoNumero.setTextFill(Color.RED);
              PAGAMENTOtfBoletoNumero.requestFocus();
          }
          else
              PAGAMENTOlbBoletoNumero.setTextFill(Color.BLACK);  
        }
        
        if(PAGAMENTOcbFormaPagamento.getValue() != null && PAGAMENTOcbFormaPagamento.getValue().equals("Cheque"))
        { //Validar campos do boleto
          if(PAGAMENTOtfChequeBanco.getText().equals(""))
          {
              erro = true;
              PAGAMENTOlbChequeBanco.setTextFill(Color.RED);
              PAGAMENTOtfChequeBanco.requestFocus();
          }
          else
              PAGAMENTOlbChequeBanco.setTextFill(Color.BLACK);  
          if(PAGAMENTOtfChequeConta.getText().equals(""))
          {
              erro = true;
              PAGAMENTOlbChequeConta.setTextFill(Color.RED);
              PAGAMENTOtfChequeConta.requestFocus();
          }
          else
              PAGAMENTOlbChequeConta.setTextFill(Color.BLACK);  
          if(PAGAMENTOtfChequeNumero.getText().equals(""))
          {
              erro = true;
              PAGAMENTOlbChequeNumero.setTextFill(Color.RED);
              PAGAMENTOtfChequeNumero.requestFocus();
          }
          else
              PAGAMENTOlbChequeNumero.setTextFill(Color.BLACK); 
        }
        
        if(PAGAMENTOcbFormaPagamento.getValue() != null && PAGAMENTOcbFormaPagamento.getValue().equals("Depósito"))
        { //Validar campos do boleto
          if(PAGAMENTOtfDepositoIdentificacao.getText().equals(""))
          {
              erro = true;
              PAGAMENTOlbDepositoIdentificacao.setTextFill(Color.RED);
              PAGAMENTOtfDepositoIdentificacao.requestFocus();
          }
          else
              PAGAMENTOlbDepositoIdentificacao.setTextFill(Color.BLACK);  
        }
        
        if(PAGAMENTOcbFormaPagamento.getValue() != null && PAGAMENTOcbFormaPagamento.getValue().equals("Cartão de Crédito"))
        { //Validar campos do boleto
          if(PAGAMENTOcbCartao.getValue() == null)
          {
              erro = true;
              PAGAMENTOlbCartao.setTextFill(Color.RED);
              PAGAMENTOcbCartao.requestFocus();
          }
          else
              PAGAMENTOlbCartao.setTextFill(Color.BLACK);  
        }
        
        if(PAGAMENTOcbFormaPagamento.getValue() == null)
        {
            erro = true;
            PAGAMENTOlbFormaPagamento.setTextFill(Color.RED);
            PAGAMENTOcbFormaPagamento.requestFocus();
        }
        else
            PAGAMENTOlbFormaPagamento.setTextFill(Color.BLACK);
        
        
        /*if(dpDataPagamento.getValue() == null || dpDataPagamento.getValue().isAfter(LocalDate.now()))
        {
            erro = true;
            lbDataPagamento.setTextFill(Color.RED);
            dpDataPagamento.requestFocus();
        }
        else
            lbDataPagamento.setTextFill(Color.BLACK);*/
        
        if(erro)
            return;
        
        //CAIXA ABERTO?
        
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() == null || !ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Banco.con.IniciarTransacao();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Caixa fechado");              
            alert.setContentText("O caixa está fechado.\nEle será aberto para realizar o pagamento.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            
            
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() != null)
            {
                ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().abrir()) 
                    Banco.con.Commit("Abrir caixa.");
                else
                {
                    Banco.con.Rollback("Não foi possível abrir o caixa.");
                    return; 
                }
            }
            else
                if(!InformarSaldoInicial())
                   return;
                       
        }
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
       
        //À vista
        float valor_entrada = 0;
        int numero_parcelas = 0;
        LocalDate data_vencimento = CONTAPAGARdpDataVencimento.getValue();
        
        if(!CONTAPAGARcbForma.getValue().contains("vista")) // valor de entrada
        {
            data_vencimento = LocalDate.now();
            valor_entrada = Float.parseFloat(ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada().replace(",", "."));
            numero_parcelas = ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas();
            if(!ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().temSaldo(valor_entrada))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Saldo insuficiente em caixa.");
                alert.setContentText("Não foi possível realizar o pagamento.\nA conta não será lançada.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
                alert.showAndWait();
                return;
            }
        }
        else //a vista
        {
            if(!ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().temSaldo(Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", "."))))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Saldo insuficiente em caixa.");
                alert.setContentText("Não foi possível realizar o pagamento.\nA conta não será lançada.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return;
            }            
        }
        
        int cartao = 0;
        if(PAGAMENTOcbFormaPagamento.getValue().equals("Cartão de Crédito"))
           cartao = PAGAMENTOcbCartao.getValue().getCod();
        Banco.con.IniciarTransacao();
        if(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Lançar"))
           erro = !ctrLancarContaPagar.getCtrcontapagar().Salvar_Pagar(CONTAPAGARtfDescricao.getText(), Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), valor_entrada, numero_parcelas, codigo_pessoa, codigo_empresa, data_vencimento, CONTAPAGARtfObservacoes.getText(), PAGAMENTOcbFormaPagamento.getValue(), PAGAMENTOtfBoletoNumero.getText(), PAGAMENTOtfChequeNumero.getText(), PAGAMENTOtfChequeConta.getText(), PAGAMENTOtfChequeBanco.getText(), PAGAMENTOtfDepositoIdentificacao.getText(),cartao);            
        else //alterar
        {
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag()) //só permito atualizar os dados do pagamento e da conta                
                   erro = !ctrLancarContaPagar.getCtrcontapagar().Atualizar(CONTAPAGARtfDescricao.getText(), codigo_pessoa, codigo_empresa, CONTAPAGARtfObservacoes.getText(), PAGAMENTOcbFormaPagamento.getValue(), PAGAMENTOtfBoletoNumero.getText(), PAGAMENTOtfChequeNumero.getText(), PAGAMENTOtfChequeConta.getText(), PAGAMENTOtfChequeBanco.getText(), PAGAMENTOtfDepositoIdentificacao.getText(), cartao);
            else  
            {
               if(!ctrLancarContaPagar.getCtrcontapagar().Desativar()) //vou desativar a conta e as parcelas antigas
               {
                   Banco.con.Rollback("");
                   return;
               }
               //agora vou inserir a nova conta e as parcelas atuais
               erro = !ctrLancarContaPagar.getCtrcontapagar().Salvar_Pagar(CONTAPAGARtfDescricao.getText(), Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), valor_entrada, numero_parcelas, codigo_pessoa, codigo_empresa, data_vencimento, CONTAPAGARtfObservacoes.getText(), PAGAMENTOcbFormaPagamento.getValue(), PAGAMENTOtfBoletoNumero.getText(), PAGAMENTOtfChequeNumero.getText(), PAGAMENTOtfChequeConta.getText(), PAGAMENTOtfChequeBanco.getText(), PAGAMENTOtfDepositoIdentificacao.getText(),cartao); 
            }
        }
        
        if(!erro)
        { 
            Banco.con.Commit("Cadastrar/alterar conta a pagar fazendo pagamento.");
            estadoOriginalContaPagar();
            ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
        }
        else
            Banco.con.Rollback("");
        
        
    }

    @FXML
    private void CONTAPAGARclkSelecionarPessoaEmpresa(ActionEvent event) throws IOException {
        //Escolher opção
        EscolherOpcaoController.opcao = 0; //não escolheu nenhuma opção por padrão
        Stage stage = new Stage();
        stage.setTitle("Selecionar");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaEscolherOpcao = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EscolherOpcao.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        if(EscolherOpcaoController.opcao == 0) //não escolheu nenhuma
            return;
        
        
        if(EscolherOpcaoController.opcao == 1) //selecionar empresa
        {
            SelecionarEmpresaController.empresa = 0;
            stage = new Stage();
            stage.setTitle("Selecionar Empresa");
            stage.getIcons().add(new Image(url.toString()));
            TelaSelecionarEmpresa = stage;
            scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarEmpresa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarEmpresaController.empresa != 0) //retorn o código da empresa, 0 se não retornou!
            { //retornou alguma empresa
                codigo_pessoa = SelecionarPessoaController.pessoa = 0;
                
                codigo_empresa = SelecionarEmpresaController.empresa;                
                CONTAPAGARtfPessoa.setText(ctrGerenciarEmpresas.getCtrempresa().getEmpresa().getEmpresa(SelecionarEmpresaController.empresa).getNome()+" ("+ctrGerenciarEmpresas.getCtrempresa().getEmpresa().getEmpresa(SelecionarEmpresaController.empresa).getCnpj()+")");
                if(CONTAPAGARtfPessoa.getText().contains("()"))
                    CONTAPAGARtfPessoa.setText(CONTAPAGARtfPessoa.getText().replace("()", ""));

            }
            return;
        }
        
        if(EscolherOpcaoController.opcao == 2) //selecionar pessoa
        {
            SelecionarPessoaController.pessoa = 0;
            stage = new Stage();
            stage.setTitle("Selecionar Pessoa");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            TelaSelecionarPessoa = stage;
            scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            { //retornou alguma pessoa
                codigo_empresa = SelecionarEmpresaController.empresa = 0;
                
                codigo_pessoa = SelecionarPessoaController.pessoa;                
                CONTAPAGARtfPessoa.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome()+" ("+ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getCpf()+")");
                if(CONTAPAGARtfPessoa.getText().contains("()"))
                    CONTAPAGARtfPessoa.setText(CONTAPAGARtfPessoa.getText().replace("()", ""));

            }
            return;
        }
    }

    @FXML
    private void CONTAPAGARclkFormaPagamento(ActionEvent event) {
        if(CONTAPAGARcbForma.getValue() == null )
            return;
        
        if(CONTAPAGARcbForma.getValue().contains("vista"))
        {
            CONTAPAGARlbDataVencimento.setText("Data de vencimento (*):");
            CONTAPAGARbtConfigurar.setDisable(true);
            CONTAPAGARdpDataVencimento.setDisable(false);
            if(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"))
               CONTAPAGARdpDataVencimento.requestFocus();
        }
        else //parcelado
        {
            CONTAPAGARlbDataVencimento.setText("Data de vencimento:");
            CONTAPAGARbtConfigurar.setDisable(false);
            CONTAPAGARdpDataVencimento.setDisable(true);
            if(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"))
               CONTAPAGARbtConfigurar.requestFocus();
        }
    }

    @FXML
    private void CONTAPAGARclkConfigurar(ActionEvent event) throws SQLException {
        if(CONTAPAGARtfValor.getText().equals("") || Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")) == 0) //erro, usuário não informou o valor
        {
           CONTAPAGARlbValor.setTextFill(Color.RED);
           CONTAPAGARtfValor.requestFocus();
           return;
        }
        else
           CONTAPAGARlbValor.setTextFill(Color.BLACK); 
        
        PARCELASPAGARtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        PARCELASPAGARtfNumeroParcelas.getEditor().setText("1");
        PARCELASPAGARdpDataInicio.setValue(null);
        
        ctrLancarContaPagar.CarregarParcelas(PARCELASPAGARtabela);
        PARCELASPAGARtabela.refresh();
        
        //TEM PARCELA PAGA? SE SIM, não deixo gerar novas parcelas
        PARCELASPAGARbtGerar.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        PARCELASPAGARbtAjustar.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        
        PARCELASPAGARlbSaldo.setText("");
        if(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") && !ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && ctrFluxoCaixa.CarregarDados())
        {
            PARCELASPAGARlbSaldo.setText("Caixa: ");
            if(Float.parseFloat(ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace(",", ".")) < 0)
            {
                PARCELASPAGARlbSaldo.setTextFill(Color.RED);
                PARCELASPAGARlbSaldo.setText(PARCELASPAGARlbSaldo.getText()+"-");
            }
            else
                PARCELASPAGARlbSaldo.setTextFill(Color.GREEN);
            PARCELASPAGARlbSaldo.setText(PARCELASPAGARlbSaldo.getText()+"R$"+ctrFluxoCaixa.getCtrcaixa().getCaixa().getSaldoDia().replace("-", ""));
        } 
        
        PARCELASPAGARtfValorEntrada.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        PARCELASPAGARtfNumeroParcelas.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        PARCELASPAGARdpDataInicio.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        PARCELASPAGARlbDoisCliques.setVisible(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") && !ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && !(PARCELASPAGARtabela.getItems().size() == 0));
        PARCELASPAGARlbSoma.setVisible(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") && !ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && !(PARCELASPAGARtabela.getItems().size() == 0));
        if(PARCELASPAGARtabela.getItems().size() > 0 && CONTAPAGARbtConfirmar.getTooltip().getText().contains("Lançar"))
           PARCELASPAGARlbSoma.setText("Soma: R$"+Funcoes.ValorMonetario(ctrLancarContaPagar.getCtrcontapagar().getConta().soma_parcelas(PARCELASPAGARtabela.getItems(), Float.parseFloat(PARCELASPAGARtfValorEntrada.getText().replace(",", ".")))));
        PARCELASPAGARbtConfirmar.setDisable(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar") || ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag());
        PARCELASPAGARbtCancelar.setVisible(true);
        PARCELASPAGARbtConfirmar.setVisible(true);
        PARCELASPAGARapAlterarParcela.setVisible(false);
        PARCELASPAGARapGerarParcela.setVisible(true);
        if(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag() && CONTAPAGARbtConfirmar.getTooltip().getText().contains("Alterar"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Não será permitido alterar as parcelas da conta.\nA conta possui parcelas pagas e/ou movimentações em caixa referentes ao valor de entrada ou pagamento à vista.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
        }
        
        
        
        //CARREGAR DADOS
        
        PARCELASPAGARtfValorEntrada.setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada());
        if(ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas() > 0)
        {
           PARCELASPAGARtfNumeroParcelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas()));
           PARCELASPAGARtfNumeroParcelas.getEditor().setText(ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas()+"");
        }
        if(ctrLancarContaPagar.getCtrcontapagar().getConta().getParcelas().size() > 0 && ctrLancarContaPagar.getCtrcontapagar().getConta().getParcelas().get(0).getData_vencimento() != null)
        {
           PARCELASPAGARdpDataInicio.setValue(Funcoes.StringToDate(ctrLancarContaPagar.getCtrcontapagar().getConta().getParcelas().get(0).getData_vencimento()));
           
        }
        
        PARCELASPAGARlbValorEntrada.setTextFill(Color.BLACK);
        PARCELASPAGARlbDataInicio.setTextFill(Color.BLACK);
        PARCELASPAGARlbParcelas.setTextFill(Color.BLACK);
        
        apTabelaContasPagar.setVisible(false);  
        apFormaPagamento.setVisible(false);
        apParcelasPagar.setVisible(true);        
        apDadosContaPagar.setDisable(true);
        
        PARCELASPAGARlbDoisCliques.setTextFill(Color.RED);
        PARCELASPAGARtabela.setDisable(PARCELASPAGARtabela.getItems().size() == 0);
        PARCELASPAGARtfValorEntrada.requestFocus();
        
        if(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"))
        {
            PARCELASPAGARtfValorEntrada.setDisable(false);
            PARCELASPAGARdpDataInicio.setDisable(false);
            PARCELASPAGARtfNumeroParcelas.setDisable(false);
        }
        PARCELASPAGARtfValorEntrada.setEditable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARtfValorEntrada.setFocusTraversable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARtfValorEntrada.setMouseTransparent(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARdpDataInicio.setEditable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARdpDataInicio.setFocusTraversable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARdpDataInicio.setMouseTransparent(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARtfNumeroParcelas.setEditable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARtfNumeroParcelas.setFocusTraversable(!CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
        PARCELASPAGARtfNumeroParcelas.setMouseTransparent(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar"));
    }

    @FXML
    private void CONTAPAGARclkCancelar(ActionEvent event) {
        if(apFormaPagamento.isVisible())
        {
            ExibirTelaFormasPagamento(false);
            
            return;
        }
        estadoOriginalContaPagar();
    }

    @FXML
    private void CONTAPAGARclkConfirmar(ActionEvent event) throws SQLException {
        if(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {   
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrLancarContaPagar.getCtrcontapagar().Desativar()) //desativando apenas, para manter histórico, não precisando excluir movimentações em caixa, manter CONSISTÊNCIA
                {   
                    Banco.con.Commit("Desativar conta a pagar.");
                    estadoOriginalContaPagar();
                    ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
                }
                else
                    Banco.con.Rollback(""); 
            }
            return;
        }


        boolean erro = false;

        if(CONTAPAGARcbForma.getValue() != null && CONTAPAGARcbForma.getValue().contains("vista") && (CONTAPAGARdpDataVencimento.getValue() == null || CONTAPAGARdpDataVencimento.getValue().isAfter(LocalDate.now())))
        {
            erro = true;
            CONTAPAGARdpDataVencimento.requestFocus();
            CONTAPAGARlbDataVencimento.setTextFill(Color.RED);
        }
        else
            CONTAPAGARlbDataVencimento.setTextFill(Color.BLACK);

        if(CONTAPAGARcbForma.getValue() != null && CONTAPAGARcbForma.getValue().contains("prazo") && ctrLancarContaPagar.getCtrcontapagar().getConta().getParcelas().size() == 0)
        {
            erro = true;
            CONTAPAGARbtConfigurar.requestFocus();
            PARCELASPAGARlbParcelas.setTextFill(Color.RED);
        }
        else
            PARCELASPAGARlbParcelas.setTextFill(Color.BLACK);

        if(CONTAPAGARcbForma.getValue() == null)
        {
            erro = true;
            CONTAPAGARcbForma.requestFocus();
            CONTAPAGARlbForma.setTextFill(Color.RED);
        }
        else
            CONTAPAGARlbForma.setTextFill(Color.BLACK);

        if(CONTAPAGARtfPessoa.getText().equals(""))
        {
            erro = true;
            CONTAPAGARbtSelecionarPessoaEmpresa.requestFocus();
            CONTAPAGARlbPessoa.setTextFill(Color.RED);
        }
        else
            CONTAPAGARlbPessoa.setTextFill(Color.BLACK);

        if(CONTAPAGARtfValor.getText().equals("") || Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")) == 0)
        {
            erro = true;
            CONTAPAGARtfValor.requestFocus();
            CONTAPAGARlbValor.setTextFill(Color.RED);
        }
        else
            CONTAPAGARlbValor.setTextFill(Color.BLACK);

        if(CONTAPAGARtfDescricao.getText().equals(""))
        {
            erro = true;
            CONTAPAGARtfDescricao.requestFocus();
            CONTAPAGARlbDescricao.setTextFill(Color.RED);
        }
        else
            CONTAPAGARlbDescricao.setTextFill(Color.BLACK);


        if(erro)
            return;

        if(CONTAPAGARcbForma.getValue().contains("vista") || (CONTAPAGARcbForma.getValue().contains("prazo") && !ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada().equals("") && Float.parseFloat(ctrLancarContaPagar.getCtrcontapagar().getConta().getValor_entrada().replace(",", ".")) > 0))
        {
            ExibirTelaFormasPagamento(true); //farei o recebimento a vista pela tela de pagamentos, após informar dados do pagamento (dinheiro, cheque, deposito, boleto)
            String msg = "";
            if(CONTAPAGARcbForma.getValue().contains("vista"))                
                msg = "Informe os dados do pagamento que será feito à vista.";
            else
                msg = "Informe os dados do pagamento referentes ao valor de entrada.";  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText(msg);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return;
        }

        //NÃO TEM PAGAMENTO A VISTA, NEM VALOR DE ENTRADA, SERÁ TUDO PARCELADO A PRAZO!
        Banco.con.IniciarTransacao();
        if(CONTAPAGARbtConfirmar.getTooltip().getText().contains("Lançar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrLancarContaPagar.getCtrcontapagar().Salvar(CONTAPAGARtfDescricao.getText(), Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), 0, ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas(), codigo_pessoa, codigo_empresa, "", null, CONTAPAGARtfObservacoes.getText());
        }
        else //Alterando
        {     
            if(ctrLancarContaPagar.getCtrcontapagar().getConta().temParcelaPag()) //só permito atualizar os dados                
               erro = !ctrLancarContaPagar.getCtrcontapagar().Atualizar(CONTAPAGARtfDescricao.getText(), codigo_pessoa, codigo_empresa, CONTAPAGARtfObservacoes.getText());
            else  
            {
               if(!ctrLancarContaPagar.getCtrcontapagar().Desativar()) //vou desativar a conta e as parcelas antigas
               {   
                   Banco.con.Rollback("");
                   return;
               }
               //agora vou inserir a nova conta e as parcelas atuais
               erro = !ctrLancarContaPagar.getCtrcontapagar().Salvar(CONTAPAGARtfDescricao.getText(), Float.parseFloat(CONTAPAGARtfValor.getText().replace(",", ".")), 0, ctrLancarContaPagar.getCtrcontapagar().getConta().getNum_parcelas(), codigo_pessoa, codigo_empresa, "", null, CONTAPAGARtfObservacoes.getText());
            }
        }

        if(!erro)
        {       
           Banco.con.Commit("Cadastrar/alterar conta a pagar.");
           estadoOriginalContaPagar();
           ctrLancarContaPagar.CarregarTabela(CONTAPAGARtabela, CONTAPAGARtxPesquisa.getText());
        } 
        else
           Banco.con.Rollback("");
        
        
        
    }

    @FXML
    private void clkTabelaParcelasPagar(MouseEvent event) {
        if(tabelaParcelasPagar.getSelectionModel().getSelectedItem() != null)
        {
            btVerPagamento.setDisable(false);
            ctrRealizarPagamento.getCtrpagamento().setParcela(tabelaParcelasPagar.getSelectionModel().getSelectedItem());
            if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().getData_pagamento() == null) //não está pago
            {
               btPagar.setDisable(false);
               btEstornarPagamento.setDisable(true);
            }
            else //está pago
            {
                btPagar.setDisable(true);
                //posso estornar?
                btEstornarPagamento.setDisable(!ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getParcelapag().podeEstornar());                
            }
                
        }
    }

    @FXML
    private void clkFiltrarParcelasPagar(ActionEvent event) {
        if(cbFiltrarParcelasPagar.getValue().contains("hoje"))
        {
            ckbFiltrarPeriodoPagar.setSelected(false);
            ckbFiltrarPeriodoPagar.setDisable(true);            
        }
        else
        {
            ckbFiltrarPeriodoPagar.setDisable(false);            
        }
        dpDataInicialPagar.setDisable(!ckbFiltrarPeriodoPagar.isSelected());
        dpDataFinalPagar.setDisable(!ckbFiltrarPeriodoPagar.isSelected());
        FiltrarPagar();
    }

    @FXML
    private void clkSelecionarPessoaEmpresa(ActionEvent event) throws IOException {
        //Escolher opção
        EscolherOpcaoController.opcao = 0; //não escolheu nenhuma opção por padrão
        Stage stage = new Stage();
        stage.setTitle("Selecionar");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaEscolherOpcao = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EscolherOpcao.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        if(EscolherOpcaoController.opcao == 0) //não escolheu nenhuma
            return;
        
        if(EscolherOpcaoController.opcao == 1) //selecionar empresa
        {
            int emp_aux = SelecionarEmpresaController.empresa;
            stage = new Stage();
            stage.setTitle("Selecionar Empresa");
            stage.getIcons().add(new Image(url.toString()));
            TelaSelecionarEmpresa = stage;
            scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarEmpresa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarEmpresaController.empresa != 0) //retorn o código da empresa, 0 se não retornou!
            { //retornou alguma empresa
                SelecionarPessoaController.pessoa = 0;
                tfPessoaEmpresa.setText(ctrGerenciarEmpresas.getCtrempresa().getEmpresa().getEmpresa(SelecionarEmpresaController.empresa).getNome()+" ("+ctrGerenciarEmpresas.getCtrempresa().getEmpresa().getEmpresa(SelecionarEmpresaController.empresa).getCnpj()+")");
                if(tfPessoaEmpresa.getText().contains("()"))
                    tfPessoaEmpresa.setText(tfPessoaEmpresa.getText().replace("()", ""));
                ckbFiltrarPorPessoaEmpresa.setDisable(false);
                ckbFiltrarPorPessoaEmpresa.setSelected(true);

            }
            else
               SelecionarEmpresaController.empresa = emp_aux; 
        }
        
        if(EscolherOpcaoController.opcao == 2) //selecionar pessoa
        {
            int pes_aux = SelecionarPessoaController.pessoa; 
            stage = new Stage();
            stage.setTitle("Selecionar Pessoa");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            TelaSelecionarPessoa = stage;
            scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            { //retornou alguma pessoa
                SelecionarEmpresaController.empresa = 0;
                tfPessoaEmpresa.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome()+" ("+ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getCpf()+")");
                if(tfPessoaEmpresa.getText().contains("()"))
                    tfPessoaEmpresa.setText(tfPessoaEmpresa.getText().replace("()", ""));
                ckbFiltrarPorPessoaEmpresa.setDisable(false);
                ckbFiltrarPorPessoaEmpresa.setSelected(true);

            }
            else
               SelecionarPessoaController.pessoa = pes_aux;
        }
        
        FiltrarPagar();
    }

    @FXML
    private void clkDataInicialPagar(ActionEvent event) {
        if(dpDataInicialPagar.getValue() != null)
           FiltrarPagar();
    }

    @FXML
    private void clkDataFinalPagar(ActionEvent event) {
        if(dpDataFinalPagar.getValue() != null)
           FiltrarPagar();
    }

    @FXML
    private void clkFiltrarPeriodoPagar(ActionEvent event) {
        dpDataInicialPagar.setDisable(!ckbFiltrarPeriodoPagar.isSelected());
        dpDataFinalPagar.setDisable(!ckbFiltrarPeriodoPagar.isSelected());
        FiltrarPagar();
    }

    @FXML
    private void clkFiltrarPorPessoaEmpresa(ActionEvent event) {
        FiltrarPagar();
    }

    @FXML
    private void clkPagar(ActionEvent event) throws SQLException, IOException {
        if(tabelaParcelasPagar.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait(); 
            return;
        }
        if(tabelaParcelasPagar.getSelectionModel().getSelectedItem().temParcelaAnteriorAberta())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Existem parcelas anteriores à esta com pagamento pendente.\nDeseja realmente realizar o pagamento desta parcela ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() != buttonTypeSim)
                return;
        }
        //CAIXA ABERTO?
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() == null || !ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Caixa fechado");
            alert.setContentText("O caixa está fechado.\nDeseja abrir o caixa ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() == buttonTypeSim) //abrir caixa
            {
                Banco.con.IniciarTransacao();
                if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() != null)
                {
                   
                    ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                    if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().abrir()) 
                       Banco.con.Commit("Abrir caixa.");
                    else
                    {
                        Banco.con.Rollback("Não foi possível abrir o caixa.");
                        return; 
                    }
                }
                else
                    if(!InformarSaldoInicial()) //se retornar falso é pq o caixa está fechado
                        return;
            }
            else
                return;           
        }
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
        //VALIDEI
        //FAÇO PAGAMENTO      
        parcelapag = tabelaParcelasPagar.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Realizar Pagamento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarPagamento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarPagamento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();  
        FiltrarPagar();
        btEstornarPagamento.setDisable(true);
        btPagar.setDisable(true);
        btVerPagamento.setDisable(true);
    }

    @FXML
    private void clkEstornarPagamento(ActionEvent event) throws SQLException, IOException {
        if(tabelaParcelasPagar.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait(); 
            return;
        }
        //CAIXA ABERTO?
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
        if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() == null || !ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().estaAberto()) //caixa fechado
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Caixa fechado");
            alert.setContentText("O caixa está fechado.\nDeseja abrir o caixa ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa() != null)
                {
                    ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().setUsuario(ctrGerenciarPessoas.getCtrpessoa().getUsuario().getCod());
                    if(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().abrir()) 
                       Banco.con.Commit("Abrir caixa.");
                    else
                    {
                        Banco.con.Rollback("Não foi possível abrir o caixa.");
                        return; 
                    }
                }
                else
                    if(!InformarSaldoInicial()) //se retornar falso é pq o caixa está fechado
                        return;
            }
            else
                return;           
        }
        ctrRealizarPagamento.Novo();
        ctrRealizarPagamento.getCtrpagamento().setCaixa(ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getUltimoCaixa());
        //FAÇO ESTORNO DO PAGAMENTO       
        parcelapag = tabelaParcelasPagar.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Estornar Pagamento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarPagamento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarPagamento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait(); 
        FiltrarPagar();
        btEstornarPagamento.setDisable(true);
        btPagar.setDisable(true);
        btVerPagamento.setDisable(true);
    }

    @FXML
    private void clkVerPagamento(ActionEvent event) throws IOException {
        if(tabelaParcelasPagar.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione uma parcela.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait(); 
            return;
        }
        
        parcelapag = tabelaParcelasPagar.getSelectionModel().getSelectedItem().getSeq();
        caixa = ctrRealizarPagamento.getCtrpagamento().getMovCaixa().getCaixa().getCod();
        Stage stage = new Stage();
        stage.setTitle("Ver Pagamento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        //RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaRealizarPagamento = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RealizarPagamento.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait(); 
    }

    @FXML
    private void clkLimparPagamento(ActionEvent event) {
        estadoOriginalPagamentos();
    }
    
    //////////////////// TELA GERENCIAR EMPRESAS /////////////////////

    private void PesquisarEmpresa()
    {
        EMPRESASbtAlterar.setDisable(true);
        EMPRESASbtApagar.setDisable(true);
        String filtro = EMPRESAStxPesquisa.getText();
        filtro = filtro.replace("(", "");
        filtro = filtro.replace(")", "");
        filtro = filtro.replace("-", "");
        filtro = filtro.replace(".", "");
        filtro = filtro.replace("/", "");
        if(Funcoes.isFloat(filtro)) //pesquisa por cnpj
        {
            EMPRESAStxPesquisarCNPJ.setText(filtro);
            ctrGerenciarEmpresas.CarregarTabelaEmpresas(EMPRESAStabela, EMPRESAStxPesquisarCNPJ.getText());
        }
        else
            ctrGerenciarEmpresas.CarregarTabelaEmpresas(EMPRESAStabela, EMPRESAStxPesquisa.getText());
    }
    
    @FXML
    private void EMPRESASkpPesquisar(KeyEvent event) {
        //implementar se o Usuário apertar ENTER
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
           PesquisarEmpresa();
    }

    @FXML
    private void EMPRESASclkIr(ActionEvent event) {
        PesquisarEmpresa();
    }

    @FXML
    private void EMPRESASclkTabela(MouseEvent event) {
        if(EMPRESAStabela.getSelectionModel().getSelectedItem() != null)
        {
            EMPRESASbtAlterar.setDisable(false);
            EMPRESASbtApagar.setDisable(false);
        }    
    }
    
    private void NovaEmpresa()
    {
        EMPRESASlbCamposObrigatorios.setVisible(true);
        EMPRESASbtConfirmar.getTooltip().setText("Cadastrar empresa");
        EMPRESAScbCidade.setPromptText("Selecione o UF");
        apTabelaEmpresas.setDisable(true);
        apDadosEmpresas.setDisable(false);
        EMPRESASbtCancelar.setDisable(false);
        EMPRESASbtConfirmar.setDisable(false);
        EMPRESAStfCod.setDisable(true);
        ctrGerenciarEmpresas.Novo(); 
        //EMPRESAScbUF.getSelectionModel().select("PR");
        //EMPRESAScbCidade.getSelectionModel().select("Colorado");
        EMPRESAStfNome.requestFocus();
    }

    @FXML
    private void EMPRESASclkNovo(ActionEvent event) {
        NovaEmpresa();
    }

    
    private void CarregarDadosEmpresa()
    {
        EMPRESAStfCod.setText(EMPRESAStabela.getSelectionModel().getSelectedItem().getCod() + "");
        EMPRESAStfBairro.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getBairro());
        EMPRESAStfCEP.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getCep());
        EMPRESAStfCNPJ.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getCnpj()+ "");
        EMPRESAStfEmail.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getEmail());
        EMPRESAStfLogradouro.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getLogradouro());
        EMPRESAStfNome.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getNome());
        EMPRESAStfNum.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getNumero());
        EMPRESAStfTelfone.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getFone());
        EMPRESAStfTelfone2.setText((EMPRESAStabela.getSelectionModel().getSelectedItem()).getFone2());        
        
        
        EMPRESAScbTipoLog.getSelectionModel().select((EMPRESAStabela.getSelectionModel().getSelectedItem()).getTipoLog());
        String uf = (EMPRESAStabela.getSelectionModel().getSelectedItem()).getCidade().getEstado().toString();
        EMPRESAScbUF.getSelectionModel().select(uf);
        ctrGerenciarEmpresas.CarregarComboBox(EMPRESAScbCidade, "cidade", "estado.est_sgl LIKE '"+uf+"'");
        EMPRESAScbCidade.getSelectionModel().select((EMPRESAStabela.getSelectionModel().getSelectedItem()).getCidade().toString());
        
        
        
        BufferedImage bimg = ctrGerenciarEmpresas.getCtrempresa().LerImagem(EMPRESAStabela.getSelectionModel().getSelectedItem().getCod());
        if(bimg != null)
        {
            EMPRESASimg.setImage(SwingFXUtils.toFXImage(bimg, null));
            EMPRESASimg.setId("0");
        }
        
        ctrGerenciarEmpresas.Novo();
        ctrGerenciarEmpresas.getCtrempresa().getEmpresa().setCod(EMPRESAStabela.getSelectionModel().getSelectedItem().getCod());
    }
    
    @FXML
    private void EMPRESASclkAlterar(ActionEvent event) {
        EMPRESASlbCamposObrigatorios.setVisible(true);
        EMPRESASbtConfirmar.getTooltip().setText("Alterar empresa");
        CarregarDadosEmpresa();
        apTabelaEmpresas.setDisable(true);
        apDadosEmpresas.setDisable(false);
        EMPRESASbtCancelar.setDisable(false);
        EMPRESASbtConfirmar.setDisable(false); 
        EMPRESAStfCod.setDisable(true);
    }

    @FXML
    private void EMPRESASclkApagar(ActionEvent event) 
    {
        EMPRESASlbCamposObrigatorios.setVisible(false);
        EMPRESASbtConfirmar.getTooltip().setText("Desativar empresa");
        CarregarDadosEmpresa();
        apTabelaEmpresas.setDisable(true);
        apDadosEmpresas.setDisable(false);
        
        EMPRESASModoExibicao(true);
        EMPRESASbtCancelar.setDisable(false);
        EMPRESASbtConfirmar.setDisable(false);
    
    }

    @FXML
    private void EMPRESASclkCarregaIMG(ActionEvent event) {
        Image foto = null;
        FileChooser fc = new FileChooser();
        Node source = (Node) event.getSource();
        fc.setTitle("Selecione a Imagem");    
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Imagens (.gif, .jpg, .png)", "*.jpg", "*.png", "*.gif"));
        
        File arq=fc.showOpenDialog(source.getScene().getWindow());
        if(arq!=null)
        {
            foto = new Image(arq.toURI().toString());
            EMPRESASimg.setImage(foto);
            EMPRESASimg.setId("0");
        }
    }

    @FXML
    private void EMPRESASclkApagaIMG(ActionEvent event) {
        java.net.URL url = getClass().getResource("imagens/logotipo.png");
        EMPRESASimg.setImage(new Image(url.toString()));
        EMPRESASimg.setId("1");
    }

    @FXML
    private void EMPRESASclkCamera(MouseEvent event) {
        if(webcam == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Câmera não detectada.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return;
        }
        if(!webcam.isOpen())
        {
            webcam.setViewSize(new Dimension(320,240));
            webcam.open();
        } 
                
        
        BufferedImage bimage = webcam.getImage();
        Image imagem;
        imagem = SwingFXUtils.toFXImage(bimage, null);
        
        EMPRESASimg.setImage(imagem);
        EMPRESASimg.setId("0");
        
        webcam.close();
    }

    @FXML
    private void EMPRESASclkBuscarCep(ActionEvent event) {
        if(EMPRESAStfCEP.getText().equals(""))
        {
            EMPRESAStfCEP.requestFocus();
            EMPRESASlbCep.setTextFill(Color.RED);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Digite um CEP válido para buscar a localização do mesmo.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return;  
        }
        EMPRESASlbCep.setTextFill(Color.BLACK);
        
        EMPRESAStfBairro.setText("");
        EMPRESAStfLogradouro.setText("");
        EMPRESAScbTipoLog.getSelectionModel().clearSelection();
        EMPRESAScbUF.getSelectionModel().clearSelection();
        EMPRESAScbCidade.getSelectionModel().clearSelection();
        
        String endereco = ctrGerenciarEmpresas.consultaCep(EMPRESAStfCEP.getText(),"json", "261641425", "64669093", "177.131.35.1", "3128"); //com proxy
        //String endereco = consultaCep(tfCEP.getText(), "json"); //sem proxy
        
        //System.out.println(endereco);
        JSONObject jdados = new JSONObject(endereco);
        //System.out.println(obj.getString("cidade")); //cidade é o campo chave, retorna o nome da cidade
        EMPRESAStfBairro.setText(jdados.getString("bairro"));
        EMPRESAStfLogradouro.setText(jdados.getString("logradouro"));
        
        
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        int achou = 0;
        for(int i=0; achou == 0 && i<EMPRESAScbTipoLog.getItems().size();i++)
            if(EMPRESAScbTipoLog.getItems().get(i).equals(jdados.getString("tipo_logradouro"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               EMPRESAScbTipoLog.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbTipoLog.getSelectionModel().getSelectedIndex());
        
        
        
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        achou = 0;
        for(int i=0; achou == 0 && i<EMPRESAScbUF.getItems().size();i++)
            if(EMPRESAScbUF.getItems().get(i).equals(jdados.getString("uf"))) //achei o cara
            {
               //cbUF.setValue(cbUF.getItems().get(i));
               EMPRESAScbUF.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbUF.getSelectionModel().getSelectedIndex());
        
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        achou  = 0;
        for(int i=0; achou == 0 && i<EMPRESAScbCidade.getItems().size();i++)
            if(EMPRESAScbCidade.getItems().get(i).equals(jdados.getString("cidade"))) //achei o cara
            {
               //cbCidade.setValue(cbCidade.getItems().get(i));
               EMPRESAScbCidade.getSelectionModel().select(i);
               achou = 1;
            }
        //System.out.println(cbCidade.getSelectionModel().getSelectedIndex());
        
        //if(achou == 0) //nao achou cep, cep inválido!
    }

    @FXML
    private void EMPRESASkpUF(KeyEvent event) {
        if(EMPRESAScbUF.getValue() != null)
        {
           EMPRESAScbCidade.setPromptText("Escolha a Cidade");
           EMPRESAScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarEmpresas.CarregarComboBox(EMPRESAScbCidade, "cidade", "estado.est_sgl LIKE '"+EMPRESAScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(EMPRESAScbCidade);
    }

    @FXML
    private void EMPRESASclkUF(ActionEvent event) {
        if(EMPRESAScbUF.getValue() != null)
        {
           EMPRESAScbCidade.setPromptText("Escolha a Cidade");
           EMPRESAScbCidade.getSelectionModel().clearSelection();
        }
        ctrGerenciarEmpresas.CarregarComboBox(EMPRESAScbCidade, "cidade", "estado.est_sgl LIKE '"+EMPRESAScbUF.getValue()+"'");
        new ComboBoxAutoComplete<String>(EMPRESAScbCidade);
    }

    @FXML
    private void EMPRESASclkCancelar(ActionEvent event) {
        estadoOriginalEmpresa();
    }

    @FXML
    private void EMPRESASclkConfirmar(ActionEvent event) throws SQLException {
        if(EMPRESASbtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrGerenciarEmpresas.getCtrempresa().Excluir())
                {
                   Banco.con.Commit("Desativar empresa."); 
                   estadoOriginalEmpresa();
                   PesquisarEmpresa();
                }
                else
                   Banco.con.Rollback("");
            }
            return;
        }        

        boolean erro = false;

        

        if(!EMPRESAStfEmail.getText().equals("") && !Funcoes.isEmail(EMPRESAStfEmail.getText()))
        {
            erro = true;
            EMPRESAStfEmail.requestFocus();
            EMPRESASlbEmail.setTextFill(Color.RED);
        }
        else
            EMPRESASlbEmail.setTextFill(Color.BLACK);

        if(!EMPRESAStfTelfone2.getText().equals("") && !Funcoes.ValidaTelefone(EMPRESAStfTelfone2.getText()))
        {
            erro = true;
            EMPRESAStfTelfone2.requestFocus();
            EMPRESASlbTelefone2.setTextFill(Color.RED);

        }
        else
            EMPRESASlbTelefone2.setTextFill(Color.BLACK);
        if(!EMPRESAStfTelfone.getText().equals("") && !Funcoes.ValidaTelefone(EMPRESAStfTelfone.getText()))
        {
            erro = true;
            EMPRESAStfTelfone.requestFocus();
            EMPRESASlbTelefone.setTextFill(Color.RED);
        }
        else
            EMPRESASlbTelefone.setTextFill(Color.BLACK);
        if(EMPRESAScbCidade.getValue() == null)
        {
            erro = true;
            EMPRESAScbCidade.requestFocus();
            EMPRESASlbCidade.setTextFill(Color.RED);
        }
        else
            EMPRESASlbCidade.setTextFill(Color.BLACK);
        if(EMPRESAScbUF.getValue() == null)
        {
            erro = true;
            EMPRESAScbUF.requestFocus();
            EMPRESASlbUf.setTextFill(Color.RED);
        }
        else
            EMPRESASlbUf.setTextFill(Color.BLACK);
        /*if(EMPRESAStfBairro.getText().equals(""))
        {
            erro = true;
            EMPRESAStfBairro.requestFocus();
            EMPRESASlbBairro.setTextFill(Color.RED);
        }
        else
            EMPRESASlbBairro.setTextFill(Color.BLACK);*/
        if(!EMPRESAStfCEP.getText().equals("") && !Funcoes.ValidaCEP(EMPRESAStfCEP.getText()))
        {
            erro = true;
            EMPRESAStfCEP.requestFocus();
            EMPRESASlbCep.setTextFill(Color.RED);
        }
        else
            EMPRESASlbCep.setTextFill(Color.BLACK);
        if(!EMPRESAStfNum.getText().equals("") && !Funcoes.isInt(EMPRESAStfNum.getText()))
        {
            erro = true;
            EMPRESAStfNum.requestFocus();
            EMPRESASlbNumero.setTextFill(Color.RED);
        }
        else
            EMPRESASlbNumero.setTextFill(Color.BLACK);
        /*
        if(EMPRESAStfLogradouro.getText().equals(""))
        {
            erro = true;
            EMPRESAStfLogradouro.requestFocus();
            EMPRESASlbLog.setTextFill(Color.RED);
        }
        else
            EMPRESASlbLog.setTextFill(Color.BLACK);
        if(EMPRESAScbTipoLog.getSelectionModel().isEmpty())
        {
            erro = true;
            EMPRESAScbTipoLog.requestFocus();
            EMPRESASlbTipoLog.setTextFill(Color.RED);
        }
        else
            EMPRESASlbTipoLog.setTextFill(Color.BLACK);*/
        
        if(EMPRESAStfNome.getText().equals(""))
        {
            erro = true;
            EMPRESAStfNome.requestFocus();
            EMPRESASlbNome.setTextFill(Color.RED);
        }
        else
            EMPRESASlbNome.setTextFill(Color.BLACK);
        if(!EMPRESAStfCNPJ.getText().equals("") && !Funcoes.ValidaCNPJ(EMPRESAStfCNPJ.getText()))
        {
            erro = true;
            EMPRESAStfCNPJ.requestFocus();
            EMPRESASlbCnpj.setTextFill(Color.RED);
        }
        else
            EMPRESASlbCnpj.setTextFill(Color.BLACK);
        

        if(erro)
            return;



        Banco.con.IniciarTransacao();
        if(EMPRESASbtConfirmar.getTooltip().getText().contains("Cadastrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrGerenciarEmpresas.getCtrempresa().Salvar(EMPRESAStfNome.getText(), EMPRESAStfCNPJ.getText(), EMPRESAStfBairro.getText(), EMPRESAScbTipoLog.getValue(), EMPRESAStfLogradouro.getText(), EMPRESAStfNum.getText(), EMPRESAStfTelfone.getText(), EMPRESAStfEmail.getText(), EMPRESAStfCEP.getText(), EMPRESAScbCidade.getValue(), EMPRESAScbUF.getValue(), EMPRESAStfTelfone2.getText());
        }
        else //Alterando
        {                
            erro = !ctrGerenciarEmpresas.getCtrempresa().Alterar(Integer.parseInt(EMPRESAStfCod.getText()), EMPRESAStfNome.getText(), EMPRESAStfCNPJ.getText(), EMPRESAStfBairro.getText(), EMPRESAScbTipoLog.getValue(), EMPRESAStfLogradouro.getText(), EMPRESAStfNum.getText(), EMPRESAStfTelfone.getText(), EMPRESAStfEmail.getText(), EMPRESAStfCEP.getText(), EMPRESAScbCidade.getValue(), EMPRESAScbUF.getValue(), EMPRESAStfTelfone2.getText());
        }

        if(!EMPRESASimg.getId().equals("1"))
        {            
            if(!ctrGerenciarEmpresas.getCtrempresa().SalvarImagem(SwingFXUtils.fromFXImage(EMPRESASimg.getImage(), null)))
            {
                Banco.con.Rollback("");
                return;
            }
        }
        else
        {
            if(!ctrGerenciarEmpresas.getCtrempresa().SalvarImagem(null))
            {
                Banco.con.Rollback("");
                return;
            }
        }

        if(!erro)
        {     
           Banco.con.Commit("Cadastrar/alterar empresa.");
           estadoOriginalEmpresa();
           PesquisarEmpresa();
        }
        else
           Banco.con.Rollback("");
        
        
    }

    @FXML
    private void clkContasPagar(MouseEvent event) throws SQLException {
        imvContas.setId("imvContasPagar");
        HabilitaTela(imvContas);
    }

    @FXML
    private void clkContasReceber(MouseEvent event) throws SQLException {
        imvContas.setId("imvContasReceber");
        HabilitaTela(imvContas);
    }
    
    @FXML
    private void clkRealizarPagamento(MouseEvent event) throws SQLException {
        imvRecebimentos.setId("imvPagamentos");
        HabilitaTela(imvRecebimentos);
    }

    @FXML
    private void clkRealizarRecebimento(MouseEvent event) throws SQLException {
        imvRecebimentos.setId("imvRecebimentos");
        HabilitaTela(imvRecebimentos);
    }

    @FXML
    private void clickMenuRelPessoas(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Emitir Relatório de Pessoas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRelatorioPessoas = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioPessoas.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
    }

    @FXML
    private void clickMenuRelVeiculos(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Emitir Relatório de Veículos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRelatorioVeiculos = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioVeiculos.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
    }

    @FXML
    private void onMouseExitRelOutros(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subOutros.png");
        imvRelOutros.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuRelOutros(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Emitir Outros Relatórios");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRelatorioOutros = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioOutros.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
    }

    @FXML
    private void OnMouseOverRelOutros(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subOutros2.png");
        imvRelOutros.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickMenuRelContas(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Emitir Relatório Financeiro");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRelatorioContas = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioContas.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();
    }
    
    @FXML
    private void clickMenuRelEventos(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Emitir Relatório de Eventos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaRelatorioEventos = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RelatorioEventos.fxml")));
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);        
        stage.showAndWait();        
    }

    @FXML
    private void onMouseExitFinanceiro(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subFinanceiro.png");
        imvRelFinanceiro.setImage(new Image(url.toString()));
    }

    @FXML
    private void OnMouseOverFinanceiro(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subFinanceiro2.png");
        imvRelFinanceiro.setImage(new Image(url.toString()));
    }

    @FXML
    private void EVENTOkpPesquisar(KeyEvent event) {
        final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
        if(ENTER.match(event))
        {
           EVENTObtAlterar.setDisable(true);
           EVENTObtApagar.setDisable(true);
           ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
        }
        
    }

    @FXML
    private void EVENTOclkIr(ActionEvent event) {
        EVENTObtAlterar.setDisable(true);
        EVENTObtApagar.setDisable(true);
        ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
    }

    @FXML
    private void EVENTOclkTabela(MouseEvent event) {
        if(EVENTOtabela.getSelectionModel().getSelectedItem() != null)
        {
            EVENTObtAlterar.setDisable(false);
            EVENTObtApagar.setDisable(false);
        }
    }
    
    private void NovoEvento()
    {
        EVENTOlbCamposObrigatorios.setVisible(true);
        codigo_pessoa = 0;
        EVENTObtConfirmar.getTooltip().setText("Registrar evento");
        apTabelaEventos.setDisable(true);
        apDadosEvento.setDisable(false);
        EVENTObtConfigurar.setTooltip(new Tooltip("Definir participantes"));
        
        EVENTObtCancelar.setDisable(false);
        EVENTObtConfirmar.setDisable(false);
        ctrRegistrarEvento.Novo();
        ObservableList <Node> componentes = aux_dadosevento.getChildren();
        
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof TextInputControl) //TextField, TextArea e HTMLeditor
                ((TextInputControl)n).setDisable(false);
            
            if(n instanceof ComboBox)
                //((ComboBox)n).getItems().clear();
                ((ComboBox)n).setDisable(false);
            
            if(n instanceof TableView)
                //((ComboBox)n).getItems().clear();
                ((TableView)n).setDisable(false);
            
            if(n instanceof Label)
                ((Label)n).setDisable(false);
        }
        EVENTOtfCod.setDisable(true);   
        //EVENTOapSituacao.setDisable(true);
        EVENTObtSelecionarPessoa.setDisable(false);
        EVENTOtfDescricao.requestFocus();        
    }

    @FXML
    private void EVENTOclkNovo(ActionEvent event) {
        NovoEvento();
    }

    private void CarregarDadosEvento()
    {
        ctrRegistrarEvento.getCtrevento().CarregarDados(EVENTOtabela.getSelectionModel().getSelectedItem().getCod());
        EVENTOtfCod.setText(EVENTOtabela.getSelectionModel().getSelectedItem().getCod()+"");
        
        if(EVENTOtabela.getSelectionModel().getSelectedItem().getStatus().equals("Andamento"))
            EVENTOrbAndamento.setSelected(true);
        else
            EVENTOrbFinalizado.setSelected(true);
        
        EVENTOtfDescricao.setText(EVENTOtabela.getSelectionModel().getSelectedItem().getDescricao());
        EVENTOcbCategoria.getSelectionModel().select((EVENTOtabela.getSelectionModel().getSelectedItem()).getCategoria().getDescricao());
        codigo_pessoa = EVENTOtabela.getSelectionModel().getSelectedItem().getResponsavel().getCod();
        EVENTOtfPessoa.setText(EVENTOtabela.getSelectionModel().getSelectedItem().getResponsavel().getNome());
        
        EVENTOdpDataInicial.setValue(Funcoes.StringToDate(EVENTOtabela.getSelectionModel().getSelectedItem().getData_inicial()));
        EVENTOdpDataFinal.setValue(Funcoes.StringToDate(EVENTOtabela.getSelectionModel().getSelectedItem().getData_final()));
        
        
        ctrRegistrarEvento.CarregarParticipantes(PARTICIPANTEtabela);
        if(PARTICIPANTEtabela.getItems().size() > 0)
           EVENTOtfParticipantes.setText(PARTICIPANTEtabela.getItems().size()+" participante(s)");
        
        EVENTOtfObservacoes.setText(EVENTOtabela.getSelectionModel().getSelectedItem().getObservacao());
        
    }
    
    @FXML
    private void EVENTOclkAlterar(ActionEvent event) {
        EVENTOlbCamposObrigatorios.setVisible(true);
        EVENTObtConfirmar.getTooltip().setText("Alterar evento");
        EVENTObtConfigurar.setTooltip(new Tooltip("Definir participantes"));
        CarregarDadosEvento();
        apTabelaEventos.setDisable(true);
        apDadosEvento.setDisable(false);
        EVENTObtCancelar.setDisable(false);
        EVENTObtConfirmar.setDisable(false); 
        EVENTOtfCod.setDisable(true);
        EVENTOtfDescricao.requestFocus();
    }

    @FXML
    private void EVENTOclkApagar(ActionEvent event) {
        EVENTOlbCamposObrigatorios.setVisible(false);
        EVENTObtConfigurar.setText("VER");
        EVENTObtConfirmar.getTooltip().setText("Desativar evento");
        EVENTObtConfigurar.setTooltip(new Tooltip("Ver participantes"));
        CarregarDadosEvento();
        apTabelaEventos.setDisable(true);
        apDadosEvento.setDisable(false);
        
        EVENTOSModoExibicao(true);
        
        EVENTObtCancelar.setDisable(false);
        EVENTObtConfirmar.setDisable(false);
    }


    @FXML
    private void PARTICIPANTEclkTabela(MouseEvent event) {
        if(!EVENTObtConfirmar.getTooltip().getText().contains("Desativar") && PARTICIPANTEtabela.getSelectionModel().getSelectedItem() != null)
        {
            PARTICIPANTETelabtAlterar.setDisable(false);
            PARTICIPANTETelabtExcluir.setDisable(false);
        }
    }


    @FXML
    private void EVENTOclkSelecionarPessoa(ActionEvent event) throws IOException {
        SelecionarPessoaController.pessoa = 0;
        Stage stage = new Stage();
        stage.setTitle("Selecionar Organizador");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
        TelaSelecionarPessoa = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        
        if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
        { //retornou alguma pessoa
            codigo_pessoa = SelecionarPessoaController.pessoa;
            EVENTOtfPessoa.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome()+" ("+ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getCpf()+")");
            if(EVENTOtfPessoa.getText().contains("()"))
                EVENTOtfPessoa.setText(EVENTOtfPessoa.getText().replace("()", ""));
            
        }
    }

    @FXML
    private void EVENTOclkConfigurar(ActionEvent event) {
        boolean erro = false;
        if(EVENTOdpDataFinal.getValue() == null)
        {
            erro = true;
            EVENTOdpDataFinal.requestFocus();
            EVENTOlbPeriodo.setTextFill(Color.RED);
        }
        else
            EVENTOlbPeriodo.setTextFill(Color.BLACK);
        
        if(EVENTOdpDataInicial.getValue() == null || (EVENTOdpDataFinal.getValue()!=null && EVENTOdpDataInicial.getValue().isAfter(EVENTOdpDataFinal.getValue())))
        {
            erro = true;
            EVENTOdpDataInicial.requestFocus();
            EVENTOlbPeriodo.setTextFill(Color.RED);
        }
        else
            if(!erro)
               EVENTOlbPeriodo.setTextFill(Color.BLACK);
        
        if(erro)
           return;
        
        apTabelaEventos.setVisible(false);  
        apParticipantes.setVisible(true);        
        apDadosEvento.setDisable(true);
        ctrRegistrarEvento.CarregarParticipantes(PARTICIPANTEtabela);
        PARTICIPANTETelabtExcluir.setDisable(true);
        PARTICIPANTETelabtAlterar.setDisable(true);
        PARTICIPANTEbtAjustar.setDisable(EVENTObtConfirmar.getTooltip().getText().contains("Desativar"));
        PARTICIPANTETelabtAdicionar.setDisable(EVENTObtConfirmar.getTooltip().getText().contains("Desativar"));
        PARTICIPANTETelabtConfirmar.setDisable(EVENTObtConfirmar.getTooltip().getText().contains("Desativar"));
        PARTICIPANTEtabela.getSelectionModel().clearSelection();
        PARTICIPANTETelabtAdicionar.requestFocus();
    }

    @FXML
    private void EVENTOclkCancelar(ActionEvent event) {
        estadoOriginalEventos();
    }

    @FXML
    private void EVENTOclkConfirmar(ActionEvent event) throws SQLException {
        if(EVENTObtConfirmar.getTooltip().getText().contains("Desativar")) //vou desativar
        {   
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desativar");
            alert.setContentText("Deseja realmente desativar ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                if(ctrRegistrarEvento.getCtrevento().Desativar()) //desativando apenas, para manter histórico, não precisando excluir movimentações em caixa, manter CONSISTÊNCIA
                {   
                    Banco.con.Commit("Desativar evento.");
                    estadoOriginalEventos();
                    ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
                }
                else
                    Banco.con.Rollback(""); 
            }
            return;
        }


        boolean erro = false;
        
        
        if(EVENTOdpDataFinal.getValue() == null)
        {
            erro = true;
            EVENTOdpDataFinal.requestFocus();
            EVENTOlbPeriodo.setTextFill(Color.RED);
        }
        else
            EVENTOlbPeriodo.setTextFill(Color.BLACK);
        
        if(EVENTOdpDataInicial.getValue() == null || (EVENTOdpDataFinal.getValue()!=null && EVENTOdpDataInicial.getValue().isAfter(EVENTOdpDataFinal.getValue())))
        {
            erro = true;
            EVENTOdpDataInicial.requestFocus();
            EVENTOlbPeriodo.setTextFill(Color.RED);
        }
        else
            if(!erro)
              EVENTOlbPeriodo.setTextFill(Color.BLACK);
        
        if(EVENTOtfPessoa.getText().equals(""))
        {
            erro = true;
            EVENTObtSelecionarPessoa.requestFocus();
            EVENTOlbPessoa.setTextFill(Color.RED);
        }
        else
            EVENTOlbPessoa.setTextFill(Color.BLACK);
        if(EVENTOcbCategoria.getValue() == null)
        {
            erro = true;
            EVENTOcbCategoria.requestFocus();
            EVENTOlbCategoria.setTextFill(Color.RED);
        }
        else
            EVENTOlbCategoria.setTextFill(Color.BLACK);
        if(EVENTOtfDescricao.getText().equals(""))
        {
            erro = true;
            EVENTOtfDescricao.requestFocus();
            EVENTOlbDescricao.setTextFill(Color.RED);
        }
        else
            EVENTOlbDescricao.setTextFill(Color.BLACK);


        if(erro)
            return;

        String status = "Finalizado";
        if(EVENTOrbAndamento.isSelected())
           status = "Andamento";
        Banco.con.IniciarTransacao();
        if(EVENTObtConfirmar.getTooltip().getText().contains("Registrar")) //Adicionando uma nova Pessoa
        {
            erro = !ctrRegistrarEvento.getCtrevento().Registrar(status, EVENTOtfDescricao.getText(), EVENTOcbCategoria.getValue(), codigo_pessoa, EVENTOdpDataInicial.getValue(), EVENTOdpDataFinal.getValue(), EVENTOtfObservacoes.getText());
        }
        else //Alterando
        {                
            erro = !ctrRegistrarEvento.getCtrevento().Alterar(status, EVENTOtfDescricao.getText(), EVENTOcbCategoria.getValue(), codigo_pessoa, EVENTOdpDataInicial.getValue(), EVENTOdpDataFinal.getValue(), EVENTOtfObservacoes.getText());
        }
        

        if(!erro)
        {
            Banco.con.Commit("Registrar/alterar evento.");            
            estadoOriginalEventos();
            ctrRegistrarEvento.CarregarTabela(EVENTOtabela, EVENTOtxPesquisa.getText(), EVENTOcbFiltrarCategoria.getValue());
        }
        else
            Banco.con.Rollback("");
        
    }

    @FXML
    private void PARTICIPANTEclkSelecionar(ActionEvent event) throws IOException {
        if(PARTICIPANTEbtSelecionar.getText().equals("SELECIONAR"))
        {
            SelecionarPessoaController.pessoa = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Pessoa");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            TelaSelecionarPessoa = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            { //retornou alguma pessoa

                codigo_participante = SelecionarPessoaController.pessoa;
                PARTICIPANTEtfParticipante.setText(ctrGerenciarPessoas.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome());
                PARTICIPANTEbtSelecionar.setText("REMOVER");
                PARTICIPANTEbtSelecionar.getTooltip().setText("Remover participante");

            }
        }
        else //remover
        {
            codigo_participante = 0;
            PARTICIPANTEtfParticipante.setText("");
            PARTICIPANTEbtSelecionar.setText("SELECIONAR");
            PARTICIPANTEbtSelecionar.getTooltip().setText("Selecionar participante");
        }
    }

    @FXML
    private void PARTICIPANTEclkCancelar(ActionEvent event) {
        PARTICIPANTEapTabela.setVisible(true);
        PARTICIPANTEapAdicionar.setVisible(false);
        PARTICIPANTETelabtCancelar.setVisible(true);
        PARTICIPANTETelabtConfirmar.setVisible(true);
        PARTICIPANTETelabtExcluir.setDisable(true);
        PARTICIPANTETelabtAlterar.setDisable(true);
        PARTICIPANTEtabela.getSelectionModel().clearSelection();
        PARTICIPANTETelabtAdicionar.requestFocus();
    }

    @FXML
    private void PARTICIPANTEclkAdicionar(ActionEvent event) {
        boolean erro = false;
        
        if(PARTICIPANTEdpDataFinal.getValue() == null || PARTICIPANTEdpDataFinal.getValue().isAfter(EVENTOdpDataFinal.getValue()))
        {
            erro = true;
            PARTICIPANTEdpDataFinal.requestFocus();
            PARTICIPANTElbPeriodo.setTextFill(Color.RED);
        }
        else
            PARTICIPANTElbPeriodo.setTextFill(Color.BLACK);
        
        if(PARTICIPANTEdpDataInicial.getValue() == null ||  PARTICIPANTEdpDataInicial.getValue().isBefore(EVENTOdpDataInicial.getValue()) || (PARTICIPANTEdpDataFinal.getValue()!=null && PARTICIPANTEdpDataInicial.getValue().isAfter(PARTICIPANTEdpDataFinal.getValue())))
        {
            erro = true;
            PARTICIPANTEdpDataInicial.requestFocus();
            PARTICIPANTElbPeriodo.setTextFill(Color.RED);
        }
        else
            if(!erro)
               PARTICIPANTElbPeriodo.setTextFill(Color.BLACK);        
        
        if(PARTICIPANTEtfParticipante.getText().equals(""))
        {
           erro = true;
           PARTICIPANTElbParticipante.setTextFill(Color.RED);
           PARTICIPANTEbtSelecionar.requestFocus();
        }
        else
           PARTICIPANTElbParticipante.setTextFill(Color.BLACK); 
        
        if(erro)
           return;
        
        PARTICIPANTETelabtExcluir.setDisable(true);
        PARTICIPANTETelabtAlterar.setDisable(true);
        PARTICIPANTEtabela.getSelectionModel().clearSelection();
        //validei, vou adicionar ou alterar
        String autorizado = "S";
        if(PARTICIPANTErbNao.isSelected())
           autorizado = "N";
        if(PARTICIPANTEbtAdicionar.getText().equals("ADICIONAR"))
        {
            ctrRegistrarEvento.AdicionarParticipante(PARTICIPANTEtabela, codigo_participante, autorizado, PARTICIPANTEdpDataInicial.getValue(), PARTICIPANTEdpDataFinal.getValue());
        }
        else //alterar
        {
             ctrRegistrarEvento.AlterarParticipante(PARTICIPANTEtabela, codigo_participante, autorizado, PARTICIPANTEdpDataInicial.getValue(), PARTICIPANTEdpDataFinal.getValue());           
        }
        
        PARTICIPANTEapTabela.setVisible(true);
        PARTICIPANTEapAdicionar.setVisible(false);
        PARTICIPANTETelabtCancelar.setVisible(true);
        PARTICIPANTETelabtConfirmar.setVisible(true);        
        PARTICIPANTETelabtExcluir.setDisable(true);
        PARTICIPANTETelabtAlterar.setDisable(true);
        PARTICIPANTEtabela.getSelectionModel().clearSelection();
        PARTICIPANTETelabtAdicionar.requestFocus();
        
    }

    @FXML
    private void PARTICIPANTETelaclkAdicionar(ActionEvent event) {
        PARTICIPANTEbtSelecionar.getTooltip().setText("Selecionar participante");
        PARTICIPANTEapAdicionar.setVisible(true);
        PARTICIPANTEapTabela.setVisible(false);
        PARTICIPANTETelabtCancelar.setVisible(false);
        PARTICIPANTETelabtConfirmar.setVisible(false);
        PARTICIPANTEckbTodoEvento.setSelected(true);
        PARTICIPANTEdpDataInicial.setValue(EVENTOdpDataInicial.getValue());
        PARTICIPANTEdpDataFinal.setValue(EVENTOdpDataFinal.getValue());
        PARTICIPANTEdpDataInicial.setDisable(true);
        PARTICIPANTEdpDataFinal.setDisable(true);
        PARTICIPANTErbSim.setSelected(true);
        PARTICIPANTEbtSelecionar.requestFocus();
        codigo_participante = 0;
        PARTICIPANTEbtSelecionar.setText("SELECIONAR");
        PARTICIPANTEbtSelecionar.setDisable(false);
        PARTICIPANTEtfParticipante.setText("");
        PARTICIPANTEbtAdicionar.setText("ADICIONAR");
        PARTICIPANTElbParticipante.setTextFill(Color.BLACK);
        PARTICIPANTElbPeriodo.setTextFill(Color.BLACK);
        PARTICIPANTElbAutorizado.setTextFill(Color.BLACK);
    }

    @FXML
    private void PARTICIPANTETelaclkAlterar(ActionEvent event) {
        PARTICIPANTEbtSelecionar.getTooltip().setText("Remover participante");
        PARTICIPANTEapAdicionar.setVisible(true);
        PARTICIPANTEapTabela.setVisible(false);
        PARTICIPANTETelabtCancelar.setVisible(false);
        PARTICIPANTETelabtConfirmar.setVisible(false);
        PARTICIPANTEbtSelecionar.setText("REMOVER");
        PARTICIPANTEbtSelecionar.setDisable(true);
        PARTICIPANTEbtAdicionar.setText("ALTERAR");
        PARTICIPANTElbParticipante.setTextFill(Color.BLACK);
        PARTICIPANTElbPeriodo.setTextFill(Color.BLACK);
        PARTICIPANTElbAutorizado.setTextFill(Color.BLACK);
        
        codigo_participante = PARTICIPANTEtabela.getSelectionModel().getSelectedIndex(); //vou utilizar o indice para alterar depois
        
        //carregar dados
        PARTICIPANTEtfParticipante.setText(PARTICIPANTEtabela.getSelectionModel().getSelectedItem().getParticipante().getNome());
        
        if(PARTICIPANTEtabela.getSelectionModel().getSelectedItem().getAutorizado().equals("Sim"))
            PARTICIPANTErbSim.setSelected(true);
        else
            PARTICIPANTErbNao.setSelected(true);
        
        PARTICIPANTEdpDataInicial.setValue(Funcoes.StringToDate(PARTICIPANTEtabela.getSelectionModel().getSelectedItem().getData_inicio()));
        PARTICIPANTEdpDataFinal.setValue(Funcoes.StringToDate(PARTICIPANTEtabela.getSelectionModel().getSelectedItem().getData_fim()));
        
        PARTICIPANTEckbTodoEvento.setSelected(PARTICIPANTEdpDataInicial.getValue().isEqual(EVENTOdpDataInicial.getValue()) && PARTICIPANTEdpDataFinal.getValue().isEqual(EVENTOdpDataFinal.getValue()));
        
        PARTICIPANTEdpDataInicial.setDisable(PARTICIPANTEckbTodoEvento.isSelected());
        PARTICIPANTEdpDataFinal.setDisable(PARTICIPANTEckbTodoEvento.isSelected());
    }

    @FXML
    private void PARTICIPANTETelaclkExcluir(ActionEvent event) {
        if(PARTICIPANTEtabela.getSelectionModel().getSelectedItem() != null)
        {
            PARTICIPANTEtabela.getItems().remove(PARTICIPANTEtabela.getSelectionModel().getSelectedIndex());
        }
        PARTICIPANTETelabtExcluir.setDisable(true);
        PARTICIPANTETelabtAlterar.setDisable(true);
        PARTICIPANTEtabela.getSelectionModel().clearSelection();
        PARTICIPANTETelabtAdicionar.requestFocus();
    }

    @FXML
    private void PARTICIPANTETelaclkCancelar(ActionEvent event) {
        PARTICIPANTEapAdicionar.setVisible(false);
        apDadosEvento.setDisable(false);
        apParticipantes.setVisible(false);
        apTabelaEventos.setVisible(true);  
        EVENTObtConfigurar.requestFocus();
        
    }

    @FXML
    private void PARTICIPANTETelaclkConfirmar(ActionEvent event) {
        if(!ctrRegistrarEvento.SalvarParticipantes(PARTICIPANTEtabela, EVENTOdpDataInicial.getValue(), EVENTOdpDataFinal.getValue()))
           return;
        
        PARTICIPANTEapAdicionar.setVisible(false);
        apDadosEvento.setDisable(false);
        apParticipantes.setVisible(false);
        apTabelaEventos.setVisible(true);
        EVENTObtConfigurar.requestFocus();
        
        if(PARTICIPANTEtabela.getItems().size() == 0)
           EVENTOtfParticipantes.setText("");
        else
           EVENTOtfParticipantes.setText(PARTICIPANTEtabela.getItems().size()+" participante(s)");
    }

    @FXML
    private void PARTICIPANTEclkTodoEvento(ActionEvent event) {
        if(PARTICIPANTEckbTodoEvento.isSelected())
        {
            PARTICIPANTEdpDataInicial.setValue(EVENTOdpDataInicial.getValue());
            PARTICIPANTEdpDataFinal.setValue(EVENTOdpDataFinal.getValue());
            PARTICIPANTEdpDataInicial.setDisable(true);
            PARTICIPANTEdpDataFinal.setDisable(true);
        }
        else
        {
            PARTICIPANTEdpDataInicial.setDisable(false);
            PARTICIPANTEdpDataFinal.setDisable(false);
            PARTICIPANTEdpDataInicial.requestFocus();
        }
        
    }

    @FXML
    private void PARTICIPANTEclkAjustar(ActionEvent event) throws IOException {
        if(PARTICIPANTEtabela.getItems().size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Não foram adicionados participantes para este evento.\nAdicione os participantes primeiro.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait(); 
            PARTICIPANTETelabtAdicionar.requestFocus();
            return;
        }
        if(PARTICIPANTEtabela.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção");
            alert.setContentText("Selecione o(s) participante(s) que você deseja ajustar o período de participação.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();      
            PARTICIPANTEtabela.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Ajustar Período de Participação");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        TelaEscolherTipoAjuste = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EscolherTipoAjuste.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
        if(EscolherTipoAjusteController.opcao == 0)
            return;
        if(EscolherTipoAjusteController.opcao == 1)            
        {
           ctrRegistrarEvento.AjustarParticipantes(PARTICIPANTEtabela, PARTICIPANTEtabela.getSelectionModel().getSelectedIndices(), EVENTOdpDataInicial.getValue(), EVENTOdpDataFinal.getValue());
           PARTICIPANTETelabtAlterar.setDisable(true);
           PARTICIPANTETelabtExcluir.setDisable(true);
           PARTICIPANTETelabtAdicionar.requestFocus();           
        }
        else
            if(EscolherTipoAjusteController.opcao == 2)            
            {
               ctrRegistrarEvento.AjustarParticipantes(PARTICIPANTEtabela, PARTICIPANTEtabela.getSelectionModel().getSelectedIndices(), EVENTOdpDataInicial.getValue(), null);
               PARTICIPANTETelabtAlterar.setDisable(true);
               PARTICIPANTETelabtExcluir.setDisable(true);
               PARTICIPANTETelabtAdicionar.requestFocus();           
            }
            else
               if(EscolherTipoAjusteController.opcao == 3)            
                {
                   ctrRegistrarEvento.AjustarParticipantes(PARTICIPANTEtabela, PARTICIPANTEtabela.getSelectionModel().getSelectedIndices(), null, EVENTOdpDataFinal.getValue());
                   PARTICIPANTETelabtAlterar.setDisable(true);
                   PARTICIPANTETelabtExcluir.setDisable(true);
                   PARTICIPANTETelabtAdicionar.requestFocus();           
                }
    }

    @FXML
    private void onMouseExitObterAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/help_p.png");
        imvAjudaEventos.setImage(new Image(url.toString()));
        imvAjudaDependentes.setImage(new Image(url.toString()));
        imvAjudaPessoas.setImage(new Image(url.toString()));
        imvAjudaEmpresas.setImage(new Image(url.toString()));
        imvAjudaVeiculos.setImage(new Image(url.toString()));
        imvAjudaContasPagar.setImage(new Image(url.toString()));
        imvAjudaContasReceber.setImage(new Image(url.toString()));
        imvAjudaPagamento.setImage(new Image(url.toString()));
        imvAjudaRecebimento.setImage(new Image(url.toString()));
        imvAjudaCaixa.setImage(new Image(url.toString()));
    }
    
    @FXML
    private void OnMouseOverObterAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/help_p2.png");
        imvAjudaEventos.setImage(new Image(url.toString()));
        imvAjudaDependentes.setImage(new Image(url.toString()));
        imvAjudaPessoas.setImage(new Image(url.toString()));
        imvAjudaEmpresas.setImage(new Image(url.toString()));
        imvAjudaVeiculos.setImage(new Image(url.toString()));
        imvAjudaContasPagar.setImage(new Image(url.toString()));
        imvAjudaContasReceber.setImage(new Image(url.toString()));
        imvAjudaPagamento.setImage(new Image(url.toString()));
        imvAjudaRecebimento.setImage(new Image(url.toString()));
        imvAjudaCaixa.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickAjudaEventos(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Eventos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaEventos.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaDependentes(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Dependentes");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaDependentes.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaPessoas(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Pessoas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaPessoas.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaEmpresas(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Empresas");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaEmpresas.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaVeiculos(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Veículos");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaVeiculos.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void BACKUPclkSelecionar(ActionEvent event) throws IOException, InterruptedException {
        File arq = null;
        if(BACKUPbtSelecionar.getText().equals("SALVAR"))
        {
            Node source = (Node) event.getSource();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("backups"));
            fileChooser.setTitle("Salvar Backup");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Backup (.backup)", "*.backup"));
            arq = fileChooser.showSaveDialog(source.getScene().getWindow());
            if (arq != null) 
            {
                BACKUPbtSelecionar.setText("REMOVER");
                BACKUPbtSelecionar.getTooltip().setText("Remover");
                BACKUPtfArquivo.setText(arq.getName());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Backup");
                alert.setContentText("Deseja fazer o backup ?");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
                ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

                if(alert.showAndWait().get() == buttonTypeSim)
                {
                    if(Banco.Backup(arq.getCanonicalPath()))
                    {
                        BACKUPbtSelecionar.setText("SALVAR");
                        BACKUPbtSelecionar.getTooltip().setText("Salvar backup");
                        BACKUPtfArquivo.setText("");
                        arq = null;
                    }
                }

            }
        }
        else //REMOVER
        {
            BACKUPbtSelecionar.setText("SALVAR");
            BACKUPbtSelecionar.getTooltip().setText("Salvar backup");
            BACKUPtfArquivo.setText("");
            arq = null;
        }
    }

    @FXML
    private void onMouseExitBackup(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subBackup.png");
        imvBackup.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickMenuBackup(MouseEvent event) throws SQLException {
        HabilitaTela(imvBackup);
    }

    @FXML
    private void OnMouseOverBackup(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subBackup2.png");
        imvBackup.setImage(new Image(url.toString())); 
    }

    @FXML
    private void onMouseExitRestauracao(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subRestauracao.png");
        imvRestauracao.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickMenuRestauracao(MouseEvent event) throws SQLException {
        HabilitaTela(imvRestauracao);
        
    }

    @FXML
    private void OnMouseOverRestauracao(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subRestauracao2.png");
        imvRestauracao.setImage(new Image(url.toString())); 
    }

    @FXML
    private void RESTAURACAOclkSelecionar(ActionEvent event) throws IOException, InterruptedException {
        File arq = null;
        if(RESTAURACAObtSelecionar.getText().equals("SELECIONAR"))
        {
            Node source = (Node) event.getSource();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("backups"));
            fileChooser.setTitle("Selecione o Backup");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Backup (.backup)", "*.backup"));
            arq = fileChooser.showOpenDialog(source.getScene().getWindow());
            if (arq != null) 
            {
                RESTAURACAObtSelecionar.setText("REMOVER");
                RESTAURACAObtSelecionar.getTooltip().setText("Remover");
                RESTAURACAOtfArquivo.setText(arq.getName());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Restauração");
                alert.setContentText("Deseja fazer a restauração ?");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                ButtonType buttonTypeSim = new ButtonType("SIM", ButtonData.YES);
                ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

                if(alert.showAndWait().get() == buttonTypeSim)
                {
                    if(Banco.Restaurar(arq.getCanonicalPath()))
                    {
                        RESTAURACAObtSelecionar.setText("SELECIONAR");
                        RESTAURACAObtSelecionar.getTooltip().setText("Selecionar backup");
                        RESTAURACAOtfArquivo.setText("");
                        arq = null;
                        ctrGerenciarPessoas.getCtrpessoa().getUsuario().setCod(0);
                        if(!ctrGerenciarPessoas.getCtrpessoa().getUsuario().ExisteAdministrador())
                            TelaInicial(true);
                        else
                            TelaLogin(true);
                    }
                }

            }
        }
        else //REMOVER
        {
            RESTAURACAObtSelecionar.setText("SELECIONAR");
            RESTAURACAObtSelecionar.getTooltip().setText("Selecionar backup");
            RESTAURACAOtfArquivo.setText("");
            arq = null;
        }        
    }

    @FXML
    private void onMouseExitMenuAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subAjuda.png");
        imvAjudaMenu.setImage(new Image(url.toString())); 
    }

    @FXML
    private void OnMouseOverMenuAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subAjuda2.png");
        imvAjudaMenu.setImage(new Image(url.toString())); 
    }
    
    @FXML
    private void clickMenuAjuda(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaMenu.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();        
        
    }  

    @FXML
    private void onMouseExitSistema(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subSistema.png");
        imvSistema.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickMenuSistema(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Sistema");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Sobre.fxml")));
        stage.setScene(scene); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();   
    }

    @FXML
    private void OnMouseOverSistema(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subSistema2.png");
        imvSistema.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickAjudaContasPagar(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Contas a Pagar");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaContasPagar.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaContasReceber(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Contas a Receber");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaContasReceber.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaPagamento(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Pagamento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaPagamento.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clickAjudaRecebimento(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Recebimento");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaRecebimento.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void clkRedefinir(ActionEvent event) {
        if(lbBancodeDados.isVisible())
        {
            cbDriver.getSelectionModel().select(params.getDriver());
            tfBase.setText(params.getBase_dados());
            tfPorta.setText(params.getPorta());
            tfSenhaServidor.setText(params.getSenha());
            tfServidor.setText(params.getServidor());
            tfUsuarioServidor.setText(params.getUsuario());
            ckbServidorLocal.setSelected(tfServidor.getText().equals("localhost"));
            DetectaServidorLocal();
        }
        else
        {
            cbDriver.getSelectionModel().select(0);
            tfServidor.setText("localhost");
            tfPorta.setText("5432");
            tfUsuarioServidor.setText("postgres");
            tfSenhaServidor.setText("postgres123");
            tfBase.setText("siscon");
            ckbServidorLocal.setSelected(tfServidor.getText().equals("localhost"));
            DetectaServidorLocal();
        }
        cbDriver.requestFocus();
    }

    @FXML
    private void clkConectar(ActionEvent event) throws SQLException, UnknownHostException {
        boolean erro = false;
        if(tfBase.getText().equals(""))
        {
            erro = true;
            lbBase.setTextFill(Color.RED);
            tfBase.requestFocus();
        }
        else
            lbBase.setTextFill(Color.BLACK);
        
        if(tfSenhaServidor.getText().equals(""))
        {
            erro = true;
            lbSenhaServidor.setTextFill(Color.RED);
            tfSenhaServidor.requestFocus();
        }
        else
            lbSenhaServidor.setTextFill(Color.BLACK);
        
        if(tfUsuarioServidor.getText().equals(""))
        {
            erro = true;
            lbUsuarioServidor.setTextFill(Color.RED);
            tfUsuarioServidor.requestFocus();
        }
        else
            lbUsuarioServidor.setTextFill(Color.BLACK);
        
        if(tfPorta.getText().equals(""))
        {
            erro = true;
            lbPorta.setTextFill(Color.RED);
            tfPorta.requestFocus();
        }
        else
            lbPorta.setTextFill(Color.BLACK);
        
        if(tfServidor.getText().equals(""))
        {
            erro = true;
            lbServidor.setTextFill(Color.RED);
            tfServidor.requestFocus();
        }
        else
            lbServidor.setTextFill(Color.BLACK);
        
        if(cbDriver.getValue() == null)
        {
            erro = true;
            lbDriver.setTextFill(Color.RED);
            cbDriver.requestFocus();
        }
        else
            lbDriver.setTextFill(Color.BLACK);
        
        if(erro)
            return;  
        //validou tudo
        
        Conexao con_aux = null;
        if(!apTitulo.isVisible())
            con_aux = Banco.con;
        
        
        if(!Banco.conectar(new Parametros(cbDriver.getValue(), tfServidor.getText(), tfPorta.getText(), tfUsuarioServidor.getText(), tfSenhaServidor.getText(), tfBase.getText())))
        {
            if(!Banco.criarBD())
            {
                //JOptionPane.showMessageDialog(null, Banco.con.getMensagemErro());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Não Conectado");
                alert.setContentText("A conexão falhou.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                alert.showAndWait();  
                if(!apTitulo.isVisible())
                    Banco.con = con_aux;
                else
                    Banco.con = null;
                return;
            }           

            String arquivo = "";
            try 
            {
                Banco.Restaurar(arquivo);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!Banco.conectar())
            {
                //JOptionPane.showMessageDialog(null, Banco.con.getMensagemErro());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Não Conectado");
                alert.setContentText("A conexão falhou.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                alert.showAndWait();  
                if(!apTitulo.isVisible())
                    Banco.con = con_aux;
                else
                    Banco.con = null;              
                return;
            }
        } 
        Banco.salvar_servidor(); //serializando
        params = Banco.con.getParametros();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Conectado");
        alert.setContentText("Conexão realizada com sucesso.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        alert.showAndWait();        
        
        //verificar se existe administrador, se não primeiro uso! (FAZER CADASTRO DE PESSOA COM USUÁRIO ADMINISTRADOR)
        ctrGerenciarPessoas.getCtrpessoa().getUsuario().setCod(0);
        if(!ctrGerenciarPessoas.getCtrpessoa().getUsuario().ExisteAdministrador())
            TelaInicial(true);
        else
            TelaLogin(true);
        
                
        
       
    }

    @FXML
    private void onMouseExitBancoDados(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subBancoDados.png");
        imvBancoDados.setImage(new Image(url.toString())); 
    }

    @FXML
    private void clickMenuBancoDados(MouseEvent event) throws SQLException {
        HabilitaTela(imvBancoDados);
    }

    @FXML
    private void OnMouseOverBancoDados(MouseEvent event) {
        java.net.URL url = getClass().getResource("botoes/sub_menu/subBancoDados2.png");
        imvBancoDados.setImage(new Image(url.toString())); 
        
    }

    @FXML
    private void clkIP(ActionEvent event) throws UnknownHostException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("IP do computador");
        String portas = VerPortasAcessiveis();
        if(portas.equals(""))
           alert.setContentText("Endereço IP: "+Inet4Address.getLocalHost().getHostAddress()+"\n\nNenhuma porta acessível.");
        else
           alert.setContentText("Endereço IP: "+Inet4Address.getLocalHost().getHostAddress()+"\n\nPortas acessíveis: "+portas);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        alert.showAndWait(); 
    }
    
    public static String VerPortasAcessiveis() throws UnknownHostException
    {
        String ip = InetAddress.getLocalHost().getHostName();
        String portas_abertas = "", portas_fechadas = "";
        for(int i=5432; i <=5434; i++)
        {
            try
            {
                Socket ServerSok = new Socket(ip,i);
                if(!portas_abertas.equals(""))
                    portas_abertas += ", ";
                portas_abertas += i;
                ServerSok.close();
            }
            catch (IOException ex)
            {
                portas_fechadas += i+"\n";
            }
        }
        /*if(!portas_fechadas.equals(""))            
           System.out.println("Portas fechadas:\n"+portas_fechadas);*/
        return portas_abertas;
    }   

    private void DetectaServidorLocal()
    {
        if(ckbServidorLocal.isSelected())
        {
            tfServidor.setText("localhost");
            tfServidor.setDisable(true);
            tfServidor.setEditable(false);
            tfServidor.setFocusTraversable(false);
            tfServidor.setMouseTransparent(true);            
        }
        else
        {
            tfServidor.setDisable(false);
            if(tfServidor.getText().equals("localhost"))
               tfServidor.setText("");
            tfServidor.setEditable(true);
            tfServidor.setFocusTraversable(true);
            tfServidor.setMouseTransparent(false);   
            tfServidor.requestFocus();
        }
        
    }
    @FXML
    private void clkServidorLocal(ActionEvent event) {
        DetectaServidorLocal();
    }

    @FXML
    private void clickAjudaCaixa(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Caixa");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaCaixa.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    
}
