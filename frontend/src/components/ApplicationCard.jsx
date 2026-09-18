import { Link } from "react-router-dom";

function ApplicationCard({ application }) {
  return (
    <article className="application-card">
      <div className="application-heading"><div><span className="eyebrow">{application.companyName}</span><h2>{application.internshipTitle}</h2></div><span className={`badge status-${application.status.toLowerCase()}`}>{application.status.replaceAll("_", " ")}</span></div>
      <p className="muted">Submitted {application.applicationDate}</p>
      {application.comment && <div className="feedback"><strong>Company feedback</strong><p>{application.comment}</p></div>}
      {application.motivationLetter && <details><summary>Your motivation letter</summary><p className="letter">{application.motivationLetter}</p></details>}
      <Link className="text-link" to={`/student/offers/${application.internshipOfferId}`}>View internship →</Link>
    </article>
  );
}
export default ApplicationCard;
