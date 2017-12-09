package BD_RELACIONALES_FULL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.net.aso.h;

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
	// ************************************************************
	// ********************** CONEXIONES **************************
	// ************************************************************
	/**
	 * Procedimiento para conectarse a una base de datos
	 * Oracle,Mysql,PosgressSql,Sqlite
	 * 
	 * @param tipoBaseDatos a la que se desea conectar
	 * @return objeto Connection
	 */
	public static Connection conectarConBD(String tipoBaseDatos) {
		Connection c = null;
		tipoBaseDatos = tipoBaseDatos.toLowerCase();
		String userBD = "";
		String contraseniaUser = "";
		String nombreBD = "";
		String urlBD = "";
		switch (tipoBaseDatos) {
		case "oracle": {
			System.out.println("\n~~Se trata de una Base de Datos de Oracle.~~");
			System.out.println("Introduce los siguientes datos.");
			userBD = pedirTexto("\t·Nombre de la base de datos: ");
			contraseniaUser = pedirTexto("\t·Contraseña: ");
			urlBD = pedirTexto("\t·Dirección de la Base de datos: ");
			// Conexion Oracle
			return conectarConOracle(urlBD, userBD, contraseniaUser);
		}
		case "sqlite": {
			System.out.println("\n~~Se trata de una Base de Sqlite embebida en el proyecto.~~");
			System.out.println("Introduce los siguientes datos.");
			urlBD = pedirTexto("\t·Dirección de la Base de datos: ");
			// Conexion Sqlite
			return conectarConSqlite(urlBD);
		}
		case "mysql": {
			System.out.println("\n~~Se trata de una Base de Datos de MySql.~~");
			System.out.println("Introduce los siguientes datos.");
			nombreBD = pedirTexto("\t·Nombre de la base de datos: ");
			userBD = pedirTexto("\t·Usuario de acceso: ");
			urlBD = pedirTexto("\t·Dirección de la Base de datos: ");
			// Conexion Squlite
			return conectarConMySql(urlBD, nombreBD, userBD);
		}
		case "posgresql": {
			System.out.println("\n~~Se trata de una Base de PosgreSQL.~~");
			System.out.println("\t·Introduce los siguientes datos.");
			userBD = pedirTexto("\t·Nombre de la base de datos: ");
			contraseniaUser = pedirTexto("\t·Contraseña: ");
			urlBD = pedirTexto("\t·Dirección de la Base de datos: ");
			// Conexion Oracle
			return conectarConOracle(urlBD, userBD, contraseniaUser);
		}
		default:
			System.out.println("**El tipo de la base de datos es erróneo.");
			break;
		}
		return c;
	}// Fin de conectar a una base de datos

	/**
	 * Conectar con una base de datos MYSQL
	 * 
	 * @param urlBD direccion ip de la BD
	 * @param nombreBD nombre de la base de datos
	 * @param userBD usuario de acceso
	 * @return un objeto Connection
	 */
	public static Connection conectarConMySql(String urlBD, String nombreBD, String userBD) {
		Connection c = null;
		try {
			// Conectar al servidor
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + urlBD + "/" + nombreBD, userBD, "");

		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la base de datos.");
		} catch (SQLException e) {
			System.out.println("*Error SQL.");
			e.printStackTrace();
		}
		return c;
	}// Fin de conectar con MySql

	/**
	 * Conectar con una base de datos SQLITE
	 * 
	 * @param urlBD direccion de la BD
	 * @return un objeto Connection
	 */
	public static Connection conectarConSqlite(String urlBD) {
		Connection c = null;
		try {
			// Conectar al servidor
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + urlBD);

		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la base de datos.");
		} catch (SQLException e) {
			System.out.println("*Error SQL.");
			e.printStackTrace();
		}
		return c;
	}// Fin de conectar con Squlite

	/**
	 * Conectar con una base de datos Oracle
	 * 
	 * @param urlBD direccion ip de la BD
	 * @param userBD usuario de acceso
	 * @param contraseniaUser contraseña de acceso
	 * @return un objeto Connection
	 */
	public static Connection conectarConOracle(String urlBD, String userBD, String contraseniaUser) {
		Connection c = null;
		try {
			// Conectar al servidor
			Class.forName("oracle.jdbc.OracleDriver");
			c = DriverManager
					.getConnection("jdbc:oracle:thin:" + userBD + "/" + contraseniaUser + "@//" + urlBD + "/XE");

		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la base de datos.");
		} catch (SQLException e) {
			System.out.println("*Error SQL.");
			e.printStackTrace();
		}
		return c;
	}// Fin de conectar con Oracle

	/**
	 * Conectar con una base de datos Postgresql
	 * 
	 * <pre>
	 * <a href=http://jdbc.postgresql.org/download.html>Driver de Postgres</a>
	 * </pre>
	 * 
	 * @param urlBD direccion ip de la BD
	 * @param userBD usuario de acceso
	 * @param contraseniaUser contraseña de acceso
	 * @return un objeto Connection
	 */
	public static Connection conectarConPostgresql(String urlBD, String userBD, String contraseniaUser) {
		Connection c = null;
		try {
			// Conectar al servidor
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://" + urlBD, userBD, contraseniaUser);

		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la base de datos.");
		} catch (SQLException e) {
			System.out.println("*Error SQL.");
			e.printStackTrace();
		}
		return c;
	}// Fin de conectar con Postgresql

	// ************************************************************
	// *********************** CONSULTAS **************************
	// ************************************************************
	/**
	 * Método que realiza una consulta a una base de datos MySql hospeda en un
	 * servidor mediante JDBC.
	 * 
	 * <pre>
	 * XAMPP -> <a href="www.apachefriends.org/es/index.html">download</a>
	 * Crear base de datos en PHPMYADMIN ->XAMPP/apache,mySQL activada/mySQL-admin
	 * Importar la libreria ->mysql.connector.java.jar -> <a href=
	"www.mysql.com/products/connection">libreria mysql connector</a>
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
				// ***System.out.println("~~Empleado~~");
				// ***System.out.println("Nombre: " + r.getString(2));
				// ***System.out.println("Oficio: " + r.getString(3));
				// ***System.out.println("Salario: " + r.getInt(6) + "\n");
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
	}// Fin de consulta_Mysql_JDBC_BDRelacional

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
			Connection c = DriverManager
					.getConnection("jdbc:oracle:thin:" + userBD + "/" + contraseniaUser + "@//" + urlBD + "/XE");
			// Consulta
			Statement st = c.createStatement();
			ResultSet r = st.executeQuery(consulta);
			// Mostrar consulta
			while (r.next()) {
				// Al consultar la tabla el ResultSet obtiene las columnas
				// ***System.out.println("~~Empleado~~");
				// ***System.out.println("Nombre: " + r.getString(2));
				// ***System.out.println("Oficio: " + r.getString(3));
				// ***System.out.println("Salario: " + r.getInt(6) + "\n");
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
				// ***System.out.println("~~Empleado~~");
				// ***System.out.println("Nombre: " + r.getString(2));
				// ***System.out.println("Oficio: " + r.getString(3));
				// ***System.out.println("Salario: " + r.getInt(6) + "\n");
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
	
	// ************************************************************
	// *********************** INSERTS **************************
	// ************************************************************
	
	/**
	 * Metodo para insertar sentencia en tablas
	 * 
	 * @param sentenciaSql del insert que se desea insertar
	 */
	public static void insert(String sentenciaSql) {
		Connection c;
		int afectadas;
		c = Bd_Relacionales_Full.conectarConBD(Bd_Relacionales_Full.pedirTexto("Tipo de base de datos: "));
		try {
			Statement st = c.createStatement();
			afectadas = st.executeUpdate(sentenciaSql);
			System.out.println("Columnas afectadas -> " + afectadas);
		} catch (SQLException e) {
			System.out.println("Error en el SQL");
			e.printStackTrace();
		}
	}

	// ************************************************************
	// *********************** METADATOS **************************
	// ************************************************************
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
	public static void getMetadatos_Simple(String tipoBaseDatos) {
		// Preparativos
		Connection c = null;
		ResultSet r = null;
		tipoBaseDatos = tipoBaseDatos.toLowerCase();
		try {
			c = conectarConBD(tipoBaseDatos);
			// Preparativos para mostrar
			DatabaseMetaData data = c.getMetaData();
			r = data.getTables(null, pedirTexto("Nombre de la Base de Datos: "), null, null);
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
		} catch (SQLException e) {
			System.out.println("*Error en el SQL");// e.printStackTrace();
		}

	}// Fin de getMetadatos

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
		ResultSet rBaseDatosTablas = null;
		ResultSet rColumnas = null;
		ResultSet rConstraint = null;
		tipoBaseDatos = tipoBaseDatos.toLowerCase();
		String nombreBD = "";
		String nombreTabla = "";
		String nombreColumna = "";
		String parametroForeignKey = "";
		String catalogoTabla = "";
		String schemTabla = "";
		// CONEXION
		try {
			c = conectarConBD(tipoBaseDatos);
			// Preparativos para mostrar
			DatabaseMetaData data = c.getMetaData();
			rBaseDatosTablas = data.getTables(null, nombreBD, null, null);
			// MOSTRAR METADATOS
			// Informacion general
			;
			System.out.println("\n###########################################");
			System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t~~ INFORMACIÓN GENERAL ~~");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t·Producida con -> " + data.getDatabaseProductName() + "["
					+ data.getDatabaseProductVersion() + "]");
			System.out.println("*\t·Driver -> " + data.getDriverName() + "[" + data.getDriverVersion() + "]");
			System.out.println("*\t·Conexión -> " + data.getConnection());
			System.out.println("*\t·Hospedaje -> " + data.getURL());
			System.out.println("*\t·Usuario -> " + data.getUserName());
			// ¿Por qué querría la contraseña de una base de datos?
			// System.out.println("Contraseña-> Imposible saber con sql simple");
			// Tablas
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("*\t~~       TABLAS       ~~");
			System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			// Recorre todas las tablas y por cada tabla sus columanas,su Primarykey y su
			// ForeingKey
			while (rBaseDatosTablas.next()) {
				// TABLAS
				catalogoTabla = rBaseDatosTablas.getString("TABLE_CAT");
				schemTabla = rBaseDatosTablas.getString("TABLE_SCHEM");
				nombreTabla = rBaseDatosTablas.getString("TABLE_NAME");
				System.out.println("*\t·" + catalogoTabla + " -> " + schemTabla + " -> "
						+ rBaseDatosTablas.getString("TABLE_TYPE") + " -> " + nombreTabla);
				// COLUMNAS
				rColumnas = data.getColumns(null, null, nombreTabla, null);
				System.out.println("*\t(");
				while (rColumnas.next()) {
					nombreColumna = rColumnas.getString("COLUMN_NAME");
					System.out.println(("*\t" + nombreColumna + " " + rColumnas.getString("TYPE_NAME") + " [Null:"
							+ rColumnas.getString("IS_NULLABLE") + "]"));
				} // while columnas
					// PRIMARY KEY
				rConstraint = data.getPrimaryKeys(catalogoTabla, schemTabla, nombreTabla);
				while (rConstraint.next()) {
					System.out.println("*\tCONSTRAINT " + rConstraint.getString("COLUMN_NAME") + " PRIMARY KEY("
							+ rConstraint.getString("COLUMN_NAME") + ")");
				}
				// FOREIGN KEY
				rConstraint = data.getImportedKeys(catalogoTabla, schemTabla, nombreTabla);
				while (rConstraint.next()) {// Leyendo todas las columnas
					parametroForeignKey = rConstraint.getString("FKCOLUMN_NAME");
					System.out.println("*\tCONSTRAINT " + rConstraint.getString("PK_NAME") + " FOREIGN KEY("
							+ parametroForeignKey + ")");
				} // While constraint

				System.out.println("*\t);\n*");
			} // While tablas

			// FIN DE MOSTRAR
			System.out.println("*");
			System.out.println("###########################################\n");
			// Cierre flujos
			rBaseDatosTablas.close();
			if (rColumnas != null) {
				rColumnas.close();
			}
			if (rConstraint != null) {
				rConstraint.close();
			}

			c.close();
		} catch (SQLException e) {
			System.out.println("*La conexión con la BD falló.");// e.printStackTrace();
		}

	}// Fin de getMetadatos

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
	}// Fin de get columna

	// ************************************************************
	// *********************** OTROS **************************
	// ************************************************************
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
}// Fin de FULL
