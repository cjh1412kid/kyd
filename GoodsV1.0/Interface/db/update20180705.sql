/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    add size remark ******/
USE [YHSmartRetail]
GO

ALTER TABLE [dbo].[YHSR_Base_Shop] alter column Lat varchar(20) NULL
GO

ALTER TABLE [dbo].[YHSR_Base_Shop] alter column Lng varchar(20) NULL
GO

ALTER TABLE [dbo].[YHSR_Base_Company] add UeditorContent [text] NULL
GO