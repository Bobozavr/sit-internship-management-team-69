import { Link } from "react-router-dom";
function CompanyRegistrationPage() {
  return <main><header className="page-header"><span className="eyebrow">FOR OUR FUTURE PARTNERS</span><h1>Make room for fresh ideas.</h1><p>Help students turn their knowledge into meaningful experience.</p></header><section className="empty-panel"><h2>Company registration is coming next</h2><p>Companies will submit their details for administrator review. Access to publish internships will follow approval.</p><p>The registration form is not available in this demo yet.</p><Link className="button" to="/">Back to overview</Link></section></main>;
}
export default CompanyRegistrationPage;
