INSERT INTO `company` (`id`,`created_at`,`created_by`,`email`,`enabled`,`modified_at`,`modified_by`,`city`,`country`,`line1`,`zip_code`,`name`) VALUES
(1,'2017-07-03 02:43:35','Md Zahid Raza','itsupport@laguna-clothing.com',false,'2017-07-03 02:43:35','Md Zahid Raza','Bangalore','India','#50, JP Nagar','560047','Laguna Clothing Pvt. Ltd.');

INSERT INTO users (id, created_at, created_by, enabled, modified_at, modified_by, account_expired, account_locked, credential_expired, email, mobile, name, otp, otp_sent_at, password, retry_count, roles, username) VALUES
(1,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'zahid7292@gmail.com','8987525008','Md Zahid Raza',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','zahid7292'),
(2,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'jawed@gmail.com','8987525008','Md Jawed Akhtar',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','jawed_akhtar'),
(3,'2017-07-02 20:39:15.831',NULL,true,'2017-07-02 20:39:15.831',NULL,false,false,false,'taufeeque8@gmail.com','8987525008','Md Taufeeque Alam',NULL,NULL,'$2a$10$c6Yb6G41usEidZAc8Oc6JuL09VmyrypL1V80l9/k0upXdtCvomRNK',NULL,'ROLE_ADMIN','taufeeque8');


INSERT INTO product (id, created_at, created_by, enabled, modified_at, modified_by, description, name, product_prefix) VALUES
(1,'2017-07-03 02:43:07','Md Zahid Raza',true,'2017-07-03 02:43:07','Md Zahid Raza','A Timeline Tracking Software for Apparrel Industories','Time And Action Calender','TNA'),
(2,'2017-07-03 02:43:07','Md Zahid Raza',true,'2017-07-03 02:43:07','Md Zahid Raza','A Issue Tracking Software','Andon System','ANDON');

INSERT INTO `license` (`id`,`created_at`,`created_by`,`enabled`,`modified_at`,`modified_by`,`activated`,`license_type`,`license_flavour`,`mac_id`,`no_of_users`,`product_code`,`product_key`,`purchased_on`,`activated_on`,`validity`,`client_id`,`product_id`) VALUES
(1,'2017-07-06 01:11:37','Md Zahid Raza','0','2017-07-06 01:11:37','Md Zahid Raza','0','TRIAL','EXPRESS',NULL,20,'TNA5765713','RTRD-DEQV-XNVO-ORUC','2017-07-06',NULL,30,1,1),
(2,'2017-07-06 01:13:06','Md Zahid Raza','0','2017-07-06 01:13:06','Md Zahid Raza','0','ANNUAL','ULTIMATE',NULL,20,'ANDON8831329','EPAK-HFOS-WKNM-EJPM','2017-07-06',NULL,30,1,2);
