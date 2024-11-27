import Button from '../../components/Button/Button.jsx'
import './NotFound.css'
function NotFound() {

  return (
    <div className="pw-notfound">
        <div className="pw-notfound-card">
            <h1>404</h1>
            <h2>NOT FOUND</h2>
            <br/>
            <Button color="danger" size="l" width="full" radius="full" onClick={() => {window.location = '/'}}>Ir al Inicio</Button>
        </div>
    </div>
  )
}

export default NotFound
