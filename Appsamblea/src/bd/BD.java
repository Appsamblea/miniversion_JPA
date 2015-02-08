package bd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import objetosBD.Asamblea;
import objetosBD.Mensaje;

public class BD {
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	public BD(){
		factory = Persistence.createEntityManagerFactory("appsamblea");
		//Se comprueba si la BD tiene alguna entrada.
		em = factory.createEntityManager();
		int filas = em.createNamedQuery("todasasambleas").setMaxResults(1).getResultList().size();
		if( filas == 0){
			em.close();
			asambleasIniciales();
			//Si la BD está vacía, se insertan un par de asambleas de ejemplo.
		}
		else{
			em.close();
			//Por el contrario, no se añade nada.
		}
		
	}
	
	private void asambleasIniciales(){
		objetosXML.Asamblea asamblea = new objetosXML.Asamblea();
		Date date = new Date();
		asamblea.setCreador("Carlos");
		asamblea.setDescripcion("La asamblea tendrá lugar en la ETSIIT");
		asamblea.setFecha(date);
		asamblea.setNombre("Asamblea en la ETSIIT");
		insertarAsamblea(asamblea);
		
		date.setMonth(6);
		date.setHours(20);
		asamblea = new objetosXML.Asamblea();
		asamblea.setCreador("Fran");
		asamblea.setDescripcion("La asamblea tendrá lugar en la Granada y hablaremos de muchas cosas");
		asamblea.setFecha(date);
		asamblea.setNombre("Asamblea en Granada");
		insertarAsamblea(asamblea);
	}
    
    public void insertarAsamblea(objetosXML.Asamblea asamblea) {
    	Asamblea as = new objetosBD.Asamblea();
    	as.setCreador(asamblea.getCreador());
    	as.setDescripcion(asamblea.getDescripcion());
    	as.setFecha(asamblea.getFecha());
    	as.setNombre(asamblea.getNombre());
    	//El ID lo genera la base de datos
    	//Los mensajes han de estar vacíos
    	em = factory.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		em.persist(as);
    		em.getTransaction().commit();
    	}finally{
    		em.close();
    	}
    }   
  
    public void eliminarAsamblea(objetosXML.Asamblea asamblea) {
    	Asamblea as = new objetosBD.Asamblea();
    	as.setCreador(asamblea.getCreador());
    	as.setDescripcion(asamblea.getDescripcion());
    	as.setFecha(asamblea.getFecha());
    	as.setNombre(asamblea.getNombre());
    	as.setId(asamblea.getId());
    	em = factory.createEntityManager();
    	try{
    		as = em.find(Asamblea.class, as.getId());
    		em.getTransaction().begin();
    		em.remove(as);
    		em.getTransaction().commit();
    	}finally{
    		em.close();
    	}
    }
    
    public ArrayList<Asamblea> getAsambleas(){
    	List<Asamblea> asambleas;
    	em = factory.createEntityManager();
    	try{
    		asambleas = em.createNamedQuery("todasasambleas").getResultList();
    	}finally{
    		em.close();
    	}
    	
    	ArrayList<Asamblea> arrayAsambleas = new ArrayList<Asamblea>();
    	for(Asamblea a: asambleas){
    		arrayAsambleas.add(a);
    	}
    	return arrayAsambleas;
    }
    
    public Asamblea insertarMensaje(objetosXML.Mensaje mensaje) {
		em = factory.createEntityManager();
		Asamblea asamblea = null;
		objetosBD.Mensaje m = new objetosBD.Mensaje();
		try{
			//Buscar la asamblea con el ID de asamblea.
			asamblea = em.find(Asamblea.class, mensaje.getIdAsamblea());
			m.setTexto(mensaje.getTexto());
			m.setEmisor(mensaje.getEmisor());
			Date date = new Date();
			date.setHours(Calendar.HOUR);
			date.setMinutes(Calendar.MINUTE);
			date.setSeconds(Calendar.SECOND);
			m.setFecha(date);
			
			//Insertarle el mensaje
			asamblea.añadirMensaje(m);
			em.getTransaction().begin();
    		asamblea = em.merge(asamblea);
    		em.getTransaction().commit();
		}finally{
    		em.close();
		}
		return asamblea;
    }

	public ArrayList<Mensaje> actualizarMensajes(long idAsamblea, Date fecha) {
    	List<Mensaje> mensajes;
    	
    	em = factory.createEntityManager();
    	try{
    		mensajes = em.createNamedQuery("actualizarmensajes")
    				.setParameter("fechaUltimo", fecha)
    				.setParameter("idAsamblea", idAsamblea)
    				.getResultList();
    	}finally{
    		em.close();
    	}
    	
    	ArrayList<Mensaje> arrayMensajes = new ArrayList<Mensaje>();
    	for(Mensaje m:mensajes){
    		arrayMensajes.add(m);
    	}
    	
    	return arrayMensajes;
	}
}
