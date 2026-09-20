import { useEffect, useState } from "react";
import { apiRequest } from "../api/authApi";
const empty = { firstName: "", lastName: "", facultyNumber: "", specialty: "", course: "1" };
export default function StudentManagement() {
  const [form, setForm] = useState(empty);
  const [students, setStudents] = useState([]);
  const [credentials, setCredentials] = useState(null);
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  async function refresh() { const users = await apiRequest("/admin/users"); setStudents(users.filter(u => u.role === "STUDENT")); }
  useEffect(() => { let active = true; apiRequest("/admin/users").then(users => { if (active) setStudents(users.filter(u => u.role === "STUDENT")); }).catch(err => { if (active) setError(err.message); }); return () => { active = false; }; }, []);
  async function create(event) {
    event.preventDefault(); setError(""); setBusy(true); setCredentials(null);
    try {
      const result = await apiRequest("/admin/student-accounts", { method: "POST", body: JSON.stringify({ ...form, course: Number(form.course) }) });
      setCredentials(result); setForm(empty); await refresh();
    } catch (err) { setError(err.message); } finally { setBusy(false); }
  }
  async function reset(student) {
    if (!window.confirm(`Issue a new temporary password for ${student.email}? Their current sessions will end.`)) return;
    setError(""); setBusy(true); setCredentials(null);
    try { setCredentials(await apiRequest(`/admin/student-accounts/${student.id}/reset-password`, { method: "POST" })); await refresh(); }
    catch (err) { setError(err.message); } finally { setBusy(false); }
  }
  return <section className="student-management"><div className="section-heading"><h2>Student accounts</h2><span>{students.length} students</span></div>
    <div className="details-grid"><section className="detail-panel"><h2>Create a student</h2><p>Issue a university login. The student must change their temporary password before using their account.</p>
    <form className="sign-in-form" onSubmit={create}>
      {[['firstName','First name'],['lastName','Last name'],['facultyNumber','Faculty number'],['specialty','Specialty']].map(([key,label]) => <div key={key}><label htmlFor={key}>{label}</label><input id={key} required maxLength={key === 'facultyNumber' ? 12 : key === 'specialty' ? 200 : 100} pattern={key === 'facultyNumber' ? '[0-9]{4,12}' : undefined} value={form[key]} onChange={e => setForm({ ...form, [key]: e.target.value })} disabled={busy} /></div>)}
      <label htmlFor="course">Course</label><select id="course" required value={form.course} onChange={e => setForm({ ...form, course: e.target.value })} disabled={busy}>{[1,2,3,4].map(course => <option key={course} value={course}>Year {course}</option>)}</select>
      <button type="submit" disabled={busy}>{busy ? "SavingвЂ¦" : "Create student"}</button>
    </form></section><section className="detail-panel"><h2>Hand over account details</h2><p>The email is a university-style login for this project, not a real mailbox. Give the details to the student personally or through your university channel.</p>
    {credentials ? <div className="issued-credentials" role="status"><p>Save these details now. The temporary password is shown only here.</p><label htmlFor="issuedEmail">Issued email</label><input id="issuedEmail" readOnly value={credentials.email} /><label htmlFor="issuedPassword">Temporary password</label><input id="issuedPassword" readOnly value={credentials.temporaryPassword} /><button className="button" onClick={() => setCredentials(null)}>I have saved the details</button></div> : <p>New credentials will appear here after creation or password reset.</p>}</section></div>
    {error && <p role="alert">{error}</p>}
    <div className="student-list">{students.map(student => <article className="student-row" key={student.id}><div><strong>{student.firstName} {student.lastName}</strong><p>{student.email}</p><small>{student.passwordChangeRequired ? "Password change required" : "Account ready"}</small></div><button className="button" disabled={busy} onClick={() => reset(student)}>Reset password</button></article>)}</div>
  </section>;
}
