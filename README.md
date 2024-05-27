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
