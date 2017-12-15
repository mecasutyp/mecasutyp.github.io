/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mecasut.admon;

import utilerias.Encriptacion;

/**
 *
 * @author Joshua
 */
public class EnvioDatosForm extends org.apache.struts.action.ActionForm {
    
    private String name = "";
    private String password = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Encriptacion.Encriptar(password, "SHA-1");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     */
    public EnvioDatosForm() {
        super();
        // TODO Auto-generated constructor stub
    }
}