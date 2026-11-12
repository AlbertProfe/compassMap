# CompassMap-React Project Summary v1.1

## Overview

`compassMap-React` is a **React 18** frontend application bootstrapped with **Vite**. It serves as the React-based UI for the Spring Boot CompassMap application. The project uses **axios** for HTTP requests and demonstrates core React patterns including **conditional rendering**, **`.map()` list rendering**, and **`useEffect`** for side effects (API calls).

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

## Component Breakdown

### 1. `App.jsx` — Main Component with Conditional Rendering

**File:** `src/App.jsx`

The `App` component is the root of the application. It imports two child components (`CompassMap` and `OpenLibrary`) and uses **conditional rendering** to toggle between them.

**Key features:**

- **Imports:**
  - `CompassMap` from `./CompassMap.jsx`
  - `OpenLibrary` from `./OpenLibrary.jsx`
  - `useState` hook from React
  - Hero image asset

- **State management:**
  - `activeView` — tracks which view is currently displayed (`'openlibrary'` or `'compassmap'`)
  - `count` — a simple counter for demonstrating `useState`

- **Conditional rendering (line 55):**
  ```jsx
  {activeView === "openlibrary" ? <OpenLibrary /> : <CompassMap />}
  ```
  A ternary operator switches between the two child components based on the `activeView` state.

- **View switcher navigation:** Two buttons allow the user to toggle between the OpenLibrary and CompassMap views. The active button receives an `active` CSS class and `aria-pressed` attribute for accessibility.

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
| **Conditional Rendering** | `App.jsx` | Ternary operator switches between `<OpenLibrary />` and `<CompassMap />` |
| **`.map()` List Rendering** | `CompassMap.jsx` | Renders assessment cards from an array |
| **`.map()` List Rendering** | `OpenLibrary.jsx` | Renders book titles and authors from an array |
| **`useEffect` with API** | `CompassMap.jsx` | Fetches from localhost `/api/v1/assessments/all` |
| **`useEffect` with API** | `OpenLibrary.jsx` | Fetches from external `https://openlibrary.org` |
| **`useState`** | All components | Manages local state for data and loading flags |
| **Loading States** | `CompassMap.jsx`, `OpenLibrary.jsx` | Shows loading messages while data is being fetched |

## Dependencies

- **React 18** — UI library
- **Vite** — Build tool and dev server
- **axios** — HTTP client for API requests
