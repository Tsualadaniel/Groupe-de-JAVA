
package gestion_professeur;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author NEC
 */
public class Connexion {
    Connection cn ;
    
   public Connexion(){
       
       try {
          Class.forName("com.mysql.cj.jdbc.Driver");

           cn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/gestion_enseignant","root", "");
           System.out.println("Connexion Etablie");
       } catch(Exception e){
           System.out.println("Erreur de connection");
           e.printStackTrace();
       }
      
   }
    public Connection maConnection(){
       return  cn ;
    }
   }

