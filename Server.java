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
import java.util.ArrayList;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.password4j.Password;


public class Server extends WebSocketServer {
        private Connection connDBUser;
        private Connection connDBSalt;
        private Connection connDBPepper;
        
        //private ArrayList<JS

        public Server(int port) throws UnknownHostException {
            super(new InetSocketAddress(port));
        }
        public Server(InetSocketAddress address) {
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
            String filePath1 = basePath + "databaseIndustrialUser.db";
            String filePath2 = basePath + "databaseIndustrialSalt.db";
            String filePath3 = basePath + "databaseIndustrialPepper.db";
            connDBUser=UtilsSQLite.connect(filePath1);
            connDBSalt=UtilsSQLite.connect(filePath2);
            connDBPepper=UtilsSQLite.connect(filePath3);
        }
        @Override public void onMessage(WebSocket conn, String message) {
            //Accions a fer quan es reben dades d'una conexio
            String[] messageList = message.split(";;");
            if(message.equals("getComponents")){
                String switchText="";
                String sliderText="";
                String comboText="";
                String sensorText="";
                if(Main.toggleButtons!=null||Main.sliders!=null||Main.dropdowns!=null){
                    for(int i : Main.toggleButtons.keySet()){
                        switchText="switch::"+Main.toggleButtons.get(i).getId()+"::"+Main.toggleButtons.get(i).getDefaultVal()+"::"+Main.toggleButtons.get(i).getLabel();
                        this.broadcast(switchText);
                        System.out.println("frameeee");
                    }
                    for(int i : Main.sliders.keySet()){
                        sliderText="slider::"+Main.sliders.get(i).getId()+"::"+Main.sliders.get(i).getDefaultVal()+"::"+Main.sliders.get(i).getMax()
                        +"::"+Main.sliders.get(i).getMin()+"::"+Main.sliders.get(i).getStep();
                        this.broadcast(sliderText);
                    }
                    System.out.println(Main.dropdowns.size());
                    for(int i : Main.dropdowns.keySet()){
                        comboText="dropdown::"+Main.dropdowns.get(i).getId()+"::"+Main.dropdowns.get(i).getDefaultVal()+"::";
                        for(int j=0;j<Main.dropdowns.get(i).getOption().length;j++){
                            comboText=comboText+Main.dropdowns.get(i).getOption()[j][0]+":"+Main.dropdowns.get(i).getOption()[j][1]+"/";
                        }
                        this.broadcast(comboText);
                    }
                    for(int i : Main.sensors.keySet()){
                        sensorText="sensor::"+Main.sensors.get(i).getId()+"::"+Main.sensors.get(i).getUnits()+"::"+Main.sensors.get(i).getThresholdlow()
                        +"::"+Main.sensors.get(i).getThresholdhight()+"::"+Main.sensors.get(i).getValue();
                        this.broadcast(sensorText);
                        System.out.println("sensor");
                    }
                    this.broadcast("Send");
                }
                else{
                    this.broadcast("message::ERROREMPTY");
                }
                
            }
            else if(messageList[0].equalsIgnoreCase("change")){
                String[] componentData = messageList[1].split("::");
                if(componentData[0].equalsIgnoreCase("switch")){
                    if(componentData[2].equalsIgnoreCase("on")){
                        Main.switches.get(Integer.parseInt(componentData[1])).setSelected(true);
                    }
                    else if(componentData[2].equalsIgnoreCase("off")){
                        Main.switches.get(Integer.parseInt(componentData[1])).setSelected(false);
                    }
                }
                else if(componentData[0].equalsIgnoreCase("slider")){
                    Main.jsliders.get(Integer.parseInt(componentData[1])).setValue(Integer.parseInt(componentData[2]));
                }
                else if(componentData[0].equalsIgnoreCase("sropdown")){
                    Main.comboBoxes.get(Integer.parseInt(componentData[1])).setSelectedIndex(Integer.parseInt(componentData[2]));
                }
            }
            else{
                String[] data = message.split(";;");
                String[] userData =null;
                for(int i=0;i<data.length;i++){
                    if(data[i].split("::")[0].equals("User")){
                        userData = data[i].split("::");
                        System.out.println(userData[1]);
                    }
                }
                //Gson gson = new Gson();
                //User user = gson.fromJson(message, User.class);
                ResultSet rsId = UtilsSQLite.querySelect(connDBUser, "SELECT id FROM user WHERE name='"+userData[1]+"';");
                if(rsId==null){
                    System.out.println("ERROR incorrect user");
                    this.broadcast("message::ERROR");
                }
                else{
                    ResultSet rsSalt=null;
                    ResultSet rsPepper=null;
                    try {
                        rsSalt = UtilsSQLite.querySelect(connDBSalt,"SELECT * FROM salt WHERE id= "+rsId.getInt("id")+";");
                        rsPepper = UtilsSQLite.querySelect(connDBPepper,"SELECT * FROM pepper WHERE id= "+rsId.getInt("id")+";"); 
                    } catch (SQLException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    String hashComp="";
                    try {
                        hashComp = Password.hash(userData[2]).addSalt(rsSalt.getString("saltString")).addPepper(rsPepper.getString("pepperString")).withArgon2().getResult();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    ResultSet rs = UtilsSQLite.querySelect(connDBUser, "SELECT * FROM user WHERE name='"+userData[1]+"' and hash='"+hashComp+"';");
                    try {
                        if(rs.getString("name")!=null){
                            System.out.println("OK correct user");
                            System.out.println("User "+userData[1]+" Correct");
                            this.broadcast("message::OK");
                            
                        }
                        else{
                            System.out.println("ERROR incorrect user");
                            this.broadcast("message::ERROR");
                            //this.stop(1000);
                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } 
                }
            }
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
        }*/
        public void enviaCanvi(String canvi){
            this.broadcast(canvi);
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
        public static byte[] arrayToBytes (ArrayList lista) {
            byte[] result = null;
            try {
                // Transforma l'objecte a bytes[]
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(lista);
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