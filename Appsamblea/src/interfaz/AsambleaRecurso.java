package interfaz;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.ws.rs.core.GenericEntity;		//Para enviar listas

import bd.BD;
import objetosXML.Asamblea;
import objetosXML.Mensaje;

//Esta clase solo devuelve una instancia de la clase Todo

@Path("/asambleas")
public class AsambleaRecurso { 
  @GET
  @Path("/todasasambleas")
  @Produces(MediaType.APPLICATION_JSON)
  public Response todasAsambleas(){
	  BD bd = new BD();
	  ArrayList<objetosBD.Asamblea> listaBD = bd.getAsambleas();
	  
	  ArrayList<Asamblea> lista = new ArrayList<Asamblea>();
	  for(objetosBD.Asamblea as : listaBD){
		  Asamblea asa = new Asamblea();
		  asa.setCreador(as.getCreador());
		  asa.setDescripcion(as.getDescripcion());
		  asa.setFecha(as.getFecha());
		  asa.setId(as.getId());
		  
		  ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
		  for(objetosBD.Mensaje mensaje: as.getMensajes()){
			  Mensaje men = new Mensaje();
			  men.setEmisor(mensaje.getEmisor());
			  men.setFecha(mensaje.getFecha());
			  men.setId(mensaje.getId());
			  men.setIdAsamblea(mensaje.getAsamblea().getId());
			  men.setTexto(mensaje.getTexto());
			  listaMensajes.add(men);
		  }
		  asa.setMensajes(listaMensajes);
		  
		  asa.setNombre(as.getNombre());
		  lista.add(asa);
	  }
	  
	  //Ejemplo... Borrarlo para la versión final
	  	Asamblea as = new Asamblea();
	  	as.setCreador("YO");
	  	as.setDescripcion("Descripción");
	  	as.setId((long)0);
	  	as.setNombre("Nombre");
	  	lista.add(as);
	  //FIN DEL EJEMPLO
	  	
	  //Con ArrayList peta
	  //GenericEntity<ArrayList<Asamblea>> glista = new GenericEntity<ArrayList<Asamblea>>(lista){};
	  	
	  //Con List también
	  List<Asamblea> l = lista;
	  GenericEntity<List<Asamblea>> glista = new GenericEntity<List<Asamblea>>(l){};
	  //Fuente para usar GenericType: http://www.adam-bien.com/roller/abien/entry/jax_rs_returning_a_list
	  
	  //return Response.status(200).entity(lista).build();
	  return Response.ok(glista).build();
  } 
  
//Recibe una asamblea vía JSON	  
  @PUT
  @Path("/crearasamblea")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response crearAsamblea(JAXBElement<Asamblea> asamblea){
	  Asamblea a = asamblea.getValue();
	  System.out.println("Asamblea recibida: " + a.getNombre());
	  BD bd = new BD();
	  bd.insertarAsamblea(a);
	  return Response.noContent().build();
  }

  
  @PUT
  @Path("/eliminarasamblea")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response eliminarAsamblea(JAXBElement<Asamblea> asamblea){
	  Asamblea a = asamblea.getValue();
	  System.out.println("Asamblea recibida: " + a.getNombre());
	  BD bd = new BD();
	  bd.eliminarAsamblea(a);
	  return Response.noContent().build();
  }
} 
