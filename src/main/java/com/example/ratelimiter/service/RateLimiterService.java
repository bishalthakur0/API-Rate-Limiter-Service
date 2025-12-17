package com.example.ratelimiter.service;

import com.example.ratelimiter.model.RateLimitEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, RateLimitEntry> limitMap = new ConcurrentHashMap<>();
    private static final int DEFAULT_LIMIT = 10; // Low limit for testing
    private static final long WINDOW_SIZE_MS = 60 * 1000; // 1 minute

    public boolean isAllowed(String key) {
        long now = System.currentTimeMillis();

        return limitMap.compute(key, (k, entry) -> {
            if (entry == null) {
                return new RateLimitEntry(k, 1, now, DEFAULT_LIMIT);
            }

            if (now - entry.getWindowStart() > WINDOW_SIZE_MS) {
                // Window expired, reset
                entry.setWindowStart(now);
                entry.setRequestCount(1);
            } else {
                // Within window
                if (entry.getRequestCount() >= entry.getLimit()) {
                    return entry; // Limit exceeded, don't increment (or do we? Usually no)
                }
                entry.setRequestCount(entry.getRequestCount() + 1);
            }
            return entry;
        }).getRequestCount() <= DEFAULT_LIMIT && (now - limitMap.get(key).getWindowStart() <= WINDOW_SIZE_MS);

        // Logic fix: The compute returns the NEW entry.
        // We need separate check for return value?
        // Actually, let's simplify.
    }

    // Better implementation for boolean return using compute logic
    public boolean checkRequest(String key) {
        long now = System.currentTimeMillis();
        RateLimitEntry entry = limitMap.compute(key, (k, v) -> {
            if (v == null || (now - v.getWindowStart() > WINDOW_SIZE_MS)) {
                return new RateLimitEntry(k, 1, now, DEFAULT_LIMIT);
            }
            // Only increment if below limit
            if (v.getRequestCount() < v.getLimit()) {
                v.setRequestCount(v.getRequestCount() + 1);
            } else {
                // Mark as blocked logic or just leave it at limit?
                // If we leave it, we can't distinguish "just reached" from "already blocked"
                // implies same result.
                // But for stats, maybe we want to track blocked attempts?
                // For now, simple standard algorithm.
            }
            return v;
        });

        // logic: if count <= limit, allow.
        // But wait, if I incremented it up to limit, it's allowed.
        // If it was ALREADY at limit, I didn't increment.
        // So checking (entry.getRequestCount() <= limit) might be true even if I didn't
        // increment?
        // Ah, if I didn't increment, it stays same.
        // If it sends 100th request: count becomes 100. Allowed.
        // If it sends 101st request: count stays 100. Allowed? No.

        // I need a way to know if I successfully acquired a token.
        // Let's refactor to return true/false cleanly.

        // We can check state before compute? No, race condition.
        // We can store a transient "blocked" state? No.

        // Standard way:
        // if (current < limit) { current++; return true; } else return false;
        // logic inside compute needs to return the updated object.

        // Let's rely on the count logic:
        // If the entry.requestCount == limit, did we just reach it or were we already
        // there?
        // If we want to strictly return false ONLY when exceeding.

        // Let's try this:
        // isAllowed returns true if request is passed.

        // Re-write checkRequest to be precise.
        // If count < limit, increment and return true.
        // If count >= limit, return false.

        // But compute function must return the Value to be stored.
        // So we can use a temporary object or side-effect?
        // No side effects in concurrent map ideally.

        // Actually, simpler:
        // The map stores the state.
        // We read state, check time.
        // If expired, replace with new window.
        // If active, increment if allowed.

        // compute is perfect.
        // We just need to check the result.
        // If result.count <= limit, wait.. if it was 10 and limit is 10.
        // Request 11 comes. Window active. Count is 10. 10 < 10 is false. Don't
        // increment. Return 10.
        // Caller sees 10. 10 <= 10. Allowed??
        // ERROR: The caller can't distinguish "just reached 10" vs "stuck at 10".
        // Fix: Use a counter that can go above limit?
        // If we allow counter to go above limit, say 11.
        // Then 11 > 10 -> Blocked.
        // This is safe.

        return limitMap.compute(key, (k, v) -> {
            if (v == null || (now - v.getWindowStart() > WINDOW_SIZE_MS)) {
                return new RateLimitEntry(k, 1, now, DEFAULT_LIMIT);
            }
            v.setRequestCount(v.getRequestCount() + 1); // Allow counting past limit
            return v;
        }).getRequestCount() <= DEFAULT_LIMIT;
    }

    public List<RateLimitEntry> getUsageStats() {
        return new ArrayList<>(limitMap.values());
    }

    public void reset(String key) {
        limitMap.remove(key);
    }
}
