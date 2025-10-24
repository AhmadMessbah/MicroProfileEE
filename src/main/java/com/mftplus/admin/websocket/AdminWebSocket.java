package com.mftplus.admin.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * WebSocket endpoint for live admin updates
 * Provides real-time updates for Person and SimCard data
 */
@ServerEndpoint("/admin/websocket")
public class AdminWebSocket {
    
    private static final Logger logger = Logger.getLogger(AdminWebSocket.class.getName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        logger.info("Admin WebSocket connection opened: " + session.getId());
        
        // Send welcome message
        try {
            session.getBasicRemote().sendText("{\"type\":\"welcome\",\"message\":\"اتصال برقرار شد\"}");
        } catch (IOException e) {
            logger.severe("Error sending welcome message: " + e.getMessage());
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        logger.info("Admin WebSocket connection closed: " + session.getId());
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.severe("WebSocket error: " + throwable.getMessage());
        sessions.remove(session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Received message: " + message);
        
        // Echo back the message
        try {
            session.getBasicRemote().sendText("{\"type\":\"echo\",\"message\":\"" + message + "\"}");
        } catch (IOException e) {
            logger.severe("Error sending echo message: " + e.getMessage());
        }
    }
    
    /**
     * Broadcast message to all connected clients
     */
    public static void broadcast(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        logger.severe("Error broadcasting message: " + e.getMessage());
                        sessions.remove(session);
                    }
                }
            }
        }
    }
    
    /**
     * Broadcast person update
     */
    public static void broadcastPersonUpdate(String action, String personData) {
        String message = String.format(
            "{\"type\":\"person_update\",\"action\":\"%s\",\"data\":%s}",
            action, personData
        );
        broadcast(message);
    }
    
    /**
     * Broadcast SIM card update
     */
    public static void broadcastSimCardUpdate(String action, String simCardData) {
        String message = String.format(
            "{\"type\":\"simcard_update\",\"action\":\"%s\",\"data\":%s}",
            action, simCardData
        );
        broadcast(message);
    }
    
    /**
     * Get number of connected clients
     */
    public static int getConnectedClientsCount() {
        return sessions.size();
    }
}
