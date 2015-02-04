package bd;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import objetosBD.Asamblea;
import objetosBD.Mensaje;

public class BD {
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	public BD(){
		factory = Persistence.createEntityManagerFactory("Appsamblea");
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
    		em.persist(asamblea);
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
    
    public List<Asamblea> getAsambleas(){
    	List<Asamblea> asambleas;
    	if(factory == null){
    		System.out.println("Factory es null");
    	}
    	em = factory.createEntityManager();
    	try{
    		asambleas = em.createNamedQuery("todos").getResultList();
    	}finally{
    		em.close();
    	}
    	return asambleas;
    }
    
    public Asamblea insertarMensaje(objetosXML.Mensaje mensaje) {
		em = factory.createEntityManager();
		Asamblea asamblea = null;
		Mensaje m = new Mensaje();
		try{
			//Buscar la asamblea con el ID de asamblea.
			asamblea = em.find(Asamblea.class, mensaje.getIdAsamblea());
			m.setTexto(mensaje.getTexto());
			m.setEmisor(mensaje.getEmisor());
			m.setFecha(new Date());
			
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

	public List<Mensaje> actualizarMensajes(long idAsamblea, Date fecha) {
    	List<Mensaje> mensajes;
    	if(factory == null){
    		System.out.println("Factory es null");
    	}
    	em = factory.createEntityManager();
    	try{
    		mensajes = em.createNamedQuery("actualizarmensajes")
    				.setParameter("fechaUltimo", fecha)
    				.setParameter("idAsamblea", idAsamblea)
    				.getResultList();
    	}finally{
    		em.close();
    	}
    	return mensajes;
	}
}
