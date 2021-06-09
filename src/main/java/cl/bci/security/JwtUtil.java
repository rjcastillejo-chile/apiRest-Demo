package cl.bci.security;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import cl.bci.ulils.Constants;
import cl.bci.ulils.GeneraToken;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


public class JwtUtil {
	protected final static Logger logger = LogManager.getLogger("JwtUtil");

	
    // Método para crear el JWT y enviarlo al cliente en el header de la respuesta
    static void addAuthentication(HttpServletResponse res, String username) {
    	logger.info("Asignando Token" );
    	String token = GeneraToken.getToken(username);   

        //agregamos al encabezado el token
        res.addHeader("Authorization", token);

        
    }

    // Método para validar el token enviado por el cliente
    static Authentication getAuthentication(HttpServletRequest request) throws JwtException {
    	logger.info("Validando Token");
        // Obtenemos el token que viene en el encabezado de la peticion
        String token = request.getHeader("Authorization");
        String user;
        // si hay un token presente, entonces lo validamos
        if (token != null) {
        		user = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace("Bearer ", "")) //este metodo es el que valida
                    .getBody()
                    .getSubject();
        
        		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            
			// Recordamos que para las demás peticiones que no sean /login
            // no requerimos una autenticacion por username/password 
            // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password
        		return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null,authorities) :
                    null;
        }
		logger.error("Se requiere Token válido.");
		throw new JwtException("Se requiere Token válido");
    }


}