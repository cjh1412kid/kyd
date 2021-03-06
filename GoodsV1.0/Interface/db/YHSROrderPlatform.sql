USE [master]
GO
/****** Object:  Database [YHSROrderPlatform]    Script Date: 2018/6/7 10:50:17 ******/
CREATE DATABASE [YHSROrderPlatform] ON  PRIMARY 
( NAME = N'YHSROrderPlatform', FILENAME = N'D:\MSSqlServer\OrderSales\YHSROrderPlatform.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'YHSROrderPlatform_log', FILENAME = N'D:\MSSqlServer\OrderSales\YHSROrderPlatform_log.ldf' , SIZE = 4224KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [YHSROrderPlatform] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [YHSROrderPlatform].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [YHSROrderPlatform] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET ARITHABORT OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [YHSROrderPlatform] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [YHSROrderPlatform] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET  DISABLE_BROKER 
GO
ALTER DATABASE [YHSROrderPlatform] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [YHSROrderPlatform] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [YHSROrderPlatform] SET RECOVERY FULL 
GO
ALTER DATABASE [YHSROrderPlatform] SET  MULTI_USER 
GO
ALTER DATABASE [YHSROrderPlatform] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [YHSROrderPlatform] SET DB_CHAINING OFF 
GO
EXEC sys.sp_db_vardecimal_storage_format N'YHSROrderPlatform', N'ON'
GO
USE [YHSROrderPlatform]
GO
/****** Object:  Table [dbo].[YHSR_OP_Announcement]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_Announcement](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[Type] [int] NULL,
	[Content] [varchar](255) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[ExpirationTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_CommunityCOMMENT]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_CommunityCOMMENT](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[Content_Seq] [int] NULL,
	[Parent_Seq] [int] NOT NULL,
	[Content] [varchar](2000) NULL,
	[InputTime] [datetime] NULL,
	[State] [int] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_CommunityCOMMENT] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_CommunityCONTENT]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_CommunityCONTENT](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[Content] [varchar](4000) NULL,
	[ContentType_Seq] [int] NOT NULL,
	[Img1] [varchar](255) NULL,
	[Img2] [varchar](255) NULL,
	[Img3] [varchar](255) NULL,
	[Img4] [varchar](255) NULL,
	[Img5] [varchar](255) NULL,
	[Img6] [varchar](255) NULL,
	[Img7] [varchar](255) NULL,
	[Img8] [varchar](255) NULL,
	[Img9] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[State] [int] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_CommunityCONTENT] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_CommunityContentType]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_CommunityContentType](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[TypeName] [varchar](50) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_CommunityContentType] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_CommunityRECORD]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_CommunityRECORD](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[TypeName] [int] NULL,
	[User_Seq] [int] NULL,
	[Content_Seq] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_CommunityRECORD] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ExpressCompany]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ExpressCompany](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Code] [varchar](32) NULL,
	[Name] [varchar](100) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_ExpressCompany] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OnlineGroup]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OnlineGroup](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[GroupName] [varchar](50) NULL,
	[CreateUser_Seq] [int] NULL,
	[InputTime] [datetime] NULL,
	[State] [int] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_OnlineGroup] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OnlineGroupMember]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OnlineGroupMember](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Group_Seq] [int] NULL,
	[User_Seq] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_OnlineGroupMember] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OnlineMessage]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OnlineMessage](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[SenderUser_Seq] [int] NULL,
	[ReceiveObject_Seq] [int] NULL,
	[ReceiveObjectType] [int] NULL,
	[Content] [varchar](max) NULL,
	[ImgPath] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_OnlineMessage] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_Order]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_Order](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[OrderNum] [varchar](255) NOT NULL,
	[OrderPrice] [float] NULL,
	[Paid] [float] NULL,
	[OrderStatus] [int] NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[ExpressCompany_Seq] [int] NULL,
	[ExpressNo] [varchar](255) NULL,
	[ExpressImg] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[RequireTime] [datetime] NULL,
	[PaymentTime] [datetime] NULL,
	[DeliverTime] [datetime] NULL,
	[ReceiveTime] [datetime] NULL,
	[Remark] [varchar](255) NULL,
	[Suggestion] [varchar](255) NULL,
	[Del] [int] NOT NULL,
	[ConfirmTime] [datetime] NULL,
	[StoreTime] [datetime] NULL,
 CONSTRAINT [PK_YHSR_OP_Order_1] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OrderExpress]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OrderExpress](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[Order_Seq] [bigint] NOT NULL,
	[ExpressCompany_Seq] [bigint] NULL,
	[ExpressNo] [varchar](64) NULL,
	[ExpressImg] [varchar](255) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OrderExpressDetails]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OrderExpressDetails](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[OrderExpress_Seq] [bigint] NOT NULL,
	[OrderProducts_Seq] [bigint] NOT NULL,
	[ShoesData_Seq] [bigint] NOT NULL,
	[Num] [int] NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[Order_Seq] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_OrderProducts]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_OrderProducts](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Order_Seq] [int] NOT NULL,
	[ShoesData_Seq] [int] NOT NULL,
	[BuyCount] [int] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
	[DeliverNum] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_OrderProducts] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_PublicityPic]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_PublicityPic](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[ImgMain] [varchar](max) NULL,
	[ImgNewest] [varchar](max) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_PublicityPic] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_PushRecord]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_PushRecord](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[PushUserSeq] [int] NOT NULL,
	[ReceiveUserSeq] [int] NOT NULL,
	[AccountName] [varchar](255) NULL,
	[Type] [int] NULL,
	[OrderSeq] [int] NULL,
	[Content] [varchar](255) NULL,
	[IsRead] [int] NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ShoesCompanyType]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ShoesCompanyType](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Shoes_Seq] [int] NULL,
	[CompanyType_Seq] [int] NULL,
	[Remark] [varchar](max) NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_OP_ShoesCompanyType] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ShoesData]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ShoesData](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Shoes_Seq] [int] NOT NULL,
	[Color_Seq] [int] NOT NULL,
	[Size] [varchar](255) NULL,
	[Num] [int] NULL,
	[StockDate] [datetime] NULL,
	[Stock] [int] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_ShoesData] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ShoesInfo]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ShoesInfo](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Shoes_Seq] [int] NOT NULL,
	[PurchasePrice] [float] NULL,
	[SalePrice] [float] NULL,
	[IsHotSale] [int] NOT NULL,
	[IsNewest] [int] NOT NULL,
	[Remark] [varchar](max) NULL,
	[Del] [int] NOT NULL,
	[SearchTimes] [int] NULL,
	[OemPrice] [float] NULL,
	[WholesalerPrice] [float] NULL,
	[StorePrice] [float] NULL,
	[OnSale] [int] NOT NULL,
	[OffSaleTime] [datetime] NULL,
 CONSTRAINT [PK_YHSR_OP_ShoesInfo] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ShoesValuate]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ShoesValuate](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[Shoes_Seq] [int] NOT NULL,
	[IsBrowse] [int] NOT NULL,
	[BrowseTime] [datetime] NULL,
	[Score] [float] NOT NULL,
	[IsCollected] [int] NOT NULL,
	[CollectedTime] [datetime] NULL,
	[Suggest] [varchar](255) NULL,
	[SuggestTime] [datetime] NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_ShoesValuate] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_ShoppingCart]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_ShoppingCart](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NOT NULL,
	[ShoesData_Seq] [int] NOT NULL,
	[BuyCount] [int] NULL,
	[TotalPrice] [float] NULL,
	[IsChecked] [int] NOT NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_ShoppingCart] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_OP_UserJurisdiction]    Script Date: 2018/6/7 10:50:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_OP_UserJurisdiction](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[User_Seq] [int] NULL,
	[CreateUser_Seq] [int] NOT NULL,
	[EffectiveDate] [datetime] NULL,
	[IsDisable] [int] NOT NULL,
	[IntOfCreateUsers] [int] NOT NULL,
	[AlreadyCreateUsers] [int] NOT NULL,
	[IsAdministrator] [int] NOT NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_OP_UserJurisdiction] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET IDENTITY_INSERT [dbo].[YHSR_OP_ExpressCompany] ON
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (1, N'YTO', N'圆通速递', CAST(N'2018-04-28T11:16:00.000' AS DateTime), 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (2, N'ZTO', N'中通快递', CAST(N'2018-04-26T11:16:47.000' AS DateTime), 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (3, N'SF', N'顺丰速运', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (4, N'HTKY', N'百世快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (5, N'STO', N'申通快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (6, N'YD', N'韵达速递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (7, N'YZPY', N'邮政快递包裹', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (8, N'EMS', N'EMS', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (9, N'HHTT', N'天天快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (10, N'JD', N'京东物流', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (11, N'UC', N'优速快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (12, N'DBL', N'德邦', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (13, N'FAST', N'快捷快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (14, N'ZJS', N'宅急送', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (15, N'TNT', N'TNT快递', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (16, N'UPS', N'UPS', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (17, N'DHL', N'DHL', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (18, N'FEDEX', N'FEDEX联邦(国内件）', NULL, 0)
INSERT [dbo].[YHSR_OP_ExpressCompany] ([Seq], [Code], [Name], [InputTime], [Del]) VALUES (19, N'FEDEX_GJ', N'FEDEX联邦(国际件）', NULL, 0)
SET IDENTITY_INSERT [dbo].[YHSR_OP_ExpressCompany] OFF

ALTER TABLE [dbo].[YHSR_OP_Announcement] ADD  DEFAULT ((0)) FOR [Type]
GO
ALTER TABLE [dbo].[YHSR_OP_Announcement] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_Announcement] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCOMMENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCOMMENT_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCOMMENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCOMMENT_State]  DEFAULT ((1)) FOR [State]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCOMMENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCOMMENT_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCONTENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCONTENT_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCONTENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCONTENT_State]  DEFAULT ((1)) FOR [State]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityCONTENT] ADD  CONSTRAINT [DF_YHSR_OP_CommunityCONTENT_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityContentType] ADD  CONSTRAINT [DF_YHSR_OP_CommunityContentType_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityContentType] ADD  CONSTRAINT [DF_YHSR_OP_CommunityContentType_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityRECORD] ADD  CONSTRAINT [DF_YHSR_OP_CommunityRECORD_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_CommunityRECORD] ADD  CONSTRAINT [DF_YHSR_OP_CommunityRECORD_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ExpressCompany] ADD  CONSTRAINT [DF_YHSR_OP_ExpressCompany_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineGroup] ADD  CONSTRAINT [DF_YHSR_OP_OnlineGroup_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineGroup] ADD  CONSTRAINT [DF_YHSR_OP_OnlineGroup_State]  DEFAULT ((1)) FOR [State]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineGroup] ADD  CONSTRAINT [DF_YHSR_OP_OnlineGroup_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineGroupMember] ADD  CONSTRAINT [DF_YHSR_OP_OnlineGroupMember_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineGroupMember] ADD  CONSTRAINT [DF_YHSR_OP_OnlineGroupMember_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineMessage] ADD  CONSTRAINT [DF_YHSR_OP_OnlineMessage_ReceiveObjectType]  DEFAULT ((1)) FOR [ReceiveObjectType]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineMessage] ADD  CONSTRAINT [DF_YHSR_OP_OnlineMessage_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_OnlineMessage] ADD  CONSTRAINT [DF_YHSR_OP_OnlineMessage_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_Order] ADD  CONSTRAINT [DF_YHSR_OP_Order_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderExpress] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderExpress] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderExpressDetails] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderExpressDetails] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderProducts] ADD  CONSTRAINT [DF_YHSR_OP_OrderProducts_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_OrderProducts] ADD  DEFAULT ((0)) FOR [DeliverNum]
GO
ALTER TABLE [dbo].[YHSR_OP_PublicityPic] ADD  CONSTRAINT [DF_YHSR_OP_PublicityPic_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_PushRecord] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_PushRecord] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesCompanyType] ADD  CONSTRAINT [DF_YHSR_OP_ShoesCompanyType_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesData] ADD  CONSTRAINT [DF_YHSR_OP_ShoesData_StockDate]  DEFAULT (getdate()) FOR [StockDate]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesData] ADD  CONSTRAINT [DF_YHSR_OP_ShoesData_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesInfo] ADD  CONSTRAINT [DF_YHSR_OP_ShoesInfo_IsHotSale]  DEFAULT ((0)) FOR [IsHotSale]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesInfo] ADD  CONSTRAINT [DF_YHSR_OP_ShoesInfo_IsNewest]  DEFAULT ((0)) FOR [IsNewest]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesInfo] ADD  CONSTRAINT [DF_YHSR_OP_ShoesInfo_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesInfo] ADD  DEFAULT ((0)) FOR [SearchTimes]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesInfo] ADD  DEFAULT ((0)) FOR [OnSale]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OP_ShoesValuate_isBrowse]  DEFAULT ((0)) FOR [IsBrowse]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OP_ShoesValuate_Score]  DEFAULT ((0)) FOR [Score]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OP_ShoesValuate_IsCollected]  DEFAULT ((0)) FOR [IsCollected]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OP_ShoesValuate_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoesValuate] ADD  CONSTRAINT [DF_YHSR_OP_ShoesValuate_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoppingCart] ADD  CONSTRAINT [DF_YHSR_OP_ShoppingCart_isChecked]  DEFAULT ((1)) FOR [IsChecked]
GO
ALTER TABLE [dbo].[YHSR_OP_ShoppingCart] ADD  CONSTRAINT [DF_YHSR_OP_ShoppingCart_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_OP_UserJurisdiction] ADD  CONSTRAINT [DF_YHSR_OP_UserJurisdiction_IsDisable]  DEFAULT ((0)) FOR [IsDisable]
GO
ALTER TABLE [dbo].[YHSR_OP_UserJurisdiction] ADD  CONSTRAINT [DF_YHSR_OP_UserJurisdiction_IntOfCreateUsers]  DEFAULT ((0)) FOR [IntOfCreateUsers]
GO
ALTER TABLE [dbo].[YHSR_OP_UserJurisdiction] ADD  CONSTRAINT [DF_YHSR_OP_UserJurisdiction_AlreadyCreateUsers]  DEFAULT ((0)) FOR [AlreadyCreateUsers]
GO
ALTER TABLE [dbo].[YHSR_OP_UserJurisdiction] ADD  CONSTRAINT [DF_YHSR_OP_UserJurisdiction_IsAdministrator]  DEFAULT ((0)) FOR [IsAdministrator]
GO
ALTER TABLE [dbo].[YHSR_OP_UserJurisdiction] ADD  CONSTRAINT [DF_YHSR_OP_UserJurisdiction_Del]  DEFAULT ((0)) FOR [Del]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'对应工厂seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Announcement', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公告类型 0：其他，1：新品，2：直播' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Announcement', @level2type=N'COLUMN',@level2name=N'Type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Announcement', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'过期时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Announcement', @level2type=N'COLUMN',@level2name=N'ExpirationTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联内容Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'Content_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联父类Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'Parent_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评论内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'Content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态(1:正常, 0:删除, 2:停用)
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'State'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCOMMENT', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分享内容（文本）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'Content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容类型序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'ContentType_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态(1:正常, 0:删除, 2:停用)
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'State'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityCONTENT', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型名称：爆款信息、消费者声音、个人建议' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityContentType', @level2type=N'COLUMN',@level2name=N'TypeName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'互动方式(1:浏览, 2:点赞)
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'TypeName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联内容Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'Content_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_CommunityRECORD', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递公司代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递公司名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ExpressCompany', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ExpressCompany', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroup', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'群组名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroup', @level2type=N'COLUMN',@level2name=N'GroupName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroup', @level2type=N'COLUMN',@level2name=N'CreateUser_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态
1：正常
0：删除
2：停用' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroup', @level2type=N'COLUMN',@level2name=N'State'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroup', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroupMember', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'群组序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroupMember', @level2type=N'COLUMN',@level2name=N'Group_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'成员序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroupMember', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroupMember', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineGroupMember', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发送消息人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'SenderUser_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接收消息对象（根据接收对象类型关联用户表或者群组表）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'ReceiveObject_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接收对象类型 1用户 2群组' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'ReceiveObjectType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'消息内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'Content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传的图片路径' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'ImgPath'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识0未删除，1删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OnlineMessage', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户Seq(外键:userInfo表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'OrderNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'OrderPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已付金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Paid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单状态(0：未接单、1：已接单（未发货）、2：已发货、3：已完成、4：已取消)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'OrderStatus'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商户序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ExpressCompany_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ExpressNo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'快递单图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ExpressImg'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'付款时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'PaymentTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发货时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'DeliverTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收货时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ReceiveTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'留言' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Suggestion'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'确认时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'ConfirmTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'入库时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_Order', @level2type=N'COLUMN',@level2name=N'StoreTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpress', @level2type=N'COLUMN',@level2name=N'Order_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'物流公司序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpress', @level2type=N'COLUMN',@level2name=N'ExpressCompany_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'物流单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpress', @level2type=N'COLUMN',@level2name=N'ExpressNo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'大货发货单信息图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpress', @level2type=N'COLUMN',@level2name=N'ExpressImg'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单物流序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpressDetails', @level2type=N'COLUMN',@level2name=N'OrderExpress_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单商品序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpressDetails', @level2type=N'COLUMN',@level2name=N'OrderProducts_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单详情的商品序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpressDetails', @level2type=N'COLUMN',@level2name=N'ShoesData_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发货量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpressDetails', @level2type=N'COLUMN',@level2name=N'Num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderExpressDetails', @level2type=N'COLUMN',@level2name=N'Order_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单序号(外键:YHSR_OP_Order表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'Order_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子数据序号(外键:YHSR_OP_ShoesData表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'ShoesData_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'BuyCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已发货量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_OrderProducts', @level2type=N'COLUMN',@level2name=N'DeliverNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所属公司序号(外键:YHSR_Base_Company表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'首页展示图片1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'ImgMain'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'新品推荐展示图' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'ImgNewest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PublicityPic', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'推送类型(1:订单，2:直播)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PushRecord', @level2type=N'COLUMN',@level2name=N'Type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0:未读，1:已读' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_PushRecord', @level2type=N'COLUMN',@level2name=N'IsRead'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesCompanyType', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司类型序号用于判断此鞋子什么类型的公司用户可以查看' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesCompanyType', @level2type=N'COLUMN',@level2name=N'CompanyType_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号(外键:YHSR_Goods_Shoes表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色序号(外键:YHSR_Goods_Color表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Color_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'尺码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Size'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'StockDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Stock'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesData', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号(外键:YHSR_Goods_Shoes表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'第一阶梯订货价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'PurchasePrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建议零售价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'SalePrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'人工热卖爆款推荐(0:否, 1:是    最多三个)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'IsHotSale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'人工推荐新品推荐(0:否, 1:是    最多5个)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'IsNewest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'搜索次数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'SearchTimes'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贴牌商价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'OemPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'批发商价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'WholesalerPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'直营店价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'StorePrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否上架（0:下架 1:上架）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'OnSale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下架时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesInfo', @level2type=N'COLUMN',@level2name=N'OffSaleTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'
用户序号(外键:YHSR_Base_User表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子序号(外键:YHSR_Goods_Shoes表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Shoes_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否浏览：0，浏览；1，取消浏览' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'IsBrowse'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'浏览时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'BrowseTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'我的评分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Score'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否收藏(0:未收藏, 1:已收藏)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'IsCollected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'收藏时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'CollectedTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户建议' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Suggest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建议时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'SuggestTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoesValuate', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户Seq(外键:YHSR_Base_User表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子数据序号(外键:YHSR_OP_ShoesData表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'ShoesData_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'BuyCount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总价格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'TotalPrice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否勾选，0不勾选 1勾选' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'IsChecked'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_ShoppingCart', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户序号(外键:YHSR_Base_User表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'CreateUser_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'有效日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'EffectiveDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否停用(0:可用, 1:停用)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'IsDisable'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'可创建用户数(默认为0:最后一级,只能使用系统)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'IntOfCreateUsers'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已创建用户数(如等于"可创建用户数",则不能在创建用户)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'AlreadyCreateUsers'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否为管理员(0:是, 1:否)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'IsAdministrator'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_OP_UserJurisdiction', @level2type=N'COLUMN',@level2name=N'Del'
GO
USE [master]
GO
ALTER DATABASE [YHSROrderPlatform] SET  READ_WRITE 
GO
