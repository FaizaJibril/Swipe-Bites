rollback;

drop table if exists app_users,restaurant, disliked_restaurants, images, liked_restaurants, match_history, user_restaurant_match cascade;

CREATE TABLE IF NOT EXISTS public.app_users
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    password_hash character varying(200) NOT NULL,
    role character varying(50) NOT NULL,
	password varchar(50),
	full_name varchar (250),
	preferences TEXT,
    PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT EXISTS public.restaurant
(
    restaurant_id serial NOT NULL,
    name character varying(250) NOT NULL,
    cuisine character varying(250) NOT NULL,
    price_range numeric(10, 0) NOT NULL,
    latitude numeric(10, 6) NOT NULL,
    longitude numeric(10, 6) NOT NULL,
    address character varying(250) NOT NULL,
    photo_url character varying(255),
    reviews text,
    PRIMARY KEY (restaurant_id)
);

CREATE TABLE IF NOT EXISTS public.disliked_restaurants
(
    user_id serial,
    restaurant_id integer,
	FOREIGN KEY (user_id) REFERENCES app_users(user_id),
	FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE IF NOT EXISTS public.images
(
    image_id integer NOT NULL,
    file_name character varying(200),
    mime_type character varying(100),
    file_size integer,
    file_blob bytea,
    active boolean
);

CREATE TABLE IF NOT EXISTS public.liked_restaurants
(
    user_id serial,
    restaurant_id integer,
	FOREIGN KEY (user_id) REFERENCES app_users(user_id),
	FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE IF NOT EXISTS public.match_history
(
    match_id serial NOT NULL,
    user_id integer,
    restaurant_id integer,
    matched_at timestamp without time zone,
    selected boolean,
    PRIMARY KEY (match_id),
	FOREIGN KEY (user_id) REFERENCES app_users(user_id),
	FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);


CREATE TABLE IF NOT EXISTS public.user_restaurant_match
(
	match_id serial NOT NULL,
    user_id integer,
    restaurant_id integer,
    swipe_direction character varying(8),
    PRIMARY KEY (match_id),
	FOREIGN KEY (user_id) REFERENCES app_users(user_id),
	FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
	
);


