import java.util.*;

public abstract class Verificador {
    private Map<String,Usuario> usuarios;
    private final String peticionDesafio;
    private Map<String,String> desafios;

    public Verificador(String peticionDesafio) {
        this.usuarios = new HashMap<>();
        this.desafios = new HashMap<>();
        this.peticionDesafio = peticionDesafio;
    }

    public LinkedList<Usuario> getUsuarios() {
        return new LinkedList<>(this.usuarios.values());
    }

    public String getPeticionDesafio() {
        return peticionDesafio;
    }

    public Map<String, String> getDesafios() {
        return desafios;
    }

    public void agregarUsuarios(Usuario... usuarios){
        for (Usuario usuario:usuarios) {
            this.usuarios.put(usuario.getLogin(),usuario);
        }
    }

    public boolean borrarUsuario(Usuario usuario){
        return this.usuarios.remove(usuario.getLogin()) != null;
    }

    public String primerPasoVerificacion(String login, String password){
        if (this.usuarios.containsKey(login) && this.usuarios.get(login).validar(password)){
            String token = UUID.randomUUID().toString();
            String respuestaDesafio = generarRespuestaDesafio(this.usuarios.get(login));
            desafios.put(token,respuestaDesafio);
            return token;
        }
        return null;
    }

    public boolean segundoPasoVerificacion(String token, String respuestaDesafio){
        if (!this.desafios.containsKey(token)) return false;
        if (!this.desafios.get(token).equals(respuestaDesafio)){
            this.desafios.remove(token);
            return false;
        }
        this.desafios.remove(token);
        return true;
    }

    public abstract String generarRespuestaDesafio(Usuario usuario);

}
