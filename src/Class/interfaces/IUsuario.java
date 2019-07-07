package Class.interfaces;

import java.util.Date;

public interface IUsuario {
    
    
    public void cadastrar();
    public void remover();
    public void editar();
    
    public void criarDiretorio();
    
    // Getters and setters
    public void setName(String name);
    public void setPass(String pass);
    public void setDate();
    
    public String getName();
    public String getPass();
    public Date getDate();
    
    
}
