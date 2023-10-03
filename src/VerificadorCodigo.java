import java.time.LocalDate;
import java.util.*;

public class VerificadorCodigo extends Verificador{
    private final int nIntentos;
    // se introduce una especia de historial de intentos de ingreso de usuario.
    // si un usuario le quedan 3 intentos de inicio y se ingresa otro usuario
    // y vuelve a ingresar el usuario anterior, seguiria con los 3 intentos
    // lo mismo aplica al otro usuario y cada uno tiene su historial
    private Map<Clave, Integer> intentosUsuarios;
    public VerificadorCodigo(int nIntentos) {
        super("Introduzca el numero que ha recibido por SMS");
        this.nIntentos = nIntentos;
        this.intentosUsuarios = new HashMap<>();
    }

    @Override
    public String generarRespuestaDesafio(Usuario usuario) {
        Random valorRandom = new Random();
        int valor = valorRandom.nextInt(1000);
        System.err.println(valor);
        return Integer.toString(valor);
    }


    @Override
    public String primerPasoVerificacion(String login, String password) {
        String tok = super.primerPasoVerificacion(login, password);
        if (tok != null){
            //se crea el registro de sesion
            Clave clave = new Clave(login,tok);
            boolean contiene = false;
            Clave key = obtenerClave(login);
            // si el usuario ya tiene un registro, entonces simplemente se remplazara la clave de ese registro
            // para que el segundo paso de verificacion pueda acceder a el simplemente con el token actualizado
            if (intentosUsuarios.containsKey(key)){
                intentosUsuarios.put(clave,intentosUsuarios.get(key));
                intentosUsuarios.remove(key);
            }
            // si no tiene un registro se crea uno nuevo con los intentos definidos en la creacion
            else intentosUsuarios.put(clave,nIntentos);
            return tok;
        }
        return null;
    }
// este metodo ayuda a reducir codigo ya que la obtencion de la clave se utiliza mucho
    public Clave obtenerClave(String valor){
        if (!intentosUsuarios.isEmpty()) {
            for (Clave clave : intentosUsuarios.keySet()) {
                if (clave.contains(valor)) return clave;
            }
        }
        return null;
    }

    @Override
    public boolean segundoPasoVerificacion(String token, String respuestaDesafio) {
        boolean paso = super.segundoPasoVerificacion(token, respuestaDesafio);
        Clave clave = obtenerClave(token);
        if (!paso){
            // si el usuario no paso la verificacion, se decrementaran sus intentos restantes en su registro
            intentosUsuarios.put(clave,intentosUsuarios.get(clave)-1);
            if (intentosUsuarios.get(clave) == 0){
                getDesafios().remove(token);
            }
            return paso;
        }
        // si el usuario logra pasar la verificacion, se le reiniciaran sus intentos restantes
        intentosUsuarios.put(clave,nIntentos);
        return paso;
    }

    public int getIntentosRestantes(String usuario) {
        return intentosUsuarios.get(obtenerClave(usuario));
    }
}
