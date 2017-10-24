/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.u2t01.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import mx.edu.ittepic.u2t01.entities.Category;
import mx.edu.ittepic.u2t01.utils.Message;

/**
 *
 * @author Jessica Lizbeth
 */
@Stateless
public class EJBOperacionesCategory {

    @PersistenceContext
    private EntityManager em;

    public String getCategories() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Query q;
        List<Category> listCategories;

        q = em.createNamedQuery("Category.findAll");
        try {
            listCategories = q.getResultList();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(listCategories));
        } catch (IllegalStateException | QueryTimeoutException | TransactionRequiredException | PessimisticLockException | LockTimeoutException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener las categorías, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener las categorías, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String getCategoryById(int id) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Query q;
        Category category;

        q = em.createNamedQuery("Category.findByCategoryid").setParameter("categoryid", id);
        try {
            category = (Category) q.getSingleResult();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(category));
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
            m.setMessage("No se pudo obtener la categoría, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo obtener la categoría, intente nuevamente");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String createCategory(String categoryName) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Category category = new Category();
        category.setCategoryname(categoryName);

        try {
            em.persist(category);
            em.flush();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La categoría se creó correctamente con la clave " + category.getCategoryid());
            m.setDetail(gson.toJson(category));
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar la categoría, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar la categoría, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String updateCategory(int id, String newCategory) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Category category;

        try {
            category = em.find(Category.class, id);
            if (category == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo actualizar la categoría, verifique que  e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ninguna categoría.");
            } else {
                category.setCategoryname(newCategory);
                try {
                    em.merge(category);
                    em.flush();
                    m.setCode(HttpServletResponse.SC_OK);
                    m.setMessage("La categoría " + category.getCategoryid() + " se actualizó correctamente.");
                    m.setDetail(gson.toJson(category));
                } catch (IllegalArgumentException | PersistenceException e) {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo actualizar la categoría, verifique que sea correcta e intente nuevamente.");
                    m.setDetail(e.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo actualizar la categoría, verifique que sea correcta e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String deleteCategory(int id) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Category category;
        try {
            category = em.find(Category.class, id);
            if (category == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo eliminar la categoría, verifique que sea correcta e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ninguna categoría.");
            } else {
                if (category.getProductList().isEmpty()) {
                    try {
                        em.remove(category);
                        m.setCode(HttpServletResponse.SC_OK);
                        m.setMessage("La categoría " + category.getCategoryid() + " se eliminó correctamente.");
                        m.setDetail(gson.toJson(category));
                    } catch (IllegalArgumentException | PersistenceException e) {
                        m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                        m.setMessage("No se pudo eliminar la categoría, verifique que sea correcta e intente nuevamente.");
                        m.setDetail(e.toString());
                    }
                } else {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo eliminar la categoría, verifique que sea correcta e intente nuevamente.");
                    m.setDetail("La categoría tiene productos asociados, no es posible eliminarlo.");
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo eliminar la categoría, verifique que sea correcta e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

}
