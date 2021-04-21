// forma de uso:  m�todo(componente) no m�todo initialize() da controladora de tela
// Ex: numericField(txpreco)



package siscon.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MaskCampos
{
    
    private static List<KeyCode> ignoreKeyCodes = new ArrayList<>();

    public static void ignoreKeys(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler) new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                if (ignoreKeyCodes.contains(keyEvent.getCode())) {
                    keyEvent.consume();
                }
            }
        });
    }

    //xxxxx-xxxxx-xxxxx-xxxxx
    public static void serialTextField(final TextField textField) {
        maxField(textField, 23);
        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() < 24) {
                String value = textField.getText();
                value = value.replaceAll("[^\\w]", "");
                value = value.replaceFirst("(\\w{5})(\\w)", "$1-$2");
                value = value.replaceFirst("(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3");
                value = value.replaceFirst("(\\w{5})\\-(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3-$4");
                textField.setText(value.toUpperCase());
                positionCaret(textField);
            }
        });
    }
    
    public static void placaField(final TextField textField) {
        maxField(textField, 8);
        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() < 9) {
                String value = textField.getText();
                value = value.replaceAll("[^\\w]", "");
                value = value.replaceFirst("(\\w{3})(\\w)", "$1-$2");
                value = value.replaceFirst("(\\w{3})\\-(\\w{4})(\\w)", "$1-$2-$3");
                textField.setText(value.toUpperCase());
                positionCaret(textField);
            }
        });
    }

    
     public static void dateField(final TextField textField)
     {
        maxField(textField, 10);
        textField.lengthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() < 11)
                {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                    value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                    textField.setText(value);
                    positionCaret(textField);
                }
            }
        });
    }

    public static void numericField(final TextField textField, int tamanho)
    {
        maxField(textField, tamanho);
        textField.lengthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = textField.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    }
                }
            }
        });
    }

    public static void monetaryField(final TextField textField)
    {
        textField.lengthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                
                //utilização do ponto na mascará, quando o número se torna maior que mil (ex: 1.001,00, 2.100.000,00)
                /*
                value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
                */
                ///////////////////////////////////////////////////////////
                value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
                textField.setText(value);
                positionCaret(textField);

                textField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue.length() > 17)
                            textField.setText(oldValue);
                    }
                });
            }
        });

        textField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean fieldChange)
            {
                if (!fieldChange)
                {
                    final int length = textField.getText().length();
                    if (length > 0 && length < 3) {
                        textField.setText(textField.getText() + "00");
                    }
                }
            }
        });
    }
    
    

    private static void positionCaret(final TextField textField)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run() {
                // Posiciona o cursor sempre a direita.
                textField.positionCaret(textField.getText().length());
            }
        });
    }

    public static void maxField(final TextField textField, final Integer length)
    {
        textField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue)
            {
                if (newValue.length() > length)
                    textField.setText(oldValue);
            }
        });
    }
    
    public static BigDecimal monetaryValueFromField(TextField textField) {
        if (textField.getText().isEmpty()) {
            return null;
        }
        BigDecimal retorno = BigDecimal.ZERO;
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            Number parsedNumber = nf.parse(textField.getText());
            retorno = new BigDecimal(parsedNumber.toString());
        } catch (ParseException ex) {
            Logger.getLogger(MaskCampos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public static void PhoneField(TextField textField) {
        maxField(textField, 15);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            try {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                int tam = value.length();
                value = value.replaceFirst("(\\d{2})(\\d)", "($1) $2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                if (tam > 10) {
                    value = value.replaceAll("-", "");
                    value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
                }
                textField.setText(value);
                positionCaret(textField);

            } catch (Exception ex) {
            }
        }
        );
    }


    public static String zeros(String value)
    {
        if (value.charAt(value.length()-2)=='.')
            value+="0";
        return value;
    }

   public static void cpfField(final TextField textField) {
        maxField(textField, 14);

        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})\\.(\\d{3})(\\d)", "$1.$2.$3");
                value = value.replaceFirst("\\.(\\d{3})(\\d)", ".$1-$2");
                textField.setText(value);
                positionCaret(textField);
            }
        });
    }
   
   
   
   public static void cepField(final TextField textField) {
        maxField(textField, 10);

        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                //value = value.replaceFirst("(\\d{3})\\.(\\d{3})(\\d)", "$1.$2.$3");
                value = value.replaceFirst("\\.(\\d{3})(\\d)", ".$1-$2");
                textField.setText(value);
                positionCaret(textField);
            }
        });
    }


    public static void cnpjField(final TextField textField) {
        maxField(textField, 18);

        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{2})\\.(\\d{3})(\\d)", "$1.$2.$3");
                value = value.replaceFirst("\\.(\\d{3})(\\d)", ".$1/$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                textField.setText(value);
                positionCaret(textField);
            }
        });

    }
    
    public static String onlyDigitsValue(TextField field) {
        String result = field.getText();
        if (result == null) {
            return null;
        }
        return result.replaceAll("[^0-9]", "");
    }

    public static String onlyAlfaNumericValue(TextField field) {
        String result = field.getText();
        if (result == null) {
            return null;
        }
        return result.replaceAll("[^0-9]", "");
    }

    static {
        Collections.addAll(ignoreKeyCodes, new KeyCode[]{KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5, KeyCode.F6, KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12});
    }
}
