/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.modelo.Alumnos;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "alumnosBean")
@ViewScoped
public class AlumnosBean implements Serializable{
    private Alumnos objeAlum;
    private List<Alumnos> listAlum;
    private boolean guardar;

    public Alumnos getObjeAlum() {
        return objeAlum;
    }

    public void setObjeAlum(Alumnos objeAlum) {
        this.objeAlum = objeAlum;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Alumnos> getListAlum() {
        return listAlum;
    }
    
    /**
     * Creates a new instance of AlumnosBean
     */
    
    public AlumnosBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeAlum = new Alumnos();
        this.guardar = true;
        this.consTodo();
    }
    public void limpForm()
    {
        System.err.println("entyra");
        this.objeAlum = new Alumnos();
        this.guardar = true;        
    }
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Guia5");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            em.persist(this.objeAlum);
            tx.commit();
            this.guardar = true;
            this.listAlum.add(this.objeAlum); //Agrega el objeto a la lista para poderse mostrar en tabla
            this.objeAlum = new Alumnos(); // Limpiar
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
            tx.rollback();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
    
    public void consTodo()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Guia5");
        EntityManager em = emf.createEntityManager();
        try
        {
            this.listAlum = em.createNamedQuery("Alumnos.findAll", Alumnos.class).getResultList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
    
    public void elim(int codiAlum) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Guia5");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Alumnos alumno;
        try {
            alumno = em.getReference(Alumnos.class, codiAlum);
            alumno.getCodiAlum();
            em.remove(alumno);
            et.commit();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos eliminados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO eliminados');");
            et.rollback();
            ex.printStackTrace();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
    public void actu() {
        RequestContext ctx = RequestContext.getCurrentInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Guia5");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Alumnos obj = em.find(Alumnos.class, this.objeAlum.getCodiAlum());
            obj.setNombAlum(this.objeAlum.getNombAlum());
            obj.setApelAlum(this.objeAlum.getApelAlum());
            obj.setFechNaciAlum(this.objeAlum.getFechNaciAlum());
            obj.setMailAlum(this.objeAlum.getMailAlum());
            obj.setTeleAlum(this.objeAlum.getTeleAlum());
            obj.setDireAlum(this.objeAlum.getDireAlum());
            obj.setGeneAlum(this.objeAlum.getGeneAlum());
            em.getTransaction().commit();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
    
    public void cons(int codiAlum) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Guia5");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Alumnos> query = em.createNamedQuery("Alumnos.findByCodiAlum", Alumnos.class);
            query.setParameter("codiAlum", codiAlum);
            List<Alumnos> result = query.getResultList();
            for (Alumnos l : result) {
                this.objeAlum = new Alumnos(l.getCodiAlum(), l.getNombAlum(), l.getApelAlum(), l.getFechNaciAlum(), l.getMailAlum(), l.getTeleAlum(), l.getDireAlum(), l.getGeneAlum());                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
}
