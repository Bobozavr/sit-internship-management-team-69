import { NavLink, useNavigate } from "react-router-dom";
import { useSession } from "./useSession";
import { signOut } from "../api/authApi";
export default function Navbar() {
  const {user,loading,error}=useSession(); const navigate=useNavigate();
  const role={STUDENT:"Student",ADMIN:"Administrator",COMPANY:"Company"}[user?.role];
  return <aside className="sidebar"><NavLink className="brand" to={user?"/account":"/"}><span className="brand-mark">i.</span><span>internship<span className="brand-sub">UNIVERSITY CAREER SPACE</span></span></NavLink>
    <p className="nav-label">{user ? `${role.toUpperCase()} WORKSPACE` : 'WELCOME'}</p>
    <nav aria-label="Main navigation">
      {user ? <><NavLink to="/account">{user.role==='ADMIN'?'Admin dashboard':'My account'}</NavLink>{!user.passwordChangeRequired && <>
      {user.role==='STUDENT' && <><NavLink to="/student/offers">Find internships</NavLink><NavLink to="/student/applications">My applications</NavLink></>}
      {user.role==='COMPANY' && <NavLink to="/company/workspace">Our offers & applications</NavLink>}
      {user.role==='ADMIN' && <NavLink to="/student/offers">Published internships</NavLink>}
      </>}</> : <><NavLink to="/" end>Welcome & sign in</NavLink><NavLink to="/student/offers">Browse internships</NavLink><NavLink to="/university-login">Student login</NavLink><NavLink to="/login?role=company">Company login</NavLink><NavLink to="/company-registration">Register your company</NavLink></>}
    </nav>
    <div className="sidebar-note">{loading ? 'Checking account…' : user ? <><span className="live-dot" />Signed in · {role}<p>{user.firstName} {user.lastName}<br />{user.email}</p>{user.passwordChangeRequired && <p>Password change required</p>}<button className="sidebar-logout" onClick={()=>{signOut();navigate('/');}}>Sign out</button></> : <><strong>Guest · Not signed in</strong><p>{error || 'Sign in to apply and track your applications.'}</p></>}</div>
  </aside>;
}
