INSERT INTO profile(name,surname,email,phone,password,created_date,role,status,updated_date,visible)
VALUES ('Ali','Aliyev','hojiakbarandaqulov5@gmail.com','+998995092376','81dc9bdb52d04dc2036dbd8313ed055',now(),'ADMIN','ACTIVE',null,true)
ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile