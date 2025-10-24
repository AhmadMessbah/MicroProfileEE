package com.mftplus.simcard.client;

import com.mftplus.person.dto.PersonDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * REST Client for Person Microservice
 * Uses @RegisterRestClient for communication between microservices
 */
@RegisterRestClient(baseUri = "http://localhost:8080/mftplus/person")
@RequestScoped
@Path("/api")
public interface PersonClient {
    
    /**
     * Get all persons
     */
    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    List<PersonDTO> getAllPersons();
    
    /**
     * Get person by ID
     */
    @GET
    @Path("/persons/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    PersonDTO getPersonById(@PathParam("id") Long id);
    
    /**
     * Create new person
     */
    @POST
    @Path("/persons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    PersonDTO createPerson(PersonDTO personDTO);
    
    /**
     * Update person
     */
    @PUT
    @Path("/persons/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    PersonDTO updatePerson(@PathParam("id") Long id, PersonDTO personDTO);
    
    /**
     * Delete person
     */
    @DELETE
    @Path("/persons/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    boolean deletePerson(@PathParam("id") Long id);
    
    /**
     * Get person count
     */
    @GET
    @Path("/persons/count")
    @Produces(MediaType.APPLICATION_JSON)
    long getPersonCount();
}
