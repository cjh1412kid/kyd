/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    add size remark ******/
USE [YHSROrderPlatform]
GO

exec sp_rename '[dbo].[YHSR_OP_Order].ParentSeq','ParentSeqs','COLUMN'
GO

ALTER TABLE [dbo].[YHSR_OP_Order] alter column ParentSeqs varchar(255) NULL
GO

ALTER TABLE [dbo].[YHSR_OP_Order] add [ClassifySplitOrder] int NOT NULL DEFAULT ((0)) 
GO

ALTER TABLE [dbo].[YHSR_OP_Order] add [ImportErpState] int NULL
GO



USE [YHSmartRetail]
GO

ALTER TABLE [dbo].[YHSR_Goods_Shoes] add [ShoesBrand] varchar(6) NULL
GO

ALTER TABLE [dbo].[YHSR_Goods_Shoes] add [Mnemonic] varchar(16) NULL
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子对应的品牌分类', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE' ,@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN', @level2name=N'ShoesBrand'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'助记符', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE' ,@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN', @level2name=N'Mnemonic'
GO

/****** Object:  Table [dbo].[YHSR_Goods_Brand]    Script Date: 2018/8/28 18:39:26 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_Goods_Brand](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[BrandCode] [varchar](6) NULL,
	[BrandName] [varchar](12) NULL,
	[InputTime] [date] NOT NULL,
	[Del] [int] NOT NULL,
	[Company_Seq] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_Goods_Brand] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子品牌代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Brand', @level2type=N'COLUMN',@level2name=N'BrandCode'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'外键：公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Brand', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO



USE [YHSmartRetail]
GO

/****** Object:  Table [dbo].[YHSR_System_Log]    Script Date: 2018/8/31 10:38:53 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_System_Log](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Content] [varchar](1000) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[UpdateTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_System_Log] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_System_Log] ADD  DEFAULT (getdate()) FOR [UpdateTime]
GO

ALTER TABLE [dbo].[YHSR_System_Log] ADD  DEFAULT ((0)) FOR [Del]
GO
