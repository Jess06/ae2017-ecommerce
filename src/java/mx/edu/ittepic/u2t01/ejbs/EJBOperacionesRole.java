/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.u2t01.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.servlet.http.HttpServletResponse;
import mx.edu.ittepic.u2t01.entities.Role;
import mx.edu.ittepic.u2t01.utils.Message;

/**
 *
 * @author Marco Chavez
 */
@Stateless
public class EJBOperacionesRole {

    @PersistenceContext
    private EntityManager em;

    public String getRoles() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();

        Query q; //hacer consulta
        List<Role> listRoles; //guardar el resultado
        q = em.createNamedQuery("Role.findAll");//consulta como si hay una que nos trae todo los roles utilizamos el Role.findAll si no esta se tiene que crear

        try {
            listRoles = q.getResultList();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(listRoles));
            //return gson.toJson(listRoles);
        } catch (IllegalStateException | QueryTimeoutException | TransactionRequiredException | PessimisticLockException | LockTimeoutException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener los roles, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener los roles, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String getRolById(int id) {
        Query q;
        Role rol;
        Message m = new Message();
        q = em.createNamedQuery("Role.findByRoleid").setParameter("rolid", id);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            rol = (Role) q.getSingleResult();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(rol));
        } catch (NoResultException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se encontraron resultados.");
            m.setDetail(e.toString());
        } catch (NonUniqueResultException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("Existe más de un resultado.");
            m.setDetail(e.toString());
        } catch (IllegalStateException | QueryTimeoutException | TransactionRequiredException | PessimisticLockException | LockTimeoutException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo obtener el rol, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo obtener el rol, intente nuevamente.");
            m.setDetail(e.toString());
        }

        String result = gson.toJson(m);
        return result;
    }

    public String createRol(String rolname, Double salary) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();

        Role rol = new Role();
        rol.setRolename(rolname);
        rol.setSalary(salary);
        rol.setCreatedat(new Date());

        try {
            //Crear nuevo registro en la BD.
            em.persist(rol);
            //Obligar al contenedor a guardar en la BD.
            em.flush();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("El rol se creó correctamente con la clave " + rol.getRoleid());
            m.setDetail(gson.toJson(rol));
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar el rol, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String updateRol(int id, String newRole) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Role rol;
        try {
            rol = em.find(Role.class, id);
            if (rol == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo actualizar el rol, verifique que sea correcto e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ningún rol.");
            } else {
                rol.setRolename(newRole);
                try {
                    em.merge(rol);
                    em.flush();
                    m.setCode(HttpServletResponse.SC_OK);
                    m.setMessage("El rol " + rol.getRoleid() + " se actualizó correctamente.");
                    m.setDetail(gson.toJson(rol));
                } catch (IllegalArgumentException | PersistenceException e) {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo actualizar el rol, verifique que sea correcto e intente nuevamente.");
                    m.setDetail(e.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo actualizar el rol, verifique que sea correcto e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String deleteRol(int id) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Role rol;

        try {
            rol = em.find(Role.class, id);
            if (rol == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo eliminar el rol, verifique que sea correcto e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ningún rol.");
            } else {
                if (rol.getUsersList().isEmpty()) {
                    try {
                        em.remove(rol);
                        m.setCode(HttpServletResponse.SC_OK);
                        m.setMessage("El rol " + rol.getRoleid() + " se eliminó correctamente.");
                        m.setDetail(gson.toJson(rol));
                    } catch (IllegalArgumentException | PersistenceException e) {
                        m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                        m.setMessage("No se pudo eliminar el rol, verifique que sea correcto e intente nuevamente.");
                        m.setDetail(e.toString());
                    }
                } else {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo eliminar el rol, verifique que sea correcto e intente nuevamente.");
                    m.setDetail("El rol tiene usuarios asociados, no es posible eliminarlo.");
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo eliminar el rol, verifique que sea correcto e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
}
