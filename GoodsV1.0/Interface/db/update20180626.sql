USE [YHSROrderPlatform]
GO

ALTER TABLE [dbo].[YHSR_OP_Order] add
[ReceiverName] varchar(30),
[Telephone] varchar(30),
[FullAddress] varchar(255)
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ReceiverName'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人手机号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Telephone'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'详细地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'FullAddress'
GO

/****** Object:  Table [dbo].[YHSR_OP_ReceiverInfo]    Script Date: 2018/6/26 19:01:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OP_ReceiverInfo](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[ReceiverName] [varchar](30) NOT NULL,
	[Telephone] [varchar](30) NOT NULL,
	[Province] [varchar](30) NOT NULL,
	[ProvinceCode] [int] NOT NULL,
	[City] [varchar](30) NOT NULL,
	[CityCode] [int] NOT NULL,
	[District] [varchar](30) NOT NULL,
	[DistrictCode] [int] NOT NULL,
	[DetailAddress] [varchar](255) NOT NULL,
	[IsDefault] [int] NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OP_ReceiverInfo] ADD  DEFAULT ((0)) FOR [IsDefault]
GO

ALTER TABLE [dbo].[YHSR_OP_ReceiverInfo] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'ReceiverName'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'Telephone'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'Province'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'ProvinceCode'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'City'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'CityCode'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'District'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'DistrictCode'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'详细地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'DetailAddress'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否默认（0：否 1：是）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'IsDefault'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'InputTime'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ReceiverInfo', @level2type=N'COLUMN',@level2name=N'Del'
GO


USE [YHSROnlineSales]
GO

ALTER TABLE [dbo].[YHSR_OLS_Order] add
[WxpayPrepayId] varchar(255),
[WxpayPrepayTime] [datetime],
[WxTransactionId] varchar(255),
[ReceiverName] varchar(30),
[Telephone] varchar(30),
[FullAddress] varchar(255)
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信商户平台订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'WxpayPrepayId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信支付PrepayId生成时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'WxpayPrepayTime'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信支付订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'WxTransactionId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'ReceiverName'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人手机号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Telephone'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'详细地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'FullAddress'
GO

/****** Object:  Table [dbo].[YHSR_OLS_Swoing]    Script Date: 2018/6/26 19:54:57 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OLS_Swoing](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[Brand_Seq] [bigint] NOT NULL,
	[Image] [varchar](64) NOT NULL,
	[Type] [int] NULL,
	[Link_Seq] [varchar](255) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OLS_Swoing] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_OLS_Swoing] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Swoing', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'轮播图类别，1：单个鞋子，2：鞋子分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Swoing', @level2type=N'COLUMN',@level2name=N'Type'
GO


USE [YHSmartRetail]
GO

/** 更新字段属性 **/
alter table [dbo].[YHSR_Base_User] alter column [Shop_Seq] varchar(100)
GO

insert into [dbo].[YHSR_System_Menu] ( [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) values ( '31', N'轮播管理', 'modules/system/home_page_management/ols_sowing.html', null, '1', 'fa fa-file-image-o', '0', '2018-06-22 10:25:59.357', '0');
GO

update [dbo].[YHSR_System_Menu] set [Perms]='sys:platform:order' where [Seq]=21;
update [dbo].[YHSR_System_Menu] set [Perms]='sys:platform:sale' where [Seq]=31;
GO