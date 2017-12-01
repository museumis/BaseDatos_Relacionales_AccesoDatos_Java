package Ejemplo_JDBC_consulta_SQlite_Oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BD_RELACIONALES_FULL.Bd_Relacionales_Full;

/**
 * 
 * Clase para consultar una base de datos en sqlite o oracle
 * 
 * @author Ismael Martin
 *
 */
public class Main {
	/**
	 * Método que realiza una consulta a una base de datos ORACLE hospeda en un
	 * servidor mediante JDBC.
	 * 
	 * <pre>
	 * Oracle DataBASE XE -> <a href=
	"http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html">download</a>
	 * 
	 * Crear BD en Oracle Database -> user:system /SQL Commands
	 * 
	 * Importar la libreria Drives JDBC para Oracle -><a href=
	"http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html">Driver</a>
	 * 
	 * Imports necesarios
	 * import java.sql.Connection;
	 * import java.sql.DriverManager;
	 * import java.sql.ResultSet;
	 * import java.sql.SQLException;
	 * import java.sql.Statement;
	 * </pre>
	 * 
	 * @param consulta que se desea realizar
	 * @param urlBD direccion donde se hospeda la base de datos
	 * @param userBD nombre de usuario de la base de datos
	 * @param contraseniaUser contrasenia de acceso
	 */
	public static void consulta_Oracle_JDBC_BDRelacional(String consulta, String urlBD, String userBD,
			String contraseniaUser) {
		// Carga drivers
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			// Conectar al servidor
			Connection c = DriverManager.getConnection(
					"jdbc:oracle:thin:" + userBD + "/" + contraseniaUser + "@//" + urlBD + "/XE");
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			// Mostrar consulta
			while (r.next()) {
				// Al consultar la tabla el ResultSet obtiene las columnas
				System.out.println("~~Empleado~~");
				System.out.println("Nombre: " + r.getString(2));
				System.out.println("Oficio: " + r.getString(3));
				System.out.println("Salario: " + r.getInt(6) + "€\n");
			}
			// Cierre flujos
			r.close();
			st.close();
			c.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al encontrar la clase");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error en el SQL");
			e.printStackTrace();
		}
	}// Fin de consultar BD de oracle

	/**
	 * Método que realiza una consulta a una base de datos SQLITE hospeda en el
	 * proyecto
	 * 
	 * <pre>
	 * SQlite -> <a href=
	"https://www.sqlite.org/download.html">download</a>
	 * 
	 * Crear BD en Sqlite -> consola->
	 * C:\...\>squlite3 NOMBRE_BD
	 * aqlite>
	 * aqlite>CrarTablas
	 * aqlite>Inserts
	 * aqlite>.quit
	 * 
	 * Importar la libreria Drives JDBC para sqlite-><a href=
	"https://bitbucket.org/xerial/sqlite-jdbc/downloads/">driver</a>
	 * 
	 * Imports necesarios
	 * import java.sql.Connection;
	 * import java.sql.DriverManager;
	 * import java.sql.ResultSet;
	 * import java.sql.SQLException;
	 * import java.sql.Statement;
	 * </pre>
	 * 
	 * @param consulta que se desea realizar
	 * @param urlBD direccion donde se hospeda la base de datos
	 * 
	 */
	public static void consulta_Sqlite_JDBC_BDRelacional(String consulta, String urlBD) {
		// Carga drivers
		try {
			Class.forName("org.sqlite.JDBC");
			// Conectar a la base de datos
			Connection c = DriverManager.getConnection("jdbc:sqlite:" + urlBD);
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			// Mostrar consulta
			while (r.next()) {
				// Al consultar la tabla el ResultSet obtiene las columnas
				System.out.println("~~Departamentos~~");
				System.out.println("Nombre: " + r.getString(2));
				System.out.println("Localidad: " + r.getString(3)+"\n");
			}
			// Cierre flujos
			r.close();
			st.close();
			c.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al encontrar la clase");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error en el SQL");
			e.printStackTrace();
		}
	}// Fin de consultar BD de sqlite

	/**
	 * Metodo que inicia la clase
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Bd_Relacionales_Full.getMetadatos("mysql");

		System.out.println(" - CONSULTAS -");		
		//Consulta sqlite
		System.out.println("\n 1.BD SQLITE");
		consulta_Sqlite_JDBC_BDRelacional("SELECT * FROM departamentos;", "src\\Ejemplo05_JDBC_consulta_SQlite_Oracle\\empresa.db");		
		//Consulta oracle
		System.out.println("\n 2.BD DE ORACLE");
		 consulta_Oracle_JDBC_BDRelacional("SELECT * FROM empleados", "localhost", "ACADT", "12345");

	}//Fin del main

}//Fin de la clase
