/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author baske
 */
@Path("person")
public class PersonResource {

    private static PersonDTO pDTO = new PersonDTO("Albert", "Lohde", "12344444", "Sej Vej", "2800", "Lyngby");

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAlbertPerson() {
        return GSON.toJson(pDTO);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPerson(@PathParam("id") int id)throws PersonNotFoundException {
        PersonDTO person = FACADE.getPerson(id);
        return GSON.toJson(person);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String person) {
        PersonDTO pp = GSON.fromJson(person, PersonDTO.class);

        //sæt person i db hvilket også giver ham et id
        PersonDTO persDTO = FACADE.addPerson(pp.getFirstName(), pp.getLastName(), pp.getPhone(), pp.getStreet(), pp.getZip(), pp.getCity());
        return GSON.toJson(persDTO);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPerson(@PathParam("id") int id, String person)throws PersonNotFoundException {
        PersonDTO persondto = GSON.fromJson(person, PersonDTO.class);

        persondto.setId(id);
        
        PersonDTO persondto2 = FACADE.editPerson(persondto);
        
        return GSON.toJson(persondto2);
    }
    


    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        PersonsDTO allPersons = FACADE.getAllPersons();
        return GSON.toJson(allPersons);
    }
    
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException{
        
        PersonDTO persondto = FACADE.deletePerson(id);
        
        String persFjern = "Person fjernet: " + GSON.toJson(persondto);
        return persFjern;
    }
}
