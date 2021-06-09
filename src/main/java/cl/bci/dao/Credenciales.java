package cl.bci.dao;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;


public class Credenciales implements Serializable{

	private static final long serialVersionUID = 4480560979625845621L;
	
	@SerializedName("name")
	@Expose
	@ApiModelProperty(value = "name del usuario", required = true)
	private String name;
	@SerializedName("password")
	@Expose
	@ApiModelProperty(value = "password del usuario", required = true)
	private String password;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}