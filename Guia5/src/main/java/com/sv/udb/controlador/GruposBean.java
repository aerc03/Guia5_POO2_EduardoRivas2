/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
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
 * @author Mauricio
 */
@Named(value = "gruposBean")
@ViewScoped
public class GruposBean implements Serializable{

    @EJB
    private GruposFacadeLocal FCDEGrupos;

    
    private Grupos objeGrup;
    private List<Grupos> listGrup;
    private boolean guardar;
    log4j log;

    public Grupos getObjeGrup() {
        return objeGrup;
    }

    public void setObjeGrup(Grupos objeGrup) {
        this.objeGrup = objeGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }
    
    
    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeGrup = new Grupos();
        this.guardar = true;
        this.consTodo();
        this.listGrup = FCDEGrupos.findAll();
        log = new log4j();
    }

    public List<Grupos> getListGrup() {
        return listGrup;
    }

    public void setListGrup(List<Grupos> listGrup) {
        this.listGrup = listGrup;
    }
    
    
    public void limpForm()
    {
        this.objeGrup = new Grupos();
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEGrupos.create(this.objeGrup);
            this.listGrup.add(this.objeGrup);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
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
            this.listGrup.remove(this.objeGrup); //Limpia el objeto viejo
            FCDEGrupos.edit(this.objeGrup);
            this.listGrup.add(this.objeGrup); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
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
                FCDEGrupos.remove(this.objeGrup);
                this.listGrup.remove(this.objeGrup);
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
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
                this.listGrup = FCDEGrupos.findAll();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
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
                this.objeGrup = FCDEGrupos.find(codi);
                this.guardar = false;
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                        String.format("%s", this.objeGrup.getNombGrup()));
            }
            catch(Exception ex)
            {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
            }
            finally
            {

            }
        }
    
}
