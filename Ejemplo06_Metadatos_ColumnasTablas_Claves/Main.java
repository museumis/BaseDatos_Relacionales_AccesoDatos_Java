package Ejemplo06_Metadatos_ColumnasTablas_Claves;

import BD_RELACIONALES_FULL.Bd_Relacionales_Full;

/**
 * 
 * @author Ismael Martin
 *
 */
public class Main {
	/**
	 * Metodo que inicia el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" - Metadatos -");
		// Consulta sqlite
		//src\Ejemplo06_Metadatos_ColumnasTablas_Claves\empresa.db
		
		Bd_Relacionales_Full.getMetadatos("sqlite");
		
		System.out.println("\n 1.BD SQLITE");
		System.out.println("----------------");
	//	Bd_Relacionales_Full.getMetadatos("sqlite");

		// Consulta oracle
		System.out.println("\n 2.BD DE ORACLE");
		System.out.println("----------------");
		Bd_Relacionales_Full.getMetadatos("oracle");

		// Consulta MySql
		System.out.println("\n 3.BD DE MYSQL");
		System.out.println("----------------");
		Bd_Relacionales_Full.getMetadatos("mysql");
	}

}
