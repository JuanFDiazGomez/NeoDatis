/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neodatisdboejemplo1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

/**
 *
 * @author alumno
 */
public class EjemploNeoDatis {

    public static void main(String[] args) {
	int opcion = 0;
	Scanner sc = new Scanner(System.in);

	do {
	    System.out.println("--------------------------------------");
	    System.out.println("            Menú Principal");
	    System.out.println("--------------------------------------");
	    System.out.println("1. Inserción de datos de ejemplo");
	    System.out.println("2. Acceso por OID");
	    System.out.println("3. Mostrar los jugadores de tenis");
	    System.out.println("4. Actualizacion de objetos");
	    System.out.println("5. Borrado de objetos");
	    System.out.println("6. Ejemplo de consultas varias");
	    System.out.println("7. Ejemplo con consultas de varios parametros");
	    System.out.println("0. Salir");
	    System.out.print("Introduce la opción deseada: ");
	    opcion = sc.nextInt();
	    switch (opcion) {
		case 1: {
		    insercionDatosEjemplo();
		    break;
		}
		case 2: {
		    accesoPorOID();
		    break;
		}
		case 3: {
		    consultaSencillaTenis();
		    break;
		}
		case 4: {
		    actualizacionDeObjetos();
		    break;
		}
		case 5: {
		    borradoObjectos();
		    break;
		}
		case 6: {
		    ejemplosConsultasBDOC();
		    break;
		}
		case 7: {
		    consultasVariosParametros();
		    break;
		}
		case 8: {
		    actualizacionPais();
		    break;
		}
		case 9: {
		    consultasDeAgregado();
		    break;
		}
		case 10: {
		    consultasConGroupBy();
		}
		default: {
		    break;
		}
	    }
	} while (opcion != 0);
    }

    private static void insercionDatosEjemplo() {
	Jugadores jugador1 = new Jugadores("Maria", "Voleybol", "Madrid", 14, new Pais(1, "Francia"));
	Jugadores jugador2 = new Jugadores("Miguel", "Tenis", "Valencia", 13, null);
	Jugadores jugador3 = new Jugadores("Mario", "Baloncesto", "Guadalajara", 15, null);
	Jugadores jugador4 = new Jugadores("Alicia", "Tenis", "Madrid", 15, new Pais(2, "Spain"));

	ODB odb = ODBFactory.open("equipos.db");

	odb.store(jugador1);
	odb.store(jugador2);
	odb.store(jugador3);
	odb.store(jugador4);

	odb.commit();

	Objects<Jugadores> objects = odb.getObjects(Jugadores.class);
	System.out.println("Se han recuperado " + objects.size() + " resultados");
	int num = 0;
	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    num++;
	    System.out.println("Jugador " + num + "\n");
	    System.out.println(jugador);
	}

	odb.close();
    }

    private static void accesoPorOID() {
	ODB odb = ODBFactory.open("equipos.db");

	OID oid = OIDFactory.buildObjectOID(3);

	Jugadores jugador = (Jugadores) odb.getObjectFromId(oid);

	System.out.println(jugador);

	odb.close();
    }

    private static void consultaSencillaTenis() {
	ODB odb = ODBFactory.open("equipos.db");

	IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("deporte", "Tenis"));

	query.orderByAsc("nombre,edad");

	Objects<Jugadores> objects = odb.getObjects(query);

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	}

	odb.close();
    }

    private static void actualizacionDeObjetos() {
	Scanner sc = new Scanner(System.in);
	ODB odb = ODBFactory.open("equipos.db");

	System.out.print("Introduce el nombre del jugador a modificar: ");

	String nombre = sc.nextLine();

	IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("nombre", nombre));

	Jugadores jugador = (Jugadores) odb.getObjects(query).getFirst();

	System.out.print("Introduce nuevo deporte: ");
	String deporte = sc.nextLine();

	jugador.setDeporte(deporte);

	System.out.println(jugador);

	odb.store(jugador);
	odb.commit();
	odb.close();
    }

    private static void borradoObjectos() {
	Scanner sc = new Scanner(System.in);
	ODB odb = ODBFactory.open("equipos.db");

	System.out.print("Introduce el nombre del jugador a borrar: ");

	String nombre = sc.nextLine();

	IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("nombre", nombre));

	Jugadores jugador = (Jugadores) odb.getObjects(query).getFirst();

	odb.delete(jugador);
	odb.commit();
	odb.close();
    }

    private static void ejemplosConsultasBDOC() {
	ODB odb = ODBFactory.open("equipos.db");
	ICriterion criterion = Where.like("nombre", "M%");
	IQuery query = new CriteriaQuery(Jugadores.class, criterion);

	Objects<Jugadores> objects = odb.getObjects(query);

	System.out.println("Jugadores cuyo nombre empieza por M\n");
	System.out.println("-----------------------------------");
	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.not(Where.like("nombre", "M%"));
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores cuyo nombre no empieza por M\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.ge("edad", 14);
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores cuya edad sea mayor o igual a 14 años\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.le("edad", 14);
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores cuya edad sea menor o igual a 14 años\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.lt("edad", 14);
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores cuya edad sea menor a 14 años\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.gt("edad", 14);
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores cuya edad sea mayor a 14 años\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");
	criterion = Where.isNotNull("ciudad");
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	System.out.println("Jugadores que tienen una residencia asignada\n");
	System.out.println("-----------------------------------");

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println("\n");

	System.out.println("Jugadores no tienen una nacionalidad\n");
	System.out.println("-----------------------------------");
	criterion = Where.isNull("pais");
	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);

	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}

	odb.close();
    }

    private static void consultasVariosParametros() {
	ODB odb = ODBFactory.open("equipos.db");

	ICriterion criterion = new And()
		.add(Where.equal("ciudad", "Madrid"))
		.add(Where.ge("edad", 15));

	IQuery query = new CriteriaQuery(Jugadores.class, criterion);
	Objects<Jugadores> objects = odb.getObjects(query);
	System.out.println("Jugadores que tienen 15 o mas años y viven en Madrid");
	System.out.println("----------------------------------------------------");
	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println();

	criterion = new And()
		.add(Where.equal("ciudad", "Madrid"))
		.add(new Or()
			.add(Where.equal("deporte", "Baloncesto"))
			.add(Where.equal("deporte", "Tenis"))
		);

	query = new CriteriaQuery(Jugadores.class, criterion);

	objects = odb.getObjects(query);
	System.out.println("Jugadores que juegan al Baloncesto o Tenis y viven en Madrid");
	System.out.println("----------------------------------------------------");
	while (objects.hasNext()) {
	    Jugadores jugador = objects.next();
	    System.out.println(jugador);
	    System.out.println("");
	}
	System.out.println();

	odb.close();

    }

    private static void actualizacionPais() {
	ODB odb = ODBFactory.open("equipos.db");

	ICriterion criterion = Where.equal("pais.nombrePais", "Francia");

	IQuery query = new CriteriaQuery(Jugadores.class, criterion);

	Objects<Jugadores> objects = odb.getObjects(query);

	if (objects.size() == 0) {
	    System.out.println("No hay jugadores que actualizar");
	} else {
	    while (objects.hasNext()) {
		Jugadores jugador = objects.next();
		jugador.setEdad(jugador.getEdad() + 1);
		System.out.println("El jugador " + jugador.getNombre() + " se ha actualizado, a continuación aparecen sus datos");
		System.out.println(jugador);
		System.out.println();
		odb.store(jugador);
	    }
	    odb.commit();
	}
	odb.close();
    }

    private static void consultasDeAgregado() {
	ODB odb = ODBFactory.open("equipos.db");

	Values valores = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).field("nombre").field("ciudad"));

	while (valores.hasNext()) {
	    ObjectValues objectValue = valores.next();
	    System.out.println("Nombre: " + objectValue.getByAlias("nombre") + " Ciudad: " + objectValue.getByIndex(1));
	}
	System.out.println();
	System.out.println("Obtenga la suma de las edades de los jugadores");
	System.out.println("----------------------------------------------");

	Values valores1 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).sum("edad"));

	ObjectValues ovl = valores1.nextValues();

	BigDecimal sumaEdades = (BigDecimal) ovl.getByAlias("edad");
	System.out.println("La suma de las edades de los jugadores es: " + sumaEdades.longValue());
	System.out.println();

	System.out.println("Obtenga la media de las edades de los jugadores");
	System.out.println("----------------------------------------------");
	Values valores2 = null;
	ObjectValues ovl1 = null;
	try {
	    valores2 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).avg("edad"));
	    ovl1 = valores2.nextValues();
	    BigDecimal mediaEdades = (BigDecimal) ovl.getByAlias("edad");

	    System.out.println("La media de las edades de los jugadores es: " + mediaEdades.longValue());
	} catch (ArithmeticException ae) {
	    valores2 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).sum("edad").count("edad"));
	    ovl1 = valores2.nextValues();
	    BigDecimal sumaPufEdades = (BigDecimal) ovl1.getByIndex(0);
	    BigInteger cuentaDeJugadores = (BigInteger) ovl1.getByIndex(1);
	    double media = sumaPufEdades.floatValue() / cuentaDeJugadores.intValue();

	    System.out.println("La media de las edades de los jugadores es: " + media);
	}

	System.out.println();

	System.out.println("Obtenga la edad minima y maxima de los jugadores y el numero de jugadores");
	System.out.println("----------------------------------------------");

	Values valores3 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).min("edad").count("nombre").max("edad"));

	ObjectValues ovl2 = valores3.nextValues();

	BigDecimal edadMin = (BigDecimal) ovl2.getByIndex(0);
	BigInteger cantidad = (BigInteger) ovl2.getByIndex(1);
	BigDecimal edadMax = (BigDecimal) ovl2.getByIndex(2);
	System.out.println("Edad minima: " + edadMin + " Edad maxima: " + edadMax + " Cantidad jugadores: " + cantidad);
	System.out.println();

	odb.close();

    }

    private static void consultasConGroupBy() {
	ODB odb = ODBFactory.open("equipos.db");
	Values valores = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.isNotNull("pais.nombrePais")).field("pais.nombrePais").count("nombre").max("edad").sum("edad").groupBy("pais.nombrePais"));
	if (valores.size() == 0) {
	    System.out.println("No hay ningún resultado");
	} else {
	    while (valores.hasNext()) {
		ObjectValues ov = valores.next();
		BigDecimal sumaEdades = (BigDecimal) ov.getByIndex(3);
		BigInteger cuentaJugadores = (BigInteger) ov.getByIndex(1);
		float media = sumaEdades.floatValue() / cuentaJugadores.floatValue();
		System.out.println("El pais " + ov.getByIndex(0) + " tiene " + ov.getByIndex(1) + " jugadores y la media de edad es " + media + ", en total suman " + sumaEdades + " años, y el de mayor edad tiene " + ov.getByIndex(2));
	    }
	}
	odb.close();

    }
}
