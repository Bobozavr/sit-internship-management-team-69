import { Link } from "react-router-dom";
function UniversityLoginPage() {
  return <main><header className="page-header"><span className="eyebrow">STUDENT ACCESS</span><h1>Your future starts here.</h1><p>Your university account will be your way into the internship community.</p></header><section className="empty-panel"><h2>University login is coming next</h2><p>University sign-in simulation is not connected yet. For now, explore the platform as one demo student.</p><Link className="button" to="/student/offers">Explore the student demo ↗</Link></section></main>;
}
export default UniversityLoginPage;
