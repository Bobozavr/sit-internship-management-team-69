import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <h2>Internship System</h2>

      <div>
        <Link to="/">Home</Link>
        <Link to="/login">Login</Link>
        <Link to="/university-login">University Login</Link>
        <Link to="/company-registration">Company Registration</Link>
        <Link to="/student/offers">Offers</Link>
        <Link to="/student/applications">Applications</Link>
      </div>
    </nav>
  );
}

export default Navbar;