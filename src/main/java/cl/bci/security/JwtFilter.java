package cl.bci.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.gson.Gson;

import cl.bci.dao.Response;
import io.jsonwebtoken.JwtException;

public class JwtFilter extends OncePerRequestFilter  {
	
	protected final static Logger logger = LogManager.getLogger("JwtFilter");
	private Response retorno;

    public Response getRetorno() {
		return retorno;
	}

	public void setRetorno(Response retorno) {
		this.retorno = retorno;
	}
	
	public JwtFilter(Response retorno) {
		this.retorno = retorno;
	}

	@Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
    		throws ServletException, IOException {
    	
    	logger.info("Verificando Token");
    	try {
	        Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        chain.doFilter(request,response);
    	}
        catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        retorno.setMensaje("Error Token no válido ");       
	        Gson gson = new Gson();
	        String employeeJsonString = gson.toJson(retorno);     
	        response.setContentType("application/json");	
        	response.getWriter().write(employeeJsonString);
			return;
		}

    }
}
