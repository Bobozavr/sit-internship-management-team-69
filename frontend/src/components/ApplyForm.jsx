import { useState } from "react";
import { Link } from "react-router-dom";
import { getMyApplications, submitApplication } from "../api/applicationApi";

function ApplyForm({ offer }) {
  const [motivationLetter, setMotivationLetter] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [initialState] = useState(() => {
    try {
      return {
        alreadyApplied: getMyApplications().some((item) => item.internshipOfferId === offer.id),
        error: ""
      };
    } catch (err) {
      return { alreadyApplied: false, error: err.message };
    }
  });

  function handleSubmit(event) {
    event.preventDefault();
    setError("");
    try {
      submitApplication(offer.id, motivationLetter);
      setMessage("Your application has been saved. Status: PENDING.");
    } catch (err) {
      setError(err.message);
    }
  }

  if (initialState.error) return <p role="alert">{initialState.error}</p>;

  if (initialState.alreadyApplied || message) {
    return (
      <section>
        <p role="status">{message || "You have already applied for this internship."}</p>
        <Link to="/student/applications">View my applications</Link>
      </section>
    );
  }

  if (offer.status !== "ACTIVE") {
    return <p>This internship is not accepting applications.</p>;
  }

  return (
    <section>
      <h2>Apply for this internship</h2>
      <p>Demo: your application is saved in this browser for one test student.</p>
      <form onSubmit={handleSubmit}>
        <label htmlFor="motivationLetter">Motivation letter</label>
        <br />
        <textarea
          id="motivationLetter"
          name="motivationLetter"
          rows={6}
          cols={50}
          required
          value={motivationLetter}
          onChange={(event) => setMotivationLetter(event.target.value)}
          placeholder="Why would you like to join this internship?"
        />
        <br />
        {error && <p role="alert">{error}</p>}
        <button type="submit">Apply</button>
      </form>
    </section>
  );
}

export default ApplyForm;
