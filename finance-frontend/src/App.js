import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  // --- CONFIGURATION ---
  // If your backend uses "http://localhost:8080/api/predict", change this to 'http://localhost:8080/api'
  const API_BASE_URL = 'http://localhost:8080/api'; 
  // ---------------------

  // State Variables
  const [transactions, setTransactions] = useState([]);
  const [prediction, setPrediction] = useState(0);
  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState('');

  // --- HELPER FUNCTIONS ---

  // 1. Fetch the AI Prediction
  const fetchPrediction = () => {
    fetch(`${API_BASE_URL}/predict`)
      .then(res => res.json())
      .then(data => {
        console.log("Prediction received:", data); // Debug log
        setPrediction(data);
      })
      .catch(err => console.error("Error fetching prediction:", err));
  };

  // 2. Fetch the Transaction List
  const fetchTransactions = () => {
    fetch(`${API_BASE_URL}/transactions`)
      .then(res => res.json())
      .then(data => setTransactions(data))
      .catch(err => console.error("Error fetching transactions:", err));
  };

  // --- USE EFFECT (Runs once on load) ---
  useEffect(() => {
    fetchTransactions();
    fetchPrediction();
  }, []);

  // --- HANDLER (Runs when you click Add) ---
  const handleAddTransaction = (e) => {
    e.preventDefault();

    if (!description || !amount) {
      alert("Please enter both description and amount.");
      return;
    }

    const newTransaction = {
      description: description,
      amount: parseFloat(amount)
    };

    // 1. Send to Backend
    fetch(`${API_BASE_URL}/add`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newTransaction)
    })
    .then(res => res.json())
    .then(savedTransaction => {
      console.log("Transaction added:", savedTransaction);
      
      // 2. Update List immediately
      setTransactions([...transactions, savedTransaction]);

      // 3. THIS IS KEY: Force AI to re-calculate immediately
      fetchPrediction();

      // 4. Clear inputs
      setDescription('');
      setAmount('');
    })
    .catch(err => console.error("Error adding transaction:", err));
  };

  // --- THE UI (What you see) ---
  return (
    <div className="App" style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>💰 AI Finance Tracker</h1>

      {/* THE PREDICTION BOX */}
      <div style={{ 
          background: '#e0f7fa', 
          padding: '15px', 
          borderRadius: '8px', 
          marginBottom: '20px',
          border: '2px solid #006064'
        }}>
        <h2>AI Predicted Spending</h2>
        {/* Display prediction (fixed to 2 decimal places) */}
        <h1 style={{ color: '#006064' }}>${prediction.toFixed(2)}</h1>
        <p>Based on your recent spending habits</p>
      </div>

      {/* INPUT FORM */}
      <div style={{ marginBottom: '20px' }}>
        <input
          type="text"
          placeholder="Description (e.g., Rent)"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          style={{ padding: '10px', marginRight: '10px' }}
        />
        <input
          type="number"
          placeholder="Amount (e.g., 500)"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          style={{ padding: '10px', marginRight: '10px' }}
        />
        <button 
          onClick={handleAddTransaction}
          style={{ padding: '10px 20px', background: '#2196F3', color: 'white', border: 'none', cursor: 'pointer' }}
        >
          Add Transaction
        </button>
      </div>

      {/* TRANSACTION LIST */}
      <h3>Recent Transactions</h3>
      <ul style={{ listStyleType: 'none', padding: 0 }}>
        {transactions.map((t, index) => (
          <li key={index} style={{ background: '#f5f5f5', margin: '5px 0', padding: '10px', borderLeft: '5px solid #2196F3' }}>
            {t.description}: <strong>${t.amount}</strong>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;