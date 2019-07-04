package Class;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Usuario {
    private String username;
    private String password;
    private Date dtCriacao;
    
    public Usuario() {}    
    
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.dtCriacao = new Date();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
       this.password = password;        
    }
   
    public Date getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    } 
    
}
