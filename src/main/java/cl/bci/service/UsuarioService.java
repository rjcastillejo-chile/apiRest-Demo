package cl.bci.service;

import java.util.List;

import cl.bci.dao.Phone;
import cl.bci.dao.Response;
import cl.bci.dao.Usuario;
import cl.bci.exception.ApiException;

public interface UsuarioService {
	
	
	public Usuario createUsuario(Usuario usuario) throws ApiException;
	
	public Response updateUsuario(Usuario usuario);
	
	public Usuario searchUsuario(String id);
	
	public Response deleteUsuario(Usuario usuario);
	
	public List<Usuario> listarUsuarios();
	
	public int createPhone(Phone phone) throws Exception;
	
	public int updatePhone(Phone phone);
	
	public Phone searchPhone(int id);
	
	public void deletePhone(int id);

}
