import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import objetosXML.Asamblea;
import objetosXML.Mensaje;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EnvioJSON {

	private static Client cliente = ClientBuilder.newClient();
	private static Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy")
			.create();

	public static void sendJson(String urli, Object obj) {
		try {
			String json = gson.toJson(obj);
			URL url = new URL(urli);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			OutputStreamWriter osw = new OutputStreamWriter(
					connection.getOutputStream());
			osw.write(json);
			osw.flush();
			osw.close();
			System.err.println(connection.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getObj(String url) {
		try {
			return new JSONObject(IOUtils.toString(new URL(url),
					Charset.forName("UTF-8")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray getArray(String url) {
		try {
			return new JSONArray(IOUtils.toString(new URL(url),
					Charset.forName("UTF-8")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Asamblea> verProximasAsambleas() {
		JSONArray asam = getArray("http://localhost:8080/Appsamblea/asambleas/todasasambleas");
		List<Asamblea> asambleas = (List<Asamblea>) gson.fromJson(
				asam.toString(), new TypeToken<List<Asamblea>>() {
				}.getType());
		return asambleas;
	}

	public static void convocarAsamblea(Asamblea asam) {
		sendJson("http://localhost:8080/Appsamblea/asambleas/crearasamblea", asam);

	}

	public static void eliminarAsamblea(Asamblea asam) {
		sendJson("http://localhost:8080/Appsamblea/asambleas/eliminarasamblea", asam);

	}

	public static List<Mensaje> verMensajes(Asamblea asamblea) {
		return asamblea.getMensajes();
	}

	public static void escribirMensaje(Mensaje m) {
		sendJson("http://localhost:8080/Appsamblea/mensajes/crearmensaje", m);

	}

	/*
	 * private static void escribirMensaje(Client client) { WebTarget servicio =
	 * client.target(UriBuilder.fromUri(
	 * "http://localhost:8080/mensajes").build()); // .... // Recibir lista de
	 * asambleas // Determinar el chat de qué asamblea se va a ver // .... //
	 * Introducir mensaje y meterlo en la variable mensaje long idAsamblea = 1;
	 * String mensaje = "Mensaje de prueba"; // Se envía el mensaje try {
	 * JSONObject json = new JSONObject(); json.put("Usuario", usuario);
	 * json.put("Mensaje", mensaje); json.put("idAsamblea", idAsamblea); try {
	 * // ... Enviar al servicio REST URL url = new URL(
	 * "http://localhost:8080/Appsamblea/mensajes/crearmensaje"); URLConnection
	 * conexion = url.openConnection(); conexion.setDoOutput(true);
	 * conexion.setDoOutput(true); conexion.setRequestProperty("Content-Type",
	 * "application/json"); conexion.setConnectTimeout(5000);
	 * conexion.setReadTimeout(5000);
	 * 
	 * OutputStreamWriter out = new OutputStreamWriter(
	 * conexion.getOutputStream()); out.write(json.toString()); out.close(); //
	 * Se reciben los datos que envíe el servidor (Sólo a modo de // prueba)
	 * BufferedReader in = new BufferedReader(new InputStreamReader(
	 * conexion.getInputStream())); System.out.println("Respuesta: " +
	 * in.readLine()); // } catch (Exception e) { System.out
	 * .println("Ha ocurrido una excepción al enviar el objeto JSON");
	 * System.out.println("Mensaje: " + e.getMessage()); System.exit(0); }
	 * 
	 * } catch (Exception e) {
	 * System.out.println("Ha ocurrido una excepción con el objeto JSON:");
	 * System.out.println("Mensaje: " + e.getMessage()); System.exit(0); } }
	 */
}
