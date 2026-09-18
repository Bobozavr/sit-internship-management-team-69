import { Link } from "react-router-dom";
function LoginPage() {
  return <main><header className="page-header"><span className="eyebrow">ACCOUNT ACCESS</span><h1>Welcome back.</h1><p>A dedicated space for approved companies and administrators.</p></header><section className="empty-panel"><h2>Account login is coming next</h2><p>This demo does not support account login yet. You can explore internships and try the student application flow.</p><Link className="button" to="/student/offers">Explore internships ↗</Link><p>Representing a new company? <Link className="text-link" to="/company-registration">Company registration →</Link></p></section></main>;
}
export default LoginPage;
