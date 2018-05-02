# appservicios

Pasos para instalar:

1. Iniciar un servidor MySQL (testeado con MySQL 5.7) local, puerto 3306
2. Correr el script crear-db.sql para crear el esquema y usuario a ser usado por el sistema
3. Importar repositorio en Eclipse (Con spring tools instalado) o Spring Tool Suite: github.com/acausality/appservicios
4. Importar el proyecto desde el repositorio como proyecto general
5. Convertir a proyecto maven (click derecho en el proyecto -> configure -> convert to maven project)
6. Deberian descargarse todas las dependencias necesarias. De no ser asi, click derecho en el proyecto -> maven -> update (o mvn clean install por consola)

Para ejecutar: 
1. Abrir el Boot Dashboard en Eclipse o STS, seleccionar "appservicios", y darle (Re)start para iniciar el servidor.
2. Acceder mediante browser a localhost:8080. 