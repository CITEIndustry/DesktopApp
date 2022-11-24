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
        ArrayList<String> saltList = new ArrayList<String>();
        ArrayList<String> pepperList = new ArrayList<String>();
        String pwd0= "1234";
        String pwd1 = "4321";        
        String pwdSalt = generateSalt();
        saltList.add(pwdSalt);
        String pwdPepper = generatePepper();
        pepperList.add(pwdPepper);
        String hash1 = Password.hash(pwd0).addSalt(pwdSalt).addPepper(pwdPepper).withArgon2().getResult();
        pwdSalt = generateSalt();
        saltList.add(pwdSalt);
        pwdPepper = generatePepper();
        pepperList.add(pwdPepper);
        String hash2 = Password.hash(pwd1).addSalt(pwdSalt).addPepper(pwdPepper).withArgon2().getResult();
        hashList.add(hash1);
        hashList.add(hash2);
        if (!fDatabaseUser.exists()) { 
            initDatabaseUser(filePath1,hashList); 
        }
        if (!fDatabaseSalt.exists()) { 
            initDatabaseSalt(filePath2,saltList); 
        }
        if (!fDatabasePepper.exists()) { 
            initDatabasePepper(filePath3,pepperList); 
        }
        Connection connDB = UtilsSQLite.connect(filePath2);
        ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM salt;");
        //ResultSet rs2 = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE name='Lluis' and password='4321';");
        try {
            System.out.println(rs.getString("saltString"));
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
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS snapshot ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " day datetime, "
                                    + " idUser integer,"
                                    + " CONSTRAINT fk_user FOREIGN KEY (idUser) "
                                    + " REFERENCES user (id)"
                                    + " );");
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS components ("
                                    + " component varchar(1000),"
                                    + " idSnapshot integer,"
                                    + " CONSTRAINT fk_snapshot FOREIGN KEY (idSnapshot) "
                                    + " REFERENCES snapshot (id)"
                                    + " );");                        
        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (name,hash) VALUES (\"Enric\",\""+hashList.get(0)+"\");");
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (name,hash) VALUES (\"Lluis\",\""+hashList.get(1)+"\");");



        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static void initDatabaseSalt (String filePath,ArrayList<String> saltList) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS salt ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " saltString varchar(500) NOT NULL);");

        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO salt (saltString) VALUES (\""+saltList.get(0)+"\");");
        UtilsSQLite.queryUpdate(conn, "INSERT INTO salt (saltString) VALUES (\""+saltList.get(1)+"\");");
        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static void initDatabasePepper (String filePath,ArrayList<String> pepperList) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS pepper ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " pepperString varchar(500) NOT NULL); ");

        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO pepper (pepperString) VALUES (\""+pepperList.get(0)+"\");");
        UtilsSQLite.queryUpdate(conn, "INSERT INTO pepper (pepperString) VALUES (\""+pepperList.get(1)+"\");");
        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
    static String generateSalt(){
        String banc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < 10; x++) {
            int indiceAleatorio = (int) (Math.random()*banc.length()-1);
            char caracterAleatorio = banc.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
    static String generatePepper(){
        String banc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < 10; x++) {
            int indiceAleatorio = (int) (Math.random()*banc.length()-1);
            char caracterAleatorio = banc.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
}
