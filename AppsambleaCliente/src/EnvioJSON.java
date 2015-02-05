import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import objetosXML.*;

public class EnvioJSON {
	
	private static Client cliente = ClientBuilder.newClient();
	private static WebTarget servicio = cliente.target(UriBuilder.fromUri("http://localhost:8080/mensajes").build());
	
	public static List<Asamblea> verProximasAsambleas(){
	    //Enviamos asamleas de prueba...
		Asamblea a1 = new Asamblea() ,a2 = new Asamblea();
		ArrayList<Asamblea> asambleas = new ArrayList<Asamblea>();
		
		a1.setId(1);
		a1.setNombre("Asamblea 1");
		a1.setDescripcion("Primera asamblea de prueba");
		a1.setFecha(new Date());
		a1.setCreador("Usuario1");

		a2.setId(2);

		a2.setNombre("Asamblea 2");
		a2.setDescripcion("Segunda asamblea de prueba");
		a2.setFecha(new Date());
		a2.setCreador("Usuario2");
		
		asambleas.add(a1);
		asambleas.add(a2);
		return asambleas;
	}
	
	public static void convocarAsamblea(Asamblea asam){
		
	}
	
	public static void eliminarAsamblea(Asamblea asam){
		
		
	}

	public static List<Mensaje> verMensajes(Asamblea asamblea) {
		//Mensajes de prueba...
		Mensaje m1 = new Mensaje(), m2 = new Mensaje();
		m1.setTexto("Prueba 1");
		m1.setEmisor("Usuario1");
		m1.setFecha(new Date());
		
		m2.setTexto("Otra Prueba");
		m2.setEmisor("Usuario2");
		m2.setFecha(new Date());
		
		ArrayList<Mensaje> ms = new ArrayList<Mensaje>();
		
		ms.add(m1); ms.add(m2);
		
		return ms;
	}

	public static void escribirMensaje(Asamblea asamblea, Mensaje m) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * private static void escribirMensaje(Client client) {
		WebTarget servicio = client.target(UriBuilder.fromUri(
				"http://localhost:8080/mensajes").build());
		// ....
		// Recibir lista de asambleas
		// Determinar el chat de qué asamblea se va a ver
		// ....
		// Introducir mensaje y meterlo en la variable mensaje
		long idAsamblea = 1;
		String mensaje = "Mensaje de prueba";
		// Se envía el mensaje
		try {
			JSONObject json = new JSONObject();
			json.put("Usuario", usuario);
			json.put("Mensaje", mensaje);
			json.put("idAsamblea", idAsamblea);
			try {
				// ... Enviar al servicio REST
				URL url = new URL(
						"http://localhost:8080/Appsamblea/mensajes/crearmensaje");
				URLConnection conexion = url.openConnection();
				conexion.setDoOutput(true);
				conexion.setDoOutput(true);
				conexion.setRequestProperty("Content-Type", "application/json");
				conexion.setConnectTimeout(5000);
				conexion.setReadTimeout(5000);

				OutputStreamWriter out = new OutputStreamWriter(
						conexion.getOutputStream());
				out.write(json.toString());
				out.close();
				// Se reciben los datos que envíe el servidor (Sólo a modo de
				// prueba)
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conexion.getInputStream()));
				System.out.println("Respuesta: " + in.readLine());
				//
			} catch (Exception e) {
				System.out
						.println("Ha ocurrido una excepción al enviar el objeto JSON");
				System.out.println("Mensaje: " + e.getMessage());
				System.exit(0);
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido una excepción con el objeto JSON:");
			System.out.println("Mensaje: " + e.getMessage());
			System.exit(0);
		}
	}
	 * */
}
