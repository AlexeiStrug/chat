CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);
ALTER TABLE oauth_client_details
  OWNER TO postgres;

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);
ALTER TABLE oauth_client_token
  OWNER TO postgres;

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
);
ALTER TABLE oauth_access_token
  OWNER TO postgres;

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
);
ALTER TABLE oauth_refresh_token
  OWNER TO postgres;

CREATE TABLE IF NOT EXISTS oauth_code (
  code           VARCHAR(256),
  authentication BYTEA
);
ALTER TABLE oauth_code
  OWNER TO postgres;

CREATE OR REPLACE RULE insert_ignore_on_oauth_client_details AS
ON INSERT TO oauth_client_details
  WHERE EXISTS(SELECT 1
               FROM oauth_client_details
               WHERE client_id = NEW.client_id)
DO INSTEAD NOTHING;

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('client-with-secret', 'oauth2-resource', '$2a$10$PYK6gVI8.0sjKAu7/PWIduZL9tWZWIkX30SZauBR4.d9Q6Heij0Ae', 'read, write, trust',
                              'password, authorization_code,refresh_token, implicit, client_credentials', '', '', 600,
                              2000, '{}', '');
DROP RULE insert_ignore_on_oauth_client_details
ON oauth_client_details;