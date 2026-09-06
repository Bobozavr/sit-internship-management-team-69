import OfferCard from "../components/OfferCard";
// take component offercard and use it to show offers in the page
import { fakeOffers } from "../data/fakeOffers";
//take list of temporary offers from fakeOffers.js and use it to show offers in the page
function StudentOffersPage() {
  return (
    <div>
      <h1>Internship Offers</h1>
      <p>Students can browse active internship offers here.</p>

      <div>
        {fakeOffers.map((offer) => (
          <OfferCard key={offer.id} offer={offer} />
          //for each offer create a card 
        ))}
      </div>
    </div>
  );
}

export default StudentOffersPage;