# Company registration and administrator review

1. Open `/company-registration`, enter company/representative details and choose a password.
2. Save the private status link displayed after submission. It can be reopened without signing in. No email is sent. Approval enables sign-in with the representative email and original password.
3. An administrator opens **Company registrations**, reviews full details and approves or rejects with a reason.
4. The company uses **Refresh status** to see the decision. After rejection, the status page shows when another request can be submitted: 24 hours after review. Earlier attempts are rejected by the server. Old requests remain as history.

The status link is a bearer secret: anyone possessing it can view the company name, decision, feedback and dates. It does not contain a password and does not authorize sign-in. The random 256-bit token is stored only as a SHA-256 hash in PostgreSQL. The browser keeps the token in the URL fragment and sends it in `X-Registration-Token`; it is not part of the HTTP request URL. Keep/save the link yourself; recovery of a lost link is not implemented.

## API and migration

- `POST /api/company-registration-requests` returns a receipt containing `statusToken` and a limited `registration` status object.
- `GET /api/company-registration-requests/status` requires the private token header and returns no email/contact/password fields. The old numeric public status endpoint is removed.
- Administrator list/detail responses include description, website, city and representative names, without password hashes or status tokens.
- Review operations lock the request row and run transactionally. Approval creates one user/company; repeated review is rejected. Rejection creates no account. Unique partial indexes prevent concurrent pending registrations for the same representative/contact email.
- Apply `database/migrations/20260920_company_status.sql` before starting with schema validation. It preserves existing rows and adds token hashes and indexes; applied locally. Existing requests predating this change have no private link and can still be reviewed by administrators.

## Verification

Six CompanyRegistrationServiceTest cases plus eight AuthSecurityTest cases passed. Tests cover token hashing, pending/rejected duplicate rules, retry after 25 hours, missing/unknown token, rejection reason, approval account creation and repeat-review rejection. Browser/API checks covered submission, status reload, admin full details, approval then company login, rejection feedback, immediate retry denial, no login before approval/after rejection, private status access and omitted contact fields. Temporary test records were removed. Frontend build and lint passed.

Publishing/editing internships and processing student applications from the company UI are the next separate step; the company workspace currently displays its own offers and applications.
