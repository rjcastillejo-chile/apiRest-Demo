package cl.bci;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import cl.bci.dao.Phone;
import cl.bci.dao.Usuario;
import cl.bci.exception.ApiException;
import cl.bci.service.UsuarioService;
import cl.bci.ulils.Validaciones;


@SpringBootTest
class UsuarioServiceTest {

	@Autowired
	UsuarioService usuarioServiceImpl;

	public Usuario usuario;
	public Phone phone;
	

	@BeforeEach
	public void setup() {
		this.phone = new Phone();
		this.phone.setCitycode("9");
		this.phone.setContrycode("56");
		this.phone.setNumber("6584952");
		List<Phone> listPhone= new ArrayList<>();
		listPhone.add(phone);

		this.usuario= new Usuario();
		this.usuario.setEmail("patino2@gmail.com");
		this.usuario.setUsername("Jose");
		this.usuario.setPassword("Mdddhs12");
		this.usuario.setPhones(listPhone);
	}


@Test
@Rollback(true)
void createUsuario() {

	try {
		Usuario usuarioSearch=usuarioServiceImpl.createUsuario(this.usuario);

		assertEquals(usuarioSearch.getUsername(), usuario.getUsername());
		assertEquals(usuarioSearch.getEmail(), usuario.getEmail());

	} catch (Exception e) {
		e.getMessage();
	}


}

@Test
void validarCorreo() {

	try {
		assertEquals(Validaciones.validarCorreo(usuario.getEmail()),true);
	} catch (ApiException e) {
		System.out.println(e.getMessage());
	}

}

@Test
void validarPassword() {

	try {
		assertEquals(Validaciones.validarPassword(usuario.getPassword()),true);
	} catch (ApiException e) {
		System.out.println(e.getMessage());
	}

}

}
