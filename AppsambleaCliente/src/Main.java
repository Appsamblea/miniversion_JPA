import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

public class Main {
	private static String usuario;
	
	private static void convocarAsamblea(Client client){
		//...
	}
	
	private static void verAsambleas(Client client){
		   WebTarget servicio = client.target(UriBuilder.fromUri("http://localhost:8080/asambleas").build());

		    // Conseguir XML
			/*System.out.println("TEXT_XML:");
		    System.out.println(servicio.path("rest").path("asambleas").request(MediaType.TEXT_XML).get(String.class));*/

		    // Conseguir XML para la aplicacion
		    System.out.println("APPLICATION_XML:");
		    System.out.println(servicio.path("rest").path("asambleas").request(MediaType.APPLICATION_XML).get(String.class));
	}
	
	private static void escribirMensaje(Client client){
		WebTarget servicio = client.target(UriBuilder.fromUri("http://localhost:8080/mensajes").build());
		//....
		//Recibir lista de asambleas
		//Determinar el chat de qué asamblea se va a ver
		//....
		//Introducir mensaje y meterlo en la variable mensaje
		long idAsamblea = 1;
		String mensaje = "Mensaje de prueba";
		//Se envía el mensaje
		try{
			JSONObject json = new JSONObject();
			json.put("Usuario", usuario);
			json.put("Mensaje", mensaje);
			json.put("idAsamblea", idAsamblea);
			try{
				//... Enviar al servicio REST
				URL url = new URL("http://localhost:8080/Appsamblea/mensajes/crearmensaje");
				URLConnection conexion = url.openConnection();
				conexion.setDoOutput(true);
				conexion.setDoOutput(true);
				conexion.setRequestProperty("Content-Type", "application/json");
				conexion.setConnectTimeout(5000);
				conexion.setReadTimeout(5000);
				
				OutputStreamWriter out = new OutputStreamWriter(conexion.getOutputStream());
                out.write(json.toString());
                out.close();
                //Se reciben los datos que envíe el servidor (Sólo a modo de prueba)
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream() ));
                System.out.println("Respuesta: " + in.readLine());
                //
			}catch(Exception e){
				System.out.println("Ha ocurrido una excepción al enviar el objeto JSON");
				System.out.println("Mensaje: " + e.getMessage());
				System.exit(0);
			}
			
		}catch(Exception e){
			System.out.println("Ha ocurrido una excepción con el objeto JSON:");
			System.out.println("Mensaje: " + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void verMensajes(Client client){
		   WebTarget servicio = client.target(UriBuilder.fromUri("http://localhost:8080/mensajes").build());

		    // Conseguir XML
			/*System.out.println("TEXT_XML:");
		    System.out.println(servicio.path("rest").path("mensajes").request(MediaType.TEXT_XML).get(String.class));*/

		    // Conseguir XML para la aplicacion
		    System.out.println("APPLICATION_XML:");
		    System.out.println(servicio.path("rest").path("mensajes").request(MediaType.APPLICATION_XML).get(String.class));
	}	
	
	public static void main(String[] args){
		   Client cliente = ClientBuilder.newClient();
		   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   String entrada = "";

		   System.out.println("Hola, introduzca un nombre de usuario:");
		   
		   try{
			  entrada = br.readLine(); 
		   }catch(Exception e){
			   System.out.println("Error al leer datos.");
			   System.exit(-1);
		   }
		   usuario = entrada;
		   
		   do{ 
			   System.out.println(usuario +", ¿Qué desea hacer?\n1: Ver las próximas asambleas.\n"+
			   "2: Convocar una nueva asamblea." +
				"\n3: Ver la lista de chats.\n4: Crear un nuevo mensaje.\n5: Salir.");
			   br = new BufferedReader(new InputStreamReader(System.in)); 
			  entrada = "";
			   try{
				  entrada = br.readLine(); 
			   }catch(Exception e){
				   System.out.println("Error al leer datos.");
				   System.exit(-1);
			   }
			   
			   switch(Integer.parseInt(entrada)){
			   		case 1: 
			   				verAsambleas(cliente);
			   				break;
			   		case 2:
			   				convocarAsamblea(cliente);
			   				break;
			   		case 3: 
			   				verMensajes(cliente);
			   				break;
			   		case 4:
			   				escribirMensaje(cliente);
			   				break;
			   		case 5:
			   				System.exit(0);
			   				break;
			   		default:
			   				System.out.println("Entrada no válida.");
			   				break;
			   }
		   }while(true);
	}
}
