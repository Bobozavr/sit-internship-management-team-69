import { Link } from "react-router-dom";

function OfferCard({ offer }) {
  return (
    <article className="offer-card">
      <div className="card-top"><span className="company-avatar">{offer.companyName.slice(0, 2).toUpperCase()}</span><span className="company-name">{offer.companyName}</span><span className="badge">{offer.type}</span></div>
      <h2>{offer.title}</h2>
      <p className="card-description">{offer.description}</p>
      <div className="tags">{offer.requiredSkills.split(",").map(skill => <span key={skill}>{skill.trim()}</span>)}</div>
      <div className="offer-meta"><span>{offer.location}</span><span>Apply by {offer.deadline}</span></div>
      <div className="card-footer"><span className="availability"><span className="live-dot" /> {offer.status === "ACTIVE" ? "Accepting applications" : offer.status}</span><Link className="button button-small" to={`/student/offers/${offer.id}`}>View details <span aria-hidden="true">↗</span></Link></div>
    </article>
  );
}
export default OfferCard;
