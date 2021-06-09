package cl.bci.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.bci.dao.Credenciales;
import cl.bci.dao.Response;
import cl.bci.dao.Usuario;
import cl.bci.dao.UsuarioRequest;
import cl.bci.dao.UsuarioResponse;
import cl.bci.exception.ApiException;
import cl.bci.service.UsuarioService;
import cl.bci.ulils.Constants;
import cl.bci.ulils.GeneraToken;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping({"/api"})
@Api(value = "RestController" , tags = {"Todas las Operaciones"})
public class DemoController {
	protected final static Logger logger = LogManager.getLogger("DemoController");

	@Autowired
	UsuarioService usuarioServiceImpl;

	@PostMapping(path={"/dev/createUsuario"}, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Crear Usuario", response = Response.class, tags = "Operaciones con Usuario")
	public ResponseEntity<Response> createUsuario(@ApiParam(name="Usuario" ,value = "Datos del Usuario", required = true)  @RequestBody UsuarioRequest usuarioReq) {
		logger.info("Consulta endPoint Post /dev/createUsuario" );
		Response response= new Response();
		try {

			Usuario usuario= new Usuario();
			usuario.setUsername(usuarioReq.getName());
			usuario.setEmail(usuarioReq.getEmail());
			usuario.setPassword(usuarioReq.getPassword());
			usuario.setPhones(usuarioReq.getPhones());
			usuarioServiceImpl.createUsuario(usuario);
			UsuarioResponse usuarioResponse= new UsuarioResponse();
			usuarioResponse.setId(usuario.getId());
			usuarioResponse.setCreated(usuario.getCreated());
			usuarioResponse.setIsactive(usuario.getIsactive());
			usuarioResponse.setLast_login(usuario.getLast_login());
			usuarioResponse.setModified(usuario.getModified());
			usuarioResponse.setToken(GeneraToken.getToken(usuario.getUsername()));
			response.setData(usuarioResponse);
			response.setMensaje("Usuario creado");
			return ResponseEntity.ok(response);
		}catch (ApiException e) {
			response.setMensaje(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		} 
		catch (Exception e) {
			logger.error("Error de tipo Exception "+e.getMessage() );
			response.setMensaje("Error creando usuario");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping(path={"/test/login"}, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Obtener Token", response = Response.class, tags = "Obtener Token")
	public @ResponseBody ResponseEntity<Response>  setLogin(@ApiParam(name="Credenciales" ,value = "username y password", required = true) 
	@RequestBody Credenciales credenciales){

		return null;

	}
	
	@GetMapping(path={"/test/token"})
	@ApiOperation(value = "Validar Token", response = Response.class, tags = "Validar Token")
	public @ResponseBody ResponseEntity<Response>  getToken(HttpServletRequest request){
		Response response= new Response();
		String token = request.getHeader("Authorization");
        String user="";
        if (token != null) {
        		user = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace("Bearer ", "")) 
                    .getBody()
                    .getSubject();
        }
		response.setMensaje("Token Válido Usuario: "+user);
		return ResponseEntity.ok(response);

	}
}
