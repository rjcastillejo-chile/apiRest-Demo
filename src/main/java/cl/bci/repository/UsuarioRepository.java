package cl.bci.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bci.dao.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	public List<Usuario> findByEmail(String email);
	public  List<Usuario> findById(String id);
	public Usuario findByUsername(String username);
}
