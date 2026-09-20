import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signIn } from "../api/authApi";

export default function SignInForm({ role }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  const navigate = useNavigate();
  async function submit(event) {
    event.preventDefault(); setBusy(true); setError("");
    try { await signIn(email, password, role); navigate("/account"); }
    catch (err) { setError(err instanceof TypeError ? "Cannot reach the server. Please check that the backend is running." : err.message); }
    finally { setBusy(false); }
  }
  return <form className="sign-in-form" onSubmit={submit}>
    <label htmlFor="email">{role === "STUDENT" ? "University email" : "Email"}</label>
    <input id="email" type="email" autoComplete="username" required value={email} onChange={e => setEmail(e.target.value)} disabled={busy} />
    <label htmlFor="password">Password</label>
    <input id="password" type="password" autoComplete="current-password" required value={password} onChange={e => setPassword(e.target.value)} disabled={busy} />
    {error && <p role="alert">{error}</p>}
    <button type="submit" disabled={busy}>{busy ? "Signing in…" : "Sign in"}</button>
  </form>;
}
