USE [master]
GO
/****** Object:  Database [YHSROnlineSales]    Script Date: 2018/6/7 10:49:48 ******/
CREATE DATABASE [YHSROnlineSales] ON  PRIMARY 
( NAME = N'YHSROnlineSales', FILENAME = N'D:\MSSqlServer\OrderSales\YHSROnlineSales.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'YHSROnlineSales_log', FILENAME = N'D:\MSSqlServer\OrderSales\YHSROnlineSales_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [YHSROnlineSales] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [YHSROnlineSales].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [YHSROnlineSales] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET ARITHABORT OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [YHSROnlineSales] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [YHSROnlineSales] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET  DISABLE_BROKER 
GO
ALTER DATABASE [YHSROnlineSales] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [YHSROnlineSales] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [YHSROnlineSales] SET RECOVERY FULL 
GO
ALTER DATABASE [YHSROnlineSales] SET  MULTI_USER 
GO
ALTER DATABASE [YHSROnlineSales] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [YHSROnlineSales] SET DB_CHAINING OFF 
GO
EXEC sys.sp_db_vardecimal_storage_format N'YHSROnlineSales', N'ON'
GO
USE [YHSROnlineSales]
GO
/****** Object:  Table [dbo].[YHSR_OLS_CustomUserInfo]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_CustomUserInfo](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[IsCommission] [int] NULL,
	[RealName] [varchar](50) NULL,
	[Telephone] [varchar](15) NULL,
	[BankCardNO] [varchar](255) NULL,
	[Flag] [int] NULL,
	[Isuse] [int] NULL,
	[Remark] [varchar](255) NULL,
	[Openid] [varchar](100) NULL,
	[Nickname] [varchar](100) NULL,
	[Sex] [int] NULL,
	[Country] [varchar](50) NULL,
	[Province] [varchar](100) NULL,
	[City] [varchar](100) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
	[Sessionkey] [varchar](64) NULL,
	[Unionid] [varchar](32) NULL,
	[UserID] [varchar](18) NULL,
	[Company_Seq] [int] NULL,
	[Brand_Seq] [int] NULL,
	[Shop_Seq] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_UserJurisdiction] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_DeliveryCycleRole]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_DeliveryCycleRole](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[DeliveryCycle] [int] NULL,
	[OrderCloseCycle] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_DeliveryCycleRole] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_ExpressCompany]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_ExpressCompany](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Code] [varchar](32) NULL,
	[Name] [varchar](100) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_ExpressCompany] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_Order]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_Order](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NULL,
	[OrderNum] [varchar](255) NULL,
	[OrderPrice] [float] NULL,
	[Paid] [float] NULL,
	[OrderStatus] [int] NULL,
	[Company_Seq] [int] NULL,
	[Shop_Seq] [int] NULL,
	[ExpressCompany_Seq] [int] NULL,
	[ExpressNo] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[PaymentTime] [datetime] NULL,
	[DeliverTime] [datetime] NULL,
	[ReceiveTime] [datetime] NULL,
	[Remark] [varchar](255) NULL,
	[Suggestion] [varchar](255) NULL,
	[Del] [int] NULL,
	[UserDelivery_Seq] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_Order] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_OrderProducts]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_OrderProducts](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Order_Seq] [int] NULL,
	[ShoesData_Seq] [int] NULL,
	[BuyCount] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
	[OpenIDLinks] [varchar](255) NULL,
 CONSTRAINT [PK_YHSR_OLS_OrderProducts] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_OrderStatus]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_OrderStatus](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[StatusName] [varchar](255) NULL,
	[DisContent] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_OrderStatus] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_SaleCommissionRule]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_SaleCommissionRule](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[levelOnePoint] [int] NULL,
	[levelTwoPoint] [int] NULL,
	[levelThreePoint] [int] NULL,
	[LevelCount] [int] NULL,
	[Drawcycle] [int] NULL,
	[Inputtime] [datetime] NULL,
	[Del] [int] NULL,
	[Company_Seq] [int] NOT NULL,
	[Brand_Seq] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OLS_SaleCommissionRule] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_ShoesData]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_ShoesData](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Shoes_Seq] [int] NULL,
	[Size] [varchar](255) NULL,
	[Num] [int] NULL,
	[StockDate] [datetime] NULL,
	[Stock] [int] NULL,
	[Del] [int] NULL,
	[Color_Seq] [int] NULL,
	[SetStock] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_ShoesData] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_ShoesInfo]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_ShoesInfo](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Shoes_Seq] [int] NULL,
	[TagPrice] [float] NULL,
	[SalePrice] [float] NULL,
	[Topic_Seq] [int] NULL,
	[Remark] [varchar](max) NULL,
	[Del] [int] NULL,
	[IsNewest] [int] NULL,
	[IsHotSale] [int] NULL,
	[OnSale] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OLS_ShoesInfo] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_ShoesValuate]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_ShoesValuate](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NULL,
	[Shoes_Seq] [int] NULL,
	[Score] [float] NULL,
	[IsCollected] [int] NULL,
	[Suggest] [varchar](255) NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_ShoesValuate] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_ShoppingCart]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_ShoppingCart](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NULL,
	[ShoesData_Seq] [int] NULL,
	[OpenIDLinks] [varchar](1000) NULL,
	[Company_Seq] [int] NULL,
	[Shop_Seq] [int] NULL,
	[BuyCount] [int] NULL,
	[TotalPrice] [float] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
	[IsChecked] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_ShoppingCart] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_Topic]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_Topic](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[TopicName] [varchar](20) NULL,
	[TopicImage] [varchar](255) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[Brand_Seq] [int] NULL,
	[TopicType] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_UserDeliveryInfo]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_UserDeliveryInfo](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NULL,
	[RecipientsName] [varchar](50) NULL,
	[Address] [varchar](255) NULL,
	[Telephone] [varchar](15) NULL,
	[Isdefault] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OLS_UserDeliveryInfo] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OLS_UserJurisdiction]    Script Date: 2018/6/7 10:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OLS_UserJurisdiction](
	[Seq] [int] IDENTITY(2,1) NOT NULL,
	[User_Seq] [int] NULL,
	[CreateUser_Seq] [int] NOT NULL,
	[EffectiveDate] [datetime] NULL,
	[IsDisable] [int] NOT NULL,
	[IsAdministrator] [int] NOT NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK__YHSR_OLS__CA1E3C8832E0915F] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET IDENTITY_INSERT [dbo].[YHSR_OLS_ExpressCompany] ON
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (1, N'YTO', N'圆通速递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (2, N'ZTO', N'中通快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (3, N'SF', N'顺丰速运', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (4, N'HTKY', N'百世快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (5, N'STO', N'申通快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (6, N'YD', N'韵达速递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (7, N'YZPY', N'邮政快递包裹', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (8, N'EMS', N'EMS', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (9, N'HHTT', N'天天快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (10, N'JD', N'京东物流', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (11, N'UC', N'优速快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (12, N'DBL', N'德邦', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (13, N'FAST', N'快捷快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (14, N'ZJS', N'宅急送', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (15, N'TNT', N'TNT快递', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (16, N'UPS', N'UPS', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (17, N'DHL', N'DHL', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (18, N'FEDEX', N'FEDEX联邦(国内件）', NULL, 0)
INSERT [dbo].[YHSR_OLS_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (19, N'FEDEX_GJ', N'FEDEX联邦(国际件）', NULL, 0)
SET IDENTITY_INSERT [dbo].[YHSR_OLS_ExpressCompany] OFF

ALTER TABLE [dbo].[YHSR_OLS_CustomUserInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserJurisdiction_IsCommission]  DEFAULT ((0)) FOR [IsCommission]
GO
ALTER TABLE [dbo].[YHSR_OLS_CustomUserInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserJurisdiction_Flag]  DEFAULT ((1)) FOR [Flag]
GO
ALTER TABLE [dbo].[YHSR_OLS_CustomUserInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserJurisdiction_Isuse]  DEFAULT ((0)) FOR [Isuse]
GO
ALTER TABLE [dbo].[YHSR_OLS_CustomUserInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserJurisdiction_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_CustomUserInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserJurisdiction_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_DeliveryCycleRole] ADD  CONSTRAINT [DF_YHSR_OLS_DeliveryCycleRole_DeliveryCycle]  DEFAULT ((0)) FOR [DeliveryCycle]
GO
ALTER TABLE [dbo].[YHSR_OLS_DeliveryCycleRole] ADD  CONSTRAINT [DF_YHSR_OLS_DeliveryCycleRole_OrderCloseCycle]  DEFAULT ((0)) FOR [OrderCloseCycle]
GO
ALTER TABLE [dbo].[YHSR_OLS_DeliveryCycleRole] ADD  CONSTRAINT [DF_YHSR_OLS_DeliveryCycleRole_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_DeliveryCycleRole] ADD  CONSTRAINT [DF_YHSR_OLS_DeliveryCycleRole_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ExpressCompany] ADD  CONSTRAINT [DF_YHSR_OLS_ExpressCompany_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_ExpressCompany] ADD  CONSTRAINT [DF_YHSR_OLS_ExpressCompany_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_Order] ADD  CONSTRAINT [DF_YHSR_OLS_Order_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_OrderProducts] ADD  CONSTRAINT [DF_YHSR_OLS_OrderProducts_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_OrderProducts] ADD  CONSTRAINT [DF_YHSR_OLS_OrderProducts_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_OrderStatus] ADD  CONSTRAINT [DF_YHSR_OLS_OrderStatus_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_OrderStatus] ADD  CONSTRAINT [DF_YHSR_OLS_OrderStatus_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_SaleCommissionRule] ADD  CONSTRAINT [DF_YHSR_OLS_SaleCommissionRule_Drawcycle]  DEFAULT ((0)) FOR [Drawcycle]
GO
ALTER TABLE [dbo].[YHSR_OLS_SaleCommissionRule] ADD  CONSTRAINT [DF_YHSR_OLS_SaleCommissionRule_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesData] ADD  CONSTRAINT [DF_YHSR_OLS_ShoesData_StockDate]  DEFAULT (getdate()) FOR [StockDate]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesData] ADD  CONSTRAINT [DF_YHSR_OLS_ShoesData_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesInfo] ADD  CONSTRAINT [DF_YHSR_OLS_ShoesInfo_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesInfo] ADD  DEFAULT ((0)) FOR [IsNewest]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesInfo] ADD  DEFAULT ((0)) FOR [IsHotSale]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesInfo] ADD  DEFAULT ((0)) FOR [OnSale]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OLS_ShoesValuate_IsCollected]  DEFAULT ((0)) FOR [IsCollected]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OLS_ShoesValuate_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoppingCart] ADD  CONSTRAINT [DF_YHSR_OLS_ShoppingCart_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoppingCart] ADD  CONSTRAINT [DF_YHSR_OLS_ShoppingCart_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_ShoppingCart] ADD  DEFAULT ((1)) FOR [IsChecked]
GO
ALTER TABLE [dbo].[YHSR_OLS_Topic] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_Topic] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_UserDeliveryInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserDeliveryInfo_Isdefault]  DEFAULT ((1)) FOR [Isdefault]
GO
ALTER TABLE [dbo].[YHSR_OLS_UserDeliveryInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserDeliveryInfo_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OLS_UserDeliveryInfo] ADD  CONSTRAINT [DF_YHSR_OLS_UserDeliveryInfo_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OLS_UserJurisdiction] ADD  CONSTRAINT [DF__YHSR_OLS_Us__Del__34C8D9D1]  DEFAULT ((0)) FOR [Del]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否有权限获取佣金1有权限，0无权限' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'IsCommission'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'真实名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'RealName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'BankCardNO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'BankCardNO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标识是企业员工还是注册会员0商户员工，1注册会员，冗余字段' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Flag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用 0启用，1未启用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Isuse'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户微信号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Openid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'昵称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Nickname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别1男性，2是女性，0未知' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Sex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'国家 中国为CN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Country'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'省份' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Province'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'市' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'City'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'会话密钥' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Sessionkey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户在开放平台的唯一标识符' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Unionid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'身份证' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'UserID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后进入的公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后进入的品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后进入的门店序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_CustomUserInfo', @level2type=N'COLUMN',@level2name=N'Shop_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自动收货周期天数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_DeliveryCycleRole', @level2type=N'COLUMN',@level2name=N'DeliveryCycle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单未付款自动关闭周期天数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_DeliveryCycleRole', @level2type=N'COLUMN',@level2name=N'OrderCloseCycle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_DeliveryCycleRole', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_DeliveryCycleRole', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ExpressCompany', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'OrderNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'OrderPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已付金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Paid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'OrderStatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商户序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'门店序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Shop_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'ExpressCompany_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'ExpressNo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'付款时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'PaymentTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发货时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'DeliverTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'ReceiveTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Suggestion'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Order', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'Order_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子数据序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'ShoesData_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'BuyCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商品分享链接' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderProducts', @level2type=N'COLUMN',@level2name=N'OpenIDLinks'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderStatus', @level2type=N'COLUMN',@level2name=N'StatusName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderStatus', @level2type=N'COLUMN',@level2name=N'DisContent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderStatus', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_OrderStatus', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'第一层佣金点数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'levelOnePoint'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'第二层佣金点数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'levelTwoPoint'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'第三层佣金点数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'levelThreePoint'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'层数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'LevelCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'佣金提现周期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'Drawcycle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'Inputtime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_SaleCommissionRule', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'尺码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Size'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'StockDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Stock'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'Color_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预设扣除库存' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesData', @level2type=N'COLUMN',@level2name=N'SetStock'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'吊牌价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'TagPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'零售价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'SalePrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联专题序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Topic_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是新品' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'IsNewest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否为热销' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'IsHotSale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0未上架，1已上架' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesInfo', @level2type=N'COLUMN',@level2name=N'OnSale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'我的评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Score'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否收藏0没收藏 1收藏' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'IsCollected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户建议' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Suggest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子数据序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'ShoesData_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商品分享链' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'OpenIDLinks'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商户序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'门店序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'Shop_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'BuyCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'TotalPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否勾选 0不勾选 1勾选' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_ShoppingCart', @level2type=N'COLUMN',@level2name=N'IsChecked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Topic', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0:热销爆款,1:新品特推,2:商家促销,3:明星同款,4:精选专题,5:品牌活动' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_Topic', @level2type=N'COLUMN',@level2name=N'TopicType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'RecipientsName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'Address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货人电话号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'Telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否默认地址0是，1不是' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'Isdefault'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserDeliveryInfo', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键自增' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OLS_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'Seq'
GO
USE [master]
GO
ALTER DATABASE [YHSROnlineSales] SET  READ_WRITE 
GO
