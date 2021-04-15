
-- Create table
create table RED_RECORD
(
  ID          NUMBER(11) not null,
  USER_ID     NUMBER(11) not null,
  RED_PACKET  VARCHAR2(255),
  TOTAL       NUMBER(11),
  AMOUT       NUMBER(10,2),
  IS_ACTIVE   NUMBER(4) default 1,
  CREATE_TIME DATE
)

-- Add comments to the table 
comment on table RED_RECORD
  is '发红包记录';
-- Add comments to the columns 
comment on column RED_RECORD.USER_ID
  is '用户id';
comment on column RED_RECORD.RED_PACKET
  is '红包全局唯一标识串';
comment on column RED_RECORD.TOTAL
  is '人数';

-- Create sequence 
create sequence SEQ_RED_RECORD
minvalue 1
maxvalue 9999999999999999999999999
start with 1
increment by 1
cache 20;


-- Create table
create table RED_DETAIL
(
  ID          NUMBER(11) not null,
  RECORD_ID   NUMBER(11) not null,
  AMOUNT      NUMBER(8,2),
  IS_ACTIVE   NUMBER(4) default 1,
  CREATE_TIME DATE
)

-- Add comments to the columns 
comment on column RED_DETAIL.RECORD_ID
  is '红包记录id';
comment on column RED_DETAIL.AMOUNT
  is '金额.';

-- Create sequence 
create sequence SEQ_RED_DETAIL
minvalue 1
maxvalue 999999999999999999999
start with 1
increment by 1
cache 20;

-- Create table
create table RED_ROB_RECORD
(
  ID         NUMBER(11) not null,
  USER_ID    NUMBER(11),
  RED_PACKET VARCHAR2(255),
  AMOUNT     NUMBER(8,2),
  ROB_TIME   DATE,
  IS_ACTIVE  VARCHAR2(255) default 1
)

-- Add comments to the columns 
comment on column RED_ROB_RECORD.RED_PACKET
  is '红包标识串';
comment on column RED_ROB_RECORD.AMOUNT
  is '红包金额.';
-- Create sequence 
create sequence SEQ_RED_ROB_RECORD
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
