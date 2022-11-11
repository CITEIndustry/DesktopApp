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

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Servidor extends WebSocketServer {
        private Connection connDB;

        public static void main(String[] args) throws IOException, InterruptedException {
            int port = 8888;
            java.lang.System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            Servidor servidor = new Servidor(port);
            servidor.start();
            System.out.println("Server working on port: " + servidor.getPort());
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
            conn.send("Started industrial server"); 

            // Enviem la direcció URI del nou client a tothom 
            broadcast("New connection: " + handshake.getResourceDescriptor());

            // Mostrem per pantalla (servidor) la nova connexió
            String host = conn.getRemoteSocketAddress().getAddress().getHostAddress();
            System.out.println(host + " connected");
        }
        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            broadcast(conn + " disconnected");

        // Mostrem per pantalla (servidor) la desconnexió
        System.out.println(conn + " disconnected");
        }
        @Override
        public void onError(WebSocket conn, Exception ex) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            System.out.println("Connected client");
            setConnectionLostTimeout(0);
            setConnectionLostTimeout(100);
            String basePath = System.getProperty("user.dir") + File.separator;
            String filePath = basePath + "databaseIndustrial.db";
            connDB=UtilsSQLite.connect(filePath);
        }
        @Override public void onMessage(WebSocket conn, String message) {
            //Accions a fer quan es reben dades d'una conexio
            Gson gson = new Gson();
            User user = gson.fromJson(message, User.class);
            //Object objecte = bytesToObject(ByteBuffer.wrap(message.array()));
            //if(objecte.getClass()==JsonObject.class){
                //JsonObject usuari = (JsonObject) bytesToObject(ByteBuffer.wrap(message.array()));
                ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE nom='"+user.getNom()+"' and contrasenya='"+user.getContra()+"';");
                try {
                    if(rs.getString("nom")!=null){
                        System.out.println("OK correct user");
                        System.out.println("User "+user.getNom()+" Correct");
                        this.broadcast("OK");
                        //this.broadcast(objToBytes(user));
                    }
                    else{
                        System.out.println("ERROR incorrect user");
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
            //}
        }
        /*@Override public void onMessage(WebSocket conn, ByteBuffer message) {
            //Accions a fer quan es reben dades d'una conexio
            Object objecte = bytesToObject(ByteBuffer.wrap(message.array()));
            if(objecte.getClass()==JsonObject.class){
                JsonObject usuari = (JsonObject) bytesToObject(ByteBuffer.wrap(message.array()));
                ResultSet rs = UtilsSQLite.querySelect(connDB, "SELECT * FROM user WHERE nom='"+usuari.get("User")+"' and contrasenya='"+usuari.get("Password")+"';");
                try {
                    if(rs.getString("nom")!=null){
                        System.out.println("OK Usuari correte");
                        System.out.println("Usuari "+usuari.get("User")+" Correcte");
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
        }*/
     
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