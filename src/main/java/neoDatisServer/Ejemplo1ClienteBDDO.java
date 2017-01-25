/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoDatisServer;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBServer;
import org.neodatis.odb.Objects;

/**
 *
 * @author alumno
 */
public class Ejemplo1ClienteBDDO {

    public static void main(String[] args) {
        String url = "~/NetBeansProjects/NeoDatisDBOEjemplo1/equipos.db";
	ODB odb = null;
	ODBServer odbServer = null;
	try {
	    odbServer = ODBFactory.openServer(8000);
	    odbServer.addBase("basedatos", url);
	    odbServer.startServer(Boolean.TRUE);
	    
	    odb = odbServer.openClient("basedatos");

	    Jugadores jugador4 = new Jugadores("Andrea", "Padel", "Guadalajara", 14, new Pais(3, "Italia"));
	    odb.store(jugador4);
	    odb.commit();
	    System.out.println("Jugador insertado en BD");
	    
	    visualizarDatos(odb);

	} finally {
	    if(odb != null){
		odb.close();
	    }
	    if(odbServer != null){
		odbServer.close();
	    }
	}
    }

    private static void visualizarDatos(ODB odb) {
	Objects<com.mycompany.neodatisdboejemplo1.Jugadores> objects = odb.getObjects(com.mycompany.neodatisdboejemplo1.Jugadores.class);

	System.out.println("El total de jugadores almacenados es: " + objects.size());

	int i = 1;

	while (objects.hasNext()) {
	    com.mycompany.neodatisdboejemplo1.Jugadores jug = objects.next();
	    System.out.println("Jugador " + i);
	    System.out.println(jug);
	    i++;
	}
    }
}
