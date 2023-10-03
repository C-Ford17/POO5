public class Clave {
    // la idea de esta clase es poder acceder al registro del usuario con el login o el token
    // ya que en el primer paso de verificacion se usa el login para comprobar el registro
    // y en el segundo paso se utilza el token que debio ser actualizado en el primer paso
    private String login;
    private String token;

    public Clave(String valor1, String valor2) {
        this.login = valor1;
        this.token = valor2;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void set(Clave clave){
        setToken(clave.getToken());
        setLogin(clave.getLogin());
    }
// notese que este metodo permite saber si contiene el token o el login a partir de cualquiera de los dos
    public boolean contains(String valor){
        return login.equals(valor) || token.equals(valor);
    }
}
