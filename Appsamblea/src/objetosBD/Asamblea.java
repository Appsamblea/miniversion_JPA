package objetosBD;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="todos",
			query="SELECT a FROM Asamblea a")
})
public class Asamblea {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long id;
	  private String nombre;
	  private String descripcion;
	  private String creador;
	  private Date fecha;
	  
	 @OneToMany(cascade = CascadeType.ALL, mappedBy="id", fetch = FetchType.LAZY)
	 private List<Mensaje> mensajes;
	  
	 public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	  public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public void añadirMensaje(Mensaje mensaje){
		mensaje.setAsamblea(this);
		this.mensajes.add(mensaje);
	}
}
