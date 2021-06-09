package cl.bci.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.bci.dao.Response;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Response retorno=new Response();
		http.csrf().disable().authorizeRequests()
		.antMatchers("/api/dev/**").permitAll() //permitimos el acceso a /login a cualquiera
		.antMatchers("/swagger-ui/**").permitAll() //permitimos el acceso a la documentación Swagger
		.antMatchers("/h2-console/**").permitAll() //permitimos el acceso a la consola h2 bd
		.anyRequest().authenticated()
		//cualquier otra peticion requiere autenticacion
		.and().antMatcher("/api/test/**")
		// Las peticiones /login pasaran previamente por este filtro
		.addFilterBefore(new LoginFilter("/api/test/login", authenticationManager(),retorno),
				UsernamePasswordAuthenticationFilter.class)

		// Las demás peticiones pasarán por este filtro para validar el token
		.addFilterBefore(new JwtFilter(retorno),
				UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui/**",
				"/h2-console/**",
				"/webjars/**");
	}
}