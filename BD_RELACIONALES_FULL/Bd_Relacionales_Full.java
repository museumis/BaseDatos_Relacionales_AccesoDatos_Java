package BD_RELACIONALES_FULL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para trabajar con base de datos relacionales en Java.
 * 
 * @author Ismael Matín Ramirez
 *
 * https://museumis.github.io/Si/
 * 
 * Nota: Esta clase esta realizada apartir de varios ejemplos,retócala a tu
 * gusto.
 * 
 * Nota: Los comentario que inician con *** deberán ser implementados para
 * utilizar clase.
 */
public class Bd_Relacionales_Full {
	/**
	 * Método que realiza una consulta a una base de datos hospeda en un servidor
	 * mediante JDBC.
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
	public static void consulta_JDBC_BDRelacional(String consulta, String urlBD, String nombreBD) {

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
				//***System.out.println("~~Empleado~~");
				//***System.out.println("Nombre: " + r.getString(2));
				//***System.out.println("Oficio: " + r.getString(3));
				//***System.out.println("Salario: " + r.getInt(6) + "\n");
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
}
