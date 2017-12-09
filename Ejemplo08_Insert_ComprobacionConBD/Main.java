package Ejemplo08_Insert_ComprobacionConBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BD_RELACIONALES_FULL.Bd_Relacionales_Full;

/**
 * 
 * @author Ismael Martin R.
 *
 */
public class Main {
	/**
	 * Ejemplo de insert de empleado
	 */
	public static void insertEmpleado() {
		System.out.println(" -- INSERS -- ");
		String nomBase = Bd_Relacionales_Full.pedirTexto(" -Nombre de la tabla donde deseas insertar:");
		String emp_no = Bd_Relacionales_Full.pedirTexto(" -Número del departamento: ");
		String apellidos = Bd_Relacionales_Full.pedirTexto(" -Apellio del empleado: ");
		String oficio = Bd_Relacionales_Full.pedirTexto(" -Oficio: ");
		String dir = Bd_Relacionales_Full.pedirTexto(" -Dir: ");
		String salario = Bd_Relacionales_Full.pedirTexto(" -Salario: ");
		String comision = Bd_Relacionales_Full.pedirTexto(" -Comisión: ");
		String departameto = Bd_Relacionales_Full.pedirTexto(" -Departamento: ");
		// System.out.print("-Fecha de Alta: ");
		// String fecha = Bd_Relacionales_Full.pedirTexto("-Año: ") + "-" +
		// Bd_Relacionales_Full.pedirTexto("-Mes: ") + "-"+
		// Bd_Relacionales_Full.pedirTexto("-Día: ");
		System.out.println("Esta es la consulta: ");
		// INSERT INTO empleados VALUES ('13', 'example2', 'nini', '10', '2017-12-06','2000', '12', '10');
		String sql = "INSERT INTO " + nomBase + " VALUES('" + emp_no + "','" + apellidos + "','" + oficio + "','" + dir
				+ "'," + "sysDate()" + ",'" + salario + "','" + comision + "','" + departameto + "');";
		System.out.println(sql);
		Bd_Relacionales_Full.insert(sql);
	}

	/**
	 * Metodo que hace comprobaciones de los datos obtenidos por teclado y la BD mysql
	 * 
	 * <pre>
	 * El departamento debe existir
	 * El numero de empleado no puede repetirse
	 * El director debe exitir	 
	 * </pre>
	 * 
	 * @see @insert()
	 * 
	 * @param consulta que lee toda la tabla del insert
	 * @param urlBD direccion de la BD
	 * @param nombreBD nombre de la BD
	 * @param departamento numero de departamento introducido por teclado
	 * @param empleado introducido por teclado
	 * @param dir director introducido por teclado
	 * 
	 * @return true si la comprobacion es correcta, false si falla algun dato
	 */
	public static boolean comprobacionesInsert_consulta_Mysql_JDBC_BDRelacional(String consulta, String urlBD,
			String nombreBD, String departamento, String empleado, String dir) {
		// Variables para las comprobaciones individuales
		boolean comprobacionDepartamento = false;
		boolean comprobacionDirector = false;
		// Carga drivers
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Conectar al servidor
			Connection c = DriverManager.getConnection("jdbc:mysql://" + urlBD + "/" + nombreBD, "root", "");
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			// Comprobaciones
			while (r.next()) {
				// Comprobacion del departamento
				if (departamento.equalsIgnoreCase(r.getString(8))) {
					comprobacionDepartamento = true;
				}
				// Comprobacion emplado
				if (empleado.equalsIgnoreCase(r.getString(1))) {
					System.out.println("El número de empleado ya exite.");
					return false;
				}
				// Comprobacion Director
				if (dir.equalsIgnoreCase(r.getString(4))) {
					comprobacionDirector = true;
				}
			}
			// Mostrar elementos incorrectos
			if (!comprobacionDepartamento) {
				System.out.println("El departamento No existe en la base de datos.");
				return false;
			}
			if (!comprobacionDirector) {
				System.out.println("El número de director NO exite.");
				return false;
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

		// Return del metodo, true si todo esta correcto.
		return true;
	}// Fin de consulta_Mysql_JDBC_BDRelacional

	/**
	 * Metodo para insertar sentencia en tablas
	 * 
	 * @param sentenciaSql del insert que se desea insertar
	 */
	public static void insert(String sentenciaSql) {
		Connection c;
		int afectadas;
		c = Bd_Relacionales_Full.conectarConBD(Bd_Relacionales_Full.pedirTexto("\tTipo de base de datos: "));
		try {
			Statement st = c.createStatement();
			afectadas = st.executeUpdate(sentenciaSql);
			System.out.println("Columnas afectadas -> " + afectadas);
		} catch (SQLException e) {
			System.out.println("Error en el SQL");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para la preparacion de la sentencia insert de empleado
	 */
	public static void insertEmpleadoEjemplo08() {
		String sentenciaSql = "";
		System.out.println(" -- INSERS -- ");
		String nomBase = Bd_Relacionales_Full.pedirTexto(" \tNombre de la Base de Datos que deseas insertar: ");
		String urlBaseDatos = Bd_Relacionales_Full.pedirTexto(" \tUrl de la BaseDatos: ");
		String tabla = Bd_Relacionales_Full.pedirTexto(" \n\tNombre de la tabla que se desea insertar: ");
		String emp_no = Bd_Relacionales_Full.pedirTexto(" \t-Número del empleado: ");
		String apellidos = Bd_Relacionales_Full.pedirTexto(" \t-Apellio del empleado: ");
		String oficio = Bd_Relacionales_Full.pedirTexto(" \t-Oficio: ");
		String dir = Bd_Relacionales_Full.pedirTexto(" \t-Dir: ");
		String salario = Bd_Relacionales_Full.pedirTexto(" \t-Salario: ");
		if (Double.parseDouble(salario) <= 0) {
			System.out.println("El salario deber ser mayor que 0");
			insertEmpleadoEjemplo08();
		}
		String comision = Bd_Relacionales_Full.pedirTexto(" \t-Comisión: ");
		String departameto = Bd_Relacionales_Full.pedirTexto(" \t-Departamento: ");
		// Hacer todas las comprobaciones y hacer el insert en caso de ser correctos
		if (comprobacionesInsert_consulta_Mysql_JDBC_BDRelacional("SELECT * FROM empleados", urlBaseDatos, nomBase,
				departameto, emp_no, dir)) {
			sentenciaSql = "INSERT INTO " + tabla + " VALUES('" + emp_no + "','" + apellidos + "','" + oficio + "','"
					+ dir + "'," + "sysDate()" + ",'" + salario + "','" + comision + "','" + departameto + "');";
			System.out.println("El insert queda así:  ");
			System.out.println(sentenciaSql +"\n");
			// Insertar en tabla
			insert(sentenciaSql);
		} else {
			System.out.println("**Los datos no son correctos.");
		}
	}

	/**
	 * Metodo que inicia el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		insertEmpleadoEjemplo08();
		System.out.println("\n ¡Saludos!");

	}

}
