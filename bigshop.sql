/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-04-17 16:15:53                          */
/*==============================================================*/


drop table if exists item;

drop table if exists itembuy;

drop table if exists itemcomment;

drop table if exists itemimg;

drop table if exists itemtag;

drop table if exists mall;

drop table if exists shop;

/*==============================================================*/
/* Table: item                                                  */
/*==============================================================*/
create table item
(
   itemId               int not null,
   tagId                int,
   itemName             varchar(200),
   itemDesc             varchar(500),
   buyId                numeric(10),
   buyScore             char(10),
   hotScore             int,
   favorScore           int,
   itemUrl              varchar(400),
   imgId                int,
   status               int,
   crDate               datetime,
   primary key (itemId)
);

/*==============================================================*/
/* Table: itembuy                                               */
/*==============================================================*/
create table itembuy
(
   buyId                int not null,
   mallId               int,
   shopId               int,
   buyUrl               varchar(0),
   buyPrice             decimal(20),
   status               tinyint,
   primary key (buyId)
);

/*==============================================================*/
/* Table: itemcomment                                           */
/*==============================================================*/
create table itemcomment
(
   commId               int not null,
   commTitle            varchar(100),
   commWord             varchar(200),
   userName             varchar(100),
   userIconUrl          varchar(300),
   commScore            int,
   commTime             datetime,
   status               tinyint,
   primary key (commId)
);

/*==============================================================*/
/* Table: itemimg                                               */
/*==============================================================*/
create table itemimg
(
   imgId                int not null,
   imgUrl               varchar(0),
   imgIcon              varchar(0),
   imgSmallIcon         varchar(0),
   status               int,
   primary key (imgId)
);

/*==============================================================*/
/* Table: itemtag                                               */
/*==============================================================*/
create table itemtag
(
   tagId                int not null auto_increment,
   tagWord              varchar(200),
   parentTagId          int,
   tagType              int,
   status               int,
   primary key (tagId)
);

/*==============================================================*/
/* Table: mall                                                  */
/*==============================================================*/
create table mall
(
   mallId               int not null,
   mallName             varchar(50),
   mallDesc             varchar(100),
   mallUrl              varchar(300),
   primary key (mallId)
);

/*==============================================================*/
/* Table: shop                                                  */
/*==============================================================*/
create table shop
(
   shopId               int not null,
   shopName             varchar(50),
   shopDesc             varchar(100),
   shopUrl              varchar(300),
   primary key (shopId)
);

