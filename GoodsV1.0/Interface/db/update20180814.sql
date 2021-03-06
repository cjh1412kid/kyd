USE [YHSROrderPlatform]
GO

/****** Object:  Table [dbo].[YHSR_OP_MeetingPlan]    Script Date: 2018/8/21 17:56:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OP_MeetingPlan](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[Period_Seq] [int] NOT NULL,
	[User_Seq] [int] NOT NULL,
	[Category_Seq] [int] NULL,
	[SX2] [varchar](6) NULL,
	[SX3] [varchar](6) NULL,
	[PlanNum] [int] NULL,
	[PlanMoney] [decimal](18, 2) NULL,
	[PlanGoodsIDs] [int] NULL,
	[ActualNum] [int] NULL,
	[ActualMoney] [decimal](18, 2) NULL,
	[ActualGoodsIDs] [int] NULL,
	[ActualGoodsIDSArr] [varchar](1000) NULL,
	[InputTime] [datetime] NOT NULL,
	[UpdateTime] [datetime] NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingPlan] ADD  DEFAULT ((0)) FOR [ActualNum]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingPlan] ADD  DEFAULT ((0)) FOR [ActualMoney]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingPlan] ADD  DEFAULT ((0)) FOR [ActualGoodsIDs]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingPlan] ADD  CONSTRAINT [DF__YHSR_OP_M__Input__0C50D423]  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingPlan] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'Period_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'Category_Seq'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'SX2'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'风格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'SX3'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计划订货数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'PlanNum'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计划订货金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'PlanMoney'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计划订货款数(货号数)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'PlanGoodsIDs'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实际订货数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'ActualNum'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实际订货金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'ActualMoney'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实际订货款数(货号数)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'ActualGoodsIDs'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已订货的货号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'ActualGoodsIDSArr'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传计划时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'InputTime'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'UpdateTime'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingPlan', @level2type=N'COLUMN',@level2name=N'Del'
GO

USE [YHSROrderPlatform]
GO

/****** Object:  Table [dbo].[YHSR_OP_MeetingRemind]    Script Date: 2018/8/21 17:56:38 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_OP_MeetingRemind](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[ScanRemind] [int] NOT NULL,
	[OrderRemind] [int] NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingRemind] ADD  DEFAULT ((1)) FOR [ScanRemind]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingRemind] ADD  DEFAULT ((1)) FOR [OrderRemind]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingRemind] ADD  DEFAULT (getdate()) FOR [InputTime]
GO

ALTER TABLE [dbo].[YHSR_OP_MeetingRemind] ADD  DEFAULT ((0)) FOR [Del]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扫码提醒（0:不提醒,1:提醒）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingRemind', @level2type=N'COLUMN',@level2name=N'ScanRemind'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下单提醒（0:不提醒,1:提醒）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_MeetingRemind', @level2type=N'COLUMN',@level2name=N'OrderRemind'
GO

