import { BrowserRouter, Routes, Route } from "react-router-dom";
  //3 tools from lib (for open pages, conteiner, rule how to open pages)
import Navbar from "./components/Navbar";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import UniversityLoginPage from "./pages/UniversityLoginPage";
import CompanyRegistrationPage from "./pages/CompanyRegistrationPage";
import StudentOffersPage from "./pages/StudentOffersPage";
import StudentApplicationsPage from "./pages/StudentApplicationsPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/university-login" element={<UniversityLoginPage />} />
        <Route path="/company-registration" element={<CompanyRegistrationPage />} />

        <Route path="/student/offers" element={<StudentOffersPage />} />
        <Route path="/student/applications" element={<StudentApplicationsPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;