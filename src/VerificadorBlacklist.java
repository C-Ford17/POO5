import java.util.ArrayList;

public class VerificadorBlacklist extends Verificador{
    ArrayList<String> usuariosBloqueados;

    public VerificadorBlacklist() {
        super("Introduzca el numero del mes de su ultimo acceso");
        this.usuariosBloqueados = new ArrayList<>();
    }

    public boolean bloquearUsuario(Usuario usuario){
        return usuariosBloqueados.add(usuario.getLogin());
    }

    public boolean desbloquearUsuario(Usuario usuario){
        return usuariosBloqueados.remove(usuario.getLogin());
    }

    @Override
    public String primerPasoVerificacion(String login, String password) {
        if (this.usuariosBloqueados.contains(login)) return null;
        return super.primerPasoVerificacion(login, password);
    }

    @Override
    public String generarRespuestaDesafio(Usuario usuario) {
        return Integer.toString(usuario.getFechaUltimaAcceso().getMonthValue());
    }
}
