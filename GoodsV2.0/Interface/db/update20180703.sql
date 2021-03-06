/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    add size remark ******/
USE [YHSROrderPlatform]
GO

ALTER TABLE [dbo].[YHSR_OP_OrderProducts] add ProductPrice [decimal](18,2) NOT NULL DEFAULT ((0))
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单中商品价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'ProductPrice'
GO