if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[f_GetPy]') and xtype in (N'FN', N'IF', N'TF'))਍ഀ
drop function [dbo].[f_GetPy]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bikee_favorite]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[bikee_favorite]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bikee_shoppingcart]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[bikee_shoppingcart]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_author_books_count]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[get_author_books_count]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_books_to_author]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[get_books_to_author]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_books_to_publisher]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[get_books_to_publisher]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_books_to_sort]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[get_books_to_sort]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_sub_categories]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[get_sub_categories]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[insertURLs]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[insertURLs]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[insert_user_ip_address]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[insert_user_ip_address]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_add_bookword]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_add_bookword]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_add_tag]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_add_tag]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_add_theme]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_add_theme]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_add_themebook]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_add_themebook]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_add_themevote]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_add_themevote]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_bookitem_add_review]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_bookitem_add_review]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_bookitem_add_reviewvote]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_bookitem_add_reviewvote]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_get_bookitem]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_get_bookitem]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_get_bookstores]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_get_bookstores]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_get_catalogitem]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_get_catalogitem]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_get_tag]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_get_tag]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_get_themeitem]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_get_themeitem]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_research_addvote]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_research_addvote]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_research_getvote_brid]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_research_getvote_brid]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_research_getvote_status]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_research_getvote_status]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sp_bk_validate_books]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)਍ഀ
drop procedure [dbo].[sp_bk_validate_books]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsd]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsd]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[abacus]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[abacus]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bauthors]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bauthors]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bhotnames]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bhotnames]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[blanguages]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[blanguages]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapAuthorBids]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapAuthorBids]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapPubBids]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapPubBids]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapSortBids]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapSortBids]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapSortwordBids]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapSortwordBids]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsa]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsa]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsb]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsb]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsc]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsc]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidse]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidse]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsf]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsf]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsg]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsg]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsh]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsh]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsi]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsi]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsj]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsj]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsk]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsk]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsl]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsl]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsm]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsm]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsn]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsn]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidso]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidso]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsother]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsother]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsp]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsp]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsq]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsq]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsr]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsr]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidss]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidss]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidst]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidst]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsu]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsu]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsv]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsv]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsw]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsw]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsx]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsx]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsy]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsy]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmapnamebidsz]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmapnamebidsz]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmaptagitem]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmaptagitem]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bmembers]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bmembers]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookcategories]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookcategories]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookresearchs]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookresearchs]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookresearchvotes]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookresearchvotes]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookreviewVotes]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookreviewVotes]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookreviews]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookreviews]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[books]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[books]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[booksearchmap]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[booksearchmap]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[booksites]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[booksites]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[booksorts]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[booksorts]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookstores]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookstores]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[booktags]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[booktags]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookthemebooks]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookthemebooks]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookthemes]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookthemes]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookthemevoteWishs]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookthemevoteWishs]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bookthemevotes]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bookthemevotes]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[border]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[border]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bourlcatalog]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bourlcatalog]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[bourls]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[bourls]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[favorite]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[favorite]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ip_address]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[ip_address]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ismap]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[ismap]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[psmap]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[psmap]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pub2]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[pub2]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[publishers]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[publishers]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[shoppingcart]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[shoppingcart]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[shoppingclasses]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[shoppingclasses]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tonormalpages]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[tonormalpages]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[urls]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[urls]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[urlsdd]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[urlsdd]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[useripaddress]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[useripaddress]਍ഀ
GO਍ഀ
਍ഀ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[word2]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)਍ഀ
drop table [dbo].[word2]਍ഀ
GO਍ഀ
਍ഀ
if not exists (select * from master.dbo.syslogins where loginname = N'bikee')਍ഀ
BEGIN਍ഀ
	declare @logindb nvarchar(132), @loginlang nvarchar(132) select @logindb = N'shopping', @loginlang = N'简体中文'਍ഀ
	if @logindb is null or not exists (select * from master.dbo.sysdatabases where name = @logindb)਍ഀ
		select @logindb = N'master'਍ഀ
	if @loginlang is null or (not exists (select * from master.dbo.syslanguages where name = @loginlang) and @loginlang <> N'us_english')਍ഀ
		select @loginlang = @@language਍ഀ
	exec sp_addlogin N'bikee', null, @logindb, @loginlang਍ഀ
END਍ഀ
GO਍ഀ
਍ഀ
if not exists (select * from dbo.sysusers where name = N'bikee' and uid < 16382)਍ഀ
	EXEC sp_grantdbaccess N'bikee', N'bikee'਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsd] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[abacus] (਍ഀ
	[abacusId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[memberId] [int] NOT NULL ,਍ഀ
	[dangdangPrice] [decimal](18, 2) NOT NULL ,਍ഀ
	[joyoPrice] [decimal](18, 2) NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bauthors] (਍ഀ
	[aid] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[aname] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[country] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[age] [float] NULL ,਍ഀ
	[sex] [bit] NULL ,਍ഀ
	[introduce] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [float] NULL ,਍ഀ
	[cr_date] [smalldatetime] NULL ,਍ഀ
	[hit] [int] NOT NULL ,਍ഀ
	[fchar] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[hstatus] [int] NOT NULL ,਍ഀ
	[bookcount] [int] NOT NULL ,਍ഀ
	[bidcount] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bhotnames] (਍ഀ
	[hid] [int] NOT NULL ,਍ഀ
	[word] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[category] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[blanguages] (਍ഀ
	[langid] [int] NOT NULL ,਍ഀ
	[lang] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[clang] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapAuthorBids] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapPubBids] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapSortBids] (਍ഀ
	[mid] [int] NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapSortwordBids] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsa] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsb] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsc] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidse] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsf] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsg] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsh] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsi] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsj] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsk] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsl] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsm] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsn] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidso] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsother] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsp] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsq] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsr] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidss] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidst] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsu] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsv] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsw] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsx] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsy] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmapnamebidsz] (਍ഀ
	[bword] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[bids] [text] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmaptagitem] (਍ഀ
	[bid] [int] NOT NULL ,਍ഀ
	[tid] [int] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bmembers] (਍ഀ
	[memberId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[memberName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[passwords] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[email] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[status] [int] NULL ,਍ഀ
	[username] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[recommend] [text] COLLATE Chinese_PRC_CI_AS NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookcategories] (਍ഀ
	[catId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[catName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[layer] [int] NOT NULL ,਍ഀ
	[parentCat] [int] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[hit] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookresearchs] (਍ഀ
	[brid] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[rname] [varchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[selected] [bit] NOT NULL ,਍ഀ
	[summary] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[op1] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes1] [int] NOT NULL ,਍ഀ
	[op2] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes2] [int] NOT NULL ,਍ഀ
	[op3] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes3] [int] NOT NULL ,਍ഀ
	[op4] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes4] [int] NOT NULL ,਍ഀ
	[op5] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes5] [int] NOT NULL ,਍ഀ
	[op6] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes6] [int] NOT NULL ,਍ഀ
	[op7] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes7] [int] NOT NULL ,਍ഀ
	[op8] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes8] [int] NOT NULL ,਍ഀ
	[op9] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes9] [int] NOT NULL ,਍ഀ
	[op10] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[votes10] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookresearchvotes] (਍ഀ
	[brid] [int] NOT NULL ,਍ഀ
	[ipaddress] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[v1] [int] NOT NULL ,਍ഀ
	[v2] [int] NOT NULL ,਍ഀ
	[v3] [int] NOT NULL ,਍ഀ
	[v4] [int] NOT NULL ,਍ഀ
	[v5] [int] NOT NULL ,਍ഀ
	[v6] [int] NOT NULL ,਍ഀ
	[v7] [int] NOT NULL ,਍ഀ
	[v8] [int] NOT NULL ,਍ഀ
	[v9] [int] NOT NULL ,਍ഀ
	[v10] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookreviewVotes] (਍ഀ
	[brid] [bigint] NOT NULL ,਍ഀ
	[uid] [int] NOT NULL ,਍ഀ
	[wish] [bit] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookreviews] (਍ഀ
	[brid] [bigint] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[bid] [int] NOT NULL ,਍ഀ
	[uid] [int] NOT NULL ,਍ഀ
	[title] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[context] [text] COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[votes] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[books] (਍ഀ
	[bid] [int] NOT NULL ,਍ഀ
	[bname] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[authorStr] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[abid] [int] NOT NULL ,਍ഀ
	[sid] [int] NOT NULL ,਍ഀ
	[s2id] [int] NOT NULL ,਍ഀ
	[isbn] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[stores] [int] NOT NULL ,਍ഀ
	[price] [float] NOT NULL ,਍ഀ
	[imgName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[simgName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bImgName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[summary] [nvarchar] (2000) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[outline] [nvarchar] (3000) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[langid] [int] NULL ,਍ഀ
	[pid] [int] NOT NULL ,਍ഀ
	[pbDate] [smalldatetime] NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[up_date] [smalldatetime] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[hit] [int] NOT NULL ,਍ഀ
	[sstatus] [int] NOT NULL ,਍ഀ
	[stime] [smalldatetime] NOT NULL ,਍ഀ
	[aid] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[booksearchmap] (਍ഀ
	[bword] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[itemcount] [float] NULL ,਍ഀ
	[type] [float] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[booksites] (਍ഀ
	[siteId] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[siteName] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[urlStr] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[searchUrl] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[payServiceUrl] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[deliverUrl] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[logo] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[descrip] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [float] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[booksorts] (਍ഀ
	[sid] [int] NOT NULL ,਍ഀ
	[sname] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[parentSid] [int] NOT NULL ,਍ഀ
	[bookcount] [int] NOT NULL ,਍ഀ
	[lev] [int] NOT NULL ,਍ഀ
	[popular] [int] NOT NULL ,਍ഀ
	[status] [char] (10) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[hit] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookstores] (਍ഀ
	[bsid] [int] NOT NULL ,਍ഀ
	[urlStr] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[urlkey] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bid] [int] NOT NULL ,਍ഀ
	[hasStock] [bit] NULL ,਍ഀ
	[ps] [decimal](18, 2) NOT NULL ,਍ഀ
	[vipPs] [decimal](18, 2) NULL ,਍ഀ
	[cPs] [decimal](18, 2) NULL ,਍ഀ
	[psImg] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[vipPsImg] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[cPsImg] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[siteID] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[imgUrl] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bname] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bauthor] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bisbn] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[pid] [int] NULL ,਍ഀ
	[pname] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[version] [int] NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[create_date] [smalldatetime] NOT NULL ,਍ഀ
	[up_date] [smalldatetime] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[booktags] (਍ഀ
	[tid] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[tname] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[cr_uid] [int] NOT NULL ,਍ഀ
	[hits] [bigint] NOT NULL ,਍ഀ
	[status] [char] (10) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookthemebooks] (਍ഀ
	[tbid] [bigint] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[thid] [int] NOT NULL ,਍ഀ
	[bid] [int] NOT NULL ,਍ഀ
	[cr_uid] [int] NOT NULL ,਍ഀ
	[votes] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookthemes] (਍ഀ
	[thid] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[thname] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[summary] [nvarchar] (3000) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[cr_uid] [int] NULL ,਍ഀ
	[hits] [bigint] NOT NULL ,਍ഀ
	[status] [int] NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookthemevoteWishs] (਍ഀ
	[bwid] [int] NOT NULL ,਍ഀ
	[wdesc] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bookthemevotes] (਍ഀ
	[tbid] [bigint] NOT NULL ,਍ഀ
	[uid] [int] NOT NULL ,਍ഀ
	[wish] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[border] (਍ഀ
	[orderId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[memberId] [int] NOT NULL ,਍ഀ
	[orderNumber] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[productIdStr] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[orderDate] [smalldatetime] NOT NULL ,਍ഀ
	[orderRemark] [ntext] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[state] [int] NOT NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bourlcatalog] (਍ഀ
	[cid] [int] NOT NULL ,਍ഀ
	[cname] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[title] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[bourls] (਍ഀ
	[u_id] [float] NULL ,਍ഀ
	[cid] [float] NULL ,਍ഀ
	[siteId] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[title] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[urlStr] [ntext] COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [float] NULL ਍ഀ
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[favorite] (਍ഀ
	[favId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[memberId] [int] NOT NULL ,਍ഀ
	[booksId] [int] NOT NULL ,਍ഀ
	[states] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[ip_address] (਍ഀ
	[ip1] [float] NULL ,਍ഀ
	[ip2] [float] NULL ,਍ഀ
	[country] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[city] [nvarchar] (255) COLLATE Chinese_PRC_CI_AS NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[ismap] (਍ഀ
	[isbn] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[uid] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[psmap] (਍ഀ
	[pid] [int] NOT NULL ,਍ഀ
	[uid] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[pub2] (਍ഀ
	[pname] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[publishers] (਍ഀ
	[pid] [int] NOT NULL ,਍ഀ
	[pname] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[pyname] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[bookcount] [int] NOT NULL ,਍ഀ
	[fchar] [char] (2) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[status] [int] NOT NULL ,਍ഀ
	[cr_date] [smalldatetime] NOT NULL ,਍ഀ
	[hit] [int] NOT NULL ,਍ഀ
	[hstatus] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[shoppingcart] (਍ഀ
	[shoppingId] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[memberId] [int] NOT NULL ,਍ഀ
	[booksName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[booksId] [int] NOT NULL ,਍ഀ
	[unitPrice] [decimal](18, 2) NOT NULL ,਍ഀ
	[totalPrice] [decimal](18, 2) NOT NULL ,਍ഀ
	[quantity] [int] NOT NULL ,਍ഀ
	[shoppingdate] [smalldatetime] NOT NULL ,਍ഀ
	[status] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[shoppingclasses] (਍ഀ
	[c_id] [int] NOT NULL ,਍ഀ
	[ename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[cname] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[tonormalpages] (਍ഀ
	[u_id] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[urls] (਍ഀ
	[urlStr] [varchar] (400) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[siteId] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[var_id] [int] NULL ,਍ഀ
	[class_id] [int] NULL ,਍ഀ
	[status] [int] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[urlsdd] (਍ഀ
	[urlStr] [varchar] (400) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[siteId] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,਍ഀ
	[var_id] [int] NULL ,਍ഀ
	[class_id] [int] NULL ,਍ഀ
	[status] [int] NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[useripaddress] (਍ഀ
	[id] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[ip] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL ,਍ഀ
	[browsedate] [smalldatetime] NOT NULL ,਍ഀ
	[sort] [int] NOT NULL ,਍ഀ
	[contid] [int] NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
CREATE TABLE [dbo].[word2] (਍ഀ
	[mid] [int] IDENTITY (1, 1) NOT NULL ,਍ഀ
	[bword] [nvarchar] (400) COLLATE Chinese_PRC_CI_AS NOT NULL ਍ഀ
) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsd] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsd] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[abacus] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_abacus] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[abacusId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bhotnames] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bhotnames] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[hid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[blanguages] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_blanguages] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[langid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapAuthorBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapAuthorBids] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapPubBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapPubwordBids] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapSortBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapSortBids] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[mid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapSortwordBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapSortwordBids] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsa] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsa] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsb] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsb] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsc] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsc] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidse] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidse] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsf] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsf] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsg] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsg] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsh] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsh] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsi] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsi] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsj] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsj] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsk] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsk] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsl] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsl] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsm] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsm] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsn] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsn] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidso] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidso] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsother] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsother] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsp] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsp] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsq] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsq] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsr] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsr] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidss] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidss] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidst] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidst] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsu] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsu] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsv] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsv] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsw] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsw] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsx] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsx] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsy] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsy] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsz] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmapnamebidsz] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bword]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmaptagitem] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmaptagitem] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bid],਍ഀ
		[tid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmembers] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bmembers] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[memberId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookcategories] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookcategories] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[catId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookresearchs] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookresearchs] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[brid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookreviews] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookreviews] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[brid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[books] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_books] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[booksorts] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_booksorts] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[sid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookstores] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookstores1] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bsid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[booktags] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_booktags] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[tid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemebooks] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookthemevotes] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[tbid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookthemes] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[thid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemevoteWishs] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookthemevoteWishs] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[bwid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemevotes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bookthemevotesDetail] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[tbid],਍ഀ
		[uid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[border] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_border] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[orderId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bourlcatalog] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bourlcatalog] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[cid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[favorite] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_favorite] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[favId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[ismap] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_ismaps] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[isbn],਍ഀ
		[uid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[psmap] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_psmaps] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[pid],਍ഀ
		[uid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[publishers] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_publishers] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[pid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[shoppingcart] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_bshoppingcart] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[shoppingId]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[shoppingclasses] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [PK_shoppingclasses] PRIMARY KEY  CLUSTERED ਍ഀ
	(਍ഀ
		[c_id]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsd] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsd_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsd_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[abacus] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_abacus_memberId] DEFAULT (0) FOR [memberId],਍ഀ
	CONSTRAINT [DF_abacus_dangdangPrice] DEFAULT (0) FOR [dangdangPrice],਍ഀ
	CONSTRAINT [DF_abacus_joyoPrice] DEFAULT (0) FOR [joyoPrice]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bauthors] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bauthors_hit] DEFAULT (0) FOR [hit],਍ഀ
	CONSTRAINT [DF_bauthors_hstatus] DEFAULT (0) FOR [hstatus],਍ഀ
	CONSTRAINT [DF_bauthors_bookcount] DEFAULT (0) FOR [bookcount],਍ഀ
	CONSTRAINT [DF_bauthors_bidcount] DEFAULT (0) FOR [bidcount]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bhotnames] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bhotnames_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_bhotnames_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[blanguages] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_blanguages_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapAuthorBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapAuthorBids_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapAuthorBids_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapPubBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapPubwordBids_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapPubwordBids_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapSortBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapSortBids_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapSortBids_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapSortwordBids] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapSortwordBids_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapSortwordBids_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsa] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsa_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsa_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsb] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsb_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsb_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsc] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsc_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsc_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidse] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidse_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidse_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsf] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsf_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsf_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsg] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsg_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsg_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsh] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsh_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsh_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsi] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsi_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsi_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsj] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsj_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsj_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsk] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsk_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsk_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsl] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsl_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsl_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsm] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsm_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsm_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsn] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsn_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsn_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidso] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidso_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidso_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsother] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsother_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsother_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsp] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsp_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsp_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsq] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsq_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsq_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsr] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsr_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsr_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidss] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidss_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidss_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidst] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidst_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidst_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsu] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsu_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsu_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsv] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsv_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsv_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsw] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsw_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsw_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsx] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsx_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsx_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsy] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsy_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsy_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmapnamebidsz] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmapnamebidsz_status] DEFAULT ((-1)) FOR [status],਍ഀ
	CONSTRAINT [DF_bmapnamebidsz_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmaptagitem] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmaptagitem_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_bmaptagitem_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bmembers] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bmembers_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookcategories] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookcategories_layer] DEFAULT (0) FOR [layer],਍ഀ
	CONSTRAINT [DF_bookcategories_parentCat] DEFAULT (0) FOR [parentCat],਍ഀ
	CONSTRAINT [DF_bookcategories_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_bookcategories_hit] DEFAULT (0) FOR [hit]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookresearchs] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookresearchs_selected] DEFAULT (0) FOR [selected],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes1] DEFAULT (0) FOR [votes1],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes2] DEFAULT (0) FOR [votes2],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes3] DEFAULT (0) FOR [votes3],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes4] DEFAULT (0) FOR [votes4],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes5] DEFAULT (0) FOR [votes5],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes6] DEFAULT (0) FOR [votes6],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes7] DEFAULT (0) FOR [votes7],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes8] DEFAULT (0) FOR [votes8],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes9] DEFAULT (0) FOR [votes9],਍ഀ
	CONSTRAINT [DF_bookresearchs_votes10] DEFAULT (0) FOR [votes10],਍ഀ
	CONSTRAINT [DF_bookresearchs_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookresearchs_status] DEFAULT ((-1)) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookresearchvotes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookresearchvotes_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookresearchvotes_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookreviewVotes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookreviewVotes_wish] DEFAULT (0) FOR [wish],਍ഀ
	CONSTRAINT [DF_bookreviewVotes_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookreviewVotes_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookreviews] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookreviews_votes] DEFAULT (0) FOR [votes],਍ഀ
	CONSTRAINT [DF_bookreviews_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookreviews_status] DEFAULT ((-1)) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[books] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_books_abid] DEFAULT ((-1)) FOR [abid],਍ഀ
	CONSTRAINT [DF_books_sid] DEFAULT ((-1)) FOR [sid],਍ഀ
	CONSTRAINT [DF_books_s2id] DEFAULT ((-1)) FOR [s2id],਍ഀ
	CONSTRAINT [DF_books_stores] DEFAULT (0) FOR [stores],਍ഀ
	CONSTRAINT [DF_books_price] DEFAULT (0) FOR [price],਍ഀ
	CONSTRAINT [DF_books_pid] DEFAULT ((-1)) FOR [pid],਍ഀ
	CONSTRAINT [DF_books_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_books_up_date] DEFAULT (getdate()) FOR [up_date],਍ഀ
	CONSTRAINT [DF_books_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_books_hit] DEFAULT (0) FOR [hit],਍ഀ
	CONSTRAINT [DF_books_sstatus] DEFAULT (0) FOR [sstatus],਍ഀ
	CONSTRAINT [DF_books_stime] DEFAULT (0) FOR [stime],਍ഀ
	CONSTRAINT [DF__books__aid__53F76C67] DEFAULT (0) FOR [aid],਍ഀ
	CONSTRAINT [ISBN_books] UNIQUE  NONCLUSTERED ਍ഀ
	(਍ഀ
		[isbn]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[booksorts] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_booksorts_parentSid_1] DEFAULT ((-1)) FOR [parentSid],਍ഀ
	CONSTRAINT [DF_booksorts_bookcount] DEFAULT (0) FOR [bookcount],਍ഀ
	CONSTRAINT [DF_booksorts_lev] DEFAULT ((-1)) FOR [lev],਍ഀ
	CONSTRAINT [DF_booksorts_popuplar] DEFAULT ((-1)) FOR [popular],਍ഀ
	CONSTRAINT [DF_booksorts_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF__booksorts__hit__33408412] DEFAULT (0) FOR [hit]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookstores] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookstores_u_id] DEFAULT ((-1)) FOR [bid],਍ഀ
	CONSTRAINT [DF_bookstores_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_bookstores_create_date] DEFAULT (getdate()) FOR [create_date],਍ഀ
	CONSTRAINT [DF_bookstores_up_date] DEFAULT (getdate()) FOR [up_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[booktags] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_booktags_hits] DEFAULT (0) FOR [hits],਍ഀ
	CONSTRAINT [DF_booktags_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_booktags_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [uni_booktags] UNIQUE  NONCLUSTERED ਍ഀ
	(਍ഀ
		[tname],਍ഀ
		[cr_uid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemebooks] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookthemevotes_cr_uid] DEFAULT ((-1)) FOR [cr_uid],਍ഀ
	CONSTRAINT [DF_bookthemevotes_votes] DEFAULT (0) FOR [votes],਍ഀ
	CONSTRAINT [DF_bookthemevotes_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookthemevotes_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [uni_TABLE1] UNIQUE  NONCLUSTERED ਍ഀ
	(਍ഀ
		[thid],਍ഀ
		[bid]਍ഀ
	)  ON [PRIMARY] ਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookthemes_hits] DEFAULT (0) FOR [hits],਍ഀ
	CONSTRAINT [DF_bookthemes_cr_date] DEFAULT (getdate()) FOR [cr_date]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bookthemevotes] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bookthemevotesDetail_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_bookthemevotesDetail_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[border] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_border_state] DEFAULT (0) FOR [state]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[bourlcatalog] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bourlcatalog_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[favorite] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_favorite_memberId] DEFAULT (0) FOR [memberId],਍ഀ
	CONSTRAINT [DF_favorite_booksId] DEFAULT (0) FOR [booksId],਍ഀ
	CONSTRAINT [DF_favorite_states] DEFAULT (0) FOR [states]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[publishers] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_publishers_bookcount] DEFAULT (0) FOR [bookcount],਍ഀ
	CONSTRAINT [DF_publishers_status] DEFAULT (0) FOR [status],਍ഀ
	CONSTRAINT [DF_publishers_cr_date] DEFAULT (getdate()) FOR [cr_date],਍ഀ
	CONSTRAINT [DF_publishers_hit] DEFAULT (0) FOR [hit],਍ഀ
	CONSTRAINT [DF_publishers_hstatus] DEFAULT (0) FOR [hstatus]਍ഀ
GO਍ഀ
਍ഀ
ALTER TABLE [dbo].[shoppingcart] WITH NOCHECK ADD ਍ഀ
	CONSTRAINT [DF_bshoppingcart_memberId] DEFAULT (0) FOR [memberId],਍ഀ
	CONSTRAINT [DF_shoppingcart_status] DEFAULT (0) FOR [status]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [key_bookreviews] ON [dbo].[bookreviews]([bid]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [sort_books] ON [dbo].[books]([sid]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [sort2_books] ON [dbo].[books]([s2id]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [IX_bid] ON [dbo].[bookstores]([bid]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [key_isbn] ON [dbo].[bookstores]([bisbn]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [key_pname] ON [dbo].[bookstores]([pname]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [key_pid] ON [dbo].[bookstores]([pid]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
 CREATE  INDEX [key_ps] ON [dbo].[bookstores]([ps]) ON [PRIMARY]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsd]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[abacus]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bauthors]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bhotnames]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[blanguages]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapAuthorBids]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapPubBids]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapSortBids]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapSortwordBids]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsa]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsb]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsc]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidse]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsf]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsg]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsh]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsi]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsj]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsk]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsl]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsm]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsn]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidso]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsother]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsp]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsq]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsr]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidss]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidst]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsu]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsv]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsw]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsx]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsy]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bmapnamebidsz]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bmaptagitem]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bmembers]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookresearchs]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookresearchvotes]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookreviewVotes]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookreviews]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[books]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[booksites]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[booksorts]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bookstores]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[booktags]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookthemebooks]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookthemes]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookthemevoteWishs]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT ,  UPDATE ,  INSERT  ON [dbo].[bookthemevotes]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[border]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bourlcatalog]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[bourls]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
GRANT  SELECT  ON [dbo].[publishers]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE proc bikee_favorite਍ഀ
@memberId int,਍ഀ
@booksId int,਍ഀ
@states int਍ഀ
as ਍ഀ
  if (select count(*) from favorite where memberId=@memberId and booksId=@booksId)>0਍ഀ
    begin ਍ഀ
      print 'a'਍ഀ
    end਍ഀ
   else ਍ഀ
    begin ਍ഀ
     insert into favorite(memberId,booksId,states) values(@memberId,@booksId,@states)਍ഀ
    end਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[bikee_favorite]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE procedure bikee_shoppingcart ਍ഀ
@productId int,਍ഀ
@memberId int,਍ഀ
@productName nvarchar(200),਍ഀ
@unitPrice float(8),਍ഀ
@totalPrice float(8),਍ഀ
@quantity int,਍ഀ
@shoppingdate nvarchar(200)਍ഀ
਍ഀ
਍ഀ
as਍ഀ
਍ഀ
    if  (select count(*) from shoppingcart where booksId = @productId and  memberId =@memberId)>0਍ഀ
       begin਍ഀ
         ਍ഀ
         update shoppingcart   ਍ഀ
         set totalPrice=@totalPrice,quantity=@quantity,shoppingdate=@shoppingdate਍ഀ
            where booksId = @productId and  memberId =@memberId਍ഀ
       end਍ഀ
    else ਍ഀ
      ਍ഀ
       begin ਍ഀ
          insert into shoppingcart(memberId,booksName,booksId,unitPrice,totalPrice,quantity,shoppingdate)਍ഀ
          values(@memberId,@productName,@productId,@unitPrice,@totalPrice,@quantity,@shoppingdate)਍ഀ
       end਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[bikee_shoppingcart]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE   proc get_author_books_count਍ഀ
@aid int ਍ഀ
as ਍ഀ
  declare @bookscount int਍ഀ
  begin ਍ഀ
    set @bookscount=(select count(bid) as total from books where aid=@aid)਍ഀ
    update bauthors set bidcount = @bookscount where aid=@aid਍ഀ
  end਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[get_author_books_count]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
create   proc get_books_to_author਍ഀ
  @aid int਍ഀ
as ਍ഀ
  begin ਍ഀ
    select * from books where aid=@aid਍ഀ
  end਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[get_books_to_author]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
create  proc get_books_to_publisher਍ഀ
  @pid int਍ഀ
as ਍ഀ
  begin ਍ഀ
    select * from books where pid=@pid਍ഀ
  end਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[get_books_to_publisher]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE proc get_books_to_sort਍ഀ
  @sortid int਍ഀ
as ਍ഀ
  begin ਍ഀ
    select * from books where cid=@sortid਍ഀ
  end਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[get_books_to_sort]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE proc get_sub_categories਍ഀ
@parentCat int਍ഀ
as਍ഀ
  ਍ഀ
  if not exists (select catId from bookcategories where parentCat=@parentCat)਍ഀ
     begin      ਍ഀ
       select * from books where cid=@parentCat਍ഀ
     end਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[get_sub_categories]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
CREATE PROCEDURE insertURLs਍ഀ
@str varchar(400),@siteId varchar(50)਍ഀ
 AS਍ഀ
਍ഀ
if not exists(select * from urls where urlstr=@str and siteid=@siteid)਍ഀ
  insert urls (urlstr,siteid) values(@str,@siteid)਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[insertURLs]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
create proc insert_user_ip_address਍ഀ
਍ഀ
@ip varchar(400),਍ഀ
@browsedate varchar(400),਍ഀ
@sort int,਍ഀ
@contid int਍ഀ
਍ഀ
਍ഀ
as ਍ഀ
  declare @hit int਍ഀ
  if exists (select * from useripaddress where contid=@contid and sort=@sort)਍ഀ
    begin ਍ഀ
       ਍ഀ
        if exists (select * from useripaddress where  ip=@ip and browsedate=@browsedate਍ഀ
           and contid=@contid and sort=@sort)਍ഀ
          begin਍ഀ
            print 'aa'਍ഀ
          end਍ഀ
        else ਍ഀ
          begin ਍ഀ
             if (@sort=1) --修改bookcategories਍ഀ
               begin ਍ഀ
                 insert into useripaddress(ip,browsedate,sort,contid)਍ഀ
                 values(@ip,@browsedate,@sort,@contid)਍ഀ
                 set @hit = (select hit  from  bookcategories where catId=@contid)਍ഀ
                 update bookcategories set hit=@hit+1 where catId=@contid਍ഀ
               end਍ഀ
             else਍ഀ
               begin ਍ഀ
                 insert into useripaddress(ip,browsedate,sort,contid)਍ഀ
                 values(@ip,@browsedate,@sort,@contid)਍ഀ
                 set @hit = (select hit  from  books where bid=@contid)਍ഀ
                 update books set hit=@hit+1 where bid=@contid਍ഀ
               end਍ഀ
          end਍ഀ
    end਍ഀ
  if not exists(select * from useripaddress where contid=@contid and sort=@sort)਍ഀ
    begin ਍ഀ
      if (@sort=1) --修改bookcategories @sort=2修改books਍ഀ
               begin ਍ഀ
                 insert into useripaddress(ip,browsedate,sort,contid)਍ഀ
                 values(@ip,@browsedate,@sort,@contid)਍ഀ
                 set @hit = (select hit  from  bookcategories where catId=@contid)਍ഀ
                 update bookcategories set hit=@hit+1 where catId=@contid਍ഀ
               end਍ഀ
             else਍ഀ
               begin ਍ഀ
                 insert into useripaddress(ip,browsedate,sort,contid)਍ഀ
                 values(@ip,@browsedate,@sort,@contid)਍ഀ
                 set @hit = (select hit  from  books where bid=@contid)਍ഀ
                 update books set hit=@hit+1 where bid=@contid਍ഀ
               end਍ഀ
    end਍ഀ
  ਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[insert_user_ip_address]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_add_bookword ਍ഀ
@word nvarchar(20), @bid int, @wordStore varchar(50)਍ഀ
AS਍ഀ
exec('਍ഀ
declare @c int਍ഀ
select @c=count(*) from '+@wordStore+' where bword='''+@word+''' and bid='+@bid+'਍ഀ
if (@c=0)਍ഀ
insert '+@wordStore+' (bword,bid) values ('''+@word+''','+@bid+')਍ഀ
')਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_add_tag਍ഀ
@tagname varchar(100),@uid bigint,@bid bigint਍ഀ
AS਍ഀ
declare @c int਍ഀ
select @c=count(*) from booktags where tname=ltrim(rtrim(@tagname)) and cr_uid=@uid਍ഀ
if (@c=0)਍ഀ
begin਍ഀ
insert booktags (tname,cr_uid) values(ltrim(rtrim(@tagname)),@uid)਍ഀ
end਍ഀ
਍ഀ
if (@bid>0)਍ഀ
begin਍ഀ
declare @tid bigint਍ഀ
select @tid=tid from booktags where tname=ltrim(rtrim(@tagname)) and cr_uid=@uid਍ഀ
if (@tid>0)਍ഀ
begin਍ഀ
if not exists(select tid from bmaptagitem where tid=@tid and bid=@bid)਍ഀ
insert bmaptagitem (tid,bid) values(@tid,@bid)਍ഀ
end਍ഀ
end਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_add_tag]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_add_theme਍ഀ
@thname varchar(100),@summary varchar(1000),@mid int,@bid int,@votes int਍ഀ
AS਍ഀ
declare @c int਍ഀ
select @c=count(*) from bookthemes where thname=ltrim(rtrim(@thname))਍ഀ
if (@c=0)਍ഀ
begin਍ഀ
insert bookthemes (thname,summary,cr_uid) values(@thname,@summary,@mid)਍ഀ
if (@bid>0)਍ഀ
begin਍ഀ
declare @thid int਍ഀ
select @thid=thid from bookthemes where thname=ltrim(rtrim(@thname))਍ഀ
exec sp_bk_add_themebook @thid,@bid,@mid,@votes਍ഀ
end਍ഀ
end਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_add_theme]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_add_themebook਍ഀ
@thid int,@bid int,@mid int,@votes int਍ഀ
AS਍ഀ
declare @c int਍ഀ
set @c=0਍ഀ
select @c=count(*) from bookthemebooks where thid=@thid and bid=@bid਍ഀ
if (@c<=0)਍ഀ
insert bookthemebooks (thid,bid,cr_uid) values(@thid,@bid,@mid)਍ഀ
declare @tbid int਍ഀ
set @tbid=-1਍ഀ
select @tbid=tbid from bookthemebooks where thid=@thid and bid=@bid਍ഀ
if (@tbid>0)਍ഀ
exec sp_bk_add_themevote @tbid,@mid,1,@votes਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_add_themebook]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_add_themevote਍ഀ
@tbid int,@mid int,@wish int,@votes int਍ഀ
AS਍ഀ
declare @c int਍ഀ
set @c=0਍ഀ
select @c=count(*) from bookthemevotes where tbid=@tbid and uid=@mid਍ഀ
if (@c<=0)਍ഀ
insert bookthemevotes (tbid,uid,wish) values(@tbid,@mid,@wish)਍ഀ
if (@wish=1)਍ഀ
update bookthemebooks set votes=votes+@votes where tbid=@tbid਍ഀ
else਍ഀ
update bookthemebooks set votes=votes-@votes where tbid=@tbid਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_add_themevote]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_bookitem_add_review ਍ഀ
@bid int,@title nvarchar(500),@context text,@mid int਍ഀ
AS਍ഀ
਍ഀ
insert bookreviews (bid,uid,title,context,votes) values (@bid,@mid,@title,@context,0)਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_bookitem_add_review]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS OFF ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_bookitem_add_reviewvote ਍ഀ
@brid int,@mid int,@wish bit਍ഀ
AS਍ഀ
declare @c int਍ഀ
select @c=count(*) from bookreviewvotes where brid=@brid and uid=@mid਍ഀ
if (@c=0)਍ഀ
begin਍ഀ
insert bookreviewvotes (brid,uid,wish) values(@brid,@mid,@wish)਍ഀ
if (@wish=1)਍ഀ
update bookreviews set votes=votes+1 where brid=@brid਍ഀ
else਍ഀ
update bookreviews set votes=votes-1 where brid=@brid਍ഀ
end਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_bookitem_add_reviewvote]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_get_bookitem਍ഀ
@uid int਍ഀ
AS਍ഀ
select a.uid,a.ps,a.vipPs,a.commonPs,a.urlstr,a.siteId,a.imgUrl,a.up_date,a.psimg,a.vippsimg,a.cpsimg,bname,bauthor,bisbn,pname from bookstores a where a.uid=@uid਍ഀ
਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_get_bookitem]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_get_bookstores਍ഀ
@bid int਍ഀ
AS਍ഀ
select bsid,urlstr,ps,cps,vipps,siteid from bookstores where bid=@bid order by ps਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_get_bookstores]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE procedure sp_bk_get_catalogitem਍ഀ
@bid int਍ഀ
as ਍ഀ
਍ഀ
	select b.bid,b.bname,b.authorStr,b.price,b.imgname,b.simgName,b.summary,b.outline,b.isbn,p.pname,p.pid,਍ഀ
	tbid=(select top 1 tbid from bookthemebooks where bid=b.bid order by cr_date desc)਍ഀ
	 from ਍ഀ
	books as b,publishers as p where b.bid=@bid  and p.pid=b.pid਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_get_catalogitem]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_get_tag਍ഀ
@tid int਍ഀ
AS਍ഀ
select tid,tname,cr_uid,hits,bookcount=(select count(*) from bmaptagitem where tid=@tid),cr_date from booktags where tid=@tid਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_get_tag]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
CREATE PROCEDURE sp_bk_get_themeitem਍ഀ
@thid int਍ഀ
AS਍ഀ
select thid,thname,summary,cr_uid,bookcount=(select count(distinct bid) from bookthemebooks where thid=a.thid),cr_date from bookthemes a where a.thid=@thid਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_get_themeitem]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
create PROCEDURE sp_bk_research_addvote਍ഀ
@brid int,@ipaddress varchar(50),@v1 int,@v2 int,@v3 int,@v4 int,@v5 int,@v6 int,@v7 int,@v8 int,@v9 int,@v10 int਍ഀ
AS਍ഀ
਍ഀ
insert bookresearchvotes਍ഀ
select @brid ,@ipaddress ,@v1 ,@v2 ,@v3 ,@v4 t,@v5 ,@v6 ,@v7 ,@v8 ,@v9 ,@v10,getdate(),0਍ഀ
਍ഀ
update bookresearchs਍ഀ
set votes1=votes1+@v1,votes2=votes2+@v2,votes3=votes3+@v3,votes4=votes4+@v4,votes5=votes5+@v5,votes6=votes6+@v6,votes7=votes7+@v7,਍ഀ
votes8=votes8+@v8,votes9=votes9+@v9,votes10=votes10+@v10 where brid=@brid਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_research_addvote]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
create PROCEDURE sp_bk_research_getvote_brid ਍ഀ
@brid int਍ഀ
AS਍ഀ
਍ഀ
਍ഀ
select top 1 brid,rname,selected,op1,votes1,op2,votes2,op3,votes3,op4,votes4,op5,votes5,op6,votes6,op7,votes7,op8,votes8਍ഀ
,op9,votes9,op10,votes10,cr_date from bookresearchs where brid=@brid਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_research_getvote_brid]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
create PROCEDURE sp_bk_research_getvote_status ਍ഀ
@status int਍ഀ
AS਍ഀ
਍ഀ
਍ഀ
select top 1 brid,rname,selected,op1,votes1,op2,votes2,op3,votes3,op4,votes4,op5,votes5,op6,votes6,op7,votes7,op8,votes8਍ഀ
,op9,votes9,op10,votes10,cr_date from bookresearchs where status=@status਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_research_getvote_status]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
create proc sp_bk_validate_books਍ഀ
as਍ഀ
declare @wrongps int਍ഀ
select @wrongps=count(*) from books where price<=0਍ഀ
if (@wrongps>0)਍ഀ
print 'there are '+cast(@wrongps as varchar(10))+' books''ps<=0'਍ഀ
਍ഀ
declare @wbook int਍ഀ
select @wbook=count(*) from bookstores where bid not in (select bid from books)਍ഀ
if (@wbook>0)਍ഀ
print 'there are '+cast(@wbook as varchar(10))+' bookstore items not in books'਍ഀ
਍ഀ
declare @wbid int਍ഀ
select @wbook=count(*) from books where bid not in (select bid from bookstores)਍ഀ
if (@wbid>0)਍ഀ
print 'there are '+cast(@wbook as varchar(10))+' book items not in bookstores'਍ഀ
਍ഀ
declare @wisbn int਍ഀ
select @wisbn=count(*) from books where isbn='-1'਍ഀ
if (@wisbn>0)਍ഀ
print 'there are '+cast(@wisbn as varchar(10))+' books'' isbn=-1'਍ഀ
਍ഀ
declare @wps int਍ഀ
select @wps=count(*) from bookstores where ps<=0਍ഀ
if (@wps>0)਍ഀ
print 'there are '+cast(@wps as varchar(10))+' bookstore'' item'' ps<=0'਍ഀ
਍ഀ
਍ഀ
਍ഀ
GO਍ഀ
SET QUOTED_IDENTIFIER OFF ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
GRANT  EXECUTE  ON [dbo].[sp_bk_validate_books]  TO [bikee]਍ഀ
GO਍ഀ
਍ഀ
SET QUOTED_IDENTIFIER ON ਍ഀ
GO਍ഀ
SET ANSI_NULLS ON ਍ഀ
GO਍ഀ
਍ഀ
਍ഀ
CREATE function f_GetPy(@Str nvarchar(400))਍ഀ
returns nvarchar(4000)਍ഀ
as਍ഀ
begin਍ഀ
 declare @strlen int,@re nvarchar(4000)਍ഀ
 declare @t table(chr nchar(1) collate Chinese_PRC_CI_AS,letter nchar(1))਍ഀ
 insert @t select '吖','A' union all select '八','B'਍ഀ
 union all select '嚓','C' union all select '咑','D'਍ഀ
 union all select '妸','E' union all select '发','F'਍ഀ
 union all select '旮','G' union all select '铪','H'਍ഀ
 union all select '丌','J' union all select '咔','K' ਍ഀ
 union all select '垃','L' union all select '嘸','M'਍ഀ
 union all select '拏','N' union all select '噢','O'਍ഀ
 union all select '妑','P' union all select '七','Q'਍ഀ
 union all select '呥','R' union all select '仨','S'਍ഀ
 union all select '他','T' union all select '屲','W'਍ഀ
 union all select '夕','X' union all select '丫','Y'਍ഀ
 union all select '帀','Z'਍ഀ
਍ഀ
 union all select '·',''਍ഀ
 union all select ' ','' ਍ഀ
 --union all select '(',''਍ഀ
 --union all select ')',''਍ഀ
਍ഀ
਍ 猀攀氀攀挀琀 䀀猀琀爀氀攀渀㴀氀攀渀⠀䀀猀琀爀⤀Ⰰ䀀爀攀㴀✀✀ഀ
਍ 眀栀椀氀攀 䀀猀琀爀氀攀渀㸀　ഀ
਍ 戀攀最椀渀ഀ
਍  猀攀氀攀挀琀 琀漀瀀 ㄀ 䀀爀攀㴀氀攀琀琀攀爀⬀䀀爀攀Ⰰ䀀猀琀爀氀攀渀㴀䀀猀琀爀氀攀渀ⴀ㄀ഀ
਍  昀爀漀洀 䀀琀 愀 眀栀攀爀攀 挀栀爀㰀㴀猀甀戀猀琀爀椀渀最⠀䀀猀琀爀Ⰰ䀀猀琀爀氀攀渀Ⰰ㄀⤀ഀ
਍  漀爀搀攀爀 戀礀 挀栀爀 搀攀猀挀ഀ
਍  椀昀 䀀䀀爀漀眀挀漀甀渀琀㴀　ഀ
਍   猀攀氀攀挀琀 䀀爀攀㴀猀甀戀猀琀爀椀渀最⠀䀀猀琀爀Ⰰ䀀猀琀爀氀攀渀Ⰰ㄀⤀⬀䀀爀攀Ⰰ䀀猀琀爀氀攀渀㴀䀀猀琀爀氀攀渀ⴀ㄀ഀ
਍ 攀渀搀ഀ
਍ 爀攀琀甀爀渀⠀䀀爀攀⤀ഀ
਍攀渀搀ഀ
਍ഀ
਍ഀ
਍ഀ
਍䜀伀ഀ
਍匀䔀吀 儀唀伀吀䔀䐀开䤀䐀䔀一吀䤀䘀䤀䔀刀 伀䘀䘀 ഀ
਍䜀伀ഀ
਍匀䔀吀 䄀一匀䤀开一唀䰀䰀匀 伀一 ഀ
਍䜀伀ഀ
਍ഀ
਍�