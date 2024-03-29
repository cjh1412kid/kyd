/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    add size remark ******/


USE [YHSmartRetail]
GO

ALTER TABLE [dbo].[YHSR_Goods_SX] add [Visible] int NOT NULL DEFAULT ((0)) 
GO



USE [YHSROrderPlatform]
GO

/****** Object:  Table [dbo].[YHSR_OP_SplitOrderModel]    Script Date: 2018/9/7 10:34:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OP_SplitOrderModel](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[ModelName] [varchar](255) NOT NULL,
	[IsDefault] [int] NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OP_SplitOrderModel] ADD  DEFAULT ((0)) FOR [IsDefault]
GO

ALTER TABLE [dbo].[YHSR_OP_SplitOrderModel] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_OP_SplitOrderModel] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModel', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否默认（0：否 1：是）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModel', @level2type=N'COLUMN',@level2name=N'IsDefault'
GO

USE [YHSROrderPlatform]
GO

/****** Object:  Table [dbo].[YHSR_OP_SplitOrderModelDetail]    Script Date: 2018/9/7 10:34:32 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OP_SplitOrderModelDetail](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Model_Seq] [int] NOT NULL,
	[ShoesBrand] [varchar](100) NULL,
	[Category_Seq] [varchar](100) NULL,
	[Period_Seq] [varchar](100) NULL,
	[SX1] [varchar](100) NULL,
	[SX2] [varchar](100) NULL,
	[SX3] [varchar](100) NULL,
	[SX4] [varchar](100) NULL,
	[SX5] [varchar](100) NULL,
	[SX6] [varchar](100) NULL,
	[SX7] [varchar](100) NULL,
	[SX8] [varchar](100) NULL,
	[SX9] [varchar](100) NULL,
	[SX10] [varchar](100) NULL,
	[SX11] [varchar](100) NULL,
	[SX12] [varchar](100) NULL,
	[SX13] [varchar](100) NULL,
	[SX14] [varchar](100) NULL,
	[SX15] [varchar](100) NULL,
	[SX16] [varchar](100) NULL,
	[SX17] [varchar](100) NULL,
	[SX18] [varchar](100) NULL,
	[SX19] [varchar](100) NULL,
	[SX20] [varchar](100) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OP_SplitOrderModelDetail] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_OP_SplitOrderModelDetail] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'模板序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'Model_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子对应的品牌分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'ShoesBrand'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子类别序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'Category_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'波次序号(外键:YHSR_Goods_Period表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'Period_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'InputTime'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_SplitOrderModelDetail', @level2type=N'COLUMN',@level2name=N'Del'
GO

