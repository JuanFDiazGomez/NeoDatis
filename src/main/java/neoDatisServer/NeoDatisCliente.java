/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoDatisServer;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import neoDatisServer.Ejemplo1ClienteBDDO;
import org.neodatis.odb.Objects;

/**
 *
 * @author alumno
 */
public class NeoDatisCliente {
    public static void main(String[] args) {
	ODB odb = null;
	try{
	    odb = ODBFactory.openClient("localhost", 8000, "basedatos");
	    Jugadores jugador5 = new Jugadores("Rafael", "Tenis", "Mallorca", 31, new Pais(2,"Spain"));
	    odb.store(jugador5);
	    
	    visualizarDatos(odb);
	    
	}finally{
	    if(odb != null){
		odb.close();
	    }
	}
    }

    private static void visualizarDatos(ODB odb) {
	Objects<Jugadores> objects = odb.getObjects(Jugadores.class);

	System.out.println("El total de jugadores almacenados es: " + objects.size());

	int i = 1;

	while (objects.hasNext()) {
	    Jugadores jug = objects.next();
	    System.out.println("Jugador " + i);
	    System.out.println(jug);
	    i++;
	}
    }
}
 