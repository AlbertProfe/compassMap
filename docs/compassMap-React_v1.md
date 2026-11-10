# compassMap-React masterDoc v1

## Summary

This document describes the **frontend** of compassMap. The application is in the `compassMap-React/` directory of the [compassMap repository](https://github.com/AlbertProfe/compassMap), alongside the `Spring Boot` backend.

> The intended frontend guides a learner through the compassMap assessment and presents the resulting profile and learning roadmap. The current `React` implementation is still a `Vite starter/demo`: it renders a counter and fetches book results from Open Library. It is not yet connected to the compassMap backend.


### References

- [React documentation](https://react.dev/)
- [ESLint React plugin (`@eslint-react/eslint-plugin`)](https://www.npmjs.com/package/@eslint-react/eslint-plugin)
- [Vite documentation](https://vite.dev/guide/)
- [Axios documentation](https://axios-http.com/docs/intro)
- [ApartmentPredictor-React](https://github.com/AlbertProfe/ApartmentPredictor-React) — related React frontend reference
- [ApartmentPredictor](https://github.com/AlbertProfe/ApartmentPredictor) — related application/backend reference
- [compassMap backend masterDoc v1](./compassMap_v1.md) / [v2](./compassMap_v2.md)
- [compassMap fullstack README](../README.md)

### Version goal

Build the React frontend for the compassMap learner assessment: provide an interface for answering questions, then display the resulting customer profile and recommended learning roadmap using data from the backend REST API.

The current v1 frontend is a starting point for that goal, not the completed assessment flow. It demonstrates React rendering, a `useState` counter, and an Axios request to Open Library. The assessment screens, backend API integration, and profile/roadmap presentation remain to be implemented.

## Project

The frontend is a separate npm/Vite application in this monorepo. Install dependencies and run its scripts from `compassMap-React/`.

### Commits

- [Initialize the React application with Vite](https://github.com/AlbertProfe/compassMap/commit/10ec997)
- [Add Axios book fetching and display](https://github.com/AlbertProfe/compassMap/commit/9503dee)
- [Frontend commit history](https://github.com/AlbertProfe/compassMap/commits/master/compassMap-React/)

### Project structure

The main application code and configuration are:

```text
compassMap-React/
├── index.html              # HTML document and root mount element
├── package.json            # Dependencies and npm scripts
├── vite.config.js          # Vite and React plugin configuration
├── eslint.config.js        # ESLint configuration
├── public/                 # Static public assets
└── src/
    ├── main.jsx            # React entry point and StrictMode root
    ├── App.jsx             # Current demo, counter, and book list
    ├── App.css             # App-specific styles
    ├── index.css           # Global styles and theme variables
    └── assets/             # Imported application assets
```

## CompassMap

> The frontend is intended to be the user-facing assessment application for the backend's customer, question-and-answer, customer-profile, roadmap, and assessment resources. 

The current demo does not call those resources; it currently displays a counter and a list of books.

### Data Axios

`src/App.jsx` uses Axios inside a React `useEffect` to send a `GET` request to the Open Library search API for *The Lord of the Rings* (up to 100 results). It stores the returned `docs` in component state and renders each book's title and author names. A loading message is shown until the request completes.

This request is a demo and is not compassMap data. No backend base URL or compassMap REST endpoint is currently configured in the frontend. The backend API resources are documented in the [fullstack README](../README.md); connect the assessment UI to those endpoints when implementing the assessment flow.

The following backend API examples document the assessment data contract. They are not yet called by the current React demo. See the [Postman API documentation](https://documenter.getpostman.com/view/7473960/2sBYHSy1U9).

In the examples, `{{base_url}}` is the API base URL, for example `http://localhost:8080/api/v1/`.

**Send an assessment — `POST {{base_url}}assessments/create`**

The customer, profile, and question IDs must refer to existing records. The service generates the assessment and answer IDs, timestamp, and question count.

```json
{
  "customer": { "id": "26256f77-06ea-4c6e-bba3-4754bced74e8" },
  "profile": { "id": "916933e0-70d5-4c52-9aa9-48840a01cc30" },
  "questionsAndAnswers": [
    {
      "question": { "id": "7504a93c-a16c-4438-a140-4ae7dd326631" },
      "answer": { "value": true },
      "scoreContribution": 1.0
    },
    {
      "question": { "id": "e0041c35-8a76-49af-8745-b9d0c128ebec" },
      "answer": { "value": false },
      "scoreContribution": 0.0
    },
    {
      "question": { "id": "813ff44d-9e58-455c-908a-b2cac485c0f5" },
      "answer": { "value": true },
      "scoreContribution": 1.0
    }
  ]
}
```

**GET all assessments — `GET {{base_url}}assessments/all`**

The endpoint returns a JSON array. For example, a response containing one assessment:

```json
[
  {
    "id": "772bbca6-f336-4f70-ad4e-84dff6b3d512",
    "customer": {
      "id": "26256f77-06ea-4c6e-bba3-4754bced74e8",
      "firstName": "Lashunda",
      "lastName": "Jacobs",
      "email": "rob.morar@gmail.com",
      "phone": "381.238.9244 x55211",
      "address": "Suite 901 874 Lucius Canyon, New Fredericachester, SC 13191"
    },
    "timestamp": "2026-11-05T10:00:17.959387592Z",
    "qtyQuestions": 3,
    "profile": {
      "id": "916933e0-70d5-4c52-9aa9-48840a01cc30",
      "codeProfile": "BEGINNER",
      "name": "Beginner",
      "description": "Wants to start learning programming from scratch",
      "goal": "Learn basic programming concepts and build a first project",
      "profileAnswers": {},
      "customer": null,
      "recommendedRoadmaps": []
    }
  }
]
```

The response omits `questionsAndAnswers`; retrieve those separately with `GET {{base_url}}assessments/questionsAndAnswers?id=<assessment-id>`.

## Components

The current component tree is deliberately small:

- `main.jsx` mounts the app using React DOM's `createRoot` and wraps it in `StrictMode`.
- `App.jsx` is the root function component. It owns the counter, book results, and loading state, and renders the page content.
- `App.css` contains styles used by the app; `index.css` defines global typography, colors, layout, and light/dark theme values.

### Tree

```text
main.jsx
└── StrictMode
    └── App
        ├── Hero image
        ├── Introductory text
        ├── Counter (useState)
        └── Books
            └── Book result (one per Open Library result)
```

## npm and install

Prerequisites: install Node.js and npm. From the repository root:

```bash
cd compassMap-React
npm ci
npm run dev
```

Open the Vite development URL shown in the terminal (normally <http://localhost:5173>).

Available scripts:

```bash
npm run dev      # Start the Vite development server
npm run build    # Create the production build in dist/
npm run preview  # Serve the production build locally
npm run lint     # Run ESLint
```

## Tech Stack

- **React and React DOM** `^19.3.1` for the user interface
- **Vite** `^8.3.6` for development and production builds
- **Axios** `^1.21.0` for the current Open Library HTTP request
- JavaScript ES modules and JSX
- **ESLint** `^10.14.0` with React Hooks and React Refresh plugins:
  - React Hooks rules help catch incorrect Hook usage and apply recommended React-specific lint checks.
  - React Refresh rules check that components are structured to support Vite's fast refresh during development.
- **Babel React Compiler** plugin configured through the Vite React integration:
  - The compiler analyzes React components and Hooks at build time and can automatically memoize eligible code, reducing the need to add manual memoization in common cases.
  - The Vite configuration applies the React Compiler preset through the Babel plugin.
