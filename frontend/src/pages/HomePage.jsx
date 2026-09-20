import { Link } from "react-router-dom";

const roles = [
  { name: "Student", label: "LEARN & GROW", icon: "01", to: "/university-login", description: "Use your university email and password to find internships and follow your applications.", action: "Student sign in" },
  { name: "Company", label: "MEET YOUR NEXT TEAM", icon: "02", to: "/login?role=company", description: "Sign in with your approved company account to publish opportunities and meet students.", action: "Company sign in" },
  { name: "Administrator", label: "MANAGE THE COMMUNITY", icon: "03", to: "/login?role=admin", description: "Use your administrator account to review companies, manage users and oversee the platform.", action: "Administrator sign in" },
];

export default function HomePage() {
  return (
    <main className="access-home">
      <header className="page-header"><span className="eyebrow">UNIVERSITY CAREER SPACE</span><h1>Your next chapter<br />starts here.</h1><p>One community. Different possibilities.<br />Choose how you would like to sign in.</p></header>
      <section className="access-roles" aria-label="Choose your account type">
        {roles.map(role => <Link className="access-role" key={role.name} to={role.to}><span className="access-role-number" aria-hidden="true">{role.icon}</span><span className="eyebrow">{role.label}</span><h2>{role.name}</h2><p>{role.description}</p><span className="text-link">{role.action} <span aria-hidden="true">→</span></span></Link>)}
      </section>
      <section className="access-footer"><div><h2>New company?</h2><p>Request an account. Our administrator will review your registration.</p><Link className="text-link" to="/company-registration">Register your company →</Link></div><Link className="button" to="/student/offers">Explore internships ↗</Link></section>
    </main>
  );
}
