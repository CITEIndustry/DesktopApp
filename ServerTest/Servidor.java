import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class Servidor extends WebSocketServer {
        private Connection connDB;

        public static void main(String[] args) throws IOException, InterruptedException {
            int port = 8888;
            java.lang.System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            Servidor servidor = new Servidor(port);
            servidor.start();
            System.out.println("Servidor funcionant al port: " + servidor.getPort());
            boolean running = true;
            Scanner sc = new Scanner(System.in);
            while(running){
                String line = sc.next();
                if(line.contentEquals("enviar")){
                    running=false;
                }
            }
            servidor.stop(1000);
        }
        public Servidor(int port) throws UnknownHostException {
            super(new InetSocketAddress(port));
        }
        public Servidor(InetSocketAddress address) {
            super(address);
        }
        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub
            //Accions a fer quan s'obre una connexio
            // Saludem personalment al nou client
            conn.send("Iniciat servidor industrial"); 

            // Enviem la direcció URI del nou client a tothom 
            broadcast("Nova connexió: " + handshake.getResourceDescriptor());

            // Mostrem per pantalla (servidor) la nova connexió
            String host = conn.getRemoteSocketAddress().getAddress().getHostAddress();
            System.out.println(host + " s'ha connectat");
        }
        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            broadcast(conn + " s'ha desconnectat");

        // Mostrem per pantalla (servidor) la desconnexió
        System.out.println(conn + " s'ha desconnectat");
        }
        @Override
        public void onError(WebSocket conn, Exception ex) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            System.out.println("Connectat client");
            setConnectionLostTimeout(0);
            setConnectionLostTimeout(100);
            String basePath = System.getProperty("user.dir") + File.separator;
            String filePath = basePath + "databaseIndustrial.db";
            connDB=UtilsSQLite.connect(filePath);
        }
        @Override public void onMessage(WebSocket conn, String message) {
            //Accions a fer quan es reben dades d'una conexio
            System.out.println("Rebut missatge: " + message);
        }
        @Override public void onMessage(WebSocket conn, ByteBuffer message) {
            //Accions a fer quan es reben dades d'una conexio
            Object objecte = bytesToObject(ByteBuffer.wrap(message.array()));
            if(objecte.getClass()==User.class){
                User usuari = (User) bytesToObject(ByteBuffer.wrap(message.array()));
                ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE nom='"+usuari.getNom()+"' and contrasenya='"+usuari.getContra()+"';");
                try {
                    if(rs.getString("nom")!=null){
                        System.out.println("OK Usuari correte");
                        System.out.println("Usuari "+usuari.getNom()+" Correcte");
                        this.broadcast("OK");
                        this.broadcast(objToBytes(usuari));
                    }
                    else{
                        System.out.println("ERROR Usuari incorrecte");
                        this.broadcast("ERROR");
                        this.stop(1000);
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        public static byte[] objToBytes (Object obj) {
            byte[] result = null;
            try {
                // Transforma l'objecte a bytes[]
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();
                result = bos.toByteArray();
            } catch (IOException e) { e.printStackTrace(); }
            return result;
        }
     
        public static Object bytesToObject (ByteBuffer arr) {
            Object result = null;
            try {
                // Transforma el ByteButter en byte[]
                byte[] bytesArray = new byte[arr.remaining()];
                arr.get(bytesArray, 0, bytesArray.length);
     
                // Transforma l'array de bytes en objecte
                ByteArrayInputStream in = new ByteArrayInputStream(bytesArray);
                ObjectInputStream is = new ObjectInputStream(in);
                return is.readObject();
     
            } catch (ClassNotFoundException e) { e.printStackTrace();
            } catch (UnsupportedEncodingException e) { e.printStackTrace();
            } catch (IOException e) { e.printStackTrace(); }
            return result;
        }
        
}