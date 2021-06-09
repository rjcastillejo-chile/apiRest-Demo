package cl.bci.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import cl.bci.dao.Response;
import cl.bci.dao.UsuarioRequest;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	private Response retorno;
	
    public Response getRetorno() {
		return retorno;
	}

	public void setRetorno(Response retorno) {
		this.retorno = retorno;
	}

	public LoginFilter(String url, AuthenticationManager authManager,Response retorno) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.retorno = retorno;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        // obtenemos el body de la peticion que asumimos viene en formato JSON
        InputStream body = req.getInputStream();

        // Asumimos que el body tendrá el siguiente JSON  {"username":"user", "password":"123"}
        // Realizamos un mapeo a nuestra clase User para tener ahi los datos
        UsuarioRequest user = new ObjectMapper().readValue(body, UsuarioRequest.class);

        // Finalmente autenticamos
        // Spring comparará el user/password recibidos
        // contra el que definimos en la clase SecurityConfig
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getName(),
                        user.getPassword(),
                        authorities
                )
        );
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    	logger.info("Autenticación Correcta" );
        // Si la autenticacion fue exitosa, agregamos el token a la respuesta
        JwtUtil.addAuthentication(res, auth.getName());

        retorno.setMensaje("Token: "+res.getHeaders("Authorization").toString().replace("[", "").replace("]", ""));
        
        Gson gson = new Gson();
        String employeeJsonString = gson.toJson(retorno);
     
        res.setContentType("application/json");
        try {
			res.getWriter().write(employeeJsonString);
		} catch (IOException e) {
			logger.error("Error generando Token : "+e.getMessage());
			e.printStackTrace();
		}        
    }
    
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    	logger.info("Autenticación Fallida" );
    	
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        retorno.setMensaje("Autenticación Fallida");
        
        
        Gson gson = new Gson();
        String employeeJsonString = gson.toJson(retorno);
     
        response.setContentType("application/json");
        try {
        	response.getWriter().write(employeeJsonString);
		} catch (IOException e) {
			logger.error("Autenticación Fallida: "+e.getMessage());
			e.printStackTrace();
		}        
    }
    
}