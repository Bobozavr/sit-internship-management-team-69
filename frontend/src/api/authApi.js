const key = "internship.auth.token";
export function updateSession(token) { sessionStorage.setItem(key, token); }
export function signOut() { sessionStorage.removeItem(key); }
export async function apiRequest(path, options = {}) {
  const token = sessionStorage.getItem(key);
  const response = await fetch(`/api${path}`, {
    ...options,
    headers: { "Content-Type": "application/json", ...(token ? { Authorization: `Bearer ${token}` } : {}), ...options.headers }
  });
  if (response.status === 401) signOut();
  const data = await response.json().catch(() => null);
  if (!response.ok) throw new Error(data?.message || (response.status === 401 ? "Please sign in again." : "Request failed. Please try again."));
  return data;
}
export async function signIn(email, password, role) {
  signOut();
  const data = await apiRequest(role === "STUDENT" ? "/auth/university-login" : "/auth/login", {
    method: "POST", body: JSON.stringify({ email: email.trim(), password })
  });
  if (data.role !== role) throw new Error("This account belongs to another role. Please choose the matching sign-in page.");
  sessionStorage.setItem(key, data.token);
  return data;
}
