package interfaces;

import com.mycompany.estudo.jdbc.Usuario;
import java.util.ArrayList;

public interface UsuarioDAOInterface {
    
    public abstract ArrayList<Usuario> listaUsuarios();
    public abstract boolean addUsuario(Usuario user);
    public abstract boolean deleteUser(int id);
    
}
