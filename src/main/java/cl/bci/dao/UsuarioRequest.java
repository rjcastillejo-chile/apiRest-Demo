package cl.bci.dao;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Model Usuario")
public class UsuarioRequest implements Serializable{

	private static final long serialVersionUID = 4480560979625845621L;
	
	@SerializedName("name")
	@Expose
	@ApiModelProperty(value = "Nombre del usuario", required = true)
	private String name;
	@SerializedName("email")
	@Expose
	@ApiModelProperty(value = "email del usuario, Ejemplo de formato válido: aaaaaaa@dominio.cl", required = true)
	private String email;
	@SerializedName("password")
	@Expose
	@ApiModelProperty(value = "password del usuario, Formato requerido Una Mayúscula, letras minúsculas, y dos numeros", required = true)
	private String password;
	@SerializedName("phones")
	@Expose
	@ApiModelProperty(value = "phones del usuario", required = true)
	private List<Phone> phones;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "UsuarioRequest [name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioRequest other = (UsuarioRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
}