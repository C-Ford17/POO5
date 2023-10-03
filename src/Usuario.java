import java.time.LocalDate;
import java.util.HashSet;

public class Usuario {
    private String login;
    private String password;
    private LocalDate fechaUltimaAcceso;
    private HashSet<String> historialPasswords;

    public Usuario(String login, String password) {
        this.login = login;
        this.password = password;
        fechaUltimaAcceso = LocalDate.now();
        historialPasswords = new HashSet<>();
        historialPasswords.add(password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaUltimaAcceso() {
        return fechaUltimaAcceso;
    }

    public HashSet<String> getHistorialPasswords() {
        return historialPasswords;
    }

    public boolean modificarPassword(String passwordActual, String nuevoPassword){
        if (validar(passwordActual) && !historialPasswords.contains(nuevoPassword)){
            this.password = nuevoPassword;
            return historialPasswords.add(nuevoPassword);
        }
        return false;
    }

    public boolean validar(String password){
        return this.password.equals(password);
    }

    public void setFechaUltimaAcceso() {
        this.fechaUltimaAcceso = LocalDate.now();
    }
}
