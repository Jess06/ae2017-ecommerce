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
import mx.edu.ittepic.u2t01.entities.Company;
import mx.edu.ittepic.u2t01.utils.Message;

/**
 *
 * @author kon_n
 */
@Stateless
public class EJBOperacionesCompany {
    @PersistenceContext
    private EntityManager em;
        // Add business logic below. (Right-click in editor and choose
        // "Insert Code > Add Business Method")
    public String getCompanies() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Query q;
        List<Company> listCompanies;

        q = em.createNamedQuery("Company.findAll");
        try {
            listCompanies = q.getResultList();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(listCompanies));
        } catch (IllegalStateException | QueryTimeoutException | TransactionRequiredException | PessimisticLockException | LockTimeoutException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener las compañías, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudieron obtener las compañías, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    public String getCompanyById(int id) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Query q;
        Company company;
        q = em.createNamedQuery("Company.findByCompanyid").setParameter("companyid", id);
        try {
            company = (Company) q.getSingleResult();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La consulta se ejecutó correctamente.");
            m.setDetail(gson.toJson(company));
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
            m.setMessage("No se pudo obtener la compañía, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo obtener la compañía, intente nuevamente");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    public String createCompany(String companyName,String neighborhood,
            String zipcode,String city,String country,String state,
            String region,String street,String streetnumber,String phone,
            String rfc,String logo) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Company company= new Company();
        company.setCompanyname(companyName);
        company.setNeighborhood(neighborhood);
        company.setZipcode(zipcode);
        company.setCity(city);
        company.setCountry(country);
        company.setState(state);
        company.setRegion(region);
        company.setStreet(street);
        company.setStreetnumber(streetnumber);
        company.setPhone(phone);
        company.setRfc(rfc);
        company.setLogo(logo);
        try {
            em.persist(company);
            em.flush();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("La compañía se creó correctamente con la clave " + company.getCompanyid());
            m.setDetail(gson.toJson(company));
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar la compañía, intente nuevamente.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar la compañía, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    //agregar los demas campos
    public String updateCompany(int id, String companyName,String neighborhood,
            String zipcode,String city,String country,String state,
            String region,String street,String streetnumber,String phone,
            String rfc,String logo) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Company company;

        try {
            company = em.find(Company.class, id);
            if (company == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo actualizar la compañía, verifique que  e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ninguna compañía.");
            } else {
                company.setCompanyname(companyName);
                company.setNeighborhood(neighborhood);
                company.setZipcode(zipcode);
                company.setCity(city);
                company.setCountry(country);
                company.setState(state);
                company.setRegion(region);
                company.setStreet(street);
                company.setStreetnumber(streetnumber);
                company.setPhone(phone);
                company.setRfc(rfc);
                company.setLogo(logo);
                try {
                    em.merge(company);
                    em.flush();
                    m.setCode(HttpServletResponse.SC_OK);
                    m.setMessage("La compañía " +company.getCompanyid() + " se actualizó correctamente.");
                    m.setDetail(gson.toJson(company));
                } catch (IllegalArgumentException | PersistenceException e) {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo actualizar la compañía, verifique que sea correcta e intente nuevamente.");
                    m.setDetail(e.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo actualizar la compañía, verifique que sea correcta e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    public String deleteCompany(int id) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        Company company;
        try {
            company= em.find(Company.class, id);
            if (company == null) {
                m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                m.setMessage("No se pudo eliminar la compañía, verifique que sea correcta e intente nuevamente.");
                m.setDetail("El id proporcionado no está asociado con ninguna compañía.");
            } else {
                if (company.getUsersList().isEmpty()) {
                    try {
                        em.remove(company);
                        m.setCode(HttpServletResponse.SC_OK);
                        m.setMessage("La compañía " +company.getCompanyid() + " se eliminó correctamente.");
                        m.setDetail(gson.toJson(company));
                    } catch (IllegalArgumentException | PersistenceException e) {
                        m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                        m.setMessage("No se pudo eliminar la compañía, verifique que sea correcta e intente nuevamente.");
                        m.setDetail(e.toString());
                    }
                } else {
                    m.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    m.setMessage("No se pudo eliminar la compañía, verifique que sea correcta e intente nuevamente.");
                    m.setDetail("La compañía tiene usuarios asociados, no es posible eliminarlo.");
                }
            }
        } catch (IllegalArgumentException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo eliminar la compañía, verifique que sea correcta e intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
}
