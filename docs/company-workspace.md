# Company offers and application review

After administrator approval, sign in as Company and open **Our offers & applications**.

- **Create internship** publishes an ACTIVE offer with a title, description, skills, location, work style and application deadline. Students can find it in the catalog immediately.
- **Edit internship** changes the content without changing its status.
- **Close internship** stops new applications and hides the offer from the active catalog. Existing applications remain available.
- **Reopen internship** explicitly resumes applications. Update an expired deadline first. An administrator-disabled INACTIVE offer cannot be reopened or have its restriction bypassed through Close.
- **Applications** filters incoming applications by offer. Read the student's motivation letter, select Under review / Approved / Rejected and save an optional comment. The student sees both the status and the comment in My applications after loading/refreshing that page.

Company IDs and ownership come from the authenticated account. The UI never chooses which company's data to manage. The server rejects attempts to modify another company's offers or applications.

The new API endpoint is `PATCH /api/company/offers/{id}/reopen`. Editing no longer implicitly reopens CLOSED offers. Other offer/application API contracts are unchanged. No database migration was needed for this step.

## Checks

- 19 Java tests passed, including five CompanyOffersTest cases for status preservation, close/reopen, administrator-disabled offers, expired deadlines and ownership.
- Frontend build and lint passed.
- Browser/API workflow: create two approved test companies and a student; publish an offer through the UI; student applies; company sets each review status and feedback; student sees the decision; close hides the offer; editing keeps it CLOSED; reopen restores it; another company cannot close it or change/read its application; decisions persist after reload.
- Temporary test records were removed after verification. Existing user data was not deleted.

Administrator offer moderation controls, expanded statistics and other remaining project requirements are separate follow-up work.
