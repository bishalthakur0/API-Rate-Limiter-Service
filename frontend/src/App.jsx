import { useState, useEffect } from 'react'
import './App.css'
import Dashboard from './components/Dashboard'

function App() {
  return (
    <>
      <h1 className="header-title">API Rate Limiter</h1>
      <Dashboard />
    </>
  )
}

export default App
