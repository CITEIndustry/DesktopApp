# DesktopApp
A. Base de Dades
La base de dades constará d'una sola taula User amb els atributs:
  - 1. Id: Es la clau primària de tipus numèric que s'autoincrementa.
  - 2. Name: Un varchar on documentem el nom de l'usuari.
  - 3. Password: Un varchar on guardem la contrasenya de l'usuari.
La base de dades ve amb un usuari de prova amb l'id: 1, el name: "usuario1" i la password: 1234.
Per a obtenir la base de dades s'ha d'executar el programa "createDatabase.java" i s'ha de tenir el programa "utilsSQLite.java" al mateix espai per a trucar funcions. 
Un cop s'ha executat "createDatabase.java" hi haurà a la seva mateixa carpeta un document "databaseIndustrial.db", amb l'usuari esmentat anteriorment.

B. API del Servidor que conecta amb l'app
Tenim una classe "Server.java" que en rebre un missatge compararà si hem d'enviar components o una confirmació. Per enviar la confirmació rebrem de l'aplicació una cadena
de text codificada de certa forma, el servidor treurà els components necessaris i compararà les credencials enviades amb la base de dades, si troba un usuari amb aquestes
credencials farà un broadcast que digui que tot està bé per a inicar l'aplicació, si no es així dirà que hi ha un error i tallarà la connexió.
