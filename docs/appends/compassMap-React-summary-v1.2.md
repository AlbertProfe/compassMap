# CompassMap-React Project Summary v1.2

## Overview

`compassMap-React` is a **React 19** frontend application bootstrapped with **Vite**. It serves as the React-based UI for the Spring Boot CompassMap application. The project uses **axios** for HTTP requests, **react-router-dom** for client-side routing, and demonstrates core React patterns including **`.map()` list rendering** and **`useEffect`** for side effects (API calls).

## Project Structure

```
compassMap-React/
├── src/
│   ├── App.jsx            # Main component with conditional rendering
│   ├── CompassMap.jsx     # Fetches assessments from localhost API
│   ├── OpenLibrary.jsx    # Fetches books from OpenLibrary API
│   ├── main.jsx           # Entry point
│   ├── App.css            # App-level styles
│   ├── index.css          # Global styles
│   └── assets/
│       └── hero.png       # Hero image
├── public/
│   ├── favicon.svg
│   └── icons.svg
├── index.html
├── vite.config.js
├── package.json
└── README.md
```

## Navigation

**File:** `src/App.jsx`

The application uses **react-router-dom** (`^7.18.5`) for client-side routing. `App.jsx` wraps the entire application in `<BrowserRouter>` and defines a route hierarchy using `<Routes>` and `<Route>` components.

### Imports

```jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import OpenLibrary from "./pages/OpenLibrary.jsx";
import CompassMap from "./pages/CompassMap.jsx";
import NoPage from "./pages/NoPage.jsx";
import Layout from "./pages/Layout.jsx";
```

**Key imports:**

- **`BrowserRouter`** — Provides the routing context for the entire application, enabling URL-based navigation without page reloads.
- **`Routes`** — Container that renders the first matching `Route` based on the current URL.
- **`Route`** — Defines individual routes mapping URL paths to components.
- **Page components:** `Home`, `OpenLibrary`, `CompassMap`, `NoPage`, and `Layout` are imported from the `./pages/` directory.

### Route Structure

```jsx
<BrowserRouter>
    <Routes>
        <Route path="/" element={<Layout />}>
            <Route index element={<Home />} />
            <Route path="compassmap" element={<CompassMap />} />
            <Route path="openlibrary" element={<OpenLibrary />} />
            <Route path="*" element={<NoPage />} />
        </Route>
    </Routes>
</BrowserRouter>
```

**Defined routes:**

| Path | Component | Description |
|------|-----------|-------------|
| `/` (index) | `<Home />` | Landing/home page, rendered inside `<Layout />` |
| `/compassmap` | `<CompassMap />` | Assessment list view, fetched from localhost backend |
| `/openlibrary` | `<OpenLibrary />` | Book search view, fetched from OpenLibrary API |
| `/*` (catch-all) | `<NoPage />` | 404 page for unmatched routes |

**Nested routing:** All page routes are nested under `<Route path="/" element={<Layout />}>`. The `Layout` component wraps every page, providing shared structure (e.g., navigation header, footer). Child routes render within the `Layout`'s outlet.

---

## Component Breakdown

### 1. `App.jsx` — Root Component with React Router Navigation

**File:** `src/App.jsx`

The `App` component is the root of the application. It imports six page components and configures **react-router-dom** for URL-based navigation instead of conditional rendering.

**Key features:**

- **Imports:**
  - `BrowserRouter`, `Routes`, `Route` from `react-router-dom`
  - `Home` from `./pages/Home.jsx`
  - `OpenLibrary` from `./pages/OpenLibrary.jsx`
  - `CompassMap` from `./pages/CompassMap.jsx`
  - `NoPage` from `./pages/NoPage.jsx`
  - `Layout` from `./pages/Layout.jsx`

- **Routing setup:** The component returns a `<BrowserRouter>` containing a `<Routes>` block with nested `<Route>` elements. Each route maps a URL path to a page component.

- **Layout wrapper:** All routes are nested under a parent `<Route path="/" element={<Layout />}>`, so every page renders inside the shared `Layout` component.

- **Catch-all route:** `<Route path="*" element={<NoPage />} />` handles any unmatched URLs, rendering a 404 page.

---

### 2. `CompassMap.jsx` — Assessments List with Localhost API

**File:** `src/CompassMap.jsx`

This component fetches assessment data from a **local Spring Boot backend** and renders them as a list using `.map()`.

**Key features:**

- **Imports:** `axios` for HTTP requests, `useEffect` and `useState` from React

- **State:**
  - `assessments` — array of assessment objects (initialized as `[]`)
  - `isLoading` — boolean flag for loading state (initialized as `true`)

- **`useEffect` with API call:**
  ```jsx
  useEffect(() => {
    axios.get('/api/v1/assessments/all')
      .then(({ data }) => {
        setAssessments(data);
        setIsLoading(false);
      });
  }, []);
  ```
  Fetches data from the **localhost** endpoint `/api/v1/assessments/all` on component mount (empty dependency array `[]`).

- **`.map()` list rendering:**
  ```jsx
  {assessments.map((assessment) => (
    <li className="assessment-card" key={assessment.id}>
      <h3>{assessment.customer ? `${assessment.customer.firstName} ${assessment.customer.lastName}` : 'No customer'}</h3>
      <p>Profile: {assessment.profile?.name || 'No profile'}</p>
      <p>Questions: {assessment.qtyQuestions}</p>
      <p>Created: {assessment.timestamp ? new Date(assessment.timestamp).toLocaleString() : 'Unknown'}</p>
    </li>
  ))}
  ```
  Iterates over the `assessments` array, rendering each assessment as a list item with customer name, profile, question count, and formatted timestamp.

- **Conditional loading states:** Shows "Loading assessments..." while fetching, "No assessments found." if the array is empty, and the list otherwise.

---

### 3. `OpenLibrary.jsx` — Books List with External API

**File:** `src/OpenLibrary.jsx`

This component fetches book data from the **OpenLibrary public API** and renders them as a list using `.map()`.

**Key features:**

- **Imports:** `axios` for HTTP requests, `useEffect` and `useState` from React

- **State:**
  - `books` — array of book objects (initialized as `[]`)
  - `isLoading` — boolean flag for loading state (initialized as `true`)

- **`useEffect` with API call:**
  ```jsx
  useEffect(() => {
    axios.get('https://openlibrary.org/search.json?q=to+the+lighthouse&limit=100')
      .then(({ data }) => {
        setBooks(data.docs);
        setIsLoading(false);
      });
  }, []);
  ```
  Fetches book search results from the **external** OpenLibrary API (`https://openlibrary.org`) on component mount. The query searches for "to the lighthouse" with a limit of 100 results.

- **`.map()` list rendering:**
  ```jsx
  {books.map((book) => (
    <li key={book.key}>
      <strong>{book.title}</strong>
      {book.author_name?.length
        ? ` by ${book.author_name.join(", ")}`
        : ""}
    </li>
  ))}
  ```
  Iterates over the `books` array, rendering each book's title in bold, followed by the author names joined with commas (e.g., "by Author One, Author Two").

- **Conditional loading state:** Shows "Loading books..." while fetching, then the list once data arrives.

---

## React Patterns Demonstrated

| Pattern | Component | Description |
|---------|-----------|-------------|
| **Client-side Routing** | `App.jsx` | `react-router-dom` with `BrowserRouter`, `Routes`, and nested `Route` components |
| **`.map()` List Rendering** | `CompassMap.jsx` | Renders assessment cards from an array |
| **`.map()` List Rendering** | `OpenLibrary.jsx` | Renders book titles and authors from an array |
| **`useEffect` with API** | `CompassMap.jsx` | Fetches from localhost `/api/v1/assessments/all` |
| **`useEffect` with API** | `OpenLibrary.jsx` | Fetches from external `https://openlibrary.org` |
| **`useState`** | All components | Manages local state for data and loading flags |
| **Loading States** | `CompassMap.jsx`, `OpenLibrary.jsx` | Shows loading messages while data is being fetched |

## Dependencies

- **React 19** (`^19.3.1`) — UI library
- **React DOM** (`^19.3.1`) — DOM bindings
- **Vite** (`^8.3.6`) — Build tool and dev server
- **axios** (`^1.21.0`) — HTTP client for API requests
- **react-router-dom** (`^7.18.5`) — Routing library (available as dev dependency)
