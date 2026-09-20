import { apiRequest } from "./authApi";
export function getMyApplications() { return apiRequest("/student/applications"); }
export function submitApplication(internshipOfferId, motivationLetter) {
  return apiRequest("/student/applications", {method:"POST",body:JSON.stringify({internshipOfferId,motivationLetter:motivationLetter.trim()})});
}
