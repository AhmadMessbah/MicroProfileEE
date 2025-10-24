package com.mftplus.person.client;

import com.mftplus.simcard.dto.SimCardDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * REST Client for SimCard Microservice
 * Uses @RegisterRestClient for communication between microservices
 */
@RegisterRestClient(baseUri = "http://localhost:8080/mftplus/simcard")
@RequestScoped
@Path("/api")
public interface SimCardClient {
    
    /**
     * Get all SIM cards
     */
    @GET
    @Path("/simcards")
    @Produces(MediaType.APPLICATION_JSON)
    List<SimCardDTO> getAllSimCards();
    
    /**
     * Get SIM card by ID
     */
    @GET
    @Path("/simcards/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    SimCardDTO getSimCardById(@PathParam("id") Long id);
    
    /**
     * Get SIM cards by person ID
     */
    @GET
    @Path("/simcards/person/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    List<SimCardDTO> getSimCardsByPersonId(@PathParam("personId") Long personId);
    
    /**
     * Create new SIM card
     */
    @POST
    @Path("/simcards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SimCardDTO createSimCard(SimCardDTO simCardDTO);
    
    /**
     * Update SIM card
     */
    @PUT
    @Path("/simcards/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SimCardDTO updateSimCard(@PathParam("id") Long id, SimCardDTO simCardDTO);
    
    /**
     * Delete SIM card
     */
    @DELETE
    @Path("/simcards/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    boolean deleteSimCard(@PathParam("id") Long id);
    
    /**
     * Get SIM card count
     */
    @GET
    @Path("/simcards/count")
    @Produces(MediaType.APPLICATION_JSON)
    long getSimCardCount();
}
