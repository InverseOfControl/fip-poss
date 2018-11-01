--------------------------------------------------------
--  文件已创建 - 星期二-八月-14-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CRM_ATTACHMENT
--------------------------------------------------------

	CREATE TABLE POSS.CRM_ATTACHMENT (	
  ID NUMBER(18,0) NOT NULL ENABLE, 
	CONTENT_TYPE VARCHAR2(64 CHAR), 
	FILE_PATH VARCHAR2(100 CHAR), 
	FILE_SIZE NUMBER(18,0), 
	ORIGINAL_NAME VARCHAR2(255 CHAR), 
	SUFFIX VARCHAR2(20 CHAR), 
	ATT_TYPE VARCHAR2(20 CHAR), 
	UPLOAD_TIME DATE, 
	MEMBER_ID NUMBER(18,0), 
	 CONSTRAINT CRM_ATTACHMENT_PK PRIMARY KEY (ID)
   ) TABLESPACE TS_DATA ;  
--------------------------------------------------------
--  DDL for Table CRM_MEMBER
--------------------------------------------------------

  CREATE TABLE POSS.CRM_MEMBER (	
  ID NUMBER(18,0) NOT NULL ENABLE, 
	PASSWORD VARCHAR2(128 CHAR) NOT NULL ENABLE, 
	USER_NAME VARCHAR2(64 CHAR) NOT NULL ENABLE, 
	STATUS NUMBER(10,0), 
	EMAIL VARCHAR2(64 CHAR), 
	AVATAR VARCHAR2(255 CHAR), 
	GENDER VARCHAR2(255 CHAR), 
	HIREDATE DATE, 
	REAL_NAME VARCHAR2(64 CHAR) NOT NULL ENABLE, 
	TELEPHONE VARCHAR2(64 CHAR), 
	 CONSTRAINT CRM_MEMBER_PK PRIMARY KEY (ID)
   ) TABLESPACE TS_DATA ;

  CREATE UNIQUE INDEX POSS.CRM_MEMBER_INDEX1 ON POSS.CRM_MEMBER (USER_NAME) TABLESPACE TS_DATA ;
--------------------------------------------------------
--  DDL for Table CRM_MEMBER_ROLE
--------------------------------------------------------

  CREATE TABLE POSS.CRM_MEMBER_ROLE (	
  MEMBER_ID NUMBER(18,0), 
	ROLE_ID NUMBER(18,0)
   )TABLESPACE TS_DATA ;
--------------------------------------------------------
--  DDL for Table CRM_RESOURCE
--------------------------------------------------------

  CREATE TABLE POSS.CRM_RESOURCE(	
  ID NUMBER(18,0) NOT NULL ENABLE, 
	FUN_URLS VARCHAR2(512 CHAR), 
	MENU_URL VARCHAR2(128 CHAR), 
	RES_KEY VARCHAR2(128 CHAR), 
	RES_NAME VARCHAR2(128 CHAR), 
	RES_TYPE VARCHAR2(20 CHAR), 
	STATUS NUMBER(2,0), 
	WEIGHT NUMBER(10,0), 
	PARENT_ID NUMBER(18,0), 
	 CONSTRAINT CRM_RESOURCE_PK PRIMARY KEY (ID)
   ) TABLESPACE TS_DATA ;

  CREATE UNIQUE INDEX POSS.CRM_RESOURCE_INDEX1 ON POSS.CRM_RESOURCE (RES_KEY) TABLESPACE TS_DATA ;
  CREATE INDEX POSS.CRM_RESOURCE_INDEX2 ON POSS.CRM_RESOURCE (PARENT_ID) TABLESPACE TS_DATA ;
--------------------------------------------------------
--  DDL for Table CRM_ROLE
--------------------------------------------------------

  CREATE TABLE POSS.CRM_ROLE (	
  ID NUMBER(18,0) NOT NULL ENABLE, 
	DESCRIPTION VARCHAR2(512 CHAR), 
	ROLE_NAME VARCHAR2(30 CHAR) NOT NULL ENABLE, 
	STATUS NUMBER(10,0) NOT NULL ENABLE, 
	 CONSTRAINT CRM_ROLE_PK PRIMARY KEY (ID)
   )TABLESPACE TS_DATA ;

   COMMENT ON COLUMN POSS.CRM_ROLE.DESCRIPTION IS '描述';
   COMMENT ON COLUMN POSS.CRM_ROLE.ROLE_NAME IS '角色名称';
   COMMENT ON COLUMN POSS.CRM_ROLE.STATUS IS '状态：1有效；0无效';

  CREATE UNIQUE INDEX POSS.CRM_ROLE_INDEX1 ON POSS.CRM_ROLE (ROLE_NAME) TABLESPACE TS_DATA ;
--------------------------------------------------------
--  DDL for Table CRM_ROLE_RESOURCE
--------------------------------------------------------

  CREATE TABLE POSS.CRM_ROLE_RESOURCE (	
  ROLE_ID NUMBER(18,0) NOT NULL ENABLE, 
	RESOURCE_ID NUMBER(18,0) NOT NULL ENABLE
   ) TABLESPACE TS_DATA ;

  CREATE INDEX POSS.CRM_ROLE_RESOURCE_INDEX1 ON POSS.CRM_ROLE_RESOURCE (ROLE_ID) TABLESPACE TS_DATA ;
  
  --创建一个公用的crm sequence
  CREATE SEQUENCE  POSS.SEQ_CRM_ID  MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  CYCLE ;
  
  
--插入测试数据  
REM INSERTING into POSS.CRM_ATTACHMENT
SET DEFINE OFF;
REM INSERTING into POSS.CRM_MEMBER
SET DEFINE OFF;
--账户/密码：admin/0000
Insert into POSS.CRM_MEMBER (ID,PASSWORD,USER_NAME,STATUS,EMAIL,AVATAR,GENDER,HIREDATE,REAL_NAME,TELEPHONE) values (1,'9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0','admin',1,'768870379@qq.com',null,'GIRL',to_date('30-6月 -17','DD-MON-RR'),'管理员','18676037292');
Insert into POSS.CRM_MEMBER (ID,PASSWORD,USER_NAME,STATUS,EMAIL,AVATAR,GENDER,HIREDATE,REAL_NAME,TELEPHONE) values (31,'9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0','gson',0,'wmails@126.com',null,'BOY',to_date('08-5月 -17','DD-MON-RR'),'郭华','13203314875');
REM INSERTING into POSS.CRM_MEMBER_ROLE
SET DEFINE OFF;
Insert into POSS.CRM_MEMBER_ROLE (MEMBER_ID,ROLE_ID) values (1,1);
REM INSERTING into POSS.CRM_RESOURCE
SET DEFINE OFF;
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (1,'#','','system','系统管理','MENU',1,0,null);                                                                                          
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (3,'/system/member/list','/system/member','system-member','用户管理','MENU',1,null,1);                                                  
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (10,'/system/role/list,/system/role/resource/tree','/system/role','system-role','角色管理','MENU',1,null,1);                            
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (11,'/system/resource/list','/system/resource','system-resource','资源管理','MENU',1,null,1);                                           
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (12,'/system/role/save','','role-create','创建角色','FUNCTION',1,null,10);                                                                               
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (13,'/system/role/delete','','role-delete','删除角色','FUNCTION',1,null,10);                                                            
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (14,'/system/role/update,/system/role/save','','role-save','保存编辑','FUNCTION',1,null,10);                                            
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (17,'/system/role/resource/save','','reole-resource-save','分配资源','FUNCTION',1,null,10);                                             
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (18,'/system/resource/form,/system/resource/parent/tree,/system/resource/save','','resource-create','创建资源','FUNCTION',1,null,11);   
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (19,'/system/resource/form,/system/resource/parent/tree,/system/resource/save','','resource-edit','编辑','FUNCTION',1,null,11);       
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (20,'/system/resource/delete','','resource-delete','删除','FUNCTION',1,null,11);                                                      
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (21,'/system/member/form,/system/member/save','','member-create','创建用户','FUNCTION',1,null,3);                                       
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (22,'/system/member/delete','','member-delete','删除用户','FUNCTION',1,null,3);                                                         
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (23,'/system/member/form,/system/member/update','','member-edit','编辑用户','FUNCTION',1,null,3);                                       
Insert into POSS.CRM_RESOURCE (ID,FUN_URLS,MENU_URL,RES_KEY,RES_NAME,RES_TYPE,STATUS,WEIGHT,PARENT_ID) values (26,'/system/member/password/reset','','member-reset-password','重置密码','FUNCTION',1,null,3);                                         
REM INSERTING into POSS.CRM_ROLE
SET DEFINE OFF;
Insert into POSS.CRM_ROLE (ID,DESCRIPTION,ROLE_NAME,STATUS) values (1,'有系统所有权限','管理员',1);
Insert into POSS.CRM_ROLE (ID,DESCRIPTION,ROLE_NAME,STATUS) values (2,'主要是上课，可以查看学员管理模块','教员',1);
REM INSERTING into POSS.CRM_ROLE_RESOURCE
SET DEFINE OFF;
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,1);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,3);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,21);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,22);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,23);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,10);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,12);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,13);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,14);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,17);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,11);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,18);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,19);
Insert into POSS.CRM_ROLE_RESOURCE (ROLE_ID,RESOURCE_ID) values (1,20);