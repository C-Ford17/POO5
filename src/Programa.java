import java.util.LinkedList;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        Usuario usuario1 = new Usuario("fperez","lechemerengada");
        usuario1.modificarPassword("lechemerengada","cr7comeback");
        Usuario usuario2 = new Usuario("mlama","tururu");
        Verificador verificadorblacklist = new VerificadorBlacklist();
        Verificador verificadorporcodigo = new VerificadorCodigo(5);
        verificadorblacklist.agregarUsuarios(usuario1,usuario2);
        verificadorporcodigo.agregarUsuarios(usuario1,usuario2);
        LinkedList<Verificador> verificadores = new LinkedList<>();
        verificadores.add(verificadorblacklist);
        verificadores.add(verificadorporcodigo);
        for (Verificador verificador:verificadores) {
            if (verificador instanceof VerificadorBlacklist) ((VerificadorBlacklist) verificador).bloquearUsuario(usuario2);
        }
        for (Verificador verificador: verificadores) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce el login: ");
            String login = scanner.nextLine();
            System.out.println("Introduce el password: ");
            String password = scanner.nextLine();
            String token;
            if (verificador instanceof  VerificadorCodigo) token = ((VerificadorCodigo)verificador).primerPasoVerificacion(login,password);
            else token = verificador.primerPasoVerificacion(login, password);
            System.out.println(verificador.getPeticionDesafio());
            if (token != null) {
                System.out.println("Introduce la respuesta del desafio: ");
                String respuesta = scanner.nextLine();
                if (verificador instanceof VerificadorCodigo){
                    System.out.println(((VerificadorCodigo) verificador).segundoPasoVerificacion(token, respuesta));
                    System.out.println(((VerificadorCodigo) verificador).getIntentosRestantes(login)); //intentos restantes
                }
                else System.out.println(verificador.segundoPasoVerificacion(token,respuesta));
            } else System.out.println("credenciales incorrectas o usuario bloqueado");
        }
        //codigo opcional para verificar que el limite de intentos funciona
        /*VerificadorCodigo verificadorporcodigo1 = (VerificadorCodigo) verificadorporcodigo;
        for (int i = 0; i < 10; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce el login: ");
            String login = scanner.nextLine();
            System.out.println("Introduce el password: ");
            String password = scanner.nextLine();
            String token = verificadorporcodigo1.primerPasoVerificacion(login, password);
            System.out.println(verificadorporcodigo1.getPeticionDesafio());
            if (token != null) {
                    System.out.println("Introduce la respuesta del desafio: ");
                    String respuesta = scanner.nextLine();
                    System.out.println(verificadorporcodigo1.segundoPasoVerificacion(token, respuesta));
                    System.out.println(verificadorporcodigo1.getIntentosRestantes(login));
            }else System.out.println("credenciales incorrectas o usuario bloqueado");
        }*/
    }
}
