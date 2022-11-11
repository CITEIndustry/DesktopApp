import java.io.Serializable;

public class User implements Serializable{
    private String nom;
    private String contrasenya;

    public User(){

    }
    public User(String nom,String contrasenya){
        this.nom=nom;
        this.contrasenya=contrasenya;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public String getNom(){
        return this.nom;
    }
    public void setContra(String contra){
        this.nom=contra;
    }
    public String getContra(){
        return this.contrasenya;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Nom: "+nom+"\nContrasenya"+this.contrasenya;
    }
}
