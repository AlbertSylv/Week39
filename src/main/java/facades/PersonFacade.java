package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.Person;
import exceptions.PersonNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
    @Override
    public PersonDTO addPerson(String firstName, String lastName, String phone, String street, String zip, String city) {
        EntityManager em = getEntityManager();
        Person per = new Person(firstName, lastName, phone);
        per.setAddress(new Address(street,zip,city));
        
        try{
            em.getTransaction().begin();
            em.persist(per);
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
        return new PersonDTO(per);
    }

   
    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
          Person person = em.find(Person.class, id);
          Address address = em.find(Address.class, id);
          try {
                    em.getTransaction().begin();
                    
                    if (person == null) {
                    throw new PersonNotFoundException(String.format("Kunne ikke finde person med id: " + id));
                } else {
                        em.remove(address);
                        em.remove(person);
                        
                        
                        
                    em.getTransaction().commit();
                    
                    }
                } finally {
                    em.close();
            }
            return new PersonDTO(person);
    }

    
    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
                EntityManager em = getEntityManager();
        try {
              Person person = em.find(Person.class, id);
              if (person == null) {
                    throw new PersonNotFoundException(String.format("Kunne ikke finde person med id: " + id));
                } else {
              
              return new PersonDTO(person);
        }}         
        finally {
            em.close();
        }
    }

    
    @Override
    public PersonsDTO getAllPersons() {
                EntityManager em = getEntityManager();
        try {
            PersonsDTO DTOpersons = new PersonsDTO(em.createQuery("SELECT p from Person p").getResultList());
            return DTOpersons;
 
        } finally {
            em.close();
        }
    }

    
    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        
        try {
            em.getTransaction().begin();
                            Person person = em.find(Person.class, p.getId());
                if (person == null) {
                    throw new PersonNotFoundException(String.format("Kunne ikke finde person med id: " + p.getId()));
                } else {
            
            
                
                person.setFirstName(p.getFirstName());
                    person.setLastName(p.getLastName());
                    person.setPhone(p.getPhone());
                    
                    person.getAddress().setStreet(p.getStreet());
                    person.getAddress().setZip(p.getZip());
                    person.getAddress().setCity(p.getCity());
                    
                    em.getTransaction().commit();
                    return new PersonDTO(person);
                    
                }
    }finally{
            em.close();
        }
    }

}

