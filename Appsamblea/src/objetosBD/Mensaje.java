package objetosBD;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name="todosmensajes",
			query="SELECT m FROM Mensaje m"),
	@NamedQuery(name="actualizarmensajes",
			query="SELECT m FROM Mensaje m "
					+ "WHERE m.asamblea = :idAsamblea AND m.fecha > :fechaUltimo")
})
public class Mensaje implements Serializable{
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long id;
	  private String texto;
	  private String emisor;
	  
	  @Temporal(TemporalType.DATE)
	  private Date fecha;
	  
	  @ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name="idasamblea")	  
	  private Asamblea asamblea;
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Asamblea getAsamblea() {
		return asamblea;
	}
	public void setAsamblea(Asamblea asamblea) {
		this.asamblea = asamblea;
	}	  
}