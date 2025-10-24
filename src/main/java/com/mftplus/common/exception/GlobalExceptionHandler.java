package com.mftplus.common.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Global Exception Handler Servlet
 * Handles all exceptions across the application
 */
@WebServlet("/error")
public class    GlobalExceptionHandler extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleException(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleException(request, response);
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleException(request, response);
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleException(request, response);
    }
    
    private void handleException(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        String errorMessage = "خطای غیرمنتظره رخ داده است";
        int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        
        if (throwable != null) {
            logger.severe("Exception occurred: " + throwable.getMessage());
            throwable.printStackTrace();
            
            if (throwable instanceof IllegalArgumentException) {
                errorMessage = throwable.getMessage();
                statusCode = HttpServletResponse.SC_BAD_REQUEST;
            } else if (throwable instanceof ConstraintViolationException) {
                errorMessage = "خطا در اعتبارسنجی داده‌ها";
                statusCode = HttpServletResponse.SC_BAD_REQUEST;
            } else if (throwable instanceof RuntimeException) {
                errorMessage = throwable.getMessage();
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }
        }
        
        response.setStatus(statusCode);
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='fa'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>خطا</title>");
        out.println("    <style>");
        out.println("        body { font-family: 'Tahoma', Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 20px; }");
        out.println("        .error-container { max-width: 600px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        .error-icon { font-size: 48px; color: #e74c3c; text-align: center; margin-bottom: 20px; }");
        out.println("        .error-title { font-size: 24px; color: #2c3e50; text-align: center; margin-bottom: 20px; }");
        out.println("        .error-message { font-size: 16px; color: #7f8c8d; text-align: center; margin-bottom: 30px; line-height: 1.6; }");
        out.println("        .back-button { display: block; width: 200px; margin: 0 auto; padding: 12px 24px; background-color: #3498db; color: white; text-decoration: none; border-radius: 5px; text-align: center; }");
        out.println("        .back-button:hover { background-color: #2980b9; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='error-container'>");
        out.println("        <div class='error-icon'>⚠️</div>");
        out.println("        <h1 class='error-title'>خطا</h1>");
        out.println("        <p class='error-message'>" + errorMessage + "</p>");
        out.println("        <a href='javascript:history.back()' class='back-button'>بازگشت</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
}
