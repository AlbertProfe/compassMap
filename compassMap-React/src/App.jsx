import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import OpenLibrary from "./pages/OpenLibrary.jsx";
import CompassMap from "./pages/CompassMap.jsx";
import NoPage from "./pages/NoPage.jsx";
import Layout from "./pages/Layout.jsx";

export default function App() {

  
  return (
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

  );
}
