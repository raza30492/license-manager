--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;



--
-- Name: company; Type: TABLE; Schema: public; Owner: mdzahidraza
--

CREATE TABLE company (
    id bigint NOT NULL,
    city character varying(50) NOT NULL,
    country character varying(50) NOT NULL,
    line1 character varying(100) NOT NULL,
    line2 character varying(255),
    state character varying(50) NOT NULL,
    zip_code character varying(255) NOT NULL,
    job_title character varying(50) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE company OWNER TO mdzahidraza;


CREATE SEQUENCE company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE company_id_seq OWNER TO mdzahidraza;

ALTER SEQUENCE company_id_seq OWNED BY company.id;

ALTER TABLE ONLY company ALTER COLUMN id SET DEFAULT nextval('company_id_seq'::regclass);

SELECT pg_catalog.setval('company_id_seq', 1, true);

ALTER TABLE ONLY company
    ADD CONSTRAINT company_pkey PRIMARY KEY (id);

-------------------------------------------------------------------------------------------------

--
-- Name: users; Type: TABLE; Schema: public; Owner: mdzahidraza
--

CREATE TABLE users (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    enabled boolean NOT NULL,
    modified_at timestamp without time zone,
    modified_by character varying(255),
    account_expired boolean NOT NULL,
    account_locked boolean NOT NULL,
    credential_expired boolean NOT NULL,
    email character varying(255) NOT NULL,
    mobile character varying(255) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    otp character varying(255),
    otp_sent_at timestamp without time zone,
    password character varying(255) NOT NULL,
    retry_count integer,
    roles character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    company_id bigint
);


ALTER TABLE users OWNER TO mdzahidraza;

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO mdzahidraza;

ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

SELECT pg_catalog.setval('users_id_seq', 1, true);

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(id);

CREATE INDEX idx7516795akd6qg7e0i8e5rv58s ON users USING btree (email, username);

-------------------------------------------------------------------------------------------------


--
-- Name: product; Type: TABLE; Schema: public; Owner: mdzahidraza
--

CREATE TABLE product (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    enabled boolean NOT NULL,
    modified_at timestamp without time zone,
    modified_by character varying(255),
    description character varying(255),
    name character varying(50) NOT NULL,
    product_prefix character varying(5) NOT NULL,
    flavours character varying(255) NOT NULL
);


ALTER TABLE product OWNER TO mdzahidraza;

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_id_seq OWNER TO mdzahidraza;

ALTER SEQUENCE product_id_seq OWNED BY product.id;

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);

SELECT pg_catalog.setval('product_id_seq', 1, false);

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);

ALTER TABLE ONLY product
    ADD CONSTRAINT uk_jmivyxk9rmgysrmsqw15lqr5b UNIQUE (name);

CREATE INDEX PRODUCT_IDX ON product USING btree (name);


-------------------------------------------------------------------------------------------------


--
-- Name: license; Type: TABLE; Schema: public; Owner: mdzahidraza
--

CREATE TABLE license (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    enabled boolean NOT NULL,
    entitlement bigint NOT NULL,
    entitlement_type character varying(255),
    modified_at timestamp without time zone,
    modified_by character varying(255),
    activated boolean NOT NULL,
    activated_on date,
    license_flavour character varying(255) NOT NULL,
    license_type character varying(255) NOT NULL,
    mac_id character varying(255),
    product_code character varying(255) NOT NULL,
    product_key character varying(255) NOT NULL,
    purchased_on date NOT NULL,
    validity integer NOT NULL,
    product_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE license OWNER TO mdzahidraza;

CREATE SEQUENCE license_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE license_id_seq OWNER TO mdzahidraza;

ALTER SEQUENCE license_id_seq OWNED BY license.id;

ALTER TABLE ONLY license ALTER COLUMN id SET DEFAULT nextval('license_id_seq'::regclass);

SELECT pg_catalog.setval('license_id_seq', 1, false);

ALTER TABLE ONLY license
    ADD CONSTRAINT license_pkey PRIMARY KEY (id);

ALTER TABLE ONLY license
    ADD CONSTRAINT uk_b4k4nfccphswnfepy4ea2m06w UNIQUE (product_code);

ALTER TABLE ONLY license
    ADD CONSTRAINT fkcaxj7wyy1p2htf4n88cbtft6y FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE ONLY license
    ADD CONSTRAINT fkkur8ykl6c4jg32f7mp12mhr8m FOREIGN KEY (user_id) REFERENCES users(id);


-------------------------------------------------------------------------------------------------

--------------------------
--- Creating tables for JdbcTokenStore
---------------------------


--
-- Name: oauth_client_details; Type: TABLE; Schema: public; Owner: mdzahidraza
--

CREATE TABLE oauth_client_details (
    client_id character varying(255),
    resource_ids character varying(255),
    client_secret character varying(255),
    scope character varying(255),
    authorized_grant_types character varying(255),
    web_server_redirect_uri character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(255)
);


ALTER TABLE oauth_client_details OWNER TO mdzahidraza;

--
-- Name: oauth_client_details oauth_client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: mdzahidraza
--

ALTER TABLE ONLY oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);
 

CREATE TABLE  oauth_client_token (
   token_id character varying(256),
   token bytea,
   authentication_id character varying(256),
   user_name character varying(256),
   client_id character varying(256)
);
 
CREATE TABLE  oauth_access_token (
   token_id character varying(256),
   token bytea,
   authentication_id character varying(256),
   user_name character varying(256),
   client_id character varying(256),
   authentication bytea,
   refresh_token character varying(256)
);
 
CREATE TABLE  oauth_refresh_token (
   token_id character varying(256),
   token bytea,
   authentication bytea
);
 
CREATE TABLE  oauth_code (
   code character varying(256),
   authentication bytea
);
    

--
-- Name: ClientDetails; Type: TABLE; Schema: public; Owner: mdzahidraza
--

-- customized oauth_client_details table
CREATE TABLE ClientDetails (
    appId character varying(255),
    resourceIds character varying(255),
    appSecret character varying(255),
    scope character varying(255),
    grantTypes character varying(255),
    redirectUrl character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096)
);


ALTER TABLE ClientDetails OWNER TO mdzahidraza;

--
-- Name: ClientDetails ClientDetails_pkey; Type: CONSTRAINT; Schema: public; Owner: mdzahidraza
--

ALTER TABLE ONLY ClientDetails
    ADD CONSTRAINT ClientDetails_pkey PRIMARY KEY (appId);

--
-- PostgreSQL database dump complete
--

