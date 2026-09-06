function OfferCard({ offer }) {
    //waiting for offer from data fetching
  return (
    <div>
      <h2>{offer.title}</h2>

      <p>
        <strong>Company:</strong> {offer.companyName}
      </p>

      <p>
        <strong>Location:</strong> {offer.location}
      </p>

      <p>
        <strong>Type:</strong> {offer.type}
      </p>

      <p>
        <strong>Required skills:</strong> {offer.requiredSkills}
      </p>

      <p>
        <strong>Deadline:</strong> {offer.deadline}
      </p>

      <p>{offer.description}</p>

      <button>View Details</button>
    </div>
  );
}

export default OfferCard;