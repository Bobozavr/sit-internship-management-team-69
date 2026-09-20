import "./access.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
  //3 tools from lib (for open pages, conteiner, rule how to open pages)
import { AuthSession, SessionGate, RequireRole } from "./components/AuthSession";
import AdminCompanyRequestsPage from "./pages/AdminCompanyRequestsPage";
import CompanyRegistrationStatusPage from "./pages/CompanyRegistrationStatusPage";
import CompanyWorkspace from "./pages/CompanyWorkspace";
import Navbar from "./components/Navbar";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import UniversityLoginPage from "./pages/UniversityLoginPage";
import CompanyRegistrationPage from "./pages/CompanyRegistrationPage";
import StudentOffersPage from "./pages/StudentOffersPage";
import StudentOfferDetailsPage from "./pages/StudentOfferDetailsPage";
import StudentApplicationsPage from "./pages/StudentApplicationsPage";

import AccountPage from "./pages/AccountPage";

function App() {
  return (
    <BrowserRouter>
      <AuthSession>
      <Navbar />
      <SessionGate>

      <Routes>
        <Route path="/account" element={<AccountPage />} />
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/university-login" element={<UniversityLoginPage />} />
        <Route path="/admin/company-requests" element={<RequireRole role="ADMIN"><AdminCompanyRequestsPage /></RequireRole>} />
        <Route path="/company-registration/status" element={<CompanyRegistrationStatusPage />} />
        <Route path="/company-registration" element={<CompanyRegistrationPage />} />

        <Route path="/student/offers" element={<StudentOffersPage />} />
        <Route path="/student/offers/:id" element={<StudentOfferDetailsPage />} />
        <Route path="/student/applications" element={<RequireRole role="STUDENT"><StudentApplicationsPage /></RequireRole>} />
        <Route path="/company/workspace" element={<RequireRole role="COMPANY"><CompanyWorkspace /></RequireRole>} />
      </Routes>
      </SessionGate>
      </AuthSession>
    </BrowserRouter>
  );
}

export default App;
