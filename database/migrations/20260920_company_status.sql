BEGIN;
ALTER TABLE company_registration_requests ADD COLUMN IF NOT EXISTS status_token_hash varchar(64);
CREATE UNIQUE INDEX IF NOT EXISTS uq_registration_status_token ON company_registration_requests(status_token_hash);
CREATE UNIQUE INDEX IF NOT EXISTS uq_pending_registration_email ON company_registration_requests(lower(representative_email)) WHERE status='PENDING';
CREATE UNIQUE INDEX IF NOT EXISTS uq_pending_registration_contact ON company_registration_requests(lower(contact_email)) WHERE status='PENDING';
COMMIT;
