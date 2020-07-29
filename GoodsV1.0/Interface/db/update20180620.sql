/****** Object:  Table [dbo].[YHSR_System_Menu]    add size remark ******/
USE [YHSmartRetail]
GO
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '15', N'新增', null, 'subAccount:add', '2', null, '0', '2018-06-19 11:17:48.263', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '15', N'修改', null, 'subAccount:update', '2', null, '1', '2018-06-19 11:29:22.123', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '15', N'删除', null, 'subAccount:delete', '2', null, '2', '2018-06-19 11:29:40.700', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '24', N'新增', null, 'orderParty:add', '2', null, '0', '2018-06-19 14:30:13.497', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '24', N'修改', null, 'orderParty:update', '2', null, '1', '2018-06-19 14:30:44.200', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '24', N'禁用', null, 'orderParty:disable', '2', null, '2', '2018-06-19 14:31:56.733', '0');
insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '24', N'删除', null, 'orderParty:delete', '2', null, '3', '2018-06-19 14:34:14.997', '0');
GO