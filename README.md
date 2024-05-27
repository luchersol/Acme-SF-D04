# README.txt

This is a starter project.  It is intended to be a core learning asset for the students
who have enrolled the Design and Testing subject of the Software Engineering curriculum of the 
University of Seville.  This project helps them start working on their new information system 
projects.

To get this project up and running, please follow the guideline in the theory/lab materials,
taking into account that you must link the appropriate version of the Acme-Framework excluding 
the following resources:

- **/fragments/**

## Aclaraciones

Para la instalación del antiSpam, el repositorio existente al mismo es el siguiente: https://github.com/luchersol/Anti-Spam-Project

Para la petición de las llamadas a la API está puesto que estas se realicen cada 90 días por defecto en el csv de system-configuration, de tal forma que en los ratios de cambio de divisa del dinero en los test se cogen siempre de base de datos para no hacer continuas llamadas a la API. En caso de querer probar el cambio de divisa con el día actual en el application#runner se debe cambiar la propiedad timeToUpdate de system-configuration para que mande realizar una actualización desde updateMoment.

El nombre de la carpeta al ser descargada es Acme-SF-D04-main, dicha carpeta debe tener el mismo nombre de como importas el proyecto en Eclipse:
  - En caso de importar el proyecto como se ponen en las transparecias con "[artefactId]-[version]", la carpeta debe de tener el nombre "Acme-SF-D04-24.4"
  - En caso de importar el proyecto y querer que el nombre de la carpeta sea "Acme-SF-D04", se debe poner de tal forma el nombre de la carpeta y luego importar el proyecto sin el "[artefactId]-[version]"

El nombre de la base de datos para ambos casos es Acme-SF-D04-24.4.0 debido a como está las carpetas plataform-development.properties y plataform-testing.properties, en caso de que se quiera que el nombre de la base de datos sea Acme-SF-D04 se deben cambiar en dichos archivos de la propiedad "spring.datasource.url" el parámetro ${acme.application.versioned-name} por ${acme.application.name}
