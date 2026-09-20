import { useState } from "react";
import { apiRequest, updateSession } from "../api/authApi";
export default function ChangePasswordForm({ onChanged }) {
  const [currentPassword, setCurrent] = useState("");
  const [newPassword, setNew] = useState("");
  const [confirm, setConfirm] = useState("");
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  async function submit(event) {
    event.preventDefault(); setError("");
    if (newPassword !== confirm) { setError("The new passwords do not match."); return; }
    setBusy(true);
    try { const result = await apiRequest("/auth/password", { method: "POST", body: JSON.stringify({currentPassword,newPassword}) }); updateSession(result.token); onChanged(await apiRequest("/auth/me")); }
    catch (err) { setError(err.message); } finally { setBusy(false); }
  }
  return <section className="empty-panel"><h2>Change your temporary password</h2><p>You are using a temporary password. Choose your own password to continue.</p><form className="sign-in-form" onSubmit={submit}>
    <label htmlFor="currentPassword">Temporary password</label><input id="currentPassword" type="password" autoComplete="current-password" required value={currentPassword} onChange={e => setCurrent(e.target.value)} disabled={busy} />
    <label htmlFor="newPassword">New password (10–64 characters)</label><input id="newPassword" type="password" autoComplete="new-password" minLength={10} maxLength={64} required value={newPassword} onChange={e => setNew(e.target.value)} disabled={busy} />
    <label htmlFor="confirmPassword">Confirm new password</label><input id="confirmPassword" type="password" autoComplete="new-password" required value={confirm} onChange={e => setConfirm(e.target.value)} disabled={busy} />
    {error && <p role="alert">{error}</p>}<button type="submit" disabled={busy}>{busy ? "Saving…" : "Save new password"}</button>
  </form></section>;
}
