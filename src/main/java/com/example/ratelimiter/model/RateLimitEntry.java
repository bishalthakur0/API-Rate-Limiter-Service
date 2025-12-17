package com.example.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitEntry {
    private String key;
    private int requestCount;
    private long windowStart;
    private int limit;
}
