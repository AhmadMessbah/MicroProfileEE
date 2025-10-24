package com.mftplus.person.rest;

import com.mftplus.person.dto.PersonDTO;
import com.mftplus.person.service.PersonService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Person REST API
 * Provides REST endpoints for Person microservice
 */
@Path("/api/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonRestAPI {
    
    @EJB
    private PersonService personService;
    
    @GET
    public Response getAllPersons() {
        try {
            List<PersonDTO> persons = personService.getAllPersons();
            return Response.ok(persons).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در دریافت لیست اشخاص: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getPersonById(@PathParam("id") Long id) {
        try {
            return personService.getPersonById(id)
                    .map(person -> Response.ok(person).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity("شخص مورد نظر یافت نشد").build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در دریافت اطلاعات شخص: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response createPerson(PersonDTO personDTO) {
        try {
            PersonDTO createdPerson = personService.createPerson(personDTO);
            return Response.status(Response.Status.CREATED).entity(createdPerson).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در ایجاد شخص: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, PersonDTO personDTO) {
        try {
            PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
            return Response.ok(updatedPerson).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در به‌روزرسانی شخص: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        try {
            boolean deleted = personService.deletePerson(id);
            if (deleted) {
                return Response.ok(true).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("شخص مورد نظر یافت نشد").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در حذف شخص: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/count")
    public Response getPersonCount() {
        try {
            long count = personService.getPersonCount();
            return Response.ok(count).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در شمارش اشخاص: " + e.getMessage()).build();
        }
    }
}
