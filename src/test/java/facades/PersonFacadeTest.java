/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author baske
 */
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Albert", "Sylvester", "88888888");
        p2 = new Person("Kasper", "Chrsitensen", "77777777");

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testgetPerson() throws PersonNotFoundException {
        PersonDTO pDTO = facade.getPerson(p1.getId());
        PersonDTO pDTO2 = new PersonDTO(p1);
        assertThat(pDTO, samePropertyValuesAs(pDTO2));
    }

    @Test
    public void facadeGetAllPersonsTest() {

        PersonsDTO DTOpersons = facade.getAllPersons();
        assertEquals(2, DTOpersons.size());
       // assertThat(DTOpersons.getAll().contains(p1);
        

    }

}
