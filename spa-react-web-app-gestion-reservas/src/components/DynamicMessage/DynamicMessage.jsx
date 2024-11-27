import React, { useState, useEffect } from "react"
import "./DynamicMessage.css"

const DynamicMessage = () => {
  const messages = [
    "reserva tu vuelo hoy.",
    "con los mejores precios.",
    "está a un clic de distancia.",
  ]

  const [currentMessage, setCurrentMessage] = useState(0)
  const [key, setKey] = useState(0)

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentMessage((prev) => (prev + 1) % messages.length)
      setKey((prev) => prev + 1)
    }, 5000)
    return () => clearInterval(interval)
  }, [])

  return (
    <div className="typewriter-content">
      <h1 className="typewriter-fixed">Explora el mundo,</h1>
      <h1 key={key} className="typewriter">
        {messages[currentMessage]}
      </h1>
    </div>
  )
}

export default DynamicMessage
