import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe Input que lê valores (sejam eles Strings, inteiros, entre outros) introduzidos pelo utilizador
 */
public class Input {

 /**
  * Método que lê uma String inserida pelo utilizador
  */
 public static String lerString() {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     String txt = "";
     while(!ok) {
         try {
             txt = input.nextLine();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println("Texto Invalido"); 
               out.print("Novo valor: ");
               input.nextLine(); 
             }
     }
     input.close();
     return txt;
  } 

 /**
  * Método que lê um número inteiro inserido pelo utilizador
  */
 public static int lerInt() {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     int i = 0; 
     while(!ok) {
         try {
             i = input.nextInt();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println("Inteiro Invalido"); 
               out.print("Novo valor: ");
               input.nextLine(); 
             }
     }
     input.close();
     return i;
  } 
}
