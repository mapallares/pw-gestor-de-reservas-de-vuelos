import { useEffect, useState } from 'react'
import './AdminLayout.css'
import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import Logo from '../components/Logo/Logo.jsx'
import Auth from '../scripts/Auth.mjs'

const AdminLayout = () => {
  const navigate = useNavigate()
  const [session, setSession] = useState(null)
  const [sidebar, setSidebar] = useState(JSON.parse(window.localStorage.getItem('sidebar')) || false)

  useEffect(() => {
    
    Auth.session(session => {
      if(!session) navigate('/signin')
      if(!session.roles.includes('ROLE_ADMIN')) navigate('/')
      setSession(session)
    })

  }, [])

  useEffect(() => {
    localStorage.setItem('sidebar', sidebar)
  }, [sidebar])

  return (session && 
    <>
      <div className="pw-admin-layout">
      <aside className={sidebar ? "pw-admin-layout-sidebar" : "pw-admin-layout-sidebar disabled"}>
        <div className="pw-admin-layout-sidebar-brand">
            <div className="pw-admin-layout-sidebar-brand-icon">
                <Logo color="white" size="s"></Logo>
            </div>
            <div className="pw-admin-layout-sidebar-brand-name">| Admin</div>
        </div>
        <div className="pw-admin-layout-sidebar-session">
          <div className="pw-admin-layout-sidebar-session-avatar">
            <div className="pw-admin-layout-sidebar-session-avatar-char">
              {session.cliente.nombre[0]}
            </div>
          </div>
          <div className="pw-admin-layout-sidebar-session-user">
              <strong>{session.cliente.nombre + ' ' + session.cliente.apellido}</strong>
              <p>{session.cliente.email}</p>
          </div>
        </div>
        <nav className="pw-admin-layout-nav">
          <NavLink
            to="/admin"
            end
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">dashboard</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Dashboard</div>
          </NavLink>
          <NavLink
            to="/admin/aeropuertos"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">connecting_airports</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Aeropuertos</div>
          </NavLink>
          <NavLink
            to="/admin/aerolineas"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">airlines</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Aerolíneas</div>
          </NavLink>
          <NavLink
            to="/admin/vuelos"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">airplane_ticket</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Vuelos</div>
          </NavLink>
          <NavLink
            to="/admin/reservas"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">bookmark</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Reservas</div>
          </NavLink>
          <NavLink
            to="/admin/pasajeros"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">airline_seat_recline_extra</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Pasajeros</div>
          </NavLink>
          <NavLink
            to="/admin/clientes"
            className={({ isActive }) =>
              isActive ? "pw-admin-layout-sidebar-nav-btn active" : "pw-admin-layout-sidebar-nav-btn"
            }
          >
            <div className="pw-admin-layout-sidebar-nav-btn-icon">
              <span className="material-symbols-outlined">group</span>
            </div>
            <div className="pw-admin-layout-sidebar-nav-btn-text">Clientes</div>
          </NavLink>
        </nav>

        <button className="pw-admin-layout-signout" onClick={() => Auth.signOut()}>
          <span className="material-symbols-outlined">logout</span>
        </button>
        <button className="pw-admin-layout-disabled" onClick={() => setSidebar(!sidebar)}>
          <span className="material-symbols-outlined">dock_to_right</span>
        </button>
      </aside>
      <main className="pw-admin-layout-main">
        <div className="pw-admin-layout-main-container">
          <Outlet />
        </div>
      </main>
    </div>
    </>
  )
}

export default AdminLayout