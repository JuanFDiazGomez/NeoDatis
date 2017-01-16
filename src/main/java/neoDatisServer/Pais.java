/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoDatisServer;

import com.mycompany.neodatisdboejemplo1.*;
import java.io.Serializable;

/**
 *
 * @author alumno
 */
class Pais implements Serializable{
    private int id;
    private String nombrePais;

    public Pais() {
    }

    public Pais(int id, String nombrePais) {
	this.id = id;
	this.nombrePais = nombrePais;
    }

    public int getId() {
	return id;
    }

    public String getNombrePais() {
	return nombrePais;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setNombrePais(String nombrePais) {
	this.nombrePais = nombrePais;
    }

    @Override
    public String toString() {
	return "ID: "+id+"; Nombre: "+nombrePais;
    }
    
    
}
