package com.mftplus.admin.servlet;

import com.mftplus.person.dto.PersonDTO;
import com.mftplus.person.service.PersonService;
import com.mftplus.simcard.dto.SimCardDTO;
import com.mftplus.simcard.service.SimCardService;
import com.mftplus.admin.websocket.AdminWebSocket;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Admin Servlet - Provides admin dashboard with live updates
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
    @EJB
    private PersonService personService;
    
    @EJB
    private SimCardService simCardService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get all persons and SIM cards
            List<PersonDTO> persons = personService.getAllPersons();
            List<SimCardDTO> simCards = simCardService.getAllSimCards();
            
            // Get counts
            long personCount = personService.getPersonCount();
            long simCardCount = simCardService.getSimCardCount();
            int connectedClients = AdminWebSocket.getConnectedClientsCount();
            
            // Set attributes
            request.setAttribute("persons", persons);
            request.setAttribute("simCards", simCards);
            request.setAttribute("personCount", personCount);
            request.setAttribute("simCardCount", simCardCount);
            request.setAttribute("connectedClients", connectedClients);
            
            // Forward to admin JSP
            request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت اطلاعات ادمین: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
        }
    }
}
