package com.mftplus.person.servlet;

import com.mftplus.person.dto.PersonDTO;
import com.mftplus.person.service.PersonService;
import com.mftplus.simcard.dto.SimCardDTO;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Person Management Servlet
 * Handles CRUD operations for Person entity
 */
@WebServlet("/person/*")
public class PersonServlet extends HttpServlet {
    
    @EJB
    private PersonService personService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all persons
            listPersons(request, response);
        } else if (pathInfo.equals("/new")) {
            // Show new person form
            showNewPersonForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit person form
            String idStr = pathInfo.substring(6);
            try {
                Long id = Long.parseLong(idStr);
                showEditPersonForm(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else if (pathInfo.startsWith("/simcards/")) {
            // Show SIM cards for person
            String idStr = pathInfo.substring(10);
            try {
                Long id = Long.parseLong(idStr);
                showPersonSimCards(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Create new person
            createPerson(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Update person
            String idStr = pathInfo.substring(6);
            try {
                Long id = Long.parseLong(idStr);
                updatePerson(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete person
            String idStr = pathInfo.substring(8);
            try {
                Long id = Long.parseLong(idStr);
                deletePerson(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void listPersons(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<PersonDTO> persons = personService.getAllPersons();
            request.setAttribute("persons", persons);
            request.getRequestDispatcher("/WEB-INF/jsp/person/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت لیست اشخاص: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/person/list.jsp").forward(request, response);
        }
    }
    
    private void showNewPersonForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/person/form.jsp").forward(request, response);
    }
    
    private void showEditPersonForm(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        try {
            Optional<PersonDTO> person = personService.getPersonById(id);
            if (person.isPresent()) {
                request.setAttribute("person", person.get());
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/WEB-INF/jsp/person/form.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "شخص مورد نظر یافت نشد");
            }
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت اطلاعات شخص: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/person/form.jsp").forward(request, response);
        }
    }
    
    private void showPersonSimCards(HttpServletRequest request, HttpServletResponse response, Long personId) 
            throws ServletException, IOException {
        try {
            Optional<PersonDTO> person = personService.getPersonById(personId);
            if (person.isPresent()) {
                List<SimCardDTO> simCards = personService.getSimCardsForPerson(personId);
                request.setAttribute("person", person.get());
                request.setAttribute("simCards", simCards);
                request.getRequestDispatcher("/WEB-INF/jsp/person/simcards.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "شخص مورد نظر یافت نشد");
            }
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت اطلاعات سیم‌کارت: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/person/simcards.jsp").forward(request, response);
        }
    }
    
    private void createPerson(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setFirstName(request.getParameter("firstName"));
            personDTO.setLastName(request.getParameter("lastName"));
            personDTO.setEmail(request.getParameter("email"));
            personDTO.setPhoneNumber(request.getParameter("phoneNumber"));
            personDTO.setAddress(request.getParameter("address"));
            
            personService.createPerson(personDTO);
            response.sendRedirect(request.getContextPath() + "/person/");
        } catch (Exception e) {
            request.setAttribute("error", "خطا در ایجاد شخص: " + e.getMessage());
            request.setAttribute("person", new PersonDTO());
            request.getRequestDispatcher("/WEB-INF/jsp/person/form.jsp").forward(request, response);
        }
    }
    
    private void updatePerson(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        PersonDTO personDTO = new PersonDTO();
        try {
            personDTO.setId(id);
            personDTO.setFirstName(request.getParameter("firstName"));
            personDTO.setLastName(request.getParameter("lastName"));
            personDTO.setEmail(request.getParameter("email"));
            personDTO.setPhoneNumber(request.getParameter("phoneNumber"));
            personDTO.setAddress(request.getParameter("address"));
            
            personService.updatePerson(id, personDTO);
            response.sendRedirect(request.getContextPath() + "/person/");
        } catch (Exception e) {
            request.setAttribute("error", "خطا در به‌روزرسانی شخص: " + e.getMessage());
            request.setAttribute("person", personDTO);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/WEB-INF/jsp/person/form.jsp").forward(request, response);
        }
    }
    
    private void deletePerson(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        try {
            boolean deleted = personService.deletePerson(id);
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/person/");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "شخص مورد نظر یافت نشد");
            }
        } catch (Exception e) {
            request.setAttribute("error", "خطا در حذف شخص: " + e.getMessage());
            listPersons(request, response);
        }
    }
}
