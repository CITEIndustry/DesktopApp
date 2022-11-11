import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class crearBaseDeDatos {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + File.separator;
        String filePath = basePath + "databaseIndustrial.db";
        File fDatabase = new File(filePath);
        if (!fDatabase.exists()) { 
            initDatabase(filePath); 
        }
        Connection connDB = UtilsSQLite.connect(filePath);
        ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE nom='usuario1' and contrasenya='1234';");
        try {
            System.out.println(rs.getString("nom"));
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
                                    + " nom varchar(15) NOT NULL, "
                                    + " contrasenya varchar(500));");


        // Afegir elements a una taula
        UtilsSQLite.queryUpdate(conn, "INSERT INTO user (nom,contrasenya) VALUES (\"test\",\"test\");");


        
        // Desconnectar
        UtilsSQLite.disconnect(conn);
    }
}