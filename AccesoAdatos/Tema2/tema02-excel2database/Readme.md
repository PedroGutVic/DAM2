# Proyecto Excel2Database

Este programa Java convierte archivos Excel (.xlsx) a bases de datos MySQL y viceversa. Es un proyecto Maven que utiliza Apache POI para leer archivos Excel y JDBC para interactuar con MySQL.

## Funcionamiento del Programa

### 1. Estructura del Proyecto
```
src/
  ├── main/java/com/iesvdc/dam/acceso/
  │   ├── Excel2Database.java       # Clase principal
  │   ├── conexion/                 # Gestión de conexiones DB
  │   ├── excelutil/               # Utilidades para Excel
  │   └── modelo/                  # Modelos de datos
  └── test/java/                   # Tests unitarios
```

### 2. Código Importante

#### Detección de Tipos de Datos (ExcelReader.java)
La clase `ExcelReader` se encarga de leer el archivo Excel y determinar los tipos de datos:

```java
public FieldType getTipoDato(Cell cell) {
    switch (cell.getCellType()) {
        case STRING -> return FieldType.VARCHAR;
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
        case BOOLEAN -> return FieldType.BOOLEAN;
        default -> return FieldType.UNKNOWN;
    }
}
```

Este método es crucial ya que:
- Analiza el tipo de cada celda en Excel
- Convierte tipos de Excel a tipos SQL
- Maneja casos especiales como fechas y números decimales

#### Conversión de Tipos de Excel a SQL

La conversión de tipos se maneja a través del método `excelToSqlType`:

```java
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
```

#### Proceso de Migración

1. **Lectura del Libro Excel**
```java
public void loadWorkBook(String filename, String db) {
    try (FileInputStream fis = new FileInputStream(filename)) {
        wb = new XSSFWorkbook(fis);
        // Para cada hoja en el libro
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet hojaActual = wb.getSheetAt(i);
            // Procesa cada hoja como una tabla
            procesarHoja(hojaActual, db);
        }
    }
}
```

2. **Creación de Tablas**

```java
// Crear estructura de tabla basada en la primera fila (nombres de columnas)
StringBuilder sqlTabla = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tabla.getName() + " (");
for (int j = 0; j < ncols; j++) {
    String nombreColumna = primeraFila.getCell(j).getStringCellValue();
    FieldType tipo = getTipoDato(segundaFila.getCell(j));
    String tipoSQL = excelToSqlType(tipo);
    sqlTabla.append(nombreColumna).append(" ").append(tipoSQL);
    if (j < ncols - 1) sqlTabla.append(", ");
}
sqlTabla.append(");");
```

## Requisitos

- Java 11 o superior
- Maven
- MySQL Server

## Dependencias principales

- Apache POI (para manejo de archivos Excel)
- Apache POI OOXML (para formato .xlsx)
- MySQL Connector/J

## Uso del programa

```bash
excel2database -f fichero.xlsx -db nombre_base_datos
```

Donde:

- `-f`: Especifica el archivo Excel a procesar
- `-db`: Nombre de la base de datos a utilizar

## Configuración

Creamos el archivo **`config.properties`**:

```bash
user=root
password=s83n38DGB8d72
useUnicode=yes
useJDBCCompliantTimezoneShift=true
port=33307
database=agenda
host=localhost
driver=MySQL
outputFile=datos/salida.xlsx
inputFile=datos/entrada.xlsx
useSSL=false
serverTimezone=Europe/Madrid
allowPublicKeyRetrieval=true
```

En producción **jamás** debemos de usar estos parámetros:

* `useSSL=false`: No encripta la conexión.
* `allowPublicKeyRetrieval=true`: No comprueba el certificado (como el candado rojo del navegador)

## Manejo de tipos de datos

El programa utiliza Apache POI para detectar automáticamente los tipos de datos en las celdas de Excel. Los tipos soportados son:

### Tipos de celda en Excel

Cuando se lee una celda con `cell.getCellType()`, se pueden obtener los siguientes tipos:

| Tipo (`CellType`) | Significado                                                             |
| ----------------- | ----------------------------------------------------------------------- |
| `NUMERIC`         | Número (entero o decimal, o incluso fecha/hora si el formato lo indica) |
| `STRING`          | Texto                                                                   |
| `BOOLEAN`         | Verdadero/Falso                                                         |
| `FORMULA`         | Celda con una fórmula                                                   |
| `BLANK`           | Celda vacía                                                             |
| `ERROR`           | Celda con error                                                         |

Excel almacena las **fechas y horas como números** (un número de días desde el 1/1/1900).
Para distinguirlas, Apache POI ofrece un método:

```java
DateUtil.isCellDateFormatted(cell)
```

Si devuelve `true`, el contenido es una **fecha o una hora**, y puedes obtenerla así:

```java
Date fecha = cell.getDateCellValue();
```

Si no, puedes tratarlo como número:

```java
double valor = cell.getNumericCellValue();
```

En Excel, todos los números se almacenan como double internamente. No existe distinción “formal” entre enteros y decimales dentro del archivo. Por eso, Apache POI siempre te devuelve NUMERIC y cell.getNumericCellValue() da un double. Como Excel no distingue formalmente entre ellos: ambos son `NUMERIC`, podemos comprobarlo fácilmente así:

```java
double valor = cell.getNumericCellValue();
if (valor == Math.floor(valor)) {
    System.out.println("Entero: " + (int) valor);
} else {
    System.out.println("Decimal: " + valor);
}
```

Un ejemplo de cómo hacer la detección sería:

```java

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtils {

    private static final double EPSILON = 1e-10;

    /**
     * Devuelve un String indicando el tipo de dato de la celda.
     * Puede ser: Entero, Decimal, Texto, Booleano, Fecha, Vacía, Fórmula, Error
     */
    public static String getTipoDato(Cell cell) {
        if (cell == null) {
            return "Vacía";
        }

        switch (cell.getCellType()) {
            case STRING:
                return "Texto";

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return "Fecha";
                } else {
                    double valor = cell.getNumericCellValue();
                    if (Math.abs(valor - Math.floor(valor)) < EPSILON) {
                        return "Entero";
                    } else {
                        return "Decimal";
                    }
                }

            case BOOLEAN:
                return "Booleano";

            case FORMULA:
                // Puedes decidir si quieres evaluar la fórmula o solo indicar que es fórmula
                return "Fórmula";

            case BLANK:
                return "Vacía";

            case ERROR:
                return "Error";

            default:
                return "Desconocido";
        }
    }
}

```

## Ejemplos de SQL Generado

El programa genera automáticamente el SQL necesario para todas las operaciones.

### Ejemplo de Creación de Base de Datos

```sql
CREATE DATABASE `agenda` COLLATE 'utf16_spanish_ci';
```

### Ejemplo de Creación de Tabla

```sql
CREATE TABLE `personas` (
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(300) NOT NULL,
  `email` varchar(100),
  `telefono` varchar(12),
  `genero` enum('FEMENINO','MASCULINO','NEUTRO','OTRO') NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE='InnoDB';
```

### Ejemplo de Inserción de Datos

```sql
INSERT INTO `personas` (
    `nombre`, `apellidos`, 
    `email`, `telefono`, 
    `genero`)
VALUES (
    'Juan', 'Sin Miedo', 
    'juan@sinmiedo.com', '+34555123456', 
    'OTRO');
```

### Comandos SQL Útiles

```sql
-- Ver todos los registros de una tabla
SELECT * FROM `personas` LIMIT 50;

-- Ver registros con paginación
SELECT * FROM `personas` LIMIT 5 OFFSET 10;

-- Eliminar una base de datos
DROP DATABASE `agenda`;
```

## Problemas Comunes y Soluciones

### 1. Errores de Conexión

Si hay problemas de conexión a la base de datos, verificar:

- Que el servidor MySQL esté corriendo
- Los datos de conexión en `config.properties`
- El puerto correcto (por defecto 3306)
- Permisos del usuario de la base de datos

### 2. Tipos de Datos Incorrectos

Al convertir Excel a MySQL:

- Verificar que la segunda fila tenga datos representativos
- Revisar que las fechas estén en formato correcto
- Comprobar que los números decimales usen punto como separador

### 3. Problemas de Codificación

Para evitar problemas con caracteres especiales:

- Usar UTF-8 en los archivos Excel
- Configurar `useUnicode=yes` en `config.properties`
- Usar collation `utf16_spanish_ci` en la base de datos

## Notas de Desarrollo

- El proyecto usa Maven para gestión de dependencias
- Apache POI se usa para la lectura de Excel
- Se utiliza JDBC para la conexión a MySQL
- El código está estructurado en paquetes para mejor organización
- Se incluyen pruebas unitarias básicas
