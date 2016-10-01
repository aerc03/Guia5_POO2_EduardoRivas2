/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnosFacadeLocal;
import com.sv.udb.modelo.Alumnos;
import com.sv.udb.utils.log4j;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.apache.log4j.Logger;

/**
 *
 * @author REGISTRO
 */
@Named(value = "alumnosBean")
@ViewScoped
public class AlumnosBean implements Serializable{
    @EJB
    private AlumnosFacadeLocal FCDEAlum;    
    private Alumnos objeAlum;
    private List<Alumnos> listAlum;
    private boolean guardar;
    log4j log;

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
        log = new log4j();
        try {
            this.consTodo();
        } catch (Exception e) {
            log.error("Error");
        }
        
        
    }
    
    public void limpForm()
    {
        this.objeAlum = new Alumnos();
        this.guardar = true;        
    }
        
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEAlum.create(this.objeAlum);
            this.listAlum.add(this.objeAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Alumno " + this.objeAlum.getNombAlum() + " " + this.objeAlum.getNombAlum() + " Guardado.");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
        }
        finally
        {
            log.error("Error al consultar los Alumnos.");
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listAlum.remove(this.objeAlum); //Limpia el objeto viejo
            FCDEAlum.edit(this.objeAlum);
            this.listAlum.add(this.objeAlum); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            log.info("Alumno: " + this.objeAlum.getNombAlum() + " " + this.objeAlum.getApelAlum() + "fue modificado.");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
            {
                FCDEAlum.remove(this.objeAlum);
                this.listAlum.remove(this.objeAlum);
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
                log.info("Un alumno fue eliminado.");
            }
            catch(Exception ex)
            {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
            }
            finally
            {

            }
        }

        public void consTodo()
        {
            try
            {
                this.listAlum = FCDEAlum.findAll();
                //log.info("Se han consultado los Alumnos.");
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                log.error("Error al consultar los Alumnos.");
            }
            finally
            {

            }
        }

        public void cons()
        {
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiAlumPara"));
            try
            {
                this.objeAlum = FCDEAlum.find(codi);
                this.guardar = false;
                log.info("Consulta en tabla: Alumnos. Alumno: " + this.objeAlum.getNombAlum() + " " + this.objeAlum.getApelAlum());
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                        String.format("%s %s", this.objeAlum.getNombAlum(), this.objeAlum.getApelAlum()) + "')");
            }
            catch(Exception ex)
            {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
                log.error("Error al consultar Alumno con codigo:" + codi);
                

            }
            finally
            {

            }
        }
    }