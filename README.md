# API Rate Limiter Service

A backend service that enforces API rate limits per user or IP using a fixed time-window algorithm.  
It includes a live dashboard to monitor request usage, limits, and blocked users in real time.

---

## 🚀 Live Demo
👉 https://bishalthakur0.github.io/API-Rate-Limiter-Service/

---

## 🧭 Dashboard – Initial State

![API Rate Limiter Initial State](https://raw.githubusercontent.com/bishalthakur0/API-Rate-Limiter-Service/f802e665cf9ccad56cc79a22f5791498e5d98019/2a.png)

This view shows the dashboard before any API requests are made.  
No users are tracked until a request hits the backend API.

---

## 🧭 Dashboard – Active Users & Rate Limit Enforcement

![API Rate Limiter Active Users](https://raw.githubusercontent.com/bishalthakur0/API-Rate-Limiter-Service/f802e665cf9ccad56cc79a22f5791498e5d98019/1a.png)

This view shows multiple users making API requests.

- Each card represents a **user or IP**
- Requests are tracked per fixed time window
- Once the limit (**10 requests per minute**) is exceeded:
  - Status changes to **BLOCKED**
  - API returns **HTTP 429 (Too Many Requests)**
- Allowed users are shown as **ALLOWED**

---

## ⚙️ How It Works

1. Client sends a request to `/api/resource`
2. Backend identifies the user (user ID or IP address)
3. Requests are counted within a fixed time window
4. If the limit is exceeded:
   - Request is rejected
   - HTTP 429 is returned
5. Dashboard updates in real time

---

## 🔗 API Endpoint

### Request
```bash
GET /api/resource
