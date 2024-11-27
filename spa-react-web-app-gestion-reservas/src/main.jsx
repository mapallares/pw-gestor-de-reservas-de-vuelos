import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './styles/global.css'
import AppLayout from './layouts/AppLayout.jsx'
import SignIn from './pages/SignIn/SignIn.jsx'
import SignUp from './pages/SignUp/SignUp.jsx'
import AdminLayout from './layouts/AdminLayout.jsx'
import AdminDashboard from './pages/Admin/Dashboard.jsx'
import AdminAeropuertos from './pages/Admin/Aeropuertos.jsx'
import AdminAerolineas from './pages/Admin/Aerolineas.jsx'
import AdminVuelos from './pages/Admin/Vuelos.jsx'
import AdminReservas from './pages/Admin/Reservas.jsx'
import AdminPasajeros from './pages/Admin/Pasajeros.jsx'
import AdminClientes from './pages/Admin/Clientes.jsx'
import NotFound from './pages/NotFound/NotFound.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<AppLayout />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminDashboard />} />
          <Route path="aeropuertos" element={<AdminAeropuertos />} />
          <Route path="aerolineas" element={<AdminAerolineas />} />
          <Route path="vuelos" element={<AdminVuelos />} />
          <Route path="reservas" element={<AdminReservas />} />
          <Route path="pasajeros" element={<AdminPasajeros />} />
          <Route path="clientes" element={<AdminClientes />} />
        </Route>

        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
)
