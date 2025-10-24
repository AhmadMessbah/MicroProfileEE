package com.mftplus.simcard.servlet;

import com.mftplus.simcard.dto.SimCardDTO;
import com.mftplus.simcard.service.SimCardService;
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
 * SimCard Management Servlet
 * Handles CRUD operations for SimCard entity
 */
@WebServlet("/simcard/*")
public class SimCardServlet extends HttpServlet {
    
    @EJB
    private SimCardService simCardService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all SIM cards
            listSimCards(request, response);
        } else if (pathInfo.equals("/new")) {
            // Show new SIM card form
            showNewSimCardForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit SIM card form
            String idStr = pathInfo.substring(6);
            try {
                Long id = Long.parseLong(idStr);
                showEditSimCardForm(request, response, id);
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
            // Create new SIM card
            createSimCard(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Update SIM card
            String idStr = pathInfo.substring(6);
            try {
                Long id = Long.parseLong(idStr);
                updateSimCard(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete SIM card
            String idStr = pathInfo.substring(8);
            try {
                Long id = Long.parseLong(idStr);
                deleteSimCard(request, response, id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "شناسه نامعتبر");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void listSimCards(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<SimCardDTO> simCards = simCardService.getAllSimCards();
            request.setAttribute("simCards", simCards);
            request.getRequestDispatcher("/WEB-INF/jsp/simcard/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت لیست سیم‌کارت‌ها: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/simcard/list.jsp").forward(request, response);
        }
    }
    
    private void showNewSimCardForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/simcard/form.jsp").forward(request, response);
    }
    
    private void showEditSimCardForm(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        try {
            Optional<SimCardDTO> simCard = simCardService.getSimCardById(id);
            if (simCard.isPresent()) {
                request.setAttribute("simCard", simCard.get());
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/WEB-INF/jsp/simcard/form.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "سیم‌کارت مورد نظر یافت نشد");
            }
        } catch (Exception e) {
            request.setAttribute("error", "خطا در دریافت اطلاعات سیم‌کارت: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/simcard/form.jsp").forward(request, response);
        }
    }
    
    private void createSimCard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            SimCardDTO simCardDTO = new SimCardDTO();
            simCardDTO.setPhoneNumber(request.getParameter("phoneNumber"));
            simCardDTO.setIccid(request.getParameter("iccid"));
            simCardDTO.setOperator(request.getParameter("operator"));
            simCardDTO.setStatus(request.getParameter("status"));
            
            String personIdStr = request.getParameter("personId");
            if (personIdStr != null && !personIdStr.trim().isEmpty()) {
                simCardDTO.setPersonId(Long.parseLong(personIdStr));
            }
            
            String balanceStr = request.getParameter("balance");
            if (balanceStr != null && !balanceStr.trim().isEmpty()) {
                simCardDTO.setBalance(Double.parseDouble(balanceStr));
            }
            
            simCardService.createSimCard(simCardDTO);
            response.sendRedirect(request.getContextPath() + "/simcard/");
        } catch (Exception e) {
            request.setAttribute("error", "خطا در ایجاد سیم‌کارت: " + e.getMessage());
            request.setAttribute("simCard", new SimCardDTO());
            request.getRequestDispatcher("/WEB-INF/jsp/simcard/form.jsp").forward(request, response);
        }
    }
    
    private void updateSimCard(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        SimCardDTO simCardDTO = new SimCardDTO();
        try {
            simCardDTO.setId(id);
            simCardDTO.setPhoneNumber(request.getParameter("phoneNumber"));
            simCardDTO.setIccid(request.getParameter("iccid"));
            simCardDTO.setOperator(request.getParameter("operator"));
            simCardDTO.setStatus(request.getParameter("status"));
            
            String personIdStr = request.getParameter("personId");
            if (personIdStr != null && !personIdStr.trim().isEmpty()) {
                simCardDTO.setPersonId(Long.parseLong(personIdStr));
            }
            
            String balanceStr = request.getParameter("balance");
            if (balanceStr != null && !balanceStr.trim().isEmpty()) {
                simCardDTO.setBalance(Double.parseDouble(balanceStr));
            }
            
            simCardService.updateSimCard(id, simCardDTO);
            response.sendRedirect(request.getContextPath() + "/simcard/");
        } catch (Exception e) {
            request.setAttribute("error", "خطا در به‌روزرسانی سیم‌کارت: " + e.getMessage());
            request.setAttribute("simCard", simCardDTO);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/WEB-INF/jsp/simcard/form.jsp").forward(request, response);
        }
    }
    
    private void deleteSimCard(HttpServletRequest request, HttpServletResponse response, Long id) 
            throws ServletException, IOException {
        try {
            boolean deleted = simCardService.deleteSimCard(id);
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/simcard/");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "سیم‌کارت مورد نظر یافت نشد");
            }
        } catch (Exception e) {
            request.setAttribute("error", "خطا در حذف سیم‌کارت: " + e.getMessage());
            listSimCards(request, response);
        }
    }
}
