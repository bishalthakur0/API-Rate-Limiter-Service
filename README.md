# API Rate Limiter Service

A backend service that enforces API rate limits per user or IP using a fixed time-window algorithm.  
It includes a live dashboard to monitor request usage, limits, and blocked users in real time.

---

## 🚀 Live Demo
👉 https://bishalthakur0.github.io/API-Rate-Limiter-Service/

---

## 🧭 Dashboard – Initial State

![API Rate Limiter Initial State](2a)

This dashboard view appears before any API requests are made.  
No users are tracked until a request hits the backend API.

---

## 🧭 Dashboard – Active Users & Rate Limit Enforcement

![API Rate Limiter Active Users](1a)

This view shows multiple users making API requests.

- Each card represents a **user or IP**
- Request count is tracked within a fixed time window
- When the limit (**10 requests per minute**) is exceeded:
  - Status changes to **BLOCKED**
  - API starts returning **HTTP 429 (Too Many Requests)**
- Users within the limit are shown as **ALLOWED**

---

## ⚙️ How It Works

1. Client sends a request to `/api/resource`
2. Backend identifies the user (user ID or IP address)
3. Requests are counted within a fixed time window
4. If the request limit is exceeded:
   - The request is blocked
   - HTTP 429 is returned
5. Dashboard updates in real time

---

## 🔗 API Endpoint

### Request
```bash
GET /api/resource
