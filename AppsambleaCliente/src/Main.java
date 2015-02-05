import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import objetosXML.Asamblea;
import objetosXML.Mensaje;

import org.json.JSONObject;

public class Main {
	private static String usuario;
	private static BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	private static int leerDatoInt() {
		do {
			try {
				return Integer.parseInt(leerDato());
			} catch (Exception e) {
				System.out.println("Debe introducir dato numérico.");
			}
		} while (true);
	}
	
	private static Date leerDatoDate() {
		do {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.parse(leerDato());
			} catch (Exception e) {
				System.out.println("Debe introducir dato tipo Fecha (dd/mm/aaaa).");
			}
		} while (true);
	}

	private static String leerDato() {
		String dato = "";
		do {
			try {
				dato = br.readLine().trim();
			} catch (IOException e) {
			}
		} while (dato.isEmpty());
		return dato;
	}

	private static void convocarAsamblea() {
		Asamblea asam = new Asamblea();
		asam.setCreador(usuario);
		System.out.println("Nombre de la Asamblea: ");
		asam.setNombre(leerDato());
		System.out.println("Descripción de la Asamblea: ");
		asam.setDescripcion(leerDato());
		System.out.println("Fecha de la Asamblea (dd/mm/aaaa): ");
		asam.setFecha(leerDatoDate());
		EnvioJSON.convocarAsamblea(asam);
		System.out.println("Asamblea creada.");
	}

	private static void verAsambleas() {
		List<Asamblea> asambleas = EnvioJSON.verProximasAsambleas();
		int asambleaId = -1;
		do {
			for (Asamblea as : asambleas) {
				System.out.println(as.getId() + " - " + as.getNombre());
			}
			System.out.println("Introduzca ID de asamblea, 0 para salir.");
			asambleaId = leerDatoInt();
			Asamblea aElegida = null;
			for (Asamblea as : asambleas) {
				if (as.getId() == asambleaId) {
					aElegida = as;
				}
			}
			if (aElegida != null) {
				accionesAsamblea(aElegida);
			}
		} while (asambleaId != 0);
	}

	private static void accionesAsamblea(Asamblea aElegida) {
		do {
			System.out.println("Datos de la Asamblea: \n" + "ID: "
					+ aElegida.getId() + "\n" + "Nombre: "
					+ aElegida.getNombre() + "\n" + "Descripcion: "
					+ aElegida.getDescripcion() + "\n"
					+ "Fecha de la Asamblea: " + aElegida.getFecha() + "\n"
					+ "Creador: " + aElegida.getCreador()
					+ "\n\n¿Qué desea hacer?\n" + "1.- Ver Mensajes \n"
					+ "2.- Eliminar Asamblea \n" + "3.- Salir");
			int dato = leerDatoInt();
			switch (dato) {
			case 1:
				verMensajes(aElegida);
				return;
			case 2:
				EnvioJSON.eliminarAsamblea(aElegida);
			case 3:
				return;
			}
		} while (true);
	}


	private static void verMensajes(Asamblea asamblea) {
		do {
			List<Mensaje> mensajes = EnvioJSON.verMensajes(asamblea);
			System.out.println("Mensajes de la Asamblea: ");
			for (Mensaje m : mensajes) {
				System.out.println(m.getEmisor() + " Dijo (" + m.getFecha()
						+ "): " + m.getTexto());
			}
			System.out.println("\nEscriba un mensaje, o ESC para salir.");
			String msg = leerDato();
			if(msg.equals("ESC")){
				return;
			}
			Mensaje m = new Mensaje();
			m.setEmisor(usuario);
			m.setFecha(new Date());
			m.setTexto(msg);
			EnvioJSON.escribirMensaje(asamblea, m);
		} while (true);
	}

	public static void main(String[] args) {
		String entrada = "";

		System.out.println("Hola, introduzca un nombre de usuario:");

		usuario = leerDato();

		do {
			System.out.println(usuario
					+ ", ¿Qué desea hacer?\n1: Ver asambleas.\n"
					+ "2: Convocar una nueva asamblea." + "\n3: Salir.");
			entrada = "";
			try {
				entrada = leerDato();
			} catch (Exception e) {
				System.out.println("Error al leer datos.");
				System.exit(-1);
			}

			switch (Integer.parseInt(entrada)) {
			case 1:
				verAsambleas();
				break;
			case 2:
				convocarAsamblea();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Entrada no válida.");
				break;
			}
		} while (true);
	}
}
