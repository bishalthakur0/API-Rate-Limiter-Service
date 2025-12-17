# API Rate Limiter Service

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.x-blue.svg)](https://reactjs.org/)

> 🚀 **Live Demo**: [https://bishalthakur0.github.io/API-Rate-Limiter-Service/](https://bishalthakur0.github.io/API-Rate-Limiter-Service/)

## 1. Objective
Build a scalable API rate-limiting service that controls request traffic per user/IP to prevent abuse, protect backend resources, and ensure fair usage. The system includes a simple frontend dashboard to visualize rate-limit behavior in real time.

## 2. Problem Statement
High-traffic APIs can be abused or overloaded by excessive requests, leading to:
- System instability
- Performance degradation
- Unfair resource usage

This service enforces request limits and provides visibility into API usage.

## 3. Goals
- Enforce rate limits per user/IP
- Support configurable limits (e.g., 100 requests/min)
- Return proper HTTP responses on limit breach
- Display live usage via frontend dashboard
- Demonstrate Redis / ElastiCache-ready design

## 4. Non-Goals
- Advanced authentication (OAuth, SSO)
- Distributed global rate limiting
- Billing or subscription enforcement

## 5. Target Users
- Backend engineers
- API gateway systems
- Microservices protecting public endpoints
- Recruiters reviewing system design skills

## 6. System Architecture
```
Client
  ↓
Frontend Dashboard (React / HTML)
  ↓
Spring Boot API
  ↓
Rate Limiter Filter
  ↓
Cache (In-Memory / Redis)
```

## 7. Functional Requirements

### 7.1 Rate Limiting Logic
- Identify clients using User ID or IP address
- Apply fixed window or sliding window algorithm
- Configurable request limit and time window
- Thread-safe implementation

### 7.2 Request Handling
- Allow requests within limit → **200 OK**
- Block excess requests → **429 Too Many Requests**
- Include rate-limit headers:
  - `X-RateLimit-Limit`
  - `X-RateLimit-Remaining`

### 7.3 Frontend Dashboard
- **Purpose**: Make the system visually understandable to recruiters
- **Dashboard shows**:
  - Requests per user/IP
  - Remaining request count
  - Rate-limit status (Allowed / Blocked)
  - Auto-refresh every few seconds

### 7.4 Admin Configuration (Basic)
- Configure request limit (e.g., 100/min)
- Reset counters manually
- View top API consumers

## 8. API Specifications

### 8.1 Protected API
**GET** `/api/resource`

**Headers**
- `X-USER-ID`: `user123`

**Responses**
- `200 OK` → request allowed
- `429 Too Many Requests` → rate limit exceeded

### 8.2 Usage Metrics API
**GET** `/api/usage`
- Returns usage stats for frontend dashboard.

## 9. Data Model
| Field | Type | Description |
|-------|------|-------------|
| key (user/IP) | String | Unique identifier |
| requestCount | Integer | Current count in window |
| windowStart | Timestamp | Start execution time of window |
| limit | Integer | Max allowed requests |

## 10. Non-Functional Requirements
- **Performance**: Constant-time request checks (O(1)), Minimal latency overhead
- **Scalability**: Cache-based design, Redis-ready architecture
- **Reliability**: Thread-safe counters, Graceful degradation on cache failure
- **Usability**: Clean UI, Minimal setup required

## 11. Tech Stack
- **Backend**: Java 17, Spring Boot 3.x, In-Memory Cache (Redis-ready), REST APIs
- **Frontend**: React 19, Vite, Modern CSS
- **Deployment**: GitHub Pages (Frontend), Local/Cloud (Backend)

## 12. Getting Started

### Prerequisites
- **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **Node.js 18+** and npm - [Download](https://nodejs.org/)
- **Git** - [Download](https://git-scm.com/)

### Backend Setup

```bash
# Clone the repository
git clone https://github.com/bishalthakur0/API-Rate-Limiter-Service.git
cd API-Rate-Limiter-Service

# Build and run the backend
mvn clean install
mvn spring-boot:run
```

Backend will be available at: `http://localhost:8080`

### Frontend Setup

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

Frontend will be available at: `http://localhost:5173`

### Testing the Rate Limiter

```bash
# Test API endpoint (PowerShell)
$headers = @{'X-USER-ID'='test_user'}
for ($i=1; $i -le 12; $i++) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/api/resource" -Headers $headers -Method GET
        Write-Host "Request $i : Status $($response.StatusCode) - $($response.Content)"
    } catch {
        Write-Host "Request $i : Status $($_.Exception.Response.StatusCode.value__) - Rate Limited"
    }
    Start-Sleep -Milliseconds 500
}
```

## 13. Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed deployment instructions.

**Quick Deploy Frontend to GitHub Pages:**
```bash
cd frontend
npm run deploy
```

## 14. Project Structure

```
API-Rate-Limiter-Service/
├── src/                      # Spring Boot backend source
│   └── main/
│       ├── java/            # Java source files
│       └── resources/       # Application properties
├── frontend/                # React frontend
│   ├── src/                # React components
│   ├── public/             # Static assets
│   └── package.json        # Frontend dependencies
├── .github/
│   └── workflows/          # GitHub Actions CI/CD
├── pom.xml                 # Maven configuration
├── README.md               # This file
├── DEPLOYMENT.md           # Deployment guide
└── DATA_MODEL.md           # Data model documentation
```

## 15. Success Metrics
- ✅ Correct rate-limit enforcement
- ✅ Accurate real-time usage display
- ✅ Clear visual demonstration for recruiters
- ✅ Clean, explainable architecture

## 16. Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## 17. License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 18. Author

**Bishal Thakur**
- GitHub: [@bishalthakur0](https://github.com/bishalthakur0)

## 19. Acknowledgments

- Spring Boot for the robust backend framework
- React and Vite for the modern frontend tooling
- GitHub Pages for free static hosting
