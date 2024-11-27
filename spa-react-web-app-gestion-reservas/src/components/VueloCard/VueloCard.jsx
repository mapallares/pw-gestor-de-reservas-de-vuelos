function VueloCard({vuelo}) {
    return <div className="pw-content-vuelos-vuelo">
    <div className="pw-content-vuelos-vuelo-top">
      <div className="pw-content-vuelos-vuelo-top-origen">
        <h2>{vuelo.origen.pais.substring(0,3).toUpperCase()}</h2>
        <p><i>{vuelo.origen.ciudad}</i></p>
        <p><b>{vuelo.origen.pais}</b></p>
      </div>
      <div className="pw-content-vuelos-vuelo-top-divider">
        <div className="pw-content-vuelos-vuelo-top-divider-plane">
          <div className="pw-content-vuelos-vuelo-divider-line"></div>
          <span className="material-symbols-outlined">flight</span>
        </div>
        <div className="pw-content-vuelos-vuelo-top-divider-duracion">
          <span className="material-symbols-outlined">schedule</span>
          <span className="pw-content-vuelos-vuelo-top-divider-duracion-time">{vuelo.duracion} horas</span>
        </div>
      </div>
      <div className="pw-content-vuelos-vuelo-top-destino">
        <h2>{vuelo.destino.pais.substring(0,3).toUpperCase()}</h2>
        <p><i>{vuelo.destino.ciudad}</i></p>
        <p><b>{vuelo.destino.pais}</b></p>
      </div>
    </div>
    <div className="pw-content-vuelos-vuelo-divider">
      <div className="pw-content-vuelos-vuelo-divider-line"></div>
    </div>
    <div className="pw-content-vuelos-vuelo-bottom">
      <div className="pw-content-vuelos-vuelo-bottom-detail">
        <strong>Fecha</strong>
        <p>{vuelo.fechaSalida}</p>
      </div>
      <div className="pw-content-vuelos-vuelo-bottom-detail">
        <strong>Hora</strong>
        <p>{vuelo.horaSalida}</p>
      </div>
      <div className="pw-content-vuelos-vuelo-bottom-detail">
        <strong>Aerolínea</strong>
        <p>{vuelo.aerolinea.nombre}</p>
      </div>
    </div>
  </div>
}

export default VueloCard