# Proyecto Spring Boot + MySQL con Dev Containers

Este proyecto es un **esqueleto base** para calentar el plato rápidamente si desarrollamos con **Spring Boot**, **MySQL** y **Dev Containers**, pensado para que cualquier persona pueda:

> clonar → abrir en VS Code → levantar el entorno → empezar a programar

sin configuraciones manuales de Java, Maven o base de datos en la máquina.

## Requisitos previos

Antes de clonar el proyecto, asegúrate de tener **todo esto instalado y funcionando** en tu sistema:

### Software obligatorio

- **Visual Studio Code** (versión oficial de Microsoft). No es compatible con *Code OSS*
- **Docker** (Engine o Docker Desktop)
- **Docker Compose**
- **Docker Buildx**

Puedes comprobarlo con:

```bash
docker --version
docker compose version
docker buildx version
```

Si estás en Linux recuerda añadir tu usuario al grupo docker. Si no sabes cómo, pregunta al GPT que normalmente uses sobre el proceso.

En el caso de estar en Windows, deberás usar el subsistema de Windows para Linux (WSL) con Docker Desktop. Si ya tienes instalados otros sistemas de virtualización como VMWare o VirtualBox, puede que tengas problemas.

### Extensiones de VS Code

* **Dev Containers** (`ms-vscode-remote.remote-containers`)
  Esta extensión es **imprescindible** para que el proyecto funcione.

## Clonado del proyecto

```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DEL_PROYECTO>
```

## Configuración inicial (.env)

Por motivos de seguridad, el fichero `.env` **no se versiona**, no se debe añadir al sistema de control de versiones.

Debes crearlo **antes de levantar el contenedor**.

En la raíz del proyecto:

```bash
cp .env.example .env
```

Puedes editar `.env` si necesitas cambiar credenciales, puertos o nombres de base de datos.

## Arranque del entorno de desarrollo

1. Abre la carpeta del proyecto en **Visual Studio Code**
2. VS Code detectará el Dev Container y mostrará un aviso
   → selecciona **“Reopen in Container”**
3. Espera a que se construya el entorno (la primera vez tarda más)
4. Abre una terminal integrada **dentro del contenedor**
5. Arranca la aplicación:

```bash
./mvnw spring-boot:run
```

## Comprobación rápida

Abre un navegador en tu máquina y entra en:

```
http://localhost:8080/
```

Deberías ver:

```
Hola mundo desde Dev Containers!
```

Sin funciona hemos conseguido:

* Correr Java dentro del contenedor
* Spring Boot + MySQL + Adminer
* El proyecto está listo para desarrollar

## Base de datos MySQL

* MySQL se ejecuta en un contenedor Docker
* El puerto expuesto al host es **33306**
* Las credenciales se definen en el fichero `.env`

Puedes conectarte desde herramientas externas (DBeaver, MySQL Workbench, etc.) usando `localhost:33306`.

Puedes interactuar vía Web de manera muy sencilla mediante el cliente "Adminer" accediendo a: (http://localhost:8181)[http://localhost:8181].

## Filosofía del proyecto

* No se requiere Java, ni Maven, ni MySQL instalados en el host
* Maven se gestiona mediante **Maven Wrapper**
* El entorno es **idéntico para todos los desarrolladores**
* Ideal para docencia, equipos o proyectos reproducibles

## Flujo recomendado de trabajo

```text
Clonar → copiar .env → Reopen in Container → ./mvnw spring-boot:run → programar
```

## Notas importantes

* Si se recrea el contenedor, las dependencias Maven se conservan gracias al volumen de caché
* El fichero `.env` debe existir antes de levantar el Dev Container
* No se debe versionar `.env` bajo ningún concepto (no añadir a GIT CVS)

## Desarrollo seguro, buenas prácticas y estándares

¿Por qué complicarnos usando variables de entorno? Porque este proyecto adopta principios de **desarrollo seguro** alineados con recomendaciones ampliamente aceptadas en la industria del software, tanto a nivel técnico como organizativo. Separar código, configuración y secretos:

- reduce riesgos de seguridad,
- mejora la mantenibilidad,
- facilita auditorías y cumplimiento normativo,
- y prepara el proyecto para entornos profesionales y productivos.

A continuación te listamos las recomendaciones en las que nos hemos basado y que nos gustaría que investigues y estudies de cara a tu futuro profesional.

### OWASP

La externalización de credenciales mediante variables de entorno y ficheros `.env` está directamente relacionada con las recomendaciones de **OWASP**, especialmente:

- **A02:2021 – Cryptographic Failures**  
  Evita la exposición de credenciales y secretos en el código fuente.
- **A05:2021 – Security Misconfiguration**  
  Reduce errores derivados de configuraciones inseguras o inconsistentes entre entornos.
- **A07:2021 – Identification and Authentication Failures**  
  Promueve una gestión adecuada de credenciales y accesos.

Separar código y secretos minimiza el riesgo de fugas de información en repositorios, logs o copias de seguridad.


### ISO/IEC 27001

Desde el punto de vista organizativo, este enfoque es coherente con **ISO/IEC 27001**, en particular con controles relacionados con:

- Gestión segura de la información
- Control de accesos y protección de credenciales
- Separación entre desarrollo, pruebas y producción
- Prevención de divulgación no autorizada de información sensible

El uso de `.env` y variables de entorno facilita la trazabilidad y el cumplimiento de políticas de seguridad de la información en diferentes entornos.


### 12-Factor App

El proyecto también sigue principios del modelo **12-Factor App**, un conjunto de buenas prácticas para desarrollar aplicaciones modernas, especialmente orientadas a despliegue en contenedores y entornos cloud.

En concreto:

- **Factor III – Configuración**  
  La configuración debe almacenarse en **variables de entorno**, no en el código ni en ficheros versionados.
- **Factor I – Codebase**  
  Un único repositorio puede desplegarse en múltiples entornos sin cambios en el código.
- **Factor VI – Procesos**  
  La aplicación es independiente del entorno donde se ejecuta, gracias a la externalización de la configuración.

Este enfoque permite que el mismo código funcione en desarrollo, pruebas y producción cambiando únicamente las variables de entorno, sin recompilar ni modificar la aplicación.

