
import { useState } from 'react';
import CompassMap from './CompassMap.jsx';
import OpenLibrary from './OpenLibrary.jsx';
import heroImg from "../assets/hero.png";

export default function Home () {
    const [activeView, setActiveView] = useState('openlibrary');
    const [count, setCount] = useState(0);


    return (
        <section>
            <h2>Home</h2>

            <main className="app">
                <h1>CompassMap React</h1>
                <div className="hero">
                    <img src={heroImg} className="base" width="170" height="179" alt="" />
                </div>
                <p>
                    This is a React project bootstrapped with Vite to render the Spring Boot
                    CompassMap application.
                </p>
                <p>
                    The project is configured to use <b>React 18</b> and <b>Vite</b> for
                    fast development and optimized builds.
                </p>
                <hr />
                <h2>Hook: useState</h2>
                <div className="card">
                    <button
                        type="button"
                        className="counter"
                        onClick={() => setCount((currentCount) => currentCount + 1)}
                    >
                        Count is {count}
                    </button>
                </div>
                <hr />
                <nav className="view-switcher" aria-label="Choose a view">
                    <button
                        type="button"
                        className={`view-button${activeView === "openlibrary" ? " active" : ""}`}
                        aria-pressed={activeView === "openlibrary"}
                        onClick={() => setActiveView("openlibrary")}
                    >
                        OpenLibrary
                    </button>
                    <button
                        type="button"
                        className={`view-button${activeView === "compassmap" ? " active" : ""}`}
                        aria-pressed={activeView === "compassmap"}
                        onClick={() => setActiveView("compassmap")}
                    >
                        CompassMap
                    </button>
                </nav>
                {activeView === "openlibrary" ? <OpenLibrary /> : <CompassMap />}
            </main>

        </section>
    );
}