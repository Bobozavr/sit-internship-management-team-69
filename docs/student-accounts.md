# Student accounts and temporary passwords

Administrators can create student accounts from **My account → Student accounts**. Enter first name, last name, faculty number (4–12 digits), specialty and course (1–4).

The login is `s<facultyNumber>@students.example`. This is a demo university identifier, not an actual mailbox. A cryptographically random temporary password is displayed once in the current page. Hand the credentials to the student personally or through the university channel. The database stores only the password hash. Clearing or leaving the page loses the displayed password; an administrator can issue a new one with **Reset password**.

Student sign-in opens the mandatory password-change form. Choose a different password of 10–64 characters (at most 72 UTF-8 bytes). The server denies student operations until this is complete. A successful change issues a new token and invalidates previously issued tokens. Administrator reset also invalidates sessions and requires another change; it does not unblock a disabled user.

## Existing database

Before starting the updated backend against an existing database, apply `database/migrations/20260920_student_credentials.sql` using the project's database credentials. The migration adds `password_change_required` and `token_version` to `users`, preserving existing rows. It is safe to rerun. It has already been applied to the local development database on this machine. Start with `--spring.jpa.hibernate.ddl-auto=validate` to verify the schema.

Existing accounts remain unchanged; old student accounts without passwords need an administrator reset. Tokens issued before this version do not contain a version and require sign-in again.

## API

- `POST /api/admin/student-accounts`: admin only; creates user and student profile transactionally, returns userId/email/temporaryPassword with Cache-Control: no-store.
- `POST /api/admin/student-accounts/{id}/reset-password`: admin only; only for students.
- `POST /api/auth/password`: authenticated account; currentPassword/newPassword; returns a new session.
- Auth responses and `/api/auth/me` expose passwordChangeRequired, never a password hash.

## Verification

Seven AuthSecurityTest tests cover credential checks, enabled state, actual server role, restricted temporary sessions, password change, token version invalidation and wrong current password. Browser/API checks cover admin creation, duplicate faculty number, student login, forced change, mismatch confirmation, old-password rejection, old-token rejection, reload, repeat login, reset and blocking. Test accounts were removed after the checks. Frontend build and lint passed.

Student offers and applications now use backend APIs. My applications contains only the signed-in student's records. The sidebar shows identity and role; company accounts have a separate read-only workspace for their own offers and incoming applications. Company registration, publishing and application review controls are implemented; see company-registration.md and company-workspace.md.
