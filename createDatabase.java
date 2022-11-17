import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.password4j.Password;

public class createDatabase {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + File.separator;
        String filePath1 = basePath + "databaseIndustrialUser.db";
        String filePath2 = basePath + "databaseIndustrialSalt.db";
        String filePath3 = basePath + "databaseIndustrialPepper.db";
        File fDatabaseUser = new File(filePath1);
        File fDatabaseSalt = new File(filePath2);
        File fDatabasePepper = new File(filePath3);
        ArrayList<String> hashList = new ArrayList<String>();
        String pwd0= "1234";
        String pwd1 = "4321";        
        String pwdSalt = generateSalt();
        String pwdPepper = generatePepper();
        String hash1 = Password.hash(pwd0).addSalt(pwdSalt).addPepper(pwdPepper).withArgon2().getResult();
        pwdSalt = generateSalt();
        pwdPepper = generatePepper();
        String hash2 = Password.hash(pwd1).addSalt(pwdSalt).addPepper(pwdPepper).withArgon2().getResult();
        hashList.add(hash1);
        hashList.add(hash2);
        if (!fDatabaseUser.exists()) { 
            initDatabaseUser(filePath1,hashList); 
        }
        if (!fDatabaseSalt.exists()) { 
            initDatabaseSalt(filePath2,pwdSalt); 
        }
        if (!fDatabasePepper.exists()) { 
            initDatabasePepper(filePath3,pwdPepper); 
        }
        Connection connDB = UtilsSQLite.connect(filePath1);
        ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE name='Enric' and password='1234';");
        //ResultSet rs2 = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE name='Lluis' and password='4321';");
        try {
            System.out.println(rs.getString("name"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    static void initDatabaseUser (String filePath,ArrayList<String> hashList) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS user ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " name varchar(35) NOT NULL UNIQUE, "
                                    + " hash varchar(500));");

        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (name,hash) VALUES (\"Enric\",\""+hashList.get(0)+"\");");
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (name,hash) VALUES (\"Lluis\",\""+hashList.get(1)+"\");");



        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static void initDatabaseSalt (String filePath,String salt) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS salt ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " salt varchar(500));");

        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO salt (salt) VALUES (\""+salt+"\");");
        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static void initDatabasePepper (String filePath,String pepper) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS pepper ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " pepper varchar(500) NOT NULL, ");

        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO salt (pepper) VALUES (\""+pepper+"\");");
        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static String generateSalt(){
        String banc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        int longitud = (int) Math.random()*10-1;
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = (int) Math.random()*banc.length()-1;
            char caracterAleatorio = banc.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
    static String generatePepper(){
        String banc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        int longitud = (int) Math.random()*10-1;
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = (int) Math.random()*banc.length()-1;
            char caracterAleatorio = banc.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
}
