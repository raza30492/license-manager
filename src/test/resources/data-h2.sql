

INSERT INTO users (id, created_at, created_by, enabled, modified_at, modified_by, account_expired, account_locked, credential_expired, email, mobile, name, otp, otp_sent_at, password, retry_count, roles, username) VALUES
(1,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'zahid7292@gmail.com','8987525008','Md Zahid Raza',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','zahid7292'),
(2,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'jawed@gmail.com','8987525008','Md Jawed Akhtar',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','jawed_akhtar'),
(3,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'taufeeque8@gmail.com','8987525008','Md Taufeeque Alam',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','taufeeque8');


INSERT INTO product (id, created_at, created_by, enabled, modified_at, modified_by, description, name, product_prefix) VALUES
(1,'2017-07-03 02:43:07','Md Zahid Raza',true,'2017-07-03 02:43:07','Md Zahid Raza','A Timeline Tracking Software for Apparrel Industories','Time And Action Calender','TNA'),
(2,'2017-07-03 02:43:07','Md Zahid Raza',true,'2017-07-03 02:43:07','Md Zahid Raza','A Issue Tracking Software','Andon System','ANDON');

--INSERT INTO product (id, created_at, created_by, enabled, modified_at, modified_by, description, name, product_prefix)  values
--(1, 'Time and Action Calender','A Timeline tracking software for Apparrel Companies', 'TNA' ),
----(2, 'Andon System','Issue Tracking software', 'ADN' );
