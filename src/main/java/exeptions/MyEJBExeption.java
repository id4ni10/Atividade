/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exeptions;

import javax.ejb.EJBException;

/**
 *
 * @author Danilo
 */
public class MyEJBExeption extends EJBException{
    private final String message;

    public MyEJBExeption(String message) {
        this.message = message;
    }

    public String getMymessage() {
        return message;
    }
}
