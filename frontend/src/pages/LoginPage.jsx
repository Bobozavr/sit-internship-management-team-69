import { Link, useSearchParams } from "react-router-dom";

import SignInForm from "../components/SignInForm";

export default function LoginPage() {
  const [params] = useSearchParams();
  const isAdmin = params.get("role") === "admin";
  const name = isAdmin ? "Administrator" : "Company";
  return (
    <main>
      <Link className="back-link" to="/">в†ђ Choose another account type</Link>
      <header className="page-header"><span className="eyebrow">{name.toUpperCase()} ACCESS</span><h1>{name} sign in</h1><p>{isAdmin ? "Manage users, review company registrations and oversee internships." : "Manage your companyвЂ™s opportunities and student applications."}</p></header>
      <section className="empty-panel"><h2>{isAdmin ? "Your administrator account" : "Your approved company account"}</h2><p>{isAdmin ? "Your account is created by the system. Public administrator registration is not available." : "Use the email and password from your company registration after approval."}</p><SignInForm key={name} role={isAdmin ? "ADMIN" : "COMPANY"} />{!isAdmin && <Link className="text-link" to="/company-registration">Request a company account в†’</Link>}</section>
    </main>
  );
}
