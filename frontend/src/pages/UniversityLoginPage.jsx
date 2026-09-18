import { Link } from "react-router-dom";
import SignInForm from "../components/SignInForm";
export default function UniversityLoginPage() {
  return <main><Link className="back-link" to="/">← Choose another account type</Link><header className="page-header"><span className="eyebrow">STUDENT ACCESS</span><h1>Student sign in</h1><p>Use the university email and password issued to you. Contact your administrator if you need access.</p></header><section className="empty-panel"><h2>Your university account</h2><SignInForm role="STUDENT" /></section></main>;
}
