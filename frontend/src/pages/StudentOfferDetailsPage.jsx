import { Link, useParams } from "react-router-dom";
import { fakeOffers } from "../data/fakeOffers";
import ApplyForm from "../components/ApplyForm";

function StudentOfferDetailsPage() {
  const { id } = useParams();
  const offer = fakeOffers.find(offer => String(offer.id) === id);
  if (!offer) return <main><div className="empty-panel"><span className="eyebrow">OPPORTUNITY UNAVAILABLE</span><h1>Internship not found</h1><p>This internship does not exist. Choose an offer from the list.</p><Link className="button" to="/student/offers">Back to offers</Link></div></main>;
  return (
    <main>
      <Link className="back-link" to="/student/offers">← Back to opportunities</Link>
      <header className="page-header"><span className="eyebrow">{offer.companyName} / INTERNSHIP</span><h1>{offer.title}</h1><p>{offer.location} · {offer.type} · Apply by {offer.deadline}</p></header>
      <div className="details-grid">
        <section className="detail-panel"><h2>At a glance</h2><p><strong>Company</strong> {offer.companyName}</p><p><strong>Location</strong> {offer.location}</p><p><strong>Work style</strong> {offer.type}</p><p><strong>Deadline</strong> {offer.deadline}</p><p><strong>Status</strong> <span className="badge">{offer.status}</span></p><div className="detail-description"><h2>About the opportunity</h2><p>{offer.description}</p></div><div className="detail-description"><h2>What you will bring</h2><div className="tags">{offer.requiredSkills.split(",").map(skill => <span key={skill}>{skill.trim()}</span>)}</div></div></section>
        <div className="apply-panel"><ApplyForm key={offer.id} offer={offer} /></div>
      </div>
    </main>
  );
}
export default StudentOfferDetailsPage;
