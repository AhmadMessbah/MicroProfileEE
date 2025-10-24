package com.mftplus.simcard.rest;

import com.mftplus.simcard.dto.SimCardDTO;
import com.mftplus.simcard.service.SimCardService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * SimCard REST API
 * Provides REST endpoints for SimCard microservice
 */
@Path("/api/simcards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimCardRestAPI {
    
    @EJB
    private SimCardService simCardService;
    
    @GET
    public Response getAllSimCards() {
        try {
            List<SimCardDTO> simCards = simCardService.getAllSimCards();
            return Response.ok(simCards).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در دریافت لیست سیم‌کارت‌ها: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getSimCardById(@PathParam("id") Long id) {
        try {
            return simCardService.getSimCardById(id)
                    .map(simCard -> Response.ok(simCard).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity("سیم‌کارت مورد نظر یافت نشد").build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در دریافت اطلاعات سیم‌کارت: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/person/{personId}")
    public Response getSimCardsByPersonId(@PathParam("personId") Long personId) {
        try {
            List<SimCardDTO> simCards = simCardService.getSimCardsByPersonId(personId);
            return Response.ok(simCards).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در دریافت سیم‌کارت‌های شخص: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response createSimCard(SimCardDTO simCardDTO) {
        try {
            SimCardDTO createdSimCard = simCardService.createSimCard(simCardDTO);
            return Response.status(Response.Status.CREATED).entity(createdSimCard).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در ایجاد سیم‌کارت: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateSimCard(@PathParam("id") Long id, SimCardDTO simCardDTO) {
        try {
            SimCardDTO updatedSimCard = simCardService.updateSimCard(id, simCardDTO);
            return Response.ok(updatedSimCard).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در به‌روزرسانی سیم‌کارت: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteSimCard(@PathParam("id") Long id) {
        try {
            boolean deleted = simCardService.deleteSimCard(id);
            if (deleted) {
                return Response.ok(true).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("سیم‌کارت مورد نظر یافت نشد").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در حذف سیم‌کارت: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/count")
    public Response getSimCardCount() {
        try {
            long count = simCardService.getSimCardCount();
            return Response.ok(count).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("خطا در شمارش سیم‌کارت‌ها: " + e.getMessage()).build();
        }
    }
}
