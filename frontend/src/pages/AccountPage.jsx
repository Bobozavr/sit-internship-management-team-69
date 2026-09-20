import { Link } from "react-router-dom";
import { useSession } from "../components/useSession";
import StudentManagement from "../components/StudentManagement";
import ChangePasswordForm from "../components/ChangePasswordForm";

export default function AccountPage() {
  const { user, loading, error } = useSession();
  if (loading) return <main><p role="status">Loading your account…</p></main>;
  if (!user) return <main><h1>Sign in to continue</h1>{error && <p role="alert">{error}</p>}<Link className="button" to="/">Choose your account type →</Link></main>;
  const role = { ADMIN: "Administrator", COMPANY: "Company", STUDENT: "Student" }[user.role];
  return <main>
    <header className="page-header"><span className="eyebrow">{role} ACCOUNT</span><h1>Welcome, {user.firstName}.</h1><p>You are signed in as {role.toLowerCase()}.</p></header>
    {user.passwordChangeRequired ? <ChangePasswordForm onChanged={() => {}} /> : <>
      <section className="empty-panel"><h2>Account details</h2><p>{user.email}</p>
        <p>{user.role === "STUDENT" ? "You are signed in. Choose an active internship, read its details and send your motivation letter. Track the decision in My applications." : user.role === "COMPANY" ? "Your workspace shows only your company’s internships and the students who applied to them." : "Manage student accounts below. Each student has a separate account and their own applications."}</p>
        {user.role === "STUDENT" && <div className="account-actions"><Link className="button" to="/student/offers">Find internships →</Link><Link className="text-link" to="/student/applications">My applications →</Link></div>}
        {user.role === "COMPANY" && <div className="account-actions"><Link className="button" to="/company/workspace">Our offers & applications →</Link></div>}
      </section>
      {user.role === "ADMIN" && <><div className="account-actions"><Link className="button" to="/admin/company-requests">Review company registrations →</Link></div><StudentManagement /></>}
    </>}
  </main>;
}
