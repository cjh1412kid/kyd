/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    add size remark ******/
USE [YHSROrderPlatform]
GO

ALTER TABLE [dbo].[YHSR_OP_Order] add [IsSplit] int NOT NULL DEFAULT ((0)) 
GO

ALTER TABLE [dbo].[YHSR_OP_Order] add [ParentSeq] int NULL
GO