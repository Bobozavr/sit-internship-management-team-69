import { fakeApplications } from "../data/fakeApplications.js";
import { fakeOffers } from "../data/fakeOffers.js";

// Temporary storage for one demo student. Replace with backend requests later.
const storageKey = "internship-management.demo-student.applications.v1";

export function getMyApplications() {
  try {
    const saved = localStorage.getItem(storageKey);
    if (saved === null) return [...fakeApplications];

    const applications = JSON.parse(saved);
    if (!Array.isArray(applications) || applications.some((item) =>
      !item || typeof item.id !== "number" || typeof item.internshipOfferId !== "number"
    )) {
      throw new Error("Invalid saved applications");
    }
    return applications;
  } catch {
    throw new Error("Could not read your demo applications. Check that browser storage is available.");
  }
}

export function submitApplication(internshipOfferId, motivationLetter) {
  const offer = fakeOffers.find((item) => item.id === internshipOfferId);
  if (!offer || offer.status !== "ACTIVE") {
    throw new Error("This internship is not accepting applications.");
  }

  const letter = motivationLetter.trim();
  if (!letter) throw new Error("Please write a motivation letter.");

  const applications = getMyApplications();
  if (applications.some((item) => item.internshipOfferId === internshipOfferId)) {
    throw new Error("You have already applied for this internship.");
  }

  const now = new Date();
  const application = {
    id: Math.max(0, ...applications.map((item) => item.id)) + 1,
    internshipOfferId: offer.id,
    internshipTitle: offer.title,
    companyName: offer.companyName,
    applicationDate: `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`,
    motivationLetter: letter,
    status: "PENDING",
    comment: ""
  };

  try {
    localStorage.setItem(storageKey, JSON.stringify([application, ...applications]));
  } catch {
    throw new Error("Your application was not saved. Check that browser storage is available and try again.");
  }
  return application;
}
