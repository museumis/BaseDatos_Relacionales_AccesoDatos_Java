package Ejemplo09_PrepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BD_RELACIONALES_FULL.Bd_Relacionales_Full;

/**
 * 
 * @author Ismael Martin
 *
 */
public class Main {
	/**
	 * Metodo para insertar sentencia en tablas mediante Statement
	 * 
	 * @param condicion numero de empleado que se desea visualizar
	 */
	public static void consulataPrepareStatemet_ejemplo09(int condicion) {
		String sql = "SELECT apellido,salario,oficio FROM empleados where emp_no = ?;";
		String sqlDos = "SELECT dnombre FROM departamentos WHERE dept_no=(SELECT dept_no FROM empleados WHERE emp_no = ?);";
		String sqlTres = "SELECT AVG(salario) FROM empleados WHERE emp_no = (SELECT dept_no FROM empleados where emp_no = ?);";
		String sqlCuatro = "SELECT COUNT(emp_no) FROM empleados WHERE dept_no = (SELECT dept_no FROM empleados WHERE emp_no = ?);";
		String departamento = "";
		String sqlCinco = "SELECT dnombre FROM departamentos";

		Connection c;
		c = Bd_Relacionales_Full.conectarConBD(Bd_Relacionales_Full.pedirTexto("\nTipo de base de datos: "));
		try {
			// Datos empleado
			PreparedStatement pst = c.prepareStatement(sql);
			pst.setInt(1, condicion);
			ResultSet r = pst.executeQuery();
			System.out.println("\n-- Empleados --");
			while (r.next()) {
				System.out.println("\t- Apellido -> " + r.getString("apellido"));
				System.out.println("\t- Salario -> " + r.getString("salario"));
				System.out.println("\t- Oficio -> " + r.getString("oficio"));
			}
			// Datos empresa
			pst = c.prepareStatement(sqlDos);
			pst.setInt(1, condicion);
			r = pst.executeQuery();
			while (r.next()) {
				departamento = r.getString(1);
				System.out.println("\t- Departamento-> " + departamento);

			}

			// Datos Salario medio del departamento
			pst = c.prepareStatement(sqlTres);
			pst.setInt(1, condicion);
			r = pst.executeQuery();
			while (r.next()) {
				System.out.println("\t- Salario Medio-> " + r.getString(1));
			}

			// Numero de empleados en el departamento
			pst = c.prepareStatement(sqlCuatro);
			pst.setInt(1, condicion);
			r = pst.executeQuery();
			while (r.next()) {
				System.out.println("\t- Empleados en el departamento-> " + r.getString(1));
			}
/*
			Statement st = c.createStatement();
			r = st.executeQuery(sqlDos);
			while (r.next()) {
				if (!r.getString(1).equalsIgnoreCase(departamento)) {
					System.out.println("El departamento no existe en la tabla.");
				}
			}
*/
			r.close();
			pst.close();
			c.close();

		} catch (SQLException e) {
			System.out.println("Error en el SQL");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("Prepare Statemente");
		consulataPrepareStatemet_ejemplo09(
				Integer.parseInt(Bd_Relacionales_Full.pedirTexto("Número del empleado que deseas visualizar: ")));
	}

}
