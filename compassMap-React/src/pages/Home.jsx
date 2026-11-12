
import { useState } from 'react';
import CompassMap from './Answers.jsx';
import OpenLibrary from './Questions.jsx';
import heroImg from "../assets/hero.png";
import Answers from "./Answers.jsx";
import Questions from "./Questions.jsx";


export default function Home () {
    const [activeView, setActiveView] = useState('questions');
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
                        className={`view-button${activeView === "questions" ? " active" : ""}`}
                        aria-pressed={activeView === "questions"}
                        onClick={() => setActiveView("questions")}
                    >
                        Questions
                    </button>
                    <button
                        type="button"
                        className={`view-button${activeView === "answers" ? " active" : ""}`}
                        aria-pressed={activeView === "answers"}
                        onClick={() => setActiveView("answers")}
                    >
                        Answers
                    </button>
                </nav>
                {activeView === "questions" ? <Questions /> : <Answers />}
            </main>

        </section>
    );
}