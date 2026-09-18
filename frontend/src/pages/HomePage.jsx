import { Link } from "react-router-dom";
import { fakeOffers } from "../data/fakeOffers";

function HomePage() {
  return (
    <main>
      <header className="page-header"><span className="eyebrow">YOUR NEXT CHAPTER</span><h1>Great careers start<br />with a first step.</h1><p>Find a place to learn, contribute and turn your studies into experience.</p></header>
      <section className="hero-panel">
        <div><span className="eyebrow">LEARN BY DOING</span><h2>Your potential.<br />A world of possibilities.</h2><p>Explore internships, meet your next team and keep every application in one place.</p><Link className="button" to="/student/offers">Explore internships <span aria-hidden="true">↗</span></Link></div>
        <div className="hero-art" aria-hidden="true"><div className="orbit orbit-one" /><div className="orbit orbit-two" /><span className="art-label">THE NEXT STEP IS YOURS</span><span className="art-arrow">↗</span><span className="art-foot">LEARN. CONNECT. GROW.</span></div>
      </section>
      <div className="section-heading"><h2>A little direction. A big difference.</h2><span>{fakeOffers.filter(offer => offer.status === "ACTIVE").length} demo opportunities</span></div>
      <div className="journey-grid">
        <Link className="journey-card" to="/student/offers"><span className="step-number">01 / DISCOVER</span><h3>Find your fit</h3><p>Browse opportunities that match your interests and the skills you want to build.</p><span className="text-link">Explore opportunities →</span></Link>
        <Link className="journey-card" to="/student/applications"><span className="step-number">02 / FOLLOW UP</span><h3>Keep track</h3><p>See your applications and follow each step, from submission to a decision.</p><span className="text-link">My applications →</span></Link>
        <Link className="journey-card" to="/company-registration"><span className="step-number">03 / FOR COMPANIES</span><h3>Meet fresh talent</h3><p>Request access to the platform and help students take their first professional steps.</p><span className="text-link">Become a partner →</span></Link>
      </div>
    </main>
  );
}
export default HomePage;
