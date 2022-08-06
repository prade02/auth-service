CREATE TABLE public.user_details
(
    id serial NOT NULL,
    user_name character varying NOT NULL,
    email character varying,
    hashed_password character varying NOT NULL,
    locked boolean NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE public.user_role
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    authority character varying NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES public.user_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);