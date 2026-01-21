/**
 * Paquete que contiene las utilidades para leer archivos Excel
 */
package com.iesvdc.dam.acceso.excelutil;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iesvdc.dam.acceso.conexion.Conexion;
import com.iesvdc.dam.acceso.modelo.FieldType;
import com.iesvdc.dam.acceso.modelo.TableModel;
import com.iesvdc.dam.acceso.modelo.WorkbookModel;

/**
 * Clase que maneja la lectura de archivos Excel y su conversión a bases de
 * datos
 */
public class ExcelReader {
    /** Contador de registros procesados */
    private static int contador = 0;
    /** Libro de trabajo Excel */
    private Workbook wb;
    /** Modelo del libro de trabajo */
    private WorkbookModel wbm;

    /**
     * Constructor por defecto
     */
    public ExcelReader() {
    }

    /** Constante para comparación de números decimales */
    private final double EPSILON = 1e-10;

    /**
     * Devuelve un String indicando el tipo de dato de la celda.
     * Puede ser: Entero, Decimal, Texto, Booleano, Fecha, Vacía, Fórmula, Error
     */
    public FieldType getTipoDato(Cell cell) {
        if (cell == null) {

            return FieldType.UNKNOWN;
        }

        switch (cell.getCellType()) {
            case STRING -> {
                return FieldType.VARCHAR;
            }

            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return FieldType.DATE;
                } else {
                    double valor = cell.getNumericCellValue();
                    if (Math.abs(valor - Math.floor(valor)) < EPSILON) {
                        return FieldType.INTEGER;
                    } else {
                        return FieldType.DECIMAL;
                    }
                }
            }

            case BOOLEAN -> {
                return FieldType.BOOLEAN;
            }

            case FORMULA -> {
                // Puedes decidir si quieres evaluar la fórmula o solo indicar que es fórmula
                return FieldType.UNKNOWN;
            }

            case BLANK -> {
                return FieldType.UNKNOWN;
            }

            case ERROR -> {
                return FieldType.UNKNOWN;
            }

            default -> {
                return FieldType.UNKNOWN;
            }
        }
    }

    public void loadWorkBook(String filename, String db) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            wb = new XSSFWorkbook(fis);
            wbm = new WorkbookModel();

            int numeroDeHojas = wb.getNumberOfSheets();
            // Empiezo por la primera hoja del libro

            // El nombre de la tabla es el nombre de la hoja
            for (int i = 0; i < numeroDeHojas; i++) {

                Sheet hojaActual = wb.getSheetAt(i);

                TableModel tabla = new TableModel(hojaActual.getSheetName());

                wbm.addTable(new TableModel(wb.getSheetName(i)));

                // de la primera fila tomo las cabeceras
                Row primeraFila = hojaActual.getRow(0);
                // obtengo el numero de columnas
                int ncols = primeraFila.getLastCellNum();

                Row segundaFila = hojaActual.getRow(1);
                Connection conexion = Conexion.getConnection(db);
                Statement stmt = conexion.createStatement();

                // insertar la tabla en la base de datos

                StringBuilder sqlTabla = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tabla.getName() + " (");

                for (int j = 0; j < ncols; j++) {
                    String nombreColumna = primeraFila.getCell(j).getStringCellValue();
                    FieldType tipo = getTipoDato(segundaFila.getCell(j));
                    String tipoSQL = excelToSqlType(tipo);

                    sqlTabla.append(nombreColumna).append(" ").append(tipoSQL);

                    if (j < ncols - 1)
                        sqlTabla.append(", ");
                }

                sqlTabla.append(");");
                System.out.println(sqlTabla.toString());
                stmt.execute(sqlTabla.toString());

                wbm.addTable(tabla);

                // recorrer las filas de la hoja para insertar los datos en la tabla
                int nrows = hojaActual.getLastRowNum();
                for (int k = 1; k <= nrows; k++) {
                    Row filaActual = hojaActual.getRow(k);
                    if (filaActual == null)
                        continue;

                    StringBuilder columnas = new StringBuilder();
                    StringBuilder valores = new StringBuilder();

                    for (int l = 0; l < ncols; l++) {
                        String nombreColumna = primeraFila.getCell(l).getStringCellValue().trim().replace(" ", "_");
                        Cell celdaActual = filaActual.getCell(l);

                        columnas.append(nombreColumna);

                        if (celdaActual == null) {
                            valores.append("NULL");
                        } else {
                            String valor = celdaActual.toString().replace("'", "''");
                            valores.append("'").append(valor).append("'");
                        }

                        if (l < ncols - 1) {
                            columnas.append(", ");
                            valores.append(", ");
                        }
                    }

                    agregarInToTabla(tabla, columnas.toString(), valores.toString(), db);
                }
            }

        } catch (Exception e) {
            System.out.println("Imposible cargar el archivo");
            e.printStackTrace();
        }
    }

    public static String excelToSqlType(FieldType ft) {
        return switch (ft) {
            case INTEGER -> "INT";
            case DECIMAL -> "DECIMAL(10,2)";
            case VARCHAR -> "VARCHAR(255)";
            case DATE -> "DATE";
            case BOOLEAN -> "BOOLEAN";
            default -> "TEXT";
        };
    }

    public static void agregarInToTabla(TableModel tabla, String columnas, String valores, String db) {

        String sqlInsert = "INSERT INTO " + tabla.getName() + " (" + columnas + ") VALUES (" + valores + ")";
        try (Connection conexion = Conexion.getConnection(db)) {
            if (conexion != null) {
                Statement stmt = conexion.createStatement();
                stmt.execute(sqlInsert);
                System.out.println("Datos insertados en la tabla: " + tabla.getName() + "Contador : " + contador);
                contador++;
            }
        } catch (Exception e) {
            System.err.println("Error al insertar datos en la tabla: " + tabla.getName());
            e.printStackTrace();
        }
    }
}