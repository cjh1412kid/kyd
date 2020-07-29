USE [YHSROrderPlatform]
GO

/****** Object:  Table [dbo].[YHSR_Ueditor_Record]    Script Date: 07/23/2018 08:19:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[YHSR_Ueditor_Record](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[Used] [int] NOT NULL,
	[Content] [text] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[YHSR_Ueditor_Record] ADD  DEFAULT (getdate()) FOR [InputTime]
GO



INSERT  INTO [YHSROrderPlatform].[dbo].[YHSR_Ueditor_Record]
([Company_Seq],	[Name] ,	[InputTime] ,	[Del],	[Used],	[Content] )
VALUES (3 , '模板1' , getdate() , 0 , 1 , ( SELECT UeditorContent FROM [YHSmartRetail].[dbo].[YHSR_Base_Company] WHERE [Seq] =3)	)
