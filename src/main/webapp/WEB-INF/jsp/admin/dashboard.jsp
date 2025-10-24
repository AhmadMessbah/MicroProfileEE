<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>پنل ادمین - به‌روزرسانی زنده</title>
    <style>
        body {
            font-family: 'Tahoma', Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
            direction: rtl;
        }
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #e74c3c;
        }
        .header h1 {
            color: #2c3e50;
            margin: 0;
        }
        .status-indicator {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .status-dot {
            width: 12px;
            height: 12px;
            border-radius: 50%;
            background-color: #e74c3c;
            animation: pulse 2s infinite;
        }
        .status-dot.connected {
            background-color: #27ae60;
        }
        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        .stat-card h3 {
            margin: 0 0 10px 0;
            font-size: 14px;
            opacity: 0.9;
        }
        .stat-card .number {
            font-size: 32px;
            font-weight: bold;
            margin: 0;
        }
        .tables-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }
        .table-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
        }
        .table-section h3 {
            color: #2c3e50;
            margin-top: 0;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 2px solid #3498db;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 8px;
            text-align: right;
            border-bottom: 1px solid #ddd;
            font-size: 12px;
        }
        th {
            background-color: #3498db;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #e3f2fd;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
            font-size: 12px;
        }
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        .btn-primary:hover {
            background-color: #2980b9;
        }
        .btn-success {
            background-color: #27ae60;
            color: white;
        }
        .btn-success:hover {
            background-color: #229954;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        .live-update {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
            display: none;
        }
        .connection-status {
            position: fixed;
            top: 20px;
            left: 20px;
            background: rgba(0,0,0,0.8);
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 12px;
            z-index: 1000;
        }
        @media (max-width: 768px) {
            .tables-container {
                grid-template-columns: 1fr;
            }
            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }
    </style>
</head>
<body>
    <div class="connection-status" id="connectionStatus">
        <div class="status-indicator">
            <div class="status-dot" id="statusDot"></div>
            <span id="statusText">در حال اتصال...</span>
        </div>
    </div>

    <div class="container">
        <div class="header">
            <h1>پنل ادمین - به‌روزرسانی زنده</h1>
            <div>
                <a href="${pageContext.request.contextPath}/person/" class="btn btn-primary">مدیریت اشخاص</a>
                <a href="${pageContext.request.contextPath}/simcard/" class="btn btn-success">مدیریت سیم‌کارت‌ها</a>
            </div>
        </div>

        <div class="live-update" id="liveUpdate">
            <strong>به‌روزرسانی زنده:</strong> <span id="updateMessage"></span>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="stats-grid">
            <div class="stat-card">
                <h3>تعداد اشخاص</h3>
                <p class="number" id="personCount">${personCount}</p>
            </div>
            <div class="stat-card">
                <h3>تعداد سیم‌کارت‌ها</h3>
                <p class="number" id="simCardCount">${simCardCount}</p>
            </div>
            <div class="stat-card">
                <h3>کاربران متصل</h3>
                <p class="number" id="connectedClients">${connectedClients}</p>
            </div>
        </div>

        <div class="tables-container">
            <div class="table-section">
                <h3>لیست اشخاص</h3>
                <c:choose>
                    <c:when test="${not empty persons}">
                        <table>
                            <thead>
                                <tr>
                                    <th>شناسه</th>
                                    <th>نام</th>
                                    <th>نام خانوادگی</th>
                                    <th>ایمیل</th>
                                </tr>
                            </thead>
                            <tbody id="personsTable">
                                <c:forEach var="person" items="${persons}">
                                    <tr>
                                        <td>${person.id}</td>
                                        <td>${person.firstName}</td>
                                        <td>${person.lastName}</td>
                                        <td>${person.email}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; color: #7f8c8d;">هیچ شخصی ثبت نشده است</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="table-section">
                <h3>لیست سیم‌کارت‌ها</h3>
                <c:choose>
                    <c:when test="${not empty simCards}">
                        <table>
                            <thead>
                                <tr>
                                    <th>شناسه</th>
                                    <th>شماره تلفن</th>
                                    <th>اپراتور</th>
                                    <th>وضعیت</th>
                                </tr>
                            </thead>
                            <tbody id="simCardsTable">
                                <c:forEach var="simCard" items="${simCards}">
                                    <tr>
                                        <td>${simCard.id}</td>
                                        <td>${simCard.phoneNumber}</td>
                                        <td>${simCard.operator}</td>
                                        <td>${simCard.status}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; color: #7f8c8d;">هیچ سیم‌کارتی ثبت نشده است</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script>
        let websocket;
        let reconnectInterval;
        
        function connectWebSocket() {
            const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
            const wsUrl = protocol + '//' + window.location.host + '${pageContext.request.contextPath}/admin/websocket';
            
            websocket = new WebSocket(wsUrl);
            
            websocket.onopen = function(event) {
                console.log('WebSocket connected');
                updateConnectionStatus(true);
                clearInterval(reconnectInterval);
            };
            
            websocket.onmessage = function(event) {
                const data = JSON.parse(event.data);
                handleWebSocketMessage(data);
            };
            
            websocket.onclose = function(event) {
                console.log('WebSocket disconnected');
                updateConnectionStatus(false);
                // Attempt to reconnect every 5 seconds
                reconnectInterval = setInterval(connectWebSocket, 5000);
            };
            
            websocket.onerror = function(error) {
                console.error('WebSocket error:', error);
                updateConnectionStatus(false);
            };
        }
        
        function updateConnectionStatus(connected) {
            const statusDot = document.getElementById('statusDot');
            const statusText = document.getElementById('statusText');
            
            if (connected) {
                statusDot.classList.add('connected');
                statusText.textContent = 'متصل';
            } else {
                statusDot.classList.remove('connected');
                statusText.textContent = 'قطع شده';
            }
        }
        
        function handleWebSocketMessage(data) {
            console.log('Received message:', data);
            
            switch(data.type) {
                case 'welcome':
                    showLiveUpdate('اتصال برقرار شد');
                    break;
                case 'person_update':
                    showLiveUpdate('به‌روزرسانی اطلاعات شخص: ' + data.action);
                    // You can update the persons table here if needed
                    break;
                case 'simcard_update':
                    showLiveUpdate('به‌روزرسانی اطلاعات سیم‌کارت: ' + data.action);
                    // You can update the simcards table here if needed
                    break;
                case 'echo':
                    showLiveUpdate('پیام دریافتی: ' + data.message);
                    break;
            }
        }
        
        function showLiveUpdate(message) {
            const liveUpdate = document.getElementById('liveUpdate');
            const updateMessage = document.getElementById('updateMessage');
            
            updateMessage.textContent = message;
            liveUpdate.style.display = 'block';
            
            // Hide after 3 seconds
            setTimeout(() => {
                liveUpdate.style.display = 'none';
            }, 3000);
        }
        
        // Connect when page loads
        window.addEventListener('load', connectWebSocket);
        
        // Clean up on page unload
        window.addEventListener('beforeunload', function() {
            if (websocket) {
                websocket.close();
            }
            clearInterval(reconnectInterval);
        });
    </script>
</body>
</html>
