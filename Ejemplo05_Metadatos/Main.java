package Ejemplo05_Metadatos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Clase que muesta los metadatos de una base de datos
 * 
 * @author Ismael Martin
 *
 */
public class Main {
	/**
	 * Procedimiento que muestra los datos de una base de datos
	 * 
	 * <pre>
	 * import java.sql.DatabaseMetaData;
	 * </pre>
	 *  
	 * @see pedirTexto();
	 * @see <a href=
	 * "https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html">Documentacion
	 * MetaData</a>
	 * 
	 * @param tipoBaseDados producto con el que se creo la BD -> oracle,sqlite o
	 * mysql
	 */
	public static void getMetadatos(String tipoBaseDatos) {
		// Preparativos
		Connection c = null;
		ResultSet r = null;
		tipoBaseDatos = tipoBaseDatos.toLowerCase();
		String userBD = "";
		String contraseniaUser = "";
		String urlBD = "";
		String nombreBD = "";
		try {
			// Dependiendo de la base de datos creamos una conexión
			switch (tipoBaseDatos) {
			case "oracle": {
				System.out.println("Se trata de una Base de Datos de Oracle.");
				System.out.println("Introduce los siguientes datos.");
				userBD = pedirTexto("Nombre de la base de datos: ");
				contraseniaUser = pedirTexto("Contraseña: ");
				urlBD = pedirTexto("Dirección de la Base de datos: ");
				// Conexion Oracle
				Class.forName("oracle.jdbc.OracleDriver");
				c = DriverManager
						.getConnection("jdbc:oracle:thin:" + userBD + "/" + contraseniaUser + "@//" + urlBD + "/XE");
				break;
			}
			case "sqlite": {
				System.out.println("Se trata de una Base de Sqlite embebida en el proyecto.");
				// Conexion Sqlite
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + "src\\Ejemplo05_Metadatos\\empresa.db");
				break;
			}
			case "mysql": {
				System.out.println("Se trata de una Base de Datos de MySql.");
				System.out.println("Introduce los siguientes datos.");
				nombreBD = pedirTexto("Nombre de la base de datos: ");
				urlBD = pedirTexto("Dirección de la Base de datos: ");
				Class.forName("com.mysql.jdbc.Driver");
				// Conectar al servidor
				c = DriverManager.getConnection("jdbc:mysql://" + urlBD + "/" + nombreBD, "root", "");
				break;
			}
			default:
				System.out.println("El tipo de la base de datos es erróneo.");
				break;
			}
			// Preparativos para mostrar
			DatabaseMetaData data = c.getMetaData();
			r = data.getTables(null, "ACADT", null, null);
			// MOSTRAR METADATOS
			System.out.println("\n###########################################");
			System.out.println("*\t~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t~~ INFORMACIÓN GENERAL ~~");
			System.out.println("*\t~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t·Producida con -> " + data.getDatabaseProductName() + "["
					+ data.getDatabaseProductVersion() + "]");
			System.out.println("*\t·Driver -> " + data.getDriverName() + "[" + data.getDriverVersion() + "]");
			System.out.println("*\t·Conexión -> " + data.getConnection());
			System.out.println("*\t·Hospedaje -> " + data.getURL());
			System.out.println("*\t·Usuario -> " + data.getUserName());
			// ¿Por qué querría la contraseña de una base de datos?
			// Diferencia entre hacker y civerdelicuente.
			// System.out.println("Contraseña-> Imposible saber con sql simple");
			System.out.println("*\t~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t~~       TABLAS       ~~");
			System.out.println("*\tCatálogo|Schema|Tipo|Nombre ");
			System.out.println("*\t~~~~~~~~~~~~~~~~~~~~~~~~~");
			while (r.next()) {
				System.out.println("*\t·" + r.getString("TABLE_CAT") + " -> " + r.getString("TABLE_SCHEM") + " -> "
						+ r.getString("TABLE_TYPE") + " -> " + r.getString("TABLE_NAME"));
			}
			System.out.println("*");
			System.out.println("###########################################\n");
			// Fin de mostrar
			// Cierre flujos
			r.close();
			c.close();
		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la clase");// e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("*Error en el SQL");// e.printStackTrace();
		}

	}// Fin de getMetadatos

	/**
	 * Metodo para obtener texto por teclado
	 * 
	 * @param pregunta
	 * @return respuesta
	 */
	public static String pedirTexto(String pregunta) {
		Scanner entrada = new Scanner(System.in);
		System.out.print(pregunta);
		return entrada.nextLine();
	}// Fin de pedir texto

	/**
	 * Metodo que inicia la clase
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" - Metadatos -");
		// Consulta sqlite
		System.out.println("\n 1.BD SQLITE");
		System.out.println("----------------");
		getMetadatos("SqliTe");
		// Consulta oracle
		System.out.println("\n 2.BD DE ORACLE");
		System.out.println("----------------");
		getMetadatos("ORaclE");
		// Consulta oracle
		System.out.println("\n 3.BD DE MYSQL");
		System.out.println("----------------");
		getMetadatos("mysql");

	}// Fin del main

}// Fin de la clase
