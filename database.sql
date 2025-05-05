--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: GeographicalZone; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."GeographicalZone" AS ENUM (
    'AIX',
    'BATZ',
    'BELLE_ILE_EN_MER',
    'BREHAT',
    'HOUAT',
    'ILE_DE_GROIX',
    'MOLENE',
    'OUESSANT',
    'SEIN',
    'YEU'
);


ALTER TYPE public."GeographicalZone" OWNER TO postgres;

--
-- Name: PricingPeriod; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."PricingPeriod" AS ENUM (
    'LOW_SEASON',
    'MID_SEASON',
    'HIGH_SEASON'
);


ALTER TYPE public."PricingPeriod" OWNER TO postgres;

--
-- Name: ReservationStatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."ReservationStatus" AS ENUM (
    'PENDING',
    'PAID'
);


ALTER TYPE public."ReservationStatus" OWNER TO postgres;

--
-- Name: Role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."Role" AS ENUM (
    'USER',
    'CAPTAIN',
    'ADMIN'
);


ALTER TYPE public."Role" OWNER TO postgres;

--
-- Name: SeaCondition; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."SeaCondition" AS ENUM (
    'CALM',
    'SLIGHTLY_ROUGH',
    'ROUGH',
    'VERY_ROUGH'
);


ALTER TYPE public."SeaCondition" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Account" (
    "userId" text NOT NULL,
    type text NOT NULL,
    provider text NOT NULL,
    "providerAccountId" text NOT NULL,
    refresh_token text,
    access_token text,
    expires_at integer,
    token_type text,
    scope text,
    id_token text,
    session_state text,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Account" OWNER TO postgres;

--
-- Name: BillingAddress; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."BillingAddress" (
    id text NOT NULL,
    name text NOT NULL,
    street text NOT NULL,
    city text NOT NULL,
    "postalCode" text NOT NULL,
    state text,
    country text NOT NULL,
    "phoneNumber" text
);


ALTER TABLE public."BillingAddress" OWNER TO postgres;

--
-- Name: Boat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Boat" (
    id text NOT NULL,
    name text NOT NULL,
    length double precision NOT NULL,
    width double precision NOT NULL,
    speed double precision NOT NULL,
    "imageUrl" text,
    equipment text[],
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Boat" OWNER TO postgres;

--
-- Name: BoatCategoryCapacity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."BoatCategoryCapacity" (
    id text NOT NULL,
    "boatId" text NOT NULL,
    "categoryId" text NOT NULL,
    "maxCapacity" integer NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."BoatCategoryCapacity" OWNER TO postgres;

--
-- Name: CaptainLog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."CaptainLog" (
    id text NOT NULL,
    "crossingId" text NOT NULL,
    "seaCondition" public."SeaCondition" NOT NULL,
    "delayMinutes" integer,
    "delayReason" text,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."CaptainLog" OWNER TO postgres;

--
-- Name: Contact; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Contact" (
    id text NOT NULL,
    name text NOT NULL,
    email text NOT NULL,
    subject text NOT NULL,
    message text NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Contact" OWNER TO postgres;

--
-- Name: Crossing; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Crossing" (
    id text NOT NULL,
    "departureTime" timestamp(3) without time zone NOT NULL,
    "boatId" text NOT NULL,
    "routeId" text NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Crossing" OWNER TO postgres;

--
-- Name: Pricing; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Pricing" (
    id text NOT NULL,
    "routeId" text NOT NULL,
    "typeId" text NOT NULL,
    period public."PricingPeriod" NOT NULL,
    amount double precision NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Pricing" OWNER TO postgres;

--
-- Name: Reservation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Reservation" (
    id text NOT NULL,
    "userId" text NOT NULL,
    "totalAmount" double precision NOT NULL,
    status public."ReservationStatus" DEFAULT 'PENDING'::public."ReservationStatus" NOT NULL,
    "billingAddressId" text,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Reservation" OWNER TO postgres;

--
-- Name: Route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Route" (
    id text NOT NULL,
    distance double precision NOT NULL,
    "departurePort" text NOT NULL,
    "arrivalPort" text NOT NULL,
    "geographicalZone" public."GeographicalZone" NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Route" OWNER TO postgres;

--
-- Name: Seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Seat" (
    id text NOT NULL,
    "crossingId" text NOT NULL,
    "seatTypeId" text NOT NULL,
    "bookedSeats" integer DEFAULT 0 NOT NULL,
    "reservationId" text,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."Seat" OWNER TO postgres;

--
-- Name: SeatCategory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SeatCategory" (
    id text NOT NULL,
    name text NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."SeatCategory" OWNER TO postgres;

--
-- Name: SeatType; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SeatType" (
    id text NOT NULL,
    name text NOT NULL,
    description text,
    "seatCategoryId" text NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."SeatType" OWNER TO postgres;

--
-- Name: User; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."User" (
    id text NOT NULL,
    name text,
    email text NOT NULL,
    "emailVerified" timestamp(3) without time zone,
    image text,
    password text,
    role public."Role" DEFAULT 'USER'::public."Role" NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updatedAt" timestamp(3) without time zone NOT NULL
);


ALTER TABLE public."User" OWNER TO postgres;

--
-- Data for Name: Account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Account" ("userId", type, provider, "providerAccountId", refresh_token, access_token, expires_at, token_type, scope, id_token, session_state, "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: BillingAddress; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."BillingAddress" (id, name, street, city, "postalCode", state, country, "phoneNumber") FROM stdin;
\.


--
-- Data for Name: Boat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Boat" (id, name, length, width, speed, "imageUrl", equipment, "createdAt", "updatedAt") FROM stdin;
cmaawc6820000n318hel2wzt5	Imane	56	13.25	9.5	https://www.lejournaldesarchipels.com/wp-content/uploads/2023/07/la-bargi.jpg	{"Fire extinguishers","Life Rafts",Wifi,Bar}	2025-05-05 09:46:50.88	2025-05-05 09:46:50.88
cmaawc6830001n318i0uhh8vq	Chatouilleuse	56	13.25	9.5	https://www.lejournaldesarchipels.com/wp-content/uploads/2023/07/la-bargi.jpg	{"Fire extinguishers","Life Rafts",Wifi,Bar}	2025-05-05 09:46:50.88	2025-05-05 09:46:50.88
cmaawc6830002n318j4jy7r7d	Polé	67	13.25	12	https://assets.meretmarine.com/s3fs-public/styles/large_xl/public/images/2017-05/pole.jpg?h=52a2667e&itok=C4oN6XXY	{"Fire extinguishers","Life Rafts",Wifi,Bar}	2025-05-05 09:46:50.88	2025-05-05 09:46:50.88
cmaawc6830003n318yqgwfysm	Karihani	67	13.25	12	https://assets.meretmarine.com/s3fs-public/styles/large_xl/public/images/2017-05/pole.jpg?h=52a2667e&itok=C4oN6XXY	{"Fire extinguishers","Life Rafts",Wifi,Bar}	2025-05-05 09:46:50.88	2025-05-05 09:46:50.88
\.


--
-- Data for Name: BoatCategoryCapacity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."BoatCategoryCapacity" (id, "boatId", "categoryId", "maxCapacity", "createdAt", "updatedAt") FROM stdin;
cmaawc68r0007n31866run7i8	cmaawc6820000n318hel2wzt5	cmaawc68h0004n318ur2dhdaj	392	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r0008n318gxaoxoew	cmaawc6820000n318hel2wzt5	cmaawc68h0005n318kbsuke5f	24	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r0009n318zcrvt4ld	cmaawc6820000n318hel2wzt5	cmaawc68h0006n318brv48zme	6	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000an318r922emfw	cmaawc6830001n318i0uhh8vq	cmaawc68h0004n318ur2dhdaj	392	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000bn318oigi0fpo	cmaawc6830001n318i0uhh8vq	cmaawc68h0005n318kbsuke5f	24	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000cn318f8wm8q9h	cmaawc6830001n318i0uhh8vq	cmaawc68h0006n318brv48zme	6	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000dn3187w9zq7oo	cmaawc6830002n318j4jy7r7d	cmaawc68h0004n318ur2dhdaj	590	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000en318vo6g8i8p	cmaawc6830002n318j4jy7r7d	cmaawc68h0005n318kbsuke5f	16	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000fn318x65c1lp0	cmaawc6830002n318j4jy7r7d	cmaawc68h0006n318brv48zme	17	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000gn3188hcuiqlp	cmaawc6830003n318yqgwfysm	cmaawc68h0004n318ur2dhdaj	590	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000hn318bj31uk7o	cmaawc6830003n318yqgwfysm	cmaawc68h0005n318kbsuke5f	16	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
cmaawc68r000in318e7ar3lz4	cmaawc6830003n318yqgwfysm	cmaawc68h0006n318brv48zme	17	2025-05-05 09:46:50.908	2025-05-05 09:46:50.908
\.


--
-- Data for Name: CaptainLog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."CaptainLog" (id, "crossingId", "seaCondition", "delayMinutes", "delayReason", "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: Contact; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Contact" (id, name, email, subject, message, "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: Crossing; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Crossing" (id, "departureTime", "boatId", "routeId", "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: Pricing; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Pricing" (id, "routeId", "typeId", period, amount, "createdAt", "updatedAt") FROM stdin;
cmaawc697000vn318kb0q4xnm	cmaawc690000rn31809h8q20a	cmaawc68w000jn318ynxv1v1j	LOW_SEASON	120	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc697000wn318xlqt7imq	cmaawc690000rn31809h8q20a	cmaawc68w000kn3186vismag9	LOW_SEASON	110	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc697000xn318bpil172c	cmaawc690000rn31809h8q20a	cmaawc68w000ln3185iddoh9i	LOW_SEASON	100	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc697000yn3181bvio3xa	cmaawc690000rn31809h8q20a	cmaawc68w000mn318h0vha0nk	LOW_SEASON	170	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc697000zn318j28lzr8y	cmaawc690000rn31809h8q20a	cmaawc68w000nn318mczekx5y	LOW_SEASON	190	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6970010n3188rci3n7m	cmaawc690000rn31809h8q20a	cmaawc68w000on318iyvrfy72	LOW_SEASON	210	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6970011n318l2q6umzf	cmaawc690000rn31809h8q20a	cmaawc68w000pn318s4hf88ew	LOW_SEASON	220	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6970012n3181sw7a9lf	cmaawc690000rn31809h8q20a	cmaawc68w000qn318aiu8brhp	LOW_SEASON	249	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0013n318x6xv8b2n	cmaawc690000tn318kfry0ffa	cmaawc68w000jn318ynxv1v1j	LOW_SEASON	120	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0014n318l1stn9k2	cmaawc690000tn318kfry0ffa	cmaawc68w000kn3186vismag9	LOW_SEASON	110	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0015n3181fyflec8	cmaawc690000tn318kfry0ffa	cmaawc68w000ln3185iddoh9i	LOW_SEASON	100	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0016n3188m24e3lo	cmaawc690000tn318kfry0ffa	cmaawc68w000mn318h0vha0nk	LOW_SEASON	170	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0017n318tx5egk7v	cmaawc690000tn318kfry0ffa	cmaawc68w000nn318mczekx5y	LOW_SEASON	190	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0018n3182anpixaa	cmaawc690000tn318kfry0ffa	cmaawc68w000on318iyvrfy72	LOW_SEASON	210	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc0019n3189hcbq1r4	cmaawc690000tn318kfry0ffa	cmaawc68w000pn318s4hf88ew	LOW_SEASON	220	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6bc001an318vqlwue0b	cmaawc690000tn318kfry0ffa	cmaawc68w000qn318aiu8brhp	LOW_SEASON	249	2025-05-05 09:46:50.923	2025-05-05 09:46:50.923
cmaawc6be001bn3185njj6esk	cmaawc690000un318dpj5lhnf	cmaawc68w000jn318ynxv1v1j	LOW_SEASON	120	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001cn31818ogzf1o	cmaawc690000un318dpj5lhnf	cmaawc68w000kn3186vismag9	LOW_SEASON	110	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001dn318mywwu2gw	cmaawc690000un318dpj5lhnf	cmaawc68w000ln3185iddoh9i	LOW_SEASON	100	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001en3189tq0uppx	cmaawc690000un318dpj5lhnf	cmaawc68w000mn318h0vha0nk	LOW_SEASON	170	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001fn318wk61uexh	cmaawc690000un318dpj5lhnf	cmaawc68w000nn318mczekx5y	LOW_SEASON	190	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001gn31860j3za84	cmaawc690000un318dpj5lhnf	cmaawc68w000on318iyvrfy72	LOW_SEASON	210	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001hn318acody4md	cmaawc690000un318dpj5lhnf	cmaawc68w000pn318s4hf88ew	LOW_SEASON	220	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6be001in318dvcgc76m	cmaawc690000un318dpj5lhnf	cmaawc68w000qn318aiu8brhp	LOW_SEASON	249	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001jn318fx5i5ngy	cmaawc690000sn318n84rllq1	cmaawc68w000jn318ynxv1v1j	LOW_SEASON	120	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001kn3184593j9s1	cmaawc690000sn318n84rllq1	cmaawc68w000kn3186vismag9	LOW_SEASON	110	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001ln3183g4nbq3v	cmaawc690000sn318n84rllq1	cmaawc68w000ln3185iddoh9i	LOW_SEASON	100	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001mn318qkk8qfz7	cmaawc690000sn318n84rllq1	cmaawc68w000mn318h0vha0nk	LOW_SEASON	170	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001nn318k0km6xaf	cmaawc690000sn318n84rllq1	cmaawc68w000nn318mczekx5y	LOW_SEASON	190	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001on3180zqt3aik	cmaawc690000sn318n84rllq1	cmaawc68w000on318iyvrfy72	LOW_SEASON	210	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001pn318vuid7vhk	cmaawc690000sn318n84rllq1	cmaawc68w000pn318s4hf88ew	LOW_SEASON	220	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
cmaawc6bh001qn318x8cm1q14	cmaawc690000sn318n84rllq1	cmaawc68w000qn318aiu8brhp	LOW_SEASON	249	2025-05-05 09:46:50.924	2025-05-05 09:46:50.924
\.


--
-- Data for Name: Reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Reservation" (id, "userId", "totalAmount", status, "billingAddressId", "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: Route; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Route" (id, distance, "departurePort", "arrivalPort", "geographicalZone", "createdAt", "updatedAt") FROM stdin;
cmaawc690000rn31809h8q20a	25.1	Le Palais	Vannes	BELLE_ILE_EN_MER	2025-05-05 09:46:50.916	2025-05-05 09:46:50.916
cmaawc690000sn318n84rllq1	23.7	Vannes	Le Palais	BELLE_ILE_EN_MER	2025-05-05 09:46:50.916	2025-05-05 09:46:50.916
cmaawc690000tn318kfry0ffa	8.3	Quiberon	Le Palais	BELLE_ILE_EN_MER	2025-05-05 09:46:50.916	2025-05-05 09:46:50.916
cmaawc690000un318dpj5lhnf	9	Le Palais	Quiberon	BELLE_ILE_EN_MER	2025-05-05 09:46:50.916	2025-05-05 09:46:50.916
\.


--
-- Data for Name: Seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Seat" (id, "crossingId", "seatTypeId", "bookedSeats", "reservationId", "createdAt", "updatedAt") FROM stdin;
\.


--
-- Data for Name: SeatCategory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."SeatCategory" (id, name, "createdAt", "updatedAt") FROM stdin;
cmaawc68h0004n318ur2dhdaj	PASSENGER	2025-05-05 09:46:50.897	2025-05-05 09:46:50.897
cmaawc68h0005n318kbsuke5f	VEHICLE_UNDER_2M	2025-05-05 09:46:50.897	2025-05-05 09:46:50.897
cmaawc68h0006n318brv48zme	VEHICLE_OVER_2M	2025-05-05 09:46:50.897	2025-05-05 09:46:50.897
\.


--
-- Data for Name: SeatType; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."SeatType" (id, name, description, "seatCategoryId", "createdAt", "updatedAt") FROM stdin;
cmaawc68w000jn318ynxv1v1j	ADULT	18 years and above	cmaawc68h0004n318ur2dhdaj	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000kn3186vismag9	JUNIOR	From 8 to 17 years	cmaawc68h0004n318ur2dhdaj	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000ln3185iddoh9i	CHILD	From 0 to 7 years	cmaawc68h0004n318ur2dhdaj	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000mn318h0vha0nk	CAR_UNDER_4M	Length inferior to 4m	cmaawc68h0005n318kbsuke5f	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000nn318mczekx5y	CAR_UNDER_5M	Length inferior to 5m	cmaawc68h0005n318kbsuke5f	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000on318iyvrfy72	VAN	Utility vehicle	cmaawc68h0006n318brv48zme	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000pn318s4hf88ew	TRUCK	Heavy transport vehicle	cmaawc68h0006n318brv48zme	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
cmaawc68w000qn318aiu8brhp	CAMPING_CAR	Vehicle used for camping	cmaawc68h0006n318brv48zme	2025-05-05 09:46:50.913	2025-05-05 09:46:50.913
\.


--
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."User" (id, name, email, "emailVerified", image, password, role, "createdAt", "updatedAt") FROM stdin;
\.


--
-- Name: Account Account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Account"
    ADD CONSTRAINT "Account_pkey" PRIMARY KEY (provider, "providerAccountId");


--
-- Name: BillingAddress BillingAddress_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BillingAddress"
    ADD CONSTRAINT "BillingAddress_pkey" PRIMARY KEY (id);


--
-- Name: BoatCategoryCapacity BoatCategoryCapacity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BoatCategoryCapacity"
    ADD CONSTRAINT "BoatCategoryCapacity_pkey" PRIMARY KEY (id);


--
-- Name: Boat Boat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Boat"
    ADD CONSTRAINT "Boat_pkey" PRIMARY KEY (id);


--
-- Name: CaptainLog CaptainLog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CaptainLog"
    ADD CONSTRAINT "CaptainLog_pkey" PRIMARY KEY (id);


--
-- Name: Contact Contact_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Contact"
    ADD CONSTRAINT "Contact_pkey" PRIMARY KEY (id);


--
-- Name: Crossing Crossing_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Crossing"
    ADD CONSTRAINT "Crossing_pkey" PRIMARY KEY (id);


--
-- Name: Pricing Pricing_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Pricing"
    ADD CONSTRAINT "Pricing_pkey" PRIMARY KEY (id);


--
-- Name: Reservation Reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reservation"
    ADD CONSTRAINT "Reservation_pkey" PRIMARY KEY (id);


--
-- Name: Route Route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Route"
    ADD CONSTRAINT "Route_pkey" PRIMARY KEY (id);


--
-- Name: SeatCategory SeatCategory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SeatCategory"
    ADD CONSTRAINT "SeatCategory_pkey" PRIMARY KEY (id);


--
-- Name: SeatType SeatType_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SeatType"
    ADD CONSTRAINT "SeatType_pkey" PRIMARY KEY (id);


--
-- Name: Seat Seat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Seat"
    ADD CONSTRAINT "Seat_pkey" PRIMARY KEY (id);


--
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);


--
-- Name: BoatCategoryCapacity_boatId_categoryId_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "BoatCategoryCapacity_boatId_categoryId_key" ON public."BoatCategoryCapacity" USING btree ("boatId", "categoryId");


--
-- Name: User_email_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "User_email_key" ON public."User" USING btree (email);


--
-- Name: Account Account_userId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Account"
    ADD CONSTRAINT "Account_userId_fkey" FOREIGN KEY ("userId") REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: BoatCategoryCapacity BoatCategoryCapacity_boatId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BoatCategoryCapacity"
    ADD CONSTRAINT "BoatCategoryCapacity_boatId_fkey" FOREIGN KEY ("boatId") REFERENCES public."Boat"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: BoatCategoryCapacity BoatCategoryCapacity_categoryId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BoatCategoryCapacity"
    ADD CONSTRAINT "BoatCategoryCapacity_categoryId_fkey" FOREIGN KEY ("categoryId") REFERENCES public."SeatCategory"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: CaptainLog CaptainLog_crossingId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CaptainLog"
    ADD CONSTRAINT "CaptainLog_crossingId_fkey" FOREIGN KEY ("crossingId") REFERENCES public."Crossing"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Crossing Crossing_boatId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Crossing"
    ADD CONSTRAINT "Crossing_boatId_fkey" FOREIGN KEY ("boatId") REFERENCES public."Boat"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Crossing Crossing_routeId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Crossing"
    ADD CONSTRAINT "Crossing_routeId_fkey" FOREIGN KEY ("routeId") REFERENCES public."Route"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Pricing Pricing_routeId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Pricing"
    ADD CONSTRAINT "Pricing_routeId_fkey" FOREIGN KEY ("routeId") REFERENCES public."Route"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Pricing Pricing_typeId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Pricing"
    ADD CONSTRAINT "Pricing_typeId_fkey" FOREIGN KEY ("typeId") REFERENCES public."SeatType"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Reservation Reservation_billingAddressId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reservation"
    ADD CONSTRAINT "Reservation_billingAddressId_fkey" FOREIGN KEY ("billingAddressId") REFERENCES public."BillingAddress"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Reservation Reservation_userId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reservation"
    ADD CONSTRAINT "Reservation_userId_fkey" FOREIGN KEY ("userId") REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: SeatType SeatType_seatCategoryId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SeatType"
    ADD CONSTRAINT "SeatType_seatCategoryId_fkey" FOREIGN KEY ("seatCategoryId") REFERENCES public."SeatCategory"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Seat Seat_crossingId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Seat"
    ADD CONSTRAINT "Seat_crossingId_fkey" FOREIGN KEY ("crossingId") REFERENCES public."Crossing"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Seat Seat_reservationId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Seat"
    ADD CONSTRAINT "Seat_reservationId_fkey" FOREIGN KEY ("reservationId") REFERENCES public."Reservation"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Seat Seat_seatTypeId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Seat"
    ADD CONSTRAINT "Seat_seatTypeId_fkey" FOREIGN KEY ("seatTypeId") REFERENCES public."SeatType"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

