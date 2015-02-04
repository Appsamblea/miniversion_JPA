package objetosXML;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "mensaje")
public class Mensaje {
	private long id;
	private String texto;
	private String emisor;
	private Date fecha;
	private long idAsamblea;
	
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

	public long getIdAsamblea() {
		return idAsamblea;
	}

	public void setIdAsamblea(long idAsamblea) {
		this.idAsamblea = idAsamblea;
	}
	

}
