# Mobile App Starter

Reusable Android + Cloudflare foundation extracted from Chess++.

## Included

- Android Credential Manager Google sign-in with cryptographic nonce
- App-owned opaque bearer sessions after provider verification
- Cloudflare Worker TypeScript API
- D1 users, identities, and access-token schema
- Server-side Google ID-token verification
- Username onboarding
- Authenticated `/v1/me` route
- Health check
- Security-oriented separation between provider identity and application sessions

## Architecture

```text
Android
  -> Google Credential Manager
  -> Google ID token + nonce
  -> Cloudflare Worker
       -> verifies signature/audience/issuer/expiry/nonce
       -> maps provider subject to app user
       -> issues random app bearer token
       -> stores only SHA-256 token hash
  -> D1
```

The provider credential proves identity once. Normal application API traffic uses the application's own session token.

## Reuse checklist

1. Rename package/application identifiers.
2. Create Google Web + Android OAuth clients.
3. Put the Web client ID into Android build configuration and the Worker secret/config.
4. Create a D1 database and bind it as `APP_DB`.
5. Apply `edge/migrations/0001_identity.sql`.
6. Deploy the Worker with Wrangler.
7. Point `AppApi` at the deployed HTTPS endpoint.
8. Add domain-specific routes/tables separately from identity/session code.

## Security notes

Never trust identity claims supplied directly by the mobile UI. Verify the Google token on the server. Never put provider secrets in the APK. Store only a hash of app bearer tokens server-side. Production Android token storage should use a Keystore-backed implementation behind the session-store abstraction.
