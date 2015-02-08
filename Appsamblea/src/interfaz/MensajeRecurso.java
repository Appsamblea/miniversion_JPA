package interfaz;


import java.util.ArrayList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import objetosXML.Mensaje;
import bd.BD;

@Path("/mensajes")
public class MensajeRecurso {
	  
//Recibe un mensaje vía JSON	  
	  @POST
	  @Path("/crearmensaje")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response crearMensaje(Mensaje m){
		  //Insterarlo en la base de datos
		  BD bd = new BD();
		  bd.insertarMensaje(m);
		  return Response.noContent().build();
	  }
	  
	  @POST
	  @Path("/todosmensajes")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response todosMensajes(Mensaje m){
		  BD bd = new BD();
		  ArrayList<objetosBD.Mensaje> listaBD = bd.actualizarMensajes(m.getIdAsamblea(), m.getFecha());
		  ArrayList<Mensaje> lista = new ArrayList<Mensaje>();
		  for(objetosBD.Mensaje me : listaBD){
			  Mensaje men = new Mensaje();
			  men.setIdAsamblea(me.getAsamblea().getId());
			  men.setEmisor(me.getEmisor());
			  men.setFecha(me.getFecha());
			  men.setId(men.getId());
			  men.setTexto(men.getTexto());
			  lista.add(men);
		  }
		  return Response.ok(lista).build();
	  }
}
