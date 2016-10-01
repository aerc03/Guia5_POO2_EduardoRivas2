/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.CursosFacadeLocal;
import com.sv.udb.ejb.CursosFacadeLocal;
import com.sv.udb.modelo.Cursos;
import com.sv.udb.modelo.Cursos;
import com.sv.udb.utils.log4j;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "cursosBean")
@ViewScoped
public class CursosBean implements Serializable{

    @EJB
    private CursosFacadeLocal FCDECurs;
    private Cursos objeCurs;
    private List<Cursos> listCurs;
    private boolean guardar;
    log4j log;

    public Cursos getObjeCurs() {
        return objeCurs;
    }

    public void setObjeCurs(Cursos objeCurs) {
        this.objeCurs = objeCurs;
    }

    public List<Cursos> getListCurs() {
        return listCurs;
    }

    public void setListCurs(List<Cursos> listCurs) {
        this.listCurs = listCurs;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

        
    /**
     * Creates a new instance of CursosBean
     */
    
    public CursosBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeCurs = new Cursos();
        this.guardar = true;
        this.consTodo();
        log = new log4j();
    }
    
    public void limpForm()
    {
        this.objeCurs = new Cursos();
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.objeCurs.setNombCurs(null);
            FCDECurs.create(this.objeCurs);
            this.listCurs.add(this.objeCurs);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Curso: " + this.objeCurs.getNombCurs() + " guardado.");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
            log.error("Error al guardar el curso.");
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listCurs.remove(this.objeCurs); //Limpia el objeto viejo
            FCDECurs.edit(this.objeCurs);
            this.listCurs.add(this.objeCurs); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            log.info("Curso: " + this.objeCurs.getNombCurs() + " fue modificado.");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
            log.error("Error al modificar el curso.");
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
                FCDECurs.remove(this.objeCurs);
                this.listCurs.remove(this.objeCurs);
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
                log.info("Curso Eliminado.");
            }
            catch(Exception ex)
            {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
                log.error("Error al eliminar el curso.");
            }
            finally
            {

            }
        }

        public void consTodo()
        {
            try
            {
                this.listCurs = FCDECurs.findAll();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                log.error("Error al guardar los cursos.");
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
                this.objeCurs = FCDECurs.find(codi);
                this.guardar = false;
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                        String.format("%s", this.objeCurs.getNombCurs()) + "')");
            }
            catch(Exception ex)
            {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
                log.error("Error al consultar el curso.");
            }
            finally
            {

            }
        }
    }