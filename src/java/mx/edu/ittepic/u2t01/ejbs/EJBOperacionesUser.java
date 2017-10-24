/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.u2t01.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.servlet.http.HttpServletResponse;
import mx.edu.ittepic.u2t01.entities.Company;
import mx.edu.ittepic.u2t01.entities.Role;
import mx.edu.ittepic.u2t01.entities.Users;
import mx.edu.ittepic.u2t01.utils.Message;

/**
 *
 * @author Jessica Lizbeth
 */
@Stateless
public class EJBOperacionesUser {
    
    @PersistenceContext
    private EntityManager em;
    
    public String createUser(String username, String email, String pass, String gender, 
            String rolid, String companyid, String phone, String cellphone, String street,
            String number, String neighborhood, String zipcode, String city, String state,
            String region, String country, String photo) {
        
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();

        Users user = new Users();
        Role rol = em.find(Role.class, Integer.parseInt(rolid));
        Company company = em.find(Company.class, Integer.parseInt(companyid));
        
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pass);
        user.setGender(gender);
        user.setRoleid(rol);
        user.setCompanyid(company);
        user.setPhone(phone);
        user.setCellphone(cellphone);
        user.setStreet(street);
        user.setNeighborhood(neighborhood);
        user.setStreetnumber(number);
        user.setZipcode(zipcode);
        user.setCity(city);
        user.setState(state);
        user.setRegion(region);
        user.setCountry(country);
        user.setPhoto(photo);

        try {
            //Crear nuevo registro en la BD.
            em.persist(user);
            //Obligar al contenedor a guardar en la BD.
            em.flush();
            m.setCode(HttpServletResponse.SC_OK);
            m.setMessage("El usuario se creó correctamente con la clave " + user.getUserid());
            m.setDetail(gson.toJson(user));
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            m.setCode(HttpServletResponse.SC_BAD_REQUEST);
            m.setMessage("No se pudo guardar el usuario, intente nuevamente.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
}
