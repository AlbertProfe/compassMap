import { useState } from 'react'
import heroImg from './assets/hero.png'
import './App.css'

export default function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <section id="center">
        <div className="hero">
          <img src={heroImg} className="base" width="170" height="179" alt="" />
        </div>
        <div>
          <h1>CompassMap React</h1>
          <p>
            This is a React project bootstrapped
            with Vite to render the Spring Boot CompassMap application.
            The project is configured to use <b>React 18</b> and <b>Vite</b>
            for fast development and optimized builds.
          </p>
        </div>
        <button
          type="button"
          className="counter"
          onClick={() => setCount((count) => count + 1)}
        >
          Count is {count}
        </button>
      </section>

      
    </>
  )
}

