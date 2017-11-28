package Ejemplo04_JDBC_ConsultaBD_II_Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	/**
	 * Método que realiza una consulta a la base de datos mediante JDBC del apellido del empleado con mayor salario.
	 * 
	 * <pre>
	 * XAMPP -> www.apachefriends.org/es/index.html
	 * Crear base de datos en PHPMYADMIN ->XAMPP/apache,mySQL activada/mySQL-admin
	 * Importar la libreria ->mysql.connector.java.jar ->www.mysql.com/products/connection
	 * Imports necesarios :
	 	import java.sql.Connection;
		import java.sql.DriverManager;
		import java.sql.ResultSet;
		import java.sql.SQLException;
		import java.sql.Statement;
	 * </pre>
	 * 
	 * @param consulta que se desea realizar
	 * @param urlBD direccion donde se hospeda la base de datos
	 * @param nombreBD nombre de la base de datos
	 */
	public static void consulta_Mysql_JDBC_BDRelacional(String consulta, String urlBD, String nombreBD) {

		// Carga drivers
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Conectar al servidor
			Connection c = DriverManager.getConnection("jdbc:mysql://" + urlBD + "/" + nombreBD, "root", "");
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			// Mostrar consulta
			while (r.next()) {
				// Al consultar la tabla el ResultSet obtiene las columnas
				System.out.println("~~Empleado con mayor salario~~");
				System.out.println("Apellido: " + r.getString(2));
				System.out.println("Salario: " + r.getInt(6) + "\n");
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
	}

	public static void main(String[] args) {
		String consulta = "SELECT * FROM empleados WHERE salario=(SELECT MAX(salario)FROM `empleados`);";
		String urlBD = "localhost";
		String nombreBD = "acadt";
		System.out.println("-- Consulta BD con JDBC --");

		consulta_Mysql_JDBC_BDRelacional(consulta, urlBD, nombreBD);
	}

}
