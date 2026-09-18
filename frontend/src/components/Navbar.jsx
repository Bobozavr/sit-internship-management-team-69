import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <aside className="sidebar">
      <NavLink className="brand" to="/"><span className="brand-mark">i.</span><span>internship<span className="brand-sub">UNIVERSITY CAREER SPACE</span></span></NavLink>
      <p className="nav-label">YOUR WORKSPACE</p>
      <nav aria-label="Main navigation">
        <NavLink to="/" end>Overview</NavLink>
        <NavLink to="/student/offers">Explore internships</NavLink>
        <NavLink to="/student/applications">My applications</NavLink>
      </nav>
      <p className="nav-label">ACCESS & PARTNERS</p>
      <nav aria-label="Account navigation">
        <NavLink to="/university-login">University login</NavLink>
        <NavLink to="/login">Company & admin login</NavLink>
        <NavLink to="/company-registration">Register your company</NavLink>
      </nav>
      <div className="sidebar-note"><span className="live-dot" /> Student demo<p>Explore opportunities.<br />Build your next chapter.</p></div>
    </aside>
  );
}
export default Navbar;
