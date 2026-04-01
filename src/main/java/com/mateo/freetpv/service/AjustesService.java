package com.mateo.freetpv.service;

import atlantafx.base.theme.*;
import com.mateo.freetpv.util.AjustesUtil;
import javafx.application.Application;

import java.util.Properties;

public class AjustesService {

    private AjustesUtil ajustesUtil = new AjustesUtil();

    private Properties ajustes = ajustesUtil.cargarAjustes();

    public String getTema() {
        return ajustes.getProperty("Tema");
    }
    public void setTema(String tema) {
        ajustes.setProperty("Tema", tema);
        ajustesUtil.guardarAjustes(ajustes);
    }
    public void loadTema() {
        String tema = getTema();
        switch(tema) {
            case "Primer Dark" -> Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            case "Nord Light" -> Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
            case "Nord Dark" -> Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            case "Cupertino Light" -> Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
            case "Cupertino Dark" -> Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
            case "Dracula" -> Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
            default -> Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        }
    }
}
