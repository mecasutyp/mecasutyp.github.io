/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;
/**
 *
 * @author EQ-30
 */
public class CgutMatriculaTotalForm extends org.apache.struts.action.ActionForm {

    private String mat_total = "";

    public String getMat_total() {
        return mat_total;
    }

    public void setMat_total(String mat_total) {
        this.mat_total = mat_total;
    }

    public CgutMatriculaTotalForm() {
        super();
    }
}
