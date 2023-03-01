--
-- PostgreSQL database dump
--

-- Dumped from database version 12.11
-- Dumped by pg_dump version 14.6 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: inventory; Type: TABLE; Schema: public; Owner: csce315331_parker
--

CREATE TABLE public.inventory (
    item_id integer NOT NULL,
    item_name text,
    item_amount double precision,
    item_measurement_type text
);


ALTER TABLE public.inventory OWNER TO csce315331_parker;

--
-- Name: sales; Type: TABLE; Schema: public; Owner: csce315331_raza
--

CREATE TABLE public.sales (
    sales_id integer NOT NULL,
    startdate date,
    enddate date,
    salesubtotal numeric(10,2),
    saletax numeric(10,2),
    totalsales numeric(10,2)
);


ALTER TABLE public.sales OWNER TO csce315331_raza;

--
-- Name: sales_sales_id_seq; Type: SEQUENCE; Schema: public; Owner: csce315331_raza
--

CREATE SEQUENCE public.sales_sales_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sales_sales_id_seq OWNER TO csce315331_raza;

--
-- Name: sales_sales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: csce315331_raza
--

ALTER SEQUENCE public.sales_sales_id_seq OWNED BY public.sales.sales_id;


--
-- Name: teammembers; Type: TABLE; Schema: public; Owner: csce315331_epsilon_master
--

CREATE TABLE public.teammembers (
    student_name text NOT NULL,
    section integer,
    favorite_movie text
);


ALTER TABLE public.teammembers OWNER TO csce315331_epsilon_master;

--
-- Name: sales sales_id; Type: DEFAULT; Schema: public; Owner: csce315331_raza
--

ALTER TABLE ONLY public.sales ALTER COLUMN sales_id SET DEFAULT nextval('public.sales_sales_id_seq'::regclass);


--
-- Data for Name: inventory; Type: TABLE DATA; Schema: public; Owner: csce315331_parker
--

COPY public.inventory (item_id, item_name, item_amount, item_measurement_type) FROM stdin;
1	Pita Bread	37	count
2	Lamb	28.5	pounds
3	Chicken	49.15	pounds
4	Lettuce	23	heads
5	Onions	54	count
6	Beef	14	pounds
7	Garlic	30	pounds
8	Sea Salt	30	pounds
9	Black Pepper	25	pounds
10	Tomatoes	97	count
11	Coca-Cola Soda Syrup	16	gallons
12	Sprite Soda Syrup	17	gallons
13	Root Beer Soda Syrup	24	gallons
14	Ice Tea	36	gallons
15	Hummus	15	gallons
16	Tzatziki Sauce	84	gallons
17	Rice	26	pounds
18	Sour Cream	12	pounds
19	Bowls	5000	count
20	Utensils	800	count
21	Serving Spoons	6	count
22	Checkout Register/Computer	2	count
\.


--
-- Data for Name: sales; Type: TABLE DATA; Schema: public; Owner: csce315331_raza
--

COPY public.sales (sales_id, startdate, enddate, salesubtotal, saletax, totalsales) FROM stdin;
1	2022-01-01	2022-01-07	17272.51	1079.53	18352.04
2	2022-01-08	2022-01-14	20367.05	1272.94	21639.99
3	2022-01-15	2022-01-21	18578.49	1161.16	19739.65
4	2022-01-22	2022-01-28	19778.81	1236.18	21014.99
5	2022-01-29	2022-02-04	21657.58	1353.60	23011.18
6	2022-02-05	2022-02-11	20972.03	1310.75	22282.78
7	2022-02-12	2022-02-18	18303.05	1143.94	19446.99
8	2022-02-19	2022-02-25	17816.84	1113.55	18930.39
9	2022-02-26	2022-03-04	16388.51	1024.28	17412.79
10	2022-03-05	2022-03-11	17019.40	1063.71	18083.11
11	2022-03-12	2022-03-18	20638.84	1289.93	21928.77
12	2022-03-19	2022-03-25	20266.88	1266.68	21533.56
13	2022-03-26	2022-04-01	17570.86	1098.18	18669.04
14	2022-04-02	2022-04-08	18611.45	1163.22	19774.67
15	2022-04-09	2022-04-15	16856.20	1053.51	17909.71
16	2022-04-16	2022-04-22	21936.16	1371.01	23307.17
17	2022-04-23	2022-04-29	21854.25	1365.89	23220.14
18	2022-04-30	2022-05-06	17846.12	1115.38	18961.50
19	2022-05-07	2022-05-13	17442.90	1090.18	18533.08
20	2022-05-14	2022-05-20	16478.37	1029.90	17508.27
21	2022-05-21	2022-05-27	21643.54	1352.72	22996.26
22	2022-05-28	2022-06-03	20061.90	1253.87	21315.77
23	2022-06-04	2022-06-10	19079.93	1192.50	20272.43
24	2022-06-11	2022-06-17	16822.25	1051.39	17873.64
25	2022-06-18	2022-06-24	17365.08	1085.32	18450.40
26	2022-06-25	2022-07-01	18315.90	1144.74	19460.64
27	2022-07-02	2022-07-08	17575.32	1098.46	18673.78
28	2022-07-09	2022-07-15	19458.44	1216.15	20674.59
29	2022-07-16	2022-07-22	16568.42	1035.53	17603.95
30	2022-07-23	2022-07-29	17976.62	1123.54	19100.16
31	2022-07-30	2022-08-05	18975.94	1186.00	20161.94
32	2022-08-06	2022-08-12	18003.24	1125.20	19128.44
33	2022-08-13	2022-08-19	19346.71	1209.17	20555.88
34	2022-08-20	2022-08-26	21908.44	1369.28	23277.72
35	2022-08-27	2022-09-02	20962.12	1310.13	22272.25
36	2022-09-03	2022-09-09	17662.79	1103.92	18766.71
37	2022-09-10	2022-09-16	18614.81	1163.43	19778.24
38	2022-09-17	2022-09-23	17788.39	1111.77	18900.16
39	2022-09-24	2022-09-30	27949.42	1863.30	29812.72
40	2022-10-01	2022-10-07	18089.46	1130.59	19220.05
41	2022-10-08	2022-10-14	17500.37	1093.77	18594.14
42	2022-10-15	2022-10-21	26980.36	1798.69	28779.05
43	2022-10-22	2022-10-28	18296.69	1143.54	19440.23
44	2022-10-29	2022-11-04	20089.90	1255.62	21345.52
45	2022-11-05	2022-11-11	20034.59	1252.16	21286.75
46	2022-11-12	2022-11-18	27761.35	1850.75	29612.11
47	2022-11-19	2022-11-25	17070.28	1066.89	18137.17
48	2022-11-26	2022-12-02	18095.84	1130.99	19226.83
49	2022-12-03	2022-12-09	18192.50	1137.03	19329.53
50	2022-12-10	2022-12-16	18509.44	1156.84	19666.28
51	2022-12-17	2022-12-23	18771.04	1173.19	19944.23
52	2022-12-24	2022-12-30	16941.04	1058.82	17999.86
53	2023-01-01	2023-01-07	16188.94	1011.81	17200.75
54	2023-01-08	2023-01-14	16467.53	1029.22	17496.75
55	2023-01-15	2023-01-21	19597.04	1224.82	20821.86
56	2023-01-22	2023-01-28	21051.83	1315.74	22367.57
57	2023-01-29	2023-02-04	19750.88	1234.43	20985.31
58	2023-02-05	2023-02-11	21285.96	1330.37	22616.33
59	2023-02-12	2023-02-18	16463.95	1029.00	17492.95
60	2023-02-19	2023-02-25	20799.22	1299.95	22099.17
61	2023-02-26	2023-03-04	18624.16	1164.01	19788.17
62	2023-03-05	2023-03-11	18998.44	1187.40	20185.84
\.


--
-- Data for Name: teammembers; Type: TABLE DATA; Schema: public; Owner: csce315331_epsilon_master
--

COPY public.teammembers (student_name, section, favorite_movie) FROM stdin;
Cameron Yoffe	901	Lord of the Rings: The Fellowship of the Ring
Jacob Parker	901	The Martian
Adam Vick	510	Zootopia
\.


--
-- Name: sales_sales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: csce315331_raza
--

SELECT pg_catalog.setval('public.sales_sales_id_seq', 1, false);


--
-- Name: inventory inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: csce315331_parker
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_pkey PRIMARY KEY (item_id);


--
-- Name: sales sales_pkey; Type: CONSTRAINT; Schema: public; Owner: csce315331_raza
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_pkey PRIMARY KEY (sales_id);


--
-- Name: teammembers teammembers_pkey; Type: CONSTRAINT; Schema: public; Owner: csce315331_epsilon_master
--

ALTER TABLE ONLY public.teammembers
    ADD CONSTRAINT teammembers_pkey PRIMARY KEY (student_name);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: useradmin
--

REVOKE ALL ON SCHEMA public FROM rdsadmin;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO useradmin;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: TABLE inventory; Type: ACL; Schema: public; Owner: csce315331_parker
--

GRANT ALL ON TABLE public.inventory TO csce315331_yoffe;
GRANT ALL ON TABLE public.inventory TO csce315331_vick;
GRANT ALL ON TABLE public.inventory TO csce315331_raza;


--
-- Name: TABLE sales; Type: ACL; Schema: public; Owner: csce315331_raza
--

GRANT SELECT ON TABLE public.sales TO csce315331_parker;
GRANT SELECT ON TABLE public.sales TO csce315331_vick;
GRANT SELECT ON TABLE public.sales TO csce315331_yoffe;


--
-- Name: TABLE teammembers; Type: ACL; Schema: public; Owner: csce315331_epsilon_master
--

GRANT ALL ON TABLE public.teammembers TO csce315331_parker;
GRANT ALL ON TABLE public.teammembers TO csce315331_yoffe;
GRANT ALL ON TABLE public.teammembers TO csce315331_vick;
GRANT ALL ON TABLE public.teammembers TO csce315331_raza;


--
-- PostgreSQL database dump complete
--

