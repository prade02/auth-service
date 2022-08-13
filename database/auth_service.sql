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

-- PROCEDURE: public.sp_get_role_hierarchy_expression()

-- DROP PROCEDURE IF EXISTS public.sp_get_role_hierarchy_expression();

CREATE OR REPLACE PROCEDURE public.sp_get_role_hierarchy_expression(
	OUT role_hierarchy_expression character varying)
LANGUAGE 'sql'
AS $BODY$
WITH RECURSIVE reachable_roles AS (
	SELECT id, parent_authority_name, authority FROM user_authority WHERE parent_authority IS NULL
	UNION ALL
	SELECT uAuthority.id, uAuthority.parent_authority_name, uAuthority.authority FROM user_authority uAuthority INNER JOIN reachable_roles reachable ON
	uAuthority.parent_authority = reachable.id
)
SELECT string_agg(parent_authority_name || ' > ' || authority, ' \n ') as authority_hierarchy FROM reachable_roles WHERE parent_authority_name IS NOT NULL;
$BODY$;
ALTER PROCEDURE public.sp_get_role_hierarchy_expression()
    OWNER TO postgres;
