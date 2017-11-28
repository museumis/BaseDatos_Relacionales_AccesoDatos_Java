package Ejemplo07_Metadatos_Columna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que obtine los datos de una columna en una base de datos SQLite
 * 
 * @author Ismael Martin
 *
 */
public class Main {

	/**
	 * Método que obtine las columnas de una base de datos en SQlite
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
	"https://bitbucket.org/xerial/sqlite-jdbc/downloads/">Driver</a>
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
	public static void getColumna_Sqlite_JDBC_BDRelacional(String consulta, String urlBD) {
		// Carga drivers
		try {
			Class.forName("org.sqlite.JDBC");
			// Conectar a la base de datos
			Connection c = DriverManager.getConnection("jdbc:sqlite:" + urlBD);
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			ResultSetMetaData rMetaData = r.getMetaData();
			// Mostrar metadatos
			for (int i = 1; i < rMetaData.getColumnCount(); i++) {
				if (i == 1) {
					System.out.println(rMetaData.getTableName(i) + "(");
				}
				System.out.print(rMetaData.getColumnName(i) + " " + rMetaData.getColumnTypeName(i));
				if (rMetaData.isNullable(i) == 0) {
					System.out.println(" NOT NULL");
				} else {
					System.out.println("");
				}
				// System.out.println(rMetaData.getColumnDisplaySize(i));
				if (i == rMetaData.getColumnCount() - 1) {
					System.out.println(");");
				}
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
	}// Fin de getColumna

	/**
	 * Metodo que inicia el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" - ResultSetMetaData - \n");
		String consulta = " SELECT * FROM empleados";
		String urlBD = "src\\Ejemplo07_Metadatos_Columna\\empresa.db";
		getColumna_Sqlite_JDBC_BDRelacional(consulta, urlBD);
	}

}
