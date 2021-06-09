package cl.bci.ulils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cl.bci.exception.ApiException;

public class Validaciones {

	public static Boolean validarCorreo(String correo) throws ApiException {
		Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mat = pat.matcher(correo);
		if(!mat.find()){
			throw new ApiException("Formato del email no es válido");
		}
		return true;
	}

	public static Boolean validarPassword(String password) throws ApiException {
		Pattern pat = Pattern.compile("^[A-Z]{1}[a-z]{2,}[0-9]{2}${4,16}");
		Matcher mat = pat.matcher(password);
		if(!mat.find()){
			throw new ApiException("Formato del password no es válido");
		}
		return true;
	}
}
