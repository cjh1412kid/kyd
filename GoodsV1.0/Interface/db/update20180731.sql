USE [YHSROrderPlatform]
GO

exec sp_rename '[dbo].[YHSR_OP_ShoesData].Size','Size_Seq','COLUMN'
GO

ALTER TABLE [dbo].[YHSR_OP_ShoesData] alter column Size_Seq INT NOT NULL
GO


USE [YHSROnlineSales]
GO

exec sp_rename '[dbo].[YHSR_OLS_ShoesData].Size','Size_Seq','COLUMN'
GO

ALTER TABLE [dbo].[YHSR_OLS_ShoesData] alter column Size_Seq INT NOT NULL
GO

INSERT INTO YHSmartRetail.dbo.YHSR_System_Menu (Parent_Seq,Name,Url) values (16,N'尺码管理', N'modules/system/good_size.html')
GO


/*
用于同步Size数据
*/
insert into YHSmartRetail.dbo.YHSR_Goods_Size(SizeName,Company_Seq) select yhsr.SizeName,yhsr.Company_Seq from ((select yos.Size_Seq as SizeName,ybb.Company_Seq as Company_Seq from YHSROrderPlatform.dbo.YHSR_OP_ShoesData yos
left join YHSmartRetail.dbo.YHSR_Goods_Shoes ygs on yos.Shoes_Seq=ygs.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Period ygp on ygs.Period_Seq=ygp.Seq
left join YHSmartRetail.dbo.YHSR_Base_Brand ybb on ygp.Brand_Seq=ybb.Seq)
UNION ALL
(select yos.Size_Seq as SizeName,ybb.Company_Seq as Company_Seq from YHSROnlineSales.dbo.YHSR_OLS_ShoesData yos
left join YHSmartRetail.dbo.YHSR_Goods_Shoes ygs on yos.Shoes_Seq=ygs.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Period ygp on ygs.Period_Seq=ygp.Seq
left join YHSmartRetail.dbo.YHSR_Base_Brand ybb on ygp.Brand_Seq=ybb.Seq)) yhsr group by yhsr.SizeName,yhsr.Company_Seq order by yhsr.Company_Seq,yhsr.SizeName



with sizeSelect as (select yos.Seq,yyy.Seq as Size_Seq from YHSROrderPlatform.dbo.YHSR_OP_ShoesData yos
left join YHSmartRetail.dbo.YHSR_Goods_Shoes ygs on yos.Shoes_Seq=ygs.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Period ygp on ygs.Period_Seq=ygp.Seq
left join YHSmartRetail.dbo.YHSR_Base_Brand ybb on ygp.Brand_Seq=ybb.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Size yyy on (yyy.Company_Seq=ybb.Company_Seq and yyy.SizeName=yos.Size_Seq))

update YHSROrderPlatform.dbo.YHSR_OP_ShoesData set Size_Seq=ss.Size_Seq from sizeSelect ss,YHSROrderPlatform.dbo.YHSR_OP_ShoesData bb where bb.Seq=ss.Seq;

with sizeSelect as (select yos.Seq,yyy.Seq as Size_Seq from YHSROnlineSales.dbo.YHSR_OLS_ShoesData yos
left join YHSmartRetail.dbo.YHSR_Goods_Shoes ygs on yos.Shoes_Seq=ygs.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Period ygp on ygs.Period_Seq=ygp.Seq
left join YHSmartRetail.dbo.YHSR_Base_Brand ybb on ygp.Brand_Seq=ybb.Seq
left join YHSmartRetail.dbo.YHSR_Goods_Size yyy on (yyy.Company_Seq=ybb.Company_Seq and yyy.SizeName=yos.Size_Seq))

update YHSROnlineSales.dbo.YHSR_OLS_ShoesData set Size_Seq=ss.Size_Seq from sizeSelect ss,YHSROnlineSales.dbo.YHSR_OLS_ShoesData bb where bb.Seq=ss.Seq;