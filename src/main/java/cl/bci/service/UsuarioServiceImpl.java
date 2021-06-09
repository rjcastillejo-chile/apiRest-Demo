package cl.bci.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.bci.dao.Phone;
import cl.bci.dao.Response;
import cl.bci.dao.Usuario;
import cl.bci.exception.ApiException;
import cl.bci.repository.PhoneRepository;
import cl.bci.repository.UsuarioRepository;
import cl.bci.ulils.Validaciones;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	protected final static Logger logger = LogManager.getLogger("UsuarioServiceImpl");
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PhoneRepository phoneRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public Usuario createUsuario(Usuario usuario) throws ApiException {
		logger.info("Metodo createUsuario" );
		try {
			logger.info("Iniciando Validaciones" );
			Validaciones.validarCorreo(usuario.getEmail());
			logger.info("email correcto" );
			Validaciones.validarPassword(usuario.getPassword());
			logger.info("password correcto" );
			if(usuarioRepository.findByEmail(usuario.getEmail()).size()>0) {
				logger.info("Ya existe un usuario con ese email" );
				throw new ApiException("Ya existe un usuario con ese email");
			}else {		
				logger.info("Creando Usuario" );
				usuario.setId(UUID.randomUUID().toString());
				usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
				Date date = new Date();
				usuario.setCreated(date);
				usuario.setModified(date);
				usuario.setLast_login(date);
				usuario.setIsactive(true);
				Usuario usuarioRes=usuarioRepository.save(usuario);
				logger.info("Creando Phones para el usuario creado" );
				for(Phone phone:usuario.getPhones() ) {
					phone.setUsuario(usuarioRes);
					phoneRepository.save(phone);
				}	
				return usuarioRes;		
			}	
			
		}catch(ApiException e){
			logger.error("Error de tipo ApiException "+e.getMessage() );
			throw e;
		}catch(Exception e){
			logger.error("Error de tipo Exception "+e.getMessage() );
			throw new ApiException("Error creando usuario");
		}
		

	}

	@Override
	public Response updateUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario searchUsuario(String id) {
		List<Usuario> listUsuario = usuarioRepository.findById(id);
		if(listUsuario.size()>0) {
			return listUsuario.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Response deleteUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPhone(Phone phone) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePhone(Phone phone) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Phone searchPhone(int id) {
		Optional<Phone> phone= phoneRepository.findById(id);
		if(phone.isPresent()) {
			return phone.get();
		}else {
			return null;
		}
	}

	@Override
	public void deletePhone(int id) {
		// TODO Auto-generated method stub
	}

}
