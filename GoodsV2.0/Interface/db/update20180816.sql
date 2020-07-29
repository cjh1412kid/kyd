USE [YHSmartRetail]
GO

ALTER TABLE [dbo].[YHSR_Goods_Period] add MeetingStartTime [datetime]
GO

ALTER TABLE [dbo].[YHSR_Goods_Period] add MeetingEndTime [datetime]
GO

ALTER TABLE [dbo].[YHSR_Goods_Category] add Category_Code [varchar](32)
GO




/****** Object:  Table [dbo].[YHSR_Base_ESmart]    Script Date: 2018/8/20 18:47:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[YHSR_Base_ESmart](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[FactoryUser_Seq] [int] NOT NULL,
	[ESmartKey] [varchar](32) NULL,
	[ESmartSecret] [varchar](64) NULL,
	[Company_Seq] [int] NULL,
	[User_Prefix] [varchar](30) NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
