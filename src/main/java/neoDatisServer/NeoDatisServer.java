/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoDatisServer;

import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBServer;

/**
 *
 * @author alumno
 */
public class NeoDatisServer {
    public static void main(String[] args) {
	ODBServer odbServer = ODBFactory.openServer(8000);
	
	odbServer.addBase("basedatos", "equipos.db");
	
	odbServer.startServer(Boolean.TRUE);
	System.out.println("Servidor iniciado");
	
	
    }
    
}
