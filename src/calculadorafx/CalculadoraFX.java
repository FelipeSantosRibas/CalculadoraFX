package calculadorafx;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Felipe Santos Ribas
 */
public class CalculadoraFX extends Application {

    static double memory, result, temp, lastResult;
    static String operation, lastPressed;
    static ArrayList<String> numbers = new ArrayList<String>();
    //numbers
    //respostas<>.add(Arrays.asList("1", "2", "3", "4", "0"));


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER); // Define o Alinhamento do GridPane
        grid.setHgap(5); // Define o vão horizontal entre as colunas
        grid.setVgap(5); // Define o vão vertical entre as colunas
        grid.setPadding(new Insets(5, 5, 5, 5)); // Define a distância entre os lados do layout (top, right, bottom, left)
        grid.setPrefSize(250, 300);
        grid.setMinSize(250, 300);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                CalculadoraFX.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(270);

        // ---------------------------------------------------------------------
        TextField resultado = new TextField();
        resultado.setId("textField");
        resultado.setPrefHeight(500);
        resultado.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        resultado.setMinHeight(30);
        resultado.setAlignment(Pos.CENTER_RIGHT);
        grid.add(resultado, 0, 0, 4, 1);
        //resultado.setEditable(false);

        // --------------------------------------- Coluna 1
        {
            Button mc = new Button("MC");
            mc.setId("btn1");
            buttonMethod(mc);
            mc.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    memory = 0;
                }
            });
            grid.add(mc, 0, 1, 1, 1);

            Button mr = new Button("MR");
            mr.setId("btn1");
            buttonMethod(mr);
            mr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resultado.setText(formatResult(String.valueOf(memory)));
                }
            });
            grid.add(mr, 1, 1, 1, 1);

            Button msum = new Button("M+");
            msum.setId("btn1");
            buttonMethod(msum);
            msum.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!resultado.getText().isEmpty()) {
                        try {
                            memory += Double.parseDouble(resultado.getText());
                        } catch (Exception e) {
                            resultado.setText("Invalid input");
                        }
                    }
                }
            });
            grid.add(msum, 2, 1, 1, 1);

            Button msub = new Button("M-");
            msub.setId("btn1");
            buttonMethod(msub);
            msub.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!resultado.getText().isEmpty()) {
                        try {
                            memory -= Double.parseDouble(resultado.getText());
                        } catch (Exception e) {
                            resultado.setText("Invalid input");
                        }
                    }
                }
            });
            grid.add(msub, 3, 1, 1, 1);
        }

        // --------------------------------------- Coluna 2
        {
            Button ce = new Button("CE");
            ce.setId("btn2");
            buttonMethod(ce);
            ce.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resultado.setText("");
                }
            });
            grid.add(ce, 0, 2, 1, 1);

            Button c = new Button("C");
            c.setId("btn2");
            buttonMethod(c);
            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resultado.setText("");
                    temp = 0;
                    resultado.setPromptText("");
                }
            });
            grid.add(c, 1, 2, 1, 1);

            Image backspaceImg = new Image(getClass().getResourceAsStream("..\\images\\backspace-icon.png"));
            ImageView backspaceImgView = new ImageView(backspaceImg);
            backspaceImgView.setFitHeight(20);
            backspaceImgView.setFitWidth(20);
            Button backspace = new Button("", backspaceImgView);
            backspace.setId("btn2");
            buttonMethod(backspace);
            backspace.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!resultado.getText().isEmpty()) {
                        resultado.setText(resultado.getText().substring(0, resultado.getLength() - 1));
                    }
                }
            }
            );
            grid.add(backspace, 2, 2, 1, 1);

            Image divideImg = new Image(getClass().getResourceAsStream("..\\images\\divide-icon.png"));
            ImageView divideImgView = new ImageView(divideImg);
            divideImgView.setFitHeight(20);
            divideImgView.setFitWidth(20);
            Button divide = new Button("", divideImgView);
            divide.setId("btn2");
            buttonMethod(divide);
            divide.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    operatorButtonMethod(resultado, "/");
                }
            });
            grid.add(divide, 3, 2, 1, 1);
        }

        // --------------------------------------- Coluna 3
        {
            Button percentage = new Button("%");
            percentage.setId("btn2");
            buttonMethod(percentage);
            percentage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!operation.equals("") && Double.parseDouble(resultado.getText()) != 0) {
                        try {
                            result = Double.parseDouble(resultado.getText());
                            switch (operation) {
                                case "+", "-" -> {
                                    resultado.setText(formatResult(String.valueOf(temp * result / 100)));
                                }
                                case "*", "/" -> {
                                    resultado.setText(formatResult(String.valueOf(result / 100)));
                                }
                            }
                        } catch (Exception e) {
                            resultado.setText("Invalid Input");
                        }
                    }
                }
            });
            grid.add(percentage, 0, 3, 1, 1);

            Button power = new Button("x²");
            power.setId("btn2");
            buttonMethod(power);
            power.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        result = Double.parseDouble(resultado.getText());
                        resultado.setText(formatResult(String.valueOf(result * result)));
                    } catch (Exception e) {
                        resultado.setText("Invalid Input");
                    }
                }
            });
            grid.add(power, 1, 3, 1, 1);

            Button sRoot = new Button("²√x");
            sRoot.setId("btn2");
            buttonMethod(sRoot);
            sRoot.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        result = Double.parseDouble(resultado.getText());
                        resultado.setText(formatResult(String.valueOf(Math.sqrt(result))));
                    } catch (Exception e) {
                        resultado.setText("Invalid Input");
                    }
                }
            });
            grid.add(sRoot, 2, 3, 1, 1);

            Button multiply = new Button("x");
            multiply.setId("btn2");
            buttonMethod(multiply);
            multiply.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    operatorButtonMethod(resultado, "*");
                }
            });
            grid.add(multiply, 3, 3, 1, 1);
        }

        // --------------------------------------- Coluna 4
        {
            Button seven = new Button("7");
            numberButtonMethod(seven, resultado, "7");
            grid.add(seven, 0, 4, 1, 1);

            Button eight = new Button("8");
            numberButtonMethod(eight, resultado, "8");
            grid.add(eight, 1, 4, 1, 1);

            Button nine = new Button("9");
            numberButtonMethod(nine, resultado, "9");
            grid.add(nine, 2, 4, 1, 1);

            Button minus = new Button("-");
            minus.setId("btn2");
            buttonMethod(minus);
            minus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    operatorButtonMethod(resultado, "-");
                }
            });
            grid.add(minus, 3, 4, 1, 1);
        }

        // --------------------------------------- Coluna 5
        {
            Button four = new Button("4");
            numberButtonMethod(four, resultado, "4");
            grid.add(four, 0, 5, 1, 1);

            Button five = new Button("5");
            numberButtonMethod(five, resultado, "5");
            grid.add(five, 1, 5, 1, 1);

            Button six = new Button("6");
            numberButtonMethod(six, resultado, "6");
            grid.add(six, 2, 5, 1, 1);

            Button plus = new Button("+");
            plus.setId("btn2");
            buttonMethod(plus);
            plus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    operatorButtonMethod(resultado, "+");
                }
            });
            grid.add(plus, 3, 5, 1, 1);
        }

        // --------------------------------------- Coluna 6
        {
            Button one = new Button("1");
            numberButtonMethod(one, resultado, "1");
            grid.add(one, 0, 6, 1, 1);

            Button two = new Button("2");
            numberButtonMethod(two, resultado, "2");
            grid.add(two, 1, 6, 1, 1);

            Button three = new Button("3");
            numberButtonMethod(three, resultado, "3");
            grid.add(three, 2, 6, 1, 1);

            Button equals = new Button("=");
            equals.setId("btn4");
            buttonMethod(equals);
            equals.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (operation != null && !resultado.getText().equals("")) {
                        try {

                            result = Double.parseDouble(resultado.getText());
                            resultado.setText(formatResult(String.valueOf(calculate(temp, result))));
                            //System.out.println("Result: "+result);

                            if (lastResult != result) {
                                temp = result;
                            }

                            lastResult = Double.parseDouble(resultado.getText());
                            result = Double.parseDouble(resultado.getText());
                            lastPressed = "=";

                            //System.out.println("Last result: "+lastResult);
                            //System.out.println("Temp: "+temp);
                            //System.out.println("-");
                        } catch (Exception e) {
                            resultado.setText("Invalid Input");
                        }
                    }
                }
            });
            grid.add(equals, 3, 6, 1, 2);
        }

        // --------------------------------------- Coluna 7
        
            Button negative = new Button("-x");
            negative.setId("btn2");
            buttonMethod(negative);
            negative.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resultado.setText(removeLeftZeros(resultado.getText()));
                    if (resultado.getText().startsWith("-")) {
                        resultado.setText(resultado.getText().substring(1, resultado.getText().length()));
                    } else {
                        resultado.setText("-" + resultado.getText());
                    }
                }
            });
            grid.add(negative, 0, 7, 1, 1);

            Button zero = new Button("0");
            numberButtonMethod(zero, resultado, "0");
            grid.add(zero, 1, 7, 1, 1);

            Button comma = new Button(",");
            comma.setId("btn2");
            buttonMethod(comma);            
            comma.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!resultado.getText().contains("."))
                    resultado.setText(resultado.getText() + ".");
                }
            });
            grid.add(comma, 2, 7, 1, 1);
            
        
        // ---------------------------------------------------------------------
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String key = String.valueOf(event.getCode());
                if (key.startsWith("DIGIT") || key.startsWith("NUMPAD")) {
                    numberButtonActions(resultado,key.substring(key.lastIndexOf("DIGIT")+5));
                } else if (key.equals("COMMA")){
                    comma.fire();
                }
                System.out.println(event.getCode());
            }
        });

        primaryStage.show();
    }

    public void numberButtonActions(TextField resultado, String n) {
        resultado.setText(removeLeftZeros(resultado.getText()));
        resultado.setText(resultado.getText() + n);
    }

    public void numberButtonMethod(Button b, TextField resultado, String n) {
        b.setId("btn3");
        buttonMethod(b);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                numberButtonActions(resultado, n);
            }
        });
        b.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //System.out.println(n);
                //System.out.println(event.getCode());
            }
        });
    }

    public void buttonMethod(Button b) {
        b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        b.setPrefWidth(500);
        b.setPrefHeight(500);
        b.setMinWidth(40);
        b.setMinHeight(30);
    }

    public double calculate(double temp, double result) {
        switch (operation) {
            case "+" -> {
                return temp + result;
            }
            case "-" -> {
                return temp - result;
            }
            case "*" -> {
                return temp * result;
            }
            case "/" -> {
                return temp / result;
            }
            default -> {
                return 0;
            }
        }
    }

    public void operatorButtonMethod(TextField resultado, String o) {
        if (!resultado.getText().isEmpty()) {
            try {
                if (temp != 0 && !operation.equals("") && !lastPressed.equals("=")) {
                    temp = calculate(temp, Double.parseDouble(resultado.getText()));
                } else {
                    temp = Double.parseDouble(resultado.getText());
                }
                resultado.setPromptText(formatResult(String.valueOf(temp)) + o);
                operation = o;
                resultado.setText("");
                lastPressed = o;
            } catch (Exception e) {
                resultado.setText("Invalid input");
            }
        } else {
            resultado.setPromptText(formatResult(String.valueOf(temp)) + o);
            operation = o;
        }
    }

    public String removeLeftZeros(String n) {
        if (!n.startsWith("0.")) {
            while (n.startsWith("0")) {
                n = n.substring(1, n.length());
            }
        }
        return n;
    }

    public String formatResult(String n) {
        if (n.endsWith(".0")) {
            n = n.substring(0, n.lastIndexOf("."));
        }
        
        if (n.contains("E") && n.contains(".") && n.length() > n.lastIndexOf("E") + 1 && Double.parseDouble(n.substring(n.lastIndexOf("E")+1)) <= 15) {
            String temp = n.substring(0, n.lastIndexOf("E"));
            temp = (temp.substring(0, temp.lastIndexOf(".")) + temp.substring(temp.lastIndexOf(".") + 1));
            n = temp;
        }

        return n;
    }

}
