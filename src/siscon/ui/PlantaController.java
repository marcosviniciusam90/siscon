/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class PlantaController implements Initializable {

    @FXML
    private ImageView imvPlanta;
    @FXML
    private Label lote23;
    @FXML
    private ImageView imvMarcador;
    @FXML
    private Label lote1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvMarcador.setVisible(true);
        imvMarcador.setX(lote1.getLayoutX()-25);
        imvMarcador.setY(lote1.getLayoutY()-25);
    }    
    
}
