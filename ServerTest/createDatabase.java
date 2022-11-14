import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class createDatabase {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + File.separator;
        String filePath = basePath + "databaseIndustrial.db";
        File fDatabase = new File(filePath);
        if (!fDatabase.exists()) { 
            initDatabase(filePath); 
        }
        Connection connDB = UtilsSQLite.connect(filePath);
        ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE name='usuario1' and password='1234';");
        try {
            System.out.println(rs.getString("name"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    static void initDatabase (String filePath) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        /*UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");
        */
        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS user ("
                                    + " id integer PRIMARY KEY AUTOINCREMENT,"
                                    + " name varchar(15) NOT NULL, "
                                    + " password varchar(500));");


        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (name,password) VALUES (\"usuario1\",\"1234\");");


        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
}
