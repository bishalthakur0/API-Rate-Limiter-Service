import React, { useState, useEffect } from 'react';

const Dashboard = () => {
    const [stats, setStats] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchStats = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/usage');
            if (response.ok) {
                const data = await response.json();
                setStats(data);
            }
        } catch (error) {
            console.error('Error fetching stats:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchStats();
        const interval = setInterval(fetchStats, 2000); // Poll every 2 seconds
        return () => clearInterval(interval);
    }, []);

    if (loading && stats.length === 0) {
        return <div style={{ color: '#94a3b8' }}>Loading metrics...</div>;
    }

    if (stats.length === 0) {
        return <div style={{ color: '#94a3b8' }}>No active API users yet. Make a request to /api/resource!</div>;
    }

    return (
        <div className="dashboard-container">
            <div className="card-grid">
                {stats.map((entry) => {
                    // Calculate remaining and status manually if not in strict model, 
                    // but we have raw data: key, requestCount, limit.
                    const isBlocked = entry.requestCount > entry.limit; // Simple check based on stored vals
                    // Actually, if using the "allow past limit" logic, count > limit = blocked.

                    return (
                        <div key={entry.key} className="card">
                            <h3>{entry.key}</h3>
                            <div className="stat-row">
                                <span>Requests</span>
                                <span className="stat-val">{entry.requestCount}</span>
                            </div>
                            <div className="stat-row">
                                <span>Limit</span>
                                <span className="stat-val">{entry.limit} / min</span>
                            </div>
                            <div className="stat-row">
                                <span>Status</span>
                                <span className={`status-badge ${isBlocked ? 'status-blocked' : 'status-allowed'}`}>
                                    {isBlocked ? 'BLOCKED' : 'ALLOWED'}
                                </span>
                            </div>
                            <div className="stat-row">
                                <span>Window Start</span>
                                <span className="stat-val">{new Date(entry.windowStart).toLocaleTimeString()}</span>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default Dashboard;
