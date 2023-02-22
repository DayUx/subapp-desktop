import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import { BrowserRouter, HashRouter, Route, Routes } from "react-router-dom";


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <BrowserRouter>
        <App></App>
    </BrowserRouter>
)