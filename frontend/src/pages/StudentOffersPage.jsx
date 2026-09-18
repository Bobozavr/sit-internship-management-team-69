import { useState } from "react";
import OfferCard from "../components/OfferCard";
import { fakeOffers } from "../data/fakeOffers";

function StudentOffersPage() {
  const [query, setQuery] = useState("");
  const [type, setType] = useState("");
  const [city, setCity] = useState("");
  const activeOffers = fakeOffers.filter(offer => offer.status === "ACTIVE");
  const offers = activeOffers.filter(offer =>
    (!type || offer.type === type) && (!city || offer.location === city) &&
    `${offer.title} ${offer.description} ${offer.requiredSkills} ${offer.companyName}`.toLowerCase().includes(query.trim().toLowerCase())
  );
  return (
    <main>
      <header className="page-header"><span className="eyebrow">EXPLORE / INTERNSHIPS</span><h1>Find your next beginning.</h1><p>Real experience starts with the right opportunity. Discover a role that feels like you.</p></header>
      <div className="filter-bar">
        <label>Search opportunities<input type="search" value={query} onChange={event => setQuery(event.target.value)} placeholder="Role, company or skill…" /></label>
        <label>Location<select value={city} onChange={event => setCity(event.target.value)}><option value="">All locations</option>{[...new Set(activeOffers.map(offer => offer.location))].map(location => <option key={location}>{location}</option>)}</select></label>
        <label>Work style<select value={type} onChange={event => setType(event.target.value)}><option value="">All work styles</option><option value="REMOTE">Remote</option><option value="HYBRID">Hybrid</option><option value="ONSITE">On-site</option></select></label>
      </div>
      <p className="results-count" role="status">{offers.length} {offers.length === 1 ? "opportunity" : "opportunities"} to explore</p>
      <div className="offer-grid">{offers.map(offer => <OfferCard key={offer.id} offer={offer} />)}</div>
      {offers.length === 0 && <div className="empty-results"><h2>No matches just yet.</h2><p>Try another keyword or broaden your filters.</p><button className="reset-button" onClick={() => { setQuery(""); setCity(""); setType(""); }}>Clear filters</button></div>}
      <p className="notice">Demo opportunities · Applications are saved in this browser.</p>
    </main>
  );
}
export default StudentOffersPage;
