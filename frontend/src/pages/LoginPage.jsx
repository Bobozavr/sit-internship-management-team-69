import { Link, useSearchParams } from "react-router-dom";

export default function LoginPage() {
  const [params] = useSearchParams();
  const isAdmin = params.get("role") === "admin";
  const name = isAdmin ? "Administrator" : "Company";
  return (
    <main>
      <Link className="back-link" to="/">← Choose another account type</Link>
      <header className="page-header"><span className="eyebrow">{name.toUpperCase()} ACCESS</span><h1>{name} sign in</h1><p>{isAdmin ? "Manage users, review company registrations and oversee internships." : "Manage your company’s opportunities and student applications."}</p></header>
      <section className="empty-panel"><h2>{isAdmin ? "Your administrator account" : "Your approved company account"}</h2><p>{isAdmin ? "Your account is created by the system. Public administrator registration is not available." : "Use the email and password from your company registration after approval."}</p><p role="status">Sign-in is not connected yet. Account access will be available once the backend is connected.</p>{!isAdmin && <Link className="text-link" to="/company-registration">Request a company account →</Link>}</section>
    </main>
  );
}
