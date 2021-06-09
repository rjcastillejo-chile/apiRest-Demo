package cl.bci.dao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {


	private static final long serialVersionUID = -3006549371671382248L;

	private String mensaje;
	private Object data;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Response [mensaje=" + mensaje + ", data=" + data + "]";
	}
	
	
}
