import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { apiRequest, signOut } from "../api/authApi";
export default function AccountPage() {
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    let active = true;
    apiRequest("/auth/me").then(value => { if (active) setUser(value); }).catch(err => { if (active) setError(err.message); });
    return () => { active = false; };
  }, []);
  if (error) return <main><p role="alert">{error}</p><Link className="text-link" to="/">Back to sign in →</Link></main>;
  if (!user) return <main><p role="status">Loading your account…</p></main>;
  const role = { ADMIN: "Administrator", COMPANY: "Company", STUDENT: "Student" }[user.role];
  return <main><header className="page-header"><span className="eyebrow">{role} ACCOUNT</span><h1>Welcome, {user.firstName}.</h1><p>You are signed in as {role.toLowerCase()}.</p></header><section className="empty-panel"><h2>Account details</h2><p>{user.email}</p><p>Your account has been verified by the server. Your workspace features are being connected next.</p><button className="button" onClick={() => { signOut(); navigate("/"); }}>Sign out</button></section></main>;
}
