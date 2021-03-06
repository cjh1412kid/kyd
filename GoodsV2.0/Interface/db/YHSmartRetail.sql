USE [master]
GO
/****** Object:  Database [YHSmartRetail]    Script Date: 2018/6/7 10:49:22 ******/
CREATE DATABASE [YHSmartRetail] ON  PRIMARY
( NAME = N'YHSmartRetail', FILENAME = N'D:\MSSqlServer\OrderSales\YHSmartRetail.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'YHSmartRetail_log', FILENAME = N'D:\MSSqlServer\OrderSales\YHSmartRetail_log.ldf' , SIZE = 2304KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [YHSmartRetail] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [YHSmartRetail].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [YHSmartRetail] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [YHSmartRetail] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [YHSmartRetail] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [YHSmartRetail] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [YHSmartRetail] SET ARITHABORT OFF 
GO
ALTER DATABASE [YHSmartRetail] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [YHSmartRetail] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [YHSmartRetail] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [YHSmartRetail] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [YHSmartRetail] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [YHSmartRetail] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [YHSmartRetail] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [YHSmartRetail] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [YHSmartRetail] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [YHSmartRetail] SET  DISABLE_BROKER 
GO
ALTER DATABASE [YHSmartRetail] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [YHSmartRetail] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [YHSmartRetail] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [YHSmartRetail] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [YHSmartRetail] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [YHSmartRetail] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [YHSmartRetail] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [YHSmartRetail] SET RECOVERY FULL 
GO
ALTER DATABASE [YHSmartRetail] SET  MULTI_USER 
GO
ALTER DATABASE [YHSmartRetail] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [YHSmartRetail] SET DB_CHAINING OFF 
GO
EXEC sys.sp_db_vardecimal_storage_format N'YHSmartRetail', N'ON'
GO
USE [YHSmartRetail]
GO
/****** Object:  Table [dbo].[YHSR_Base_Agent]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_Agent](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[AgentName] [varchar](60) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[Remark] [varchar](255) NULL,
	[Brand_Seq] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_Area]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_Area](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[ParentSeq] [int] NULL,
	[Brand_Seq] [int] NOT NULL,
	[Name] [varchar](50) NULL,
	[InputTime] [datetime] NULL,
	[Bound] [text] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Base_Area] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_Brand]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_Brand](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NULL,
	[Company_Seq] [int] NOT NULL,
	[Remark] [varchar](255) NULL,
	[Del] [int] NOT NULL,
	[Image] [varchar](255) NULL,
 CONSTRAINT [PK_YHSR_Base_Brand] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_Company]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_Company](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](255) NULL,
	[Address] [varchar](255) NULL,
	[ParentSeq] [int] NULL,
	[Lat] [float] NULL,
	[Lng] [float] NULL,
	[CompanyType_Seq] [int] NOT NULL,
	[Remark] [varchar](255) NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Base_Company] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_CompanyType]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_CompanyType](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[TypeName] [varchar](50) NULL,
	[Del] [int] NULL,
 CONSTRAINT [PK_YHSR_Base_CompanyType] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_Shop]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_Shop](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Area_seq] [int] NOT NULL,
	[Id] [varchar](255) NULL,
	[Name] [varchar](255) NULL,
	[Address] [varchar](255) NULL,
	[Lat] [float] NULL,
	[Lng] [float] NULL,
	[InstallDate] [datetime] NULL,
	[Remark] [varchar](255) NULL,
	[InputTime] [datetime] NULL,
	[ShopTypeFlag] [int] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Base_Shop] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Base_User]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Base_User](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[AccountName] [varchar](50) NULL,
	[Password] [varchar](max) NULL,
	[Company_Seq] [int] NULL,
	[Brand_Seq] [int] NULL,
	[AttachType] [int] NOT NULL,
	[Attach_Seq] [bigint] NULL,
	[SaleType] [int] NOT NULL,
	[Shop_Seq] [int] NULL,
	[UserName] [varchar](50) NULL,
	[Telephone] [varchar](20) NULL,
	[Address] [varchar](max) NULL,
	[HeadImg] [varchar](max) NULL,
	[RongCloudToken] [varchar](150) NULL,
	[JPushToken] [varchar](150) NULL,
	[GotyeToken] [varchar](150) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Base_User] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Goods_Category]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Goods_Category](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[ParentSeq] [int] NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
	[Visible] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Goods_Category] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Goods_Color]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Goods_Color](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Company_Seq] [int] NOT NULL,
	[Code] [varchar](50) NULL,
	[Name] [varchar](50) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Goods_Period]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Goods_Period](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Brand_Seq] [int] NULL,
	[Name] [varchar](255) NULL,
	[Year] [int] NULL,
	[SaleDate] [datetime] NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Goods_Period] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Goods_Shoes]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Goods_Shoes](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[Period_Seq] [int] NULL,
	[Category_Seq] [int] NULL,
	[GoodName] [varchar](50) NULL,
	[GoodID] [varchar](50) NULL,
	[Color] [varchar](100) NULL,
	[SurfaceMaterial] [varchar](100) NULL,
	[InnerMaterial] [varchar](100) NULL,
	[PopularElement] [varchar](100) NULL,
	[SoleMaterial] [varchar](100) NULL,
	[CloseForm] [varchar](100) NULL,
	[HeelShape] [varchar](100) NULL,
	[ToeStyle] [varchar](100) NULL,
	[HeelHeight] [varchar](100) NULL,
	[Introduce] [varchar](250) NULL,
	[Description] [varchar](max) NULL,
	[Video] [varchar](max) NULL,
	[Img1] [varchar](max) NULL,
	[Img2] [varchar](max) NULL,
	[Img3] [varchar](max) NULL,
	[Img4] [varchar](max) NULL,
	[Img5] [varchar](max) NULL,
	[InputTime] [datetime] NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK_YHSR_Goods_Shoes] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_Home_Carousel]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_Home_Carousel](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[Brand_Seq] [bigint] NOT NULL,
	[Image] [varchar](64) NOT NULL,
	[Type] [int] NOT NULL,
	[Link_Seq] [varchar](255) NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [int] NOT NULL,
 CONSTRAINT [PK__YHSR_Hom__CA1E3C880D7A0286] PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_System_Menu]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_System_Menu](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[Parent_Seq] [bigint] NOT NULL,
	[Name] [varchar](50) NULL,
	[Url] [varchar](200) NULL,
	[Perms] [varchar](500) NULL,
	[Type] [int] NULL,
	[Icon] [varchar](50) NULL,
	[Order_Num] [int] NOT NULL,
	[InputTime] [datetime] NOT NULL,
	[Alone] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[YHSR_System_User_Menu]    Script Date: 2018/6/7 10:49:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[YHSR_System_User_Menu](
	[Seq] [bigint] IDENTITY(1,1) NOT NULL,
	[User_Seq] [bigint] NOT NULL,
	[Menu_Seq] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET IDENTITY_INSERT [dbo].[YHSR_Base_CompanyType] ON
INSERT [dbo].[YHSR_Base_CompanyType] ([Seq], [TypeName], [Del]) VALUES (1, N'厂家', 0)
INSERT [dbo].[YHSR_Base_CompanyType] ([Seq], [TypeName], [Del]) VALUES (2, N'贴牌商', 0)
INSERT [dbo].[YHSR_Base_CompanyType] ([Seq], [TypeName], [Del]) VALUES (3, N'批发商', 0)
INSERT [dbo].[YHSR_Base_CompanyType] ([Seq], [TypeName], [Del]) VALUES (4, N'直营店', 0)
SET IDENTITY_INSERT [dbo].[YHSR_Base_CompanyType] OFF

SET IDENTITY_INSERT [dbo].[YHSR_Base_User] ON
INSERT [dbo].[YHSR_Base_User] ([Seq], [AccountName], [Password], [Company_Seq], [Brand_Seq], [AttachType], [Attach_Seq], [SaleType], [Shop_Seq], [UserName], [Telephone], [Address], [HeadImg], [RongCloudToken], [JPushToken], [GotyeToken], [InputTime], [Del]) VALUES (1, N'admin', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', -1, -1, 1, 1, 1, -1, N'超级管理员', N'', N'', N'', N'', NULL, NULL, CAST(N'2018-06-01T00:00:00.000' AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[YHSR_Base_User] OFF

SET IDENTITY_INSERT [dbo].[YHSR_System_Menu] ON
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (1, 0, N'管理员', NULL, NULL, 0, N'fa fa-cog', 0, CAST(N'2018-05-28T17:21:03.000' AS DateTime), 0)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (2, 1, N'工厂创建', N'modules/system/super/factory.html', N'sys:factory:list', 1, N'fa fa-user', 1, CAST(N'2018-05-28T17:33:45.000' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (3, 2, N'新增', NULL, N'sys:factory:save', 2, NULL, 0, CAST(N'2018-05-29T11:26:30.687' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (4, 2, N'修改', NULL, N'sys:factory:save,sys:factory:info', 2, NULL, 0, CAST(N'2018-05-29T11:26:52.857' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (5, 2, N'删除', NULL, N'sys:factory:delete', 2, NULL, 0, CAST(N'2018-05-29T11:27:20.670' AS DateTime), 0)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (6, 1, N'菜单管理', N'modules/system/super/menu.html', N'sys:menu:list', 1, N'fa fa-th-list', 2, CAST(N'2018-05-28T17:47:34.000' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (7, 6, N'新增', NULL, N'sys:menu:save,sys:menu:select', 2, NULL, 0, CAST(N'2018-05-29T09:52:01.653' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (8, 6, N'修改', NULL, N'sys:menu:info,sys:menu:update,sys:menu:select', 2, NULL, 0, CAST(N'2018-05-29T09:53:24.780' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (9, 6, N'删除', NULL, N'sys:menu:delete', 2, NULL, 0, CAST(N'2018-05-29T09:54:07.857' AS DateTime), 0)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (10, 0, N'企业基础信息', NULL, NULL, 0, N'fa fa-building', 1, CAST(N'2018-05-29T16:00:54.403' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (11, 10, N'品牌管理', N'modules/system/brand.html', NULL, 1, N'fa fa-bandcamp', 0, CAST(N'2018-05-29T16:03:14.530' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (12, 10, N'分公司管理', N'modules/system/area.html', NULL, 1, N'fa fa-briefcase', 2, CAST(N'2018-05-29T16:06:34.797' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (13, 10, N'代理管理', N'modules/system/agent.html', NULL, 1, N'fa fa-user-md', 3, CAST(N'2018-05-29T16:14:00.593' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (14, 10, N'门店管理', N'modules/system/shop_manage.html', NULL, 1, N'fa fa-map-pin', 4, CAST(N'2018-05-29T16:15:45.310' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (15, 10, N'用户管理', N'modules/system/factory/sub_account.html', NULL, 1, N'fa fa-address-book', 5, CAST(N'2018-06-01T16:04:30.153' AS DateTime), 1)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (16, 0, N'货品管理', NULL, NULL, 0, N'fa fa-shopping-bag', 2, CAST(N'2018-05-29T16:26:56.700' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (17, 16, N'波次管理', N'modules/system/period.html', NULL, 1, N'fa fa-align-left', 1, CAST(N'2018-05-29T16:05:12.297' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (18, 16, N'颜色管理', N'modules/system/good_color.html', NULL, 1, N'fa fa-square', 0, CAST(N'2018-05-31T16:57:17.217' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (19, 16, N'分类管理', N'modules/system/category.html', NULL, 1, N'fa fa-bars', 2, CAST(N'2018-06-04T16:32:25.593' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (20, 16, N'商品管理', N'modules/system/order_platform/goods.html', NULL, 1, N'fa fa-bars', 3, CAST(N'2018-06-03T15:18:41.310' AS DateTime), 0)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (21, 0, N'快易订系统', NULL, NULL, 0, N'fa fa-book', 3, CAST(N'2018-05-29T16:28:11.687' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (22, 21, N'公告管理', N'modules/system/order_platform/announcement.html', NULL, 1, N'fa fa-street-view', 0, CAST(N'2018-06-08T12:49:29.780' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (23, 21, N'轮播管理', N'modules/system/home_page_management/sowing_map.html', NULL, 1, N'fa fa-file-image-o', 0, CAST(N'2018-05-30T09:44:15.250' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (24, 21, N'订货方管理', N'modules/system/order_platform/order_party.html', NULL, 1, N'fa fa-gavel', 2, CAST(N'2018-05-30T15:02:45.967' AS DateTime), 1)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (25, 21, N'订单管理', N'modules/system/order_platform/order.html', NULL, 1, N'fa fa-newspaper-o', 3, CAST(N'2018-06-03T16:35:18.200' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (26, 25, N'接单', NULL, N'order:receiveOrder', 2, NULL, 0, CAST(N'2018-06-07 17:11:47.170' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (27, 25, N'审核', NULL, N'order:checkOrder', 2, NULL, 0, CAST(N'2018-06-07 17:12:15.340'AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (28, 25, N'入库', NULL, N'order:storeOrder', 2, NULL, 0, CAST(N'2018-06-07 17:12:35.497'AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (29, 25, N'发货', NULL, N'order:deliverOrder', 2, NULL, 0, CAST(N'2018-06-07 17:12:57.763'AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (30, 25, N'取消订单', NULL, N'order:cancelOrder', 2, NULL, 0, CAST(N'2018-06-07 17:13:16.373'AS DateTime), 0)

INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (31, 0, N'自动售卖系统', NULL, NULL, 0, N'fa fa-credit-card-alt', 4, CAST(N'2018-05-29T16:29:35.640' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (32, 31, N'订单管理', N'modules/system/online_sale/order.html', NULL, 1, N'fa fa-newspaper-o', 0, CAST(N'2018-06-03T16:43:32.733' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (33, 31, N'专题管理', N'modules/system/online_sale/topic.html', NULL, 1, N'fa fa-flag', 1, CAST(N'2018-06-03T16:46:13.577' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (34, 31, N'退换货管理', N'modules/system/online_sale/return_handling.html', NULL, 1, N'fa fa-volume-control-phone', 2, CAST(N'2018-06-03T16:48:28.233' AS DateTime), 0)
INSERT [dbo].[YHSR_System_Menu] ([Seq], [Parent_Seq], [Name], [Url], [Perms], [Type], [Icon], [Order_Num], [InputTime], [Alone]) VALUES (35, 31, N'用户管理', N'modules/system/online_sale/user_management.html', NULL, 1, N'fa fa-street-view', 3, CAST(N'2018-06-03T16:49:29.780' AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[YHSR_System_Menu] OFF

SET IDENTITY_INSERT [dbo].[YHSR_System_User_Menu] ON
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (1, 1, 1)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (2, 1, 2)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (3, 1, 3)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (4, 1, 4)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (5, 1, 5)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (6, 1, 6)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (7, 1, 7)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (8, 1, 8)
INSERT [dbo].[YHSR_System_User_Menu] ([Seq], [User_Seq], [Menu_Seq]) VALUES (9, 1, 9)
SET IDENTITY_INSERT [dbo].[YHSR_System_User_Menu] OFF

ALTER TABLE [dbo].[YHSR_Base_Agent] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Base_Agent] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_Area] ADD  CONSTRAINT [DF_YHSR_Base_Area_CreatTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Base_Area] ADD  CONSTRAINT [DF_YHSR_Base_Area_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_Brand] ADD  CONSTRAINT [DF_YHSR_Base_Brand_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_Company] ADD  CONSTRAINT [DF_YHSR_Base_Company_ParentSeq]  DEFAULT ((0)) FOR [ParentSeq]
GO
ALTER TABLE [dbo].[YHSR_Base_Company] ADD  CONSTRAINT [DF_YHSR_Base_Company_TypeName]  DEFAULT ((1)) FOR [CompanyType_Seq]
GO
ALTER TABLE [dbo].[YHSR_Base_Company] ADD  CONSTRAINT [DF_YHSR_Base_Company_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_CompanyType] ADD  CONSTRAINT [DF_YHSR_Base_CompanyType_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_Shop] ADD  CONSTRAINT [DF_YHSR_Base_Shop_InputDate]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Base_Shop] ADD  CONSTRAINT [DF_YHSR_Base_Shop_ShopTypeFlag]  DEFAULT ((0)) FOR [ShopTypeFlag]
GO
ALTER TABLE [dbo].[YHSR_Base_Shop] ADD  CONSTRAINT [DF_YHSR_Base_Shop_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Base_User] ADD  CONSTRAINT [DF_YHSR_Base_User_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Base_User] ADD  CONSTRAINT [DF_YHSR_Base_User_Del_1]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Goods_Category] ADD  CONSTRAINT [DF_YHSR_Goods_Category_CreatTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Goods_Category] ADD  CONSTRAINT [DF_YHSR_Goods_Category_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Goods_Category] ADD  DEFAULT ((0)) FOR [Visible]
GO
ALTER TABLE [dbo].[YHSR_Goods_Color] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Goods_Color] ADD  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Goods_Period] ADD  CONSTRAINT [DF_YHSR_Goods_Period_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Goods_Period] ADD  CONSTRAINT [DF_YHSR_Goods_Period_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Goods_Shoes] ADD  CONSTRAINT [DF_YHSR_Goods_Shoes_InputTime]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Goods_Shoes] ADD  CONSTRAINT [DF_YHSR_Goods_Shoes_Del]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_Home_Carousel] ADD  CONSTRAINT [DF__YHSR_Home__Input__10566F31]  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_Home_Carousel] ADD  CONSTRAINT [DF__YHSR_Home_C__Del__0F624AF8]  DEFAULT ((0)) FOR [Del]
GO
ALTER TABLE [dbo].[YHSR_System_Menu] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[YHSR_System_Menu] ADD  DEFAULT ((0)) FOR [Alone]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'代理商名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Agent', @level2type=N'COLUMN',@level2name=N'AgentName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Agent', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Agent', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区域父节点序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'ParentSeq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号 品牌下的区域(外键:YHSR_Base_Brand表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区域名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区域的地理范围' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'Bound'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Area', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所属公司序号(外键:YHSR_Base_Company表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌描述图片' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Brand', @level2type=N'COLUMN',@level2name=N'Image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司集团名字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总公司（父序号）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'ParentSeq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'纬度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Lat'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'经度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Lng'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司类型序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'CompanyType_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Company', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司类型
1、厂家
2、贴牌商
批发商' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_CompanyType', @level2type=N'COLUMN',@level2name=N'TypeName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联区域表序号(外键:YHSR_Base_Area表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Area_seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'门店编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'店名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'纬度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Lat'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'经度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Lng'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'安装时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'InstallDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'入库时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'门店类型(0:普通店,1:集合店)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'ShopTypeFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_Shop', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'AccountName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Password'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司序号(外键:YHSR_Base_Company表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'账号所属类型：1.工厂，2.分公司，3.代理商，4.工厂子账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'AttachType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'账号所属类型为分公司时为分公司seq，类型为代理商时为代理商seq，其他情况为null' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Attach_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'账号销售类型：1.工厂，2.贴牌商，3.批发商，4.直营店' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'SaleType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'门店序号（用于判断用户是否是直营店的用户）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Shop_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'UserName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Telephone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址（物流）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Address'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'头像' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'HeadImg'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'融云平台token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'RongCloudToken'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'极光推送平台token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'JPushToken'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'亲加直播平台token' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'GotyeToken'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'插入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Base_User', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类父节点序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'ParentSeq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司序号(外键:YHSR_Base_Company表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否可见（0:可见 1:不可见）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Category', @level2type=N'COLUMN',@level2name=N'Visible'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司序号(外键:YHSR_Base_Company表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'Company_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'Code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Color', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'品牌序号(外键:YHSR_Base_Brand表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'波次名字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'年份' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'Year'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上架销售时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'SaleDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'入库时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Period', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'序号(主键)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'波次序号(外键:YHSR_Goods_Period表)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Period_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋子类别序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Category_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'货品名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'GoodName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'货号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'GoodID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Color'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋面材质' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'SurfaceMaterial'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋里材质' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'InnerMaterial'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流行元素' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'PopularElement'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋底材质' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'SoleMaterial'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'闭合方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'CloseForm'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋跟形状' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'HeelShape'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋头风格' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'ToeStyle'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'鞋跟高度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'HeelHeight'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商品介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Introduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'产品详细描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Description'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'视频介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Video'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片（主）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Img1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Img2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片3' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Img3'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片4' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Img4'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'图片5' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Img5'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'入库时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识(0:未删除,1:已删除)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Goods_Shoes', @level2type=N'COLUMN',@level2name=N'Del'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联品牌的Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Home_Carousel', @level2type=N'COLUMN',@level2name=N'Brand_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'轮播图名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Home_Carousel', @level2type=N'COLUMN',@level2name=N'Image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'轮播图类别，1：单个鞋子，2：鞋子分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Home_Carousel', @level2type=N'COLUMN',@level2name=N'Type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联的类型的序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Home_Carousel', @level2type=N'COLUMN',@level2name=N'Link_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_Home_Carousel', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父菜单ID，一级菜单为0' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Parent_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单URL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Url'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'授权(多个用逗号分隔，如：user:list,user:create)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Perms'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型   0：目录   1：菜单   2：按钮' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单图标' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Icon'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Order_Num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工厂私有菜单' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_Menu', @level2type=N'COLUMN',@level2name=N'Alone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_User_Menu', @level2type=N'COLUMN',@level2name=N'User_Seq'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'菜单Seq' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'YHSR_System_User_Menu', @level2type=N'COLUMN',@level2name=N'Menu_Seq'
GO
USE [master]
GO
ALTER DATABASE [YHSmartRetail] SET  READ_WRITE 
GO
