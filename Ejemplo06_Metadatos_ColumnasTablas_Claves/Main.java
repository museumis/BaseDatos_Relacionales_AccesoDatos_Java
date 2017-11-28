package Ejemplo06_Metadatos_ColumnasTablas_Claves;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD_RELACIONALES_FULL.Bd_Relacionales_Full;

/**
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
		ResultSet rBaseDatosTablas = null;
		ResultSet rColumnas = null;
		ResultSet rConstraint = null;
		tipoBaseDatos = tipoBaseDatos.toLowerCase();
		String contraseniaUser = "";
		String urlBD = "";
		String nombreBD = "";
		String nombreTabla = "";
		String nombreColumna = "";
		String parametroPrimaryKey = "";
		String parametroForeignKey = "";
		String catalogoTabla = "";
		String schemTabla = "";
		// CONEXION
		try {
			// Dependiendo de la base de datos creamos una conexión
			switch (tipoBaseDatos) {
			case "oracle": {
				System.out.println("Se trata de una Base de Datos de Oracle.");
				System.out.println("Introduce los siguientes datos.");
				nombreBD = Bd_Relacionales_Full.pedirTexto(" -Nombre de la base de datos: ");
				contraseniaUser = Bd_Relacionales_Full.pedirTexto(" -Contraseña: ");
				urlBD = Bd_Relacionales_Full.pedirTexto(" -Dirección de la Base de datos: ");
				// Conexion Oracle
				Class.forName("oracle.jdbc.OracleDriver");
				c = DriverManager
						.getConnection("jdbc:oracle:thin:" + nombreBD + "/" + contraseniaUser + "@//" + urlBD + "/XE");
				break;
			}
			case "sqlite": {
				System.out.println("Se trata de una Base de Sqlite embebida en el proyecto.");
				// Conexion Sqlite
				Class.forName("org.sqlite.JDBC");
				c = DriverManager
						.getConnection("jdbc:sqlite:" + "src\\Ejemplo06_Metadatos_ColumnasTablas_Claves\\empresa.db");
				break;
			}
			case "mysql": {
				System.out.println("Se trata de una Base de Datos de MySql.");
				System.out.println("Introduce los siguientes datos.");
				nombreBD = Bd_Relacionales_Full.pedirTexto(" -Nombre de la base de datos: ");
				urlBD = Bd_Relacionales_Full.pedirTexto(" -Dirección de la Base de datos: ");
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
				rConstraint = data.getPrimaryKeys(catalogoTabla, schemTabla, nombreTabla);
				while (rConstraint.next()) {
					System.out.println("*\tCONSTRAINT " + rConstraint.getString("COLUMN_NAME") + " PRIMARY KEY("
							+ rConstraint.getString("COLUMN_NAME") + ")");
				}
				// PRIMARY KEY
				rConstraint = data.getImportedKeys(catalogoTabla, schemTabla, nombreTabla);
				while (rConstraint.next()) {// Leyendo todas las columnas
					// FOREIGN KEY
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
			rColumnas.close();
			rConstraint.close();

			c.close();
		} catch (ClassNotFoundException e) {
			System.out.println("*Error al encontrar la clase");// e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("*La conexión con la BD falló.");// e.printStackTrace();
		}

	}// Fin de getMetadatos

	/**
	 * Metodo que inicia el programa
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
	}

}
