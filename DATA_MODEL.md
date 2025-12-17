# Data Structures and Models

## Rate Limit Entry
This structure represents the state of a user's rate limit bucket.

### Schema
```json
{
  "key": "user_123",        // Unique Identifier (User ID or IP)
  "requestCount": 42,       // Number of requests made in current window
  "windowStart": 1700000000,// Timestamp of when the current window started
  "limit": 100              // Max requests allowed per window
}
```

### Storage Strategy
- **In-Memory**: `ConcurrentHashMap<String, RateLimitEntry>`
- **Redis**: Key-Value pair with TTL (Time To Live).
  - Key: `rate_limit:{userId}`
  - Value: `requestCount`
  - TTL: Remaining time in the window

## API Usage Stats (For Dashboard)
Response model for `/api/usage`.

```json
[
  {
    "userId": "user_123",
    "requests": 42,
    "limit": 100,
    "remaining": 58,
    "status": "ALLOWED" // or "BLOCKED"
  },
  {
    "userId": "user_456",
    "requests": 101,
    "limit": 100,
    "remaining": 0,
    "status": "BLOCKED"
  }
]
```
