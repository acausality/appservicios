package api.grupo.appservicios.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClienteDTO {

	@NotNull
	@Min(0)
	private long id;

	@NotNull
	@Size(min = 3, max = 50)
	private String nombre;

	@NotNull
	@Size(min = 3, max = 50)
	private String apellido;

	@NotNull
	@Size(max = 50)
	private String tipoDocumento;

	@NotNull
	@Size(max = 50)
	private String numeroDocumento;

	public ClienteDTO() {
	}

	public ClienteDTO(@NotNull @Min(0) long id, @NotNull @Size(min = 3, max = 50) String nombre,
			@NotNull @Size(min = 3, max = 50) String apellido, @NotNull @Size(max = 50) String tipoDocumento,
			@NotNull @Size(max = 50) String numeroDocumento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
