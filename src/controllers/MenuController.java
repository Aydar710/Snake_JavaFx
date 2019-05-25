package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;

public class MenuController {


    @FXML
    private Button btnSelectMode;

    @FXML
    private Label labelText;

    @FXML
    private Rectangle kvad;


    @FXML
    void initialize(){
        btnSelectMode.setOnAction(event -> System.out.println("asf"));
        System.out.println(kvad.getX() + "  " + kvad.getY());
    }



}

